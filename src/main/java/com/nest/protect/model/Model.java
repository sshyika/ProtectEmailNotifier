package com.nest.protect.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private List<Structure> structures;


    public static Model parse(Map<String, Object> object) {
        Model model = new Model();
        Map<String, Protect> protectDevices = parseProtectDevices(object);
        model.structures = parseStructures(object, protectDevices);
        return model;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Protect> parseProtectDevices(Map<String, Object> object) {
        Map<String, Protect> protectDevices = new HashMap<>();
        Map<String, Object> devices = (Map<String, Object>)object.get("devices");
        if (devices != null) {
            Map<String, Object> protects = (Map<String, Object>)devices.get("smoke_co_alarms");
            if (protects != null) {
                for (Object value : protects.values()) {
                    Protect protect = Protect.parse((Map<String, String>)value);
                    protectDevices.put(protect.getId(), protect);
                }
            }
        }
        return protectDevices;
    }

    @SuppressWarnings("unchecked")
    private static List<Structure> parseStructures(Map<String, Object> object, Map<String, Protect> protectDevices) {
        List<Structure> structures = new ArrayList<>();
        Map<String, Object> structs = (Map<String, Object>)object.get("structures");
        if (structs != null) {
            for (Object value : structs.values()) {
               structures.add(Structure.parse((Map<String, Object>) value, protectDevices));
            }
        }
        return structures;
    }


    public List<Structure> getStructures() {
        return structures;
    }

}
