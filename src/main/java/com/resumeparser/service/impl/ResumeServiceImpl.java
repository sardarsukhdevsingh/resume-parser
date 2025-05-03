package com.resumeparser.service.impl;

import com.resumeparser.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Override
    public String handleuploadedResume(MultipartFile file) throws IOException {
        System.out.println("first step");
        if(file.isEmpty()){
            System.out.println("isempty **********");
            throw new IOException("Uploaded file is Empty");
        }
//        File f= (File) file;
//        System.out.println("File name from file : "+f.getName());
        String filename=file.getOriginalFilename();
        String contentType=file.getContentType();
        System.out.println(filename);
        System.out.println(contentType);
        if(filename == null || (!filename.endsWith(".pdf"))&& (!filename.endsWith(".docx"))){
            System.out.println("something **********");
            throw new IOException("File extension must be pdf or docx");
        }

        Path uploadDir= Paths.get("uploads");
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path filepath=uploadDir.resolve(filename);
        Files.copy(file.getInputStream(),filepath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("filename : "+filename);
        System.out.println("content type : "+contentType);
        System.out.println("Size : "+file.getSize()+" bytes.");
        System.out.println("saved to : "+filepath.toAbsolutePath());
        return filename;

    }
}
