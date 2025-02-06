package org.neuefische.applicationmangementapp.repo;

import org.neuefische.applicationmangementapp.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepo extends MongoRepository<JobOffer, String> {
}
