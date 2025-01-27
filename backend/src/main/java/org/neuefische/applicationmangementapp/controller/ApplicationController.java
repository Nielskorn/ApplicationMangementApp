package org.neuefische.applicationmangementapp.controller;

import lombok.RequiredArgsConstructor;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/application")
@RestController

public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public Application createApplication(Application application) {
        return applicationService.addApplication(application);
    }
    @PutMapping
    public Application updateApplication(@RequestBody Application application) {
        return applicationService.updateApplication(application);
    }
    @DeleteMapping
    public void deleteApplication(@RequestParam String id) {
        applicationService.deleteApplicationById(id);
    }


    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();

    }
    @GetMapping
    public Application getApplicationById(@RequestParam String id) {
        return applicationService.getApplicationById(id);
    }
}
