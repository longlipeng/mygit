package com.huateng.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * action的基类
 * 
 * @author liming.feng
 * 
 */
public class BaseAction extends ActionSupport implements Preparable {

	public Logger logger = Logger.getLogger(BaseAction.class);

	private static final long serialVersionUID = -185640529641098689L;

	// ectable的id，默认为ec
	protected String tableId = "ec";

	// 开始前是否清除所有消息和错误信息，默认选是
	protected boolean isClearMessage = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		if (isClearMessage)
			this.clearMessages();

		this.clearErrors();

		initBase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 初始化方法
	 */
	protected void initBase() {

	}

	/**
	 * 取得session
	 * 
	 * @return
	 */
	public Map<String, Object> getSession() {
		return ServletActionContext.getContext().getSession();
	}

	/**
	 * 取得Request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) ServletActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
	}

	/**
	 * 取得Response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) ServletActionContext.getContext().get(
				ServletActionContext.HTTP_RESPONSE);
	}

	/**
	 * 取得application
	 * 
	 * @return
	 */
	public Map<String, Object> getApplication() {
		return ServletActionContext.getContext().getApplication();
	}

	/**
	 * 取得Parameters
	 * 
	 * @return
	 */
	public Map<String, Object> getParameters() {
		return ServletActionContext.getContext().getParameters();
	}

	/**
	 * @param date
	 * @param format
	 *            -1:YYYY-MM-DD 0:2009年1月1日 星期四 1:2009年1月1日 2:2009-1-1
	 *            3:09-1-1
	 * @return
	 */
	protected String getDateFormat(Date date, int format) {
		String returnValue = null;
		try {
			if (format == -1) {
				returnValue = DateFormat.getDateInstance().format(date);
			} else {
				returnValue = DateFormat.getDateInstance(format).format(date);
			}

		} catch (Exception e) {

		}
		return returnValue;
	}

	/**
	 * @param date
	 * @return YYYY-MM-DD
	 */
	protected String getDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String returnValue = null;
		try {
			returnValue = simpleDateFormat.format(date);
		} catch (Exception e) {

		}
		return returnValue;
	}

	/***
	 *判断是否为空
	 */

	public boolean isNotEmpty(String string) {
		if (string != null && !"".equals(string)) {
			return true;
		}
		return false;
	}

	public boolean isEmpty(String string) {
		return !isNotEmpty(string);
	}

	/**
	 * 取一个当前用户一个包含IssuerGroup和Issuer的一个列表
	 * 列表的值用map来表示，如果该值是IssuerGroup则key的值为
	 * 0＋IssuerGroupID来表示，如果该值为Issuer则key的值为 1＋IssuerId
	 * 
	 * @return
	 */

	// public List<Map<String, String>> getIssuerGroupLst() {
	//
	// User user = getUser();
	//
	// List<Map<String, String>> lstIssuerGroup = new
	// ArrayList<Map<String, String>>();
	//
	// Short groupUserFlag = user.getGroupUserFlag();
	// /**
	// * 如果当前用户为发卡机构用户，则只返回该发卡机构信息
	// */
	// if (groupUserFlag == 0) {
	// Map<String, String> issuer = new HashMap<String, String>();
	// issuer.put("key", "1" + user.getIssuerId());
	// issuer.put("value", user.getIssuerName());
	// lstIssuerGroup.add(issuer);
	// } else {
	// Map<String, String> issuerGroup = new HashMap<String,
	// String>();
	//
	// issuerGroup.put("key", "0" +
	// user.getIssuerGroupId().intValue());
	// issuerGroup.put("value", user.getIssuerGroupName());
	//
	// lstIssuerGroup.add(issuerGroup);
	//
	// /**
	// * 取组下面发卡机构信息
	// */
	// List<IssuerDTO> lstIssuer = user.getIssuerList();
	//
	// for (int i = 0; i < lstIssuer.size(); i++) {
	// IssuerDTO issuerDTO = lstIssuer.get(i);
	// Map<String, String> issuer = new HashMap<String, String>();
	// issuer.put("key", "1" + issuerDTO.getIssuerId().intValue());
	// issuer.put("value", issuerDTO.getIssuerName());
	// lstIssuerGroup.add(issuer);
	// }
	// }
	//
	// return lstIssuerGroup;
	// }

	/**
	 * 根据当前用户信息返回一个发卡机构的列表数据 如果该用户为发卡机构组用户,则列表据为
	 * 该组下面所有发卡机构的信息，如果该用户为发卡机构用户 则列表数据就是该发卡机构的信息
	 * 
	 * @return
	 */
	// public List<IssuerDTO> getIssuerLst() {
	//
	// User user = getUser();
	// Integer issuerId = user.getIssuerId();
	//
	// Short groupUserFlag = user.getGroupUserFlag();
	// List<IssuerDTO> lstIssuer = new ArrayList<IssuerDTO>();
	// /**
	// * 如果当前用户是发卡机构用户则返回list只包含该发卡机构信息
	// */
	// if (groupUserFlag == 0) {
	// IssuerDTO issuerDTO = new IssuerDTO();
	// issuerDTO.setIssuerId(user.getIssuerId());
	// issuerDTO.setIssuerName(user.getIssuerName());
	// issuerDTO.setIssuerGroupId(user.getIssuerGroupId());
	// lstIssuer.add(issuerDTO);
	// } else {
	// // 如果当前用户是发卡机构组用户,且当前操作数据为发卡机构下面的发卡机构
	// if (issuerId == 0) {
	// lstIssuer = user.getIssuerList();
	// } else {
	// List<IssuerDTO> lstIssuer1 = user.getIssuerList();
	// for (int i = 0; i < lstIssuer1.size(); i++) {
	// IssuerDTO issuerDTO = lstIssuer1.get(i);
	// if (issuerDTO.getIssuerId() == issuerId.intValue()) {
	// lstIssuer.add(issuerDTO);
	// continue;
	// }
	// }
	// }
	// }
	// return lstIssuer;
	// }

	/**
	 * 分到元得转换
	 * 
	 * @param money
	 * @return
	 */
	public String moneyTransF2Y(String b) {
		if (b == null) {
			return "0.00";
		}
		if (b.length() < 3 && b.length() > 1) {
			b = "0." + b;
		} else if (b.length() < 2) {
			b = "0.0" + b;
		} else {
			b = b.substring(0, b.length() - 2) + "."
					+ b.substring(b.length() - 2);
		}
		return b;
	}

	public String moneyTransF2Y(Long l) {
		if (l == null) {
			return moneyTransF2Y("");
		}
		return moneyTransF2Y(l.toString());
	}

	/**
	 * 元到分得转换
	 * 
	 * @param s
	 * @return
	 */
	public Long moneyTransY2F(String s) {
		String[] ss = s.split("\\.");
		if (ss.length < 2) {
			return new Long(s + "00");
		} else {
			if (ss[1].length() == 1) {
				ss[1] += "0";
			} else if (ss[1].length() > 2) {
				ss[1] = ss[1].substring(0, 2);
			}

			return new Long(ss[0] + ss[1]);
		}
	}

}
