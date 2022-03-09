/**
 * ���ϸ�: tubis.asi.x.tray.AbstractTray.java ���ϰ���: Ŭ���̾�Ʈ ��û ������ ĸ��ȭ�� Ŭ���� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 */

package org.sosfo.framework.tray;

// Java API
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.sosfo.framework.exception.AppException;

/**
 * HTTP request ������ Tray ��ü�� ��� ���� �⺻ ������ �����ϴ� �߻� Ŭ����
 * <p>
 * ����
 * @author <b>������</b>
 * @version 1.0, 2004/02/01
 */

public abstract class AbstractTray implements Tray {
    // Map�� List�� �����ϸ�, List���� String�� �����Ѵ�.
    private static String   EMPTY_STRING       = "";

    private static String[] EMPTY_STRING_ARRAY = new String[0];

    protected Map	   map		= new TreeMap();

    public AbstractTray() {
    }

    /**
     * �� ���� ������ �����͵��� ����Ǿ������� �����Ѵ�.
     * @return - ����ϰ� �ִ� Ű�� ����
     */
    public int size() {
	return map.size();
    }

    /**
     * Ư�� key�� �ش��ϴ� �������� ������ �����Ѵ�.
     * @param key - �����ִ� Ű
     * @return - Ű�� ���õ� ������ ����
     */
    public int size(String key) {
	Object obj = map.get(key);

	int iSize = 0;
	if (obj != null) {
	    List list = (List) obj;
	    iSize = list.size();
	}

	return iSize;
    }

    /**
     * ��û������ ����ִ����� �˻��Ѵ�.
     * @return - ��������� true, �׷��� ������ false
     */
    public boolean isEmpty() {
	return size() == 0;
    }

    /**
     * ������ �������ִ����� �˻��Ѵ�.
     * @return - ����Ʈ ���ϰ��� false
     */
    public boolean isFull() {
	return false;
    }

    /**
     * ����� �����Ϳ� ���� ���Ÿ� �����Ѵ�.
     * @return - Enumeration
     * @throws AppException - �⺻���� ���� �߻�
     */
    public Enumeration getEnumeration() throws AppException {
	throw new AppException("�⺻ ������ �������� �ʽ��ϴ�.");
    }

    /**
     * ����� ���� ����
     */
    public void clear() {
	// this.clearList();
	map.clear();
    }

    /**
     * ����� Ư�� ���� ����
     * @param key - Ư�� ������ ���� Ű
     */
    public void clear(String key) {
	map.remove(key);
    }

    /**
     * ��û �����鿡 ���� Ű�� �����Ѵ�.
     * @return - Ű���� �迭
     */
    public String[] getKeys() {
	int iSize = map.size();
	System.out.println("iSize=" + iSize);
	String[] keys = new String[iSize];
	Set set = map.keySet();
	Iterator it = set.iterator();
	int i = 0;
	while (it.hasNext()) {
	    keys[i++] = (String) it.next();
	}

	return keys;
    }

    /**
     * index ��° key ���� ����.
     * @param key - ����� Ű
     * @param index - ���ϴ� index
     * @return - index ��° key ��, �ǹ̰� ���ٸ� null�� �����Ѵ�.
     */
    public String get(String key, int index) {
	List list = (List) map.get(key);
	if (list == null) {
	    return (String) null;
	}

	try {
	    String value = (String) list.get(index);
	    return value;
	} catch (IndexOutOfBoundsException ex) {
	    throw new AppException("key = " + key + ", index = " + index, ex);
	}
    }

    /**
     * 0��° key�� ���� ���� ����.
     * @param key - ����� Ű
     * @return - 0��° key�� �����ϴ� ��
     */
    public String get(String key) {
	return get(key, 0);
    }

    /**
     * 0��° key�� ���� ���� ����.
     * @param key - ����� Ű
     * @return - 0��° key�� �����ϴ� ��
     */
    public String getString(String key) {
	return getString(key, 0);
    }

    public String getDateString(String key) {
	return getDateString(key, 0);
    }

    /**
     * key�� ���� ��� ���� ���ڿ� �迭�� ����.
     * @param key - ����� Ű
     * @return - key�� �����ϴ� ���ڿ� �迭 ��
     */
    public String[] getAllString(String key) {
	List list = (List) map.get(key);
	if (list == null) {
	    return EMPTY_STRING_ARRAY;
	}

	String[] values = (String[]) list.toArray(EMPTY_STRING_ARRAY);
	return values;
    }

