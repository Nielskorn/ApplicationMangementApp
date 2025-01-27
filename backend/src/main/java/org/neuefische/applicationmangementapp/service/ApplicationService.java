package org.neuefische.applicationmangementapp.service;

import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplicationService {

    final private ApplicationRepo applicationRepo;

    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public Application addApplication(Application application) {
     return    applicationRepo.save(application);
    }

    public Application updateApplication(Application application) {
     return    applicationRepo.save(application);
    }

    public void deleteApplicationById(String id) {
    applicationRepo.deleteById(id);
    }

    public List<Application> getAllApplications() {
    return     applicationRepo.findAll();
    }

    public Application getApplicationById(String id) {
      return   applicationRepo.findById(id).orElseThrow();
    }
}
