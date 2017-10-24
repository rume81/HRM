package com.webhawks.hr.model;

import com.webhawks.Hawks_model.HEmployee;

public class UserSession {
    private int sessionId;
    private String clientIpAddress;
    private HEmployee selectedProfile;
    
    protected HEmployee user = null;

    public int getSessionId() {
	return sessionId;
    }

    public void setSessionId(int sessionId) {
	this.sessionId = sessionId;
    }

    public String getClientIpAddress() {
	return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
	this.clientIpAddress = clientIpAddress;
    }

    public HEmployee getUser() {
	return user;
    }

    public void setUser(HEmployee user) {
	this.user = user;
    }

    public HEmployee getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(HEmployee selectedProfile) {
        this.selectedProfile = selectedProfile;
    }   
    
}
