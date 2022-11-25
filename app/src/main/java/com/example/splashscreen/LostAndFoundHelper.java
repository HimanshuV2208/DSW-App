package com.example.splashscreen;

import java.io.Serializable;

public class LostAndFoundHelper implements Serializable {
    private String itemName, infoType, itemDate, itemLocation, itemDescription;
    private String registeringUserInfo;

    public LostAndFoundHelper(){
    }

    public LostAndFoundHelper(String itemName, String infoType, String itemDate, String itemLocation, String itemDescription, String userInfo) {
        this.itemName = itemName;
        this.infoType = infoType;
        this.itemDate = itemDate;
        this.itemLocation = itemLocation;
        this.itemDescription = itemDescription;
        this.registeringUserInfo = userInfo;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInfoType() {
        return infoType;
    }

    public String getItemDate() {
        return itemDate;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getRegisteringUserInfo() {
        return registeringUserInfo;
    }

}
