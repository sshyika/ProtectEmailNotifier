package com.nest.protect;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {

    private String sender;
    private Session session;

    private Mailer(String sender, Session session) {
        this.sender = sender;
        this.session = session;
    }

    public static Mailer build(Config config) {
        Properties props = new Properties();
        props.put("mail.smtp.host", config.getMailHost());
        props.put("mail.smtp.port", config.getMailPort());
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", config.getMailPort());
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");


        return new Mailer(
                config.getMailSender(),
                Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getMailUserName(), config.getMailPassword());
                    }
                }));
    }


    public void send(String to, String subj, String body) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subj);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
