/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
==============================================================================*/

package com.posdata.glue.miplatform.vo;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;

/**
 * MiPlatform Dataset의 하나의 레코드를 표현하는 PosRecord Class. 
 * Client Side와 Server Side 간의 종속성을 제거하고자 만들어진 Data Structure 클래스. <br><br>
 * 
 * 자세한 사용방법은 com.posdata.glue.miplatform.vo.PosDataset의 Javadoc 설명을 참조하기 바람.
 * 
 * @author 조성민
 * @see PosDataset
 * @see PosRecordType
 */
public class PosRecord implements PosRecordType
{
    private int recordType;
    
    private Map attributes;
    
    /**
     * 디폴트 생성자
     */
    public PosRecord()
    {
        this.attributes = new CaseInsensitiveMap();
    }
    
    /**
     * 생성자
     * 
     * @param type 레코드 타입 (PosRecordType 참조)
     */
    public PosRecord(int type)
    {
        this();
        this.recordType = type;
    }  
    
    /**
     * 레코드 타입을 얻는다. (PosRecordType 타입 값)
     * 
     * @return PosRecordType 타입 값
     */
    public int getType() 
    {
        return this.recordType;
    }
    
    /**
     * 항목을 설정한다.
     * 
     * @param value 항목 값
     * @param name 항목 명
     */
    public void setAttribute(String name, Object value) 
    {
        this.attributes.put(name, value);
    }
    
    /**
     * 항목 값을 얻는다.
     * 
     * @return 항목 값
     * @param name 항목 명
     */
    public Object getAttribute(String name) 
    {
        return this.attributes.get(name);
    }
    
    /**
     * 항목 값을 String 형태로 얻는다.
     * 
     * @return String 형태의 항목 값
     * @param name 항목 명
     */
    public String getString(String name) 
    {
        return (String) this.attributes.get(name);
    }

    /**
     * 오브젝트 상태 정보를 얻는다. (RecordType, 항목정보)
     * 
     * @return 오브젝트 상태 정보
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer(1024);
        buff.append("Type: ").append(getTypeString());
        buff.append(", Attributes: ").append(this.attributes.toString());
        return buff.toString();
    }
    
    /**
     * 레코드 타입에 해당하는 문자열을 얻는다.
     * 
     * @return 레코드 타입 문자열
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