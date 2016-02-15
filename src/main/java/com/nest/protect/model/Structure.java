package com.nest.protect.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure {

    private String name;
    private Map<String, Protect> protectDevices = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static Structure parse(Map<String, Object> object, Map<String, Protect> protectDevices) {
        Structure structure = new Structure();
        Map<String, Where> wheres = parseWheres(object, structure);
        structure.name = (String)object.get("name");
        List<String> protects = (List<String>)object.get("smoke_co_alarms");
        if (protects != null) {
            for (String protectId : protects) {
                Protect protect = protectDevices.get(protectId);
                protect.setWhere(wheres.get(protect.getWhereId()));
                structure.protectDevices.put(protectId, protect);
            }
        }
        return structure;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Where> parseWheres(Map<String, Object> object, Structure structure) {
        Map<String, Where> parsed = new HashMap<>();
        Map<String, Object> wheres = (Map<String, Object>)object.get("wheres");
        if (wheres != null) {
            for (Object value : wheres.values()) {
                Where where = Where.parse((Map<String, String>)value);
                where.setStructure(structure);
                parsed.put(where.getId(), where);
            }
        }
        return parsed;
    }


    public Map<String, Protect> getProtectDevices() {
        return protectDevices;
    }

    public String getName() {
        return name;
    }

}
