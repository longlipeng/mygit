package com.huateng.framework.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.extremecomponents.table.context.Context;
import org.extremecomponents.table.context.HttpServletRequestContext;
import org.extremecomponents.table.core.Preferences;
import org.extremecomponents.table.core.PreferencesConstants;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModelUtils;
import org.extremecomponents.table.core.TableProperties;
import org.extremecomponents.table.limit.Limit;
import org.extremecomponents.table.limit.LimitFactory;
import org.extremecomponents.table.limit.TableLimit;
import org.extremecomponents.table.limit.TableLimitFactory;
import org.springframework.security.context.SecurityContextHolder;

import com.allinfinance.framework.dto.BaseDTO;
import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.dto.PageQueryDTO;
import com.huateng.framework.constant.Const;
import com.huateng.framework.security.model.User;
import com.huateng.framework.webservice.service.WebServiceClientRptService;
import com.huateng.framework.webservice.service.WebServiceClientService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * action的基类
 *
 * @author liming.feng
 *
 */
public class BaseAction extends ActionSupport implements Preparable {

	private Logger logger = Logger.getLogger(BaseAction.class);

    private static final long serialVersionUID = -185640529641098689L;

    // 和后台交互的service
    private WebServiceClientService webServiceClientService;
    
    /** The web service client rpt service. */
	private WebServiceClientRptService webServiceClientRptService;
	
	/**
	 * Gets the web service client rpt service.
	 * 
	 * @return the web service client rpt service
	 */
	public WebServiceClientRptService getWebServiceClientRptService() {
		return webServiceClientRptService;
	}

	/**
	 * Sets the web service client rpt service.
	 * 
	 * @param webServiceClientRptService the new web service client rpt service
	 */
	public void setWebServiceClientRptService(
			WebServiceClientRptService webServiceClientRptService) {
		this.webServiceClientRptService = webServiceClientRptService;
	}

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
     * 调用后台程序,如果出现异常情况，将异常信息写入ErrorMessage中
     *
     * @param code
     * @param DTO
     *
     */
    @SuppressWarnings("unchecked")
    public OperationResult sendService(String code, Object DTO) {

        OperationResult operationResult = null;
        try {
            OperationCtx operationCtx = new OperationCtx();
            //设置交易代码和交易用户
            operationCtx.setTxncode(code);
            operationCtx.setOprId(getUser().getUserId() + "");
            OperationRequest operationRequest = new OperationRequest();
            /**
             * 如果来的DTO为BaseDTO,则加上用户和发卡机构信息
             */
            if (DTO instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) DTO;
                baseDTO.setLoginUserId(this.getUser().getUserId());
                baseDTO.setDefaultEntityId(this.getUser().getEntityId());
                operationRequest.setTxnvo(baseDTO);
            } else {
                operationRequest.setTxnvo(DTO);
            }
            // 调用后台请求
            operationResult = webServiceClientService.process(operationCtx, operationRequest);
            if (operationResult.getTxnstate().equals(Const.RETURN_FAILED)) {
                if (operationResult.getErrMessage() != null) {
                    addActionError(operationResult.getErrMessage());
                }
                if (operationResult.getTxncause() != null) {
                    List causeList = (List) operationResult.getTxncause();
                    if (causeList != null) {
                        for (int i = 0; i < causeList.size(); i++) {
                            addActionError((String) causeList.get(i));
                        }
                    }
                }
                if (!hasActionErrors()) {
                	logger.error("response code is error :::: 0");
                    addActionError("系统异常");
                }
            }

        } catch (Exception e) {
        	this.logger.error(e.getMessage());
            addActionError("系统异常");
        }
        return operationResult;
    }

    /**
     * list列表页面初始化获得页数等信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected void ListPageInit(String tableId, PageQueryDTO pageQueryDTO) throws Exception {

        if (pageQueryDTO == null)
            return;

        if (tableId != null)
            this.tableId = tableId;

        HttpServletRequest request = getRequest();

        Limit limit = getLimit(request, tableId);
        pageQueryDTO.setCurrentPage(limit.getPage());

        pageQueryDTO.setRowsDisplayed(getRowsDisplayed(request, tableId));

        pageQueryDTO.setQueryAll(this.isExportView(request, tableId));

        // 是否有排序，如果有，记录排序的字段名、字段别名和排序的种类（降序or升序）
        if (limit.getSort() != null) {
            // 排序的种类
            pageQueryDTO.setSort(limit.getSort().getSortOrder());
            if (TableConstants.SORT_ASC.equals(limit.getSort().getSortOrder())
                    || TableConstants.SORT_DESC.equals(limit.getSort().getSortOrder())) {
                // 排序的字段名
                pageQueryDTO.setSortFieldName(limit.getSort().getProperty());
                // 排序的字段别名
                pageQueryDTO.setSortFieldAliasName(limit.getSort().getAlias());
            }
        }
    }

    /**
     * 得到ec分页、排序、索引用的Limit
     *
     * @param request
     * @return
     */
    protected Limit getLimit(HttpServletRequest request, String tableId) {
        if (tableId == null)
            tableId = "ec";
        Context context = new HttpServletRequestContext(request);
        LimitFactory limitFactory = new TableLimitFactory(context, tableId);
        Limit limit = new TableLimit(limitFactory);
        return limit;
    }

    /**
     * 判断是否是导出操作
     *
     * @param request
     * @return
     */
    protected boolean isExportView(HttpServletRequest request, String tableId) {
        if (tableId == null)
            tableId = "ec";
        Object rd = request.getParameterMap().get(TableConstants.EXPORT_TABLE_ID);

        if (rd != null) {
            String ex = ((String[]) rd)[0];
            return StringUtils.isNotBlank(ex);
        }
        return false;
    }

    /**
     * 得到每页的页数
     *
     * @param request
     * @return
     */
    protected int getRowsDisplayed(HttpServletRequest request, String tableId) {
        if (tableId == null)
            tableId = "ec";
        Context context = new HttpServletRequestContext(request);
        String rowsDisplayed = String.valueOf(getLimit(request, tableId).getCurrentRowsDisplayed());
        Preferences preferences = new TableProperties();
        preferences.init(context, TableModelUtils.getPreferencesLocation(context));
        if ("0".equals(rowsDisplayed)) {
            Object rd = request.getParameterMap().get(tableId + "_" + TableConstants.ROWS_DISPLAYED);
            if (rd != null) {
                rowsDisplayed = ((String[]) rd)[0];
            } else {
                rowsDisplayed = preferences.getPreference(PreferencesConstants.TABLE_ROWS_DISPLAYED);
            }
        }
        return Integer.parseInt(rowsDisplayed);
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
        return (HttpServletRequest) ServletActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }

    /**
     * 取得Response
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) ServletActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
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
     *            -1:YYYY-MM-DD 0:2009年1月1日 星期四 1:2009年1月1日 2:2009-1-1 3:09-1-1
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
    
    public  boolean isNotEmpty(String string){
		if(string!=null&&!"".equals(string)){
			return true;
		}
		return false;
	}
	
	public  boolean isEmpty(String string){
		return !isNotEmpty(string);
	}
    
    
    /**
     * 取登陆用户信息
     *
     * @return
     */
    public User getUser() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * 取一个当前用户一个包含IssuerGroup和Issuer的一个列表 列表的值用map来表示，如果该值是IssuerGroup则key的值为
     * 0＋IssuerGroupID来表示，如果该值为Issuer则key的值为 1＋IssuerId
     *
     * @return
     */
    
    
