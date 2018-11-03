package emailcontroller.ssl;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Using to sent messages using ssl
 */
public class EmailSender {

    private String username;
    private String password;
    private Properties props;

    /**
     *  Constructor initialize Email settings
     * @param username admin's Email
     * @param password admin's password
     */
    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    /**
     * Sent message from admin to user
     * @param subject theme of message
     * @param text text of message
     * @param fromEmail admin's Email
     * @param toEmail user's Email
     */
    public void send(String subject, String text, String fromEmail, String toEmail){
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        initialization(subject, text, toEmail, session, fromEmail);
    }

    /**
     * Initialize email settings
     * @param subject theme of message
     * @param text text of message
     * @param toEmail user's Email
     * @param session Email settings
     * @param username admin's Email
     */
    public static void initialization(String subject, String text, String toEmail, Session session, String username) {
        try {
            Message message = new MimeMessage(session);
            //from whom
            message.setFrom(new InternetAddress(username));
            //to whom
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //theme of message
            message.setSubject(subject);
            //text of message
            message.setText(text);

            //sent letter
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
