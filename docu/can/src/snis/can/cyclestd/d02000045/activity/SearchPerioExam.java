/*================================================================================
 * �ý���		    : �л����
 * �ҽ����� �̸�	: snis.can.cyclestd.d02000008.activity.SearchPerioExam.java
 * ���ϼ���		: �����ڰݽ���
 * �ۼ���		    : ������
 * ����			: 1.0.0
 * ��������		: 2007-01-18
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000045.activity;

import java.util.ArrayList;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;
/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/


public class SearchPerioExam extends SnisActivity 
{
	public SearchPerioExam() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        return PosBizControlConstants.SUCCESS;
    }
   
    
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	PosContext ���񱸺� �����
     * @return  none
     * @throws  none
     */
     protected void searchState(PosContext ctx) 
     {
 		ArrayList alItemCd = new ArrayList();

 		// �����ŽǱ� ��ȸ
		alItemCd.clear();
		alItemCd.add("301");
		alItemCd.add("302");
		alItemCd.add("303");
		alItemCd.add("304");
		alItemCd.add("305");
		alItemCd.add("306");
		 
		alItemCd.add(ctx.get("RACER_PERIO_NO"));
		alItemCd.add("004");
		alItemCd.add("003");
		alItemCd.add(ctx.get("RACER_PERIO_NO"));

     	String sResultKey = "dsPerioExam1";
        ctx.put(sResultKey, searchPerioExam(ctx, alItemCd));
        Util.addResultKey(ctx, sResultKey);
     }
     
    /**
     * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
     * @param   ctx	 PosContext	ȯ�����ǥ �����
     * @return  none
     * @throws  none
     */
	protected PosRowSet searchPerioExam(PosContext ctx, ArrayList alItemCd)
	{
		// �����ŽǼ� ��ȸ
		PosParameter param = new PosParameter();

		int i = 0;
		int j = 0;
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j  ));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));
		param.setWhereClauseParameter(i++, alItemCd.get(j++));

		return this.getDao("candao").find("tbdb_in_exam_fb001", param);
	}
}
