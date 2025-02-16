package org.neuefische.applicationmangementapp.controller;

import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Application;
import org.neuefische.applicationmangementapp.model.ApplicationStatus;
import org.neuefische.applicationmangementapp.model.Note;
import org.neuefische.applicationmangementapp.repo.ApplicationRepo;
import org.neuefische.applicationmangementapp.repo.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteRepo noteRepo;
    @Autowired
    private ApplicationRepo applicationRepo;

    @Test
    @DirtiesContext
    void getNotesByApplicationId() throws Exception {
        noteRepo.saveAll(List.of(
                new Note("TestId", "AIdTest", "TestnoteText"),
                new Note("TestId2", "AIdTest2", "testText"),
                new Note("TestId3", "AIdTest", "T")
        ));

        mockMvc.perform(get("/api/note/getNodesByApplication/AIdTest")).
                andExpect(status().isOk()).
                andExpect(content().json(
                        """
                                [{"id": "TestId","applicationId": "AIdTest","notes": "TestnoteText"},{
                                "id": "TestId3","applicationId": "AIdTest","notes": "T"
                                }]
                                
                                """
                ));
    }

    @Test
    @DirtiesContext
    void getNoteById() throws Exception {
        noteRepo.saveAll(List.of(new Note("TestId", "AIdTest", "TestnoteText"),
                new Note("TestId2", "AIdTest2", "testText")));

        mockMvc.perform(get("/api/note/TestId2")).
                andExpect(status().isOk()).
                andExpect(content().json("""
                        {
                        "id": "TestId2",
                        "applicationId": "AIdTest2",
                        "notes": "testText"
                        }
                        """));
    }

    @Test
    void getNoteByIdNoSuchId() throws Exception {
        mockMvc.perform(get("/api/note/TestId2")).
                andExpect(status().isNotFound()).andExpect(content().json("""
                        {"message": "no such id: TestId2"}
                        """));
    }

    @Test
    @DirtiesContext
    void UpdateNote() throws Exception {
        noteRepo.saveAll(List.of(new Note("TestId", "AIdTest", "TestnoteText"),
                new Note("TestId2", "AIdTest2", "testText")));
        applicationRepo.saveAll(List.of(
                new Application("AIdTest", "JOID", "max_musterman_resume.pdf", null, ApplicationStatus.IN_PROGRESS, null, LocalDate.of(2025, 3, 4)),
                new Application("AIdTest2", "JOID2", "max_musterman_resume.pdf", null, ApplicationStatus.IN_PROGRESS, null, LocalDate.of(2025, 2, 22))
        ));

        String updatedNote = """
                {
                "applicationId": "AIdTest2",
                "notes": "testText123"
                }
                """;

        mockMvc.perform(put("/api/note/TestId2").
                        contentType(MediaType.APPLICATION_JSON).
                        content(updatedNote)).
                andExpect(status().isOk()).andExpect(content().json("""
                        {
                        "id":"TestId2",
                        "applicationId": "AIdTest2",
                        "notes": "testText123"
                        }
                        """));
    }

    @Test
    @DirtiesContext
    void updateNoteWithNoJobOffer() throws Exception {
        noteRepo.saveAll(List.of(new Note("TestId", "AIdTest", "TestnoteText"),
                new Note("TestId2", "AIdTest2", "testText")));


        String updatedNote = """
                {
                "applicationId": "AIdTest2",
                "notes": "testText123"
                }
                """;

        mockMvc.perform(put("/api/note/" + "TestId2").
                        contentType("application/json").
                        content(updatedNote)).
                andExpect(status().isNotFound()).
                andExpect(content().json("""
                        {
                        "message": "no such id: AIdTest2 no Application with that Id"
                        }
                        """));
    }

    @Test
    @DirtiesContext
    void deleteNote() throws Exception {
        noteRepo.save(new Note("TestId", "AIdTest", "TestnoteText"));

        mockMvc.perform(delete("/api/note/" + "TestId")).andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void deleteNoteExpectThrowNoSuchId() throws Exception {
        mockMvc.perform(delete("/api/note/" + "TestId")).
                andExpect(status().isNotFound()).
                andExpect(result ->
                        assertInstanceOf(NoSuchId.class, result.getResolvedException()))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("no such id:")));
    }
}
