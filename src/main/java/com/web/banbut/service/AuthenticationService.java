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
import com.web.banbut.dto.response.VerifyOTPResponse;
import com.web.banbut.entity.User;
import com.web.banbut.exception.AppException;
import com.web.banbut.exception.ErrorCode;
import com.web.banbut.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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

    public void requestOTP(String email, int option) {
        if (option < 0 && option > 2)
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        if (option != 1)
            userRepository.findByEmail(email)
                .orElseThrow(
                    () -> new AppException(ErrorCode.USER_NOT_FOUND)
                );
        String otp = generateOTP();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        String expireTime = LocalDateTime.now().plusMinutes(5).format(formatter);
        String resetPassword = String.format(
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
        String registerAccount = String.format(
            """
                <h1 style="color:green;">OTP for banbut</h1>
                <p>Hey customer,</p>
                <p>Use the One-time Password (OTP): <b>%s</b> to verify and complete your register account progress.</p>
                <br>
                <p>This code will be active till <b>%s</b>. You may request for a new code if you did not enter it within the stipulated timing.</p>
            """,
            otp,
            expireTime
        );
        String confirmOldEmail = String.format(
            """
                <h1 style="color:green;">OTP for banbut</h1>
                <p>Hey customer,</p>
                <p>Use the One-time Password (OTP): <b>%s</b><p style="color:red;"> to verify and complete your update email progress. If you did not request it, please reset your password <a href="https://example.com/reset-password">here</a></p></p>
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
            switch(option) {
                case 0:
                    helper.setText(resetPassword, true);
                    break;
                case 1:
                    helper.setText(registerAccount, true);
                    break;
                case 2:
                    helper.setText(confirmOldEmail, true);
                    break;
                default:
                    break;
            }
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("[Error]: In send java mail " + e);
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    private String generateTemporaryToken(String email) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(email)
                .issuer("banbut.com")
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(
                        Instant.now().plus(5, ChronoUnit.MINUTES)
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.AUTH_TOKEN_SIGN_FAILED);
        }
    }

    public boolean verifyTemporaryToken(String token, String email) {
        SecretKey secretKey = new SecretKeySpec(signerKey.getBytes(), "HS512");
        JwtDecoder decoder = NimbusJwtDecoder
            .withSecretKey(secretKey)
            .macAlgorithm(MacAlgorithm.HS512)
            .build();
        try {
            Jwt jwt =  decoder.decode(token);
            Instant expireTime = jwt.getClaimAsInstant("exp");
            String subject = jwt.getSubject();
            if (Instant.now().isBefore(expireTime) && email.equals(subject) && redisTemplate.opsForValue().get("token:" + token) == null) {
                redisTemplate.opsForValue().set("token:" + token, email, Duration.ofMinutes(5));
                return true;
            } else
                return false;
        } catch (JwtException e) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    public VerifyOTPResponse verifyOTP(String email, String otp) {
        String oTP = redisTemplate.opsForValue().get("otp:" + email);
        if (oTP == null)
            throw new AppException(ErrorCode.OTP_EXPIRED);
        if (!oTP.equals(otp))
            throw new AppException(ErrorCode.OTP_DOES_NOT_MATCH);
        redisTemplate.delete("otp:" + email);
        return new VerifyOTPResponse(
                generateTemporaryToken(email)
        );
    }
}