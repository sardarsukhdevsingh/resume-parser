package com.resumeparser.service.impl;

import com.resumeparser.dto.ResumeResponse;
import com.resumeparser.service.ResumeService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ResumeServiceImpl implements ResumeService {
    @Override
    public String handleuploadedResume(MultipartFile file) throws IOException {
//        System.out.println("first step");
//        ResumeResponse resumeResponse=new ResumeResponse();
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
        String text="";
        if(filename.toLowerCase().endsWith(".pdf")){
            try(PDDocument document=PDDocument.load(filepath.toFile())){
                PDFTextStripper pdfTextStripper=new PDFTextStripper();
                text=pdfTextStripper.getText(document);
//                resumeResponse.setFilename(filename);
//                resumeResponse.setText(text);
                System.out.println("Extracted from pdf : "+text);
            }
        }
        else if (filename.toLowerCase().endsWith(".docx")){
            try (FileInputStream fis=new FileInputStream(filepath.toFile())){
                XWPFDocument doc=new XWPFDocument(fis);
                XWPFWordExtractor extractor=new XWPFWordExtractor(doc);
                text=extractor.getText();
//                resumeResponse.setFilename(filename);
//                resumeResponse.setText(text);
                System.out.println("TEXT from docx : "+text);
            }
        }



        System.out.println("filename : "+filename);
        System.out.println("content type : "+contentType);
        System.out.println("Size : "+file.getSize()+" bytes.");
        System.out.println("saved to : "+filepath.toAbsolutePath());
        return text;

    }
}
