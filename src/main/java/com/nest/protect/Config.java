package com.nest.protect;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class Config {

    private String mailHost;
    private String mailPort;
    private String mailUserName;
    private String mailPassword;
    private String mailSender;

    private String nestUrl;
    private String nestToken;

    private String recipientEmail;

    private Config() {
    }


    public static Config load(String path) {
        try {

            Properties props = new Properties();
            props.load(new FileReader(new File(Config.class.getClassLoader().getResource(path).toURI())));

            Config config = new Config();
            config.mailHost = props.getProperty("mail.host");
            config.mailPort = props.getProperty("mail.port");
            config.mailUserName = props.getProperty("mail.username");
            config.mailPassword = props.getProperty("mail.password");
            config.mailSender = props.getProperty("mail.sender");
            config.nestUrl = props.getProperty("nest.url");
            config.nestToken = props.getProperty("nest.token");
            config.recipientEmail = props.getProperty("recipient.mail");

            return config;

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public String getMailHost() {
        return mailHost;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailPort() {
        return mailPort;
    }

    public String getMailSender() {
        return mailSender;
    }

    public String getMailUserName() {
        return mailUserName;
    }

    public String getNestUrl() {
        return nestUrl;
    }

    public String getNestToken() {
        return nestToken;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

}
