package com.example.splashscreen;

import java.io.Serializable;

public class EventHelper implements Serializable {

    private String textDescription, textEventName, textEventDateStart, textEventDateEnd,
            textHostClub, textVenue, textEventTime;
    private String registeringUserInfo;

    public EventHelper(){ }

    public EventHelper(String registeringUserInfo, String textDescription, String textEventDateEnd, String textEventDateStart, String textEventName,
                       String textEventTime, String textHostClub, String textVenue) {
        this.textDescription = textDescription;
        this.textEventName = textEventName;
        this.textEventDateStart = textEventDateStart;
        this.textEventDateEnd = textEventDateEnd;
        this.textHostClub = textHostClub;
        this.textVenue = textVenue;
        this.textEventTime = textEventTime;
        this.registeringUserInfo = registeringUserInfo;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public String getTextEventName() {
        return textEventName;
    }

    public String getTextEventDateStart() {
        return textEventDateStart;
    }

    public String getTextEventDateEnd() {
        return textEventDateEnd;
    }

    public String getTextHostClub() {
        return textHostClub;
    }

    public String getTextVenue() {
        return textVenue;
    }

    public String getTextEventTime() {
        return textEventTime;
    }

    public String getRegisteringUserInfo() {
        return registeringUserInfo;
    }
}
