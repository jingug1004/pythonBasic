
/**************************************************************************************************
*  함   수  명				: fnc_GetRaceTms
*  설       명				: 해당 년도의 경주회차 조회
*  param1) sStndYear		: 해당년도
*  param2) sQurtCd		    : 해당분기
*  param3) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetRaceTms(sStndYear, sMbrCd, sQurtCd, sDatasetName)
{
	var sServiceName = "common_race-service:searchRaceTms";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRaceTms ";
    
	fce_AddParam(Trim("STND_YEAR"), sStndYear);
	fce_AddParam(Trim("MBR_CD"   ), sMbrCd   );
	fce_AddParam(Trim("QURT_CD"  ), sQurtCd  );

	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

 
/**************************************************************************************************
*  함   수  명				: fnc_GetRaceDayOrd
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sStndYear		: 해당년도
*  param2) sRaceTms		    : 회차
*  param3) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetRaceDayOrd(sStndYear, sMbrCd, sRaceTms, sDatasetName)
{
	var sServiceName = "common_race-service:searchRaceDayOrd";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRaceDayOrd ";
    
	fce_AddParam(Trim("STND_YEAR"), sStndYear);
	fce_AddParam(Trim("MBR_CD"   ), sMbrCd   );
	fce_AddParam(Trim("TMS"      ), sRaceTms );

	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fnc_GetRace
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sStndYear		: 해당년도
*  param2) sRaceTms		    : 회차
*  param3) sRaceDayOrd		: 일차
*  param4) sDatasetName		: Dataset
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetRace(sStndYear, sMbrCd, sRaceTms, sRaceDayOrd, sDatasetName)
{
	var sServiceName = "common_race-service:searchRace";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRace ";
    
	fce_AddParam(Trim("STND_YEAR"), sStndYear   );
	fce_AddParam(Trim("MBR_CD"   ), sMbrCd      );
	fce_AddParam(Trim("TMS"      ), sRaceTms    );
	fce_AddParam(Trim("DAY_ORD"  ), sRaceDayOrd );

	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
}

/**************************************************************************************************
*  함   수  명				: fnc_SetCurTms
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetCurTms(sMbrCd)
{
	var sServiceName = "common_race-service:searchCurTms";
    var sInDataSet   = "";
    var sOutDataSet  = "gdsRace=dsOutCurTms ";
    
    if ( sMbrCd == null ) sMbrCd = fce_GetUserInfo("MBR_CD");
	fce_AddParam(Trim("MBR_CD"), sMbrCd);
    
    http.sync = true;
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
}

/**************************************************************************************************
*  함   수  명				: fnc_GetRaceDay
*  설       명				: 해당 경주회차의 일차의 경주일자를 조회
*  return Type				: 없음
*  return 내용				: 경주일자
**************************************************************************************************/
function fce_GetRaceDay(sStndYear, sMbrCd, sTms, sDayOrd, sDatasetName)
{
	var sServiceName = "common_race-service:searchRaceDay";
    var sInDataSet   = "";
    var sOutDataSet  = sDatasetName + "=dsOutSRaceDay ";
    
	fce_AddParam(Trim("STND_YEAR    "), sStndYear);
	fce_AddParam(Trim("TMS          "), sTms     );
	fce_AddParam(Trim("DAY_ORD      "), sDayOrd  );
	
    if ( sMbrCd == null ) sMbrCd = fce_GetUserInfo("MBR_CD");
	fce_AddParam(Trim("MBR_CD"), sMbrCd);
    
    http.sync = true;
	fce_SendTransaction(sServiceName, sInDataSet, sOutDataSet, "fcCallBack", false);
    http.sync = false;
    
    var dsObj = object(sDatasetName);
    var sRaceDay = "";
    if (dsObj.rowcount() > 0) {
		sRaceDay = dsObj.GetColumn(0, "RACE_DAY");
    }
    return sRaceDay;
}

