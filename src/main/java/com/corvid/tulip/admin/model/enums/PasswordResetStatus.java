package com.corvid.tulip.admin.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

 /*******************************
  password reset
 */
public enum PasswordResetStatus {
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private String name;

    PasswordResetStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonCreator
    public static PasswordResetStatus create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (PasswordResetStatus v : values()) {
            if (value.equals(v.getName())) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }
}