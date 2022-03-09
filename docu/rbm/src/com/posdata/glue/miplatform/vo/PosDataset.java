/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : ������
*@LastVersion    : 1.0
*    2007-10-01    ������
*        1.0       ���� ����
==============================================================================*/

package com.posdata.glue.miplatform.vo;

import com.posdata.glue.dao.vo.PosColumnDef;
import com.posdata.glue.util.log.PosLog;
import com.posdata.glue.util.log.PosLogFactory;

import com.tobesoft.platform.data.ColumnInfo;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.Variant;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


/**
 * MiPlatform Dataset �ϳ��� ǥ���ϴ� �ڹ� Ŭ����. Client Side�� Server Side ���� ���Ӽ���
 * �����ϰ��� ������� Data Structure Ŭ����. <br><br>
 * 
 * MiPlatform Client���� �߻��� Dataset�� �ڵ鸵�ϱ� ���ؼ��� ������ ���� �����Ѵ�. <br>
 * (Ŭ���̾�Ʈ�κ��� ���� Dataset�� Ÿ�Ժ��� �����Ͽ� DML Operation�� �����ϴ� �ҽ�)
 * 
 * <xmp>
    PosDataset ds = (PosDataset)ctx.get("input");
    int size = ds.getRecordCount();
    for (int i=0; i<size; i++) 
    {
        PosRecord record = ds.getRecord(i);
        if (record.getType() == PosRecord.UPDATE)
        {
            // update
            PosParameter param = new PosParameter();
            param.setValueParamter(0, record.getAttribute("SAL"));
            param.setValueParamter(1, record.getAttribute("ENAME"));
            param.setWhereClauseParameter(0, record.getAttribute("EMPNO"));
            
            int dmlcount = this.getDao("testdao").update("emp.update", param);
            logger.logInfo("UPDATED DML SIZE: " + dmlcount);
            ctx.put("UpdatedCount", new Integer(dmlcount));
        }
        else if (record.getType() == PosRecord.INSERT) 
        {
            // insert
            PosParameter param = new PosParameter();
            param.setValueParamter(0, record.getAttribute("EMPNO"));
            param.setValueParamter(1, record.getAttribute("ENAME"));
            param.setValueParamter(2, record.getAttribute("SAL"));
            param.setValueParamter(3, record.getAttribute("COMM"));
            param.setValueParamter(4, record.getAttribute("DEPTNO"));
            
            int dmlcount = this.getDao("testdao").insert("emp.insert", param);
            logger.logInfo("INSERTED DML SIZE: " + dmlcount);
            ctx.put("InsertedCount", new Integer(dmlcount));
        }
        else if (record.getType() == PosRecord.DELETE)
        {
            // delete
            PosParameter param = new PosParameter();
            param.setWhereClauseParameter(0, record.getAttribute("EMPNO"));
            
            int dmlcount = this.getDao("testdao").delete("emp.delete", param);
            logger.logInfo("DELETED DML SIZE: " + dmlcount);
            ctx.put("DeletedCount", new Integer(dmlcount));
        }
        else 
        {
            // do nothing
            String errorMessage = 
                "This record is not matched, RecordType=[" + record.getTypeString() + "]";
            logger.logWarn(errorMessage);
            throw new PosException(errorMessage);
        }
    }
 * </xmp>
 * 
 * @author ������
 */
public class PosDataset implements PosRecordType   
{
    private PosColumnDef[] coldefs;
    
    private List recordList;
    
    private int[] recordTypeList;
    
    protected static final PosLog log = PosLogFactory.getLogger(PosDataset.class);
    
    
    /**
     * ������
     * 
     * @param ds MiPlatform Dataset
     */
    public PosDataset(Dataset ds)
    {
        // column def ���� ����
        constructColumnDef(ds);
        
        // record ����
        constructDataset(ds);
    }
    
    /**
     * �־��� �ε����� ��ġ�ϴ� PosRecord�� ��´�.
     * 
     * @return ���ڵ� ��ü
     * @param index �ε���
     */
    public PosRecord getRecord(int index) 
    {
        return (PosRecord)this.recordList.get(index);
    }
    
