package com.hiring.hiring_management_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hiring.hiring_management_system.entity.Candidate;
import com.hiring.hiring_management_system.service.CandidateService;
import com.hiring.hiring_management_system.service.JobService;

@Controller
public class AdminCandidateController {

    private final CandidateService candidateService;
    private final JobService jobService;

    public AdminCandidateController(
            CandidateService candidateService,
            JobService jobService) {

        this.candidateService = candidateService;
        this.jobService = jobService;
    }

    // View all candidates
    @GetMapping("/admin/candidates")
    public String getCandidates(
            @RequestParam(required = false) Long jobId,
            @RequestParam(required = false) String status,
            Model model) {

        List<Candidate> candidates =
                candidateService.getAllCandidates();

        // Filter by Job
        if (jobId != null) {

            candidates = candidates.stream()
                    .filter(candidate ->
                            candidate.getJob() != null &&
                            candidate.getJob().getId().equals(jobId))
                    .collect(Collectors.toList());
        }

        // Filter by Status
        if (status != null &&
                !status.isBlank() &&
                !status.equals("ALL")) {

            candidates = candidates.stream()
                    .filter(candidate ->
                            status.equals(candidate.getStatus()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("candidates", candidates);

        // Jobs for Job filter dropdown
        model.addAttribute("jobs", jobService.getAllJobs());

        // Keep selected filter values
        model.addAttribute("selectedJobId", jobId);
        model.addAttribute("selectedStatus", status);

        return "admin-candidates";
    }

    // Update candidate hiring stage
    @PostMapping("/admin/candidates/status")
    public String updateStatus(
            @RequestParam Long candidateId,
            @RequestParam String status) {

        Candidate candidate =
                candidateService.getCandidateById(candidateId);

        if (candidate != null) {

            candidate.setStatus(status);

            candidateService.saveCandidate(candidate);
        }

        return "redirect:/admin/candidates";
    }
}