package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForCreated;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForEdit;
import org.neuefische.applicationmangementapp.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/application")
@RestController

public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping()
    public Application createApplication(@RequestPart("resume") MultipartFile resume, @RequestPart(required = false) MultipartFile coverLetter, @RequestPart("meta") ApplicationDtoForCreated application) throws IOException {
        return applicationService.addApplication(application, resume, coverLetter);
    }

    @PutMapping("/{id}")
    public Application updateApplication(@PathVariable String id, @RequestBody ApplicationDtoForEdit application) throws NoSuchId {
        return applicationService.updateApplication(id, application);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable String id) throws NoSuchId, IOException {
        applicationService.deleteApplicationById(id);
    }


    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();

    }

    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable String id) throws NoSuchId {
        return applicationService.getApplicationById(id);
    }
}
