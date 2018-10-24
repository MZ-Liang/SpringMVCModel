package com.cms.entity;

import java.io.Serializable;

/**
 * 实体
 * 
 */
public class Entity implements Serializable {

	/** SerialVersionUID */
	private static final long serialVersionUID = 1L;

	/** ID */
	protected Long id;

	/**
	 * 取得ID
	 * 
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设定ID
	 * 
	 * @param id ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
}