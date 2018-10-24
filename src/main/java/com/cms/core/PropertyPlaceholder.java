package com.cms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 资源文件处理
 * 
 * @author Y.H
 * @data 2017-03-31
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer {

	// PropertyPlaceholder.getProperty("sql.name");
	/** 资源文件Map */
	private static Map<String, String> propertyMap;

	/**
	 * 资源文件Map填充处理
	 * 
	 * @param factory 配置定义对象
	 * @param props 资源文件对象
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory factory, Properties props) throws BeansException {
		super.processProperties(factory, props);
		propertyMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			propertyMap.put(keyStr, value);
		}
	}

	/**
	 * 根据关键字取得对应的值
	 * 
	 * @param key 关键字
	 * @return 对应值
	 */
	public static String getProperty(String key) {
		return propertyMap.get(key);
	}
}
