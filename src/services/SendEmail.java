package services;

import java.io.FileReader;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//not bad naming but if this hosts all the sending of all emails its better to naming it something like EmailSendingManager if its user specific name it as such
public class SendEmail {

    String senderEmail;
    String senderPassword;//can be in an object
    final String emailSMTPserver = "smtp.gmail.com";
    final String emailServerPort = "465";//can be in an object or a config
    String receiverEmail = null;//can be in an object or config
    static String emailSubject;//can be in an object
    static String emailBody;//can be in an object

    
    /*
    So this method is not bad at all its a good method depending on the use case but the naming could be checked if its specific for users then name 
    it as such , if its a generic method for sending all emails its a good idea to name it in a way or write a comment to share that
    
    In the method nothing is wrong with it but it can be abstracted a bit for scalabilty and ease of use. Java 101 says 1 mehod does 1 thing, 1 class does 1 thing 
    try to always use this principle for the future. 
    */
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