//    public List<Map<String, String>> getIssuerGroupLst() {
//
//        User user = getUser();
//
//        List<Map<String, String>> lstIssuerGroup = new ArrayList<Map<String, String>>();
//
//        Short groupUserFlag = user.getGroupUserFlag();
//        /**
//         * 如果当前用户为发卡机构用户，则只返回该发卡机构信息
//         */
//        if (groupUserFlag == 0) {
//            Map<String, String> issuer = new HashMap<String, String>();
//            issuer.put("key", "1" + user.getIssuerId());
//            issuer.put("value", user.getIssuerName());
//            lstIssuerGroup.add(issuer);
//        } else {
//            Map<String, String> issuerGroup = new HashMap<String, String>();
//
//            issuerGroup.put("key", "0" + user.getIssuerGroupId().intValue());
//            issuerGroup.put("value", user.getIssuerGroupName());
//
//            lstIssuerGroup.add(issuerGroup);
//
//            /**
//             * 取组下面发卡机构信息
//             */
//            List<IssuerDTO> lstIssuer = user.getIssuerList();
//
//            for (int i = 0; i < lstIssuer.size(); i++) {
//                IssuerDTO issuerDTO = lstIssuer.get(i);
//                Map<String, String> issuer = new HashMap<String, String>();
//                issuer.put("key", "1" + issuerDTO.getIssuerId().intValue());
//                issuer.put("value", issuerDTO.getIssuerName());
//                lstIssuerGroup.add(issuer);
//            }
//        }
//
//        return lstIssuerGroup;
//    }

    /**
     * 根据当前用户信息返回一个发卡机构的列表数据 如果该用户为发卡机构组用户,则列表据为 该组下面所有发卡机构的信息，如果该用户为发卡机构用户
     * 则列表数据就是该发卡机构的信息
     *
     * @return
     */
