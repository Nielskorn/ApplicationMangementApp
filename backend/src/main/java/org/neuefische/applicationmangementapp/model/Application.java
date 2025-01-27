package org.neuefische.applicationmangementapp.model;

import org.springframework.data.annotation.Id;

public record Application(@Id  String id, String jobTitle, String jobDescription, String resume, String coverLetter, appliStatus appliStatus ) {
}
