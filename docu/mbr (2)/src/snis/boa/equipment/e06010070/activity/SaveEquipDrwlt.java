/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquipDrwlt.java
 * ���ϼ���		: ���� /��Ʈ ��÷ ���� ���� (�Ϸ�)�� �����Ѵ�. 
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-22
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010070.activity;


import java.util.HashMap;

import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import snis.boa.common.util.Util;
import snis.boa.system.e01010220.activity.SaveProcess;


/**
* ���� ��Ʈ�� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveEquipDrwlt extends SnisActivity
{    
	public SaveEquipDrwlt()
    {
    }

    /**
     * <p> SaveStates Activity�� �����Ű�� ���� �޼ҵ� </p>
     * @param   ctx		PosContext	�����
     * @return  SUCCESS	String		sucess ���ڿ�
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	//����� ���� Ȯ��
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

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutEquipDrwlt");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
        	PosRecord record = ds.getRecord(i);
            saveEquipDrwlt(ctx, record);
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ������ Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipDrwlt(PosContext ctx, PosRecord record)
    {
//    	String stndYear = (String)record.getAttribute("STND_YEAR");
//    	String mbrCd = (String)record.getAttribute("MBR_CD" );
//    	Integer tms =  new Integer(((Double)record.getAttribute("TMS")).intValue());
    	/*
    	//�������ȸ ȸ�� ���°� �ּ�/�����÷ �̻��̸� ����� ���� �޽��� ��� 
    	PosRowSet prs = (new SearchEquipDrwlt()).getEquipDrwltRwoSet(stndYear, mbrCd, tms);
    	if(prs.hasNext()){
    		PosRow[] pr = prs.getAllRow();
    		if(pr!= null && pr.length>0){
    			pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN");
	    		String equipDrwltCmplYn = (String)pr[0].getAttribute("EQUIP_DRWLT_CMPL_YN");
	    		String raceTmsStatCd = (String)pr[0].getAttribute("RACE_TMS_STAT_CD");
	    		String raceTmsStatCdNm = (String)pr[0].getAttribute("RACE_TMS_STAT_CD_NM");
	    		if(!(raceTmsStatCd.equals("001")|| raceTmsStatCd.equals("010"))){
	    			throw new RuntimeException(stndYear +"�⵵ " + tms.intValue() +"ȸ�� ���´�  " + raceTmsStatCdNm + " �Դϴ�.\n ����/��Ʈ ��������� ������ �� �����ϴ�");
	    		}
    		}
    	}
    	*/
    	return updateEquipDrwlt(ctx, record);
    }
			
    /**
     * <p> ���  Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateEquipDrwlt(PosContext ctx, PosRecord record)
    {
    	PosParameter param = new PosParameter();
        int i = 0;
        String equipDrwltCmplYn = (String)record.getAttribute("EQUIP_DRWLT_CMPL_YN");
        if(equipDrwltCmplYn.equals("X"))return 0;
        String sStndYear = (String)record.getAttribute("STND_YEAR");
        String sMbrCd    = (String)record.getAttribute("MBR_CD");
        String sTms      = ((Double)record.getAttribute("TMS")).toString();

        // ������� üũ
        String sStatCd = "";
        SaveProcess sp = new SaveProcess();        
        sStatCd = sp.getRaceTmsStatCd(sStndYear, sMbrCd, sTms);

		if(!"010".equals(sStatCd) && !"001".equals(sStatCd)){
    		try { 
    			throw new Exception(); 
        	} catch(Exception e) {       		
        		this.rollbackTransaction("tx_boa");
        		Util.setSvcMsg(ctx, "����/��Ʈ��������� �Ϸ�Ǿ� Ȯ����Ҹ� �� �� �����ϴ�!!!");
        		return 0;        		
        	}			
		}

        Integer tms = new Integer(((Double)record.getAttribute("TMS")).intValue());
        param.setValueParamter(i++, equipDrwltCmplYn);
        param.setValueParamter(i++, "010"); //��� ��÷ ��� 
        param.setValueParamter(i++, SESSION_USER_ID);
        param.setValueParamter(i++, sStndYear);      
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));   
        param.setValueParamter(i++, tms);
	        
        int effectedRow = 0;
        effectedRow = this.getDao("boadao").update("tbeb_race_tms_uf001", param);
       
        //���μ��� ��Ȳ �α�
//        if(equipDrwltCmplYn.equals("Y")){
//        	String title = stndYear+"�� " + tms +"ȸ��  ������� ��� ���� �Ϸ�";
//            String content = stndYear+"�� " + tms +"ȸ��  ������� ��� ���� �Ϸ� �Ǿ����ϴ�";
//            new SaveProcessProg().insertProcessProg("006", stndYear, tms.toString(), "", title, content); 
//        }

        //���μ��� ���� ��Ȳ �α�(������-�����÷���Ȯ��-�Ϸ�/���)
        if(Util.trim(equipDrwltCmplYn).equals("Y")){
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
            hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
            hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
            hmProcess.put("DUTY_CD"  , "006");
            hmProcess.put("WORK_CD"  , "015");
            hmProcess.put("PROG_STAT", "001");
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            //SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
        }
        else if(Util.trim(equipDrwltCmplYn).equals("N")){
            HashMap hmProcess = new HashMap();
            hmProcess.put("STND_YEAR", record.getAttribute("STND_YEAR"));
            hmProcess.put("MBR_CD"   , record.getAttribute("MBR_CD"   ));
            hmProcess.put("TMS"      , record.getAttribute("TMS"      ));
            hmProcess.put("DUTY_CD"  , "006");
            hmProcess.put("WORK_CD"  , "015");
            hmProcess.put("PROG_STAT", "002");
            hmProcess.put("USER_ID",   SESSION_USER_ID);

            //SaveProcess sp = new SaveProcess();
            sp.saveProcess(hmProcess);
        }
        
        return effectedRow;
    }
}
