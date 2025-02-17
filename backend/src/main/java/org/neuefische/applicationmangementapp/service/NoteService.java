package org.neuefische.applicationmangementapp.service;

import org.neuefische.applicationmangementapp.exceptions.NoSuchId;
import org.neuefische.applicationmangementapp.model.Note;
import org.neuefische.applicationmangementapp.model.NoteDto;
import org.neuefische.applicationmangementapp.repo.NoteRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepo noteRepo;
    private final ApplicationService applicationService;

    public NoteService(NoteRepo noteRepo, ApplicationService applicationService) {
        this.noteRepo = noteRepo;
        this.applicationService = applicationService;
    }

    public List<Note> getNotesByApplicationId(String applicationId) {
        return noteRepo.findAllByApplicationId(applicationId);
    }

    public Note getNoteById(String id) throws NoSuchId {
        return noteRepo.findById(id).orElseThrow(() -> new NoSuchId(id));
    }

    public Note createNoteOnApplication(NoteDto noteDto) throws NoSuchId {
        if (applicationService.getOptionalApplicationById(noteDto.applicationId()).isPresent()) {
            Note note = new Note(IdService.getId(), noteDto.applicationId(), noteDto.notes());
            return noteRepo.save(note);
        } else {
            throw new NoSuchId(noteDto.applicationId() + "no Application");
        }
    }

    public Note updateNoteOnApplication(String id, NoteDto noteDto) throws NoSuchId {
        noteRepo.findById(id).orElseThrow(() -> new NoSuchId(id));
        if (applicationService.getOptionalApplicationById(noteDto.applicationId()).isPresent()) {
            Note note = new Note(id, noteDto.applicationId(), noteDto.notes());
            return noteRepo.save(note);
        } else {
            throw new NoSuchId(noteDto.applicationId() + " no Application with that Id");

        }
    }

    public void deleteNoteById(String id) throws NoSuchId {
        noteRepo.findById(id).orElseThrow(() -> new NoSuchId(id));
        noteRepo.deleteById(id);

    }
}

