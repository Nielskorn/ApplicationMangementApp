package org.neuefische.applicationmangementapp.service;


import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.JobApplicationTracker;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationTrackerService {
    final JobOfferService jobOfferService;
    final ApplicationService applicationService;

    public JobApplicationTrackerService(JobOfferService jobOfferService, ApplicationService applicationService) {
        this.jobOfferService = jobOfferService;
        this.applicationService = applicationService;
    }

    public List<JobApplicationTracker> getJobApplications() {
        return getApplications(false);

    }

    public List<JobApplicationTracker> getAllJobApplications() {
        return getApplications(true);
    }

    public List<JobApplicationTracker> getApplications(boolean getAll) {
        List<JobApplicationTracker> jobApplicationTrackers = new ArrayList<>();
        List<Application> appli = applicationService.getAllApplications();
        for (Application app : appli) {
            Optional<JobOffer> optionalJobOffer = jobOfferService.getOJobOfferById(app.jobOfferID());
            if (optionalJobOffer.isPresent()) {
                JobOffer jobOffer = optionalJobOffer.get();
                jobApplicationTrackers.add(new JobApplicationTracker(jobOffer, app));
            } else if (getAll) {
                jobApplicationTrackers.add(new JobApplicationTracker(null, app));
            }

        }
        return jobApplicationTrackers;
    }

    public JobApplicationTracker getJobApplicationByApplicationId(String id) throws NoSuchId {
        Optional<Application> app = applicationService.getOptionalApplicationById(id);
        if (app.isPresent()) {
            Optional<JobOffer> optionalJobOffer = jobOfferService.getOJobOfferById(app.get().jobOfferID());
            if (optionalJobOffer.isPresent()) {
                JobOffer x = optionalJobOffer.get();
                return new JobApplicationTracker(x, app.get());
            } else {
                return new JobApplicationTracker(null, app.get());
            }
        } else throw new NoSuchId(id + "By Application");

    }

}
