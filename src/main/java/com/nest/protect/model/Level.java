package com.nest.protect.model;

public enum Level {
    OK("ok"),
    WARNING("warning"),
    EMERGENCY("emergency");

    Level(String code) {
        this.code = code;
    }

    private String code;


    public static Level parse(String text) {
        for (Level level : Level.values()) {
            if (level.code.equalsIgnoreCase(text)) {
                return level;
            }
        }
        return null;
    }

}
