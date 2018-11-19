<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>结算单手续费修改</title>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
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

    function querySettle() {
        var mchntSettleForm = Ext.get("mchntSettleForm").dom;
        if (mchntSettleForm['ec_eti'] != null) {
            mchntSettleForm['ec_eti'].disabled=true;
        }
        mchntSettleForm.action = '${ctx}/merchantSettle/query.action?state=4';
        mchntSettleForm.submit();
    }
//var settleId="";
    function checkedCount() {
        var checkboxs = document.getElementsByName('settleIdList');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
          // settleId=checkboxs[i].value;           
                count++;
            }
        }
        return count;
    }

    function changeFee() {
         var checkboxs = document.getElementsByName('settleIdList');
        var count = 0;
        var settleId;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
          	 settleId=checkboxs[i].value;           
                count++;
            }
        }
        if (count < 1) {
            errorDisplay('请至少选择一条记录！');
            return;
        }else if(count>1){
        	errorDisplay('请选择一条记录！');
            return;
        } 
        var mchntSettleForm = Ext.get("mchntSettleForm").dom;
        if (mchntSettleForm['ec_eti'] != null) {
            mchntSettleForm['ec_eti'].disabled=true;
        }
        mchntSettleForm.action = '${ctx}/merchantSettle/settleDetail.action?state=4&settleIdList='+settleId;
        mchntSettleForm.submit();
    }
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算单手续费修改</span>
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
							<s:form id="mchntSettleForm" name="mchntSettleForm"
								action="merchantSettle/query" method="post">
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<td width="85" align=right>
											<span>结算单号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleId"></s:textfield>
											<s:fielderror>
												<s:param>
													mchntSettleQueryDTO.mchntSettleId
												</s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											
										</td>
										<td width="160">
											
										</td>
					                 </tr>
									<tr height="35">
										<td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject2"></s:textfield>
											<s:fielderror>
												<s:param>
													mchntSettleQueryDTO.merchantId
												</s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span>商户名称：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject2Name"/>
											<s:fielderror>
												<s:param>
													settleQueryDTO.settleObject2Name
												</s:param>
											</s:fielderror>
										</td>
					                 </tr>
								     
					                  <tr>
									
										<td width="85" align=right>
											开始时间：
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleStartDate" onclick="dateClick(this)" class="Wdate"/>
											<!-- <input type="text" name="settleQueryDTO.settleStartDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='settleQueryDTO.settleStartDate' format='yyyy-MM-dd'/>"/> -->
											
										</td>
									
							
							
										<td width="90" align=right>
											结束时间：
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleEndDate" onclick="dateClick(this)" class="Wdate"/>
											<!-- <input type="text" name="settleQueryDTO.settleEndDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='settleQueryDTO.settleEndDate' format='yyyy-MM-dd'/>"/> -->
										</td>
										</tr>
				                    <tr>
				                        <td align="right" colspan="4">
				                            <table border="0" cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                    
				                                       <input type="button" class="bt" style="margin: 5px" onclick="querySettle();" value="查 询"/>
				                                    </td>
				                                </tr>
				                            </table>
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
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="mchntSettleForm"
								action="${ctx}/merchantSettle/query.action?state=4"
								imagePath="${ctx}/images/extremecomponents/*.gif"
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false">
								<ec:exportXls fileName="mchntSettleList.xls" tooltip="导出Excel" />
								<ec:row onclick="">
									<ec:column property="null" alias="settleIdList" title="选择" width="7%" sortable="false" headerCell="selectAll" viewsAllowed="html">
										<input type="checkbox" name="settleIdList" id="settleIdList" value="${map.settleId}" />
									</ec:column>
						             <ec:column property="settleId" title="结算单号" width="8%" escapeAutoFormat="true"/>
									<ec:column property="merchantCode" title="商户号" width="8%" escapeAutoFormat="true"/>
									<ec:column property="settleObject2Name" title="商户名称" width="12%"/>
									<ec:column property="settleStartDate" title="结算起始日期" width="9%"/>
									<ec:column property="settleEndDate" title="结算截止日期" width="9%"/>
									<ec:column property="settleAmt" title="结算金额" width="9%"/>
									<ec:column property="txnAmt" title="结算本金" width="9%" />
                                    <ec:column property="txnFee" title="手续费" width="9%"/>
                                    <ec:column property="settleState" title="状态" width="9%" cell="dictInfo" alias="214">
                                    </ec:column>                   						
								</ec:row>
							</ec:table>


								<table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												<display:security urlId="501061">
												<div id="modifyBtn" class="btn"
													style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="changeFee()">
													手续费修改
												</div>
												</display:security>
											</div>
										</td>
									</tr>
								</table>
							
						</div>
					</td>
				</tr>
			</table>
		</div>
		<br>	</s:form>     
	</body>
</html>