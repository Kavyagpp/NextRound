package com.nextround.repository;

import com.nextround.model.JobApplication;
import com.nextround.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByStatus(Status status);

    List<JobApplication> findByStatusAndFollowUpOnLessThanEqual(Status status, LocalDate date);
}