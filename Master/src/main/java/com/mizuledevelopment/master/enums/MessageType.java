package com.mizuledevelopment.master.enums;

public enum MessageType {

    ADD("ADD");

    private final String type;

    MessageType(String s) { type = s; }

    public boolean equals(String name) { return type.equals(name); }

    public String toString() { return this.type; }

}
