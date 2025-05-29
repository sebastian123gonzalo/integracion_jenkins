package com.upc.controllers;

import com.upc.models.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            return ResponseEntity.ok(Map.of("token", "abc123"));
        } else {
            ProblemDetail error = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            error.setTitle("Unauthorized");
            error.setDetail("Credenciales incorrectas");
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
            //error.setProperty("path","/api/login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.APPLICATION_JSON) // 🔹 Fuerza JSON
                    .body(error);
        }
    }
}
