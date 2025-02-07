package org.neuefische.applicationmangementapp.service;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForCreated;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForEdit;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepo applicationRepo;
    private final CloudinaryService cloudinaryService;

    public ApplicationService(ApplicationRepo applicationRepo, CloudinaryService cloudinaryService) {
        this.applicationRepo = applicationRepo;
        this.cloudinaryService = cloudinaryService;
    }

    public Application addApplication(ApplicationDtoForCreated applicationDto, MultipartFile resume, MultipartFile coverLetter) throws IOException {
        String cloudinaryResumeUrl = cloudinaryService.uploadFile(resume);
        Application application;
        if (coverLetter != null) {
            String cloudinaryCoverletterUrl = cloudinaryService.uploadFile(coverLetter);
            application = new Application(IdService.getId(), applicationDto.jobOfferID(), cloudinaryResumeUrl, cloudinaryCoverletterUrl, appliStatus.OPEN, applicationDto.reminderTime(), LocalDate.now());
        } else {
            application = new Application(IdService.getId(), applicationDto.jobOfferID(), cloudinaryResumeUrl, null, appliStatus.OPEN, applicationDto.reminderTime(), LocalDate.now());
        }

        return applicationRepo.save(application);
    }

    public Application updateApplication(String id, ApplicationDtoForEdit applicationDto) throws NoSuchId {
        Application oApplication = applicationRepo.findById(id).orElseThrow(() -> new NoSuchId(id));
        Application application = new Application(id, applicationDto.jobOfferID(), applicationDto.resume(), applicationDto.coverLetter(), applicationDto.appliStatus(), applicationDto.reminderTime(), oApplication.dateOfCreation());
        return applicationRepo.save(application);
    }

    public void deleteApplicationById(String id) throws NoSuchId, IOException {


        Application application = applicationRepo.findById(id).orElseThrow(() -> new NoSuchId(id));

        if (multipyApplicationExistsIfTnisResume(application.resume())) {
            applicationRepo.deleteById(id);
        } else {
            applicationRepo.deleteById(id);
            cloudinaryService.deleteFile(application.resume());
        }

    }

    public List<Application> getAllApplications() {
        return applicationRepo.findAll();
    }

    public Application getApplicationById(String id) throws NoSuchId {
        return applicationRepo.findById(id).orElseThrow(() -> new NoSuchId(id));

    }

    public Optional<Application> getOptionalApplicationById(String id) {
        return applicationRepo.findById(id);
    }

    public boolean multipyApplicationExistsIfTnisResume(String resumeId) {
        List<Application> listofAppWithCv = applicationRepo.findAllByResume(resumeId);
        return (listofAppWithCv.size() > 1);
    }
}
