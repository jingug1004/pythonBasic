package com.posdata.glue.miplatform.biz.activity;

import com.posdata.glue.PosException;
import com.posdata.glue.context.PosContext;
import com.posdata.glue.dao.PosGenericDao;
import com.posdata.glue.dao.vo.*;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import snis.can.common.util.SnisActivity;

public class PosDatasetGenerator extends SnisActivity
{

    public PosDatasetGenerator(HttpServletRequest request, HttpServletResponse response, String charset)
    {
        log = PosLogFactory.getLogger(getClass());
        this.request = request;
        this.response = response;
        characterSet = charset;
    }

    public void processXMLGeneration()
    {
        PosContext ctx = (PosContext)request.getAttribute("PosContext");
        VariableList varList = new VariableList();
        DatasetList dataList = new DatasetList();
        if(ctx != null)
        {
            try
            {
                dataList = constructDatasetList();
                varList = constructVariableList();
            }
            catch(Exception ex)
            {
                varList.add("ErrorCode", "-1");
                varList.add("ErrorMsg", ex.getMessage());
                log.logError((new StringBuilder("Fail to construct DatasetList: ")).append(ex.getMessage()).toString(), ex);
            }
        } else
        {
            String message = "### CONTEXT IS NULL ###";
            log.logWarn(message);
            varList.add("ErrorCode", "-1");
            varList.add("ErrorMsg", message);
        }
        try
        {
            PlatformResponse platformReponse = new PlatformResponse(response, 2, characterSet);
            platformReponse.sendData(varList, dataList);
        }
        catch(Throwable th)
        {
            log.logError((new StringBuilder("Fail to generate XML FILE: ")).append(th.getMessage()).toString(), th);
            String orgMessage = varList.getValue("ErrorMsg").asString();
            if(orgMessage != null)
                varList.add("ErrorMsg", (new StringBuilder(String.valueOf(orgMessage))).append(" // detail msg: ").append(th.getMessage()).toString());
        }
    }

    protected PosContext findContext()
        throws PosException
    {
        PosContext ctx = (PosContext)request.getAttribute("PosContext");
        if(ctx == null)
        {
            String service = request.getParameter("ServiceName");
            String message = (new StringBuilder("Context is null, because Service [")).append(service).append("] did not execute or has a problem").toString();
            throw new PosException(message);
        }
        Properties prop = new Properties();
        String isCheck = "N";
        String daoType = "";
        String sqlKey = "";
        isCheck = "Y";
        try
        {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("loginCheck.properties"));
            if(prop.getProperty("duplicationCheck") != null)
                isCheck = prop.getProperty("duplicationCheck");
            if(prop.getProperty("daoType") != null)
            {
                daoType = prop.getProperty("daoType");
                if("".equals(daoType))
                    isCheck = "N";
            } else
            {
                isCheck = "N";
            }
            if(prop.getProperty("sqlKey") != null)
            {
                sqlKey = prop.getProperty("sqlKey");
                if("".equals(sqlKey))
                    isCheck = "N";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            isCheck = "N";
        }
        if("Y".equals(isCheck.toUpperCase()))
            checkDuplicationLogin(ctx, daoType, sqlKey);
        return ctx;
    }

    protected VariableList constructVariableList()
    {
        PosContext ctx = findContext();
        List resultKeys = (List)ctx.get("resultKeyList");
        VariableList varList = new VariableList();
        int keySize = resultKeys.size();
        if(resultKeys != null && keySize > 0)
        {
            for(int i = 0; i < keySize; i++)
            {
                Object resultVal = ctx.get(resultKeys.get(i));
                if((resultVal instanceof Integer) || (resultVal instanceof String))
                {
                    varList.add((String)resultKeys.get(i), resultVal);
                    log.logInfo((new StringBuilder("[")).append(resultKeys.get(i)).append("] -> value added to VariableList").toString());
                }
            }

        }
        Throwable th = ctx.getException();
        if(th == null)
        {
            varList.add("ErrorCode", "0");
            varList.add("ErrorMsg", "SUCC");
        } else
        {
            varList.add("ErrorCode", "-1");
            varList.add("ErrorMsg", th.getMessage());
        }
        return varList;
    }

