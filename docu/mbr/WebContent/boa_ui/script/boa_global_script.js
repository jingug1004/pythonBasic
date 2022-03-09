/**************************************************************************************************
*  ��   ��  ��				: fnc_GetCurRaceInfo
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sColumnName		: �÷���
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fce_GetCurRaceInfo(sColumnName)
{
    if ( gdsRace.GetRowCount() == 0 ) return "";
    
    var nRow = fnc_GetExistValue(gdsRace, "CURDATE", "TRUE");
    if ( nRow < 0 ) nRow = 0;
    
    return gdsRace.GetColumn(nRow, sColumnName);
}

/**************************************************************************************************
*  ��   ��  ��				: fnc_GetCurRaceInfo
*  ��       ��				: �ش� �⵵�� ����ȸ���� ���� ��ȸ
*  param1) sColumnName		: �÷���
*  return Type				: ����
*  return ����				: ����
**************************************************************************************************/
function fce_GetUserInfo(sColumnName)
{
    if ( gdsUser.GetRowCount() == 0 ) return "";
    
    var nRow = 0;
    
    return gdsUser.GetColumn(nRow, sColumnName);
}


