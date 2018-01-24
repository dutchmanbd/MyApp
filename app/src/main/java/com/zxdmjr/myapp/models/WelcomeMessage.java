package com.zxdmjr.myapp.models;

/**
 * Created by dutchman on 1/24/18.
 */

public class WelcomeMessage {

    private int icon;
    private String message;

    public WelcomeMessage(int icon, String message) {
        this.icon = icon;
        this.message = message;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
