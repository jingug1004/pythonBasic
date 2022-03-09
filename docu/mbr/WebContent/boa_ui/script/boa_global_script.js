/**************************************************************************************************
*  함   수  명				: fnc_GetCurRaceInfo
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sColumnName		: 컬럼명
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetCurRaceInfo(sColumnName)
{
    if ( gdsRace.GetRowCount() == 0 ) return "";
    
    var nRow = fnc_GetExistValue(gdsRace, "CURDATE", "TRUE");
    if ( nRow < 0 ) nRow = 0;
    
    return gdsRace.GetColumn(nRow, sColumnName);
}

/**************************************************************************************************
*  함   수  명				: fnc_GetCurRaceInfo
*  설       명				: 해당 년도의 경주회차의 일차 조회
*  param1) sColumnName		: 컬럼명
*  return Type				: 없음
*  return 내용				: 없음
**************************************************************************************************/
function fce_GetUserInfo(sColumnName)
{
    if ( gdsUser.GetRowCount() == 0 ) return "";
    
    var nRow = 0;
    
    return gdsUser.GetColumn(nRow, sColumnName);
}


