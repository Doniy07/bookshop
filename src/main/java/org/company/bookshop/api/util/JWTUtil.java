package org.company.bookshop.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JWTUtil {
    @Value("${jwt.access.token.expired}")
    private int accessTokenLifeTime;
    @Value("${jwt.refresh.token.expired}")
    private int refreshTokenLifeTime;
    @Value("${jwt.sign.key}")
    private String secretKey;

    public String getAccessToken(String phone) {
        return generateToken(phone, accessTokenLifeTime);
    }

    public String getRefreshToken(String phone) {
        return generateToken(phone, refreshTokenLifeTime);
    }

    private String generateToken(String phone, int tokenLifeTime) {
        return Jwts.builder()
                .issuedAt(new Date())
                .signWith(getSignInKey())
                .claim("phone", phone)
                .expiration(new Date(System.currentTimeMillis() + tokenLifeTime))
                .issuer("Book Shop")
                .compact();
    }

    public String decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return (String) claims.get("phone");
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
