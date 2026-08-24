package com.hiring.hiring_management_system.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hiring.hiring_management_system.entity.Job;
import com.hiring.hiring_management_system.service.JobService;

@Controller
public class AdminJobController {

    private final JobService jobService;

    public AdminJobController(JobService jobService) {
        this.jobService = jobService;
    }

    // View all jobs
    @GetMapping("/admin/jobs")
    public String getJobs(Model model) {

        List<Job> jobs = jobService.getAllJobs();

        model.addAttribute("jobs", jobs);

        return "admin-jobs";
    }

    // Show Add Job form
    @GetMapping("/admin/jobs/new")
    public String showJobForm(Model model) {

        model.addAttribute("job", new Job());

        return "job-form";
    }

    // Save new job
    @PostMapping("/admin/jobs/save")
    public String saveJob(@ModelAttribute Job job) {

        jobService.saveJob(job);

        return "redirect:/admin/jobs";
    }

    // Show Edit Job form
    @GetMapping("/admin/jobs/edit/{id}")
    public String editJob(
            @PathVariable Long id,
            Model model) {

        Job job = jobService.getJobById(id);

        if (job == null) {
            return "redirect:/admin/jobs";
        }

        model.addAttribute("job", job);

        return "job-form";
    }

    // Delete job
    @GetMapping("/admin/jobs/delete/{id}")
    public String deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return "redirect:/admin/jobs";
    }
}