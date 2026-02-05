package com.jwt.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        System.out.println("[STARTUP] JWT Auth Backend starting...");
        SpringApplication.run(AuthApplication.class, args);
        System.out.println("[STARTUP] Backend is running on http://localhost:8080");
        System.out.println("[INFO] API endpoints: POST /api/auth/signup, POST /api/auth/login, GET /api/auth/me");
    }
}
