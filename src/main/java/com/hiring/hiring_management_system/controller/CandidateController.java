package com.hiring.hiring_management_system.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hiring.hiring_management_system.entity.Candidate;
import com.hiring.hiring_management_system.entity.Job;
import com.hiring.hiring_management_system.service.CandidateService;
import com.hiring.hiring_management_system.service.JobService;

@Controller
public class CandidateController {

    private final CandidateService candidateService;
    private final JobService jobService;

    public CandidateController(CandidateService candidateService,
                                JobService jobService) {

        this.candidateService = candidateService;
        this.jobService = jobService;
    }

    // Home page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // View all candidates
    @GetMapping("/candidates")
    public String getCandidates(Model model) {

        List<Candidate> candidates =
                candidateService.getAllCandidates();

        model.addAttribute("candidates", candidates);

        return "candidates";
    }

    // Show candidate application form
    @GetMapping("/candidates/new")
    public String showCandidateForm(Model model) {

        model.addAttribute("candidate", new Candidate());

        // Send all jobs to the dropdown
        model.addAttribute("jobs", jobService.getAllJobs());

        return "candidate-form";
    }

    // Save candidate application
    @PostMapping("/candidates/save")
    public String saveCandidate(
            @ModelAttribute Candidate candidate,
            @RequestParam("resumeFile") MultipartFile resumeFile,
            @RequestParam("job") Long jobId)
            throws IOException {

        // Get selected job
        Job job = jobService.getJobById(jobId);

        if (job == null) {
            return "redirect:/candidates/new";
        }

        // Attach selected job
        candidate.setJob(job);

        // Initial status
        candidate.setStatus("Applied");

        // Create uploads folder
        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save resume
        if (!resumeFile.isEmpty()) {

            String originalFileName =
                    resumeFile.getOriginalFilename();

            String fileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    resumeFile.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            candidate.setResumeUrl("/uploads/" + fileName);
        }

        // Save candidate
        candidateService.saveCandidate(candidate);

        return "redirect:/application-success";
    }

    // Application success page
    @GetMapping("/application-success")
    public String applicationSuccess() {
        return "application-success";
    }

    // Delete candidate
    @GetMapping("/candidates/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {

        candidateService.deleteCandidate(id);

        return "redirect:/candidates";
    }
}