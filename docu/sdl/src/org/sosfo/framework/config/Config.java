package org.sosfo.framework.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * <pre>
 *  Title: ȯ�漳�� ���� 
 *  CLASSPATH�� �����ִ� properties������ �о� ���δ�.
 *  Description: (commons-configuration-1.2.jar)
 *  Copyright: Copyright (c) 2006
 *  Company: www.sosfo.or.kr
 *  @author yunkidon@hotmail.com
 *  @version 1.0
 * </pre>
 */
public class Config extends PropertiesConfiguration {

    /**
     * �⺻ ������
     */
    protected Config(String propertiesName) throws Exception {
	super(propertiesName);
	super.setReloadingStrategy(new FileChangedReloadingStrategy());
	super.setAutoSave(true);
    }

    /**���� ������ Config
     * @param propertiesName String
     * @param editAble boolean
     * @throws Exception
     */
    protected Config(String propertiesName, boolean editAble) throws Exception {
	super(propertiesName);
	super.setAutoSave(editAble);
    }
}
