/**
 * 
 */
package snis.rbm.common.util;

import com.posdata.glue.dao.vo.PosRow;
import com.posdata.glue.dao.vo.PosRowSet;

public class UtilDb extends SnisActivity  {
	public UtilDb()
	{
	
	}

    public String getCurTime()
    {
 		PosRowSet rowset;
 		String curDateTime = "";
 			
 		rowset = this.getDao("rbmdao").find("rsy3020_s05");
 		if (rowset.hasNext()) {
 			PosRow row = rowset.next();
 			curDateTime = row.getAttribute("CUR_DTM").toString();
 		}
 		return curDateTime;
    } 

}
