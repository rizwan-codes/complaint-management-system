package com.example.complaint.dto;

import com.example.complaint.enums.Status;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {
    
    @NotNull(message = "Status is required")
    private Status status;
    
    public StatusUpdateRequest() {
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}
