<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>门店管理</title>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
		var isDisplayTableBody = false;
		var isDisplayQueryBody = false;
		function displayTableBody() {
			if (isDisplayTableBody) {
				display('TableBody');
				isDisplayTableBody = false;
			} else {
				undisplay('TableBody');
				isDisplayTableBody = true;
			}
		}
		function displayQueryBody() {
			if (isDisplayQueryBody) {
				display('QueryBody');
				isDisplayQueryBody = false;
			} else {
				undisplay('QueryBody');
				isDisplayQueryBody = true;
			}
		}

    function checkedCount() {
        var checkboxs = document.getElementsByName('shopIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                count++;
            }
        }
        return count;
    }
    function queryShop() {
        var shopForm = Ext.get("shopForm").dom;
        shopForm.action = '${ctx}/inquiryShop.action';
        shopForm.submit();
    }

    function loadPage() {
        city = ${city};
        changeCity(${shopQueryDTO.businessArea});
    }

    function changeCity(area) {
        var cityId = document.getElementById('city').value;
        var businessAreaData = city[cityId];
        var businessAreaSelect = document.getElementById('businessArea');
        businessAreaSelect.innerHTML = '';
        var opt = document.createElement('option');
        opt.value = '';
        opt.innerHTML = '';
        businessAreaSelect.appendChild(opt);
        if (businessAreaData != null) {
	        for (var i = 0; i < businessAreaData.length; i++) {
	            var businessArea = businessAreaData[i];
	            var opt = document.createElement('option');
	            opt.value = businessArea['dictId'];
	            opt.innerHTML = businessArea['dictShortName'];
	            if (area != null && area == opt.value) {
                    opt.selected = "selected";
                }
	            businessAreaSelect.appendChild(opt);
	        }
        }
        businessAreaSelect.disabled = false;
    }
    var city;
    
    function chooseShop(){
    	var shopId;
        var shopIdList = document.getElementsByName('shopIdList');
         var shopName= document.getElementById('shopName').value;
        for (var i=0; i<shopIdList.length; i++) {
            if (shopIdList[i].checked) {
                shopId = shopIdList[i].value;
                break;
            }
        }
        if (shopId == null) {
            alert("请选择门店！");
        }
        if (shopId != null) {
            window.returnValue=shopId+','+shopName;
			window.close();
        }  
    	
    }
    
    
	</script>
	</head>
	<body onload="loadPage()">
		<%@ include file="/commons/messages.jsp"%>
		
		<div class="TitleHref">
			<span>门店信息管理</span>
		</div>
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayQueryBody();"
									style="cursor: pointer;">
									<span class="TableTop">查询条件</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="QueryBody">
						  <s:form id="shopForm" name="shopForm"
								action="inquiryShop" method="post">
			                <table>
			                	<s:hidden name="shopQueryDTO.entityId"/>
			              
									<tr height="35">
										<td width="85" align=right>
											<span>门店号：</span>
										</td>
										<td width="160">
											<s:textfield name="shopQueryDTO.shopId"></s:textfield>
										</td>
										<td width="90" align=right>
											<span>门店名称：</span>
										</td>
										<td width="160">
											<s:textfield name="shopQueryDTO.shopName"/>
										</td>
									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>城市：</span>
										</td>
										<td width="160">
                                          <edl:entityDictList displayName="shopQueryDTO.shopCity" dictValue="${shopQueryDTO.shopCity}" dictType="405" tagType="2" defaultOption="true" props="id='city' onchange='changeCity()'"/>
											&nbsp;
										</td>
										<td width="90" align=right>
											<span>区域：</span>
										</td>
										<td width="160">
										  <select name="shopQueryDTO.businessArea" id="businessArea" disabled="disabled"></select>
											&nbsp;
										</td>
									</tr>
									<tr height="35">
										<td width="85" align=right>
											<span>门店状态：</span>
										</td>
										<td width="160">
									      <s:select 
                                            list="#{'1':'有效','0':'无效'}" 
                                            name="shopQueryDTO.state" 
                                            emptyOption="false"
                                            label="state" 
                                            headerKey="" 
                                            headerValue="--请选择--"
                                           />
											&nbsp;
										</td>
									   <td align="center">
                                         <input type="button" class="bt" style="margin: 5px" onclick="queryShop()" value="查 询"/>
									   </td>										
									</tr>
			                </table>
						</div>
					</td>
				</tr>
			</table>

		</div>
		<br>
		<br>
		<div style="width: 100%" align=center>
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="98%" height="10" align="center" valign="middle"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTableBody();"
									style="cursor: pointer;">
									<span class="TableTop">记录列表</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						
						<div id="TableBody">	
						<s:hidden name="shopName" id="shopName"/>
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="shopForm"
								action="${ctx}/inquiryShop"
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:row onclick="">
									<ec:column property="null" alias="choose" title="选择" width="10%" sortable="false" headerCell="selectAll">
										<input type="radio" name="shopIdList" value="${map.shopCode},${map.shopId}" onclick="document.getElementById('shopName').value='${map.shopName}' " />												
										<input type="hidden" name="stateList" value="${map.state}" />
                                        <input type="hidden" name="shopIdAll" value="${map.shopId}"/>   
									</ec:column>
									<ec:column property="shopId" title="门店号" width="10%">
									</ec:column>
									<ec:column property="shopName" title="门店名称" width="10%" />
									<ec:column property="shopCity" title="城市"  width="15%">
									  <edl:entityDictList dictValue="${map.shopCity}" dictType="405" tagType="1" defaultOption="false" />
									</ec:column>
									<ec:column property="businessArea" title="区域"  width="15%">
									  <edl:entityDictList dictValue="${map.businessArea}" dictType="408" tagType="1" defaultOption="false" />
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="15%" escapeAutoFormat="true"/>
									<ec:column property="merchantName" title="商户名称" width="10%" />
									<ec:column property="shopState" title="门店状态" width="10%"/>
								</ec:row>
							</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<input type="button" class="bt" style="margin: 5px"
													onclick="chooseShop()" value="确定" />
												<input type="button" class="bt" style="margin: 5px"
													onclick="window.close()" value="返回" />
												<div style="clear: both"></div>
											</div>
										</td>
									</tr>
								</table>
							</s:form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>
	</body>
</html>