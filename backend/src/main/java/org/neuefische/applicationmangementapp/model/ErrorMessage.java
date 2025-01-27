package org.neuefische.applicationmangementapp.model;

import java.io.Serializable;
import java.time.Instant;

public record ErrorMessage(String message, Instant timestamp)  {
}