    /**
     * index ��° key ���� ����.
     * @param key - ����� Ű
     * @param index - ���ϴ� index
     * @return - index ��° key ��
     */
    public String getString(String key, int index) {
	String value = get(key, index);
	if (value == null) {
	    return "";
	}

	return value;
    }

    /**
     * �ӽ� -- ������ 20040223 - ��¥����(21��) �� ��¥(yyyy-mm-dd)�� �����Ѵ�. index ��° key ���� ����.
     * @param key - ����� Ű
     * @param index - ���ϴ� index
     * @return - index ��° key ��
     */
    public String getDateString(String key, int index) {
	String value = get(key, index);
	if (value == null || value.length() != 21) {
	    return "";
	}

	return value.substring(0, 10);
    }

    /**
     * �ӽ� -- ������ 20040223 - ��¥����(21��) �� �ð�(hh:mm)�� �����Ѵ�. index ��° key ���� ����.
     * @param key - ����� Ű
     * @param index - ���ϴ� index
     * @return - index ��° key ��
     */
    public String getTimeString(String key, int index) {
	String value = get(key, index);
	if (value == null || value.length() != 21) {
	    return "";
	}

	return value.substring(11, 16);
    }

    /**
     * key�� 0��° ���� BigDecimal�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @return - key�� 0��° ���� ���� BigDecimal
     */
    public BigDecimal getBigDecimal(String key) {
	return getBigDecimal(key, 0);
    }

    /**
     * key�� index��° ���� BigDecimal�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @param index - index��° ���
     * @return - key�� index��° ���� ���� BigDecimal
     */
    public BigDecimal getBigDecimal(String key, int index) {
	String value = get(key, index);
	if (value == null) {
	    return new BigDecimal("0");
	}

	if (value.equals("")) {
	    return new BigDecimal("0");
	}

	return new BigDecimal(value);
    }

    /**
     * key�� 0��° ���� int�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @return - key�� 0��° ���� ���� BigDecimal
     */
    public int getInt(String key) {
	return getInt(key, 0);
    }

    /**
     * key�� index��° ���� int�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @param index - index��° ���
     * @return - key�� index��° ���� ���� int
     */
    public int getInt(String key, int index) {
	String value = get(key, index);
	if (value == null) {
	    return 0;
	}

	if (value.equals("")) {
	    return 0;
	}

	int iValue = 0;
	try {
	    iValue = Integer.parseInt(value);
	} catch (NumberFormatException ex) {
	}

	return iValue;
    }

    /**
     * key�� 0��° ���� double�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @return - key�� 0��° ���� ���� double
     */
    public double getDouble(String key) {
	return getDouble(key, 0);
    }

    public double getDouble(String key, int index) {
	String value = get(key, index);
	if (value == null) {
	    return 0L;
	}

	if (value.equals("")) {
	    return 0L;
	}

	double dValue = 0.0D;
	try {
	    dValue = Double.parseDouble(value);
	} catch (NumberFormatException ex) {
	}

	return dValue;
    }

    /**
     * key�� 0��° ���� long�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @return - key�� 0��° ���� ���� BigDecimal
     */
    public long getLong(String key) {
    	return getLong(key, 0);
    }

    /**
     * key�� index��° ���� long�� ��ȯ�Ͽ� �����Ѵ�.
     * @param key - ����� Ű
     * @param index - index��° ���
     * @return - key�� index��° ���� ���� int
     */
    public long getLong(String key, int index) {
		String value = get(key, index);
		if (value == null) {
		    return 0;
		}
	
		if (value.equals("")) {
		    return 0;
		}
	
		long lValue = 0;
		try {
			lValue = Long.parseLong(value);
		} catch (NumberFormatException ex) {
		}
	
		return lValue;
    }
    
    private void clearList() {
	String[] keys = this.getKeys();

	int size = keys.length;
	for (int i = 0; i < size; i++) {
	    List list = (List) map.get(keys[i]);
	    list.clear();
	}
    }

    /**
     * ����Ϸ��� index�� ��ȿ�� ���� �����Ѵ�.
     * @param index - �����Ϸ��� index
     * @return - ��ȿ�ϸ� true, �׷��� ������ false
     */
    private boolean isValidIndex(int index) {
	int iSize = size();
	return (index >= 0 && index < iSize);
    }

