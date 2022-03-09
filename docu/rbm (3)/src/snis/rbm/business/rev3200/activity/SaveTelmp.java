/*================================================================================
 * �ý���			: �߸Ž��� �ʱ�ȭ
 * �ҽ����� �̸�	: snis.rbm.business.rev3200.activity.SaveTelmp.java
 * ���ϼ���		: �߸Ž��� �ʱ�ȭ
 * �ۼ���			: ��ȣö
 * ����			: 1.0.0
 * ��������		: 2012-01-19
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.rbm.business.rev3200.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.rbm.business.rev1010.activity.SaveData;
import snis.rbm.business.rev2010.activity.SavePrmRslt;
import snis.rbm.common.util.SnisActivity;
import snis.rbm.common.util.Util;

public class SaveTelmp extends SnisActivity {
	
	public SaveTelmp(){}

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
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        
        String sEvalYear = (String)ctx.get("ESTM_YEAR");	//�򰡳⵵
        String sEvalNum  = (String)ctx.get("ESTM_NUM");		//��ȸ��
        String sCommCd   = (String)ctx.get("DEPT_CD");		//����
        String sPrmStrDt = (String)ctx.get("PRM_STR_DT");	//�򰡱Ⱓ������
        String sPrmEndDt = (String)ctx.get("PRM_END_DT");	//�򰡱Ⱓ������
        
        String sDeptCd   = getDeptCd(sCommCd); //�μ�
        
        //�򰡸��� �Ǿ��ٸ� ���� �Ұ���
        SavePrmRslt SavePrmRslt = new SavePrmRslt();
        
        if( SavePrmRslt.getEndYn(sEvalYear, sEvalNum).equals("Y") ) {
        	try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_rbm");
        		Util.setSvcMsg(ctx, "�򰡸����� �Ϸ�Ǿ� �����Ͻ� �� �����ϴ�.");
        		
        		return;
        	}
        }
        
        //�߸Ž�������
        deleteTelmp(sEvalYear, sEvalNum, sDeptCd);
        
        //�߸Ž��� Insert
        nSaveCount = insertTelmp(sEvalYear, sEvalNum, sDeptCd, sPrmStrDt, sPrmEndDt);
        
        //���������� �߸Ž��� �ʱ�ȭ
        updateReleaInit(sEvalYear, sEvalNum, sDeptCd);
        
        SaveData sd = new SaveData();
        sd.updateByPass(sEvalYear, sEvalNum);  //�Ⱓ �߸Ž��� �����ڴ�  TOTAL_CNT = 99999, WK_DAY_CNT = 99999�� ����
        sd.updateTeamAvg(sEvalYear, sEvalNum); //�Ⱓ �߸Ž��� �������� ��� �߸ŰǼ��� ����� ����
        
        Util.setReturnParam(ctx, "RESULT", "0");
        Util.setSaveCount  (ctx, nSaveCount  );
        Util.setDeleteCount(ctx, nDeleteCount);
    }  
    
    /**
     * <p> �߸Ž��� �μ��� ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int deleteTelmp(String sEvalYear, String sEvalNum, String sDeptCd)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		//1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		//2.��ȸ��
        param.setValueParamter(i++, sDeptCd);		//3.�����ڵ�
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_d02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �߸Ž��� �μ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int insertTelmp(String sEvalYear, String sEvalNum, String sDeptCd, String sPrmStrDt, String sPrmEndDt)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;

        param.setValueParamter(i++, sEvalYear);		   //1.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		   //2.��ȸ��
        param.setValueParamter(i++, SESSION_USER_ID);  //3.�����ID(�����)
        param.setValueParamter(i++, SESSION_USER_ID);  //4.�����ID(������)
        param.setValueParamter(i++, sPrmStrDt);		   //5.��ȸ��
        param.setValueParamter(i++, sPrmEndDt);		   //6.��ȸ��
        param.setValueParamter(i++, sEvalYear);		   //7.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		   //8.��ȸ��
        param.setValueParamter(i++, sDeptCd);		   //9.�μ�(����)
        param.setValueParamter(i++, sEvalYear);		   //10.�򰡳⵵
        param.setValueParamter(i++, sEvalNum);		   //11.��ȸ��
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_i02", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ���������� �߸Ž��� �ʱ�ȭ</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateReleaInit(String sEvalYear, String sEvalNum, String sDeptCd)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, SESSION_USER_ID);  //1.�����ID(������)

        i = 0;
        param.setWhereClauseParameter(i++, sEvalYear); //2.�򰡳⵵
        param.setWhereClauseParameter(i++, sEvalNum);  //3.��ȸ��
        param.setWhereClauseParameter(i++, sDeptCd);   //4.�μ�(����)
        
        int dmlcount = this.getDao("rbmdao").update("rev3200_u03", param);
        
        return dmlcount;
    }
    
    /**
     * <p> �����ڵ带 �μ��ڵ�� ��ȯ </p>
     */
    protected String getDeptCd(String sCommCd) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, sCommCd);
        PosRowSet keyRecord = this.getDao("rbmdao").find("rev3200_s05", param);        
        	
        PosRow pr[] = keyRecord.getAllRow();
        
        String rtnKey = String.valueOf(pr[0].getAttribute("DEPT_CD"));
        
        return rtnKey;
    }
}