/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
==============================================================================*/

package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.PosException;
import com.posdata.glue.biz.activity.PosActivity;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.vo.PosParameter;
import com.posdata.glue.miplatform.vo.PosDataset;
import com.posdata.glue.miplatform.vo.PosRecord;
import com.posdata.glue.miplatform.biz.activity.PosMiPlatformConstants;
import com.posdata.glue.miplatform.vo.PosRecordType;

/**
 * 데이터베이스에 관련된 CUD(Create, Update, Delete) 오퍼레이션을 담당하는 Abstract Activity.
 * 
 * @author 조성민
 */
public abstract class PosDMLActivity extends PosActivity 
{
    protected int doDML(int recordType, PosContext ctx)
    {
        if (logger.isDebugEnabled()) 
        {
            logProperties();
        }
    
        // user friendly 한 error message로 보완해야 함.
        String datasetID = getProperty(PosMiPlatformConstants.DATESET);
        PosDataset ds = (PosDataset)ctx.get(datasetID);
        
        PosRecord[] records = getRecordsForDML(ds, recordType);        
        
        int totalDMLsize = 0;
        for (int i=0, size=records.length; i<size; i++) 
        {
            totalDMLsize += internalDoDML(recordType, records, i);
        }
        String resultKey = getProperty(PosMiPlatformConstants.RESULT_KEY);
        ctx.put(resultKey, new Integer(totalDMLsize));
        return totalDMLsize;
    }
    
    private int internalDoDML(int recordType, PosRecord[] records, int rowIndex) 
    {
        String daokey = getProperty(PosMiPlatformConstants.DAO);
        String queryKey = getProperty(PosMiPlatformConstants.SQLKEY);
        int paramSize = Integer.parseInt(getProperty(PosMiPlatformConstants.PARAM_COUNT));
        PosParameter param = new PosParameter();
        int dmlSize = 0;
        if (recordType == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
        {
            for (int j=0; j<paramSize; j++) 
                param.setWhereClauseParameter(j, 
                    records[rowIndex].getAttribute(getProperty(PosMiPlatformConstants.PARAM + j)));
            dmlSize = getDao(daokey).update(queryKey, param);
        }
        else if (recordType == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
        {
            for (int j=0; j<paramSize; j++) 
                param.setValueParamter(j, 
                    records[rowIndex].getAttribute(getProperty(PosMiPlatformConstants.PARAM + j)));
            dmlSize = getDao(daokey).insert(queryKey, param);
        }
        else if (recordType == com.posdata.glue.miplatform.vo.PosRecord.DELETE) 
        {
            for (int j=0; j<paramSize; j++) 
                param.setWhereClauseParameter(j, 
                    records[rowIndex].getAttribute(getProperty(PosMiPlatformConstants.PARAM + j)));
            dmlSize = getDao(daokey).delete(queryKey, param);
        }
        return dmlSize;
    }
    
    protected PosRecord[] getRecordsForDML(PosDataset ds, int recordType) throws PosException
    {
        PosRecord[] records = null;
        if (recordType == com.posdata.glue.miplatform.vo.PosRecord.UPDATE) 
        {
            records = ds.getRecordForUpdate();
        }
        else if (recordType == com.posdata.glue.miplatform.vo.PosRecord.INSERT)
        {
            records = ds.getRecordForInsert();
        }
        else if (recordType == com.posdata.glue.miplatform.vo.PosRecord.DELETE) 
        {
            records = ds.getRecordForDelete();
        }
        else 
        {
            throw new PosException(
                "Cannot find PosRecord Array. Current record type is [" + recordType + "]");
        }
        return records;
    }
    
    protected void logProperties() 
    {
        try 
        {
            logger.logDebug("Dataset ID: " + getProperty(PosMiPlatformConstants.DATESET));
            logger.logDebug("Result Key: " + getProperty(PosMiPlatformConstants.RESULT_KEY));
            logger.logDebug("DAO Key: " + getProperty(PosMiPlatformConstants.DAO));
            logger.logDebug("SQL Key: " + getProperty(PosMiPlatformConstants.SQLKEY));
            int paramSize = Integer.parseInt(getProperty(PosMiPlatformConstants.PARAM_COUNT));
            for (int i=0; i<paramSize; i++) 
            {
                logger.logDebug("Parameter" + i + ": " + 
                            getProperty(PosMiPlatformConstants.PARAM + i));
            }
        } 
        catch (Exception ex) 
        {
            logger.logWarn("Cannot log properties: " + ex.getMessage());
        }
    }
}