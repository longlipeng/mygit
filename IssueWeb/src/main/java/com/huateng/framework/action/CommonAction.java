package com.huateng.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.huateng.framework.exception.BizServiceException;

/**
 * action的基类
 * 
 * @author liming.feng
 * 
 */
public abstract class CommonAction extends BaseAction {
	
	public Logger logger = Logger.getLogger(CommonAction.class);

	public final static String EDIT = "edit";
	
	public final static String VIEW = "view";

	public final static String DELETE = "delete";

	public final static String ADD = "add";

	public final static String SAVE = "save";
	
	public final static String INSERT = "insert";

	public final static String LIST = "list";
	
	public final static String CANCEL = "cancel";
	
	public final static String SUBMIT = "submit";
	
	public final static String SEARCH = "search";

	protected String returnParam = LIST;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#input()
	 */
	@Override
	public String input() throws Exception {
        return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String add() throws Exception {
		init();
		// 新增操作
		isClearMessage = true;
		returnParam = ADD;
		doAdd(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }

	/**
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception {
		init();
		// 保存操作
		isClearMessage = false;
		returnParam = INSERT;
		doInsert(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		init();
		// 新增操作
		isClearMessage = true;
		returnParam = SAVE;
		doSave(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String edit() throws Exception {
		init();
		// 编辑操作
		isClearMessage = true;
		returnParam = EDIT;
		doEdit(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String delete() throws Exception {
		init();
		// 删除操作
		isClearMessage = false;
		returnParam = DELETE;
		doDelete(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */

	public String list() throws Exception {
		init();
		// 列表操作
		isClearMessage = true;
		returnParam  = LIST;
		doService(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }
	
	/**
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String view() throws Exception {
		init();
		// 列表操作
		isClearMessage = true;
		returnParam  = VIEW;
		doView(getRequest(), getResponse());
		
		sucess();
		return returnParam;
    }

	/**
	 * list列表操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 当前 操作是否为导出操作
		if (isExportView(request, tableId)) {
			// 导出操作
			doExport(request, response);
		} else {
			// 非导出操作
			doList(request, response);
		}
	}
	
	/**
	 * 初始化参数，例如tableId，该值默认为ec
	 */
	protected abstract void init();

	/**
	 * 所有操作完成后调用该方法
	 */
	protected abstract void sucess();
	
	/**
	 * 普通业务逻辑调用方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract void doList(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 单条数据获得调用方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public abstract void doView(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 导出xls和csv
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected abstract void doExport(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 新增操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 编辑操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 删除操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 保存操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doSave(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 插入操作
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void doInsert(HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}