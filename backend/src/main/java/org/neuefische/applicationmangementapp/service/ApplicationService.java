package org.neuefische.applicationmangementapp.service;

import org.neuefische.applicationmangementapp.execaptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;

import org.neuefische.applicationmangementapp.model.ApplicationDtoForCreated;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForEdit;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepo applicationRepo;

    public ApplicationService(ApplicationRepo applicationRepo) {
        this.applicationRepo = applicationRepo;
    }

    public Application addApplication(ApplicationDtoForCreated applicationDto) {
        Application application = new Application(IdService.getId(), applicationDto.jobOfferID(), applicationDto.resume(), applicationDto.coverLetter(), appliStatus.OPEN, applicationDto.reminderTime(), LocalDate.now());
        return applicationRepo.save(application);
    }

    public Application updateApplication(String id, ApplicationDtoForEdit applicationDto) throws NoSuchId {

        Optional<Application> oApplication = applicationRepo.findById(id);

        if (oApplication.isEmpty()) {
            throw new NoSuchId("no such id: " + id);
        } else {
            Application application = new Application(id, applicationDto.jobOfferID(), applicationDto.resume(), applicationDto.coverLetter(), applicationDto.appliStatus(), applicationDto.reminderTime(), oApplication.get().dateOfCreation());
            return applicationRepo.save(application);
        }

    }

    public void deleteApplicationById(String id) {
        applicationRepo.deleteById(id);
    }

    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    public Application getApplicationById(String id) throws NoSuchId {
        Optional<Application> oApplication = applicationRepo.findById(id);
        if (oApplication.isEmpty()) {
            throw new NoSuchId("no such id: " + id);
        } else {
            return oApplication.get();
        }
    }
}
