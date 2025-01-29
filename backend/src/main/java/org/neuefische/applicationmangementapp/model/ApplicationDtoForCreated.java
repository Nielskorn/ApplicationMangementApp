package org.neuefische.applicationmangementapp.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record ApplicationDtoForCreated(String jobOfferID, String resume, String coverLetter, LocalDateTime reminder) {
}
