/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : ������
*@LastVersion    : 1.0
*    2007-10-01    ������
*        1.0       ���� ����
==============================================================================*/

package com.posdata.glue.miplatform.vo;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * MiPlatform Dataset�� �ϳ��� ���ڵ带 ǥ���ϴ� PosRecord Class. 
 * Client Side�� Server Side ���� ���Ӽ��� �����ϰ��� ������� Data Structure Ŭ����. <br><br>
 * 
 * �ڼ��� ������� com.posdata.glue.miplatform.vo.PosDataset�� Javadoc ������ �����ϱ� �ٶ�.
 * 
 * @author ������
 * @see PosDataset
 * @see PosRecordType
 */
public class PosRecord implements PosRecordType
{
    private int recordType;
    
    private Map attributes;
    
    /**
     * ����Ʈ ������
     */
    public PosRecord()
    {
        this.attributes = new CaseInsensitiveMap();
    }
    
    /**
     * ������
     * 
     * @param type ���ڵ� Ÿ�� (PosRecordType ����)
     */
    public PosRecord(int type)
    {
        this();
        this.recordType = type;
    }  
    
    /**
     * ���ڵ� Ÿ���� ��´�. (PosRecordType Ÿ�� ��)
     * 
     * @return PosRecordType Ÿ�� ��
     */
    public int getType() 
    {
        return this.recordType;
    }
    
    /**
     * �׸��� �����Ѵ�.
     * 
     * @param value �׸� ��
     * @param name �׸� ��
     */
    public void setAttribute(String name, Object value) 
    {
        this.attributes.put(name, value);
    }
    
    /**
     * �׸� ���� ��´�.
     * 
     * @return �׸� ��
     * @param name �׸� ��
     */
    public Object getAttribute(String name) 
    {
        return this.attributes.get(name);
    }
    
    /**
     * �׸� ���� String ���·� ��´�.
     * 
     * @return String ������ �׸� ��
     * @param name �׸� ��
     */
    public String getString(String name) 
    {
        return (String) this.attributes.get(name);
    }

    /**
     * ������Ʈ ���� ������ ��´�. (RecordType, �׸�����)
     * 
     * @return ������Ʈ ���� ����
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer(1024);
        buff.append("Type: ").append(getTypeString());
        buff.append(", Attributes: ").append(this.attributes.toString());
        return buff.toString();
    }
    
    /**
     * ���ڵ� Ÿ�Կ� �ش��ϴ� ���ڿ��� ��´�.
     * 
     * @return ���ڵ� Ÿ�� ���ڿ�
     */
    public String getTypeString() 
    {
        if (this.recordType == UPDATE) 
            return "Update";
        else if (this.recordType == INSERT)
            return "Insert";
        else if (this.recordType == NORMAL) 
            return "Normal";
        else if (this.recordType == DELETE)
            return "Delete";
        return "Unknown";
    }
}