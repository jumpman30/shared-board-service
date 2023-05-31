package com.lapr4.sharedboardservice.session;

public class UserSession {

    private boolean isAuthenticated;

    public UserSession() {
        this.isAuthenticated = false;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void authenticate() {
        isAuthenticated = true;
    }

    public void endSession(){
        isAuthenticated = false;
    }
}
