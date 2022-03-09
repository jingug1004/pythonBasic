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

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
 * MiPlatform Dataset의 레코드를 쿼리문에 바인딩하여 Update SQL문을 수행하는 Activity이다.<br>
 * MiPlatform Dataset의 복수 개 레코드를 지원한다. <br>
 * Binding Parameter Type으로 MiPlatform Dataset 만을 지원한다. <br>
 * (일반 오브젝트 타입을 사용하려면 com.posdata.glue.biz.activity.PosUpdate를 사용하면 된다)
 * PosUpdate Activity 수행 결과는 Update가 완료된 Row 개수이다.<br>
 * 자세한 사용방법은 다음을 참조한다.<br>
 * 
 * <xmp>
 *  <activity name="UpdateEmp" class="com.posdata.glue.miplatform.biz.activity.PosModify">
 *    <transition name="success" value="SearchEmp" />
 *    <property name="param0" value="SAL" />
 *    <property name="param1" value="ENAME" />
 *    <property name="param2" value="EMPNO" />
 *    <property name="sqlkey" value="emp.update" />
 *    <property name="dao" value="testdao" />
 *    <property name="param-count" value="3" />
 *    <property name="dataset-id" value="input" />
 *    <property name="resultkey" value="UpdatedCount" />
 *  </activity>
 *  
 *  << Property 설명 >>
 *  dataset-id : MiPlatform Dataset ID (실제 바인딩 되는 객체는 PosDataset 오브젝트)
 *  sqlkey : query.xml의 query id 
 *  dao : applicationContext.xml의 DAO id
 *  param-count : Binding 할 파라미터 개수 (“?” 개수)
 *  param#(param0,param1...): Binding Value ("?"와 순서 일치 하여야 함)
 *  resultkey : DML Operation 수행 결과를 컨텍스트에 저장할 Key
 * </xmp>
 * 
 * @author 조성민
 */
public class PosModify extends PosDMLActivity
{
    public static final int UPDATE_DML = PosRecord.UPDATE;
    
    public String runActivity(PosContext ctx)
    {
        super.doDML(UPDATE_DML, ctx);
        return PosBizControlConstants.SUCCESS;
    }
}