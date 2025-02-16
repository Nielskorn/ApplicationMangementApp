package org.neuefische.applicationmangementapp.model;

import java.util.List;

public record JobApplicationTracker(
        JobOffer jobOffer,
        Application application,
        List<Note> notes
) {
}
