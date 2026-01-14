package com.example.complaint.service;

import com.example.complaint.dto.ComplaintRequest;
import com.example.complaint.dto.ComplaintResponse;
import com.example.complaint.dto.StatusUpdateRequest;
import com.example.complaint.entity.Complaint;
import com.example.complaint.entity.User;
import com.example.complaint.enums.Category;
import com.example.complaint.enums.Status;
import com.example.complaint.exception.ComplaintNotFoundException;
import com.example.complaint.repository.ComplaintRepository;
import com.example.complaint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplaintService {
    
    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public ComplaintResponse createComplaint(ComplaintRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Complaint complaint = new Complaint();
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setCategory(request.getCategory());
        complaint.setCreatedBy(user);
        
        Complaint savedComplaint = complaintRepository.save(complaint);
        
        // Create notification
        notificationService.createNotification(
            "Your complaint '" + savedComplaint.getTitle() + "' has been submitted successfully",
            user
        );
        
        return convertToResponse(savedComplaint);
    }
    
    public List<ComplaintResponse> getMyComplaints(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Complaint> complaints = complaintRepository.findByCreatedByOrderByCreatedAtDesc(user);
        
        List<ComplaintResponse> responses = new ArrayList<>();
        for (Complaint complaint : complaints) {
            responses.add(convertToResponse(complaint));
        }
        
        return responses;
    }
    
    public List<ComplaintResponse> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAllByOrderByCreatedAtDesc();
        
        List<ComplaintResponse> responses = new ArrayList<>();
        for (Complaint complaint : complaints) {
            responses.add(convertToResponse(complaint));
        }
        
        return responses;
    }
    
    public ComplaintResponse updateComplaintStatus(Long id, StatusUpdateRequest request) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
        
        complaint.setStatus(request.getStatus());
        Complaint updatedComplaint = complaintRepository.save(complaint);
        
        // Create notification (Can see by notificatin endpoint)
        notificationService.createNotification(
            "Your complaint '" + complaint.getTitle() + "' status has been updated to " + request.getStatus(),
            complaint.getCreatedBy()
        );
        
        return convertToResponse(updatedComplaint);
    }
    
    public List<ComplaintResponse> searchComplaints(Status status, Category category) {
        List<Complaint> complaints;
        
        if (status != null && category != null) {
            complaints = complaintRepository.findByStatusAndCategoryOrderByCreatedAtDesc(status, category);
        } else if (status != null) {
            complaints = complaintRepository.findByStatusOrderByCreatedAtDesc(status);
        } else if (category != null) {
            complaints = complaintRepository.findByCategoryOrderByCreatedAtDesc(category);
        } else {
            complaints = complaintRepository.findAllByOrderByCreatedAtDesc();
        }
        
        List<ComplaintResponse> responses = new ArrayList<>();
        for (Complaint complaint : complaints) {
            responses.add(convertToResponse(complaint));
        }
        
        return responses;
    }
    
    private ComplaintResponse convertToResponse(Complaint complaint) {
        ComplaintResponse response = new ComplaintResponse();
        response.setId(complaint.getId());
        response.setTitle(complaint.getTitle());
        response.setDescription(complaint.getDescription());
        response.setCategory(complaint.getCategory());
        response.setStatus(complaint.getStatus());
        response.setCreatedAt(complaint.getCreatedAt());
        response.setCreatedBy(complaint.getCreatedBy().getUsername());
        return response;
    }
}
