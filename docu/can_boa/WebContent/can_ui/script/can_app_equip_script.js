/*================================================================================================/
 * PROGRAM-ID                           template_app_script 
 * DESCRIPTION   :  �ش� ������ ���� Ʈ�����ó��                                            
 * CREATE DATE   :  2007. 10. 16                                                            
 * UPDATE DATE   :  2007. 10. 16                                                           
 * PROGRAMMER    :                                                                          
=================================================================================================*/
#include "lib::can_common_script.js";
#include "lib::can_message_script.js";
#include "lib::can_app_equip_script.js";


/*-------------------------------------------------------------------------------------------------+
>>  �Ҹ�ǰ������ ���� Tappage ó�� �� ���ð� ��ȸ �Լ�
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
		sPartsTpeNm = "�Ҹ�ǰ";
	} else {
		sPartsTpeNm = "��ǰ";
	}
	return sPartsTpeNm;
}


function fctabOnChanged(obj,nOldIndex,nNewindex)
{
	fcSearch();	
}


