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
package snis.boa.equipment.e06010070.activity;


import snis.boa.common.util.SnisActivity;
import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.Util;

/**
* ���� ��Ʈ�� ��� ���� ������ ���, ����, ���� �Ѵ�.
* @auther �輺�� 
* @version 1.0
*/
public class SaveEquipLot extends SnisActivity
{    
	private String equipDrwltCmplYn  = "N";
    public SaveEquipLot()
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
    	saveEquipDrwlt(ctx);		//1. ���°� ����
    	saveEquipStatLost(ctx);		//2. Ȯ����� ���� ����
        saveState(ctx);				//3. �ڷ� ó��
        return PosBizControlConstants.SUCCESS;
    }
    
    
    //Ȯ���˻� ��� ���� ���� 
    public void saveEquipStatLost(PosContext ctx){
    	PosDataset ds    = (PosDataset) ctx.get("dsOutEquipDrwlt");
        int nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            equipDrwltCmplYn  = (String)record.getAttribute("EQUIP_DRWLT_CMPL_YN");
            if(equipDrwltCmplYn.equals("Y")) deleteEquipLotStat(record);
        }
    }

    /**
    * <p> �ϳ��� ����Ÿ���� ������ �� ���ڵ徿 looping�ϸ鼭 DML �޼ҵ带 ȣ���ϱ� ���� �޼ҵ� </p>
    * @param   ctx		PosContext	�����
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	saveMotLot( ctx);			//3.1 ���� ��÷��� ����
    	saveBoatLot( ctx);			//3.2 ��Ʈ ��÷��� ����
    	savePropellerLot(ctx);		//3.3 ������� ��÷��� ����
    }
    
    /**
     * 3.1 ���� ��÷ ��Ȳ
     * @param ctx
     */
    protected void saveMotLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutMotLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            String lot_yn = (String)record.getAttribute("LOT_YN")== null ? "" : (String)record.getAttribute("LOT_YN");
            String repare_yn = (String)record.getAttribute("PREPAR_YN")==null ? "" : (String)record.getAttribute("PREPAR_YN");
            String repare_add1_yn = (String)record.getAttribute("PREPAR_ADD1_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD1_YN");
            String repare_add2_yn = (String)record.getAttribute("PREPAR_ADD2_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD2_YN");
            if (lot_yn.equals("0") && repare_yn.equals("0") && repare_add1_yn.equals("0") && repare_add2_yn.equals("0")){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }
        //���� ��� ���� ���� 
        if(equipDrwltCmplYn.equals("Y"))saveEquipLotStat(ds);
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 3.2 ��Ʈ ��÷ ��Ȳ
     * @param ctx
     */
    protected void saveBoatLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutBoatLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            String lot_yn = (String)record.getAttribute("LOT_YN")== null ? "" : (String)record.getAttribute("LOT_YN");
            String repare_yn = (String)record.getAttribute("PREPAR_YN")==null ? "" : (String)record.getAttribute("PREPAR_YN");
            String repare_add1_yn = (String)record.getAttribute("PREPAR_ADD1_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD1_YN");
            String repare_add2_yn = (String)record.getAttribute("PREPAR_ADD2_YN")==null ? "" : (String)record.getAttribute("PREPAR_ADD2_YN");
            if (lot_yn.equals("0") && repare_yn.equals("0") && repare_add1_yn.equals("0") && repare_add2_yn.equals("0")){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }
        //��Ʈ  ��� ���� ���� 
        if(equipDrwltCmplYn.equals("Y"))saveEquipLotStat(ds);
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }
    
    /**
     * 3.3 �����緯 ��÷ ��Ȳ
     * @param ctx
     */
    protected void savePropellerLot(PosContext ctx) 
    {
    	int nSaveCount   = 0; 
    	int nDeleteCount = 0; 

    	PosDataset ds;
        int nSize        = 0;
        
        ds    = (PosDataset) ctx.get("dsOutPropellerLot");
        nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            if (record.getType() == com.posdata.glue.miplatform.vo.PosRecord.DELETE){
            	nDeleteCount = nDeleteCount + deleteEquipLot(record);
            }else{
            	nSaveCount = nSaveCount + saveEquipLot(record);
            }
        }

        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> 3.1.2 ���� ��Ʈ ��÷ ���  ����  Save </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		update ���ڵ� ����
     * @throws  none
     */
    protected int saveEquipLot(PosRecord record)
    {
    	int effectedRowCnt = 0;
    	effectedRowCnt = mergeIntoEquipLot(record);
    	return effectedRowCnt;
    }

    
    /**
     * <p>3.1.2.1 ��� �Է�</p>
     * @param   record	PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount	int		insert ���ڵ� ����
     * @throws  none
     */
    protected int mergeIntoEquipLot(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        String preparValue = "N";
    	String preparYn = (String)record.getAttribute("PREPAR_YN");
    	String preparAdd1Yn = (String)record.getAttribute("PREPAR_ADD1_YN");
    	String preparAdd2Yn = (String)record.getAttribute("PREPAR_ADD2_YN");
    	String lotOrd = "0";
    	if(preparYn != null && preparYn.equals("1"))preparValue = "Y";
    	if(preparAdd1Yn.equals("1"))lotOrd="1";
    	if(preparAdd2Yn.equals("1"))lotOrd="2";
    	if(preparAdd1Yn.equals("1")|| preparAdd2Yn.equals("1")) preparValue = "Y";
    	
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD"));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO"));
        param.setValueParamter(i++, preparValue);
        param.setValueParamter(i++, lotOrd);
        param.setValueParamter(i++, record.getAttribute("EXCL_YN"));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return this.getDao("boadao").update("tbef_equip_lot_mf001", param);
    }

    /**
     * <p>3.1.1 ��� ���� </p>
     * @param   record	 PosRecord	����Ÿ�¿� ���� �ϳ��� ���ڵ�
     * @return  dmlcount int		delete ���ڵ� ����
     * @throws  none
     */
    protected int deleteEquipLot(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO" ));
        
        return  this.getDao("boadao").update("tbef_equip_lot_df001", param);
    }
    
    /**
     * Ȯ�� ���� ���� 
     * @param ctx
     * @return
     */
    protected String saveEquipDrwlt(PosContext ctx){
    	SaveEquipDrwlt equipDrwlt = new SaveEquipDrwlt();
    	return equipDrwlt.runActivity(ctx);
    }
   
    
    /**
     *  ��� ���� ����
     * @param ds
     */
    protected void saveEquipLotStat(PosDataset ds) 
    {
        int nSize = ds.getRecordCount();
        for ( int i = 0; i < nSize; i++ ){
            PosRecord record = ds.getRecord(i);
            insertEquipLotStat(record);
        }

    }
    
    /**
     * 2.1 ������ ���� 
     * @param record
     * @return
     */
    protected int deleteEquipLotStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        return  this.getDao("boadao").update("tbef_equip_lot_stat_df001", param);
    }
    
    
    /**
     * 3.1.3.1 ��� ���� ���
     * @param record
     * @return
     */
    protected int insertEquipLotStat(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, record.getAttribute("STND_YEAR" ));
        param.setValueParamter(i++, record.getAttribute("MBR_CD" ));
        param.setValueParamter(i++, record.getAttribute("TMS" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_TPE_CD" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_NO" ));
        param.setValueParamter(i++, record.getAttribute("EQUIP_STAT_CD" ));
        param.setValueParamter(i++, SESSION_USER_ID);
        
        return  this.getDao("boadao").update("tbef_equip_lot_stat_if001", param);
    }
}
