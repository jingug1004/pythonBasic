/*================================================================================
 * �ý���			: ������
 * �ҽ����� �̸�	: snis.boa.organization.e02040050.activity.SearchRaceConfirm.java
 * ���ϼ���		: �������ȸ
 * �ۼ���			: ������
 * ����			: 1.0.0
 * ��������		: 2007-10-26
 * ������������	: 
 * ����������		: 
 * ������������	: 
=================================================================================*/
package snis.boa.organization.e02040050.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowImpl;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.dao.vo.PosRowSetImpl;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* �� Ŭ������ Ŭ���̾�Ʈ�κ����� ���� ����Ÿ���� �޾� �ش� Query�� �������� �´� �Ķ���͸�
* �����Ͽ� ������/������� ����(�Է�/����)�� �����ϴ� Ŭ�����̴�.
* @auther ������
* @version 1.0
*/
public class SearchRaceConfirm extends SnisActivity
{    
	
    public SearchRaceConfirm()
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
    	// ����� ���� Ȯ��
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        searchState(ctx);
        
        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ��ȸ���� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void searchState(PosContext ctx) 
    {
    	int i =0;
    	PosRowSet rowsetRaces = null;
        String organType = (String)ctx.get("ORGAN_TYPE");
        
        //ORGAN_TYPE - NEW REAL TEMP
        //SAVE_TYPE - CONFIRM SAVE_TO_ORGAN SAVE
        
        // �������ȸ
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRaces = searchTempOrgan(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRaces = searchOrgan(ctx);
        }
        
    	PosColumnDef[] columnDef = rowsetRaces.getColumnDefs();
        PosRow rowRaces[] = rowsetRaces.getAllRow();

        // ���� �̷�� ���ٸ�
		String sRaceNo  = "0";
    	if ( rowsetRaces.hasNext() ) {
        	List   raceList = new java.util.ArrayList();

    		for ( int j = 0; j < rowRaces.length; j++ ) {
    			PosRow rowRace = rowRaces[j];
    			
    			// �� ������ ������ �ش� ���� ����
    			if ( sRaceNo.equals(rowRace.getAttribute("RACE_NO")) ) {
					raceList.add(rowRace);
    			} else {
    				// ���ֹ�ȣ�� ����� ��� �ش� ������ �ش� ���� Dataset����
    				if ( !sRaceNo.equals("0") ) {
    			    	PosRowSet rowsetRace = new PosRowSetImpl(raceList);
    			    	rowsetRace.setColumnDefs(columnDef);

    			    	String sResultKey = "dsOutRace_" + Integer.parseInt(sRaceNo);
    		            ctx.put(sResultKey, rowsetRace);
    		            Util.addResultKey(ctx, sResultKey);
    				}

    				sRaceNo = (String) rowRace.getAttribute("RACE_NO");
    				raceList = new java.util.ArrayList();
					raceList.add(rowRace);
    			}
    		}

    		// ������ ���� ����
	    	PosRowSet rowsetRace = new PosRowSetImpl(raceList);
	    	rowsetRace.setColumnDefs(columnDef);

	    	String sResultKey = "dsOutRace_" + Integer.parseInt(sRaceNo);
            ctx.put(sResultKey, rowsetRace);
            Util.addResultKey(ctx, sResultKey);
    	}

        // ���� ��ȸ
    	PosParameter param = new PosParameter();
    	i = 0;
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        PosRowSet rowset = this.getDao("boadao").find("tbeb_race_fb002", param);

        PosRow rows[] = rowset.getAllRow();
        
        // ���ָ��� ����ִ� ������ ã�Ƽ� ������ �ش��ϴ� recode����
    	if ( rowset.hasNext() ) {
        	for ( int k = 1; k <= rows.length; k++ ) {
        		
            	String sTempRaceNo   =  (String)     rows[k - 1].getAttribute("RACE_NO");
            	int    nRaceRegNoCnt = ((BigDecimal) rows[k - 1].getAttribute("RACE_REG_NO_CNT")).intValue();
            	
            	PosRowSet rowsetRace = null;
            	if ( ctx.get("dsOutRace_" + k) != null ) {
            		try {
            			rowsetRace = (PosRowSet) ctx.get("dsOutRace_" + k);
            		} catch ( Exception e ) {
        	        	List rowList = new java.util.ArrayList();
        	        	for ( int j = 0; j < nRaceRegNoCnt; j++ ) {
        	        		addRaceRegNo(rowList, sTempRaceNo, j + 1);
        	        	}
            	    	rowsetRace = new PosRowSetImpl(rowList);
            	    	rowsetRace.setColumnDefs(columnDef);

            	    	String sResultKey = "dsOutRace_" + k;
                        ctx.put(sResultKey, rowsetRace);
                        Util.addResultKey(ctx, sResultKey);
                        continue;
            		}
            		
            		PosRow temprows[] = rowsetRace.getAllRow();
            		if ( temprows.length == nRaceRegNoCnt) continue;
            		
            		int nRaceRegNo = 1;
            		int nRow       = 0;
    	        	List rowList = new java.util.ArrayList();
            		while(nRaceRegNo <= nRaceRegNoCnt) {
            			if ( temprows.length <= nRow ) {
        	        		addRaceRegNo(rowList, sTempRaceNo, nRaceRegNo);
            			} else if ( nRaceRegNo == Integer.parseInt((String) temprows[nRow].getAttribute("RACE_REG_NO"))) {
            				rowList.add(temprows[nRow]);
            				nRow++;
            			} else {
        	        		addRaceRegNo(rowList, sTempRaceNo, nRaceRegNo);
            			}
            			nRaceRegNo++;
            		}
            		
        	    	rowsetRace = new PosRowSetImpl(rowList);
        	    	rowsetRace.setColumnDefs(columnDef);

        	    	String sResultKey = "dsOutRace_" + k;
                    ctx.put(sResultKey, rowsetRace);
                    Util.addResultKey(ctx, sResultKey);
            	} else {
    	        	List rowList = new java.util.ArrayList();
    	        	for ( int j = 0; j < nRaceRegNoCnt; j++ ) {
    	        		addRaceRegNo(rowList, sTempRaceNo, j + 1);
    	        	}
        	    	rowsetRace = new PosRowSetImpl(rowList);
        	    	rowsetRace.setColumnDefs(columnDef);

        	    	String sResultKey = "dsOutRace_" + k;
                    ctx.put(sResultKey, rowsetRace);
                    Util.addResultKey(ctx, sResultKey);
            	}
        	}
    	}
    	
        // �ּ����� ��ȸ
    	PosRowSet rowsetRacer = null;
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	rowsetRacer = searchTempOrganAlloc(ctx);
        }else if("REAL".equals(organType)){
        	rowsetRacer = searchOrganAlloc(ctx);
        }

