/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : ������
*@LastVersion    : 1.0
*    2007-10-01    ������
*        1.0       ���� ����
==============================================================================*/

package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * MiPlatform Dataset�� ���ڵ带 �������� ���ε��Ͽ� Delete SQL���� �����ϴ� Activity�̴�.<br>
 * MiPlatform Dataset�� ���� �� ���ڵ带 �����Ѵ�. <br>
 * Binding Parameter Type���� MiPlatform Dataset ���� �����Ѵ�. <br>
 * (�Ϲ� ������Ʈ Ÿ���� ����Ϸ��� com.posdata.glue.biz.activity.PosDelete�� ����ϸ� �ȴ�)
 * PosDelete Activity ���� ����� Delete�� �Ϸ�� Row �����̴�.<br>
 * �ڼ��� ������� ������ �����Ѵ�.<br>
 * 
 * <xmp>
 * <activity name="DeleteEmp" class="com.posdata.glue.miplatform.biz.activity.PosDelete">
 *    <transition name="success" value="SearchEmp" />
 *    <property name="param0" value="EMPNO" /> 
 *    <property name="sqlkey" value="emp.delete" />
 *    <property name="dao" value="testdao" />
 *    <property name="param-count" value="1" />
 *    <property name="dataset-id" value="input" />
 *    <property name="resultkey" value="DeletededCount" />
 * </activity>
 *  
 *  << Property ���� >>
 *  dataset-id : MiPlatform Dataset ID (���� ���ε� �Ǵ� ��ü�� PosDataset ������Ʈ)
 *  sqlkey : query.xml�� query id 
 *  dao : applicationContext.xml�� DAO id
 *  param-count : Binding �� �Ķ���� ���� (��?�� ����)
 *  param#(param0,param1...): Binding Value ("?"�� ���� ��ġ �Ͽ��� ��)
 *  resultkey : DML Operation ���� ����� ���ؽ�Ʈ�� ������ Key
 * </xmp>
 * 
 * @author ������
 */
public class PosDelete extends PosDMLActivity 
{
    public static final int DELETE_DML = PosRecord.DELETE;
    
    public String runActivity(PosContext ctx)
    {
        super.doDML(DELETE_DML, ctx);
        return PosBizControlConstants.SUCCESS;
    }
}