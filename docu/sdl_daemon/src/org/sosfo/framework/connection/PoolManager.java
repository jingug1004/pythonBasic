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
 *      Title: 데이타 베이스 커넥션 관리
 *      Description: 하나씩 커넥션을 생성한다.
 *      Copyright: Copyright (c) www.sosfo.or.kr  All Rights Reserved.
 *      Company: www.cyclerace.or.kr
 * </pre>
 * 
 * @author yunkidon@sosfo.or.kr
 * @version 1.0
 */
public class PoolManager {

    private static PoolManager instance = null;

    /**
     * Properties 객체
     */
    private static Config conf = null;

    /**
     * 기본 데이타소스
     */
    private static String DEFAULT_Connection = "SPDM";

    static {
		try {
		    conf = ConfigFactory.getInstance().getConfiguration("db2.properties");
		} catch (PropNotFoundException pe) {
		    System.err.println("Can't read the properties file. Make sure db2.properties is in the CLASSPATH");
		} catch (Exception e) {
		    System.err.println("find db.properties. but other problems are here.");
		}
    }

    /**
     * 싱클톤 패턴
     * 
     * @see getInstatce()
     */
    private PoolManager() {
    }

    /**
     * Double Check Lock 구현
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
     * 커넥션 닫기
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
     * 기본 오라클 커넥션을 생성 작성 날짜: (2002-07-10 15:43:40)
     * 
     * @return java.sql.Connection
     * @exception java.sql.SQLException 예외 설명.
     */
    public Connection getConnection() throws SQLException, Exception {
    	return getConnection(DEFAULT_Connection);
    }

    /**
     * 커넥션 가져오기(training) db.properties의 spdm.driver 일 경우 spdm을 인수로 사용한다.
     * 
     * @return Connection
     */
    public Connection getConnection(String conName) throws SQLException, Exception {
		Connection conn = null;
		String url = conf.getString(conName + ".url");
		try {
		    Class.forName(conf.getString(conName + ".driver"));
		    System.out.println(conf.getString(conName + ".driver"));
		    System.out.println(conf.getString(conName + ".user")+ conf.getString(conName + ".password"));
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
