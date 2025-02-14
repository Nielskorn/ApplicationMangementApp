package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.JobApplicationTracker;
import org.neuefische.applicationmangementapp.service.JobApplicationTrackerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/JobApplication")
public class JobApplicationTrackerController {
    final JobApplicationTrackerService jobApplicationTrackerService;

    public JobApplicationTrackerController(JobApplicationTrackerService jobApplicationTrackerService) {
        this.jobApplicationTrackerService = jobApplicationTrackerService;
    }

    @GetMapping
    public List<JobApplicationTracker> getAllJobApplicationsWithAOffer() {
        return jobApplicationTrackerService.getJobApplications();
    }

    @GetMapping("all")
    public List<JobApplicationTracker> getAllJobApplications() {
        return jobApplicationTrackerService.getAllJobApplications();
    }

    @GetMapping("/{id}")
    public JobApplicationTracker getJobApplication(@PathVariable String id) throws NoSuchId {
        return jobApplicationTrackerService.getJobApplicationByApplicationId(id);
    }

}
