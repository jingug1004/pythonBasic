/*================================================================================
 * �ý���			: 2�� ���߽���  ����   ����
 * �ҽ����� �̸�	: snis.can.system.d02000008.activity.SaveSndSeltExam3.java
 * ���ϼ���		: 2�� ���߽��� ��������
 * �ۼ���			: ���μ�
 * ����			: 1.0.0
 * ��������		: 2008-02-21
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.can.cyclestd.d02000008.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.can.common.util.SnisActivity;
import snis.can.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� 2�� ���߽��� ���� ������ ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ���μ�
* @version 1.0
*/
public class SaveSndSeltExam3  extends SnisActivity
{    
	
    public SaveSndSeltExam3()
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
        PosDataset ds;
        int        nSize        = 0;
        String     sDsName      = "";
        
        sDsName = "dsSndSeltExam3";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo("dsSndSeltExam3============>"+record);
	        }
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
    	String sDsName   = "";
    	
    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;
        sDsName = "dsSndSeltExam3";
        if ( ctx.get(sDsName) != null ) {
	        ds   		 = (PosDataset) ctx.get(sDsName);
	        nSize 		 = ds.getRecordCount();

	         for ( int i = 0; i < nSize; i++ ) {
	            PosRecord record = ds.getRecord(i);         
	                    
	            if ( record.getType() == com.posdata.glue.miplatform.vo.PosRecord.UPDATE 
	    	    || record.getType() == com.posdata.glue.miplatform.vo.PosRecord.INSERT){ 
	  				nTempCnt = insertSaveSndSeltExam3(record);
	  				nSaveCount = nSaveCount + nTempCnt;
	    	    }
	        } // end for
        }     // end if
        Util.setSaveCount  (ctx, nSaveCount     );
    }
    
    
    /**
     * <p> 2�����߽��� ����  �Է�  </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int insertSaveSndSeltExam3(PosRecord record) 
    {
   	    logger.logInfo("==========================  2�� ���߽������   �Է�   ============================");
                
        //PosParameter param = new PosParameter();  
   	    PosParameter param = null;
        int i = 0;
        int dmlcount = 0;
			
        String a1_scr = Util.trim(String.valueOf(record.getAttribute("A1_SCR")));
        String a2_scr = Util.trim(String.valueOf(record.getAttribute("A2_SCR")));
        String a3_scr = Util.trim(String.valueOf(record.getAttribute("A3_SCR")));
        String a4_scr = Util.trim(String.valueOf(record.getAttribute("A4_SCR")));
        String a5_scr = Util.trim(String.valueOf(record.getAttribute("A5_SCR")));
        
        String b1_scr = Util.trim(String.valueOf(record.getAttribute("B1_SCR")));
        String b2_scr = Util.trim(String.valueOf(record.getAttribute("B2_SCR")));
        String b3_scr = Util.trim(String.valueOf(record.getAttribute("B3_SCR")));
        String b4_scr = Util.trim(String.valueOf(record.getAttribute("B4_SCR")));
        String b5_scr = Util.trim(String.valueOf(record.getAttribute("B5_SCR")));
        
        String c1_scr = Util.trim(String.valueOf(record.getAttribute("C1_SCR")));
        String c2_scr = Util.trim(String.valueOf(record.getAttribute("C2_SCR")));
        String c3_scr = Util.trim(String.valueOf(record.getAttribute("C3_SCR")));
        String c4_scr = Util.trim(String.valueOf(record.getAttribute("C4_SCR")));
        String c5_scr = Util.trim(String.valueOf(record.getAttribute("C5_SCR")));
        
        String d1_scr = Util.trim(String.valueOf(record.getAttribute("D1_SCR")));
        String d2_scr = Util.trim(String.valueOf(record.getAttribute("D2_SCR")));
        String d3_scr = Util.trim(String.valueOf(record.getAttribute("D3_SCR")));
        String d4_scr = Util.trim(String.valueOf(record.getAttribute("D4_SCR")));
        String d5_scr = Util.trim(String.valueOf(record.getAttribute("D5_SCR")));
        
        String e1_scr = Util.trim(String.valueOf(record.getAttribute("E1_SCR")));
        String e2_scr = Util.trim(String.valueOf(record.getAttribute("E2_SCR")));
        String e3_scr = Util.trim(String.valueOf(record.getAttribute("E3_SCR")));
        String e4_scr = Util.trim(String.valueOf(record.getAttribute("E4_SCR")));
        String e5_scr = Util.trim(String.valueOf(record.getAttribute("E5_SCR")));
        
        if(!a1_scr.equals("")){
       	i = 0;
        param = new PosParameter();
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "611");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "611");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
        	 			 	
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }
        
        if(!a2_scr.equals("")){
   		i = 0;  
        param = new PosParameter();	
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "612");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "612");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);

   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }        
        
        if(!a3_scr.equals("")){
   		i = 0;      
        param = new PosParameter();	
        	
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "613");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "613");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);

   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }     
        
        if(!a4_scr.equals("")){
   		i = 0;  
        param = new PosParameter();			
   	       
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "614");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "614");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   			
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
        }   
        
        if(!a5_scr.equals("")){
   		i = 0;
        param = new PosParameter();	

        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "615");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   		
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
   		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
   		param.setValueParamter(i++, "615");
   		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
   		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
   		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
   		param.setValueParamter(i++, SESSION_USER_ID);
   			
   			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
       }    
        if(!b1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "621");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "621");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!b2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "622");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "622");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!b3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "623");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "623");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!b4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "624");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "624");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!b5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "625");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "625");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
            if(!c1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "631");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "631");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!c2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "632");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "632");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!c3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "633");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "633");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!c4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "634");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "634");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!c5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "635");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "635");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           }
            if(!d1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "641");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "641");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!d2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "642");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "642");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!d3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "643");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "643");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!d4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "644");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "644");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!d5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "645");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "645");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
            if(!e1_scr.equals("")){
           	i = 0;
            param = new PosParameter();
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "651");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "651");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P1_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P1_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
            	 			 	
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }
            
            if(!e2_scr.equals("")){
       		i = 0;  
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "652");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "652");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P2_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P2_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }        
            
            if(!e3_scr.equals("")){
       		i = 0;      
            param = new PosParameter();	
            	
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "653");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "653");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P3_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P3_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);

       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }     
            
            if(!e4_scr.equals("")){
       		i = 0;  
            param = new PosParameter();			
       	       
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "654");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "654");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P4_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P4_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
            }   
            
            if(!e5_scr.equals("")){
       		i = 0;
            param = new PosParameter();	

            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "655");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       		
            param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_PERIO_NO")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("EXAM_CD")));
       		param.setValueParamter(i++, Util.trim(record.getAttribute("ITEM_GBN_CD")));
       		param.setValueParamter(i++, "655");
       		param.setValueParamter(i++, Util.trim(record.getAttribute("CAND_NO")));	
       		param.setValueParamter(i++, Util.trim(String.valueOf(record.getAttribute("P5_SCR"))));	
       		param.setValueParamter(i++, record.getAttribute("P5_SCR"));
       		param.setValueParamter(i++, SESSION_USER_ID);
       			
       			dmlcount += this.getDao("candao").insert("tbdb_perio_exam_Snd_ib001", param);
           } 
   		logger.logInfo("insertPerio_Exam3 end============================");
   		return dmlcount;
       }
}