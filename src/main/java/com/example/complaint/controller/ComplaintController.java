package com.example.complaint.controller;

import com.example.complaint.dto.ComplaintRequest;
import com.example.complaint.dto.ComplaintResponse;
import com.example.complaint.dto.StatusUpdateRequest;
import com.example.complaint.enums.Category;
import com.example.complaint.enums.Status;
import com.example.complaint.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {
    
    @Autowired
    private ComplaintService complaintService;
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ComplaintResponse> createComplaint(
            @Valid @RequestBody ComplaintRequest request,
            Authentication authentication) {
        
        String username = authentication.getName();
        ComplaintResponse response = complaintService.createComplaint(request, username);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(Authentication authentication) {
        String username = authentication.getName();
        List<ComplaintResponse> complaints = complaintService.getMyComplaints(username);
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<ComplaintResponse> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ComplaintResponse> updateComplaintStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request) {
        
        ComplaintResponse response = complaintService.updateComplaintStatus(id, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ComplaintResponse>> searchComplaints(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Category category) {
        
        List<ComplaintResponse> complaints = complaintService.searchComplaints(status, category);
        return ResponseEntity.ok(complaints);
    }
}
