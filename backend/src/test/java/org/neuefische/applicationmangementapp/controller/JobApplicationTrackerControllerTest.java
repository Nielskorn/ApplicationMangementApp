package org.neuefische.applicationmangementapp.controller;

import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.ApplicationStatus;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.model.Note;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.neuefische.applicationmangementapp.repo.JobOfferRepo;
import org.neuefische.applicationmangementapp.repo.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobApplicationTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private JobOfferRepo jobOfferRepo;
    @Autowired
    private NoteRepo noteRepo;

    @Test
    @DirtiesContext
    void testGetAllJobApplicationTrackers() throws Exception {
        applicationRepo.save(
                new Application(
                        "testA",
                        "testJO",
                        "testCv", null,
                        ApplicationStatus.OPEN, null,
                        LocalDate.of(2025, 10, 10))
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
                        "applicationStatus":"OPEN",
                         "reminderTime": null,
                         "dateOfCreation": "2025-10-10"
                 },"notes":null
                 }
                ]
                """));
    }

    @Test
    @DirtiesContext
    void testGetJobApplicationTrackerById() throws Exception {
        applicationRepo.save(new Application("testA", "testJo", "testCv", null, ApplicationStatus.OPEN, null, LocalDate.of(2025, 10, 10)));
        jobOfferRepo.save(new JobOffer("testJo", "logoT", "cTest", "testLane", "tester2", "testing2", "testlink2"));
        mockMvc.perform(get("/api/JobApplication/" + "testA")).
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
                                                                "applicationStatus":"OPEN",
                                                                 "reminderTime": null,
                                                                 "dateOfCreation": "2025-10-10"
                                                         },
                                                         "notes":[]
                                                         }
                                """)
                );
    }

    @Test
    @DirtiesContext
    void testTryGetJobApplicationTrackerByApplicationIdExpectThrowNoSuchId() throws Exception {
        applicationRepo.save(new Application("testA", "testJo", "testCv", null, ApplicationStatus.OPEN, null, LocalDate.of(2025, 10, 10)));
        jobOfferRepo.save(new JobOffer("testJo", "logoT", "cTest", "testLane", "tester2", "testing2", "testlink2"));
        mockMvc.perform(get("/api/JobApplication/" + "testB")).
                andExpect(status().isNotFound()).
                andExpect(result -> assertInstanceOf(NoSuchId.class, result.getResolvedException())
                ).andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("no such id: " + "testB" + "By Application")));
    }

    @Test
    @DirtiesContext
    void testTryGetJobApplicationTrackerByApplicationIdWithNoJobOffer() throws Exception {
        applicationRepo.save(new Application("testA", "testJo", "testCv", null, ApplicationStatus.OPEN, null, LocalDate.of(2025, 10, 10)));
        mockMvc.perform(get("/api/JobApplication/" + "testA")).
                andExpect(status().isOk()).
                andExpect(content().
                        json("""
                                {
                                                            "jobOffer":null,
                                                            "application":
                                                              {
                                                                "id":"testA",
                                                          "jobOfferID":"testJo",
                                                          "resume":"testCv",
                                                          "coverLetter": null,
                                                                "applicationStatus":"OPEN",
                                                                 "reminderTime": null,
                                                                 "dateOfCreation": "2025-10-10"
                                                         },
                                                         "notes":[]
                                                         }
                                """)
                );

    }

    @Test
    @DirtiesContext
    void testGetAllJobApplicationTracker() throws Exception {
        applicationRepo.saveAll(List.of(
                new Application("testA",
                        "testJO",
                        "testCv", null,
                        ApplicationStatus.OPEN, null,
                        LocalDate.of(2025, 10, 10))
                , new Application("testB",
                        "noId",
                        "testCvB", null,
                        ApplicationStatus.OPEN, null,
                        LocalDate.of(2025, 11, 11))
                , new Application("testC", "testJo2", "testC_Cv.pdf", null, ApplicationStatus.IN_PROGRESS, null, LocalDate.of(2025, 2, 19)))
        );
        jobOfferRepo.saveAll(List.of(new JobOffer("testJO",
                "test",
                "testC",
                "testdorf",
                "tester",
                "testing",
                "testlink"), new JobOffer("testJo2", "c2testLogo", "C2Test", "teststadt", "Tester", "testing", "www.C2Test.com")));
        noteRepo.save(new Note("nid1", "testC", "testnote"));
        mockMvc.perform(get("/api/JobApplication/all")).
                andExpect(status().isOk()).
                andExpect(content().json("""
                                          [ { "jobOffer":
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
                                                        "applicationStatus":"OPEN",
                                                         "reminderTime": null,
                                                         "dateOfCreation": "2025-10-10"
                                                 }
                                                 }
                                                 ,
                                                 {
                                                 jobOffer:null,
                                                 "application":{
                                                 "id":"testB",
                                                 "jobOfferID":"noId",
                                                 "resume":"testCvB",
                                                 "coverLetter": null,
                                                 "applicationStatus":"OPEN",
                                                 "reminderTime": null,
                                                 "dateOfCreation": "2025-11-11"
                                                 } },
                                                 {jobOffer:{
                                                 "id":"testJo2",
                                                        "Url_companyLogo":"c2testLogo",
                                                        "companyName": "C2Test",
                                                        "location": "teststadt",
                                                        "jobTitle": "Tester",
                                                        "jobDescription":"testing",
                                                        "LinkJobAd": "www.C2Test.com"
                                                 },application:{
                                                 "id":"testC",
                                                 "jobOfferID":"testJo2",
                                                 "resume":"testC_Cv.pdf",
                                                 "coverLetter": null,
                                                 "applicationStatus":"IN_PROGRESS",
                                                 "reminderTime": null,
                                                 "dateOfCreation": "2025-02-19"
                                                 },notes:[{
                                                 "id":"nid1",
                                                 "applicationId":"testC",
                                                 "notes": "testnote"
                                                 }]}
                        
                                                 ]
                        """)
                );

    }

    @Test
    @DirtiesContext
    void testGet6JobApploicationWithNextReminder() throws Exception {
        applicationRepo.saveAll(List.of(new Application("testA", "JOB54321", "Lebenslauf_Thomas_Meyer.pdf", "Anschreiben_Thomas_Meyer.pdf", ApplicationStatus.OPEN, LocalDateTime.of(2025, 2, 25, 8, 0), LocalDate.of(2025, 2, 12)), new Application("testB", "JOB12345", "Lebenslauf_Max_Mustermann.pdf", "Anschreiben_Max_Mustermann.pdf", ApplicationStatus.IN_PROGRESS, LocalDateTime.of(2025, 2, 20, 9, 0), LocalDate.of(2025, 2, 10)), new Application("testC", "JOB67890", "Lebenslauf_Anna_Schmidt.pdf", "Anschreiben_Anna_Schmidt.pdf", ApplicationStatus.OPEN, null, LocalDate.of(2025, 2, 11))));
        jobOfferRepo.saveAll(List.of(new JobOffer("JOB123456", "https://example.com/logo_company_a.png", "Company A", "Berlin, Germany", "Software Engineer", "Entwicklung von Webanwendungen und Cloud-Services.", "https://example.com/job12345"), new JobOffer("JOB67890", "https://example.com/logo_company_b.png", "Company B", "München, Germany", "Data Scientist", "Analyse von Daten und Erstellung von Machine-Learning-Modellen.", "https://example.com/job67890"), new JobOffer("JOB54321", "https://example.com/logo_company_c.png", "Company C", "Hamburg, Germany", "Product Manager", "Verantwortung für die Produktstrategie und -entwicklung.", "https://example.com/job54321"))

        );

        mockMvc.perform(get("/api/JobApplication/dash")).andExpect(status().isOk()).andExpect(content().json("""
                [
                {"jobOffer": null,
                "application":{
                "id": "testB",
                "jobOfferID": "JOB12345",
                "resume": "Lebenslauf_Max_Mustermann.pdf",
                "coverLetter": "Anschreiben_Max_Mustermann.pdf",
                "applicationStatus": "IN_PROGRESS",
                "reminderTime": "2025-02-20T09:00:00",
                "dateOfCreation": "2025-02-10"
                }
                },
                {"jobOffer": {
                "id": "JOB54321",
                "Url_companyLogo":"https://example.com/logo_company_c.png",
                "companyName": "Company C",
                "location": "Hamburg, Germany",
                "jobTitle": "Product Manager",
                "jobDescription": "Verantwortung für die Produktstrategie und -entwicklung.",
                "LinkJobAd": "https://example.com/job54321"
                },
                "application":{ "id": "testA",
                "jobOfferID": "JOB54321",
                "resume": "Lebenslauf_Thomas_Meyer.pdf",
                "coverLetter": "Anschreiben_Thomas_Meyer.pdf",
                "applicationStatus": "OPEN",
                "reminderTime": "2025-02-25T08:00:00",
                "dateOfCreation": "2025-02-12"}
                 }
                ,{"jobOffer": {
                "id": "JOB67890",
                "Url_companyLogo":"https://example.com/logo_company_b.png",
                "companyName": "Company B",
                "location": "München, Germany",
                "jobTitle":  "Data Scientist",
                "jobDescription":  "Analyse von Daten und Erstellung von Machine-Learning-Modellen.",
                "LinkJobAd": "https://example.com/job67890"
                },
                "application":{
                 "id": "testC",
                "jobOfferID": "JOB67890",
                "resume": "Lebenslauf_Anna_Schmidt.pdf",
                "coverLetter":  "Anschreiben_Anna_Schmidt.pdf",
                "applicationStatus":  "OPEN",
                "reminderTime": null,
                "dateOfCreation": "2025-02-11"
                
                } }
                ]
                
                """));
    }

    @Test
    @DirtiesContext
    void testGet6JobApploicationWithNextReminderwithMoreThen6() throws Exception {
        applicationRepo.saveAll(List.of(
                new Application("testA", "JOB54321", "Lebenslauf_Thomas_Meyer.pdf", "Anschreiben_Thomas_Meyer.pdf", ApplicationStatus.OPEN, LocalDateTime.of(2025, 2, 25, 8, 0), LocalDate.of(2025, 2, 12)),
                new Application("testB", "JOB12345", "Lebenslauf_Max_Mustermann.pdf", "Anschreiben_Max_Mustermann.pdf", ApplicationStatus.IN_PROGRESS, LocalDateTime.of(2025, 2, 20, 9, 0), LocalDate.of(2025, 2, 10)),
                new Application("testC", "JOB67890", "Lebenslauf_Anna_Schmidt.pdf", "Anschreiben_Anna_Schmidt.pdf", ApplicationStatus.OPEN, null, LocalDate.of(2025, 2, 11)),
                new Application("testD", "JOB09876", "Lebenslauf_Sophie_Bauer.pdf", "Anschreiben_Sophie_Bauer.pdf", ApplicationStatus.OPEN, LocalDateTime.of(2025, 2, 21, 10, 0), LocalDate.of(2025, 2, 13)),
                new Application("testE", "JOB11223", "Lebenslauf_Lukas_Fischer.pdf", "Anschreiben_Lukas_Fischer.pdf", ApplicationStatus.OPEN, LocalDateTime.of(2025, 2, 28, 11, 0), LocalDate.of(2025, 2, 14)),
                new Application("testF", "JOB12233", "Lebenslauf_Julia_Weber.pdf", "Anschreiben_Julia_Weber.pdf", ApplicationStatus.OPEN, null, LocalDate.of(2025, 2, 14)),
                new Application("testG", "nothing", "Lebenslauf_Michael_Fischer.pdf", null, ApplicationStatus.OPEN, null, LocalDate.of(2025, 6, 14))


        ));
        jobOfferRepo.saveAll(List.of(
                new JobOffer("JOB123456", "https://example.com/logo_company_a.png", "Company A", "Berlin, Germany", "Software Engineer", "Entwicklung von Webanwendungen und Cloud-Services.", "https://example.com/job12345"),
                new JobOffer("JOB67890", "https://example.com/logo_company_b.png", "Company B", "München, Germany", "Data Scientist", "Analyse von Daten und Erstellung von Machine-Learning-Modellen.", "https://example.com/job67890"),
                new JobOffer("JOB54321", "https://example.com/logo_company_c.png", "Company C", "Hamburg, Germany", "Product Manager", "Verantwortung für die Produktstrategie und -entwicklung.", "https://example.com/job54321"),
                new JobOffer("JOB09876", "https://example.com/logo_company_d.png", "Company D", "Stuttgart, Germany", "UX Designer", "Gestaltung von benutzerfreundlichen Interfaces und Prototypen.", "https://example.com/job09876"),
                new JobOffer("JOB11223", "https://example.com/logo_company_e.png", "Company E", "Köln, Germany", "IT Consultant", "Beratung und Implementierung von IT-Lösungen.", "https://example.com/job11223"),
                new JobOffer("JOB12233", "https://example.com/logo_company_f.png", "Company F", "Berlin, Germany", "Software Engineer", "Entwicklung und Wartung von Webanwendungen.", "https://example.com/job12233")


        ));

        mockMvc.perform(get("/api/JobApplication/dash")).andExpect(status().isOk()).andExpect(content().json("""
                 [
                 {"jobOffer": null,
                 "application":{
                 "id": "testB",
                 "jobOfferID": "JOB12345",
                 "resume": "Lebenslauf_Max_Mustermann.pdf",
                 "coverLetter": "Anschreiben_Max_Mustermann.pdf",
                 "applicationStatus": "IN_PROGRESS",
                 "reminderTime": "2025-02-20T09:00:00",
                 "dateOfCreation": "2025-02-10"
                 }
                 },
                 {"jobOffer":{"id":"JOB09876",
                 "Url_companyLogo":"https://example.com/logo_company_d.png",
                 "companyName":"Company D","location":"Stuttgart, Germany",
                 "jobTitle":"UX Designer",
                 "jobDescription":"Gestaltung von benutzerfreundlichen Interfaces und Prototypen.",
                 "LinkJobAd":"https://example.com/job09876"},
                 "application":{"id":"testD",
                 "jobOfferID":"JOB09876",
                 "resume":"Lebenslauf_Sophie_Bauer.pdf",
                 "coverLetter":"Anschreiben_Sophie_Bauer.pdf",
                 "applicationStatus":"OPEN",
                 "reminderTime":"2025-02-21T10:00:00",
                 "dateOfCreation":"2025-02-13"}
                 },
                 {"jobOffer": {
                 "id": "JOB54321",
                 "Url_companyLogo":"https://example.com/logo_company_c.png",
                 "companyName": "Company C",
                 "location": "Hamburg, Germany",
                 "jobTitle": "Product Manager",
                 "jobDescription": "Verantwortung für die Produktstrategie und -entwicklung.",
                 "LinkJobAd": "https://example.com/job54321"
                 },
                 "application":{ "id": "testA",
                 "jobOfferID": "JOB54321",
                 "resume": "Lebenslauf_Thomas_Meyer.pdf",
                 "coverLetter": "Anschreiben_Thomas_Meyer.pdf",
                 "applicationStatus": "OPEN",
                 "reminderTime": "2025-02-25T08:00:00",
                 "dateOfCreation": "2025-02-12"}
                  },{"jobOffer": {"id": "JOB11223",
                 "Url_companyLogo":"https://example.com/logo_company_e.png",
                 "companyName": "Company E",
                 "location": "Köln, Germany",
                 "jobTitle": "IT Consultant",
                 "jobDescription": "Beratung und Implementierung von IT-Lösungen.",
                 "LinkJobAd": "https://example.com/job11223"},
                 "application":{
                 "id": "testE",
                 "jobOfferID": "JOB11223",
                 "resume": "Lebenslauf_Lukas_Fischer.pdf",
                  "coverLetter": "Anschreiben_Lukas_Fischer.pdf",
                  "applicationStatus": "OPEN",
                  "reminderTime": "2025-02-28T11:00:00",
                  "dateOfCreation": "2025-02-14"
                  }
                  }
                 ,{"jobOffer": {
                 "id": "JOB67890",
                 "Url_companyLogo":"https://example.com/logo_company_b.png",
                 "companyName": "Company B",
                 "location": "München, Germany",
                 "jobTitle":  "Data Scientist",
                 "jobDescription":  "Analyse von Daten und Erstellung von Machine-Learning-Modellen.",
                 "LinkJobAd": "https://example.com/job67890"
                 },
                 "application":{
                  "id": "testC",
                 "jobOfferID": "JOB67890",
                 "resume": "Lebenslauf_Anna_Schmidt.pdf",
                 "coverLetter":  "Anschreiben_Anna_Schmidt.pdf",
                 "applicationStatus":  "OPEN",
                 "reminderTime": null,
                 "dateOfCreation": "2025-02-11"
                 } },{"jobOffer":{
                 "id": "JOB12233",
                 "Url_companyLogo":"https://example.com/logo_company_f.png",
                "companyName": "Company F",
                 "location": "Berlin, Germany",
                 "jobTitle":  "Software Engineer",
                 "jobDescription":  "Entwicklung und Wartung von Webanwendungen.",
                 "LinkJobAd": "https://example.com/job12233"
                
                 },"application": {
                 "id": "testF",
                 "jobOfferID": "JOB12233",
                 "resume": "Lebenslauf_Julia_Weber.pdf",
                 "coverLetter":  "Anschreiben_Julia_Weber.pdf",
                 "applicationStatus":  "OPEN",
                 "reminderTime": null,
                 "dateOfCreation": "2025-02-14"
                 }}
                 ]
                
                """));
    }
}
