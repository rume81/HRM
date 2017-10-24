package com.webhawks.hr.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.webhawks.hr.utils.JdbcService;

public class BaseDAO {
    private JdbcService jdbcService;

    public JdbcService getJdbcService() {
	return jdbcService;
    }

    public void setJdbcService(JdbcService jdbcService) {
	this.jdbcService = jdbcService;
    }

    /*
     * protected String getCommonArgs() {
     * 
     * return "deleted = 0, modifierid = 1"; }
     */
}
