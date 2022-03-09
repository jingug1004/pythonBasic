
/**************************************************************************************************
*  ��   ��  ��				: fnc_GetRaceTms
*  ��       ��				: �ش� �⵵�� ����ȸ�� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sQurtCd		    : �ش�б�
*  param3) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_GetRaceDayOrd
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sRaceTms		    : ȸ��
*  param3) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_GetRace
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sStndYear		: �ش�⵵
*  param2) sRaceTms		    : ȸ��
*  param3) sRaceDayOrd		: ����
*  param4) sDatasetName		: Dataset
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_SetCurTms
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  return Type				: ����
*  return ����				: ����
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
*  ��   ��  ��				: fnc_GetRaceDay
*  ��       ��				: �ش� ����ȸ���� ������ �������ڸ� ��ȸ
*  return Type				: ����
*  return ����				: ��������
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