        // �ּ������� �������� ���� ���� Dataset����
        PosRow rowRacer[] = rowsetRacer.getAllRow();
        
    	List   racerList = new java.util.ArrayList();
    	for ( int j = 0; j < rowRacer.length; j++ ) {
    		int    nAllocCnt = ((BigDecimal) rowRacer[j].getAttribute("RACE_ALLOC_CNT")).intValue();
			for ( int k = 0; k < nAllocCnt; k++ ) {
				racerList.add(rowRacer[j]);
			}
    	}

    	for ( int j = 0; j < rowRaces.length; j++ ) {
    		String sRacerNo  = (String) rowRaces[j].getAttribute("RACER_NO");
    		
        	for ( int k = 0; k < racerList.size(); k++ ) {
        		if ( sRacerNo.equals((String)((PosRow) racerList.get(k)).getAttribute("RACER_NO")) ) {
        			racerList.remove(k);
        			break;
        		}
        	}
    	}
        
    	//row����
    	rowsetRacer = new PosRowSetImpl(racerList);
    	rowsetRacer.setColumnDefs(columnDef);
        
        String sResultKey = "dsOutRacer";
        ctx.put(sResultKey, rowsetRacer);
        Util.addResultKey(ctx, sResultKey);
        
        // �� ���������� ��ȸ
        if("NEW".equals(organType) || "TEMP".equals(organType)){
        	searchTempOrganLastUpdate(ctx);
        }else if("REAL".equals(organType)){
        	searchOrganLastUpdate(ctx);
        }
        
    }
    
    /**
     * <p> ���ֿ� �������� </p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		update ���ڵ� ����
     * @throws  none
     */
    protected void addRaceRegNo(List raceList, String sRaceNo, int nRaceRegNo) 
    {
    	if ( sRaceNo.equals("0") ) sRaceNo = "01";
    		
    	Map rowMap = new HashMap();
		rowMap.put("RACE_NO"    , sRaceNo);
		rowMap.put("RACE_REG_NO", Integer.toString(nRaceRegNo));
    	PosRow row = new PosRowImpl(rowMap);
    	raceList.add(row);
    }
    
    /**
     * <p> ��������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrgan(PosContext ctx){
    	
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ""                  );
        
        return this.getDao("boadao").find("tbeb_organ_fb004", paramRace);    	

    }
    
    /**
     * <p> ����������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrgan(PosContext ctx){
    	
    	// �������ȸ
    	PosParameter paramRace = new PosParameter();
        int i = 0;
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));

        
        // ���ϼ��� - ������ ���� ��� ���� ���ֹ�ȣ,����
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        // ������ ���ּ�
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        // �߰�
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
        
        paramRace.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRace.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRace.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRace.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRace.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));   
        
        paramRace.setWhereClauseParameter(i++, ""                  );
        
        return this.getDao("boadao").find("tbeb_temp_organ_fb001", paramRace);    	

    }  
    
    /**
     * <p> �� �ּ� ������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchOrganAlloc(PosContext ctx){
    	
    	// �ּ����� ��ȸ
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb003", paramRacer);    	
    	
    	
    }
    
    /**
     * <p> ���� �ּ� ������ȸ </p>
     * @param ctx
     * @return
     */
    protected PosRowSet searchTempOrganAlloc(PosContext ctx){
    	
    	// �ּ����� ��ȸ
    	PosParameter paramRacer = new PosParameter();
        int i = 0;
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));        
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // ���ϼ��� - ������ ���� ��� ���� ���ֹ�ȣ,����
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));    	
    	
        // ������ ���ּ�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));

    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));           	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	
        // �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
    	// �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	
        // �߰�
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));       
    	
    	paramRacer.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("TMS"      ));
    	paramRacer.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        return this.getDao("boadao").find("tbeb_racer_race_alloc_fb010", paramRacer);    	    	
    }
    
    
    /**
     * <p> ���� ���� ������������ȸ </p>
     * @param ctx
     */
    protected void searchTempOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_OWNER_ID"  ));
        param.setWhereClauseParameter(i++, ctx.get("ORGAN_SEQ"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_temp_organ_fb005", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }  
    
    /**
     * <p> �� ���� ������������ȸ </p>
     * @param ctx
     */    
    protected void searchOrganLastUpdate(PosContext ctx) 
    {
    	int i =0;
    	PosParameter param = new PosParameter();
    	
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));
        
        param.setWhereClauseParameter(i++, ctx.get("STND_YEAR"));
        param.setWhereClauseParameter(i++, ctx.get("MBR_CD"   ));
        param.setWhereClauseParameter(i++, ctx.get("TMS"      ));
        param.setWhereClauseParameter(i++, ctx.get("DAY_ORD"  ));

        PosRowSet rowset = this.getDao("boadao").find("tbeb_organ_fb027", param);

    	String sResultKey = "dsOutLastUpdate";
        ctx.put(sResultKey, rowset);
        Util.addResultKey(ctx, sResultKey);
    }    
}
