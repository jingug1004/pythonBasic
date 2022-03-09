/*==============================================================================
*Copyright(c) 2006 POSDATA
*Change history 
*@LastModifyDate : 20071001
*@LastModifier   : 조성민
*@LastVersion    : 1.0
*    2007-10-01    조성민
*        1.0       최초 생성
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
 * MiPlatform Dataset 하나를 표현하는 자바 클래스. Client Side와 Server Side 간의 종속성을
 * 제거하고자 만들어진 Data Structure 클래스. <br><br>
 * 
 * MiPlatform Client에서 발생한 Dataset을 핸들링하기 위해서는 다음과 같이 구현한다. <br>
 * (클라이언트로부터 받은 Dataset을 타입별로 구분하여 DML Operation을 수행하는 소스)
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
 * @author 조성민
 */
public class PosDataset implements PosRecordType   
{
    private PosColumnDef[] coldefs;
    
    private List recordList;
    
    private int[] recordTypeList;
    
    protected static final PosLog log = PosLogFactory.getLogger(PosDataset.class);
    
    
    /**
     * 생성자
     * 
     * @param ds MiPlatform Dataset
     */
    public PosDataset(Dataset ds)
    {
        // column def 정보 추출
        constructColumnDef(ds);
        
        // record 구성
        constructDataset(ds);
    }
    
    /**
     * 주어진 인덱스에 위치하는 PosRecord를 얻는다.
     * 
     * @return 레코드 객체
     * @param index 인덱스
     */
    public PosRecord getRecord(int index) 
    {
        return (PosRecord)this.recordList.get(index);
    }
    
    /**
     * 주어진 인덱스에 위치하는 PosRecord Type을 얻는다.
     * (eg. PosRecordType.NORMAL, PosRecordType.INSERT, 
     * PosRecordType.UPDATE, PosRecordType.DELETE Type)
     * 
     * @return 레코드 타입
     * @param index 인덱스
     */
    public int getRecordType(int index) 
    {
        return getRecord(index).getType();
    }
    
    /**
     * Dataset이 포함하고 있는 총 레코드 개수를 얻는다.
     * 
     * @return 레코드 개수
     */
    public int getRecordCount() 
    {
        return this.recordList.size();
    }
    
    /**
     * UPDATE 타입의 PosRecord Array를 얻는다.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForUpdate() 
    {
        return getRecordForTypes(UPDATE);
    }
    
    /**
     * INSERT 타입의 PosRecord Array를 얻는다.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForInsert() 
    {
        return getRecordForTypes(INSERT);
    }
    
    /**
     * DELETE 타입의 PosRecord Array를 얻는다.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForDelete() 
    {
        return getRecordForTypes(DELETE);
    }
    
    /**
     * NORMAL 타입의 PosRecord Array를 얻는다.
     * 
     * @return PosRecord Array
     */
    public PosRecord[] getRecordForNormal()
    {
        return getRecordForTypes(NORMAL);
    }
    
    /**
     * 레코드의 Column Definition Array를 얻는다.
     * Column Definition: 컬럼명, 컬럼길이, 컬럼타입
     * 
     * @return Column Definition Array
     */
    public PosColumnDef[] getColumnDefs() 
    {
        return this.coldefs;
    }
    
    /**
     * 주어진 타입에 해당하는 PosRecord Array를 얻는다.
     * 
     * @return PosRecord Array
     * @param recordType PosRecordType의 상수값 (INSERT, UPDATE, DELETE, NORMAL)
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
     * MiPlatform Dataset으로부터 PosDataset을 생성한다.
     * 
     * @param ds MiPlatform Dataset
     */
    protected void constructDataset(Dataset ds) 
    {
        // NOTE That
        //
        // MiPlatform Spec.
        //   Delete Record Type경우 
        //    - ds.getDeleteRowCount,
        //    - ds.getOriginalRowType,
        //    - ds.getDeleteColumn 메소드를 사용해야 함
        //   
        
        // 두 가지 경우로 나누어서 PosDataset을 구성해야 함. 
        //   (MiPlatform Data Structure 구조상의 문제)
        // 1. UPDATE/INSERT
        // 2. DELETE
        
        int rowSize = ds.getRowCount();
        int deletedRowSize = ds.getDeleteRowCount();
        this.recordList = new ArrayList(rowSize);
        this.recordTypeList = new int[rowSize + deletedRowSize];
        
        // 1. UPDATE/INSERT 타입 처리
        int typeListIndex = 0;
        for (int i=0; i<rowSize; i++, typeListIndex++) 
        {
            int recordType = getDsRecordType(ds, i);
            this.recordTypeList[i] = recordType;
            PosRecord newRecord = createRecord(ds, i, recordType);
            this.recordList.add(newRecord);
        }
        
        // 2. DELETE 타입 처리
        //  - deleted row 개수만큼 iteration
        for (int i=0, deletedSize=ds.getDeleteRowCount(); i<deletedRowSize; i++) 
        {
            this.recordTypeList[typeListIndex++] = DELETE;
            PosRecord newRecord = createRecordForDeleteOperation(ds, i, DELETE);
            this.recordList.add(newRecord);
        }
    }
    
    /**
     * MiPlatform Dataset으로부터 PosRecord를 생성한다.
     * 
     * @return 생성된 PosRecord
     * @param recordType 레코드 타입 (PosRecordType)
     * @param rowIndex 인덱스
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
     * Delete Operation인 경우에 MiPlatform Dataset으로부터 PosRecord를 생성한다.
     * 
     * @return 생성된 PosRecord
     * @param recordType 레코드 타입 (PosRecordType)
     * @param rowIndex 인덱스
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
     * MiPlatform Dataset으로부터 Column Definition 정보를 생성한다.
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
     * MiPlatform ColumnInfo의 컬럼 타입 값에 매칭되는 java.sql.Types의 타입 값을 제공한다.
     * 
     * @return java.sql.Types의 컬럼 타입 값
     * @param type MiPlatform ColumnInfo의 컬럼 타입 값
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
     * MiPlatform Dataset Type <-> PosRecordType 간의 값 변환 메소드
     * (UPDATE, INSERT, DELETE, NORMAL 4종류의 타입이 존재한다)
     * 
     * @return PosRecordType 값
     * @param index 인덱스
     * @param ds MiPlatform Dataset
     */
    private int getDsRecordType(Dataset ds, int index) 
    {
        // client 에서 발생하는 RECORD TYPE은 UPDATE, INSERT, DELETE, NORMAL 4 종류.
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