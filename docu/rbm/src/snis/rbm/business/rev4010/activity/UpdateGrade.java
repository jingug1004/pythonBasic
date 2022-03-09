/*================================================================================
 * �ý���			: �������� �� ��޻���
 * �ҽ����� �̸�	: snis.rbm.business.rev4010.activity.UpdateGrade.java
 * ���ϼ���		: �������� �� ��޻���
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2011-10-24
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev4010.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SaveFile;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class UpdateGrade extends SnisActivity {
	
	public UpdateGrade(){}

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
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount = 0;
    	int nSize      = 0;
    	
    	String sDsName   = "";
    	String sEvalYear = (String)ctx.get("ESTM_YEAR");
        String sEvalNum  = (String)ctx.get("ESTM_NUM");
        
        PosDataset ds;
        
        
        SavePrmRslt SavePrmRslt = new SavePrmRslt();
        
        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("N") ) {
        	try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ�� ��޻����� �� �� �ֽ��ϴ�.");
        		
        		return;
        	}
        }
        
        //delete
        deleteTotalRslt(sEvalYear, sEvalNum);
        
        //insert
        nSaveCount = insertTotalRslt(sEvalYear, sEvalNum);
    	
        updateGrade(sEvalYear, sEvalNum);	//��޻��� ����/�ð� update

        Util.setSaveCount(ctx, nSaveCount);
    }
    
    /**
     * <p> �������� �� ��޻��� </p>
     * @param   sEvalYear	�򰡳⵵
     * 			sEvalNum	��ȸ��
     * @return  dmlcount	int		update ����
     * @throws  none
     */
    protected int updateGrade(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setValueParamter(i++, SESSION_USER_ID);  	// ����� ID
        param.setValueParamter(i++, sEvalYear);         // �򰡳⵵
        param.setValueParamter(i++, sEvalNum);          // ��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_u01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������� �� ��޻��� ���� </p>
     * @param   sEvalYear	�򰡳⵵
     * 			sEvalNum	��ȸ��
     * @return  dmlcount	int		update ����
     * @throws  none
     */
    protected int deleteTotalRslt(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        	        		        
        param.setWhereClauseParameter(i++, sEvalYear);         // �򰡳⵵
        param.setWhereClauseParameter(i++, sEvalNum);          // ��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev4010_d01", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �������� �� ��޻��� insert</p>
     * @param   sEvalYear	�򰡳⵵
     * 			sEvalNum	��ȸ��
     * @return  dmlcount	int		update ����
     * @throws  none
     */
    protected int insertTotalRslt(String sEvalYear, String sEvalNum) 
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
               
        param.setValueParamter(i++, SESSION_USER_ID);   
        
        param.setValueParamter(i++, sEvalYear);  // 1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 2.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 3.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 4.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 5.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 6.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 7.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 8.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  // 9.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  //10.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��
        param.setValueParamter(i++, sEvalYear);  //11.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);   //   ��ȸ��

        //2016�� ������ ���Կ� ���� �ٹ����� 1������ �ټ��� ��� ó�� 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i03", param);
        
        
        /* 2011��,2013�� : �����оߺ��� �����򰡵�� ���� 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i01", param);
        */ 
        

        /* 2012�⵵ : �μ��׷캰�� �����򰡵�� ����(2012.11.24) 
        int dmlcount = this.getDao("rbmdao").update("rev4010_i02", param);
        */
        
        return dmlcount;
    }
}
