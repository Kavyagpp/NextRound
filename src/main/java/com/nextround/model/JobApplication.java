package com.nextround.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String company;

    @NotBlank
    private String role;

    @Enumerated(EnumType.STRING)
    private Status status = Status.APPLIED;

    private LocalDate appliedOn;
    private LocalDate followUpOn;
    private String notes;

    @PrePersist
    void setDefaults() {
        if (appliedOn == null) appliedOn = LocalDate.now();
        if (followUpOn == null) followUpOn = appliedOn.plusDays(7);
        if (status == null) status = Status.APPLIED;
    }

    public Long getId() { return id; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDate getAppliedOn() { return appliedOn; }
    public void setAppliedOn(LocalDate appliedOn) { this.appliedOn = appliedOn; }
    public LocalDate getFollowUpOn() { return followUpOn; }
    public void setFollowUpOn(LocalDate followUpOn) { this.followUpOn = followUpOn; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}