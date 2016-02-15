package com.nest.protect;

import com.nest.protect.connector.MailConnector;
import com.nest.protect.connector.NestConnector;
import com.nest.protect.model.Level;
import com.nest.protect.model.Model;
import com.nest.protect.model.Protect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProtectEmailNotifier {
    private static final Logger LOG = LoggerFactory.getLogger(ProtectEmailNotifier.class);

    private Model model;

    public void run() throws Exception {
        final Config config = Config.load("config.properties");
        final NestConnector nest = NestConnector.build(config);
        final MailConnector mail = MailConnector.build(config);

        nest.addHomeStateListener(newModel -> {
            List<Change> changes = findUpdated(model, newModel);
            LOG.debug("Found {} updated protect devices", changes.size());

            for (Change change : changes) {
                mail.send(config.getUserMail(), "Nest Protect`s status updated", change.getMessage());
            }

            model = newModel;
        });
    }


    private List<Change> findUpdated(Model oldModel, Model newModel) {
        List<Change> updated = new ArrayList<>();
        if (oldModel != null) {
            Map<String, Protect> oldDevices = oldModel.getStructures().stream()
                    .flatMap(s -> s.getProtectDevices().values().stream())
                    .collect(Collectors.toMap(Protect::getId, Function.identity()));

            newModel.getStructures().stream()
                    .flatMap(s -> s.getProtectDevices().values().stream())
                    .map(p -> new Change(p, oldDevices.get(p.getId())))
                    .filter(c -> c.was != null && (c.was.getSmokeAlarm() != c.now.getSmokeAlarm() || c.was.getCoAlarm() != c.now.getCoAlarm()))
                    .forEach(updated::add);
        }
        return updated;
    }


    private class Change {
        Protect was;
        Protect now;

        Change(Protect now, Protect was) {
            this.now = now;
            this.was = was;
        }

        String getMessage() {
            return String.format("Your protect device located in the %s of your %s detected that:\n%s",
                    now.getWhere().getName(), now.getWhere().getStructure().getName(), getLevels());
        }

        String getLevels() {
            String result = "";

            String coLevel = (now.getCoAlarm() == Level.OK ? "normal" : (now.getCoAlarm() == Level.WARNING ? "high" : "critical"));
            String smokeLevel = (now.getSmokeAlarm() == Level.OK ? "normal" : (now.getSmokeAlarm() == Level.WARNING ? "high" : "critical"));

            if (was.getCoAlarm() != now.getCoAlarm()) {
                result += "CO level now is " + coLevel + ".\n";
            } else {
                result += "CO level still " + coLevel + ".\n";
            }
            if (was.getSmokeAlarm() != now.getSmokeAlarm()) {
                result += "Smoke level now is " + smokeLevel + ".\n";
            } else {
                result += "Smoke level still " + smokeLevel + ".\n";
            }
            return result;
        }

    }

}
