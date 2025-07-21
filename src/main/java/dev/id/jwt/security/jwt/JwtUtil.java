package dev.id.jwt.security.jwt;


import dev.id.jwt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final String jwtSecret = "mySecretKeymySecretKeymySecretKeymySecretKey"; // Use at least 256 bits
    private final long jwtExpirationMs = 86400000; // 1 day
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()))
                .claim("permissions", user.getRoles().stream()
                    .flatMap(r -> r.getPermissions().stream())
                    .map(p -> p.getName()).distinct().collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Refresh token methods
    public String generateRefreshToken(String username, String refreshSecret) {
        Key refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        long refreshExpirationMs = 7 * 24 * 60 * 60 * 1000; // 7 days
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefreshToken(String token, String refreshSecret) {
        try {
            Key refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
            Jwts.parser().setSigningKey(refreshKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromRefreshToken(String token, String refreshSecret) {
        Key refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes());
        return Jwts.parser().setSigningKey(refreshKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
