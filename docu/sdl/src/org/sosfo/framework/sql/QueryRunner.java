package org.sosfo.framework.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.tray.Tray;

/**
 * �Ѱ��� SQL���� ���� �����ǰ�, �� SQL���� ������ å������, �αױ���� ���� Ŭ����. �з����� ���ÿ� ��� ������ �ּ�ȭ�ϱ� ����
 * 
 * <pre>
 * SELECT empno, empname FROM emp WHERE empno = :empno
 * </pre>
 * 
 * �� ���� �ݷ� ǥ������� SQL���� ǥ���ϰ�, {@link com.kyeryong.framework.tray.Tray}�� �μ��� �޾� ���ε庯������ �ѹ��� ��ü�Ѵ�. ���� ����ÿ��� {@link #query} �޽��(SELECT�� ���)�� {@link #update}(�׿��� ���)�� ����� �� ������, {@link #query} �޽�� ����
 * {@link ResultConverter} �ν��Ͻ��� �Բ� �����Ͽ� <code>java.sql.ResultSet</code>�� ������ ��� �ٸ� Ŭ������ ������ �� �ִ�.
 * 
 * @author �����(hkjin@daou.co.kr)
 * @version 1.1 ���ڿ� �߰��� ':'�� ���ε庯���� �ν��ϴ� ���� �ذ�
 * @version 1.0 2003.12.20
 */
public class QueryRunner {

