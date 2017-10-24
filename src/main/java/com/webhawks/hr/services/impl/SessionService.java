package com.webhawks.hr.services.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webhawks.hr.dao.interfaces.ISessionDAO;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.Session;
import com.webhawks.hr.model.UserSession;
import com.webhawks.hr.services.interfaces.ISessionService;

public class SessionService implements ISessionService {

    private final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private ISessionDAO sessionDao;

    private UserSession userSession;

    public void setSessionDao(ISessionDAO sessionDao) {
	this.sessionDao = sessionDao;
    }

    public void setUserSession(UserSession userSession) {
	this.userSession = userSession;
    }

    public UserSession getUserSession() {
	return userSession;
    }

    public UserSession insertSession(HttpServletRequest request, HEmployee user) throws Exception {
	if (!isSessionValid()) {
	    // sets session info
	    Session session = new Session();
	    session.setDeleted(false);
	    session.setModId(user.getAvator());
	    session.setId(-1);
	    session.setUser(user);
	    session.setReferrer(request.getHeader("referer"));
	    session.setBrowser(request.getHeader("user-agent"));
	    session.setSearchstring(request.getQueryString());

	    String requestUrl = request.getRequestURL().toString();
	    requestUrl = requestUrl.replaceAll("'", "''");
	    session.setLocation(requestUrl);
	    String ip = request.getRemoteAddr();
	    session.setClientIpAddress(ip);
	    Date date = new Date();

	    session.setSessionStart(date);
	    session.setSessionEnd(date);

	    // insert session info in DB
	    int sessionId = sessionDao.insertSession(session);

	    // update current web session
	    userSession.setSessionId(sessionId);
	    userSession.setClientIpAddress(ip);
	    userSession.setUser(user);
	} // session not set

	logger.info("User Session Id - > " + userSession.getSessionId());
	return userSession;
    }

    public void setUser(HEmployee user) {
	userSession.setUser(user);
    }

    public boolean isSessionValid() {
	return (userSession.getSessionId() > 0 ? true : false);
    }

    public void invalidateSession(int sessionId) {
	userSession.setSessionId(0);
    }

}