    /**
     * �־��� �ε����� ��ġ�ϴ� PosRecord Type�� ��´�.
     * (eg. PosRecordType.NORMAL, PosRecordType.INSERT, 
     * PosRecordType.UPDATE, PosRecordType.DELETE Type)
     * 
     * @return ���ڵ� Ÿ��
     * @param index �ε���
     */
    public int getRecordType(int index) 
    {
        return getRecord(index).getType();
    }
    
    /**
     * Dataset�� �����ϰ� �ִ� �� ���ڵ� ������ ��´�.
     * 
     * @return ���ڵ� ����
     */
    public int getRecordCount() 
    {
        return this.recordList.size();
    }
    
    /**
     * UPDATE Ÿ���� PosRecord Array�� ��´�.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForUpdate() 
    {
        return getRecordForTypes(UPDATE);
    }
    
    /**
     * INSERT Ÿ���� PosRecord Array�� ��´�.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForInsert() 
    {
        return getRecordForTypes(INSERT);
    }
    
    /**
     * DELETE Ÿ���� PosRecord Array�� ��´�.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForDelete() 
    {
        return getRecordForTypes(DELETE);
    }
    
    /**
     * NORMAL Ÿ���� PosRecord Array�� ��´�.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForNormal()
    {
        return getRecordForTypes(NORMAL);
    }
    
    /**
     * ���ڵ��� Column Definition Array�� ��´�.
     * Column Definition: �÷���, �÷�����, �÷�Ÿ��
     * 
     * @return Column Definition Array
     */
    public PosColumnDef[] getColumnDefs() 
    {
        return this.coldefs;
    }
    
    /**
     * �־��� Ÿ�Կ� �ش��ϴ� PosRecord Array�� ��´�.
     * 
     * @return PosRecord Array
     * @param recordType PosRecordType�� ����� (INSERT, UPDATE, DELETE, NORMAL)
     */
    public PosRecord[] getRecordForTypes(int recordType) 
    {
        ArrayList rlist = new ArrayList();
        int rowsize = this.recordTypeList.length;
        int matched = 0;
        for (int i=0; i<rowsize; i++) 
        {
            if (this.recordTypeList[i] == recordType)
            {
                rlist.add(this.recordList.get(i));
                matched++;
            }
        }
        PosRecord[] records = new PosRecord[matched];
        return (PosRecord[])rlist.toArray(records);
    }
    
    /**
     * MiPlatform Dataset���κ��� PosDataset�� �����Ѵ�.
     * 
     * @param ds MiPlatform Dataset
     */
    protected void constructDataset(Dataset ds) 
    {
        // NOTE That
        //
        // MiPlatform Spec.
        //   Delete Record Type��� 
        //    - ds.getDeleteRowCount,
        //    - ds.getOriginalRowType,
        //    - ds.getDeleteColumn �޼ҵ带 ����ؾ� ��
        //   
        
        // �� ���� ���� ����� PosDataset�� �����ؾ� ��. 
        //   (MiPlatform Data Structure �������� ����)
        // 1. UPDATE/INSERT
        // 2. DELETE
        
        int rowSize = ds.getRowCount();
        int deletedRowSize = ds.getDeleteRowCount();
        this.recordList = new ArrayList(rowSize);
        this.recordTypeList = new int[rowSize + deletedRowSize];
        
        // 1. UPDATE/INSERT Ÿ�� ó��
        int typeListIndex = 0;
        for (int i=0; i<rowSize; i++, typeListIndex++) 
        {
            int recordType = getDsRecordType(ds, i);
            this.recordTypeList[i] = recordType;
            PosRecord newRecord = createRecord(ds, i, recordType);
            this.recordList.add(newRecord);
        }
        
        // 2. DELETE Ÿ�� ó��
        //  - deleted row ������ŭ iteration
        for (int i=0, deletedSize=ds.getDeleteRowCount(); i<deletedRowSize; i++) 
        {
            this.recordTypeList[typeListIndex++] = DELETE;
            PosRecord newRecord = createRecordForDeleteOperation(ds, i, DELETE);
            this.recordList.add(newRecord);
        }
    }
    
    /**
     * MiPlatform Dataset���κ��� PosRecord�� �����Ѵ�.
     * 
     * @return ������ PosRecord
     * @param recordType ���ڵ� Ÿ�� (PosRecordType)
     * @param rowIndex �ε���
     * @param ds MiPlatform Dataset
     */
    private PosRecord createRecord(Dataset ds, int rowIndex, int recordType) 
    {
        int colsize = ds.getColumnCount();
        PosRecord record = new PosRecord(recordType);
        for (int j=0; j<colsize; j++) 
        {
            String id = ds.getColumnId(j);
            Variant var = ds.getColumn(rowIndex, j);
            Object value = var.getObject();
            record.setAttribute(id, value);
        }
        return record;        
    }
    
