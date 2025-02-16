package org.neuefische.applicationmangementapp.controller;

import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.model.Note;
import org.neuefische.applicationmangementapp.repo.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteRepo noteRepo;

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
    void getNoteByIdNosuchId() throws Exception {
        mockMvc.perform(get("/api/note/TestId2")).
                andExpect(status().isNotFound()).andExpect(content().json("""
                        {"message": "no such id: TestId2"}
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
}
