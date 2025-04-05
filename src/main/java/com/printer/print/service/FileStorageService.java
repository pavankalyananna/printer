package com.printer.print.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {
    private final String uploadDir = "/home/pavan/Downloads/Print";

    public void storeFile(MultipartFile file) throws IOException {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File destinationFile = new File(directory, file.getOriginalFilename());
        file.transferTo(destinationFile);
    }
}