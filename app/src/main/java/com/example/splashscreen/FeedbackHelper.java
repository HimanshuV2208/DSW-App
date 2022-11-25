package com.example.splashscreen;

import java.io.Serializable;

public class FeedbackHelper implements Serializable {

    private String subject, type, description, registeringUser;

    public FeedbackHelper() {
    }

    public FeedbackHelper(String subject, String type, String description, String registeringUser) {
        this.subject = subject;
        this.type = type;
        this.description = description;
        this.registeringUser = registeringUser;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getRegisteringUser() {
        return registeringUser;
    }
}
