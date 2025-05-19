package com.aesopwow.subsubclipclop.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final RedisTemplate<String, String> redisTemplate;
    private static final long ACCESS_TOKEN_EXP = 1000L * 60L * 15L; // 15분
    private static final long REFRESH_TOKEN_EXP = 1000L * 60L * 60L * 24; // 1일


    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret,
                            RedisTemplate<String, String> redisTemplate) {
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.redisTemplate = redisTemplate;
    }

    private String createToken(Map<String, String> claims, long tokenExp) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .claims(claims)
                .id(Long.toHexString(System.nanoTime()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExp))
                .signWith(secretKey)
                .compact();
    }

    public String createAccessToken(String email, String role) {
        Map<String, String> claims = new HashMap<>();

        claims.put("email", email);
        claims.put("role", role);

        return createToken(claims, ACCESS_TOKEN_EXP);
    }

    public String createRefreshToken(String email) {
        Map<String, String> claims = Map.of("email", email);
        String refreshToken = createToken(claims, REFRESH_TOKEN_EXP);

        redisTemplate.opsForValue().set("refresh:" + email,
                refreshToken, REFRESH_TOKEN_EXP, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    public String getEmail(String token) {
        return getClaims(token).get("email").toString();
    }

    public boolean validateToken(String token) {
        if (isBlacklisted(token)) {
            return false;
        }
        return !getClaims(token).getExpiration().before(new Date());
    }
    private boolean isBlacklisted(String token) {
        String key = "blacklist:" + getJti(token);

        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void addBlacklist(String accessToken) {
        String key = "blacklist:" + getJti(accessToken);

        redisTemplate.opsForValue().set(key, "true", ACCESS_TOKEN_EXP, TimeUnit.MILLISECONDS);
    }

    private String getJti(String token) {

        return getClaims(token).getId();
    }

    private Claims getClaims(String token) {
        // 토큰이 만료되면 parseSignedClaims() 단계에서 ExpiredJwtException이 발생한다.
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public void deleteRefreshToken(String accessToken) {
        String email = getEmail(accessToken);

        redisTemplate.delete("refresh:" + email);
    }
}