    /**
     * ����ִ� List�� �����Ͽ� ��ȯ�Ѵ�.
     * @return - Empty List
     */
    private List newList() {
	return new java.util.ArrayList();
    }

    /**
     * key-value ������ ���� �����Ѵ�.
     * @param key - ����Ϸ��� Ű
     * @param value - Ű�� �����ϴ� ��
     */
    public void set(String key, String value) {
	String[] values = new String[] { value };
	set(key, values);
    }

    /**
     * key-values[] ������ ���� �����Ѵ�.
     * @param key - ����Ϸ��� Ű
     * @param values - Ű�� �����ϴ� �迭��
     */
    public void set(String key, String[] values) {
	if (key == null || values == null) {
	    throw new java.lang.IllegalArgumentException("key�� value�� ������ �ֽ��ϴ�.");
	}

	List list = this.newList();
	int size = values.length;
	for (int i = 0; i < size; i++) {
	    list.add(values[i]);
	}

	map.put(key, list);
    }

    /**
     * ���� key�� value�� �߰��Ѵ�.
     * @param key - ����Ϸ��� Ű
     * @param value - Ű�� �����ϴ� ��
     */
    public void add(String key, String value) {
	if (map.get(key) == null) {
	    set(key, value);
	    return;
	}

	List list = (List) map.get(key);
	list.add(value);
    }

    public void setString(String key, String value) {
	set(key, value);
    }

    public void setString(String key, String[] values) {
	this.set(key, values);
    }

    /**
     * Tray�� �����ϰ� �ִ� ������ �����ִ� Collection View�̴�.
     * @return - Tray�� ����
     */
    public String toString() {
	StringBuffer buffer = new StringBuffer();
	String[] keys = this.getKeys();
	int size = keys.length;
	for (int i = 0; i < size; i++) {
	    //System.out.println("key: " + keys[i]);
	    buffer.append(keys[i]).append(": \n");
	    buffer.append(toString(keys[i]));
	}

	return buffer.toString();
    }

    /**
     * key�� ���� ���鿡 ���� ���ڿ�
     * @param key - ���� key
     * @return - key�� ���õ� ������ ���ڿ�
     */
    private String toString(String key) {
	StringBuffer buffer = new StringBuffer();
	Object obj = getObject(key);
	List list = (List) obj;
	int listSize = list.size();
	for (int j = 0; j < listSize; j++) {
	    Object value = list.get(j);
	    String svalue = value != null ? value.toString() : "{null}";
	    buffer.append("\t").append(svalue).append("\n");
	}

	return buffer.toString();
    }

    /**
     * key-List ���� �����ϴ� �޼ҵ�
     * @param key - ��� Ű
     * @param list - Ű�� ���õ� ��� List
     */
    public void set(String key, List list) {
	map.put(key, list);
    }

    // -------------���� ���ε� ���� �޼ҵ��-------------------------------------//

    /**
     * ���ϸ��� �����Ѵ�.
     * @param key - ���� Ű
     * @return - ����Ʈ�� �� ���ڿ� ����
     */
    public String getFileName(String key) {
	return getFileName(key, 0);
    }

    /**
     * key�� index��° ���ϸ��� �����Ѵ�.
     * @param key - ���� Ű
     * @param index
     * @return - ����Ʈ�� �� ���ڿ� ����
     */
    public String getFileName(String key, int index) {
	return EMPTY_STRING;
    }

    /**
     * ���� Ÿ���� �����Ѵ�.
     * @param key - ���� Ű
     * @return - �� ���ڿ� ����
     */
    public String getFileType(String key) {
	return getFileType(key, 0);
    }

    /**
     * key�� index��° ���� Ÿ���� �����Ѵ�.
     * @param key - ���� Ű
     * @param index
     * @return - �� ���ڿ� ����
     */
    public String getFileType(String key, int index) {
	return EMPTY_STRING;
    }

    /**
     * key�� 0��° ������ ����� ��ġ�� �����Ѵ�.
     * @param key - ���� Ű
     * @return - �� ���ڿ� ����
     */
    public String getSaveDir(String key) {
	return getSaveDir(key, 0);
    }

