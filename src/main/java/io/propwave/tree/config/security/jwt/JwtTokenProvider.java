package io.propwave.tree.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.propwave.tree.config.security.model.JwtToken;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String BEARER_TOKEN = "Bearer";

    @Value("${jwt.accessToken.expired}")
    private String ACCESS_TOKEN_EXPIRED_TIME;

    @Value("${jwt.refreshToken.expired}")
    private String REFRESH_TOKEN_EXPIRED_TIME;

    private final Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret.key}") String jwtSecretKey
    ) {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    public JwtToken generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authority", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = pareseClaims(accessToken);

        if (claims.get("authority") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("Authority").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principle = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principle, "", authorities);
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
}
