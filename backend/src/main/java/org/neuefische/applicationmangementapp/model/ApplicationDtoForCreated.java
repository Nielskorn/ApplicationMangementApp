package org.neuefische.applicationmangementapp.model;

import java.time.LocalDateTime;


public record ApplicationDtoForCreated(String jobOfferID, String resume, String coverLetter,
                                       LocalDateTime reminderTime) {
}
