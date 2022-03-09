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

import com.posdata.glue.biz.activity.PosServiceParamIF;

/**
 * Glue Adaptor for MiPlatform 용 상수 인터페이스
 * 
 * @author 조성민
 */
public interface PosMiPlatformConstants extends PosServiceParamIF
{
    public static final String RESULT_KEY_LIST = "resultKeyList";
    public static final String RESULT_KEY_LIST_DELIMITER = "\\|";
    public static final String EMPTY_VALUE = "";
    public static final String DATESET = "dataset-id";
    
    public static final String ERROR_MESSAGE = "ErrorMsg";
    public static final String ERROR_CODE = "ErrorCode";    
    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MESSAGE = "SUCC";
    public static final String FAILURE_CODE = "-1";
    
    public static final String FILE_CONTENT = "filecontent";
    public static final String FILE_PATH = "filepath";
    public static final String FILE_NAME = "filename";
}