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
 * MiPlatform Dataset�� ���ڵ带 �������� ���ε��Ͽ� Insert SQL���� �����ϴ� Activity�̴�.<br>
 * Activity �������� �߰� �� Row �Ǽ��̴�.<br>
 * MiPlatform Dataset�� ���� �� ���ڵ带 �����Ѵ�. <br>
 * Binding Parameter Type���� MiPlatform Dataset ���� �����Ѵ�. <br>
 * (�Ϲ� ������Ʈ Ÿ���� ����Ϸ��� com.posdata.glue.biz.activity.PosInsert�� ����ϸ� �ȴ�)
 * �ڼ��� ������� ������ �����Ѵ�.<br>
 * 
 * <xmp>
 * <activity name="InsertEmp" class="com.posdata.glue.miplatform.biz.activity.PosInsert">
 *    <transition name="success" value="SearchEmp" />
 *    <property name="param0" value="EMPNO" />    
 *    <property name="param1" value="ENAME" />
 *    <property name="param2" value="SAL" />
 *    <property name="param3" value="COMM" />
 *    <property name="param4" value="DEPTNO" />
 *    <property name="sqlkey" value="emp.insert" />
 *    <property name="dao" value="testdao" />
 *    <property name="param-count" value="5" />
 *    <property name="dataset-id" value="input" />
 *    <property name="resultkey" value="InsertedCount" />
 *  </activity>
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
public class PosInsert extends PosDMLActivity 
{
    public static final int INSERT_DML = PosRecord.INSERT;

    public String runActivity(PosContext ctx)
    {        
        super.doDML(INSERT_DML, ctx);
        return PosBizControlConstants.SUCCESS;
    }
}