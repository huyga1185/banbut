package com.web.banbut.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.web.banbut.dto.request.AuthenticationRequest;
import com.web.banbut.dto.request.IntrospectRequest;
import com.web.banbut.dto.response.AuthenticationResponse;
import com.web.banbut.dto.response.IntrospectResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final String signerKey;
    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender javaMailSender;
    private final String sender;

    public AuthenticationService(
        UserRepository userRepository,
        @Value("${jwt.secret}") String signerKey,
        RedisTemplate<String, String> redisTemplate,
        JavaMailSender javaMailSender,
        @Value("${spring.mail.username}") String sender
    ) {
        this.userRepository = userRepository;
        this.signerKey = signerKey;
        this.redisTemplate = redisTemplate;
        this.javaMailSender = javaMailSender;
        this.sender = sender;
    }

    public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_DOES_NOT_MATCH);
        return new AuthenticationResponse(generateToken(user.getUsername(), user.getRole()));
    }

    private String generateToken(String username, String role) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("authorities", List.of(role))
                .issuer("banbut.com")
                .issueTime(new Date())
                .expirationTime(Date.from(
                        Instant.now().plus(14, ChronoUnit.DAYS)
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.AUTH_TOKEN_SIGN_FAILED);
        }
    }

    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        String token = introspectRequest.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier) && expiryTime.after(new Date());
            List<String> authorities = signedJWT.getJWTClaimsSet().getStringListClaim("authorities");
            String role = (authorities != null && !authorities.isEmpty()) ? authorities.getFirst() : null;

            return new IntrospectResponse(
                    verified,
                    verified ? signedJWT.getJWTClaimsSet().getSubject() : null,
                    verified ? role : null,
                    verified ? signedJWT.getJWTClaimsSet().getExpirationTime().getTime() / 1000 : null
            );

        } catch (ParseException ex) {
            throw new AppException(ErrorCode.COULD_NOT_PARSE_TOKEN);
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.COULD_NOT_VERIFY_TOKEN);
        }
    }

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder OTP = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            OTP.append(random.nextInt(10));
        }
        return OTP.toString();
    }

    public void requestOTP(String email) {
        userRepository.findByEmail(email)
            .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );
        String otp = generateOTP();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        String expireTime = LocalDateTime.now().plusMinutes(5).format(formatter);
        String html = String.format(
            """
                <h1 style="color:green;">OTP for banbut</h1>
                <p>Hey customer,</p>
                <p>Use the One-time Password (OTP): <b>%s</b> to verify and complete your reset password progress.</p>
                <br>
                <p>This code will be active till <b>%s</b>. You may request for a new code if you did not enter it within the stipulated timing.</p>
            """,
            otp,
            expireTime
        );
        try {
            redisTemplate.opsForValue().set("otp:" + email, otp, Duration.ofMinutes(5));
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("OTP for Banbut");
            helper.setText(html, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public void verifyOTP(String email, String otp) {

    }
}
