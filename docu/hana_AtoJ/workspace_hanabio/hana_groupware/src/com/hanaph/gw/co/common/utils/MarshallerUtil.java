package com.hanaph.gw.co.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <pre>
 * Class Name : MarshallerUtil.java
 * 설명 : 공통 오브젝트를 마샬링 class
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17. slamwin          생성
 * </pre>
 * 
 * @version 1.0
 * @author slawin(@irush.co.kr)
 * @since 2014. 10. 17.
 */
public class MarshallerUtil {

	/**
	 * 오브젝트를 마샬링 하는 메소드
	 * @param type	포맷 선택(xml일때만 xml, 그 외에는 json)
	 * @param response	응답객체
	 * @param obj	마샬링할 객체
	 * @throws IOException
	 */
	public static void marshalling(String type, HttpServletResponse response, Object obj) throws IOException {
        
		if ("txt".equals(type)) {
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().print((String)obj);
        } else {
            response.setContentType("application/json; charset=utf-8");
            String json = "";
            
            try{
            	/*object to json*/
        		JSONObject jsonObject = JSONObject.fromObject(obj); 
        		json = jsonObject.toString();
            } catch(Exception e) {
            	/*array to json*/
            	JSONArray jsonArray=JSONArray.fromObject(obj); 
        		json = jsonArray.toString();
            }
            
            response.getWriter().print(json);
        }
    }



}
