package org.validation.validator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
public class JwtGenerator {

    private String secretKey;
    private long expirationMillis;

    public JwtGenerator(String secretKey, long expirationMillis){
        this.secretKey = secretKey;
        this.expirationMillis = expirationMillis;
    }
    public JwtGenerator(){

    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public long getExpirationMillis(){
        return expirationMillis;
    }

    public void setExpirationMillis(long expirationMillis){
        this.expirationMillis = expirationMillis;
    }
    public String generateJwt(String subject, long expirationMillis, String secretKey) {
        // Create a signing key from the provided secret key
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        // Calculate the expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMillis);

        // Generate the JWT token
        String jwtToken = Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //System.out.println(jwtToken);
        return jwtToken;
    }

    public String verifyJwt(String jwtToken, String secretKey) {
        try {
            // Create a verification key from the provided secret key
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            // Parse and verify the JWT token
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

            return "verification pass";
        } catch (Exception e) {
            return "verification fails";
        }
    }
}
