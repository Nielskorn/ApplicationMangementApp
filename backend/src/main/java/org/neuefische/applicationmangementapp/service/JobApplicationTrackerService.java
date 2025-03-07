package org.neuefische.applicationmangementapp.service;


import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.JobApplicationTracker;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationTrackerService {
    final JobOfferService jobOfferService;
    final ApplicationService applicationService;
    final NoteService noteService;

    public JobApplicationTrackerService(JobOfferService jobOfferService, ApplicationService applicationService, NoteService noteService) {
        this.jobOfferService = jobOfferService;
        this.applicationService = applicationService;
        this.noteService = noteService;
    }

    public List<JobApplicationTracker> getJobApplications() {
        return getApplications(false);

    }

    public List<JobApplicationTracker> getAllJobApplications() {
        return getApplications(true);
    }

    public List<JobApplicationTracker> getApplications(boolean getAll) {
        List<JobApplicationTracker> jobApplicationTrackers = new ArrayList<>();

        List<Application> applications = applicationService.getAllApplications();
        for (Application app : applications) {
            Optional<JobOffer> optionalJobOffer = jobOfferService.getOJobOfferById(app.jobOfferID());
            JobOffer jobOffer = optionalJobOffer.orElse(null);
            JobApplicationTracker tracker;
            List<Note> noteList = noteService.getNotesByApplicationId(app.id());

            if (!noteList.isEmpty()) {
                tracker = new JobApplicationTracker(jobOffer, app, noteList);
            } else {
                tracker = new JobApplicationTracker(jobOffer, app, null);
            }
            if (getAll || jobOffer != null) {
                jobApplicationTrackers.add(tracker);
            }
        }

        return jobApplicationTrackers;
    }

    public JobApplicationTracker getJobApplicationByApplicationId(String id) throws NoSuchId {
        Optional<Application> app = applicationService.getOptionalApplicationById(id);
        if (app.isPresent()) {
            JobOffer jobOffer = null;
            List<Note> noteList = noteService.getNotesByApplicationId(id);
            Optional<JobOffer> optionalJobOffer = jobOfferService.getOJobOfferById(app.get().jobOfferID());
            if (optionalJobOffer.isPresent()) {
                jobOffer = optionalJobOffer.get();
            }

            return new JobApplicationTracker(jobOffer, app.get(), noteList);

        } else throw new NoSuchId(id + "By Application");

    }

    public List<JobApplicationTracker> get6JobApplicationsWithNextDate() {
        List<JobApplicationTracker> allApplications = getApplications(true);
        allApplications.sort(Comparator.comparing(
                jobApplicationTracker -> jobApplicationTracker.application()
                        .reminderTime(), Comparator.nullsLast(Comparator.naturalOrder())));

        return allApplications.size() > 6 ? allApplications.subList(0, 6) : allApplications;

    }


}
