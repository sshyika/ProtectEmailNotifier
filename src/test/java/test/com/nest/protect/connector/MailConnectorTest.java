package test.com.nest.protect.connector;

import com.nest.protect.Config;
import com.nest.protect.connector.MailConnector;
import org.junit.Test;

public class MailConnectorTest {

    @Test
    public void sendMail() {
        Config config = Config.load("config.properties");

        MailConnector mailConnector = MailConnector.build(config);

        mailConnector.send("ProtectEmailNotifier@gmail.com", "Test email", "This is email from MailConnectorTest");
    }

}
