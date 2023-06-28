package io.propwave.tree.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.propwave.tree.auth.domain.RefreshToken;
import io.propwave.tree.auth.domain.Role;
import io.propwave.tree.auth.infrastructure.RefreshTokenRepository;
import io.propwave.tree.config.security.CustomUserDetailsService;
import io.propwave.tree.config.security.model.JwtToken;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_TOKEN = "Bearer";

    @Value("${jwt.accessToken.expired}")
    private String ACCESS_TOKEN_EXPIRED_TIME;

    @Value("${jwt.refreshToken.expired}")
    private String REFRESH_TOKEN_EXPIRED_TIME;

    private final Key key;

    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtTokenProvider(
            @Value("${jwt.secret.key}") String jwtSecretKey,
            CustomUserDetailsService customUserDetailsService,
            RefreshTokenRepository refreshTokenRepository
    ) {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
        this.userDetailsService = customUserDetailsService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public JwtToken generateToken(String email, Role role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("authority", role.getKey());

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(ACCESS_TOKEN_EXPIRED_TIME)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(REFRESH_TOKEN_EXPIRED_TIME)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        saveRefreshToken(refreshToken, email);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(pareseClaims(accessToken).getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return true;
    }

    // 토큰 복호화
    private Claims pareseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException error) {
            return error.getClaims();
        }
    }

    private void saveRefreshToken(String refreshToken, String email) {
        refreshTokenRepository.save(
                RefreshToken.newInstance(
                        refreshToken,
                        email
                )
        );
    }
}
