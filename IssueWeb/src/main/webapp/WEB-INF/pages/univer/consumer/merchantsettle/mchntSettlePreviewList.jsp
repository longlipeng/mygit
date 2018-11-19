<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>结算单手动生成</title>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript">
		var isDisplayTableBody = false;
		var isDisplayQueryBody = false;
		var box=null;
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
        var checkboxs = document.getElementsByName('settleObject2List');
        var count = 0;
        for (var i = 0; i < checkboxs.length; i++) {
            if (checkboxs[i].checked) {
                box=checkboxs[i].value;
                count++;
            }
        }
        return count;
    }

    function querySettlePre() {
       var queryForm = Ext.get("queryForm").dom;
       var settleState='0';
        queryForm.action = 'settlePreview.action';
        queryForm.submit();
    }
    function generate() {
        var count = checkedCount();
        var list= box.split(",");
        var contractId;
        if(list != null && list.length>0){
			var id=list[0];
			var name=list[1];
			var date=list[2];
			var startDate=list[4];
			var eligibility=list[5] ;
			contractId= list[6];
        }
        if (count < 1) {
           alert('请至少选择一条记录！');
            return;
        }
       if(eligibility==0){
			alert('此结算单不符合条件！');
			return;
       }      
        var queryForm = Ext.get("queryForm").dom;
        queryForm.action = '${ctx}/merchantSettle/generateSettle.action?settleDTO.settleObject2='+id+'&settleDTO.settleStartDate='+startDate+'&settleDTO.settleDate='+date+'&settleDTO.contractId='+contractId;
        queryForm.submit();
    }

    function settleView() {
       
         var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        } else if (count > 1) {
            alert('只能编辑一条记录！');
            return;
        }
        var queryForm = Ext.get("queryForm").dom;
      // var settleId=document.getElementById("settleObject2List").value;
      //box=encodeURIComponent(encodeURIComponent(box));      
     //  alert(box);
       document.queryForm.action='${ctx}/merchantSettle/changeDateDetail.action';
        document.queryForm.submit();
    }
    function batchSettleDate(){
    	var count = checkedCount();
        if (count < 1) {
            alert('请选择一条记录！');
            return;
        }
        if(count>1){
			alert('只能选择一条记录！');
			return;
        }
        var queryForm = Ext.get("queryForm").dom;
        queryForm.action = '${ctx}/merchantSettle/settleDetail.action?state=6';
        queryForm.submit();
    }
   // function su(){
    	//var min=document.getElementById('minAmt').value;
    	//var max=document.getElementById('maxAmt').value;
    	//var patrn=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)|[0-9]*)$/; 
    	//if(min!=''){
    	//	if(!patrn.exec(min)){
    	//		document.getElementById("minErr").style.display='';
    	//		return;
    	//	}else{
    	//		document.getElementById("minErr").style.display='none';
    	//	}
    	//}
    	//if(max!=''){
    	//	if(!patrn.exec(max)){
    	//		document.getElementById("maxErr").style.display='';
    	//		return;
    	//	}else{
    	//		document.getElementById("maxErr").style.display='none';
    	//	/}
    	///}
    	//querySettlePre();
    //}
    function view(settleId){
    	queryForm.acion='${ctx}/merchantSettle/querySettleDetail';
    	queryForm.submit();
    }

    function choiceSeller() {
    	var sellerDTO=window.showModalDialog('${ctx}/ireport/merchantChoose.action', '_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
         if (sellerDTO != null) {
             
        	 var string=sellerDTO.split(",");
            document.getElementById('merchantName').value = string[1];
            document.getElementById('merchantEntityId').value = string[2];
        }
    }
	</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>结算单手动生成</span>
		</div>
		<div id="ContainBox">
			<s:form id="queryForm" name="queryForm"
				action="merchantSettle/settlePreviewInit" method="post">
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
						<!--<s:hidden name="settleQueryDTO.merchantDTOs"></s:hidden>-->
								<table width="100%" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr height="35">
										<!-- <td width="85" align=right>
											<span>商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject2" ></s:textfield>
											<s:fielderror>
												<s:param>
													mchntSettleQueryDTO.merchantId
												</s:param>
											</s:fielderror>
										</td>
										<td width="90" align=right>
											<span class="no-empty">*</span><span>商户名称：</span>
										</td>
										<td width="300">
											<s:select list="settleQueryDTO.merchantDTOs" listKey="entityId" listValue="merchantName" name="settleQueryDTO.settleObject2" ></s:select>
											<s:fielderror>
												<s:param>
													mchntSettleQueryDTO.settleObject2
												</s:param>
											</s:fielderror>
										</td>-->
										<s:hidden id="merchantEntityId" name="settleQueryDTO.settleObject2"></s:hidden>
	
										<td>
											<table style="text-align: right; width: 100%">
												<tr>
													<td style="width: 50%; text-align: right;">
														<span style="color: red">*</span>商户名称：
													</td>
													<td align="left">
														<s:textfield 
															cssClass="watch" id="merchantName" readonly="true" 
															onclick="choiceSeller()" />
														
													</td>
												</tr>
											</table>
										</td>
									</tr>
								  <!--   <tr>
										<td width="102" align=right>
											<span>符合结算条件：</span>
										</td>
								        <td width="160">
											<input type="checkbox" checked="" id="settleState" name="settleQueryDTO.settleState" <s:property value="settleQueryDTO.settleState==1?'checked':''"/> value="1">
											&nbsp;
										</td>
										<td width="90" align=right>
											<span>老商户号：</span>
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.settleObject1"/>
											<s:fielderror>
												<s:param>
													mchntSettleQueryDTO.legacyMerchantId
												</s:param>
											</s:fielderror>
										</td>
									</tr>
									<tr>
									
										<td width="85" align=right>
											开始时间：
										</td>
										<td width="160">
											
											<input type="text" name="settleQueryDTO.settleStartDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='settleQueryDTO.settleStartDate' format='yyyy-MM-dd'/>"/>
											
										</td>
									
							
							
										<td width="90" align=right>
											结束时间：
										</td>
										<td width="160">
											
											<input type="text" name="settleQueryDTO.settleEndDate" onclick="dateClick(this)" class="Wdate" value="<s:date name='settleQueryDTO.settleEndDate' format='yyyy-MM-dd'/>"/>
										</td>
										</tr>
									<tr>
									
										<td width="85" align=right>
											最小交易金额：
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.minAmt" id='minAmt'/>元
											<label id="minErr" style="display: none;"><font color="red">请输入整数或小数</font></label>
										</td>
									
							
							
										<td width="90" align=right>
											最大交易金额：
										</td>
										<td width="160">
											<s:textfield name="settleQueryDTO.maxAmt" id='maxAmt'/>元
											<label id="maxErr" style="display: none;"><font color="red">请输入整数或小数</font></label>
										</td>
										</tr>-->
				                    <tr>
				                        <td align="right" colspan="4">
				                            <table border="0" cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                        <input type="button" class="bt" style="margin: 5px" onclick="querySettlePre();" value="查 询"/>
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
								<!--<s:hidden name="settleDTO.settleStartDate" id="startDate"></s:hidden>
								<s:hidden name="settleDTO.settleDate" id="settleDate"></s:hidden> -->
							<ec:table items="pageDataDTO.data" var="map" width="100%"
								form="queryForm"
								action="${ctx}/merchantSettle/settlePreviewInit"
								imagePath="${ctx}/images/extremecomponents/*.gif" 
								view="html"
								retrieveRowsCallback="limit"
								autoIncludeParameters="false"
								showPagination="false"
                              	sortable="false">								
								<ec:row>
									<ec:column property="null" alias="settleObject2List" title="选择" width="5%" sortable="false" headerCell="selectAll" viewsAllowed="html">
										<input type="checkbox" name="settleObject2List" id="settleObject2List" value="${map.merchantId},${map.merchantName},${map.settleEndDate},${map.amtDesc},${map.settStartDate},${map.merchantCode},${map.contractId}" />
									</ec:column>
									<ec:column property="merchantCode" title="商户号" width="10%" escapeAutoFormat="true"/>
									<ec:column property="merchantName" title="商户名称" width="15%"/>
									<ec:column property="settStartDate" format="yyyy-MM-dd" title="结算开始日期" width="10%"/>
									<s:hidden name="settStartDate"></s:hidden>
									<ec:column property="settleEndDate" format="yyyy-MM-dd" title="结算截止日期" width="10%"/>	
									 <ec:column property="amtDesc" title="结算本金" width="10%" sortable="false"/>
									 <ec:column property="contractId" title="合同号" width="10%"></ec:column>
								</ec:row>
							</ec:table>
						</div>
								
					</td>
				</tr>
			</table>
			<table width="100%" height="20" border="0">
									<tr>
										<td>
											<div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
													<display:security urlId="501022">
												    <div id="modifyBtn" class="btn"
													    style="width: 90px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													        onclick="generate();">
													    生成结算单
												    </div>
													</display:security>
													<display:security urlId="501021">
												    <div id="modifyBtn" class="btn"
						        					    style="width: 105px; height: 18px; float: right; background: url(${ctx}/images/icon/modify.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													        onclick="settleView()">
													   	设置结算日期
												    </div>
												    </display:security>
											</div>
										</td>
									</tr>
								</table>
		</div>
		</s:form>
	</body>
</html>