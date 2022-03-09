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
	 * default Ŀ�ؼ�
	 */
	private String			defalut_connection	= null;

	/**
	 * Properties ��ü
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
			throw new AppException("BaseBean.createTray()���� �Ϲ� ���� �߻�", ex);
		} finally {
			returnConnection(conn);
		}
	}

	/**
	 * �� �޼ҵ�� �� Tray�� �����Ͽ� insert(pTray)�� ȣ���Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @return Tray: �� �� Map ����� ���� Ŭ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected Tray createTray(Connection conn) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
	}

	/**
	 * �� �����Ͻ��� Trace�Ѵ�.
	 * 
	 * @param start long
	 * @param end long
	 * @return long
	 */
	protected long currentTimeMillis(long start, long end) {
		return (end - start);
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
	 * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected boolean delete(Connection conn, Tray pTray) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
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
			throw new AppException("BaseBean.delete(Tray reqTray)���� �Ϲ� ���� �߻�", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ����� �ٰ��� �����͸� �˻��Ͽ� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
	 * @return Tray: ResultSetTray ��ü - primary key�� �ƴ� �ٸ� �������� ã�� �Ǵ� �����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected Tray find(Connection conn, Tray reqTray) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
	}

	public Tray find(Tray reqTray) throws AppException {
		long start = System.currentTimeMillis();
		long end = 0;
		Connection conn = null;
		Tray rsTray;
		try {
			Log.debug("", this, "BaseBean ���� find �޼ҵ� ȣ�� ����");
			conn = this.getConnection(defalut_connection);
			Log.debug("", this, "connection ��ü �ϳ��� ��� �´�. : " + conn);
			rsTray = find(conn, reqTray);
			return rsTray;
		} catch (AppException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new AppException("BaseBean.find(Tray reqTray)���� �Ϲ� ���� �߻�", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ����� �˻��� ���� ������� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param reqTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
	 * @return Collection: ResultSetTray ��ü�� ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected Collection findAll(Connection conn, Tray reqTray) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
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
			throw new AppException("BaseBean.find(Tray reqTray)���� �Ϲ� ���� �߻�", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ����� ���ڵ带 �˻��ϴ� ���� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param pPrimaryKey: ������ �ҽ����� ���ڵ带 �����ϴ� ������ ID ��
	 * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected Tray findByPrimaryKey(Connection conn, Tray reqTrayPrimaryKey) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
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
			throw new AppException("BaseBean.findByPrimaryKey(Object pPrimaryKey)���� �Ϲ� ���� �߻�", ex);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}

	/**
	 * �⺻���� DB ����̹��� �ε��ϰ� connection ��ü�� ��ȯ�Ѵ�.
	 * 
	 * @return Connection
	 */
	protected Connection getConnection(String defalut_connection) throws Exception {
		Log.info("", this, " create connection ");
		return DBCPManager.getInstance().getConnection(defalut_connection);
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
	 * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected boolean insert(Connection conn, Tray pTray) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
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
			throw new AppException("BaseBean.insert(Tray reqTray)���� �Ϲ� ���� �߻�", ex);
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
				 * �ݴ� å���� ���� Ŭ������ �ִµ� ���� Ŭ�������� �ݾҴٸ�? if (conn.isClosed()) { }
				 */
				Log.info("", this, "close connection ");
				conn.close();
			} catch (SQLException ex) {
				throw new AppException("BaseBean.returnConnection(Connection conn)���� Connection.close() ���� �߻�", ex);
			}
		}
	}

	/**
	 * <pre>
	 *  db.properties�� Ŀ�ؼ��� �����ϱ� �ٶ�
	 *  �������� �ʴ´ٸ� base.properties�� �⺻ Ŀ�ؼ��� ������.
	 * </pre>
	 * 
	 * @param java.lang.String - connectionName
	 */
	protected void setConnectionName(String connectionName) {
		this.defalut_connection = connectionName;
	}

	/**
	 * �� �޼ҵ�� ������ �ҽ��� ���ڵ带 �����ϴ� ���� �����Ѵ�.
	 * 
	 * @param conn: Database Connection
	 * @param pTray: ����ڰ� ȭ�鿡�� �Է��� �����͸� Map ������� ������ ���� Ŭ����
	 * @return Tray: ������ �ҽ����� �˻��� �����͸� Map ������� ������ ���� Ŭ����
	 * @exception <code>java.rmi.RemoteException</code>: ����Ʈ ȣ�� ����
	 * @exception <code>org.sosfo.framework.exception.AppException</code>: ����Ͻ� ���� ����� �߻��ϴ� ���� ��.
	 */
	protected boolean update(Connection conn, Tray pTray) throws AppException {
		throw new AppException("���� Ŭ�������� �������̵��Ͽ� �ۼ��Ͽ��� �մϴ�.");
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
			throw new AppException("BaseBean.update(Tray reqTray)���� �Ϲ� ���� �߻�", ex);
		} catch (Throwable t) {
			throw new AppException("BaseBean.update(Tray reqTray)���� throwable �߻�", t);
		} finally {
			end = System.currentTimeMillis();
			Log.info("TRACE", this, " process time : " + currentTimeMillis(start, end));
			returnConnection(conn);
		}
	}
}
