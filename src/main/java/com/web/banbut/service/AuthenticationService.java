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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final String signerKey;

    public AuthenticationService(UserRepository userRepository, @Value("${jwt.secret}") String signerKey) {
        this.userRepository = userRepository;
        this.signerKey = signerKey;
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
}
