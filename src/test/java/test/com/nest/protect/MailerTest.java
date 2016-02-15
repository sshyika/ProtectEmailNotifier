package test.com.nest.protect;

import com.nest.protect.Config;
import com.nest.protect.Mailer;
import org.junit.Test;

public class MailerTest {

    @Test
    public void sendMail() {
        Config config = Config.load("config.properties");

        Mailer mailer = Mailer.build(config);

        mailer.send("ProtectEmailNotifier@gmail.com", "Test email", "This is email from MailerTest");
    }

}