	/**
	 * <code>java.sql.Statement</code>�� �ݴµ� ���Ǵ� ���� �޽��
	 * 
	 * @param stmt �ݰ��� �ϴ� Statement �ν��Ͻ�
	 */
	private static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
		}
	}

	/**
	 * <code>java.sql.ResultSet</code>�� �ݴµ� ���Ǵ� ���� �޽��
	 * 
	 * @param rs �ݰ��� �ϴ� ResultSet �ν��Ͻ�
	 */
	private static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
		}

	}

	/**
	 * �� Ŭ������ �ν��Ͻ��� �����ϴ� SQL ���ڿ�
	 */
	private String				sql;

	/**
	 * SQL ���ڿ��� ���Ե� ���ε� ������ ����
	 */
	private int					paramCount;

	/**
	 * colon notation(ex. :emp_id)�� ����� ���, ���ε� ������ �̸��� �����ϴ� ����Ʈ
	 */
	private List				paramNames;

	/**
	 * ���ε� ������ �Ҵ�� ���� �����ϴ� �迭. ���� ���ڿ��� DATE ���¸� �����Ѵ�.
	 */
	private Object[]			paramValues;

	/**
	 * �⺻ fetch ������.
	 */
	protected static final int	DEFAULT_FETCH_SIZE	= 10;

	/**
	 * �־��� SQL���ڿ��� ��ü�� �����Ѵ�. SQL ���ڿ��� '?'�� �����ϰų� ���ε�����(':name')�� ������ �� �ִ�.
	 * 
	 * @param sql �����ų SQL ���ڿ�
	 */
	public QueryRunner(String sql) {
		StringBuffer buf = new StringBuffer(sql.length());
		StringBuffer paramName = new StringBuffer();

		int length = sql.length();
		boolean partOfParamName = false;
		boolean partOfString = false;
		for (int i = 0; i < length; i++) {
			char c = sql.charAt(i);

			// :empname ������ ���ε庯�� ó��
			if (partOfParamName) {
				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {
					// �������� ��ӵǰ� �ִ� ���: ������ ���ۿ� �߰�.
					paramName.append(c);
				} else {
					// �������� ���� ���: �ϼ��� �������� ������ �迭�� �߰�.
					paramNames.add(paramName.toString());
					paramName.delete(0, paramName.length());
					buf.append(c);
					partOfParamName = false;
				}
			} else {
				switch (c) {
					// ���ε庯���� ����: '?'�� ġȯ
					case ':' :

						// ���ڿ� �߰��� ':'��� ����
						if (partOfString) {
							break;
						}
						if (paramNames == null) {
							paramNames = new ArrayList();
						}
						paramCount++;
						partOfParamName = true;
						c = '?';
						break;

					// ���ڿ��� ����/����
					case '\'' :
						partOfString = !partOfString;
						break;

					// ���ε庯��(JDBC ǥ��): ���ڿ��� �Ϻΰ� �ƴ϶�� ������ ����
					case '?' :
						if (!partOfString) {
							paramCount++;
						}
						break;
				}
				buf.append(c);
			}
		}

		// �������� �ϰ���� ���� ���¿��� SQL�� ������ ���: �������� �迭�� �߰�.
		if (partOfParamName) {
			paramNames.add(paramName.toString());
		}

		// ��� ���� �Ҵ�
		this.sql = buf.toString();
		paramValues = new Object[paramCount];
	}

	/**
	 * �־��� SQL���ڿ��� Tray�� ��ü�� �����Ѵ�.
	 * 
	 * @param sql �����ų SQL ���ڿ�
	 * @param paramTray SQL���� ���޵� �з����Ͱ��� ��� �ִ� Tray
	 */
	public QueryRunner(String sql, Tray paramTray) {
		this(sql);
		setParams(paramTray);
	}

	/**
	 * SQL���� ���޵� �μ����� �����Ѵ�.
	 * 
	 * @param paramValues
	 * @deprecated '?' ǥ����� SQL�� ����� ��� �����Ǵ� �޽���, ������ Tray ����� ����
	 */
	public void setParams(String[] paramValues) {
		System.arraycopy(paramValues, 0, this.paramValues, 0, paramValues.length);
	}

	/**
	 * SQL���� ���޵� �μ����� �����Ѵ�. Tray ���� ����ִ� key-value ������ ���� Tray�� ��� �ִ� ��� ���� ����.
	 * 
	 * @param paramTray SQL���� ���޵� �з����Ͱ��� ��� �ִ� {@link com.kyeryong.framework.tray.Tray}
	 */
	public void setParams(Tray paramTray) {
		for (int i = 0; i < paramCount; i++) {
			paramValues[i] = paramTray.getString((String) paramNames.get(i));
		}
	}

	/**
	 * SQL���� ���޵� �μ����� �����Ѵ�.
	 * 
	 * @param paramName
	 * @param paramValue
	 */
	public void setParam(String paramName, Object paramValue) {
		int index = paramNames.indexOf(paramName);
		for (int i = 0; i < paramValues.length; i++) {
			if (paramNames.get(i).equals(paramName)) {
				paramValues[i] = paramValue;
			}
		}
	}

	/**
	 * �ν��Ͻ� ���� ������ ��� ���ε庯���� ���� null�� �ʱ�ȭ�Ѵ�.
	 */
	public void clearParams() {
		for (int i = 0; i < paramValues.length; i++) {
			paramValues[i] = null;
		}
	}

	/**
	 * SQL���� ���Ե� ���ε庯���� ������ �����ش�.
	 * 
	 * @return SQL���� ���Ե� ���ε庯���� ����
	 */
	public int getParamCount() {
		return paramCount;
	}

	/**
	 * SQL���� ���Ե� ���ε庯���� �̸� �迭�� �����ش�.
	 * 
	 * @return SQL���� ���Ե� ���ε庯���� �̸� �迭
	 */
	public String[] getParamNames() {
		return (String[]) paramNames.toArray(new String[0]);
	}

	/**
	 * SQL���� ���Ե� ���ε庯���� �̸� �迭�� �����ش�.
	 * 
	 * @return SQL���� ���Ե� ���ε庯���� �̸� �迭
	 */
	public Object[] getParamValues() {
		return paramValues;
	}

	/**
	 * SELECT �������� �����ϰ�, �� ����� {@link ResultConverter}�� �̿��Ͽ� ��ȯ�� Ŭ������ ���·� �����Ѵ�.
	 * 
	 * @param conn <code>java.sql.Connection</code>
	 * @return
	 * @throws AppException
	 */
	public Object query(Connection conn) throws AppException {
		return query(conn, ResultConverter.ResultSetTray);
	}

	/**
	 * SELECT �������� �����ϰ�, �� ����� <code>ResultConverter</code>�� �̿��Ͽ� ��ȯ�� Ŭ������ ���·� �����Ѵ�.
	 * 
	 * @param conn <code>java.sql.Connection</code>
	 * @param converter <code>ResultConverter</code>
	 * @return
	 * @throws AppException
	 */
	public Object query(Connection conn, ResultConverter converter) throws AppException {
		return query(conn, converter, null);
	}

	/**
	 * SELECT �������� �����ϰ�, �� ����� <code>ResultConverter</code>�� �̿��Ͽ� ��ȯ�� Ŭ������ ���·� �����Ѵ�.
	 * 
	 * @param conn <code>java.sql.Connection</code>
	 * @param converter <code>ResultConverter</code>
	 * @param arg
	 * @return
	 * @throws AppException
	 */
	public Object query(Connection conn, ResultConverter converter, Object arg) throws AppException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object result = null;

		try {
			// Log.info("DB",this,sql);
			pstmt = conn.prepareStatement(sql);
			/*
			 * if (arg instanceof QueryFactor) {
			 * pstmt.setFetchSize(((QueryFactor) arg).fetchSize); } else {
			 */
			pstmt.setFetchSize(DEFAULT_FETCH_SIZE);
			// }

			for (int i = 0; i < paramCount; i++) {
				pstmt.setObject(i + 1, (String) paramValues[i]);
			}

			rs = pstmt.executeQuery();
			result = converter.fromResultSet(rs, arg);
		} catch (SQLException ex) {
			wrapSQLException(ex);
		} finally {
			closeResultSet(rs);
			closeStatement(pstmt);
		}

		return result;
	}

	/**
	 * DML���� �����ϰ� ����� ���ڵ��� ������ �����Ѵ�.
	 * 
	 * @return
	 * @throws AppException
	 */
	public int update(Connection conn) throws AppException {
		PreparedStatement pstmt = null;
		int rows = 0;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setFetchSize(DEFAULT_FETCH_SIZE);

			for (int i = 0; i < paramCount; i++) {
				pstmt.setObject(i + 1, paramValues[i]);
			}
			rows = pstmt.executeUpdate();
		} catch (SQLException ex) {
			wrapSQLException(ex);
		} finally {
			closeStatement(pstmt);
		}

		return rows;
	}

	/**
	 * �������� ��Ƽ� �ѹ��� execute
	 * 
	 * @param Connection
	 * @param Tray
	 * @return
	 * @throws AppException
	 */
	public boolean updateBatch(Connection conn, Tray rsTray[]) throws AppException {
		PreparedStatement pstmt = null;
		int failure_count = 0;
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setFetchSize(DEFAULT_FETCH_SIZE);

			for (int i = 0; i < rsTray.length; i++) {
				this.clearParams();
				this.setParams(rsTray[i]);

				Log.debug("DB", this, "\nupdateBatch [" + i + "] \n" + this.toString());

				for (int j = 0; j < paramCount; j++) {
					pstmt.setObject(j + 1, paramValues[j]);
				}

				pstmt.addBatch();
			}

			/**
			 *  pstmt.executeBatch() ���ϰ� 
			 *  0~n: ������ row count �� 
			 *  -2: ������ �Ͽ����� row count ���� �� �� ���� -3: ����
			 */
			int[] updateCounts = pstmt.executeBatch();

			for (int i = 0; i < updateCounts.length; i++){
				if (updateCounts[i] == -3){
					failure_count++;
				}
			}

			Log.debug("at QueryRunner.updateBatch failure_count : " + failure_count);

		} catch (SQLException ex) {
			wrapSQLException(ex);
		} finally {
			closeStatement(pstmt);
		}

		return failure_count == 0;
	}

	public String getQuery() {
		return sql;
	}

	/**
	 * �α׸� ����� ���� �� ��ü�� �����ִ� SQL���� ���ڿ� ���·� �����. �̶�, ���ε庯���� �Ҵ�� ���� '['�� ']'���� �ѷ��ο�����.
	 * 
	 * @return
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer(sql.length());
		int len = sql.length();
		int param_index = 0;

		boolean partOfString = false;
		char c;
		for (int i = 0; i < len; i++) {
			c = sql.charAt(i);
			switch (c) {
				case '?' :
					if (!partOfString && param_index < paramCount && paramValues[param_index] != null) {
						buf.append("/*:").append(paramNames.get(param_index)).append("*/'");
						buf.append(paramValues[param_index++]).append("'");
					} else {
						buf.append(c);
					}
					break;
				case '\'' :
					partOfString = !partOfString;
				default :
					buf.append(c);
			}
		}

		return buf.toString();
	}

	/**
	 * SQL������ �߻��� ���, �߻��� ������ ó���ϰ�, <code>AppException</code>�� ���μ� �ٽ� throw�Ѵ�.
	 * 
	 * @param ex
	 * @throws SQLException
	 */
	private void wrapSQLException(SQLException ex) throws AppException {
		Log.debug("QueryRunner SQL����. sql = " + ex.toString());
		throw new AppException("QueryRunner���� ���� �߻�: SQL�ڵ� = " + ex.getErrorCode(), ex);
		/*
		 * switch (ex.getErrorCode()) { case
		 * OracleSQLCode.UNIQUE_CONSTRAINT_VIOLATED: throw new
		 * AppException("X1001"); case OracleSQLCode.CANNOT_INSERT_NULL: throw
		 * new AppException("X1002"); }
		 */
	}
}
