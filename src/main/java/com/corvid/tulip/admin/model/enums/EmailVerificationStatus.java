package com.corvid.tulip.admin.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum EmailVerificationStatus {
    SENT("Sent"),//an email was sent in response to a verification initiate request
    RESENT("Resent"),//multiple emails were sent in response to verification initiate requests
    DELIVERED("Delivered"), //the email was successfully delivered to the user's mailbox (as reported by the remote server)
    BOUNCE("Bounce"),//an email previously sent was bounced back by the remote server and couldn't be delivered
    DEFERRED("Deferred"),//the mail system ran into transient problems delivering the email and deferred further attempts
    DROPPED("Dropped"),//the mail system dropped the email because of errors
    CONFIRMED("Confirmed");// the user opened the email and clicked on the link enclosed in the email

    private String name;

    EmailVerificationStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonCreator
    public static EmailVerificationStatus create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (EmailVerificationStatus v : values()) {
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