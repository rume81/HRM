package com.webhawks.hr.services.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.hr.model.UserSession;


public interface ISessionService {
    public UserSession insertSession(HttpServletRequest request,HEmployee user)  throws Exception;
	
    public boolean isSessionValid();

    public void invalidateSession(int sessionId);
	
    public UserSession getUserSession();
}
