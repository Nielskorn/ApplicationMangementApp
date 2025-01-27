package org.neuefische.applicationmangementapp.controller;

import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationControllerTest {
@Autowired
private MockMvc mockMvc;
@Autowired
private ApplicationRepo applicationRepo;

    @Test
    @DirtiesContext
    void getAllApplications() throws Exception {
        applicationRepo.saveAll(List.of(
                new Application("test","tester","desc","resume","coverletter", appliStatus.OPEN),
                new Application("test2","tester2","desc","resume","coverletter", appliStatus.OPEN)

        ));
        mockMvc.perform(get("/api/application")).andExpect(status().isOk()).andExpect(content().json(
                """
                        [
                        {"id": "test",
                        "jobTitle": "tester",
                        "jobDescription": "desc",
                        "resume": "resume",
                        "coverLetter": "coverletter",
                        "appliStatus": "OPEN"
                        },
                        {"id": "test2",
                        "jobTitle": "tester2",
                        "jobDescription": "desc",
                        "resume": "resume",
                        "coverLetter": "coverletter",
                        "appliStatus": "OPEN"}
                        ]
                   
                        """
                )
        );
    }
    @Test
    @DirtiesContext
    void getApplicationById() throws Exception {
        applicationRepo.saveAll(List.of(new Application("test","tester","desc","resume","coverletter", appliStatus.OPEN),
                new Application("test2","tester2","desc","resume","coverletter", appliStatus.OPEN)));
        mockMvc.perform(get("/api/application/"+"test")).andExpect(status().isOk()).andExpect(content().json(
        """
        {"id": "test",
        "jobTitle": "tester",
        "jobDescription": "desc",
        "resume": "resume",
        "coverLetter": "coverletter",
        "appliStatus": "OPEN"}
        """
        ));
    }
    @Test
    @DirtiesContext
    void deleteApplicationById() throws Exception {
        applicationRepo.save(new Application("test","tester","desc","resume","coverletter", appliStatus.OPEN));
        mockMvc.perform(delete("/api/application/"+"test")).andExpect(status().isOk());

    }
    @Test
    @DirtiesContext
    void updateApplication() throws Exception {
        applicationRepo.save(new Application("test","tester","desc","resume","coverletter", appliStatus.OPEN));
        String updateApplication = """
                {"id":"test",
                 "jobTitle": "tester2",
                 "jobDescription": "desc",
                 "resume": "resume",
                 "coverLetter": "coverletter",
                 "appliStatus": "IN_PROGRESS"}
                """
                ;
        mockMvc.perform(put("/api/application/"+"test").contentType(MediaType.APPLICATION_JSON)
                .content(updateApplication)).andExpect(status().isOk()).andExpect(content().json("""
{"id": "test",
"jobTitle": "tester2",
"jobDescription": "desc",
"resume": "resume",
"coverLetter": "coverletter",
"appliStatus": "IN_PROGRESS"}
"""));
    }

}
