package com.printer.print.controller;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

    public static void main(String[] args) {
        // Email configuration
        String to = "pavananna234@gmail.com"; // recipient email
        String from = "apk.pavan.07@outlook.com"; // sender email
        String host = "smtp.office365.com"; // SMTP server

        // Set up properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // or 465 for SSL
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("apk.pavan.07@outlook.com", "./APK/paWan/@07"); // sender email and password
            }
        });

        try {
            // Create a MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Subject of the email");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Part one - the text message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This is the message body.");
            multipart.addBodyPart(messageBodyPart);

            // Part two - the attachment (if needed)
            // Uncomment and modify the following lines if you want to add an attachment
            
            messageBodyPart = new MimeBodyPart();
            String filename = "path/to/your/file.txt"; // Change to your file path
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("file.txt"); // The name that will be shown in the email
            multipart.addBodyPart(messageBodyPart);
            

            // Set the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}