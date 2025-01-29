package org.neuefische.applicationmangementapp.model;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;

public record JobOffer(@Id String id,String Url_companyLogo,String companyName,String location,String jobTitle, String jobDescription) {
}
