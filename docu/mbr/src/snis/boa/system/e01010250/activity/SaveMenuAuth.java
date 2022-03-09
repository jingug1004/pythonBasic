package snis.boa.system.e01010250.activity;

import java.util.ArrayList;
import java.util.List;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;
import snis.boa.common.util.UtilDb;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

public class SaveMenuAuth extends SnisActivity {
	public SaveMenuAuth(){
		
	}
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

        PosDataset ds;
        int        nSize        = 0;
        String     sDsName = "";
        	        
        sDsName = "dsUserListValue";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
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
    	String sDsName2  = "";
    	String sMenuId	 = "";
    	String sUserId	 = "";
    	
    	PosDataset ds;
    	PosDataset ds2;
        int nSize        = 0;
        int nSize2       = 0;
        int nTempCnt     = 0;
                
        String srchYn 	 = "";
        String sPerYn 	 = "";
        String sPerMnIp  = "";
        //String sLvl	  	 = "";
        
        List userlist = new ArrayList();
        
        sDsName2 	= "dsMenuGrpValue";		//�޴����Ѹ���Ʈ
        sDsName 	= "dsUserListValue";	//����ڸ���Ʈ

        String  curDateTime = "";
        UtilDb udb = new UtilDb();
        curDateTime = udb.getCurTime();

        System.out.println(ctx.get(sDsName2));
        
        if ( ctx.get(sDsName2) != null) {
        	System.out.println("---1---");        	
        	ds2			= (PosDataset) ctx.get(sDsName2);
        	nSize2 		= ds2.getRecordCount();
        	
        	for ( int j = 0; j < nSize2; j++ ) {		//�޴� ����Ʈ��ŭ �ݺ�
        		System.out.println("---2---");        	
        		PosRecord mrecord = ds2.getRecord(j);
        		
        		sMenuId = (String)mrecord.getAttribute("MN_ID");
    			
    			if(sMenuId == null) sMenuId="";
    			
    			if ( ctx.get(sDsName2) != null  && !sMenuId.equals("")) {
	    	        ds    = (PosDataset) ctx.get(sDsName);
	    	        nSize = ds.getRecordCount();
	    	        
	    	        for ( int i = 0; i < nSize; i++ ) {
	    	           System.out.println("---3---");
	    	           PosRecord record = ds.getRecord(i); 
	    	           nSaveCount += updateAuthMenu(sMenuId, curDateTime, record);
	    	           
	    	           sUserId = (String)record.getAttribute("USER_ID");
	    	           //System.out.println(sUserId);
	    	           if (userlist.indexOf(sUserId) < 0) userlist.add(sUserId);
	    	        } // end for
	            }	  // end if
        	}
        }
        
