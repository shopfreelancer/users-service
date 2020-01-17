package com.shopfreelancer.userservice.shared;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class Utils {

    private static String TOKEN_SECRET;
    private static Integer EXPIRATION_TIME;

    @Value("$(app.token.secret)")
    public void setTokenSecret(String tokenSecret){
        TOKEN_SECRET = tokenSecret;
    }

    @Value("$(app.token.expiration.time)")
    public void setExpirationTime(Integer expirationTime){
        EXPIRATION_TIME = expirationTime;
    }

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateAddressId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public static boolean hasTokenExpired(String token) {
        boolean returnValue = false;

        try {
            Claims claims = Jwts.parser().setSigningKey(Utils.TOKEN_SECRET).parseClaimsJws(token)
                    .getBody();

            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();

            returnValue = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            returnValue = true;
        }

        return returnValue;
    }

    public String generateEmailVerificationToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Utils.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Utils.TOKEN_SECRET)
                .compact();
        return token;
    }

    public String generatePasswordResetToken(String userId)
    {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + Utils.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, Utils.TOKEN_SECRET)
                .compact();
        return token;
    }

}
