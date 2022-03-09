/*================================================================================
 * ½Ã½ºÅÛ			: Æí¼º°ü¸®
 * ¼Ò½ºÆÄÀÏ ÀÌ¸§	: snis.boa.organization.e02030030.activity.SaveArrangeBacAlloc.java
 * ÆÄÀÏ¼³¸í		: ¼±¼öº°±âº»­‡ÁÖÈ½¼öµî·Ï
 * ÀÛ¼ºÀÚ			: À¯µ¿ÈÆ
 * ¹öÀü			: 1.0.0
 * »ı¼ºÀÏÀÚ		: 2007-10-26
 * ÃÖÁ¾¼öÁ¤ÀÏÀÚ	: 
 * ÃÖÁ¾¼öÁ¤ÀÚ		: 
 * ÃÖÁ¾¼öÁ¤³»¿ë	: 
=================================================================================*/
package snis.boa.organization.e02030030.activity;

import com.posdata.glue.biz.constants.PosBizControlConstants;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;

import snis.boa.common.util.SnisActivity;
import snis.boa.common.util.Util;

/**
* ÀÌ Å¬·¡½º´Â Å¬¶óÀÌ¾ğÆ®·ÎºÎÅÍÀÇ ÀúÀå µ¥ÀÌÅ¸¼ÂÀ» ¹Ş¾Æ ÇØ´ç QueryÀÇ Á¶°ÇÀı¿¡ ¸Â´Â ÆÄ¶ó¹ÌÅÍ¸¦
* ¸ÅÇÎÇÏ¿© ¼±¼öº°±âº»­‡ÁÖÈ½¼ö¸¦ ÀúÀå(ÀÔ·Â/¼öÁ¤)ÇÏ´Â Å¬·¡½ºÀÌ´Ù.
* @auther À¯µ¿ÈÆ
* @version 1.0
*/
public class SaveArrangeBacAlloc extends SnisActivity
{
	private String sStndYear = "";
	private String sQurtCd   = "";

    public SaveArrangeBacAlloc()
    {
    }

    /**
     * <p> SaveStates Activity¸¦ ½ÇÇà½ÃÅ°±â À§ÇÑ ¸Ş¼Òµå </p>
     * @param   ctx		PosContext	ÀúÀå¼Ò
     * @return  SUCCESS	String		sucess ¹®ÀÚ¿­
     * @throws  none
     */    
    public String runActivity(PosContext ctx)
    {
    	// »ç¿ëÀÚ Á¤º¸ È®ÀÎ
    	if ( !setUserInfo(ctx).equals(PosBizControlConstants.SUCCESS) ) {
    		Util.setSvcMsgCode(ctx, "L_COM_ALT_0028");
            return PosBizControlConstants.SUCCESS;
    	}
    	
        PosDataset ds;
        int        nSize   = 0;
        String     sDsName = "";
        
        sDsName = "dsOutBacAlloc";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset)ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	        for ( int i = 0; i < nSize; i++ )
	        {
	            PosRecord record = ds.getRecord(i);
	            logger.logInfo(record);
	        }
        }

        saveState(ctx);

        return PosBizControlConstants.SUCCESS;
    }

    /**
    * <p> ÇÏ³ªÀÇ µ¥ÀÌÅ¸¼ÂÀ» °¡Á®¿Í ÇÑ ·¹ÄÚµå¾¿ loopingÇÏ¸é¼­ DML ¸Ş¼Òµå¸¦ È£ÃâÇÏ±â À§ÇÑ ¸Ş¼Òµå </p>
    * @param   ctx		PosContext	ÀúÀå¼Ò
    * @return  none
    * @throws  none
    */
    protected void saveState(PosContext ctx) 
    {
    	int    nSaveCount   = 0; 
    	int    nDeleteCount = 0; 
        String sDsName      = "";

    	PosDataset ds;
        int nSize        = 0;
        int nTempCnt     = 0;

        // ¼±¼öº°±âº»­‡ÁÖÈ½¼öÀúÀå
        sStndYear = Util.nullToStr((String) ctx.get("STND_YEAR".trim()));
        sQurtCd   = Util.nullToStr((String) ctx.get("QURT_CD  ".trim()));
        
        sDsName = "dsOutBacAlloc";
        if ( ctx.get(sDsName) != null ) {
	        ds    = (PosDataset) ctx.get(sDsName);
	        nSize = ds.getRecordCount();
	
            // ÀúÀå
	        for ( int i = 0; i < nSize; i++ ) 
	        {
	            PosRecord record = ds.getRecord(i);
            	if ( (nTempCnt = updateBacRaceAlloc(record)) == 0 ) {
                	nTempCnt = insertBacRaceAlloc(record);
                }

            	nSaveCount = nSaveCount + nTempCnt;
	        }
	        
	        Util.setSaveCount  (ctx, nSaveCount     );
	        return;
        }
        
        Util.setSaveCount  (ctx, nSaveCount     );
        Util.setDeleteCount(ctx, nDeleteCount   );
    }

    /**
     * <p> ¼±¼öº°±âº»­‡ÁÖÈ½¼ö Update </p>
     * @param   record	 PosRecord	µ¥ÀÌÅ¸¼Â¿¡ ´ëÇÑ ÇÏ³ªÀÇ ·¹ÄÚµå
     * @return  dmlcount int		update ·¹ÄÚµå °³¼ö
     * @throws  none
     */
    protected int updateBacRaceAlloc(PosRecord record)
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );

        i = 0;
        param.setWhereClauseParameter(i++, sStndYear);
        param.setWhereClauseParameter(i++, sQurtCd  );
        param.setWhereClauseParameter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ub001", param);
        
        return dmlcount;
    }

    /**
     * <p> ¼±¼öº°±âº»­‡ÁÖÈ½¼ö ÀÔ·Â </p>
     * @param   record	PosRecord	µ¥ÀÌÅ¸¼Â¿¡ ´ëÇÑ ÇÏ³ªÀÇ ·¹ÄÚµå
     * @return  dmlcount	int		update ·¹ÄÚµå °³¼ö
     * @throws  none
     */
    protected int insertBacRaceAlloc(PosRecord record) 
    {
        PosParameter param = new PosParameter();
        int i = 0;
        param.setValueParamter(i++, sStndYear);
        param.setValueParamter(i++, sQurtCd  );
        param.setValueParamter(i++, Util.trim(record.getAttribute("RACER_NO      ".trim())));
        param.setValueParamter(i++, Integer.toString(Util.nullToInt(Util.trim(record.getAttribute("RACE_ALLOC_CNT".trim())), 0)));
        param.setValueParamter(i++, SESSION_USER_ID );
        param.setValueParamter(i++, SESSION_USER_ID );

        int dmlcount = this.getDao("boadao").update("tbeb_racer_bac_race_alloc_ib001", param);
        return dmlcount;
    }
}
