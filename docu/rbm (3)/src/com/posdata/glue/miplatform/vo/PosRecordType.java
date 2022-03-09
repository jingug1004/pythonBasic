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

/**
 * PosRecord�� Ÿ���� �����ϴ� ����� �����ϴ� �������̽�
 * 
 * @author ������
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