package org.neuefische.applicationmangementapp.model;

import java.time.LocalDateTime;


public record ApplicationDtoForCreated(String jobOfferID, String coverLetter,
                                       LocalDateTime reminderTime) {
}
