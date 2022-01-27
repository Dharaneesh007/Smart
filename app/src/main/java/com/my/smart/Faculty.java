package com.my.smart;

public class Faculty {
    String id, Name;

    public Faculty(String id, String name) {
        this.id = id;
        Name = name;
    }

    public Faculty() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
