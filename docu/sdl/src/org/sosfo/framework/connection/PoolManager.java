package org.sosfo.framework.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.exception.PropNotFoundException;
import org.sosfo.framework.log.Log;

/**
 * <pre>
 *      Title: ����Ÿ ���̽� Ŀ�ؼ� ����
 *      Description: �ϳ��� Ŀ�ؼ��� �����Ѵ�.
 *      Copyright: Copyright (c) www.sosfo.or.kr  All Rights Reserved.
 *      Company: www.cyclerace.or.kr
 * </pre>
 * 
 * @author yunkidon@sosfo.or.kr
 * @version 1.0
 */

public class PoolManager {

	private static PoolManager	instance			= null;

	/**
	 * Properties ��ü
	 */
	private static Config		conf				= null;

	/**
	 * �⺻ ����Ÿ�ҽ�
	 */
	private static String		DEFAULT_Connection	= "jdbc/cra";

	static {
		try {
			conf = ConfigFactory.getInstance().getConfiguration("db2.properties");
		} catch (PropNotFoundException pe) {
			System.err.println("Can't read the properties file. Make sure db.properties is in the CLASSPATH");
		} catch (Exception e) {
			System.err.println("find db.properties. but other problems are here.");
		}
	}

	/**
	 * ��Ŭ�� ����
	 * 
	 * @see getInstatce()
	 */
	private PoolManager() {}

	/**
	 * Double Check Lock ����
	 * 
	 * @return PoolManager
	 */
	public static PoolManager getInstance() {
		if (instance == null) {
			synchronized (PoolManager.class) {
				if (instance == null) {
					instance = new PoolManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Ŀ�ؼ� �ݱ�
	 * 
	 * @param s String
	 * @param con Connection
	 */
	public void freeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception ee) {}

	}

	/**
	 * �⺻ ����Ŭ Ŀ�ؼ��� ���� �ۼ� ��¥: (2002-07-10 15:43:40)
	 * 
	 * @return java.sql.Connection
	 * @exception java.sql.SQLException ���� ����.
	 */
	public Connection getConnection() throws SQLException, Exception {

		return getConnection(DEFAULT_Connection);

	}

	/**
	 * Ŀ�ؼ� ��������(training) db.properties�� spdm.driver �� ��� spdm�� �μ��� ����Ѵ�.
	 * 
	 * @return Connection
	 */
	public Connection getConnection(String conName) throws SQLException, Exception {
		Connection conn = null;
		String url = conf.getString(conName + ".url");
		try {
			Class.forName(conf.getString(conName + ".driver"));
			return DriverManager.getConnection(url, conf.getString(conName + ".user"), conf.getString(conName + ".password"));
		} catch (SQLException e) {
			Log.error("ERROR", this, e);
			e.printStackTrace();
			throw e;
		} catch (Exception ex) {
			Log.error("ERROR", this, ex);
			throw ex;
		}
	}
}