    /**
     * Delete Operation�� ��쿡 MiPlatform Dataset���κ��� PosRecord�� �����Ѵ�.
     * 
     * @return ������ PosRecord
     * @param recordType ���ڵ� Ÿ�� (PosRecordType)
     * @param rowIndex �ε���
     * @param ds MiPlatform Dataset
     */
    private PosRecord createRecordForDeleteOperation(Dataset ds, int rowIndex, int recordType) 
    {
        int colsize = ds.getColumnCount();
        PosRecord record = new PosRecord(recordType);
        for (int j=0; j<colsize; j++) 
        {
            String id = ds.getColumnId(j);
            Variant var = ds.getDeleteColumn(rowIndex, id);
            Object value = var.getObject();
            record.setAttribute(id, value);
        }
        return record;  
    }
    
    /**
     * MiPlatform Dataset���κ��� Column Definition ������ �����Ѵ�.
     * 
     * @param ds MiPlatform Dataset
     */
    protected void constructColumnDef(Dataset ds) 
    {
        int size = ds.getColumnCount();
        PosColumnDef[] defs = new PosColumnDef[size];
        for (int i=0; i<size; i++) 
        {
            ColumnInfo colinfo = ds.getColumnInfo(i);
            short columntype = colinfo.getColumnType();
            
            PosColumnDef coldef = new PosColumnDef(colinfo.getColumnID(),
                                                getColumnType(columntype),
                                                colinfo.getColumnSize());
            defs[i] = coldef;
        }
        this.coldefs = defs;
    }
    
    /**
     * MiPlatform ColumnInfo�� �÷� Ÿ�� ���� ��Ī�Ǵ� java.sql.Types�� Ÿ�� ���� �����Ѵ�.
     * 
     * @return java.sql.Types�� �÷� Ÿ�� ��
     * @param type MiPlatform ColumnInfo�� �÷� Ÿ�� ��
     */
    private int getColumnType(short type) 
    {
        if (type == ColumnInfo.COLTYPE_STRING || type == ColumnInfo.COLTYPE_CHAR)
        {
            return Types.VARCHAR;
        }
        else if (type == ColumnInfo.COLTYPE_INT || type == ColumnInfo.COLTYPE_LONG)
        {
            return Types.NUMERIC;
        }
        else if (type == ColumnInfo.COLTYPE_DATE) 
        {
            return Types.DATE;
        }
        else if (type == ColumnInfo.COLTYPE_BLOB)
        {
            return Types.BLOB;
        }
        else 
        {
            return Types.OTHER;
        }
    }
    
    /**
     * MiPlatform Dataset Type <-> PosRecordType ���� �� ��ȯ �޼ҵ�
     * (UPDATE, INSERT, DELETE, NORMAL 4������ Ÿ���� �����Ѵ�)
     * 
     * @return PosRecordType ��
     * @param index �ε���
     * @param ds MiPlatform Dataset
     */
    private int getDsRecordType(Dataset ds, int index) 
    {
        // client ���� �߻��ϴ� RECORD TYPE�� UPDATE, INSERT, DELETE, NORMAL 4 ����.
        int type = ds.getRowType(index);
        
        if (type == Dataset.ROWTYPE_UPDATE) 
            return UPDATE;
        else if (type == Dataset.ROWTYPE_INSERT)
            return INSERT;
        else if (type == Dataset.ROWTYPE_NORMAL) 
            return NORMAL;
        else if (type == Dataset.ROWTYPE_DELETE)
            return DELETE;
//        else if (type == Dataset.ROWTYPE_EMPTY) 
//        {
//            int orgRecordType = ds.getOrgRowType(index);
//            log.logInfo("Dataset[" + ds.getDataSetID() + ", " + index + 
//                        "]: ### Row Type is EMPTY ### | Orginal Row Type: [" + 
//                        orgRecordType +"] \n Maybe it's a delete operation...");
//            return DELETE;
//        }
        else
            return UNKNOWN;
    }
}