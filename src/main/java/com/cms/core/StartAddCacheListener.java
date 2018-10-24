package com.cms.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 服务器启动处理
 * 
 */
public class StartAddCacheListener implements ApplicationListener<ContextRefreshedEvent> {
	
	/**
	 * 应用程序启动
	 * 
	 * @param event 事件对象
	 */
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 避免二次加载
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
	}

}
