package com.svenson95.track_e_backend.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${JWT_SECRET}")
  private String secret;

  private Key secretKey;

  @PostConstruct
  public void init() {

    if (secret == null || secret.isBlank()) {
      throw new IllegalStateException("JWT_SECRET environment variable not set!");
    }
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(Map<String, Object> userInfo) {
    String subject = userInfo.get("email").toString();

    long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
        .setClaims(userInfo)
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(this.secretKey)
        .compact();
  }

  public Map<String, Object> validateToken(String token) throws TokenExpiredException {
    try {
      Claims claims =
          Jwts.parserBuilder()
              .setSigningKey(this.secretKey)
              .build()
              .parseClaimsJws(token)
              .getBody();

      return claims;
    } catch (ExpiredJwtException ex) {
      throw new TokenExpiredException("JWT expired", ex);
    } catch (MalformedJwtException
        | UnsupportedJwtException
        | SignatureException
        | IllegalArgumentException ex) {
      throw new RuntimeException("Invalid JWT", ex);
    }
  }

  public static class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
