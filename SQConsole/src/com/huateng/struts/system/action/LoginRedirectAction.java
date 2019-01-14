package com.huateng.struts.system.action;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.huateng.bo.base.T90102BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.base.TblOprInfoDAO;
import com.huateng.po.TblOprInfo;
import com.huateng.po.base.TblBranchManageTrue;
import com.huateng.startup.init.MenuInfoUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Title:登录后跳转
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-3
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class LoginRedirectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginRedirectAction.class);
	public String execute() throws Exception {
		String oprId = (String) getSessionAttribute("oprId");
		log(oprId);
		if(oprId == null)
		{
			return LOGIN;
		}
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
			
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprId);
		
		//判断操作员是否存在
		if(tblOprInfo == null) {
			ServletActionContext.getRequest().getSession().removeAttribute("oprId");
			log("登录失败，操作员不存在。编号[ " + oprId + " ]");
			return LOGIN;
		}
		//验证通过
		//T10101BO t10101BO = (T10101BO) ContextUtil.getBean("T10101BO");
		//TblBrhInfo tblBrhInfo = t10101BO.get(tblOprInfo.getBrhId());
		T90102BO t90102BO=(T90102BO) ContextUtil.getBean("T90102BO");
		TblBranchManageTrue tblBranchManageTrue=t90102BO.get(tblOprInfo.getBrhId());
		
		Operator operator = new Operator();
		operator.setOprId(oprId);
		operator.setOprName(StringUtils.trim(tblOprInfo.getOprName()));
		operator.setOprBrhId(tblBranchManageTrue.getId());
		operator.setOprBrhName(StringUtils.trim(tblBranchManageTrue.getBranchname()));
		operator.setOprDegree(tblOprInfo.getOprDegree());
		//operator.setOprBrhLvl(tblBrhInfo.getBrhLevel());
		//由于目前分公司 设计缺陷，没有分等级，所以所有的分公司 级别都为0
		operator.setOprBrhLvl("0");
		//本行及下属机构信息MAP
		Map<String, String> brhMap = new LinkedHashMap<String, String>();
		brhMap.put(operator.getOprBrhId(),operator.getOprBrhName());
		operator.setBrhBelowMap(CommonFunction.getBelowBrhMap(brhMap));
		operator.setBrhBelowId(CommonFunction.getBelowBrhInfo(operator.getBrhBelowMap()));
		log("本公司及下属分公司编号：" + operator.getBrhBelowId());
		setSessionAttribute(Constants.OPERATOR_INFO, operator);
		//银联编号  由于分公司没有银联编号，并且15商户编号为人工输入，不按规则生成 ，所以银联编号都设为“00000000”
		//setSessionAttribute("CUP_BRH_ID", tblBrhInfo.getCupBrhId());
		setSessionAttribute("CUP_BRH_ID","00000000");
		/*String sql = "select cup_brh_id from tbl_brh_info where trim(brh_id) = '" + operator.getOprBrhId().trim() + "'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				String cup = (String) list.get(0);
				setSessionAttribute("cupBrhId", cup);
			} else {
				setSessionAttribute("cupBrhId", "-");
			}
		}*/
		setSessionAttribute("cupBrhId", "-");
		
		LinkedHashMap<String,Object> menuMap = MenuInfoUtil.setOperatorMenuWithDegree(operator.getOprDegree(),
						ServletActionContext.getRequest().getContextPath());
		Iterator<String> iter = menuMap.keySet().iterator();
		LinkedList<Object> toolList = new LinkedList<Object>();
		while(iter.hasNext()) {
			toolList.add(menuMap.get(iter.next()));
		}
		String toolBarStr = JSONArray.fromObject(toolList).toString();
		//转换JSON格式中的方法名称
		toolBarStr = toolBarStr.replaceAll(Constants.MENU_LVL1_JSON_FUNC, Constants.MENU_LVL1_FUNC).
									replaceAll(Constants.MENU_LVL3_JSON_FUNC, Constants.MENU_LVL3_FUNC);
		//设置操作员
		setSessionAttribute(Constants.OPERATOR_INFO, operator);
		//去掉菜单树图标样式
		removeTreeCls(menuMap);
		//设置菜单树
		setSessionAttribute(Constants.TREE_MENU_MAP, menuMap);
		//设置工具栏
		setSessionAttribute(Constants.TOOL_BAR_STR, toolBarStr);
		//设置交易权限
		setSessionAttribute(Constants.USER_AUTH_SET, MenuInfoUtil.getAuthSet(operator.getOprDegree()));
		
		//判断是否时首次登陆  为1时首次登陆
		setSessionAttribute("resv2", tblOprInfo.getResv2());
		return SUCCESS;
	}
	
	/**
	 * 去掉菜单树图标样式
	 * @param menuMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map removeTreeCls(Map<String,Object> menuMap) {
		Iterator<String> iter = menuMap.keySet().iterator();
		String key;
		Map map;
		List list;
		while(iter.hasNext()) {
			key = iter.next();
			if(menuMap.get(key) instanceof String && 
						Constants.TOOLBAR_ICON.equals(key)) {
				menuMap.remove(key);
				return menuMap;
			} else if(menuMap.get(key) instanceof Map) {
				map = (Map) menuMap.get(key);
				if(map.get(Constants.TOOLBAR_ICON) != null) {
					map.remove(Constants.TOOLBAR_ICON);
				}
				map = removeTreeCls(map);
				menuMap.put(key, map);
			}else if(menuMap.get(key) instanceof List) {
				list = (List)menuMap.get(key);
				for(int i = 0; i < list.size(); i++) {
					if(list.get(i) instanceof Map) {
						list.set(i, removeTreeCls((Map)list.get(i)));
					}
				}
				menuMap.put(key, list);
			}
		}
		return menuMap;
	}
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected void log(String info) {
		log.info(info);
	}
	/**
	 * 设置session的attribute
	 * @param name
	 * @param value
	 */
	protected void setSessionAttribute(String name,Object value) {
		ServletActionContext.getRequest().getSession().setAttribute(name, value);
	}
	/**
	 * 获得session的attribute
	 * @param name
	 */
	protected Object getSessionAttribute(String name) {
		return ServletActionContext.getRequest().getSession().getAttribute(name);
	}
}
