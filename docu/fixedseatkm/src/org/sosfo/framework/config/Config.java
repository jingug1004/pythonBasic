package org.sosfo.framework.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * <pre>
 *  Title: 환경설정 관리 
 *  CLASSPATH에 잡혀있는 properties파일을 읽어 들인다.
 *  Description: (commons-configuration-1.2.jar)
 *  Copyright: Copyright (c) 2006
 *  Company: www.sosfo.or.kr
 *  @author yunkidon@hotmail.com
 *  @version 1.0
 * </pre>
 */
public class Config extends PropertiesConfiguration {

    /**
     * 기본 생성자
     */
    protected Config(String propertiesName) throws Exception {
	super(propertiesName);
	super.setReloadingStrategy(new FileChangedReloadingStrategy());
	super.setAutoSave(true);
    }

    /**수정 가능한 Config
     * @param propertiesName String
     * @param editAble boolean
     * @throws Exception
     */
    protected Config(String propertiesName, boolean editAble) throws Exception {
	super(propertiesName);
	super.setAutoSave(editAble);
    }
}
