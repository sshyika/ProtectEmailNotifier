package com.nest.protect.model;

import java.util.Map;

public class Where {
    private String id;
    private String name;
    private Structure structure;

    public static Where parse(Map<String, String> object) {
        Where where = new Where();
        where.id = object.get("where_id");
        where.name = object.get("name");
        return where;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}
