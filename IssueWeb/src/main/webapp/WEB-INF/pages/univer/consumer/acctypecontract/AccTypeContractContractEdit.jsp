<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/ajax.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>修改服务合同</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"></base>
		<script type="text/javascript">
			var isDisplay = false;
			function displayTable(divShow) {
				if (isDisplay) {
					display(divShow);
					isDisplay = false;
				} else {
					undisplay(divShow);
					isDisplay = true;
				}
			}         
function chooseRule(){
	var ruleNo;
        var ruleNoList = document.getElementsByName('ruleNoList');
        for (var i=0; i<ruleNoList.length; i++) {
            if (ruleNoList[i].checked) {
                ruleNo = ruleNoList[i].value;
                break;
            }
        }
        if (ruleNo == null) {
            alert("请选择计算规则！");
        }
        if (ruleNo != null) {
            document.getElementById("ruleNo").value=ruleNo;
        }  
}


function setForm() {
			var newForm = document.getElementById("newForm");			
//			alert('${actionName}');
            newForm.action='${actionName}';
            newForm.submit();
}

window.onload=function(){
        var ruleNo=document.getElementById("ruleNo").value;
        var radio=document.getElementsByName('ruleNoList');
        for(var i=0;i<radio.length;i++){
            if(ruleNo==radio[i].value){
                radio[i].checked=true;
            }
        }
       
    }


		</script>
	</head>
	<body>
		<%@ include file="/commons/messages.jsp"%>
		<div class="TitleHref">
			<span>修改服务合同</span>
		</div>
    <s:form id="newForm" name="newForm"
        action="">
		<div id="ContainBox">
			<table width="98%" border="0" cellpadding="0" cellspacing="1"
				bgcolor="B5B8BF">
				<tr>
					<td width="100%" height="10" align="left" valign="top"
						bgcolor="#FFFFFF">
						<table width="100%" height="20" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="TableTitleFront" onclick="displayTable('acctypeContractTable');"
									style="cursor: pointer;">
									<span class="TableTop">服务合同信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="acctypeContractTable">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														合同号：
													</td>
													<td>
														<s:textfield name="accTypeContractDTO.consumerContractId"
															id="consumerContractId" readonly="true"></s:textfield>
														<s:fielderror>
															<s:param>
																accTypeContractDTO.consumerContractId
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
										  </table>
										</td>
										<td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>服务号：
													</td>
													<td>
														<s:textfield 
														name="accTypeContractDTO.acctypeId"
														 readonly="true">
														</s:textfield>
												
														<s:fielderror>
															<s:param>
																accTypeContractDTO.acctypeId
															</s:param>
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
													<td style="width: 110px; text-align: right;">
														账户合同号：
													</td>
													<td>
														<s:textfield id="seq" name="accTypeContractDTO.acctypeContractId" readonly="true"/>
														<s:fielderror>
															<s:param>
                                                              accTypeContractDTO.acctypeContractId
															</s:param>
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
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>费用计算规则：
													</td>
													<td>
                                                        <s:textfield id="ruleNo" name="accTypeContractDTO.ruleNo" readonly="true"/>
														<s:fielderror>
															<s:param>
                                                              accTypeContractDTO.ruleNo
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									
									
								</table>
						        <s:hidden name="accTypeContractDTO.contractBuyer"/>
					  </div>
					</td>
				</tr>
			</table>
		</div>
      </s:form>
        <div id="btnDiv" style="text-align: right; width: 100%">
            <button class='bt' style="float: right; margin: 5px 10px"
                onclick="window.close()">
                   返回
            </button>
            <button class='bt' style="float: right; margin: 5px"
                onclick="setForm();">
                   提交
            </button>
            <div style="clear: both"></div>
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
								<td class="TableTitleFront" onclick="displayTable('feeRuleTable');"
									style="cursor: pointer;">
									<span class="TableTop">费用计算规则</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>
						<div id="feeRuleTable">
					    <s:form id="feeRuleForm" name="feeRuleForm" action="" method="post">
                          <ec:table items="accTypeContractDTO.caclDspDTOList" var="feeRuleDTO" width="100%"
                              action=""
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="contact"
                              showPagination="false"
                              sortable="false">
              <ec:row>
                <ec:column property="null" alias="ruleNoList" title="选择" width="10%" sortable="false" headerCell="selectAll" viewsAllowed="html">
                    <input type="radio" name="ruleNoList" value="${feeRuleDTO.discCd}" />
                    <c:set var="discCd" value="${caclInfDTO.discCd}" scope="page"/>
                </ec:column>
                <ec:column property="discCd" title="规则编号" width="20%" >
                <a href="javascript:var discCd = window.showModalDialog('${ctx}/caclInf/viewModalDialog.action?state=1&caclInfQueryDTO.caclInfDTO.discCd=${feeRuleDTO.discCd}','_blank', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');">${feeRuleDTO.discCd}</a>
                </ec:column>
                <ec:column property="caclName" title="规则名称" width="40%" />
                <ec:column property="caclDsp" title="规则描述" width="40%" />               
              </ec:row>
            </ec:table>
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td align="right">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>
                                <input type="button" class="bt" style="margin: 5px" onclick="chooseRule()" value="确定"/>
                            </td>
                            
                        </tr>
                    </table>
                  </td>
               </tr>
          </table>
      </s:form>
      </div>
					</td>
				</tr>
			</table>
		</div>
 
	</body>
</html>