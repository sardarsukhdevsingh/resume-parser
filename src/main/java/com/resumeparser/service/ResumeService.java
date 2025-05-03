package com.resumeparser.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResumeService {

    String handleuploadedResume(MultipartFile file) throws IOException;
}
