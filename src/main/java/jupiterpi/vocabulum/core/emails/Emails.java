package jupiterpi.vocabulum.core.emails;

import jupiterpi.vocabulum.core.util.TextFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

public class Emails {
    private static Emails instance = null;
    public static Emails get() {
        if (instance == null) {
            instance = new Emails();
        }
        return instance;
    }

    // ----------

    private final String emailDir = "./emails";

    private List<String> getConfig() {
        return TextFile.readLines(emailDir + "/config.txt");
    }
    // properties in order: 0) sender email, 1) sender password, 2) verification email subject

    private String getVerificationHTML() {
        return TextFile.readFile(emailDir + "/verification_email.html");
    }

    public void sendVerificationEmail(String recipient) throws MessagingException {
        List<String> config = getConfig();
        String username = config.get(0);
        String password = config.get(1);
        String subject = config.get(2);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp-mail.outlook.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(getVerificationHTML(), "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}