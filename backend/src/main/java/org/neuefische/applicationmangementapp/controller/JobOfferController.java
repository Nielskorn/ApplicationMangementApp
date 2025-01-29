package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.model.JobOfferDto;
import org.neuefische.applicationmangementapp.service.JobOfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joboffer")
public class JobOfferController {
    final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @GetMapping()
    public List<JobOffer> getAllJobOffers() {
        return jobOfferService.getAllJobOffers();
    }
    @GetMapping("/{id}")
    public JobOffer getJobOfferById(@PathVariable String id) throws NoSuchId {
        return jobOfferService.getJobOfferById(id);
    }
    @PostMapping()
    public JobOffer createJobOffer(@RequestBody JobOfferDto jobOffer) {
        return jobOfferService.createJobOffer(jobOffer);
    }
    @PutMapping("/{id}")
    public JobOffer updateJobOffer(@PathVariable String id, @RequestBody JobOffer jobOffer) throws NoSuchId {
        return jobOfferService.updateJobOffer(id,jobOffer);
    }
    @DeleteMapping("/{id}")
    public void deleteJobOffer(@PathVariable String id) {
    jobOfferService.deleteJobOffer(id);
    }
}
