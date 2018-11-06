<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<%@ include file="/commons/ajax.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript" src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="${ctx}/widgets/js/IdCard-Validate.js"></script>
<script type="text/javascript">
	function sub(n){
		document.getElementById("personFlag").value=n;
		document.customerForm.action="${ctx}/customer/add.action";
		document.customerForm.submit();
		//submitForm('customerForm', '${ctx}/customer/add.action', 'add');
	}
	
	function deleteCustomer(){
	var type;
	 var checkboxs = document.getElementsByName('customerIds');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
            	var ids=checkboxs[i].value.split(",");
            	type=ids[2];
            	if(type=="3"){
	        		errorDisplay("散户客户不能注销！");
					return;
        		}
                count++;
            }
        }
        if(count<1){
        	errorDisplay("请选择一条记录操作！");
			return;
        }else{
        		Ext.MessageBox.confirm("确 认", "确定要删除或注销吗?",function(btn) {
				if (btn == "yes") {
				  	customerForm.action="${ctx}/customer/delete.action";
		    		customerForm.submit();
				}
			});

        }	
	}

	function editCustomer(){
		var cusState;
		var checkboxs = document.getElementsByName('customerIds');	
			 var count = 0;
		for (var i = 0; i < checkboxs.length; i++) {
	            if (checkboxs[i].checked) {
	            	var ids=checkboxs[i].value.split(",");
	            	cusState=ids[3];
	                count++;
	            }
	        }
		 if(count<1){
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }else if(count==1){
	        	if(cusState=="4" || cusState=="1"){
	        		errorDisplay("客户信息审核状态无法编辑！");
					return;
	        	}
	        	document.customerForm.action="${ctx}/customer/edit.action";
	        	document.customerForm.submit();
	        }else{
	        	errorDisplay("请选择一条记录操作！");
				return;
	        }
	}
