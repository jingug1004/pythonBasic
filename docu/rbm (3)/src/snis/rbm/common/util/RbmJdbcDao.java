/**
 * 
 */
package snis.rbm.common.util;

import org.springframework.dao.DataAccessException;

import com.posdata.glue.bean.PosBeanFactory;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosDaoException;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.PosJdbcDao;
import com.posdata.glue.dao.manager.PosColumnManager;
import com.posdata.glue.dao.manager.PosQueryDefinition;
import com.posdata.glue.dao.manager.PosQueryManager;
import com.posdata.glue.dao.sequence.PosSequence;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;
import com.posdata.glue.dao.PosGenericDao;

public class RbmJdbcDao extends PosJdbcDao {
	public RbmJdbcDao()
	{
	
	}
	public int[] executeBatch(String[] arrSql) throws PosDaoException {
	    int[] updateCounts = new int[arrSql.length];
	    
	    try
	    {
	    	updateCounts = getJdbcTemplate().batchUpdate(arrSql);
	    }
	    catch(DataAccessException dae)
	    {
	        PosDaoException daoex = handleDataAccessException(dae);
            //if(daoex.getSql() == null) daoex.setSql(queryStmt);
            throw daoex;
	    }
	    
	    return updateCounts;
    }    

}
