package org.neuefische.applicationmangementapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {
   @Resource
    private Cloudinary cloudinary;


    public String uploadFile(MultipartFile file) throws IOException {

            Map<String,String> options = new HashMap<>();
            options.put("folder", "ApplicationMangementApp");

            options.put("resource_type", "raw");
            options.put("format", "pdf");
            File fileToUpload = File.createTempFile(file.getOriginalFilename(),"");
            file.transferTo(fileToUpload);
            //NOSONAR
            Map uploadFile=cloudinary.uploader().upload(fileToUpload,options);
            String publicId = (String) uploadFile.get("public_id");
             return cloudinary.url().secure(true).generate(publicId);


    }
    public void deleteFile(String url) throws IOException {


          String removeUrl= url.replaceFirst("^.*?/upload/", "");
          String removeVersion=removeUrl.replaceFirst("^v[0-9]+/", "");
           cloudinary.uploader().destroy(removeVersion, ObjectUtils.asMap("resource_type", "raw"));



    }
}
