package com.huateng.framework.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.TagUtils;
import org.springframework.security.context.SecurityContextHolder;

import com.allinfinance.univer.system.dictinfo.dto.EntityDictInfoDTO;
import com.huateng.framework.exception.BizServiceException;
import com.huateng.framework.security.model.User;
import com.huateng.framework.util.SystemInfo;

/**
 * 
 * @author dawn
 * 
 */
public class EntityDictListTag extends BodyTagSupport {
	
	private Logger logger = Logger.getLogger(EntityDictListTag.class);

	@Override
	public int doStartTag() throws JspException {
		try {

			String strHtml = "";
			/**
			 * 如果是显示名称
			 */
			
			if (DICT_NAME.equals(tagType)) {
				strHtml = getDictName();
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
		/***
		 * 获取实体数据字典
		 */
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<?, ?> map = SystemInfo.getEntityDictInfo().get(user.getEntityId());
		if(map!=null){
			
			List<?> lstDictInfo = (List<?>) map.get(dictType);
			
			if(lstDictInfo!=null){
				for (int i = 0; i < lstDictInfo.size(); i++) {
					EntityDictInfoDTO entityDictInfoDTO = (EntityDictInfoDTO) lstDictInfo.get(i);
					String dictCode = entityDictInfoDTO.getDictCode();
					if (dictValue.equals(dictCode)) {
						dictName = entityDictInfoDTO.getDictShortName();
						break;
					}
				}
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
	
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Map<?, ?> map = SystemInfo.getEntityDictInfo().get(user.getEntityId());
		
		List<?> lstDictInfo = null;
		
		if(map!=null){
			 lstDictInfo = (List<?>) map.get(dictType);
		}
			if (props != null) {
				sbHtml.append("<select name=\"" + displayName + "\"  " + props 
						+ ">\n");
			}else{
				sbHtml.append("<select name=\"" + displayName + "\"  " + ">\n");
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

		StringBuffer result = new StringBuffer();
		ComponentUitl util=new ComponentUitl(TagUtils.getStack(pageContext));
		for (int i = 0; dictList != null && i < dictList.size(); i++) {
			EntityDictInfoDTO entitydictInfoDTO = (EntityDictInfoDTO) dictList.get(i);
			
			if (valueRange != null) {
				String  expr = util.filtersStr((String)valueRange);
		        Object value = TagUtils.getStack(pageContext).findValue(expr);
				if ((Integer)value < Integer.parseInt(entitydictInfoDTO.getDictCode())) continue;
				
			}
			if(valueRangeMax!=null){
				String  expr =util.filtersStr((String)valueRangeMax);
		        Object value = TagUtils.getStack(pageContext).findValue(expr);
				if ((Integer)value > Integer.parseInt(entitydictInfoDTO.getDictCode())) continue;
			}
			
			result.append("<option value=\"").append(entitydictInfoDTO.getDictCode())
					.append("\"");
			// 是否被选中
			if (dictValue != null
					&& dictValue.equals(entitydictInfoDTO.getDictCode())) {
				result.append(" selected ");
			}
			result.append(">");
			result.append(entitydictInfoDTO.getDictShortName()).append("</option>\n");
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
