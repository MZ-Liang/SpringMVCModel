package com.cms.core.template;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

/**
 * 输出内容省略标签
 * 
 * @author Y.H
 * @date 2017-05-27
 */
public class EllipsisTextDirective extends TemplateDirective {

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
		// 内容
		String value = getNotEmptyString(paramMap, PARAM_VALUE).trim();
		// 长度
		Number length = getNumber(paramMap, PARAM_LENGTH);
		// 结果
		String result = StringUtils.EMPTY;
		// 内容存在时
		if (StringUtils.isNotEmpty(value)) {
			// 长度
			int lengthInt = length == null ? 150 : length.intValue();
			//去除html标签
            result = getPlainText(result);
			// 省略
			result = value.length() > lengthInt ? value.substring(0, lengthInt) + "..." : value;
			
		}
		// 省略后内容
		env.getOut().write(encodeHtml(result));
	}

	/**
	 * 取得标签名称
	 * 
	 * @return 标签名称
	 */
	@Override
	protected String getTagName() {
		return "encodeHtml";
	}

}
