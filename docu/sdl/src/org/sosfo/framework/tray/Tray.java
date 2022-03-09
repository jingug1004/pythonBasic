/**
 * ���ϸ�: tubis.asi.x.tray.Tray.java ���ϰ���: Data Transfer Object�� ���� ���� �������̽� ���۱�: Copyright (c) 2003 by SK C&C. All rights reserved. �ۼ���: ������ (nucha@dreamwiz.com)
 */

package org.sosfo.framework.tray;

// Java API
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;

/**
 * Tray �������̽��� Data Transfer Object�� ���� ���� �������̽��̴�. Map�� key-List ���� �����ϴ� �����̸� �⺻������ ���ڿ��� List�� �����Ѵ�. ����� ���ڿ��� ���ϴ� Ÿ������ �����ϴ� �޼ҵ尡 �����Ǹ� �޼ҵ� ���ڷ� ���� (key, index)�� key�� ����� List�� index��° ���ڿ��� �����ȴ�. �޼ҵ� ���ڷ� ���� (key)�� (key, index)=(key, 0)�� �����ȴ�.
 * <p>
 * ����
 * @author <b>������</b>
 * @version 1.0, 2004/02/01
 */

public interface Tray extends java.io.Serializable {
    /**
     * ��û ������ ������ �����Ѵ�.
     * @return - ��û ������ ����
     */
    int size();

    /**
     * ��û������ ����ִ����� �˻��Ѵ�.
     */
    boolean isEmpty();

    /**
     * ������ �������ִ����� �˻��Ѵ�.
     */
    boolean isFull();

    /**
     * ����ִ� ���� ����
     */
    void clear();

    /**
     * �����Ϳ� ������ �� �ִ� �������̽��� �����Ѵ�.
     * @return - Enumeration
     */
    Enumeration getEnumeration();

    /**
     * ��û ���� Ư�� ������ ����
     * @param key - ������ �˰� ���� Ư�� ������ ���� Ű
     * @return - Ư�� ������ ����
     */
    int size(String key);

    // key-value ��� Tray�� ��츦 ���� �������̽�

    /**
     * key�� �����ϴ� List�� �����Ѵ�.
     * @param key - ���� Ű
     */
    void clear(String key);

    /**
     * ��� Ű�� ���ڿ� �迭�� �����Ѵ�.
     * @return - Ű���� ���ڿ� �迭
     */
    String[] getKeys();

    String get(String key, int index);

    String get(String key);

    String getString(String key);

    String getDateString(String key);

    String[] getAllString(String key);

    String getString(String key, int index);

    String getDateString(String key, int index);

    String getTimeString(String key, int index);

    BigDecimal getBigDecimal(String key);

    BigDecimal getBigDecimal(String key, int index);

    int getInt(String key);

    int getInt(String key, int index);

    long getLong(String key);

    long getLong(String key, int index);
    
    /*
     * char getChar(String key); char getChar(String key, int index); byte getByte(String key); byte getByte(String key, int index); long getLong(String key); long getLong(String key, int index); float getFloat(String key); float getFloat(String key, int index);
     */

    double getDouble(String key);

    double getDouble(String key, int index);
    
    // set methods
    void set(String key, String value); // �����ϸ� ������Ʈ �ȴ�.

    void set(String key, String[] values);

    void setString(String key, String value);

    void setString(String key, String[] values);

    void set(String key, List list);

    // add methods
    void add(String key, String value); // �����ϸ� ������ ���ҷ� ������Ʈ �ȴ�.

    // ���� ���ε� ���� �������̽�: �� ����Ÿ�� �Ϻ� �� ���Ͼ��ε� ������Ʈ�� ���� ��� ��ȿ�� �޼ҵ��
    public String getFileName(String name);

    public String getFileName(String name, int index);

    public String getFileType(String name);

    public String getFileType(String name, int index);

    public String getSaveDir(String name);

    public String getSaveDir(String name, int index);

    public long getFileLength(String name);

    public long getFileLength(String name, int index);

    /**
     * ���Ͽ� Tray�� ������ ����Ѵ�.
     * @param - fileName �ַ� �� ȭ�鿡�� ������ cmd ���̴�.
     * @return - ����� ������ ��ü���-�۾��� ������ ��� �����ϴµ� ���ȴ�.
     */
    public String writeToFile(String fileName);

    //
    /**
     * Tray�� ������ ����� ��ü��� ���ϸ��� �޾Ƽ� �����Ѵ�. Ʈ������� �����ϸ� 2������ҿ� ����ߴ� ������ �����ϴµ� ����Ѵ�.
     * @param - fqfn �������� ��ġ�� fully qualified file name���� �Է¹޴´�.
     * @return - ����.
     */
    public void removeFile(String fqfn);

    /**
     * {@link ResultSetTray}ó�� �ϳ��� key�� ���� ��� ���� ������ value�� �����ϴ� tray�� ��쿡 �����ؾ� �ϴ� method. key���� value�� ������ �޶����� Tray�� ���� -1�� �����ϵ��� �����ؾ� �Ѵ�.
     * @return
     */
    public int getRowCount();
    /*
     * void setInt(String key); void setInt(String key, int index); void setChar(String key); void setChar(String key, int index); void setByte(String key); void setByte(String key, int index); void setLong(String key); void setLong(String key, int index); void setFloat(String key); void setFloat(String key, int index); void setDouble(String key); void setDouble(String key, int index);
     */
}
