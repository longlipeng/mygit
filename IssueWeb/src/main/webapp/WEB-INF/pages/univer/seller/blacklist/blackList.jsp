<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>


<title>黑名单批量管理</title>
</head>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
function toImportFile(){
	
	  window.location.href= "${ctx}//blacklistRegion/toImportFile.action";

}
function queryBlackList(){
	
	blacklistForm.submit();
}

</script>
<body >
<%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>黑名单批量管理</span>      
    </div>

    <div id="query" style="border: 1px solid #B9B9B9; margin: 5px 8px 0px">
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
        
    
        <!--  持卡人黑名单 -->
    <c:if test="${balackListFlag eq 'person'}"> 
        <div id="QueryBody">      
        <s:form id="blacklistForm" name="blacklistForm" action="blacklistRegion/inquery.action" method="post">
        <div id="TableBody">      							
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td>
											<table width="100%">
												<tr>
													<td width="45%" align="right">
														姓名:
													</td>
													<td>
														<s:textfield name="blackListPerDTO.name"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
											<tr>
													<td  width="45%" align="right">
														证件号:
													</td>
													<td>
														<s:textfield name="blackListPerDTO.idnumbers"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
												
											</table>
										</td>
										<td>
											<%-- <table width="100%">
												<tr>
													<td width="45%" align="right">
														支付节点:
													</td>
													<td>
														<dl:dictList displayName="sellOrderQueryDTO.paymentTerm"
														dictType="207" dictValue="${sellOrderQueryDTO.paymentTerm}"
														tagType="2" defaultOption="true"/>
													</td>
												</tr>
											</table> --%>
										</td>
									</tr>
									<tr height="35">							
										
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														黑名单类型:
													</td>
													<td>
														<s:select name="blackListDTO.flag" list="#{'1':'持卡人黑名单','2':'地区黑名单'}" value="1" onchange="queryBlackList()" ></s:select>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
																
									<tr >
										<td colspan="3" align="right">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="noneBtn" onclick="queryBlackList();">&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
									</tr>
								</table>
						
						
						
						</div>
        
    
        
       
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
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
        
  
      
        
        
        
        
         
        <div id="TableBody">
						<ec:table items="pageDataDTO.data" var="map" width="100%" form="blacklistForm"
							action="${ctx}/blacklistRegion/inquery.action"
							imagePath="${ctx}/images/extremecomponents/*.gif" 
							view="html"
							retrieveRowsCallback="limit" 
							autoIncludeParameters="false"
							>						
							<ec:row>
								 <ec:column property="NAME" title="姓名" width="6%"  />
                      <ec:column property="SEX" title="性别" width="6%"  />
                      <ec:column property="BIRTHDAY"  title="出生日期"  width="10%" />
                      <ec:column property="CONUTRY" title="国家"  width="10%" />
                      <ec:column property="IDNUMBERS" title="证件信息(||号分隔)"  width="10%" />
                      <ec:column property="LISTOFTYPE" title="名单类别" width="10%" /> 
                      <ec:column property="LISTOFSOURCE" title="名单来源" width="10%" /> 
                      <ec:column property="ENTITYTYPE" title="实体类型"  width="10%" />
                      <ec:column property="ADDRESS" title="地址" width="10%" /> 
                      <ec:column property="DESCRIPTION" title="备注信息" width="10%" /> 
                      </ec:row>
                      
                      
                      
                      
                      
						</ec:table>
						</div>
        
        </s:form>
    </div>
     
     </c:if>
     
         <!--  地区黑名单 -->
    <c:if test="${balackListFlag eq 'area'}"> 
        <div id="QueryBody">      
        <s:form id="blacklistForm" name="blacklistForm" action="blacklistRegion/inquery.action" method="post">
        <div id="TableBody">      							
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td>

											<table width="100%">
												<tr>
													<td width="45%" align="right">
														地区代码:
													</td>
													<td>
														<s:textfield name="blackListAreaDTO.areaCode"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table width="100%">
											<tr>
													<td  width="45%" align="right">
														地区名称:
													</td>
													<td>
														<s:textfield name="blackListAreaDTO.areaName"
															id="orderId" required="true"></s:textfield>
													</td>
												</tr>
												
											</table>
										</td>
										<td>
											<table width="100%">
											<tr>
													<td  width="45%" align="right">
														地区类型:
													</td>
													<td>
														<s:select name="blackListAreaDTO.AreaType" list="#{'0':'国家','1':'省','2':'市','3':'县'}"
														headerKey="" headerValue="全部"></s:select>
													</td>
												</tr>
												
											</table>
											
											
										</td>
									</tr>
									<tr height="35">							
										
										<td>
											<table width="100%">
												<tr>
													<td  width="45%" align="right">
														黑名单类型:
													</td>
													<td>
														<s:select name="blackListDTO.flag" list="#{'1':'持卡人黑名单','2':'地区黑名单'}"
														 value="2" onchange="queryBlackList()"></s:select>
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
																
									<tr >
										<td colspan="3" align="right">
											<img src="${ctx}/images/table/cx.GIF" width="50" height="19"
												class="noneBtn" onclick="queryBlackList();">&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
									</tr>
								</table>
						
						
						
						</div>
        
    
        
       
    </div>
    <div id="list" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
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
        
  
      
        
        
      
         
        <div id="TableBody">
						<ec:table items="pageDataDTO.data" var="map" width="100%" form="blacklistForm"
							action="${ctx}/blacklistRegion/inquery.action"
							imagePath="${ctx}/images/extremecomponents/*.gif" 
							view="html"
							retrieveRowsCallback="limit" 
							autoIncludeParameters="false"
							>						
							<ec:row>
								 <ec:column property="areaCode" title="地区代码" width="10%"  />
                      <ec:column property="areaName" title="地区名称" width="10%"  />
                      <ec:column property="areaType"  title="地区类型"  width="10%" />
                     
                      </ec:row>
 
						</ec:table>
						</div>
        
        </s:form>
    </div>
     
     </c:if>
     
     
     
     
    
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
										<td>
											
											<input type="button" class="btn"
												style="width: 50px; height: 20px; background: url(${ctx}/images/icon/input.gif) no-repeat; margin: 2px 5px; text-align: right"
												onclick="toImportFile()"
												value="导入" />
										</td>
										
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
</body>
</html>