package org.neuefische.applicationmangementapp.service;


import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.model.JobOfferDto;
import org.neuefische.applicationmangementapp.repo.JobOfferRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobOfferService {
    final JobOfferRepo jobOfferRepo;


    public JobOfferService(JobOfferRepo jobOfferRepo) {
        this.jobOfferRepo = jobOfferRepo;
    }

    public JobOffer createJobOffer(JobOfferDto jobOfferDto) {
        JobOffer jobOffer = new JobOffer(IdService.getId(), jobOfferDto.Url_companyLogo(),
                jobOfferDto.companyName(), jobOfferDto.location(), jobOfferDto.jobTitle(),
                jobOfferDto.jobDescription(), jobOfferDto.LinkJobAd());
        return jobOfferRepo.save(jobOffer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepo.findAll();
    }

    public JobOffer getJobOfferById(String id) throws NoSuchId {
        return jobOfferRepo.findById(id).orElseThrow(() -> new NoSuchId(id));

    }

    public Optional<JobOffer> getOJobOfferById(String id) {
        return jobOfferRepo.findById(id);
    }

    public JobOffer updateJobOffer(String id, JobOfferDto jobOfferDto) throws NoSuchId {
        jobOfferRepo.findById(id).orElseThrow(() -> new NoSuchId(id));
        JobOffer jobOffer = new JobOffer(id, jobOfferDto.Url_companyLogo(), jobOfferDto.companyName(), jobOfferDto.location(), jobOfferDto.jobTitle(), jobOfferDto.jobDescription(), jobOfferDto.LinkJobAd());
        return jobOfferRepo.save(jobOffer);

    }

    public void deleteJobOffer(String id) {
        jobOfferRepo.deleteById(id);
    }

}
