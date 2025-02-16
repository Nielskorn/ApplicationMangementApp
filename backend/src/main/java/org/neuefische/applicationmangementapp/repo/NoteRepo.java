package org.neuefische.applicationmangementapp.repo;

import org.neuefische.applicationmangementapp.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends MongoRepository<Note, String> {


    List<Note> findAllByApplicationId(String applicationId);
}
