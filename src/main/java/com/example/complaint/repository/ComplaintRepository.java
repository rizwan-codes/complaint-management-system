package com.example.complaint.repository;

import com.example.complaint.entity.Complaint;
import com.example.complaint.entity.User;
import com.example.complaint.enums.Category;
import com.example.complaint.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    
    List<Complaint> findByCreatedByOrderByCreatedAtDesc(User user);
    
    List<Complaint> findAllByOrderByCreatedAtDesc();
    
    List<Complaint> findByStatusOrderByCreatedAtDesc(Status status);
    
    List<Complaint> findByCategoryOrderByCreatedAtDesc(Category category);
    
    List<Complaint> findByStatusAndCategoryOrderByCreatedAtDesc(Status status, Category category);
}
