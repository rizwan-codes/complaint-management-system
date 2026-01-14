package com.example.complaint.exception;

public class ComplaintNotFoundException extends RuntimeException {
    
    public ComplaintNotFoundException(String message) {
        super(message);
    }
    
    public ComplaintNotFoundException(Long id) {
        super("Complaint not found with id: " + id);
    }
}
