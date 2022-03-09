/*================================================================================
 * �ý���			: ������ 
 * �ҽ����� �̸�	: snis.boa.equipment.e06010010.activity.SaveEquipLot.java
 * ���ϼ���		: ���� /��Ʈ ��÷ ����� �����Ѵ�.  
 * �ۼ���			: �輺�� 
 * ����			: 1.0.0
 * ��������		: 2007-11-01
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.equipment.e06010040.activity;


import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

/**
* ����/��Ʈ ���� �̷¿� ���� ������ ���, ����, ���� �Ѵ�.
* ����/��Ʈ ���� ������ ���Ͽ� ���, ���� �Ѵ�. 
* @auther �輺�� 
* @version 1.0
*/
public class SaveEquipReprHis extends SnisActivity
{    
    public SaveEquipReprHis()
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
    	if ( setUserInfo(ctx)== null || !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
        saveState(ctx);
        return PosBizControlConstants.SUCCESS;
    }

   
    
    protected void saveState(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
    	PosDataset dsParts;
        int nSize        = 0;
        
        ds      = (PosDataset) ctx.get("dsOutEquipReprHis");
        dsParts = (PosDataset) ctx.get("dsOutOrderPartsList");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipReprHis(record);
            	//���� ���� ���� 
    			deleteEquipReprTpeHis(record);
    			//�����ǰ ���� 
    			deleteEquipReprparts(record);    			
            } else {
            	nSaveCount = nSaveCount + saveEquipReprHis(record);
            	//���� ���� ������ ����  
    			deleteEquipReprTpeHis(record); 
    			insertEquipReprTpeHis(record);
    			
    			saveEquipReprParts(record, dsParts);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
  
    /**
     * <p> �����̷� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipReprHis(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	String sReprMaxSeq = "";
    	
    	if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){
    		// �����Ϸù�ȣ ����
    		sReprMaxSeq = getMaxReprSeq(record);
    		
    		// �����Ϸù�ȣ ���� 
    		record.setAttribute("REPR_SEQ", sReprMaxSeq);
    		
    	} 
    	effectedRowCnt = mergeEquipReprHis(record);
    	
        return effectedRowCnt;
    }

    /**
     * <p> �����̷� ��� /����  </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int mergeEquipReprHis(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
    	String homepageYN = (String)record.getAttribute("HOMEPAGE_YN");
    	homepageYN = homepageYN != null && homepageYN.equals("1") ? "Y" :  "N";
    	
    	
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("MBR_CD"));   
    	param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));  
    	param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_DT"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_TPE_CDS"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_TPE_NMS"));  
    	param.setValueParamter(i++, record.getAttribute("MJR_PARTS"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_DESC"));  
    	param.setValueParamter(i++, record.getAttribute("REPR_NM"));  
    	param.setValueParamter(i++, homepageYN);  
    	param.setValueParamter(i++, record.getAttribute("HOMEPAGE_DESC")); 
    	param.setValueParamter(i++, record.getAttribute("REL_STND_YEAR"));  
    	param.setValueParamter(i++, record.getAttribute("REL_MBR_CD"));  
    	param.setValueParamter(i++, record.getAttribute("REL_TMS"));  
    	param.setValueParamter(i++, record.getAttribute("REL_DAY_ORD"));  
    	param.setValueParamter(i++, record.getAttribute("REL_RACE_NO"));  
    	param.setValueParamter(i++, record.getAttribute("REL_RACE_REG_NO"));  
    	param.setValueParamter(i++, SESSION_USER_ID);
         
        return this.getDao("boadao").update("tbef_equip_repr_his_mf001", param);
    }
    
    /**
     * ���� ���� ��� 
     * @param record
     * @return
     */
    protected int insertEquipReprTpeHis(PosRecord record) 
    {
        int effectedRowCnt = 0;
        
        String[] reprTpeCds = ((String)record.getAttribute("REPR_TPE_CDS")).split(",");
    	for(int j=0; j<reprTpeCds.length; j++){
    		PosParameter param = new PosParameter();
            int i = 0;
	        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
	        param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
	    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
	    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
	    	param.setValueParamter(i++, reprTpeCds[j]);
	    	effectedRowCnt += this.getDao("boadao").update("tbef_equip_repr_tpe_his_if001", param);
    	}
        
        return  effectedRowCnt;
    }

    

    /**
     * <p>���� �̷�  ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipReprHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_equip_repr_his_df001", param);
    }
    
    
    /**
     * ���� ���� ���� 
     * @param record
     * @return
     */
    protected int deleteEquipReprTpeHis(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
        
        return  this.getDao("boadao").update("tbef_equip_repr_tpe_his_df001", param);
    }
    
    /**
     * �����ǰ���� ���� 
     * @param record
     * @return
     */
    protected int deleteEquipReprparts(PosRecord record) 
    {
    	int dCount = 0;
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("REPR_SEQ")); 
    	
    	dCount  = this.getDao("boadao").update("tbef_equip_repr_his_delv_parts_del", param);        
    	//dCount += this.getDao("boadao").update("tbef_equip_repr_his_delv_del", param);
                
        return dCount;
        
    }

