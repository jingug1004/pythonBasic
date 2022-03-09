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

import com.posdata.glue.context.PosContext;

/**
 * ��ȸ ������ �����ϰ� �� �����(RowSet)�� ���ؽ�Ʈ�� �����Ѵ�.
 * 
 * <xmp>
 * ���ε� �� �� �ִ� ������ Ÿ���� Miplatform VariableList�� Ư�� Value�̴�.
 * 
 * ��� ��� 
 *  <activity name="SearchTest" class="com.posdata.glue.miplatform.biz.activity.PosSearch">
 *    <transition name="success" value="end" />
 *    <property name="resultkey" value="ds_emp" />
 *    <property name="sqlkey" value="emp.select.all" />
 *    <property name="param-count" value="1" />
 *    <property name="dao" value="testdao" />
 *    <property name="param0" value="DeptnoP" />
 *  </activity>  
 *  
 *  Property ����
 *  sqlkey : query.xml�� Query ID 
 *  dao : applicationContext.xml�� DAO Bean ID
 *  param-count : Binding �� �� �� (select * from emp where deptno=?)�� "?" ��
 *  param#(param0,param1...): Binding Value ("?"�� ���� ��ġ �Ͽ��� ��)
 *  resultkey : Query ����� ���ؽ�Ʈ�� ������ Key
 * </xmp>
 *  
 * @author  ������
 * @see     com.posdata.glue.biz.activity.PosSearch
 */
public class PosSearch extends com.posdata.glue.biz.activity.PosSearch
{
    public String runActivity(PosContext ctx)
    {
        return super.runActivity(ctx);
    }
}