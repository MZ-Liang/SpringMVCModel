package com.cms.core.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 本地数据源
 * 
 */
public class LocalRoutingDataSource extends AbstractRoutingDataSource {

	/**
	 * 取得数据源查询关键字
	 * 
	 * @return 数据源类型
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceTypeManager.getDataSourceType();
	}

}
