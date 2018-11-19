<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>修改合同信息</title>
        <%@ include file="/commons/meta.jsp"%>

        <script type="text/javascript">
            var isDisplay = false;
            var userChoice="";
            function displayTable(divShow) {
                if (isDisplay) {
                    display(divShow);
                    isDisplay = false;
                } else {
                    undisplay(divShow);
                    isDisplay = true;
                }
            }
            
            function relative(){
              var accTypeContractIdList = document.getElementsByName("acctypeInf");
              var caclDspDTOList = document.getElementsByName("feeRuleIdList");
              var count =0;
              var count1=0;
              var ruleNo;
              for (var i = 0; i < accTypeContractIdList.length; i++) {
                if (accTypeContractIdList[i].checked ==true) {
                  count=count+1;
                        }
                    }
                
             if(count < 1){
                alert("账户必须选择");
                return;
                  }
              for (var j = 0; j < caclDspDTOList.length; j++) {
                if (caclDspDTOList[j].checked == true) {
                  count1=count1+1;
                  ruleNo=caclDspDTOList[j].value.toString().split(",")[0];
                  var dataState=caclDspDTOList[j].value.toString().split(",")[1];
                  
                  if(dataState =="新建"){
                    alert("规则未启用,不能与服务关联！");
                    return;
                           }  
                        }
                    }
                    
              if(count1 < 1){
                alert("计算规则必须选择");
                return;
                  }
               document.newForm.action='${ctx}/mchntContractManagement/insertAcctypeContract.action?rule='+ruleNo;
               document.newForm.submit();
                }
           
           
            function getParam(name) {
              var str = '';
              var checkbox = document.getElementsByName(name);
              for (var i = 0; i < checkbox.length; i++) {
                if (checkbox[i].checked) {
                  str += name + '=' + checkbox[i].value;
                  str += '&';
                }
              }
              if (str != '') {
                str = str.substring(0, str.length - 1);
              }
              return str;
            }

    function operateDetail(operater,list,url, inqueryUrl) {
        var count = checkedCount();
        if (operater != 'add') {
          var count = checkedCount(list);
          if (operater == 'delete') {
            if (count < 1) {
                alert("请至少选择一项");
                return;
            }
            document.getElementById("inqueryUrl").value=inqueryUrl;
            document.getElementById("url").value=url;
            confirm("你确定要删除吗？",oper);
            return;
          }
          if (operater == 'edit') {
            if (count != 1) {
                alert("请选择一项");
                return;
            }
          }
        }
        var merchantContractId = document.getElementById('consumerContractId').value;
        var merchantName = document.getElementById('merchantName').value; 
      
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
         if(returnValue=='success'){
          		document.newForm.action=inqueryUrl;
        		document.newForm.submit();
          }
    }
    function oper(){
        var url=document.getElementById("url").value;
        var returnValue = window.showModalDialog(url, '', 'center:yes;dialogHeight:400px;dialogWidth:600px;resizable:no');
        document.newForm.action=document.getElementById("inqueryUrl").value;
        document.newForm.submit();
    }
    function checkedCount() {
        var caclDspDTOList = document.getElementsByName("feeRuleIdList");        
           var count = 0;
              for (var j = 0; j < caclDspDTOList.length; j++) {
                if (caclDspDTOList[j].checked == true) {
                  count=count+1;
                        }
                    }   
             
        return count;
    }
    
       function isEnable(){
    
           var caclDspDTOList = document.getElementsByName("feeRuleIdList"); 
           var dataState;
           for (var j = 0; j < caclDspDTOList.length; j++) {
                if (caclDspDTOList[j].checked == true) {                
                  dataState=caclDspDTOList[j].value.toString().split(",")[1];
                   return dataState;
                            } 
                       
                    }
                    
        }
  function enableRule(){
  
          var dataState = isEnable();
              if(dataState =="启用"){
                    alert("规则已启用！");
                    return;
                    }  
           var count=checkedCount();
            if (count < 1) {
              alert('请至少选择一条记录！');
              return;
              }
           confirm("确认要启用吗?",update);
          }
          
         function deleteRule(){
              var dataState = isEnable();
              if(dataState =="启用"){
                    alert("规则已启用不能删除！");
                    return;
                    }          
         	userChoice="delete";
           var count = checkedCount();
              if (count < 1) {
              alert('请至少选择一条记录！');
              return;
          }
           confirm("确认要删除吗?",update);
         }

	  function update(type){
	
		  var ruleForm = Ext.get("ruleForm").dom;
		if(type=="add"){
			ruleForm.action='${ctx}/caclInf/add.action?flag=1';
		}else if(type=="edit"){
		     var dataState = isEnable();
              if(dataState =="启用"){
                    alert("规则已启用不能再编辑！");
                    return;
                    } 
			ruleForm.action='${ctx}/caclInf/view.action?state=0&flag=1';
		}else if(userChoice=='delete'){
			ruleForm.action='${ctx}/caclInf/update.action?state=3&flag=1';
		}else{
			ruleForm.action='${ctx}/caclInf/update.action?state=2&flag=1';
		}		
		ruleForm.submit();
	  }

        
     function addFinish() {
        confirm("你确定要完成合同添加吗？",operation);
    }
    function operation(){
    	document.newForm.action='${ctx}/mchntContractManagement/inquiry.action';
        document.newForm.submit();
    }
    function updateContract(){
    	document.newForm.action='${ctx}/mchntContractManagement/update.action';
    	document.newForm.submit();
    }
 
