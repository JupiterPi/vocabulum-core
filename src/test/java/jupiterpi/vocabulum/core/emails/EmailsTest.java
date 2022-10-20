package jupiterpi.vocabulum.core.emails;

import javax.mail.MessagingException;

class EmailsTest {
    public static void main(String[] args) throws MessagingException {
        Emails.get().sendVerificationEmail("jupiterpi.ddns@gmail.com");
    }
}