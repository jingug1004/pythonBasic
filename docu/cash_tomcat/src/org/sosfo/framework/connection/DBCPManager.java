package org.sosfo.framework.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.dbcp.BasicDataSource;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;

/**
 * 
 * <pre>
 * Title: DBCP  
 * Description: db.properties
 * Copyright: www.cyclerace.or.kr Copyright (c) 2008
 * Company: 
 * </pre>
 * 
 * @author yunkidon@hotmail.com
 * @version 1.1
 */
public class DBCPManager {

    /**
     * Properties 
     */
    private static Config conf = null;

    static {
		try {
		    conf = ConfigFactory.getInstance().getConfiguration("db.properties");
		} catch (PropNotFoundException pe) {
		    System.err.println("Can't read the properties file. Make sure db.properties is in the CLASSPATH");
		} catch (Exception e) {
		    System.err.println("find db.properties. but other problems are here.");
		}
    }

    /**
     * use DBManager.getInstance()
     */
    private static DBCPManager instance = null;

    private static HashMap DataSource_MAP = new HashMap();

    /**
     * use getInstance();
     */
    private DBCPManager() {
    	loadDrivers((Iterator) conf.getKeys());
    }

    /**
     * Double Check Lock 
     * 
     * @return PoolManager
     */
    public static DBCPManager getInstance() {
		if (instance == null) {
		    synchronized (DBCPManager.class) {
				if (instance == null) {
				    instance = new DBCPManager();
				}
		    }
		}
		
		return instance;
    }

    /**
     * db.properties---> oracledb
     * 
     * @param connName String
     * @return Connection
     * @throws SQLException
     * @see db.properties
     */
    public Connection getConnection(String connName) throws SQLException {
		Connection conn = null;
		
		try {
		    conn = ((BasicDataSource) DataSource_MAP.get(connName)).getConnection();
		} catch (SQLException ex) {
		    Log.error("ERROR", this, ex);
		    ex.printStackTrace();
		    
		    throw ex;
		} catch (Exception ex) {
		    System.out.print("\n error occur at DBCPManager.getInstance().getConnection()");
		    ex.printStackTrace();
		    Log.error("ERROR", this, ex);
		}
		
		return conn;
    }

    /**
     * 
     * 
     * @param keys Iterator
     */
    private void loadDrivers(Iterator keys) {
    	
		BasicDataSource bds 		= null;
		String name 				= null;
		String pool 				= null;
		String driver 				= null;
		String url 					= null;
		String user 				= null;
		String password 			= null;
	
		int maxActive 				= 0;
		int maxIdle 				= 0;
		int maxWait 				= -1;
		int removeAbandonedTimeout 	= 0;
	
		boolean removeAbandoned 	= false;
		boolean logAbandoned 		= false;
	
		boolean defaultAutoCommit 	= false;
		boolean defaultReadOnly 	= false;
	
		while (keys.hasNext()) {
			
		    name = (String) keys.next();
		    
		    if (name.endsWith(".driver")) {
				try {
				    pool = name.substring(0, name.lastIndexOf("."));
				    driver = conf.getString(pool + ".driver");
				    url = conf.getString(pool + ".url");
				    user = conf.getString(pool + ".user");
				    password = conf.getString(pool + ".password");
		
				    maxActive = Integer.parseInt(conf.getString(pool + ".maxActive"));
				    maxIdle = Integer.parseInt(conf.getString(pool + ".maxIdle"));
				    maxWait = Integer.parseInt(conf.getString(pool + ".maxWait"));
				    removeAbandonedTimeout = Integer.parseInt(conf.getString(pool + ".removeAbandonedTimeout"));
		
				    removeAbandoned = conf.getString(pool + ".removeAbandoned").equals("true");
				    logAbandoned = conf.getString(pool + ".logAbandoned").equals("true");
		
				    defaultAutoCommit = conf.getString(pool + ".defaultAutoCommit").equals("true");
				    defaultReadOnly = conf.getString(pool + ".defaultReadOnly").equals("true");
		
				    Log.info("", this, "\n pool:" + pool + " driver:" + driver + " url:" + url + " password:" + password + " maxActive:" + maxActive + " maxWait:" + maxWait + " maxIdle:" + maxIdle + " removeAbandonedTimeout:" + removeAbandonedTimeout + " removeAbandoned:" + removeAbandoned + " logAbandoned:" + logAbandoned + " defaultAutoCommit:" + defaultAutoCommit + " defaultReadOnly:"
					    + defaultReadOnly);
		
				    if (url == null) {
						Log.error("ERROR", this, "Can't initialize pool : confirm url in db.properties.");
						continue;
				    }
		
				    bds = new BasicDataSource();
		
				    bds.setDriverClassName(driver);
				    bds.setUrl(url);
				    bds.setUsername(user);
				    bds.setPassword(password);
				    bds.setMaxActive(maxActive);
		
				    bds.setMaxIdle(maxIdle);
				    bds.setMaxWait(maxWait);
		
				    bds.setRemoveAbandonedTimeout(removeAbandonedTimeout);
				    bds.setRemoveAbandoned(removeAbandoned);
				    bds.setLogAbandoned(logAbandoned);
		
				    bds.setDefaultAutoCommit(defaultAutoCommit);
				    bds.setDefaultReadOnly(defaultReadOnly);
		
				    createPools(pool, bds);
				    Log.debug("", this, "Initialized pool : " + pool);
				} catch (Exception e) {
				    Log.error("ERROR", this, "Can't initialize loadDrivers : " + pool + "\n" + e);
				}
		    }
		}
    }

    private void createPools(String poolName, BasicDataSource bds) throws Exception {
		try {
		    DataSource_MAP.put(poolName, bds);
		    toString(poolName);
		} catch (Exception e) {
		    Log.error("ERROR", this, "Can't initialize DBCP : " + poolName + "\n" + e);
		    throw e;
		}
    }

    /**
     * view pool of properties
     * 
     * @param pool String
     * @throws Exception
     */
    public String toString(String poolName) {
		try {
		    BasicDataSource trace_bds = (BasicDataSource) DataSource_MAP.get(poolName);
		    Log.info("", this, "\n pool name:" + poolName + "\n driver:" + trace_bds.getDriverClassName() + "\n url:" + trace_bds.getUrl() + "\n user:" + trace_bds.getUsername() + "\n password:" + trace_bds.getPassword() + "\n maxActive:" + trace_bds.getMaxActive() + "\n maxWait:" + trace_bds.getMaxWait() + "\n maxIdle:" + trace_bds.getMaxIdle() + "\n removeAbandonedTimeout:"
			    + trace_bds.getRemoveAbandonedTimeout() + "\n removeAbandoned:" + trace_bds.getRemoveAbandoned() + "\n logAbandoned:" + trace_bds.getLogAbandoned() + "\n defaultAutoCommit:" + trace_bds.getDefaultAutoCommit() + "\n defaultReadOnly:" + trace_bds.getDefaultReadOnly());
		} catch (Exception e) {
		    // TODO: handle exception
		}
		return "";
    }
    
}
