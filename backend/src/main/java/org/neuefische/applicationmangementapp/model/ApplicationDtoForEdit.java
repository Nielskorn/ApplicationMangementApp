package org.neuefische.applicationmangementapp.model;

import java.time.LocalDateTime;

public record ApplicationDtoForEdit(String jobOfferID, String resume, String coverLetter, appliStatus appliStatus, LocalDateTime reminderTime) {
}
