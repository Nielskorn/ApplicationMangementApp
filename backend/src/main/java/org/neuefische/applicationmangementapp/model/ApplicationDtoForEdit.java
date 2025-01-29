package org.neuefische.applicationmangementapp.model;

import java.time.LocalDateTime;

public record ApplicationDtoForEdit(String jobOfferId, String resume, String coverLetter, appliStatus appliStatus, LocalDateTime reminderTime) {
}
