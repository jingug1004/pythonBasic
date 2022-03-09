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

/**
 * PosRecord의 타입을 정의하는 상수를 관리하는 인터페이스
 * 
 * @author 조성민
 */
public interface PosRecordType 
{
    /**
     * NORMAL TYPE VALUE
     */
    public static final int NORMAL = 1;
    
    /**
     * INSERT TYPE VALUE
     */
    public static final int INSERT = 2;
    
    /**
     * UPDATE TYPE VALUE
     */
    public static final int UPDATE = 4;
    
    /**
     * DELETE TYPE VALUE
     */
    public static final int DELETE = 8;
    
    /**
     * UNKNOWN
     */
    public static final int UNKNOWN = 256;
}