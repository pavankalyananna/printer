package com.printer.print.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.printer.print.service.FileStorageService;

@Controller
public class FileUploadController {
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public String uploadPage() {
        return "upload"; // Return the name of the HTML file without the .html extension
    }

    @PostMapping("/upload")
    public String handleFileUpload(MultipartFile file, String colorOption, Model model) {
        try {
            // Store the uploaded file
            fileStorageService.storeFile(file);
            
            // Generate QR code for the UPI payment link
            String qrCodeData = "upi://pay?pa=7995132134@axisb&pn=ANNAPAVANKALYAN&am=10";
            String qrCodeImage = generateQRCodeImage(qrCodeData);
            
            // Add attributes to the model
            model.addAttribute("qrCodeImage", qrCodeImage);
            model.addAttribute("colorOption", colorOption);
            return "upload"; // Return to the upload page to display the QR code
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            return "upload"; // Return to upload page on error
        }
    }

    private String generateQRCodeImage(String data) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        byte[] qrCodeBytes = byteArrayOutputStream.toByteArray();
        
        // Encode to Base64
        return Base64.getEncoder().encodeToString(qrCodeBytes);
    }

}