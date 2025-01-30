package org.neuefische.applicationmangementapp.model;


import java.time.Instant;

public record ErrorMessage(String message, Instant timestamp)  {
}
