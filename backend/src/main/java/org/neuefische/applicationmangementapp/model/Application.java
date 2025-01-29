package org.neuefische.applicationmangementapp.model;

import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record Application(@Id String id, String jobOfferID, String resume, String coverLetter, appliStatus appliStatus, LocalDateTime reminderTime,
                          LocalDate dateOfCreation) {
}
