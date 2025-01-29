package org.neuefische.applicationmangementapp.service;

import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.JobOffer;
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

    public JobOffer createJobOffer(JobOffer jobOffer) {
        return jobOfferRepo.save(jobOffer);
    }

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepo.findAll();
    }

    public JobOffer getJobOfferById(String id) throws NoSuchId {
        Optional<JobOffer> jobOffer = jobOfferRepo.findById(id);
        if(jobOffer.isPresent()){
         return    jobOffer.get();
        }
        else throw new NoSuchId("no such id"+id);
    }
    public JobOffer updateJobOffer(String id ,JobOffer jobOffer) throws NoSuchId {
        if(jobOfferRepo.existsById(id)){
            return jobOfferRepo.save(jobOffer);
        }
        else{
            throw new NoSuchId("no such id"+id);
        }
    }

    public void deleteJobOffer(String id) {
        jobOfferRepo.deleteById(id);
    }

}
