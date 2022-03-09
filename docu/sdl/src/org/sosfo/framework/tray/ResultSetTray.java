/**
 * ���ϸ�: tubis.asi.x.tray.ResultSetTray.java ���ϰ���: java.sql.ResultSet ������ ĸ��ȭ�� Ŭ���� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 * @version 1.1 2004-05-12 toHtml() method �ۼ� - ������
 * @version 1.1 2004-05-03 toString() method ���ۼ�
 */

package org.sosfo.framework.tray;

// Java API
import java.util.List;

public class ResultSetTray extends AbstractTray implements java.io.Serializable {
    // ������� �÷��� ����
    private String[] columnNames     = null;

    // ������� �� �÷��� Ÿ��
    private String[] columnTypeNames = null;

    private int      rowCount	= 0;

    public ResultSetTray() {
    }

    public String toHtml() {
	if (getRowCount() <= 0) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append("<table width='910' cellspacing='1' cellpadding='2' border='0' class='tab'>");
	    buffer.append("<tr>");
	    buffer.append("<td class='td_ce'>").append("��ȸ�� ������ �����ϴ�.").append("</td>");
	    buffer.append("</tr>");
	    buffer.append("</table>");
	    return buffer.toString();
	}

	int columnCount = this.getColumnNames().length;

	StringBuffer buffer = new StringBuffer();

	buffer.append("<table width='100%' cellspacing='1' cellpadding='2' border='0' class='tab'>");
	buffer.append("<tr>");

	// ����ǥ��
	buffer.append("<td class='td_g_ce'>");
	buffer.append("NO");
	buffer.append("</td>");

	for (int i = 0; i < columnCount; i++) {
	    String eachColumnName = columnNames[i];

	    buffer.append("<td class='td_g_ce'>");
	    buffer.append(eachColumnName);
	    buffer.append("</td>");
	}
	buffer.append("</tr>");

	// ����ǥ��
	List list = null;
	int recordCount = getRowCount();
	for (int i = 0; i < recordCount; i++) {
	    buffer.append("<tr class='cursor' id='tr_" + i + "' bgcolor='#FFFFFF' onClick=\"chg_back(" + i + ",'#F1F8F4');location.href='#none';\">");
	    buffer.append("<td class='td_ce'>").append(String.valueOf(i + 1)).append("</td>");
	    for (int j = 0; j < columnCount; j++) {
		String eachColumnName = columnNames[j];
		list = (List) map.get(eachColumnName);

		buffer.append("<td class='td_ce'>").append(list.get(i).toString()).append("</td>");
	    }
	    buffer.append("</tr>");
	}

	buffer.append("</table>");

	return buffer.toString();
    }

    /**
     * �����ϰ� �ִ� Ű�� �����Ϳ� ���� ����� ���ڿ��� �����Ѵ�.
     * @return - ����� Ű-�� ���� ����
     */
    public String toString() {
	int col_count = columnNames == null ? 0 : columnNames.length;
	int row_count = getRowCount();
	int row_max = 0;

	if (col_count == 0 || getRowCount() == 0) {
	    return "��ȸ�� ������ �����ϴ�.";
	}

	StringBuffer buffer = new StringBuffer();
	buffer.append("��ȸ�Ǽ�: ").append(getRowCount()).append('\n');

	for (int i = 0; i < col_count; i++) {
	    String col_name = columnNames[i];
	    buffer.append(col_name).append('\t');
	    row_max = Math.max(row_max, size(col_name));
	}
	buffer.append('\n');

	for (int i = 0; i < row_max; i++) {
	    for (int j = 0; j < col_count; j++) {
		String col_name = columnNames[j];
		buffer.append(i > size(col_name) ? "{null}" : get(col_name, i));
		buffer.append("\t");
	    }
	    buffer.append('\n');
	}

	return buffer.toString();
    }

    /**
     * select ���������� ����� column���� �����Ѵ�.
     * @param columnNames - �÷��� �迭
     */
    public void setColumnNames(String[] columnNames) {
	this.columnNames = columnNames;
    }

    /**
     * select ���������� ����� column�� Ÿ���� �����Ѵ�.
     * @param columnTypeNames - �÷�Ÿ�Ը� �迭
     */
    public void setColumnTypeNames(String[] columnTypeNames) {
	this.columnTypeNames = columnTypeNames;
    }

    /**
     * �÷��� �迭�� �����Ѵ�.
     * @return - �÷��� �迭
     */
    public String[] getColumnNames() {
	return columnNames;
    }

    /**
     * �÷�Ÿ�Ը� �迭�� �����Ѵ�.
     * @return - �÷�Ÿ�Ը� �迭
     */
    public String[] getColumnTypeNames() {
	return columnTypeNames;
    }

    public int getRowCount() {
	return rowCount;
    }

    protected void setRowCount(int rowCount) {
	this.rowCount = rowCount;
    }
}