package com.example.splashscreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(ArrayList<EventHelper> events) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        for(EventHelper event : events) {
            List<String> aboutEvent = new ArrayList<String>();
            String eventName = event.getTextEventName();
            aboutEvent.add("Host Club : " + event.getTextHostClub());
            aboutEvent.add("Starts on : " + event.getTextEventDateStart());
            aboutEvent.add("Ends on : " + event.getTextEventDateEnd());
            aboutEvent.add("Starts at : " + event.getTextEventTime());
            aboutEvent.add("Venue : " + event.getTextVenue());
            aboutEvent.add("Description : " + event.getTextDescription());
            expandableListDetail.put(eventName, aboutEvent);
        }
        return expandableListDetail;
    }
}