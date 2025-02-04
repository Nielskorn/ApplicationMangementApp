package org.neuefische.applicationmangementapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.appliStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.neuefische.applicationmangementapp.service.ApplicationService;
import org.neuefische.applicationmangementapp.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    @Autowired
    ObjectMapper objectMapper;
    @Mock
    CloudinaryService cloudinaryService;
    @InjectMocks
    ApplicationService applicationService;


    @Test
    @DirtiesContext
    void getAllApplications() throws Exception {
        applicationRepo.saveAll(List.of(
                new Application("test","tester","desc","resume", appliStatus.OPEN,null,LocalDate.now()),
                new Application("test2","tester2","desc","resume", appliStatus.OPEN,null,LocalDate.now())

        ));
        mockMvc.perform(get("/api/application")).andExpect(status().isOk()).andExpect(content().json(
                """
                        [
                        {"id": "test",
                        "jobOfferID": "tester",
                        "resume": "desc",
                        "coverLetter": "resume",
                        "appliStatus": "OPEN"
                        },
                        {"id": "test2",
                        "jobOfferID": "tester2",
                        "resume": "desc",
                        "coverLetter": "resume",
                        "appliStatus": "OPEN"}
                        ]
                   
                        """
                )
        );
    }
    @Test
    @DirtiesContext
    void getApplicationById() throws Exception {
        applicationRepo.saveAll(List.of(new Application("test","tester","resume","cl",appliStatus.OPEN, null, LocalDate.of(2025,2,20)),
                new Application("test2","tester2","desc","resume", appliStatus.OPEN,null,LocalDate.of(2025,5,6))));
        mockMvc.perform(get("/api/application/"+"test")).andExpect(status().isOk()).andExpect(content().json(
        """
        {"id": "test",
        "jobOfferID": "tester",
        "resume": "resume",
        "coverLetter": "cl",
        "appliStatus": "OPEN",
        "reminderTime": null,
       "dateOfCreation": "2025-02-20"       }
       """
        ));
    }
    @Test
    void getApplicationByIDExpecedError() throws Exception {
        mockMvc.perform(get("/api/application/"+"test2")).andExpect(status().isNotFound()).andExpect(content().json(
                """
{"message": "no such id: test2"}
"""
        ));
    }
    @Test
    @DirtiesContext
    void deleteApplicationById() throws Exception {
        applicationRepo.save(new Application("fakeid","testerId","resume","cl",appliStatus.OPEN, null,LocalDate.now()));
        mockMvc.perform(delete("/api/application/"+"fakeid")).andExpect(status().isOk());

    }
    @Test
    @DirtiesContext
    void updateApplication() throws Exception {
        applicationRepo.save(new Application("test1","tester2","resune","cob", appliStatus.OPEN, null,LocalDate.of(2020,1,1)))
                ;
        mockMvc.perform(put("/api/application/"+"test1").contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                 "jobOfferID": "tester2",
                 "resume": "resume",
                 "coverLetter": "coverletter",
                 "appliStatus": "IN_PROGRESS",
                 "reminderTime": null
                 }
                """)).andExpect(status().isOk()).andExpect(content().json("""
{"id": "test1",
"jobOfferID": "tester2",
"resume": "resume",
"coverLetter": "coverletter",
"appliStatus": "IN_PROGRESS",
"reminderTime": null,
"dateOfCreation": "2020-01-01"}
"""));
    }
    @Test
    @DirtiesContext
    void tryUpdateApplicationByIdShoudFail() throws Exception {
        String updatedApplication="""
             {
                 "jobOfferID": "tester2",
                 "resume": "resume",
                 "coverLetter": "coverletter",
                 "appliStatus": "IN_PROGRESS",
                 "reminderTime": null
                 }
             """;
        mockMvc.perform(put("/api/joboffer/"+"test").contentType(MediaType.APPLICATION_JSON).
                content(updatedApplication)).andExpect(status().isNotFound()).andExpect(content().json("""
{"message": "no such id: test"}
"""));
    }
    @DirtiesContext
    @Test
    void expectSuccessfulPost() throws Exception {
        String actual = mockMvc.perform(
                        post("/api/application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""

                                        {"jobOfferID":"1a","resume": "Omar", "coverLetter": "omar","reminderTime": null}
                                    """)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                         {
                       "jobOfferID":"1a",
                       "resume": "Omar",
                       "coverLetter": "omar"
                       //"reminderTime":null,
                    }
                    """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Application actualApplication = objectMapper.readValue(actual, Application.class);
        assertThat(actualApplication.id()).isNotBlank();
    }

}
