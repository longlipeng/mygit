package com.huateng.framework.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.TagUtils;

import com.allinfinance.univer.system.dictinfo.dto.DictInfoDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.util.SystemInfo;

/**
 * 
 * @author jianji.dai
 * 
 */
public class DictListTag extends BodyTagSupport {

	private static final Logger logger = Logger.getLogger(DictListTag.class);
	
	@Override
	public int doStartTag() throws JspException {
		// HttpServletRequest req =
		// (HttpServletRequest)pageContext.getRequest();
		try {

			String strHtml = "";
			/**
			 * 如果是显示名称
			 */
			
			if (DICT_NAME.equals(tagType)) {
				strHtml = getDictName();
				System.out.println(strHtml.length()+strHtml);
			}
			
			if (DICT_LIST_BOX.equals(tagType)) {
				strHtml = getDictListBox();
			}
			/**
			 * 如果是显示下拉列表
			 */
			// Print this field to our output writer
			JspWriter writer = pageContext.getOut();

			try {
				writer.print(strHtml);
			} catch (IOException e) {
				this.logger.error(e.getMessage());
				throw new JspException(e.toString());
			}

		} catch (Exception e) {
			this.logger.error(e.getMessage());
			throw new JspException(e.toString());
		}
		return BodyTagSupport.EVAL_BODY_TAG;
	}

	@Override
	public int doEndTag() throws JspException {
		return BodyTagSupport.EVAL_BODY_TAG;
	}

	/**
	 * 根据字典类型和字典值，取字典用来显示的名称
	 * 
	 * @return
	 * @throws BizServiceException
	 */
	private String getDictName() throws BizServiceException {
		String dictName = "";

		Map<?, ?> map = SystemInfo.getDictInfo();
		List<?> lstDictInfo = (List<?>) map.get(dictType);
		
		/*add by wanglei 如果数据库的数据字典为空则直接返回dictName  begin*/
		if(lstDictInfo==null){
			System.out.println("lstDictInfo is null");
			return dictName;
		}
		/*add by wanglei 如果数据库的数据字典为空则直接返回dictName  end*/
		for (int i = 0; i < lstDictInfo.size(); i++) {
			DictInfoDTO dictInfoDTO = (DictInfoDTO) lstDictInfo.get(i);
			String dictCode = dictInfoDTO.getDictCode();
			if (dictValue.equals(dictCode)) {
				dictName = dictInfoDTO.getDictShortName();
				break;
			}
		}
		return dictName;

	}

	/**
	 * 根据字典类型和已经选中的值获得用于页面显示 的下拉列表框
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getDictListBox() throws Exception {
		// 取得代码表数据

		StringBuffer sbHtml = new StringBuffer();
	
		Map<?, ?> map = SystemInfo.getDictInfo();
		List<?> lstDictInfo = (List<?>) map.get(dictType);
		if(lstDictInfo==null){
			lstDictInfo = new ArrayList();
		}
		if (props != null) {
			sbHtml.append("<select name=\"" + displayName + "\" " + props
					+ ">\n");
		}else{
			sbHtml.append("<select name=\"" + displayName + "\" " + ">\n");
		}
		if (defaultOption) {
			sbHtml.append("<option value=\"\" >--请选择--</option>\n");
		}
		if (defaultAllOption) {
			sbHtml.append("<option value=\"0\" >--全选--</option>\n");
		}
		sbHtml.append(getOptionsFromList(lstDictInfo));
		sbHtml.append("</select>\n");

		return sbHtml.toString();
	}

	private String getOptionsFromList(List<?> dictList) {

		ComponentUitl util=new ComponentUitl(TagUtils.getStack(pageContext));
		StringBuffer result = new StringBuffer();
		for (int i = 0; dictList != null && i < dictList.size(); i++) {
			DictInfoDTO dictInfoDTO = (DictInfoDTO) dictList.get(i);
			
			if (valueRange != null) {
				String  expr = util.filtersStr((String)valueRange);
		        Object value = TagUtils.getStack(pageContext).findValue(expr);
				if ((Integer)value < Integer.parseInt(dictInfoDTO.getDictCode())) continue;
				
			}
			if(valueRangeMax!=null){
				String  expr =util.filtersStr((String)valueRangeMax);
		        Object value = TagUtils.getStack(pageContext).findValue(expr);
				if ((Integer)value > Integer.parseInt(dictInfoDTO.getDictCode())) continue;
			}
			
			result.append("<option value=\"").append(dictInfoDTO.getDictCode())
					.append("\"");
			// 是否被选中
			if (dictValue != null
					&& dictValue.equals(dictInfoDTO.getDictCode())) {
				result.append(" selected ");
			}
			result.append(">");
			result.append(dictInfoDTO.getDictShortName()).append("</option>\n");
		}

		return result.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616522568301612873L;
	/**
	 * 标签类型为显示名称
	 */
	private final String DICT_NAME = "1";
	/**
	 * 标签类型为显示下拉列表
	 */
	private final String DICT_LIST_BOX = "2";
	/**
	 * 字典值
	 */
	private String dictValue;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 标签类型
	 */
	private String tagType;
	/**
	 * 显示在html中控件的名称
	 */
	private String displayName;
	/**
	 * 显示在html控件中的其他属性
	 */
	private String props;

	/**
	 * 在下拉框列表是否加上请选择选项
	 */
	private boolean defaultOption;
	
	/**
	 * 过滤列表值
	 * 小于这个值
	 */
	private Object valueRange;
	
	/**
	 * 大于这个值
	 */
	private Object valueRangeMax;
	
	
	private boolean defaultAllOption;
	
	

	public boolean isDefaultAllOption() {
		return defaultAllOption;
	}

	public void setDefaultAllOption(boolean defaultAllOption) {
		this.defaultAllOption = defaultAllOption;
	}

	public boolean isDefaultOption() {
		return defaultOption;
	}

	public void setDefaultOption(boolean defaultOption) {
		this.defaultOption = defaultOption;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public Object getValueRange() {
		return valueRange;
	}

	public void setValueRange(Object valueRange) {
		this.valueRange = valueRange;
	}

	public Object getValueRangeMax() {
		return valueRangeMax;
	}

	public void setValueRangeMax(Object valueRangeMax) {
		this.valueRangeMax = valueRangeMax;
	}

	
}
