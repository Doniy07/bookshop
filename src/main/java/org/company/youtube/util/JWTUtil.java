package org.company.youtube.util;

import io.jsonwebtoken.*;
import org.company.youtube.dto.auth.JwtDTO;
import org.company.youtube.enums.ProfileRole;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


public class JWTUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "very_long_mazgiskjdh2skjdhvery_long_mazgiskjdh2skjdhadasdasg7fgdfgdfdadasdasg7fgdfgdfd";

    // email
    public static String encode(String profileId, String email, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(secretKeySpec);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("email", email);
        jwtBuilder.claim("role", role);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.issuer("Youtube");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jws<Claims> jws = jwtParser.parseSignedClaims(token);
        Claims claims = jws.getPayload();

        String id = (String) claims.get("id");
        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, email, profileRole);
        }
        return new JwtDTO(id);
    }
}
