package snis.rbm.business.rsm1020.activity;
import snis.rbm.common.util.RbmJdbcDao;

import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.dao.vo.PosRowSet;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

public class MyCat {
	protected PosLog logger;
	private PosContext ctx = null;
	private String sFilePath = "";
	private RbmJdbcDao rbmdao = null;
	private String user_id = "";
	
	public MyCat()
    {
    }
	
	public MyCat(PosContext ctx, String sFilePath, RbmJdbcDao rbmdao, String session_user_id){
		this.ctx = ctx;
		this.sFilePath = sFilePath;
		this.rbmdao = rbmdao;
		this.user_id = session_user_id;
		
		logger = PosLogFactory.getLogger(getClass());
	}

	
	/**
     * <p> PC Satlit파일 입력 </p>
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	//public int insertSatlitMyCat(PosGenericDao dao, String sStndYear, String sTms, String sDayOrd, String sSessionNo) 
	public int insertSatlitMyCat(PosGenericDao dao, String sRaceDay, String sSessionNo)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sSessionNo);
        param.setValueParamter(i++, sRaceDay);
        
        int dmlcount = dao.update("mycat_satlit_ins", param);
        
        return dmlcount;
    }
	
	/**
     * <p> PC Sellst파일 입력 </p>
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	//public int insertSellstMyCat(PosGenericDao dao, String sStndYear, String sTms, String sDayOrd, String sSessionNo) 
	public int insertSellstMyCat(PosGenericDao dao, String sRaceDay, String sSessionNo)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sSessionNo);
        param.setValueParamter(i++, sRaceDay);
        
        int dmlcount = dao.update("mycat_sellst_ins", param);
        
        return dmlcount;
    }
	
	/**
     * <p> PC tbes_sdl_dt 테이블 입력 </p>
     * @return  dmlcount	int		update 레코드 개수
     * @throws  none
     */
	//public int insertTbesSdlDTMyCat(PosGenericDao dao, String sStndYear, String sTms, String sDayOrd) 
	public int insertTbesSdlDTMyCat(PosGenericDao dao, String sRaceDay)
    {
        PosParameter param = new PosParameter();   
        
        int i = 0;
        
        param.setValueParamter(i++, sRaceDay);
        
        int dmlcount = dao.update("rsm1020_i07", param);	// MyCat-TBES_SDL_DT입력
        
        return dmlcount;
    }
    
}
