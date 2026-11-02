package com.nextround.controller;

import com.nextround.model.JobApplication;
import com.nextround.model.Status;
import com.nextround.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository repo;

    public JobApplicationController(JobApplicationRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<JobApplication> all(@RequestParam(required = false) Status status) {
        return status == null ? repo.findAll() : repo.findByStatus(status);
    }

    @PostMapping
    public JobApplication create(@Valid @RequestBody JobApplication app) {
        return repo.save(app);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> update(@PathVariable Long id,
                                                 @Valid @RequestBody JobApplication in) {
        return repo.findById(id).map(app -> {
            app.setCompany(in.getCompany());
            app.setRole(in.getRole());
            app.setStatus(in.getStatus());
            app.setFollowUpOn(in.getFollowUpOn());
            app.setNotes(in.getNotes());
            return ResponseEntity.ok(repo.save(app));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/due")
    public List<JobApplication> due() {
        return repo.findByStatusAndFollowUpOnLessThanEqual(Status.APPLIED, LocalDate.now());
    }

    @GetMapping("/stats")
    public Map<Status, Long> stats() {
        Map<Status, Long> result = new EnumMap<>(Status.class);
        for (Status s : Status.values()) result.put(s, 0L);
        repo.findAll().forEach(a -> result.merge(a.getStatus(), 1L, Long::sum));
        return result;
    }
}