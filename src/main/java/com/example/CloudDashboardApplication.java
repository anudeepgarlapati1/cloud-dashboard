package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudDashboardApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("☁️  Multi-Cloud Dashboard Started!");
        System.out.println("📍 API: http://localhost:8080/api/clouds");
        System.out.println("📊 Analytics: http://localhost:8080/api/clouds/analytics");
        System.out.println("========================================\n");
    }
}