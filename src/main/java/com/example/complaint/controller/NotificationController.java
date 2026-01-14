package com.example.complaint.controller;

import com.example.complaint.dto.NotificationResponse;
import com.example.complaint.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(Authentication authentication) {
        String username = authentication.getName();
        List<NotificationResponse> notifications = notificationService.getMyNotifications(username);
        return ResponseEntity.ok(notifications);
    }
}
