/*================================================================================
 * �ý���			: ���α�������
 * �ҽ����� �̸�	: snis.can.system.d02000002.activity.SaveCDetailEduSchd.java
 * ���ϼ���		: �ڵ� ����
 * �ۼ���			: �ֹ���
 * ����			: 1.0.0
 * ��������		: 2007-01-03
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can_boa.boatstd.d06000016.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;

import snis.can_boa.common.util.SnisActivity;
import snis.can_boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� �Խù��� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther �ֹ���
* @version 1.0
*/


public class DelDtStrtMesur extends SnisActivity 
{
	public DelDtStrtMesur() { }
	
	/**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String	sucess ���ڿ�
     * @throws  none
     */    
	
    
    public String runActivity(PosContext ctx)
    {  
    	//����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}

    	saveStateDt_Strt_Mesur(ctx);         	
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
  	* <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
  	* @param   ctx		PosContext	����� : ���ں�ü������
  	* @return  none
  	* @throws  none
  	*/
  	protected void saveStateDt_Strt_Mesur(PosContext ctx) 
  	{
  		int nDeleteCount = 0; 

  		nDeleteCount = nDeleteCount + deleteDt_Strt_Mesur(ctx);


  		Util.setDeleteCount(ctx, nDeleteCount   );
  	}    
 
  	/**
      * <p> ���ں�ü������ ����</p>
      * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
      * @return  dmlcount	int		delete ���ڵ� ����
      * @throws  none
      */
     protected int deleteDt_Strt_Mesur(PosContext ctx) 
     {
		logger.logInfo("deleteDt_Strt_Mesur start============================");
		PosParameter param = new PosParameter();
		String	racerPerioNo    = (String) ctx.get("racerPerioNo");
		String	dt				= (String) ctx.get("dt");
		System.out.println("deleteDt_Strt_Mesur start============================" + "racerPerioNo:" + racerPerioNo);
		
		int i = 0;
		     
		param.setWhereClauseParameter(i++, Util.trim(racerPerioNo));
		param.setWhereClauseParameter(i++, Util.trim(dt));
		         
		int dmlcount = this.getDao("candao").delete("tbdn_dt_strt_mesur_dn002", param);
		 
		logger.logInfo("deleteDt_Strt_Mesur end============================");
		return dmlcount;
     }
}