    /**
     * <p> �����̷� Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipReprParts(PosRecord record, PosDataset dsParts)
    {
    	int mcount = 0;
    	String stndYear = record.getAttribute("STND_YEAR").toString();
        String mbrCd    = record.getAttribute("MBR_CD").toString();
        String delvSeq  = record.getAttribute("DELV_SEQ").toString();
        String sDelvseq = "";
        String stndYearParts = "";
        String mbrCdParts    = "";
        String delvSeqParts  = "";
        
        // �����̷� ���¸� üũ�Ͽ� �ű��� ��� ����ȣ ����
		// �����Ϸù�ȣ ��ȸ
		sDelvseq = getMaxDelvSeq(record);	// ��� �ڷᰡ ���� ��� ��� ���̺� Insert
    	MergeTbefDelv(record, sDelvseq);
    	
    	// ���γ���
        int nSize = dsParts.getRecordCount();
        
    	for ( int i = 0; i < nSize; i++ ){
            PosRecord recordParts = dsParts.getRecord(i);
            stndYearParts = recordParts.getAttribute("STND_YEAR").toString();
            mbrCdParts    = recordParts.getAttribute("MBR_CD").toString();
            delvSeqParts  = recordParts.getAttribute("DELV_SEQ").toString();
        	
            if (stndYear.equals(stndYearParts) && mbrCd.equals(mbrCdParts) &&
            	delvSeq.equals(delvSeqParts)) {
            	
	            if (recordParts.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
	                int j = 0;
	                PosParameter param = new PosParameter();
	                param.setValueParamter(j++, stndYearParts);  
	            	param.setValueParamter(j++, mbrCdParts);   
	            	param.setValueParamter(j++, sDelvseq);
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_CD"));  
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_YEAR"));   
	                
	            	mcount += this.getDao("boadao").update("tbef_equip_parts_repr_df002", param);   
	                
	            	//��� ���� ����
	            	//updatePartsNowUnitCnt(recordParts, "DELETE"); ���ͺ�Ʈ���� �� �������� �������� ���� BY CHO

	            } else {
	                int j = 0;
	                PosParameter param = new PosParameter();
	                param.setValueParamter(j++, stndYearParts);  
	            	param.setValueParamter(j++, mbrCdParts);   
	            	param.setValueParamter(j++, sDelvseq);
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_CD"));  
	            	param.setValueParamter(j++, recordParts.getAttribute("PARTS_YEAR"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("DELV_CNT"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("RECEPT_ID"));   
	            	param.setValueParamter(j++, recordParts.getAttribute("RMK"));   
	            	param.setValueParamter(j++, SESSION_USER_ID);
	                
	            	mcount += this.getDao("boadao").update("tbef_delv_parts_repr_mf001", param);	            }
	            
	            	//��� ���� ����
	            	//updatePartsNowUnitCnt(recordParts, "MERGE"); ���ͺ�Ʈ���� �� �������� �������� ���� BY CHO
            }
        }
        return mcount;
    }

    /**
     * <p> �����Ϸù�ȣ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getMaxReprSeq(PosRecord record) 
    {
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, record.getAttribute("STND_YEAR"));	//���س⵵        

        PosRowSet keyRecord = this.getDao("boadao").find("tbef_equip_repr_his_max",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
       
        rtnKey = String.valueOf(pr[0].getAttribute("REPR_SEQ_MAX"));

        return rtnKey;
    }
        
    /**
     * <p> ����Ϸù�ȣ ���� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected String getMaxDelvSeq(PosRecord record) 
    {
        String stndYear = record.getAttribute("STND_YEAR").toString();
        String reprSeq  = record.getAttribute("REPR_SEQ").toString();
    	
        String rtnKey = "";
        
        PosParameter param = new PosParameter();
        int i = 0;
        param.setWhereClauseParameter(i++, stndYear);	//���س⵵        
        param.setWhereClauseParameter(i++, reprSeq);	//�����Ϸù�ȣ        
        param.setWhereClauseParameter(i++, stndYear);	//���س⵵        
        
        PosRowSet keyRecord = this.getDao("boadao").find("tbef_equip_repr_his_delv_max",param);        
        	
        PosRow pr[] = keyRecord.getAllRow();     
        
        // �ڷᰡ ������ ���̺� �űԷ� �߰�
        if (pr[0].getAttribute("CUR_SEQ").toString().trim().equals("0")) {
        	rtnKey = String.valueOf(pr[0].getAttribute("NEW_SEQ"));
        } else {
        	rtnKey = String.valueOf(pr[0].getAttribute("CUR_SEQ"));
        } 

        return rtnKey;
    }
        
    /**
     * <p> ����ǰ ��� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int MergeTbefDelv(PosRecord record, String sDelvSeq) 
    {			           
        PosParameter param = new PosParameter();   
        
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR"));       // 0.���س⵵
        param.setValueParamter(i++, record.getAttribute("MBR_CD"));  		 // 1.�������ڵ�
        param.setValueParamter(i++, sDelvSeq);       					     // 2.����ȣ
        param.setValueParamter(i++, record.getAttribute("REPR_DT"));         // 3.�������
        param.setValueParamter(i++, record.getAttribute("MJR_PARTS")+" ("+record.getAttribute("REPR_DESC")+")");       // 4.�����
        param.setValueParamter(i++, record.getAttribute("RECEPT_ID"));      	 // 5.������
        param.setValueParamter(i++, SESSION_USER_NM);          			     // 6.������(�Է��ڸ�)	
        param.setValueParamter(i++, SESSION_USER_ID);                        // 7.�Է��ڻ��
        param.setValueParamter(i++, record.getAttribute("REPR_SEQ"));        // 4.�����Ϸù�ȣ
                
        int dmlcount = this.getDao("boadao").update("tbef_equip_repr_his_delv_merg", param);
        
        return dmlcount;
    }
    
    /**
     * <p> ��ǰ���̺� ����� Update </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int updatePartsNowUnitCnt(PosRecord record, String recordTpe)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, recordTpe); 
        param.setValueParamter(i++, record.getAttribute("DELV_CNT")); 
    	param.setValueParamter(i++, record.getAttribute("DELV_CNT")); 
    	param.setValueParamter(i++, record.getAttribute("ORG_DELV_CNT")); 
    	param.setValueParamter(i++, SESSION_USER_ID);
    	param.setValueParamter(i++, record.getAttribute("STND_YEAR")); 
    	param.setValueParamter(i++, record.getAttribute("MBR_CD")); 
        param.setValueParamter(i++, record.getAttribute("PARTS_CD"));
        param.setValueParamter(i++, record.getAttribute("PARTS_YEAR"));
	        
        return this.getDao("boadao").update("tbef_delv_parts_uf702", param);
    }
    
}
