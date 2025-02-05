package org.neuefische.applicationmangementapp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.ApplicationDtoForCreated;
import org.neuefische.applicationmangementapp.model.ApplicationStatus;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.neuefische.applicationmangementapp.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
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

    @MockitoBean
    CloudinaryService mockCloudinaryService;

    @Test
    @DirtiesContext
    void getAllApplications() throws Exception {
        applicationRepo.saveAll(List.of(
                new Application("test","tester","desc","resume", ApplicationStatus.OPEN,null,LocalDate.now()),
                new Application("test2","tester2","desc","resume", ApplicationStatus.OPEN,null,LocalDate.now())

        ));
        mockMvc.perform(get("/api/application")).andExpect(status().isOk()).andExpect(content().json(
                        """
                                [
                                {"id": "test",
                                "jobOfferID": "tester",
                                "resume": "desc",
                                "coverLetter": "resume",
                                "applicationStatus": "OPEN"
                                },
                                {"id": "test2",
                                "jobOfferID": "tester2",
                                "resume": "desc",
                                "coverLetter": "resume",
                                "applicationStatus": "OPEN"}
                                ]
                                
                                """
                )
        );
    }

    @Test
    @DirtiesContext
    void getApplicationById() throws Exception {
        applicationRepo.saveAll(List.of(new Application("test","tester","resume","cl", ApplicationStatus.OPEN, null, LocalDate.of(2025,2,20)),
                new Application("test2","tester2","desc","resume", ApplicationStatus.OPEN,null,LocalDate.of(2025,5,6))));
        mockMvc.perform(get("/api/application/"+"test")).andExpect(status().isOk()).andExpect(content().json(
        """
        {"id": "test",
        "jobOfferID": "tester",
        "resume": "resume",
        "coverLetter": "cl",
        "applicationStatus": "OPEN",
        "reminderTime": null,
       "dateOfCreation": "2025-02-20"       }
       """
        ));
    }

    @Test
    void getApplicationByIDExpectedError() throws Exception {
        mockMvc.perform(get("/api/application/"+"test2")).andExpect(status().isNotFound()).andExpect(content().json(
                """
{"message": "no such id: test2"}
"""
        ));
    }

    @Test
    @DirtiesContext
    void deleteApplicationById() throws Exception {
        applicationRepo.save(new Application("fakeId","testerId","resume","cl", ApplicationStatus.OPEN, null,LocalDate.now()));
        mockMvc.perform(delete("/api/application/"+"fakeId")).andExpect(status().isOk());

    }

    @Test
    @DirtiesContext
    void deleteApplicationByIdExpectThrowNoSuchId() throws Exception {
        mockMvc.perform(delete("/api/application/" + "test2"))
                .andExpect(status().isNotFound()).
                andExpect(result -> assertInstanceOf(NoSuchId.class, result.getResolvedException())).
                andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("no such id:")));
    }

    @Test
    @DirtiesContext
    void updateApplication() throws Exception {
        applicationRepo.save(new Application("test1","tester2","resume2","cob", ApplicationStatus.OPEN, null,LocalDate.of(2020,1,1)))
                ;
        mockMvc.perform(put("/api/application/"+"test1").contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                 "jobOfferID": "tester2",
                 "resume": "resume",
                 "coverLetter": "cover letter",
                 "applicationStatus": "IN_PROGRESS",
                 "reminderTime": null
                 }
                """)).andExpect(status().isOk()).andExpect(content().json("""
{"id": "test1",
"jobOfferID": "tester2",
"resume": "resume",
"coverLetter": "cover letter",
"applicationStatus": "IN_PROGRESS",
"reminderTime": null,
"dateOfCreation": "2020-01-01"}
"""));
    }

    @Test
    @DirtiesContext
    void tryUpdateApplicationByIdShouldFail() throws Exception {
        String updatedApplication = """
                {
                    "jobOfferID": "tester2",
                    "resume": "resume",
                    "coverLetter": "cover letter",
                    "applicationStatus": "IN_PROGRESS",
                    "reminderTime": null
                    }
                """;
        mockMvc.perform(put("/api/job-offer/" + "test").contentType(MediaType.APPLICATION_JSON).
                        content(updatedApplication)).andExpect(status().isNotFound()).
                andExpect(result -> assertInstanceOf(NoSuchId.class, result.getResolvedException())).
                andExpect(content().json("""
                        {"message": "no such id: test"}
                        """)).andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("no such id:")));
    }

    @DirtiesContext
    @Test
    void expectSuccessfulPost() throws Exception {

        ApplicationDtoForCreated metadata = new ApplicationDtoForCreated("1a", null);
        MockMultipartFile meta = new MockMultipartFile("meta", "metadata", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(metadata).getBytes(StandardCharsets.UTF_8));

        MockMultipartFile file = new MockMultipartFile(
                "resume", "test.txt", "text/plain", "Hello, file content".getBytes()
        );
        when(mockCloudinaryService.uploadFile(file)).thenReturn("success");
        String actual = mockMvc.perform(
                        multipart("/api/application").file(meta).file(file)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                             {
                           "jobOfferID":"1a",
                           "resume": "success"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Application actualApplication = objectMapper.readValue(actual, Application.class);
        assertThat(actualApplication.id()).isNotBlank();
    }

    @DirtiesContext
    @Test
    void expectSuccessfulPostWithTwoFiles() throws Exception {

        ApplicationDtoForCreated metadata =new ApplicationDtoForCreated("1a",null);
        MockMultipartFile meta=new MockMultipartFile("meta","metadata",MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsString(metadata).getBytes(StandardCharsets.UTF_8));

        MockMultipartFile file = new MockMultipartFile(
                "resume", "test.txt", "text/plain", "Hello, file content".getBytes()
        );
        MockMultipartFile coverLetter = new MockMultipartFile(
                "coverLetter", "test.txt", "text/plain", "Hello, file content".getBytes()
        );
        when(mockCloudinaryService.uploadFile(file)).thenReturn("success");
        when(mockCloudinaryService.uploadFile(coverLetter)).thenReturn("cl is there");
        String actual = mockMvc.perform(
                        multipart("/api/application").file(meta).file(file).file(coverLetter)
                )
                .andExpect(status().isOk())
                .andExpect(content().json("""
                             {
                           "jobOfferID":"1a",
                           "resume": "success",
                           "coverLetter": "cl is there"
                        }
                        """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Application actualApplication = objectMapper.readValue(actual, Application.class);
        assertThat(actualApplication.id()).isNotBlank();
    }

}
