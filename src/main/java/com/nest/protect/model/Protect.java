package com.nest.protect.model;

import java.util.Map;

public class Protect {

    private String id;
    private String name;
    private String locale;
    private Level coAlarm;
    private Level smokeAlarm;
    private String whereId;
    private Where where;


    public static Protect parse(Map<String, String> object) {
        Protect protect = new Protect();
        protect.id = object.get("device_id");
        protect.name = object.get("name_long");
        protect.locale = object.get("locale");
        protect.coAlarm = Level.parse(object.get("co_alarm_state"));
        protect.smokeAlarm = Level.parse(object.get("smoke_alarm_state"));
        protect.whereId = object.get("where_id");
        return protect;
    }


    public Level getCoAlarm() {
        return coAlarm;
    }

    public String getId() {
        return id;
    }

    public String getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public Level getSmokeAlarm() {
        return smokeAlarm;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public String getWhereId() {
        return whereId;
    }
}
