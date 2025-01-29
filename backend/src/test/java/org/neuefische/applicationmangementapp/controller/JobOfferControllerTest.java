package org.neuefische.applicationmangementapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.neuefische.applicationmangementapp.model.JobOffer;
import org.neuefische.applicationmangementapp.repo.JobOfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobOfferControllerTest {
 @Autowired
 private MockMvc mockMvc;
 @Autowired
    private ObjectMapper objectMapper;
 @Autowired
    private JobOfferRepo jobOfferRepo;
 @Test
 @DirtiesContext
    void getAllJobOffers() throws Exception {
     jobOfferRepo.saveAll(List.of(
             new JobOffer("test","test","test",
                     "testL","testT","testD"),
             new JobOffer("test2","test2","test2",
                     "testL2","testT2","testD2")));
     mockMvc.perform(get("/api/joboffer")).andExpect(status().isOk())
             .andExpect(content().json("""
[
 {"id":"test",
  "Url_companyLogo":"test",
  "companyName":"test",
  "location":"testL",
  "jobTitle":"testT",
  "jobDescription":"testD"
 },
 {"id":"test2",
  "Url_companyLogo":"test2",
  "companyName":"test2",
  "location":"testL2",
  "jobTitle":"testT2",
  "jobDescription":"testD2"}]
""")
             );
 }
 @Test
    @DirtiesContext
    void getJobOfferById() throws Exception {
     jobOfferRepo.saveAll(List.of(new JobOffer("test","test","test",
             "testL","testT","testD"),new JobOffer("test2","test2","test2",
             "testL2","testT2","testD2")));
     mockMvc.perform(get("/api/joboffer/"+ "test")).
             andExpect(status().isOk()).andExpect(content().json("""
{"id":"test",
"Url_companyLogo":"test",
"companyName":"test",
"location":"testL",
"jobTitle":"testT",
"jobDescription":"testD"
 }
"""
     ));
 }
 @Test
    @DirtiesContext
    void deleteJobOfferById() throws Exception {
     jobOfferRepo.save(new JobOffer("test","test","test","testL","tz","testD"));
     mockMvc.perform(delete("/api/joboffer/"+"test","test")).andExpect(status().isOk());
 }
 @Test
    @DirtiesContext
    void updateJobOfferById() throws Exception {
     jobOfferRepo.save(new JobOffer("test","test","test","testL","tz","testD"));
     String updatedOffer="""
             {
             "id":"test",
             "Url_companyLogo":"Utest",
             "companyName":"Utest",
             "location":"UtestL",
             "jobTitle":"UtestT",
             "jobDescription":"UtestD"
             }
             """;
     mockMvc.perform(put("/api/joboffer/"+"test").contentType(MediaType.APPLICATION_JSON).
             content(updatedOffer)).
             andExpect(status().isOk()).
             andExpect(content().json("""
{             "id":"test",
             "Url_companyLogo":"Utest",
             "companyName":"Utest",
             "location":"UtestL",
             "jobTitle":"UtestT",
             "jobDescription":"UtestD"}
"""));

 }
 @Test
    @DirtiesContext
    void createJobOffer() throws Exception {
     String actual=mockMvc.perform(post("/api/joboffer").contentType(MediaType.APPLICATION_JSON).content("""
{            "id":"test",
             "Url_companyLogo":"Utest",
             "companyName":"Utest",
             "location":"UtestL",
             "jobTitle":"UtestT",
             "jobDescription":"UtestD"}
""")).andExpect(status().isOk()).andExpect(content().json("""
{
             "id":"test",
             "Url_companyLogo":"Utest",
             "companyName":"Utest",
             "location":"UtestL",
             "jobTitle":"UtestT",
             "jobDescription":"UtestD"
             }
""")).andReturn().getResponse().getContentAsString();
     JobOffer jobOffer=objectMapper.readValue(actual, JobOffer.class);
     assertThat(jobOffer.id()).isNotBlank();
 }

}
