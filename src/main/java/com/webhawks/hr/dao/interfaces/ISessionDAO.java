package com.webhawks.hr.dao.interfaces;

import com.webhawks.Hawks_model.Session;

public interface ISessionDAO {
	
	public int getSessionLastId();
	
	public int insertSession(Session session)  throws Exception;
}
