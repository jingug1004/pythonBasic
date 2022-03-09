/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  해당 업무의 공통 트랜잭션처리                                            
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::can_common_script.js";
#include "lib::can_message_script.js";
#include "lib::can_app_equip_script.js";


/*-------------------------------------------------------------------------------------------------+
>>  소모품관리를 위한 Tappage 처리 및 선택값 조회 함수
+-------------------------------------------------------------------------------------------------*/
function fcgetPartGbn() {
	var sPartsTpe = "";
	if (tabTpe.TabIndex == 1) {
		sPartsTpe = "W";
	} else {
		sPartsTpe = "P";
	}
	return sPartsTpe;
}


function fcgetPartTpe() {
	var sPartsTpe = "";
	if (tabTpe.TabIndex == 1) {
		sPartsTpe = "W";
	} else {
		sPartsTpe = "M";
	}
	return sPartsTpe;
}

function fcgetPartGbnNm() {
	var sPartsTpeNm = "";
	if (tabTpe.TabIndex == 1) {
		sPartsTpeNm = "소모품";
	} else {
		sPartsTpeNm = "부품";
	}
	return sPartsTpeNm;
}


function fctabOnChanged(obj,nOldIndex,nNewindex)
{
	fcSearch();	
}


