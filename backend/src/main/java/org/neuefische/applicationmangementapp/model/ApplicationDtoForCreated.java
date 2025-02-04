package org.neuefische.applicationmangementapp.model;


import java.time.LocalDateTime;


public record ApplicationDtoForCreated(String jobOfferID,  LocalDateTime reminderTime) {
}
