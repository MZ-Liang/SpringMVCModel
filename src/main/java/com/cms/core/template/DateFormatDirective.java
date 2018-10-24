package com.cms.core.template;

import java.util.Calendar;
import java.util.Map;

import com.cms.util.CalendarUtility;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;

/**
 * 模板日期格式化标签
 * 
 * @author Y.H
 * @date 2017-05-19
 */
public class DateFormatDirective extends TemplateDirective {

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
		// 值
		Number value = getNumber(paramMap, PARAM_VALUE);
		// 格式
		String pattern = getNotEmptyString(paramMap, PARAM_PATTERN);
		// 取得日期对象
		Calendar calendar = CalendarUtility.getCalendar(value.longValue());
		// Calendar类型对象转字符串
		env.getOut().write(CalendarUtility.convertCalendarToStr(calendar, pattern));
	}

	/**
	 * 取得标签名称
	 * 
	 * @return 标签名称
	 */
	@Override
	protected String getTagName() {
		return "dateFormat";
	}

}
