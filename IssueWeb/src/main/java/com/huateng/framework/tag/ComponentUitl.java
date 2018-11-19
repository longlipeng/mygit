package com.huateng.framework.tag;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class ComponentUitl extends Component {

	public ComponentUitl(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}
	
	public String filtersStr(String str){
		return stripExpressionIfAltSyntax(str);
		 
		
	}

}
