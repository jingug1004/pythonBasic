/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
==============================================================================*/

package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.context.PosContext;

/**
 * 조회 쿼리를 실행하고 그 결과값(RowSet)을 컨텍스트에 저장한다.
 * 
 * <xmp>
 * 바인딩 할 수 있는 데이터 타입은 Miplatform VariableList의 특정 Value이다.
 * 
 * 사용 방법 
 *  <activity name="SearchTest" class="com.posdata.glue.miplatform.biz.activity.PosSearch">
 *    <transition name="success" value="end" />
 *    <property name="resultkey" value="ds_emp" />
 *    <property name="sqlkey" value="emp.select.all" />
 *    <property name="param-count" value="1" />
 *    <property name="dao" value="testdao" />
 *    <property name="param0" value="DeptnoP" />
 *  </activity>  
 *  
 *  Property 설정
 *  sqlkey : query.xml의 Query ID 
 *  dao : applicationContext.xml의 DAO Bean ID
 *  param-count : Binding 할 개 수 (select * from emp where deptno=?)의 "?" 수
 *  param#(param0,param1...): Binding Value ("?"와 순서 일치 하여야 함)
 *  resultkey : Query 결과를 컨텍스트에 저장할 Key
 * </xmp>
 *  
 * @author  조성민
 * @see     com.posdata.glue.biz.activity.PosSearch
 */
public class PosSearch extends com.posdata.glue.biz.activity.PosSearch
{
    public String runActivity(PosContext ctx)
    {
        return super.runActivity(ctx);
    }
}