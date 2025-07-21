package dev.id.jwt.controller;

import dev.id.jwt.entity.User;
import dev.id.jwt.repository.UserRepository;
import dev.id.jwt.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private final String REFRESH_SECRET = "refreshSecretKeyrefreshSecretKeyrefreshSecretKey";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String salt = user.getPasswordSalt();
            String hash = hashPassword(password, salt);
            if (user.getPasswordHash().equals(hash)) {
                String accessToken = jwtUtil.generateToken(user);
                String refreshToken = jwtUtil.generateRefreshToken(username, REFRESH_SECRET);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                return ResponseEntity.ok(tokens);
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    private String hashPassword(String password, String salt) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (jwtUtil.validateRefreshToken(refreshToken, REFRESH_SECRET)) {
            String username = jwtUtil.getUsernameFromRefreshToken(refreshToken, REFRESH_SECRET);
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String newAccessToken = jwtUtil.generateToken(user);
                Map<String, String> token = new HashMap<>();
                token.put("accessToken", newAccessToken);
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(401).body("Invalid refresh token");
    }

}
