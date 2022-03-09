/**
 * ���ϸ�: tubis.asi.x.tray.TubisResultSetTrayFactory.java ���ϰ���: ResultSet�� ���� Tray�� �����ϴ� ���丮 Ŭ���� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 */

package org.sosfo.framework.tray;

// Java API
import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sosfo.framework.exception.AppException;
import org.sosfo.framework.log.Log;
import org.sosfo.framework.util.CRAUtil;

/**
 * TubisResultSetTrayFactory Ŭ������ java.sql.ResultSet ��ü�� ���� ������ ���δ� Tray ��ü�� �����ϴ� ���丮�̴�.
 * <p>
 * ���� <blockquote>
 * 
 * <pre>
 * ResultSet rs = pstmt.executeQuery();
 * ResultSetTrayFactory factory = new TubisResultSetTrayFactory();
 * Tray rsTray = factory.getTray(rs);
 * for (int i = 0; i < rsTray.getRowCount(); i++) {
 *     String name = rsTray.getString("name", i);
 *     int age = rsTray.getInt("age", i);
 * }
 * </pre>
 * 
 * </blockquote>
 * @author <b>������</b>
 * @version 1.1, 2004-05-28 CLOB�� ���� ���� �߰�
 * @version 1.0, 2004/02/01
 */

public class CRAResultSetTrayFactory extends ResultSetTrayFactory {
    // ������� �÷��� ����
    private String[] columnNames     = null;

    // ������� �� �÷��� Ÿ��
    private String[] columnTypeNames = null;

    // ���̺��� �÷� ��� �� �÷��� �ش��ϴ� ���� ������ �����ϴ� ��
    // �÷����� Ű(String)��, �÷������� ��(List)���� ���ν��� �����Ѵ�.
    // Map map = new HashMap();

    /**
     * ���޹��� ResultSet���� �ʿ��� ������ ������ ResultSetTray ��ü�� ������ �� ����.
     * @param rs - java.sql.ResultSet
     * @return - ResultSetTray
     */
    protected Tray createTray(ResultSet rs) {
	ResultSetTray tray = new ResultSetTray();

	try {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    setColumnNames(rsmd, tray);
	    setColumnTypeNames(rsmd, tray);
	    setColumnValues(rs, tray);
	} catch (SQLException ex) {
	    throw new AppException("TubisResultSetTrayFactory.createTray()", ex);
	}

	return tray;
    }

    /**
     * ResultSetMeta���� �÷����� ��� ResultSetTray�� �����Ѵ�.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnNames(ResultSetMetaData rsmd, ResultSetTray tray) throws java.sql.SQLException {
	int columnCount = rsmd.getColumnCount();
	this.columnNames = new String[columnCount];

	for (int i = 0; i < columnCount; i++) {
	    columnNames[i] = rsmd.getColumnName(i + 1).toLowerCase();
	}

	tray.setColumnNames(columnNames);
    }

    /**
     * ResultSetMeta���� �÷�Ÿ���� ��� ResultSetTray�� �����Ѵ�.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnTypeNames(ResultSetMetaData rsmd, ResultSetTray tray) throws java.sql.SQLException {
	int columnCount = rsmd.getColumnCount();
	String[] columnTypeNames = new String[columnCount];
	for (int i = 0; i < columnCount; i++) {
	    columnTypeNames[i] = rsmd.getColumnTypeName(i + 1);
	}

	tray.setColumnTypeNames(columnTypeNames);
    }

    /**
     * �÷������� ������ List �迭�� �����Ѵ�.
     * @param rsmd
     * @param tray
     * @throws java.sql.SQLException
     */
    private List[] getEmptyList(int columnCount) {
	List[] columnValues = new List[columnCount];
	for (int i = 0; i < columnCount; i++) {
	    columnValues[i] = new ArrayList();
	}
	return columnValues;
    }

    /**
     * �÷������� ResultSetTray�� �����Ѵ�.
     * @param rs
     * @param tray
     * @throws java.sql.SQLException
     */
    private void setColumnValues(java.sql.ResultSet rs, ResultSetTray tray) throws java.sql.SQLException {
	int columnCount = tray.getColumnNames().length;
	List[] columnValues = null;

	Object col_obj = null;
	String col_value = null;
	int row_cnt = 0;
	while (rs.next()) {
	    if (columnValues == null) {
		columnValues = getEmptyList(columnCount);
	    }

	    for (int i = 0; i < columnCount; i++) {
		col_obj = rs.getObject(i + 1);

		if (col_obj == null) {
		    columnValues[i].add("");
		    continue;
		}

		if (col_obj instanceof Clob) {
		    try {
			Reader in = ((Clob) col_obj).getCharacterStream();
			col_value = CRAUtil.readToEnd(in);
			in.close();
		    } catch (IOException ex) {
			Log.warn("Clob �д°������� ���� �߻�");
		    }
		} else if (col_obj instanceof Blob) {
		    throw new AppException("BLOB �б�� ���� �����Ǿ����� �ʽ��ϴ�.");
		} else {
		    col_value = col_obj.toString();
		}

		columnValues[i].add(col_value);
	    }

	    row_cnt++;
	}

	if (columnValues != null) {
	    for (int i = 0; i < columnCount; i++) {
		tray.set(tray.getColumnNames()[i], columnValues[i]);
	    }
	}

	tray.setRowCount(row_cnt);
    }
}
