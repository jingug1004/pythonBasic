/**
 * Hana Project
 * Copyright 2014 iRush Co.,
 *
 */
package com.hanaph.gw.co.common.interceptor;

/**
 * <pre>
 * Class Name : NoLoginCheck.java
 * 설명 : 로그인 체크 불필요한 어노테이션 생성
 * 
 * Modification Information
 *    수정일      수정자              수정 내용
 *  ------------ -------------- --------------------------------
 *  2014. 10. 17.      재가부          
 * </pre>
 * 
 * @version : 
 * @author  : 재가부(@irush.co.kr)
 * @since   : 2014. 10. 17.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLoginCheck {
}
