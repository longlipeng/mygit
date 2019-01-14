/**
 *
 */
package com.huateng.framework.util;

import com.huateng.framework.dao.CommonsDAO;

/**
 * @author jiagang 主键生成
 */
public class SequenceAccessor {

	private CommonsDAO commonsDAO;

	private String sequenceName;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public CommonsDAO getCommonsDAO() {
		return commonsDAO;
	}

	public void setCommonsDAO(CommonsDAO commonsDAO) {
		this.commonsDAO = commonsDAO;
	}
}
