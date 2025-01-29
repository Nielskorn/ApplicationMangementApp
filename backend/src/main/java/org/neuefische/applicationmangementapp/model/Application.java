package org.neuefische.applicationmangementapp.model;

import org.springframework.data.annotation.Id;


import java.time.LocalDate;
import java.time.LocalDateTime;


public record Application(@Id String id, String jobOfferID, String resume, String coverLetter, appliStatus appliStatus, LocalDateTime reminderTime,
                          LocalDate dateOfCreation) {}
