/**
 * 
 */
package snis.can_boa.common.util;

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
 			
 		rowset = this.getDao("candao").find("d05000007_s03");
 		if (rowset.hasNext()) {
 			PosRow row = rowset.next();
 			curDateTime = row.getAttribute("CUR_DTM").toString();
 		}
 		return curDateTime;
    } 

}
