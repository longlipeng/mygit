/*
 * Copyright (C), 2002-2013, 苏宁易购电子商务有限公司
 * FileName: TreeNode.java
 * Author:   13010154
 * Date:     2013-11-6 下午02:52:53
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.allinfinance.univer.seller.seller.dto;

import com.allinfinance.framework.dto.BaseDTO;

/**
 * 树节点<br> 
 * 
 *
 * @author 13010154
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TreeNodeDTO extends BaseDTO {
	
	private static final long serialVersionUID = 7261712903068621559L;
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 父节点
	 */
	private String pId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 是否展开
	 */
	private boolean open;
	/**
	 * 是否选中
	 */
	private boolean checked;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
}
