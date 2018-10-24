package com.cms.core.template;

import java.io.IOException;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import net.htmlparser.jericho.Source;

/**
 * 模板日期格式化标签
 * 
 * @author Y.H
 * @date 2017-05-19
 */
public abstract class TemplateDirective implements TemplateDirectiveModel {
	
	/** 键 */
	protected static final String PARAM_KEY = "key";

	/** 值 */
	protected static final String PARAM_VALUE = "value";
	
	/** 格式 */
	protected static final String PARAM_PATTERN = "pattern";

	/** 长度 */
	protected static final String PARAM_LENGTH = "length";

	/**
	 * 执行
	 * 
	 * @param env 模板环境对象
	 * @param paramMap 参数Map
	 * @param loopVars 变量数组
	 * @param body 标签体对象
	 * @throws TemplateException Template异常
	 * @throws IOException IO异常
	 */
	@SuppressWarnings("rawtypes")
	public final void execute(Environment env, Map paramMap, TemplateModel[] loopVars, TemplateDirectiveBody body)
	        throws TemplateException, IOException {
		try {
			beforeProccess(env, paramMap, body);
			proccess(env, paramMap, loopVars, body);
			afterProccess(env);
		} catch (Exception e) {
			throw new TemplateException(e, env);
		}
	}

	/**
	 * 前处理
	 * 
	 * @param env 模板环境对象
	 * @param paramMap 参数Map
	 * @param body 标签体对象
	 * @throws TemplateException Template异常
	 */
	protected void beforeProccess(Environment env, Map<?, ?> paramMap, TemplateDirectiveBody body) throws TemplateException {
	}

	/**
	 * 执行处理
	 * 
	 * @param env 模板环境对象
	 * @param paramMap 参数Map
	 * @param loopVars 变量数组
	 * @param body 标签体对象
	 * @throws Exception 异常
	 */
	protected abstract void proccess(Environment env, Map<?, ?> paramMap, TemplateModel[] loopVars,
	        TemplateDirectiveBody body) throws Exception;

	/**
	 * 后处理
	 * 
	 * @param env 模板环境对象
	 * @throws TemplateException Template异常
	 */
	protected void afterProccess(Environment env) throws TemplateException {
	}

	/**
	 * 取得标签名称
	 * 
	 * @return 标签名称
	 */
	protected abstract String getTagName();

	/**
	 * 取得字符串
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @return 字符串
	 * @throws TemplateException Template异常
	 */
	protected String getString(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (!paramMap.containsKey(paramName)) {
			return StringUtils.EMPTY;
		}
		TemplateModel model = (TemplateModel) paramMap.get(paramName);
		if (!(model instanceof SimpleScalar)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "]'s object type must be a string.");
		}
		return ((SimpleScalar) model).getAsString();
	}

	/**
	 * 取得不能为空的字符串
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @return 字符串
	 * @throws TemplateException Template异常
	 */
	protected String getNotEmptyString(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (!paramMap.containsKey(paramName)) {
			return StringUtils.EMPTY;
		}
		String result = getString(paramMap, paramName);
		if (StringUtils.isEmpty(result)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "] can not be empty.");
		}
		return result;
	}

	/**
	 * 取得布尔类型对象
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @return 数值类型对象
	 * @throws TemplateException Template异常
	 */
	protected Number getNumber(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (!paramMap.containsKey(paramName)) {
			return null;
		}
		TemplateModel model = (TemplateModel) paramMap.get(paramName);
		if (!(model instanceof SimpleNumber)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "]'s object type must be a number.");
		}
		return ((SimpleNumber) model).getAsNumber();
	}

	/**
	 * 取得布尔类型对象
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @return 布尔类型对象
	 * @throws TemplateException Template异常
	 */
	protected boolean getBoolean(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (!paramMap.containsKey(paramName)) {
			return false;
		}
		TemplateModel model = (TemplateModel) paramMap.get(paramName);
		if (!(model instanceof TemplateBooleanModel)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "]'s object type must be a boolean.");
		}
		return ((TemplateBooleanModel) model).getAsBoolean();
	}
	
	/**
	 * 取得模板模型对象
	 * 
	 * @param env 模板环境对象
	 * @param property 属性名称
	 * @return 模板模型对象
	 * @throws TemplateModelException TemplateModel异常
	 */
	protected TemplateModel getTemplateModel(Environment env, String property) throws TemplateModelException {
		TemplateModel model = env.getGlobalVariable(property);
		return (model instanceof TemplateDirectiveModel) ? null : model;
	}

	/**
	 * 取得属性值
	 * 
	 * @param env コンテキスト
	 * @param property 属性名称
	 * @return 属性值
	 * @throws TemplateModelException TemplateModel异常
	 */
	protected String getModelValue(Environment env, String property) throws TemplateModelException {

		// 取得模板模型对象
		TemplateModel model = getTemplateModel(env, property);

		// 不存在时
		if (model == null) {
			return StringUtils.EMPTY;
		}

		// 值
		String value = null;

		// 字符类型
		if (model instanceof SimpleScalar) {
			value = ((SimpleScalar) model).getAsString();
		}
		// 数值类型
		else if (model instanceof SimpleNumber) {
			Number number = ((SimpleNumber) model).getAsNumber();
			value = number.toString();
		}
		// 布尔类型
		else if (model instanceof TemplateBooleanModel) {
			value = String.valueOf(((TemplateBooleanModel) model).getAsBoolean());
		}
		else {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] value's type must be a string, number or boolean.");
		}
		// 值
		return value;
	}

	/**
	 * 判断参数是否必须
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @throws TemplateException Template异常
	 */
	protected void parameterRequired(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (!paramMap.containsKey(paramName)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "] must be required.");
		}
	}

	/**
	 * 判断参数是否允许
	 * 
	 * @param paramMap 参数Map
	 * @param paramName 参数名
	 * @throws TemplateException Template异常
	 */
	protected void parameterNotAllowed(Map<?, ?> paramMap, String paramName) throws TemplateModelException {
		if (paramMap.containsKey(paramName)) {
			throw new TemplateModelException(
			        "The tag[" + getTagName() + "] parameter[" + paramName + "] is not allowed.");
		}
	}
	
	/**
	 * HTML特殊字符转码
	 * 
	 * @param html HTML
	 * @return 转码后HTML
	 */
	protected String encodeHtml(String html) {
		return StringUtils.replaceEach(html, new String[] {"&", "<", ">", "\"", "'", "\\r\\n"}, new String[] {"&amp;", "&lt;",
		        "&gt;", "&quot;", "&#39;", "<br>"});
	}
	
	/**
	 * 去掉html标签，获取纯文本
	 * 
	 * @return 纯文本string
	 */
	protected String getPlainText(String html){
	    if( html==null|| "".equals(html) ){
	        return "";
	    }else{
	        Source src = new Source(html);
	        String str = src.getTextExtractor().toString().trim();
	        return str.replaceAll("  ", " ");
	    }
	}
}