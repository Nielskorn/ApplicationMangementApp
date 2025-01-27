package org.neuefische.applicationmangementapp.repo;

import org.neuefische.applicationmangementapp.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepo extends MongoRepository<Application, String> {
}
