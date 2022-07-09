package services;

import java.io.FileReader;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendEmail {

    String senderEmail;
    String senderPassword;
    final String emailSMTPserver = "smtp.gmail.com";
    final String emailServerPort = "465";
    String receiverEmail = null;
    static String emailSubject;
    static String emailBody;

    public SendEmail(String email, String subject, String body) {
        receiverEmail = email;
        emailSubject = subject;
        emailBody = body;
        FileReader reader;
        Properties properties;
        try {
            reader = new FileReader("properties.properties");
            properties = new Properties();
            properties.load(reader);
            senderEmail = properties.getProperty("emailAddress");
            senderPassword = properties.getProperty("emailPassword");
        } catch (IOException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }

        Properties props = new Properties();
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.host", emailSMTPserver);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(emailBody);
            msg.setSubject(emailSubject);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    }

}
