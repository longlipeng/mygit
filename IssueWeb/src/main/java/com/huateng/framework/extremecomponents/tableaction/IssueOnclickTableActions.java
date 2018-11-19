package com.huateng.framework.extremecomponents.tableaction;

import org.apache.commons.lang.StringUtils;
import org.extremecomponents.table.core.TableConstants;
import org.extremecomponents.table.core.TableModel;
import org.extremecomponents.table.view.html.BuilderUtils;
import org.extremecomponents.table.view.html.TableActions;

/**
 * 扩展TableActions，使原来页面中下拉框由显示行数变为显示页敄1�7
 * 这里完成了下拉框中js语句onchenge()的实玄1�7
 * @author liming.feng
 *
 */
public class IssueOnclickTableActions extends TableActions {
	
	private TableModel model;

	public IssueOnclickTableActions(TableModel model) {
		super(model);
		this.model = model;
	}
	
	/**
	 * 构建页面控件“翻页下拉框”的js中onchenge()语句
	 * @return String
	 */
    public String getPagesDisplayedAction(int totalPages) {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getRowsDisplayedFormParameter(TableConstants.PAGE,totalPages));
        action.append(getOnInvokeOrSubmitAction());
        
        return action.toString();
    }
    public String getRowsDisplayedFormParameter(String name,int totalPages) {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(this.model);
        String inputValue = "document.getElementsByName('" + this.model.getTableHandler().prefixWithTableId() + "aa')[0].value";
        result.append("if(!/^[1-9]\\d*$/.test("+inputValue+"))");
        result.append("{errorDisplay('请输入正整数');"+inputValue+"='';return false;};");
        
        result.append("if("+inputValue+" > "+totalPages+")");
        result.append("{"+inputValue+" = "+totalPages +" };");
        result.append("document.forms.").append(form).append(".");
        result.append(this.model.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value=").append(inputValue).append(";");

        return result.toString();
      }
    
    /*
     * Added 16 AUG 2007 - Todd Fredrich - To duplicate previous functionality of getOnInvokeAction().
     */
    public String getOnInvokeOrSubmitAction() {
        String onInvokeAction = getOnInvokeAction();
        if (StringUtils.isNotBlank(onInvokeAction)) {
            return onInvokeAction;
        }
        
        return getSubmitAction();
    }
    
    public String getSubmitAction() {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(model);
        String action = model.getTableHandler().getTable().getAction();
        result.append("document.forms.").append(form).append(".setAttribute('action','").append(action).append("');");
        String method = model.getTableHandler().getTable().getMethod();
        result.append("document.forms.").append(form).append(".setAttribute('method','").append(method).append("');");
//        result.append("document.forms.").append(form).append(".setAttribute('onSubmit','return false;');");
        result.append("document.forms.").append(form).append(".submit()");
        
        return result.toString();
    }
    
}