        Util.setSaveCount  (ctx, nSaveCount);
    }


     /**
     * <p> �޴����� �Է� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected int updateAuthMenu(String MenuID, String curDtm, PosRecord record) 
    {
 		// 1)���� �ڷ� ���翩��
 		// 2)�ڷᰡ ������ 
 		//   2-1) ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
 		// 3)�ڷᰡ ������ 
 		//   3-1) ������ �ִ� ��� �����ڷ� ����(��������:����), �ű��ڷ� �Է�(��������:����, ��������:���Ѵ�)
 		//   3-2) ������ ���� ��� ����ó��(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
    	
    	String sPerYn = "N";
 		String sPerAuthChk = "N";
 		
 		String sPerMnIp = record.getAttribute("PERSONAL_MN_IP").toString();
 		String sSrchYn 	= record.getAttribute("SRCH_YN").toString();
 		
 		
 		// 0) ���Ѻο� �Ǵ� ���� ���� üũ
 		boolean bAuthSet   = true;		// ������ �Ѱ��� �ִ� �� ����(������ false)
 		boolean bAuthExist = false;
 		int     dmlcount = 0;
 		int     dDelcount = 0;
 		int		dPercount = 0;
 		
 		if(record.getAttribute("PERSONAL_YN") != null) {
 			if (Double.valueOf(record.getAttribute("PERSONAL_YN").toString()) == 1) {
 				sPerYn = "Y";
 				sPerAuthChk = "Y";
 			}
 		}
 		if (sPerYn.equals("N")) {
 			sPerAuthChk = "N";
 		   	bAuthSet = false;
 		}
 		
 		// 1) ������ �ڷᰡ �����ϴ� ���� ��ȸ
 		PosParameter param = new PosParameter();    
 		PosParameter perparam = new PosParameter();
 		PosRowSet rowset;
 		int i = 0;
 		
 		String sUserId = Util.trim(record.getAttribute("USER_ID"));
 		String sStrDtTm = (String)record.getAttribute("STR_DT_TM");
 		String sMnId    = MenuID;
    	
 		param.setWhereClauseParameter(i++, sUserId);
 		param.setWhereClauseParameter(i++, sMnId);
 		param.setWhereClauseParameter(i++, curDtm);		// ���� ������ ���� ���θ� üũ
 		
 		rowset = this.getDao("boadao").find("e01010040_s02", param);
 		if (rowset.hasNext()) bAuthExist = true;
 		
 		// 2)�ڷᰡ ������ ������ �ִ� ��� �űԷ� �Է�(�������� : ����, �������� : ���Ѵ�)
 		if (bAuthExist == false && bAuthSet == true) {
 			System.out.println("case 1:");
 			param = new PosParameter();
 			i = 0;
 			param.setValueParamter(i++, sUserId);
 	        param.setValueParamter(i++, sMnId);  
 	        param.setValueParamter(i++, curDtm);        	    	     
 	        param.setValueParamter(i++, sPerYn);
	        param.setValueParamter(i++, SESSION_USER_ID);  
	        param.setValueParamter(i++, sPerMnIp); 
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPerAuthChk); 
	        param.setValueParamter(i++, curDtm);
	        param.setValueParamter(i++, sSrchYn);        	    	     
	        
 	        dmlcount += this.getDao("boadao").update("e01010250_i01", param);
 	        
 	        System.out.println("case 2:");
	        perparam = new PosParameter();
	        i = 0;
	        perparam.setValueParamter(i++, sMnId); 
	        perparam.setValueParamter(i++, SESSION_USER_ID);
	        perparam.setValueParamter(i++, sPerAuthChk); 
	        perparam.setValueParamter(i++, curDtm);
	        perparam.setValueParamter(i++, sUserId);
	        perparam.setValueParamter(i++, sPerMnIp); 
	        
	        dPercount += this.getDao("boadao").update("e01010250_i02", perparam);
 			
 	    // 3)�ڷᰡ ������
 		} else if (bAuthExist == true) {
 			System.out.println("case 3:");
 	    	// 3-1) ������ �ִ� ��� ���������� ����(��������:����) : �������ڰ� ���ú��� ���� ��쿡��
 			param = new PosParameter();
			i = 0;
	        param.setValueParamter(i++, sPerYn);           	    	     
	        param.setValueParamter(i++, sPerMnIp);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        param.setValueParamter(i++, sPerAuthChk);
	        param.setValueParamter(i++, SESSION_USER_ID);
	        i = 0;
	        param.setWhereClauseParameter(i++, sUserId);
	        param.setWhereClauseParameter(i++, sMnId);	        
	        param.setWhereClauseParameter(i++, sStrDtTm);	

	        dmlcount += this.getDao("boadao").update("e01010250_u01", param);
	        
	        System.out.println("case 4:");
	        perparam = new PosParameter();
	        i = 0;
	        perparam.setValueParamter(i++, sMnId); 
	        perparam.setValueParamter(i++, SESSION_USER_ID);
	        perparam.setValueParamter(i++, sPerAuthChk); 
	        perparam.setValueParamter(i++, curDtm);
	        perparam.setValueParamter(i++, sUserId);
	        perparam.setValueParamter(i++, sPerMnIp); 
 	        
 	        dPercount += this.getDao("boadao").update("e01010250_i02", perparam);
 		}		
        
        return dmlcount;
    }
  
}
