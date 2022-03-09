/**
 * ���ϸ�: DBUtil.java �ۼ���: 2004. 2. 11. �ۼ���: �����(hkjin@daou.co.kr)
 */
package org.sosfo.framework.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * DB ���߰������� ���� ���Ǵ� ����� ����ִ� ��ƿ��Ƽ Ŭ����.
 * 
 * @author �����
 * @version 2004. 2. 11.
 */
public class DBUtil {
	/**
	 * <code>java.sql.ResultSet</code>�� �޾� ��Ÿ������ ������ش�. ���߰������� ����� �뵵�� ���.
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public static void dumpResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int cnt = rsmd.getColumnCount();
		for (int i = 1; i <= cnt; i++) {
			System.out.println("Column #" + i + ": " + rsmd.getColumnName(i));
		}
	}

	/**
	 * �־��� DB connection�� close�Ѵ�.
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
		}
	}
}
