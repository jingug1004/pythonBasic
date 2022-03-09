package org.sosfo.framework.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import org.sosfo.framework.config.Config;
import org.sosfo.framework.config.ConfigFactory;
import org.sosfo.framework.connection.DBCPManager;
import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.Tray;

public abstract class BaseBean implements Business {

	/**
	 * default 커넥션
	 */
	private String			defalut_connection	= null;

	/**
	 * Properties 객체
	 */
	private static Config	conf				= null;

	public BaseBean() {
		try {
			conf = ConfigFactory.getInstance().getConfiguration("db.properties");
			defalut_connection = conf.getString("sdl.default.datasource");
		} catch (Exception e) {
			System.err.print(e);
		}
	}

	public Tray createTray() throws AppException {
		Connection conn = null;
		Tray tray = null;
		try {
			conn = getConnection(defalut_connection);
			tray = createTray(conn);
			return tray;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.createTray()에서 일반 예외 발생", ex);
		} finally {
			returnConnection(conn);
		}
	}

	/**
	 * 이 메소드는 빈 Tray를 생성하여 insert(pTray)를 호출한다.
	 * 
	 * @param conn: Database Connection
	 * @return Tray: 텅 빈 Map 기반의 래퍼 클래스
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected Tray createTray(Connection conn) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	/**
	 * 각 비지니스별 Trace한다.
	 * 
	 * @param start long
	 * @param end long
	 * @return long
	 */
	protected long currentTimeMillis(long start, long end) {
		return (end - start);
	}

	/**
	 * 이 메소드는 데이터 소스에 레코드를 삭제하는 일을 수행한다.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @return Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected boolean delete(Connection conn, Tray pTray) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public boolean delete(Tray reqTray) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;

		Connection conn = null;
		Tray tray = null;
		try {
			conn = getConnection(defalut_connection);
			return delete(conn, reqTray);
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.delete(Tray reqTray)에서 일반 예외 발생", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * 이 메소드는 데이터 소스에서 다건의 데이터를 검색하여 리턴한다.
	 * 
	 * @param conn: Database Connection
	 * @param reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @return Tray: ResultSetTray 객체 - primary key가 아닌 다른 조건으로 찾게 되는 결과셋
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected Tray find(Connection conn, Tray reqTray) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public Tray find(Tray reqTray) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;
		Connection conn = null;
		Tray rsTray;
		try {
			Log.debug("", this, "BaseBean 에서 find 메소드 호출 시작");
			conn = this.getConnection(defalut_connection);
			Log.debug("", this, "connection 객체 하나를 얻어 온다. : " + conn);
			rsTray = find(conn, reqTray);
			return rsTray;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.find(Tray reqTray)에서 일반 예외 발생", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * 이 메소드는 데이터 소스에서 검색한 여러 결과셋을 리턴한다.
	 * 
	 * @param conn: Database Connection
	 * @param reqTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @return Collection: ResultSetTray 객체의 모음
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected Collection findAll(Connection conn, Tray reqTray) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public Collection findAll(Tray reqTray) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;
		Connection conn = null;
		Collection trayCollection;
		try {
			conn = getConnection(defalut_connection);
			trayCollection = findAll(conn, reqTray);
			return trayCollection;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.find(Tray reqTray)에서 일반 예외 발생", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * 이 메소드는 데이터 소스에서 레코드를 검색하는 일을 수행한다.
	 * 
	 * @param conn: Database Connection
	 * @param pPrimaryKey: 데이터 소스에서 레코드를 구별하는 유일한 ID 값
	 * @return Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected Tray findByPrimaryKey(Connection conn, Tray reqTrayPrimaryKey) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public Tray findByPrimaryKey(Tray reqTrayPrimaryKey) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;

		Connection conn = null;
		Tray tray = null;
		try {
			conn = getConnection(defalut_connection);
			tray = findByPrimaryKey(conn, reqTrayPrimaryKey);
			return tray;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.findByPrimaryKey(Object pPrimaryKey)에서 일반 예외 발생", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * 기본적인 DB 드라이버를 로딩하고 connection 객체를 반환한다.
	 * 
	 * @return Connection
	 */
	protected Connection getConnection(String defalut_connection) throws Exception {
		Log.info("", this, " create connection ");
		return DBCPManager.getInstance().getConnection(defalut_connection);
	}

	/**
	 * 이 메소드는 데이터 소스에 레코드를 삽입하는 일을 수행한다.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @return Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected boolean insert(Connection conn, Tray pTray) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public boolean insert(Tray reqTray) throws AppException {

		long start = System.currentTimeMillis();
		long end = 0;

		Connection conn = null;
		Tray tray = null;
		try {
			conn = getConnection(defalut_connection);
			return insert(conn, reqTray);
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.insert(Tray reqTray)에서 일반 예외 발생", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	protected void returnConnection(Connection conn) throws AppException {
		if (conn != null) {
			try {
				/*
				 * 닫는 책임이 상위 클래스에 있는데 하위 클래스에서 닫았다면? if (conn.isClosed()) { }
				 */
				Log.info("", this, "close connection ");
				conn.close();
			} catch (SQLException ex) {
				throw new AppException("BaseBean.returnConnection(Connection conn)에서 Connection.close() 예외 발생", ex);
			}
		}
	}

	/**
	 * <pre>
	 *  db.properties의 커넥션을 설정하기 바람
	 *  설정하지 않는다면 base.properties의 기본 커넥션을 수행함.
	 * </pre>
	 * 
	 * @param java.lang.String - connectionName
	 */
	protected void setConnectionName(String connectionName) {
		this.defalut_connection = connectionName;
	}

	/**
	 * 이 메소드는 데이터 소스에 레코드를 수정하는 일을 수행한다.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: 사용자가 화면에서 입력한 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @return Tray: 데이터 소스에서 검색된 데이터를 Map 기반으로 포장한 래퍼 클래스
	 * @exception <code>java.rmi.RemoteException</code>: 리모트 호출 예외
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: 비즈니스 로직 수행상 발생하는 예외 등.
	 */
	protected boolean update(Connection conn, Tray pTray) throws AppException {
		throw new AppException("하위 클래스에서 오버라이드하여 작성하여야 합니다.");
	}

	public boolean update(Tray reqTray) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;

		Connection conn = null;
		Tray tray = null;
		try {
			conn = getConnection(defalut_connection);
			return update(conn, reqTray);
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.update(Tray reqTray)에서 일반 예외 발생", ex);
		} catch (Throwable t) {
			throw new AppException("BaseBean.update(Tray reqTray)에서 throwable 발생", t);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}
}
