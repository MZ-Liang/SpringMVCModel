package com.cms.core.database;

/**
 * 数据源管理
 * 
 */
public class DataSourceTypeManager {

	/** 本地数据源类型 */
	private static final ThreadLocal<DataSourceType> dataSourceTypes = new ThreadLocal<DataSourceType>();

	/**
	 * 设定数据源类型
	 * 
	 * @param type 数据源类型
	 */
	public static void setDataSourceType(DataSourceType type) {
		dataSourceTypes.set(type);
	}

	/**
	 * 取得数据源类型
	 * 
	 * @return 数据源类型
	 */
	public static DataSourceType getDataSourceType() {
		return dataSourceTypes.get();
	}

	/**
	 * 清除数据源类型
	 */
    public static void clear() {
    	dataSourceTypes.remove();
    }
}