</script>
    </head>
    <body>
        <%@ include file="/commons/messages.jsp"%>
        <div class="TitleHref">
            <span>修改合同信息</span>
        </div>
        <s:form id="newForm" name="newForm"
            action="/mchntContractManagement/update">
        <div id="ContainBox">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('serviceTable');"
                                    style="cursor: pointer;">
                                   <c:if test="consumerContactDTO.contractType==1">
                                   <span class="TableTop">收单机构合同信息</span>
                                   </c:if>
                                   <span class="TableTop">商户合同信息</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>

                        <div id="serviceTable">
                                <table width="100%" style="table-layout: fixed;">
                                  <tr>
                                      <td colspan="2">
                                        <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                        <s:text name="xxl.merchant.agreeID"/>
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.consumerContractId"
                                                            id="consumerContractId" readonly="true" ></s:textfield>
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
                                                                商户号：
                                                    </td>
                                                    <td>
                                                        <s:textfield  name="consumerContractDTO.merchantCode" readonly="true"/>
                                                        <s:hidden id="merchantId" name="consumerContractDTO.contractBuyer"></s:hidden>
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractBuyer
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                          </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                              商户名称：
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.merchantName" id="merchantName" readonly="true" />
                                                        <s:fielderror>
                                                            <s:param>
                                                               consumerContractDTO.merchantName
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
                                                        <span class="no-empty">*</span>合同有效期开始日:
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.contractStartDateDate" readonly="true">
                                                          <s:param name="value"><s:date name="consumerContractDTO.contractStartDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield>
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractStartDateDate
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table style="text-align: left; width: 100%">
                                                <tr>
                                                    <td style="width: 150px; text-align: right;">
                                                              合同有效期结束日：
                                                    </td>
                                                    <td>
                                                        <s:textfield name="consumerContractDTO.contractEndDateDate" onclick="dateClick(this)" cssClass="Wdate" cssStyle="cursor:pointer">
                                                          <s:param name="value"><s:date name="consumerContractDTO.contractEndDateDate" format="yyyy-MM-dd" /></s:param>
                                                        </s:textfield> 
                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.contractEndDateDate
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
                                                     	结算周期规则：
                                                    </td>
                                                    <td>

                                                        <s:textfield id="ruleNo" name="consumerContractDTO.ruleNo" readonly="true"/>

                                                        <s:fielderror>
                                                            <s:param>
                                                                consumerContractDTO.ruleNo
                                                            </s:param>
                                                        </s:fielderror> 
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 150px; text-align: right;">
														<span class="no-empty">*</span>结算周期名称：
													</td>
													<td>
                                                        <s:textfield id="ruleName" name="consumerContractDTO.ruleName" readonly="true" cssClass="readonly"/>
														<s:fielderror>
															<s:param>consumerContractDTO.ruleName</s:param>
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
														<span class="no-empty">*</span>结算类型：
													</td>
													<td>
													<s:select list="#{'1':'汇总结算'}"
															name="consumerContractDTO.clearTp"
															id="consumerContractDTO.clearTp" 
															 label="规则类型："/>
                                                       <!--  <s:textfield id="ruleNo" name="consumerContractDTO.clearTp" cssClass="watch" onclick="choosePeriodRule()" readonly="true"/>-->
														<s:fielderror>
															<s:param>consumerContractDTO.clearTp</s:param>
														</s:fielderror>  
													</td>
												</tr>
											</table>
										</td>
									</tr>
                                      <s:hidden id="inqueryUrl"></s:hidden>            
                                       <s:hidden id="url"></s:hidden>
                                       <s:hidden id="consumerContractDTO.contractSeller" name="consumerContractDTO.contractSeller"></s:hidden>                    
                                </table>
                      </div>
                    </td>
                </tr>
            </table>
        </div>
        <div>
             <table border="0" cellpadding="0" cellspacing="0" width="100%">
                 <tr>
                     <td style="text-align:right">
                         <input type="button" class="bt" style="margin: 5px;" onclick="javascript:document.newForm.submit();" value="提交合同"/>
                     </td>
                 </tr>
             </table>
        </div>
    
        <div id="ServiceContractBox" style="margin-top:10px">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('serviceContractTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">账户明细</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        </td>
                        </tr>
                        </table>
                       
                         <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <div id="serviceContractTable">

                          <ec:table items="consumerContractDTO.accTypeContractDTOList" var="accTypeContractDTO" width="100%" 
							
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              tableId="serviceContract"
                              showPagination="false"
                              sortable="false">
            	  <ec:row>
                <ec:column property="null" alias="accTypeContractIdList" title="选择" width="5%" sortable="false" viewsAllowed="html">
                    <input type="radio" name="acctypeInf" id="accTypeContractIdList" value="${accTypeContractDTO.acctypeId},${accTypeContractDTO.acctypeContractId}" />

                </ec:column>

                <ec:column property="acctypeContractId" title="账户合同号" width="15%">
                </ec:column>
                <ec:column property="acctypeId" title="账户号" width="15%" />
                <ec:column property="ruleNo" title="费用计算规则编号" width="15%" />
                <ec:column property="ruleName" title="费用计算规则名称" width="15%">
                </ec:column>
              </ec:row>
            </ec:table>
            
<!--            <table border="0" cellpadding="0" cellspacing="0" width="100%">-->
<!--              <tr>-->
<!--                <td align="right">-->
<!--                    <table border="0" cellpadding="0" cellspacing="0">-->
<!--                        <tr>-->
<!--                            <td>-->
<!--                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right" onclick="operateDetail('add','accTypeContractIdList','mchntContractManagement/addAcctypeContract?accTypeContractDTO.consumerContractId=${consumerContractDTO.consumerContractId}'+'&'+'actionName=mchntContractManagement/insertAcctypeContract'+'&'+'accTypeContractDTO.contractSeller=${consumerContractDTO.contractSeller}'+'&'+'accTypeContractDTO.contractBuyer=${consumerContractDTO.contractBuyer}'+'&contractTypeStr=${consumerContractDTO.contractType}', '${ctx}/mchntContractManagement/edit.action')" value="添加"/>-->
<!---->
<!--                            </td>-->
<!--                            <td>-->
<!---->
<!--                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right" onclick="operateDetail('edit','accTypeContractIdList','mchntContractManagement/editAcctypeContract?'+getParam('accTypeContractIdList')+'&'+'actionName=mchntContractManagement/updateAcctypeContract'+'&'+'accTypeContractDTO.contractSeller=${consumerContractDTO.contractSeller}', '${ctx}/mchntContractManagement/edit.action')" value="编辑"/>-->
<!---->
<!--                            </td>-->
<!--                            <td>-->
<!--							-->
<!--                                <input type="button" class="btn" style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/delete.gif) no-repeat; text-align: right" onclick="operateDetail('delete','accTypeContractIdList','mchntContractManagement/deleteAcctypeContract?'+getParam('accTypeContractIdList'),'${ctx}/mchntContractManagement/edit.action')" value="删除"/>-->
<!---->
<!--                            </td>-->
<!--                        </tr>-->
<!--                    </table>-->
<!--                  </td>-->
<!--               </tr>-->
<!--          </table>-->
            </div>
            </td>
                        </tr>
                        </table>
                         </br>
                        </br>
                        <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                        <table width="100%" height="20" border="0" cellpadding="0"
                            cellspacing="0">
                            <tr>
                                <td class="TableTitleFront" onclick="displayTable('serviceContractTable');"
                                    style="cursor: pointer;">
                                    <span class="TableTop">计算规则信息</span>
                                </td>
                                <td class="TableTitleEnd">
                                    &nbsp;
                                </td>
                            </tr>
                        </table>
                        </td>
                        </tr>
                        </table>
                        <s:form id="ruleForm" name="ruleForm"
					action="caclInf/listCaclInf.action" method="post">
            <table width="98%" border="0" cellpadding="0" cellspacing="1"
                bgcolor="B5B8BF" align="center">
                <tr>
                    <td width="100%" height="10" align="left" valign="top"
                        bgcolor="#FFFFFF">
                     <s:hidden name="consumerContractDTO.consumerContractId"></s:hidden>  
             <ec:table items="consumerContractDTO.caclDspDTOs" var="caclDspDTO" width="100%" 
							
                              imagePath="${ctx}/images/extremecomponents/*.gif" view="html"
                              autoIncludeParameters="false"
                              showPagination="false"
                              tableId="caclDsp"
                              sortable="false">
            	  <ec:row onclick="">
            	   <ec:column property="null" alias="feeRuleIdList" title="选择" width="5%" sortable="false" viewsAllowed="html">
					 <input type="radio" name="feeRuleIdList" id="feeRuleIdList" value="${caclDspDTO.discCd},${caclDspDTO.dataStat}" />
                    <c:set var="discCd" value="${caclInfDTO.discCd}" scope="page"/>
                </ec:column>
              <ec:column property="discCd" title="规则号" width="10%">
              <a href="${ctx}/caclInf/view.action?state=1&caclInfQueryDTO.caclInfDTO.discCd=${caclDspDTO.discCd}">${caclDspDTO.discCd}</a>
							
							</ec:column>
							<ec:column property="caclName" title="规则名称" width="20%" />
							<ec:column property="dataStat" title="规则状态" width="10%" />
							<ec:column property="recCrtTsString" title="操作时间" width="10%" />
              </ec:row>
            </ec:table>
  
        
                       
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>

										<td>
										<display:security urlId="60701">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="relative();" value="关联" />
										</display:security>
										</td>
										<td align="right">
										<td>
										<display:security urlId="60701">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/add.gif) no-repeat; text-align: right"
												onclick="update('add');" value="添加" />
										</display:security>
										</td>
										<td>
										<display:security urlId="60702">
											<input type="button" class="btn"
												style="width: 50px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="update('edit');" value="编辑" />
										</display:security>
										</td>
										<td>
										<display:security urlId="60703">
											<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="enableRule();" value="启用" />
										</display:security>
										</td>

										<td>
										<display:security urlId="60704">
											<input type="button" class="btn"
												style="width: 70px; height: 20px; margin: 2px 5px; background: url(${ctx}/images/icon/modify.gif) no-repeat; text-align: right"
												onclick="deleteRule();" value="删除" />
										</display:security>
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
                     
                        </table>
                        </s:form> 
                        	
                        
        </div>
        </s:form>
    </body>
</html>