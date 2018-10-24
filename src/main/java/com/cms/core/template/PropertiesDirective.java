package com.cms.core.template;

import java.util.Map;

import com.cms.core.PropertyPlaceholder;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

/**
 * 读取配置文件标签
 * 
 * @author Y.H
 * @date 2017-06-26
 */
public class PropertiesDirective extends TemplateDirective {

	/**
	 * 执行处理
	 * 
	 * @param env 模板环境对象
	 * @param paramMap 参数Map
	 * @param loopVars 变量数组
	 * @param body 标签体对象
	 * @throws Exception 异常
	 */
	@Override
	protected void proccess(Environment env, Map<?, ?> paramMap, TemplateModel[] loopVars, TemplateDirectiveBody body)
	        throws Exception {
		env.getOut().write(PropertyPlaceholder.getProperty(getNotEmptyString(paramMap, PARAM_KEY)));
	}

	/**
	 * 取得标签名称
	 * 
	 * @return 标签名称
	 */
	@Override
	protected String getTagName() {
		return "prop";
	}
}