    /**
     * key�� index��° ������ ����� ��ġ�� �����Ѵ�.
     * @param key - ���� Ű
     * @param index
     * @return - �� ���ڿ� ����
     */
    public String getSaveDir(String key, int index) {
	return EMPTY_STRING;
    }

    /**
     * key�� 0��° ���� ũ�⸦ �����Ѵ�.
     * @param key - ���� Ű
     * @return - ����Ʈ�� -1�� ����
     */
    public long getFileLength(String key) {
	return getFileLength(key, 0);
    }

    /**
     * key�� index��° ���� ũ�⸦ �����Ѵ�.
     * @param key - ���� Ű
     * @param index -
     * @return - ����Ʈ�� -1�� �����Ѵ�.
     */
    public long getFileLength(String key, int index) {
	return -1;
    }

    /**
     * key�� ���õ� ��ü�� �����Ѵ�.
     * @param key - ���� Ű
     * @return - Object (List ��ü)
     */
    protected Object getObject(String key) {
	return map.get(key);
    }

    /**
     * ResultSetTray�� ��쿡�� ��ȿ�� method��, tray�� ��� �ִ� row�� ������ �����Ѵ�. AbstractTray������ -1�� ������.
     * @return -1
     */
    public int getRowCount() {
	return -1;
    }

    // ===============================================================================
    // Request �����͸� ���� tray�� ������
    // ���Ͽ� ����ϰų� �����ϴ� ��� ����. BaseCommand���� ���� �� �ִ�.
    // ===============================================================================

    // Tray�� �ִ� ������ ���� �����ڷ� key1 value1 key2 value2 �������� ���Ͽ� �����Ѵ�.
    private String myroot = "/";

    private String myDir  = "tubis/domains/tubisDomain/logs/";

    public String writeToFile(String fileName) {
	String root = myroot + myDir;
	String myfile = "";

	int lastPointIndex = fileName.lastIndexOf(".");
	String packageName = fileName.substring(0, lastPointIndex);
	String cmdName = fileName.substring(lastPointIndex + 1);
	String myLocate = makeDir(packageName);

	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

	String timestamp = df.format(new java.util.Date());

	long writeTime = System.currentTimeMillis();
	FileWriter fw = null;
	BufferedWriter bw = null;
	try {
	    myfile = myroot + myLocate + cmdName + "_" + timestamp + ".txt";
	    fw = new FileWriter(myfile);
	    bw = new BufferedWriter(fw);

	    Set keySet = map.keySet();
	    Iterator keyIterator = keySet.iterator();
	    StringBuffer buffer = new StringBuffer();
	    while (keyIterator.hasNext()) {
		String key = (String) keyIterator.next();
		List eachList = (List) map.get(key);
		for (int i = 0; i < eachList.size(); i++) {
		    buffer.append(key).append("\t").append(eachList.get(i)).append("\n");
		}
	    }

	    bw.write(buffer.toString());
	    bw.flush();

	    System.out.println(buffer.toString());

	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (bw != null) {
		try {
		    bw.close();
		} catch (IOException ignore) {
		}
	    }

	    if (fw != null) {
		try {
		    fw.close();
		} catch (IOException ignore) {
		}
	    }
	}

	return myfile;
    }

    public void removeFile(String fqfn) {
	if (fqfn == null || fqfn.equals("")) {
	    return;
	}

	try {
	    File f = new File(fqfn);
	    if (f.exists()) {
		f.delete();
	    }
	} catch (Exception ignore) {
	    ignore.printStackTrace();
	}
    }

    private String makeDir(String packageName) {
	String myLocate = myDir + convertPackageToDir(packageName);

	java.util.StringTokenizer st = new java.util.StringTokenizer(myLocate, "/");

	String path = "/";
	while (st.hasMoreTokens()) {
	    String dirName = st.nextToken();
	    path += dirName + "/";
	    File f = new File(path);
	    if (!f.exists()) {
		boolean isMade = f.mkdir();
	    }
	}

	return myLocate;
    }

    /**
     * tubis.ccs.a.a10 -> tubis/ccs/a/a10/ ���� ��ȯ
     * @param packageName
     * @return
     */
    String convertPackageToDir(String packageName) {
	java.util.StringTokenizer st = new java.util.StringTokenizer(packageName, ".");

	String path = "";
	while (st.hasMoreTokens()) {
	    String dirName = st.nextToken();
	    path += dirName + "/";
	}

	return path;
    }

}
