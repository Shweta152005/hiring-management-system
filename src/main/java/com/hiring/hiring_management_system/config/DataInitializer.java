package com.hiring.hiring_management_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hiring.hiring_management_system.entity.Job;
import com.hiring.hiring_management_system.repository.JobRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JobRepository jobRepository;

    public DataInitializer(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {

        // Only create jobs if the database is empty
        if (jobRepository.count() == 0) {

            jobRepository.save(new Job(
                    "Java Developer",
                    "Develop and maintain Java applications using Spring Boot."
            ));

            jobRepository.save(new Job(
                    "Frontend Developer",
                    "Build responsive web applications using HTML, CSS and JavaScript."
            ));

            jobRepository.save(new Job(
                    "Backend Developer",
                    "Develop REST APIs and backend services."
            ));

            jobRepository.save(new Job(
                    "Full Stack Developer",
                    "Work on both frontend and backend application development."
            ));

            jobRepository.save(new Job(
                    "Software Engineer",
                    "Design, develop and maintain software applications."
            ));

            jobRepository.save(new Job(
                    "Python Developer",
                    "Develop backend applications using Python and Flask or Django."
            ));

            jobRepository.save(new Job(
                    "Angular Developer",
                    "Develop modern web applications using Angular."
            ));

            jobRepository.save(new Job(
                    "Spring Boot Developer",
                    "Build scalable REST APIs and enterprise applications."
            ));

            jobRepository.save(new Job(
                    "QA Engineer",
                    "Test applications and identify software defects."
            ));

            jobRepository.save(new Job(
                    "DevOps Engineer",
                    "Manage deployment, CI/CD pipelines and cloud infrastructure."
            ));

            System.out.println("10 sample jobs created successfully.");

        } else {

            System.out.println("Jobs already exist. Skipping initialization.");

        }
    }
}