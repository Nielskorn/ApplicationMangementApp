package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.JobApplicationTracker;
import org.neuefische.applicationmangementapp.service.JobApplicationTrackerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/JobApplication")
public class JobApplicationTrackerController {
    final JobApplicationTrackerService jobApplicationTrackerService;

    public JobApplicationTrackerController(JobApplicationTrackerService jobApplicationTrackerService) {
        this.jobApplicationTrackerService = jobApplicationTrackerService;
    }
    @GetMapping
    //returns All jobApplicationsWithA Offer
    public List<JobApplicationTracker> getJobApplications(){
      return  jobApplicationTrackerService.getJobApplications();
    }
    @GetMapping("/all")
    public List<JobApplicationTracker> getAllJobApplications(){
        return  jobApplicationTrackerService.getAllJobApplications();
    }
    @GetMapping("/{id}")
    public JobApplicationTracker getJobApplication(@PathVariable String id) throws NoSuchId {
        return jobApplicationTrackerService.getJobApplicationByApplicationId(id);
    }

}
