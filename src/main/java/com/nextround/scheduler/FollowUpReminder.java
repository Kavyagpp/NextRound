package com.nextround.scheduler;

import com.nextround.model.JobApplication;
import com.nextround.model.Status;
import com.nextround.repository.JobApplicationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class FollowUpReminder {

    private final JobApplicationRepository repo;

    public FollowUpReminder(JobApplicationRepository repo) {
        this.repo = repo;
    }

    // Every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void remind() {
        List<JobApplication> due =
            repo.findByStatusAndFollowUpOnLessThanEqual(Status.APPLIED, LocalDate.now());
        for (JobApplication a : due) {
            System.out.println("REMINDER: follow up with " + a.getCompany() + " for " + a.getRole());
        }
    }
}