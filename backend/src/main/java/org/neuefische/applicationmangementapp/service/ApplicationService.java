package org.neuefische.applicationmangementapp.service;


import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;

import org.neuefische.applicationmangementapp.model.ApplicationDtoForCreated;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForEdit;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApplicationService {

    private final  ApplicationRepo applicationRepo;

    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public Application addApplication(ApplicationDtoForCreated applicationDto) {
        Application application=new Application(IdService.getId(),applicationDto.jobTitle(),applicationDto.jobDescription(),applicationDto.resume(),applicationDto.coverLetter(), appliStatus.OPEN);
     return    applicationRepo.save(application);
    }

    public Application updateApplication(String id, ApplicationDtoForEdit applicationDto) throws NoSuchId {
        if (applicationRepo.findById(id).isEmpty()){
            throw new NoSuchId("no such id: "+id );
        }else{
            Application application=new Application(id,applicationDto.jobTitle(),applicationDto.jobDescription(),applicationDto.resume(),applicationDto.coverLetter(), applicationDto.appliStatus());
            return    applicationRepo.save(application);
        }

    }

    public void deleteApplicationById(String id) {
    applicationRepo.deleteById(id);
    }

    public List<Application> getAllApplications() {
    return     applicationRepo.findAll();
    }

    public Application getApplicationById(String id) throws NoSuchId {
      if (applicationRepo.findById(id).isEmpty()){
          throw new NoSuchId("no such id: "+id);
      }else{
        return  applicationRepo.findById(id).get();}
    }
}