//    public List<IssuerDTO> getIssuerLst() {
//
//        User user = getUser();
//        Integer issuerId = user.getIssuerId();
//
//        Short groupUserFlag = user.getGroupUserFlag();
//        List<IssuerDTO> lstIssuer = new ArrayList<IssuerDTO>();
//        /**
//         * 如果当前用户是发卡机构用户则返回list只包含该发卡机构信息
//         */
//        if (groupUserFlag == 0) {
//            IssuerDTO issuerDTO = new IssuerDTO();
//            issuerDTO.setIssuerId(user.getIssuerId());
//            issuerDTO.setIssuerName(user.getIssuerName());
//            issuerDTO.setIssuerGroupId(user.getIssuerGroupId());
//            lstIssuer.add(issuerDTO);
//        } else {
//            // 如果当前用户是发卡机构组用户,且当前操作数据为发卡机构下面的发卡机构
//            if (issuerId == 0) {
//                lstIssuer = user.getIssuerList();
//            } else {
//                List<IssuerDTO> lstIssuer1 = user.getIssuerList();
//                for (int i = 0; i < lstIssuer1.size(); i++) {
//                    IssuerDTO issuerDTO = lstIssuer1.get(i);
//                    if (issuerDTO.getIssuerId() == issuerId.intValue()) {
//                        lstIssuer.add(issuerDTO);
//                        continue;
//                    }
//                }
//            }
//        }
//        return lstIssuer;
//    }
    
    /**
     * 调用后台程序,如果出现异常情况，将异常信息写入ErrorMessage中
     *
     * @param code
     * @param DTO
     *
     */
    @SuppressWarnings("unchecked")
    public OperationResult sendRptService(String code, Object DTO) {

        OperationResult operationResult = null;
        try {
            OperationCtx operationCtx = new OperationCtx();
            //设置交易代码和交易用户
            operationCtx.setTxncode(code);
            operationCtx.setOprId(getUser().getUserId() + "");
            OperationRequest operationRequest = new OperationRequest();
            /**
             * 如果来的DTO为BaseDTO,则加上用户和默认实体ID
             */
            if (DTO instanceof BaseDTO) {
                BaseDTO baseDTO = (BaseDTO) DTO;
                baseDTO.setLoginUserId(this.getUser().getUserId());
                baseDTO.setDefaultEntityId(this.getUser().getEntityId());
                operationRequest.setTxnvo(baseDTO);
            } else {
                operationRequest.setTxnvo(DTO);
            }
            // 调用后台请求
            operationResult = webServiceClientRptService.process(operationCtx, operationRequest);
            if (operationResult.getTxnstate().equals(Const.RETURN_FAILED)) {
                if (operationResult.getErrMessage() != null) {
                    addActionError(operationResult.getErrMessage());
                }
                if (operationResult.getTxncause() != null) {
                    List causeList = (List) operationResult.getTxncause();
                    if (causeList != null) {
                        for (int i = 0; i < causeList.size(); i++) {
                            addActionError((String) causeList.get(i));
                        }
                    }
                }
                if (!hasActionErrors()) {
                    addActionError("系统异常");
                }
            }

        } catch (Exception e) {
            addActionError("系统异常");
        }
        return operationResult;
    }
    
    /**
	 * 分到元得转换
	 * 
	 * @param money
	 * @return
	 */
	public String moneyTransF2Y(String b) {
		if(b==null){
			return "0.00";
		}
		if (b.length() < 3 && b.length() > 1) {
			b = "0." + b;
		} else if (b.length() < 2) {
			b = "0.0" + b;
		} else {
			b = b.substring(0, b.length() - 2) + "." + b.substring(b.length() - 2);
		}
		return b;
	}
	
	public String moneyTransF2Y(Long l){
		if(l==null){
			return moneyTransF2Y("");
		}
		return moneyTransF2Y(l.toString());
	}
	
	/**
	 * 元到分得转换
	 * @param s
	 * @return
	 */
	public Long  moneyTransY2F(String s){
		String[] ss=s.split("\\.");
		if(ss.length<2){
			return new Long(s+"00");
		}else{
			if(ss[1].length()==1){
				ss[1]+="0";
			}else if(ss[1].length()>2){
				ss[1]=ss[1].substring(0,2);
			}
			
			return new Long(ss[0]+ss[1]);
		}
	}

    public WebServiceClientService getWebServiceClientService() {
        return webServiceClientService;
    }

    public void setWebServiceClientService(WebServiceClientService webServiceClientService) {
        this.webServiceClientService = webServiceClientService;
    }
}
