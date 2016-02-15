package test.com.nest.protect;

import com.nest.protect.Config;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    @Test
    public void configLoad() {
        Config config = Config.load("test-config.properties");

        assertEquals("host", config.getMailHost());
        assertEquals("port", config.getMailPort());
        assertEquals("username", config.getMailUserName());
        assertEquals("password", config.getMailPassword());
        assertEquals("sender", config.getMailSender());
        assertEquals("nestUrl", config.getNestUrl());
        assertEquals("nestToken", config.getNestToken());
    }

}
