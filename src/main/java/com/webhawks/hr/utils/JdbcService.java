package com.webhawks.hr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class JdbcService extends JdbcAccessor {
    private static Logger log = LoggerFactory.getLogger(JdbcService.class);

    private JdbcTemplate jdbcTemplate;

    private PlatformTransactionManager transactionManager;

    public TransactionStatus beginTran() {

	if (transactionManager == null) {
	    log.warn("null txn manager");

	    return null;
	}

	TransactionDefinition txn = new DefaultTransactionDefinition();
	return transactionManager.getTransaction(txn);
    }

    public void commitTran(TransactionStatus status) {
	if (transactionManager == null) {
	    log.warn("null txn manager");
	    return;
	}
	transactionManager.commit(status);
    }

    public void rollbackTran(TransactionStatus status) {

	if (transactionManager == null) {
	    log.warn("null txn manager");
	    return;
	}

	transactionManager.rollback(status);
    }

    public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
	this.transactionManager = transactionManager;
    }

    public TransactionStatus getTransactionStatus() {
	TransactionDefinition def = new DefaultTransactionDefinition();
	return transactionManager.getTransaction(def);
    }
}
