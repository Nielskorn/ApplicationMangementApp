package org.neuefische.applicationmangementapp.controller;

import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.neuefische.applicationmangementapp.repo.JobOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
 class JobApplicationTrackerControllerTest {
 @Autowired
 private MockMvc mockMvc;
    @Autowired
    private    ApplicationRepo applicationRepo;
 @Autowired
 private    JobOfferRepo jobOfferRepo;
    @Test
    @DirtiesContext
    void testGetAllJobApplicationTrackers() throws Exception {
        applicationRepo.save(
                new Application(
                        "testA",
                "testJO",
                "testCv",null,
                appliStatus.OPEN,null,
                LocalDate.of(2025,10,10))
        );
                jobOfferRepo.save(
                        new JobOffer(
                                "testJO",
                        "test",
                        "testC",
                        "testdorf",
                        "tester",
                        "testing",
                        "testlink")
                );
                mockMvc.perform(get("/api/JobApplication")).andExpect(status().isOk()).andExpect(content().json("""
                        [
                          {
                            "jobOffer":
                              {
                                "id":"testJO",
                                "Url_companyLogo":"test",
                                "companyName": "testC",
                                "location": "testdorf",
                                "jobTitle": "tester",
                                "jobDescription":"testing",
                                "LinkJobAd": "testlink"
                              },
                            "application":
                              {   "id":"testA",
                          "jobOfferID":"testJO",
                          "resume":"testCv",
                          "coverLetter": null,
                                "appliStatus":"OPEN",
                                 "reminderTime": null,
                                 "dateOfCreation": "2025-10-10"
                         }
                         }
                        ]
                        """));
    }
    @Test
    @DirtiesContext
    void testGetJobApplicationTrackerById() throws Exception {
        applicationRepo.save(new Application("testA","testJo","testCv",null,appliStatus.OPEN,null,LocalDate.of(2025,10,10)));
        jobOfferRepo.save(new JobOffer("testJo","logoT","cTest","testLane","tester2","testing2","testlink2"));
        mockMvc.perform(get("/api/JobApplication/"+"testA")).
                andExpect(status().isOk()).
                andExpect(content().
                        json("""
{
                            "jobOffer":
                              {
                                "id":"testJo",
                                "Url_companyLogo":"logoT",
                                "companyName": "cTest",
                                "location": "testLane",
                                "jobTitle": "tester2",
                                "jobDescription":"testing2",
                                "LinkJobAd": "testlink2"
                              },
                            "application":
                              {   "id":"testA",
                          "jobOfferID":"testJo",
                          "resume":"testCv",
                          "coverLetter": null,
                                "appliStatus":"OPEN",
                                 "reminderTime": null,
                                 "dateOfCreation": "2025-10-10"
                         }
                         }
""")
                );
    }
}
