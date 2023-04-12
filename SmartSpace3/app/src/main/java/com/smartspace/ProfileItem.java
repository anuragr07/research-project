package com.smartspace;

public class ProfileItem {
    private String userName;
    private String readings;

    public ProfileItem(String userName, String readings) {
        this.userName = userName;
        this.readings = readings;
    }

    public String getUserName() {
        return userName;
    }

    public String getReadings() {
        return readings;
    }
}

