package com.nest.protect;

import com.nest.protect.connector.MailConnector;
import com.nest.protect.connector.NestConnector;
import com.nest.protect.model.Model;
import com.nest.protect.model.Protect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProtectEmailNotifier {
    private static final Logger LOG = LoggerFactory.getLogger(ProtectEmailNotifier.class);

    private Model model;

    public void run() throws Exception {
        final Config config = Config.load("config.properties");
        final NestConnector nest = NestConnector.build(config);
        final MailConnector mail = MailConnector.build(config);

        nest.addHomeStateListener(newModel -> {
            List<Protect> updated = findUpdated(model, newModel);
            LOG.debug("Found {} updated protect devices", updated.size());

            for (Protect protect : updated) {
                mail.send(config.getUserMail(), "test", "test");
            }

            model = newModel;
        });
    }


    private List<Protect> findUpdated(Model oldModel, Model newModel) {
        List<Protect> updated = new ArrayList<>();
        if (oldModel != null) {
            Map<String, Protect> oldDevices = oldModel.getStructures().stream()
                    .flatMap(s -> s.getProtectDevices().values().stream())
                    .collect(Collectors.toMap(Protect::getId, Function.identity()));
            newModel.getStructures().stream()
                    .flatMap(s -> s.getProtectDevices().values().stream())
                    .filter(p -> {
                        Protect old = oldDevices.get(p.getId());
                        return (old != null && (old.getSmokeAlarm() != p.getSmokeAlarm() || old.getCoAlarm() != p.getCoAlarm()));
                    })
                    .forEach(updated::add);
        }
        return updated;
    }

}
