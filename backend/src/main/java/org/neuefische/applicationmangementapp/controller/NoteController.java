package org.neuefische.applicationmangementapp.controller;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Note;
import org.neuefische.applicationmangementapp.model.NoteDto;
import org.neuefische.applicationmangementapp.service.NoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    public final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/getNodesByApplication/{id}")
    public List<Note> getNotesByApplicationIdId(@PathVariable String id) throws NoSuchId {
        return noteService.getNotesByApplicationId(id);
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) throws NoSuchId {
        return noteService.getNoteById(id);
    }

    @PostMapping()
    public Note createNodeOnApplication(@RequestBody NoteDto notes) throws NoSuchId {
        return noteService.createNodeOnApplication(notes);
    }

    @PutMapping("/{id}")
    public Note updateNodeOnApplication(@PathVariable String id, @RequestBody NoteDto notes) throws NoSuchId {
        return noteService.updateNodeOnApplication(id, notes);
    }
}