    protected DatasetList constructDatasetList()
    {
        PosContext ctx = findContext();
        List resultKeys = (List)ctx.get("resultKeyList");
        DatasetList datasetList = new DatasetList();
        int keySize = resultKeys.size();
        if(resultKeys != null && keySize > 0)
        {
            Dataset dataset = null;
            for(int i = 0; i < keySize; i++)
            {
                Object resultVal = ctx.get(resultKeys.get(i));
                if(resultVal instanceof PosRowSet)
                {
                    PosRowSet rowset = (PosRowSet)resultVal;
                    if(rowset != null)
                    {
                        dataset = constructDataSet(rowset, (String)resultKeys.get(i));
                        datasetList.addDataset(dataset);
                        log.logInfo((new StringBuilder("[")).append(resultKeys.get(i)).append("] -> Dataset is constructed").toString());
                    }
                }
            }

        }
        return datasetList;
    }

    protected Dataset constructDataSet(PosRowSet rowset, String resultkey)
    {
        if(rowset == null)
            throw new PosException((new StringBuilder("RowSet is null.. [")).append(resultkey).append("]").toString());
        Dataset ds = new Dataset(resultkey);
        PosRow rows[] = rowset.getAllRow();
        PosColumnDef coldefs[] = rowset.getColumnDefs();
        if(coldefs == null || coldefs.length == 0)
            throw new PosException("Does not exist Column Definition. Please register 'columnDefCache' property value at DAO Bean of applicationContext.xml");
        for(int i = 0; i < coldefs.length; i++)
            if(isNumeric(coldefs[i].getType()))
                ds.addColumn(coldefs[i].getName(), (new Short("4")).shortValue(), 256);
            else
                ds.addColumn(coldefs[i].getName(), (new Short("1")).shortValue(), 256);

        for(int i = 0; i < rows.length; i++)
        {
            int rowindex = ds.appendRow();
            Map row = rows[i].getAttributes();
            String key;
            Object value;
            for(Iterator keyIter = row.keySet().iterator(); keyIter.hasNext(); ds.setColumn(rowindex, key, value.toString()))
            {
                key = (String)keyIter.next();
                value = row.get(key);
                if(value == null)
                    value = "";
            }

        }

        return ds;
    }

    public boolean isNumeric(int sqlType)
    {
        return -7 == sqlType || -5 == sqlType || 3 == sqlType || 8 == sqlType || 6 == sqlType || 4 == sqlType || 2 == sqlType || 7 == sqlType || 5 == sqlType || -6 == sqlType;
    }

    private void checkDuplicationLogin(PosContext ctx, String daoType, String sqlKey)
    {
        //System.out.println("111111111111111:USER_ID:"+ctx.getSessionUserData("USER_ID"));
    	if(ctx.getSessionUserData("USER_ID") != null && request.getAttribute("JSESSIONID") != null)
        {
            PosParameter param = new PosParameter();
            int i = 0;
            param.setWhereClauseParameter(i++, (String)ctx.getSessionUserData("USER_ID"));
            PosRowSet rowset = null;
            try
            {
                rowset = getDao(daoType).find(sqlKey, param);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(rowset != null && rowset.count() != 0)
            {
                PosRow row[] = rowset.getAllRow();
                String userSessionId = (String)request.getAttribute("JSESSIONID");
                String savedSessionId = (String)row[0].getAttribute("SESSION_ID");
                //log.logWarn("userSessionId:"+userSessionId);
                //log.logWarn("savedSessionId:"+savedSessionId);
                if(!"".equals(userSessionId) && !userSessionId.equals(savedSessionId))
                    throw new PosException("[DUP] \uB2E4\uB978 \uACF3\uC5D0\uC11C \uB85C\uADF8\uC778\uC744 \uD588\uC2B5\uB2C8\uB2E4. \n\n\uC2DC\uC2A4\uD15C\uC744 \uC885\uB8CC\uD569\uB2C8\uB2E4.");
            }
        }
    }

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String characterSet;
    protected PosLog log;
}
