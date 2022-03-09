package org.sosfo.framework.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.sosfo.framework.tray.CRAResultSetTrayFactory;
import org.sosfo.framework.tray.ResultSetTrayFactory;

/**
 * <code>java.sql.ResultSet</code>�� �ٸ� ������ Ŭ������ ��ȯ�Ͽ� �����ִ� �������̽�.
 * 
 * @author �����(hkjin@daou.co.kr)
 */
public interface ResultConverter {

	/**
	 * <code>ResultSet</code>�� ��� ���ڵ带 �迭�� List�� ��ȯ���ִ� �ν��Ͻ�
	 */
	public static final ResultConverter	ListOfArray		= new ResultConverter() {
															public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
																ArrayList v = new ArrayList(10);
																ResultSetMetaData rsmd = rs.getMetaData();

																while (rs.next()) {
																	String[] array = new String[rsmd.getColumnCount()];
																	for (int i = 0; i < array.length; i++) {
																		array[i] = rs.getString(i + 1);
																	}
																	v.add(array);
																}

																return v.size() > 0 ? v : null;
															}
														};

	/**
	 * <code>ResultSet</code>�� ��� ���ڵ带 Map�� List�� ��ȯ���ִ� �ν��Ͻ�
	 */
	public static final ResultConverter	ListOfMap		= new ResultConverter() {
															public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
																ArrayList v = new ArrayList(10);
																ResultSetMetaData rsmd = rs.getMetaData();

																String[] colNames = new String[rsmd.getColumnCount()];
																for (int i = 0; i < colNames.length; i++) {
																	colNames[i] = rsmd.getColumnName(i + 1);
																}

																while (rs.next()) {
																	HashMap map = new HashMap();
																	for (int i = 0; i < colNames.length; i++) {
																		map.put(colNames[i], rs.getString(i + 1));
																	}
																	v.add(map);
																}

																return v.size() > 0 ? v : null;
															}
														};

	/**
	 * <code>ResultSet</code>�� ù��° ���ڵ带 Map���� ��ȯ���ִ� �ν��Ͻ�
	 */
	public static final ResultConverter	Map				= new ResultConverter() {
															public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
																if (rs.next()) {
																	ResultSetMetaData rsmd = rs.getMetaData();
																	HashMap map = new HashMap();

																	for (int i = 0; i < rsmd.getColumnCount(); i++) {
																		map.put(rsmd.getColumnName(i + 1), rs.getString(i + 1));
																	}

																	return map;
																} else {
																	return null;
																}
															}
														};

	/**
	 * <code>ResultSet</code>�� ù��° ���ڵ��� ù��° �ʵ带 ���ڿ������� ��ȯ���ִ� �ν��Ͻ�. count(*)�� EXISTS ���� ���ǿ��� ���
	 */
	public static final ResultConverter	Scalar			= new ResultConverter() {
															public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
																if (rs.next()) {
																	return rs.getString(1);
																} else {
																	return null;
																}
															}
														};

	/**
	 * <code>ResultSet</code>�� ��� ���ڵ带 Tray�� ��ȯ���ִ� �ν��Ͻ�.
	 */
	public static final ResultConverter	ResultSetTray	= new ResultConverter() {
															public Object fromResultSet(ResultSet rs, Object arg) throws SQLException {
																ResultSetTrayFactory factory = new CRAResultSetTrayFactory();
																return factory.getTray(rs);
															}
														};

	/**
	 * <code>java.sql.ResultSet</code> ��ü�� �޾� �ٸ� Ŭ������ ��ü�� ��ȯ�ϴ� method. <code>Object</code>�� �����ֹǷ� �޴� �ʿ��� ������ ĳ������ �ʿ��ϴ�.
	 * 
	 * @param rs ��������� ��� �ִ� <code>java.sql.ResultSet</code>
	 * @param arg ��ȯ������ �ʿ��� �Ű�����
	 * @return rs�� ��ȯ�� �ٸ� ��ü.
	 * @throws SQLException
	 */
	abstract Object fromResultSet(ResultSet rs, Object arg) throws SQLException;
}