</script>
<title>客户信息管理</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>客户信息管理</span>
    </div>
    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px;">
        <div id="queryTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">查询条件</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="queryTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="queryForm" name="queryForm" action="customer/inquery.action" method="post">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户号：</td>
                                    <td><s:textfield name="customerQueryDTO.entityId"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.entityId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:textfield name="customerQueryDTO.customerName"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.customerName</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    <td>
                     <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件类型：</td>
                                    <td>
                                     <edl:entityDictList displayName="customerQueryDTO.corpCredType" dictValue="${customerQueryDTO.corpCredType}" dictType="140" tagType="2" defaultOption="true" />
                                   
                                    </td>
                                </tr>
                            </table>
                          
                        </td>
                        <td> <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件号码：</td>
                                    <td><s:textfield name="customerQueryDTO.corpCredId"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.corpCredId</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table></td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">缺省支付节点：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerQueryDTO.paymentTerm" dictValue="${customerQueryDTO.paymentTerm}" dictType="207" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户行业：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerQueryDTO.activitySector" dictValue="${customerQueryDTO.activitySector}" dictType="400" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                      	<td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">销售区域：</td>
                                    <td>
                                        <edl:entityDictList displayName="customerQueryDTO.salesRegionId" dictValue="${customerQueryDTO.salesRegionId}" dictType="407" tagType="2" defaultOption="true" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '4':'审核中','1':'已审核','3':'审核未通过',0:'无效',2:'未审核'}" name="customerQueryDTO.cusState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                       <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">创建时间：From</td>
                                    <td>
                                       <input type="text" name="customerQueryDTO.startTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.startTime" />"/>
                                     <s:fielderror>
                                            <s:param>customerQueryDTO.startTime</s:param>
                                        </s:fielderror>
                                    </td>
                                    
                                </tr>
                            </table>
                        </td>
                        
                         <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">To：</td>
                                    <td>
                                       <input type="text" name="customerQueryDTO.stopTime"  	onclick="dateClick(this)"
											 class="Wdate"  value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.stopTime" />"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                    	 <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">注销状态：</td>
                                    <td>
                                        <s:select list="#{'':'--请选择--', '1':'未注销', '0':'已注销'}" name="customerQueryDTO.dataState"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">渠道：</td>
                                    <td><edl:entityDictList displayName="customerQueryDTO.channel"
												dictValue="${customerQueryDTO.channel}" dictType="144"
												tagType="2" defaultOption="true" />
                                        
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td align="right">
                            <!-- <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table> -->
                        </td>
                    </tr>
                    <tr>
                    	 <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">联系电话：</td>
                                    <td>
                                        <s:textfield name="customerQueryDTO.telephoneNumber"/>
                                        <s:fielderror>
                                            <s:param>customerQueryDTO.telephoneNumber</s:param>
                                        </s:fielderror>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                         <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户类型：</td>
                                    <td><s:select list="#{'':'--请选择--', '0':'企业','1':'个人'}" name="customerQueryDTO.customerType"></s:select>
                         
                                    </td>
                                </tr>
                            </table>
                         </td>
                         
                        </tr>
                        
                       <tr>
                      
                        <td align="right"  colspan="3">
                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr  align="right">
                                    <td>
                                        <input type="button" class="bt" style="margin: 5px" onclick="queryForm.submit();" value="查 询"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                   </tr>
                </table>
               
            </s:form>
        </div>
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px;">
        <div id="listTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td class="TableTitleFront">
                        <span class="TableTop">记录列表</span>
                    </td>
                    <td class="TableTitleEnd">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </div>
        <div id="listTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
            <s:form id="customerForm" name="customerForm" action="customer/inquery.action" method="post">
            
            
                <input type="hidden"  name="customerQueryDTO.corpCredType" value="${customerQueryDTO.corpCredType}"/>
                <input type="hidden"  name="customerQueryDTO.entityId" value="${customerQueryDTO.entityId}"/>
                <input type="hidden"  name="customerQueryDTO.customerName" value="${customerQueryDTO.customerName}" />
                <input type="hidden"  name="customerQueryDTO.externalId" value="${customerQueryDTO.externalId}" />
                <input type="hidden" name="customerQueryDTO.paymentTerm" value="${customerQueryDTO.paymentTerm}"/>
                <input type="hidden"  name="customerQueryDTO.activitySector" value="${customerQueryDTO.activitySector}"/>
                <input type="hidden"  name="customerQueryDTO.salesRegionId" value="${customerQueryDTO.salesRegionId}"/>
                <input type="hidden"  name="customerDTO.channel" value="${customerDTO.channel}"/>
                <input type="hidden"  name="customerQueryDTO.dataState" value="${customerQueryDTO.dataState}"/>
                <input type="hidden"  name="customerQueryDTO.cusState" value="${customerQueryDTO.cusState}"/>
                <input type="hidden"  name="customerQueryDTO.customerType" value="${customerQueryDTO.customerType}"/>
                <input type="hidden"  name="personFlag" id="personFlag" value="${personFlag}"/>
                <input type="hidden"  name="customerQueryDTO.channel" id="customerQueryDTO.channel" value="${customerQueryDTO.channel}"/>
               	<input type="hidden" name="customerQueryDTO.startTime" value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.startTime" />"/>
               	<input type="hidden" name="customerQueryDTO.stopTime" value="<s:date format="yyyy-MM-dd" name="customerQueryDTO.stopTime" />"/>
                <ec:table items="pageDataDTO.data" var="map" width="100%"
                    form="customerForm"
                    action="${ctx}/customer/inquery.action"
                    imagePath="${ctx}/images/extremecomponents/*.gif"
                    view="html"
                    retrieveRowsCallback="limit"
                    autoIncludeParameters="false">
                    <ec:row>
                        <ec:column property="null" alias="customerIds" title="选择" width="4%"  headerCell="selectAll">
                        	<s:if test="#attr['map'].dataState == 1">
                            	<input type="checkbox" name="customerIds" value="${map.entityId},${map.fatherEntityId},${map.customerType},${map.cusState}" />
                            </s:if>
                        </ec:column>
                        <ec:column property="entityId" title="客户号" width="9%" >
                            <a href="${ctx}/customer/view.action?customerDTO.entityId=${map.entityId}&customerDTO.fatherEntityId=${map.fatherEntityId}">
                            	${map.entityId}
                            </a>
                        </ec:column>
                        <ec:column property="customerName" title="客户名称" width="14%"  />
                        <ec:column property="customerType" title="客户类型" width="9%" >
                        	<s:property value="#attr['map'].customerType == 0 ? '企业客户' : #attr['map'].customerType == 1? '个人客户':'散户客户'"/>
                        </ec:column>
                        <ec:column property="salesRegionId" title="销售区域" width="9%" >
                            <edl:entityDictList dictValue="${map.salesRegionId}" dictType="407" tagType="1"/>
                        </ec:column>
                        <ec:column property="paymentTerm" title="支付节点" width="9%" >
                            <edl:entityDictList dictValue="${map.paymentTerm}" dictType="207" tagType="1"/>
                        </ec:column>
	                    <ec:column property="activitySector" title="客户行业" width="6%" >
	                        <edl:entityDictList dictValue="${map.activitySector}" dictType="400" tagType="1"/>
	                    </ec:column>
						<ec:column property="awareness" title="客户职业" width="6%" >
	                        <edl:entityDictList dictValue="${map.awareness}" dictType="145" tagType="1"/>
	                    </ec:column>
                        <ec:column property="channel" title="渠道" width="9%" >
                            <edl:entityDictList dictValue="${map.channel}" dictType="144" tagType="1"/>
                        </ec:column>
                        <ec:column property="cusState" title="客户状态" width="5%" cell="cusState" />
                           
                        <ec:column property="dataState" title="注销状态" width="5%" >
                            <s:property value="#attr['map'].dataState == 1 ? '未注销' : '已注销'"/>
                        </ec:column>
                         <ec:column property="riskGrade" title="风险级别" width="4%">
                         	<edl:entityDictList dictValue="${map.riskGrade}" dictType="197" tagType="1"/>
                         </ec:column>
                         <ec:column property="isblacklist" title="黑名单标识" width="4%">
                        	 <edl:entityDictList dictValue="${map.isblacklist}" dictType="196" tagType="1"/>
                         </ec:column>
                        
                         <ec:column property="createTime" title="创建日期" width="10%"  />
                    </ec:row>
                </ec:table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<display:security urlId="402101">
											 <input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="sub(0);"
												value="企业添加" /> 
												<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="sub(1);"
												value="个人添加" />
											</display:security>
										</td>
										<td>
											<display:security urlId="402102">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="editCustomer();"
												value="编辑" />
											</display:security>
										</td>
										<%-- <td>
											<display:security urlId="402103">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right"
												onclick="deleteCustomer();"
												value="注销" />
											</display:security>
										</td> --%>
										<td>
											<display:security urlId="402104">
											<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="submitForm('customerForm', '${ctx}/customer/modifyState.action', 'edit', 'customerIds')"
												value="修改状态" />
											</display:security>
										</td>
									</tr>
								</table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </div>
</body>
</html>