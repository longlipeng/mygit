<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>编辑结算周期规则</title>
		<%@ include file="/commons/meta.jsp"%>
	    <base target="_self"></base>
	    <script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
	    <script src="${ctx}/widgets/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
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
			function onLoad(){
				var flag=document.getElementById("flag").value;
				if(flag == 1){
					window.returnValue=1;
					window.close();
				}
			}
    
     function change(){
       clear();
    	var feeType= document.getElementById('settlePeriodRuleDTO.ruleType').value;
    	if(feeType==1){
    	    document.getElementById("box1").style.display="";
    	    document.getElementById("box11").style.display="";
		    document.getElementById("box2").style.display="none";
		    document.getElementById("box3").style.display="";
		    document.getElementById("box51").style.display="none";
		    document.getElementById("box60").style.display="none";
    	}else if(feeType==0){
    		document.getElementById("box2").style.display="";
		    document.getElementById("box1").style.display="none";
		    document.getElementById("box11").style.display="none";
		    document.getElementById("box3").style.display="none";
		    document.getElementById("box51").style.display="none";
		    document.getElementById("box60").style.display="none";
    	}else if(feeType==2){
    		document.getElementById("box2").style.display="";
		    document.getElementById("box1").style.display="";
		    document.getElementById("box11").style.display="";
		    document.getElementById("box3").style.display="";
		    document.getElementById("box51").style.display="none";
		    document.getElementById("box60").style.display="none";
    	}else if(feeType==3){
    		document.getElementById("box2").style.display="none";
		    document.getElementById("box1").style.display="none";
		    document.getElementById("box11").style.display="none";
		    document.getElementById("box3").style.display="none";
		    document.getElementById("box51").style.display="";
		    document.getElementById("box60").style.display="none";
    	}
    }
    function change1(){
        clear1();
         var periodType=$("#settlePeriodRuleDTO\\.periodType").val();
        var appointDay=$("#settlePeriodRuleDTO\\.appointDay");   
         appointDay.find("option[value!='']").remove();    
    	if(periodType=="D"){
    	     $("#box4").css("display","none");
    	}else if(periodType=="W"){
    		 $("#box4").css("display","");
    		for(var i=0;i<=6;i++){
    		 appointDay.append("<option value="+i+">"+i+"</option>"); 
    		}
    	}else if(periodType=="M"){
    		 $("#box4").css("display","");
    		for(var i=0;i<=30;i++){
    		 appointDay.append("<option value="+i+">"+i+"</option>"); 
    		}
    	}
    }
    function change2(){
        clear2();
         var period=$("#settlePeriodRuleDTO\\.period").val();
        var periodType=$("#settlePeriodRuleDTO\\.periodType").val();
        var appointPeriod=$("#settlePeriodRuleDTO\\.appointPeriod");
         appointPeriod.find("option[value!='']").remove();
    	if(period!=null || period!="" || period!=0){
    		for(var i=0;i<period;i++){
                appointPeriod.append("<option value="+i+">"+i+"</option>"); 
    		}
    	}
    }
     function change3(){
     	
    	var partAmount=document.getElementById('settlePeriodRuleDTO.partAmount').value;
    	if(partAmount==2){
    		document.getElementById("box60").style.display="";
    		document.getElementById("box601").style.display="";
    		document.getElementById("box602").style.display="none";
    		document.getElementById("box603").style.display="none";
    		document.getElementById("box604").style.display="none";  		
    	}else if(partAmount==3){
    		document.getElementById("box60").style.display="";
    		document.getElementById("box601").style.display="";
    		document.getElementById("box602").style.display="";
    		document.getElementById("box603").style.display="none";
    		document.getElementById("box604").style.display="none";
    	}else if(partAmount==4){
    		document.getElementById("box60").style.display="";
    		document.getElementById("box601").style.display="";
    		document.getElementById("box602").style.display="";
    		document.getElementById("box603").style.display="";
    		document.getElementById("box604").style.display="none";
    	}else if(partAmount==5){
    		document.getElementById("box60").style.display="";
    		document.getElementById("box601").style.display="";
    		document.getElementById("box602").style.display="";
    		document.getElementById("box603").style.display="";
    		document.getElementById("box604").style.display="";	
    	}else{
    		document.getElementById("box60").style.display="none";
    	}
    }
      function clear(){ 	 
      	 document.getElementById('settlePeriodRuleDTO.periodType').value='';
      	 document.getElementById('settlePeriodRuleDTO.period').value='';
      	 document.getElementById('settlePeriodRuleDTO.amountMin').value='';
      	 document.getElementById('settlePeriodRuleDTO.appointPeriod').value='';
      	 document.getElementById('settlePeriodRuleDTO.appointDay').value='';
      	 document.getElementById('settlePeriodRuleDTO.partAmount').value='';
      }
     function clear1(){
      	 document.getElementById('settlePeriodRuleDTO.period').value='';
      	 document.getElementById('settlePeriodRuleDTO.appointPeriod').value='';
      	 document.getElementById('settlePeriodRuleDTO.appointDay').value='';
      }
      function clear2(){
      	 document.getElementById('settlePeriodRuleDTO.appointPeriod').value='';
      	 document.getElementById('settlePeriodRuleDTO.appointDay').value='';
      }
      function clear3(){
      	 document.getElementById('settlePeriodRuleDTO.settleOne').value='';
      	 document.getElementById('settlePeriodRuleDTO.settleTwo').value='';
      	 document.getElementById('settlePeriodRuleDTO.settleThree').value='';
      	 document.getElementById('settlePeriodRuleDTO.settleFour').value='';
      }
    function addRule(){
    var ruleType=document.getElementById('settlePeriodRuleDTO.ruleType').value;
    	var periodType=document.getElementById('settlePeriodRuleDTO.periodType').value;
    	var appointPeriod=document.getElementById('settlePeriodRuleDTO.appointPeriod').value;
    	var appointDay=document.getElementById('settlePeriodRuleDTO.appointDay').value;
    	var ruleName=document.getElementById('settlePeriodRuleDTO.ruleName').value;
    	var partAmount = document.getElementById('settlePeriodRuleDTO.partAmount').value;
    	var settleOne =document.getElementById('settlePeriodRuleDTO.settleOne').value;
    	var settleTwo =document.getElementById('settlePeriodRuleDTO.settleTwo').value;
    	var settleThree =document.getElementById('settlePeriodRuleDTO.settleThree').value;
   		var settleFour =document.getElementById('settlePeriodRuleDTO.settleFour').value;
   		
    	if(ruleName==null || ruleName==""){
    		errorDisplay("周期规则名不能为空！");
    		return;
    	}
    	if(ruleType==1 || ruleType==2){
    		if(periodType!="D"){
    			if(appointPeriod!=null && appointPeriod!=""){
    				if(appointDay==null || appointDay==""){
    					errorDisplay("指定日期不能为空！");
    					return;
    				}
    			}
    			if(appointDay!=null && appointDay!=""){
    				if(appointPeriod==null || appointPeriod==""){
    					errorDisplay("指定周期不能为空！");
    					return;
    				}
    			}
    		}
    	}    	
    	if(ruleType==3){
    		if(partAmount==null || partAmount==""){
    			errorDisplay("分段数不能为空!");
    			return;
    		}else if(partAmount==2){
    			if(settleOne==null ||settleOne==""){
    				errorDisplay("结算点日期不能为空!");
    				return;
    			}
    		}else if(partAmount==3){
    			if(settleOne==null ||settleOne==""||settleTwo==null ||settleTwo==""){
    				errorDisplay("结算点日期不能为空!");
    				return;
    			}
    			if(settleOne>=settleTwo){
    				errorDisplay("前一结算点日期必须小于后一结算点!");
    				return;
    			}
    		}else if(partAmount==4){
    			if(settleOne==null ||settleOne==""|| settleTwo==null ||settleTwo==""||settleThree==null ||settleThree==""){
    				errorDisplay("结算点日期不能为空!");
    				return;
    			}
    			if(settleOne>=settleTwo||settleTwo>=settleThree){
    				errorDisplay("前一结算点日期必须小于后一结算点!");
    				return;
    			}
    		}else if(partAmount==5){
    			if(settleOne==null ||settleOne==""|| settleTwo==null ||settleTwo==""||settleThree==null ||settleThree==""||settleFour==null ||settleFour==""){
    				errorDisplay("结算点日期不能为空!");
    				return;
    			}
    			if(settleOne>=settleTwo||settleTwo>=settleThree||settleThree>=settleFour){
    				errorDisplay("前一结算点日期必须小于后一结算点!");
    				return;
    			}
    		}
    	
    	}
    	maskDocAll();
        settleRuleForm.action='${ctx}/settleRule/update.action';
        settleRuleForm.submit();
    }
    function load(){
      var ruleType=${settlePeriodRuleDTO.ruleType};
      if(ruleType=='0'){
          document.getElementById("box1").style.display="none";
          document.getElementById("box11").style.display="none";
		  document.getElementById("box2").style.display="";
		  document.getElementById("box3").style.display="none";
		  document.getElementById("box51").style.display="none";
		  document.getElementById("box60").style.display="none";
      }else if(ruleType=='3'){
      	  document.getElementById("box1").style.display="none";
          document.getElementById("box11").style.display="none";
		  document.getElementById("box2").style.display="none";
		  document.getElementById("box3").style.display="none";
		  document.getElementById("box51").style.display="";
		  document.getElementById("box60").style.display="none";
		  change3();
      }
      else{
      	if(ruleType=='1'){
          document.getElementById("box1").style.display="";
          document.getElementById("box11").style.display="";
		  document.getElementById("box2").style.display="none";
		  document.getElementById("box3").style.display="";
		  document.getElementById("box51").style.display="none";
		  document.getElementById("box60").style.display="none";
		 }else{
		  document.getElementById("box1").style.display="";
          document.getElementById("box11").style.display="";
		  document.getElementById("box2").style.display="";
		  document.getElementById("box3").style.display="";
		  document.getElementById("box51").style.display="none";
		  document.getElementById("box60").style.display="none";
		 }		 
		  
	   var periodType=$("#settlePeriodRuleDTO\\.periodType").val();
        var appointPeriod=$("#settlePeriodRuleDTO\\.appointPeriod");
	    var appointPeriodValue='${settlePeriodRuleDTO.appointPeriod}';
        var appointDay=$("#settlePeriodRuleDTO\\.appointDay");
        var appointDayValue='${settlePeriodRuleDTO.appointDay}';
        var period=$("#settlePeriodRuleDTO\\.period").val();
         appointPeriod.find("option[value!='']").remove();
    	if(period!=null || period!="" || period!=0){
    		for(var i=0;i<period;i++){          
                appointPeriod.append("<option value="+i+">"+i+"</option>"); 
    		}
    		 if(appointPeriodValue!=null && appointPeriodValue!=''){
    		 		appointPeriod.val(parseInt(appointPeriodValue));
		      } 
    	}
    	 appointDay.find("option[value!='']").remove();
    	if(periodType=="D"){
    		$("#box4").css("display","none");
    	}else if(periodType=="W"){
    		$("#box4").css("display","");
    		for(var i=0;i<=6;i++){               
                appointDay.append("<option value="+i+">"+i+"</option>"); 
    		}
    	}else if(periodType=="M"){
    		$("#box4").css("display","");
    		for(var i=0;i<=30;i++){
                appointDay.append("<option value="+i+">"+i+"</option>"); 
    		}    		
		 }
		 if(appointDayValue!=null && appointDayValue!=''){
		           appointDay.val(parseInt(appointDayValue));
		    } 
      }
    }
		</script>
	</head>
	<body onload="load();">
		<%@ include file="/commons/messages.jsp"%>
		<s:hidden name="flag" id="flag"></s:hidden>
		<div class="TitleHref">
			<span>结算规则信息</span>
		</div>
		<s:form id="settleRuleForm" name="settleRuleForm" action="settlePeriodRule/insert.action" method="post">
          <s:token></s:token>
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
									<span class="TableTop">规则信息</span>
								</td>
								<td class="TableTitleEnd">
									&nbsp;
								</td>
							</tr>
						</table>

						<div id="serviceTable">
								<table width="100%" style="table-layout: fixed;">
									<tr>
									  <td>
									    <table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														规则号：
													</td>
													<td>
														<s:textfield readonly="true" name="settlePeriodRuleDTO.ruleNo"
															id="settlePeriodRuleDTO.ruleNo"></s:textfield>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleNo
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
														<span class="no-empty">*</span>规则名称：
													</td>
													<td>
														<s:textfield id="settlePeriodRuleDTO.ruleName" name="settlePeriodRuleDTO.ruleName"/>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleName
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
														<span class="no-empty">*</span>周期类型：
													</td>
													<td>
														<s:select list="#{'1':'固定周期','0':'非固定周期','2':'混合周期','3':'分段结算'}"
															name="settlePeriodRuleDTO.ruleType"
															id="settlePeriodRuleDTO.ruleType" 
															 label="规则类型："
                                                             onchange="change();"/>
                                                        <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.ruleType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										
									</tr>
									<tr id="box1">
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>周期单位：
													</td>
													<td>
										               <s:select list="#{'D':'按天数','W':'按星期数','M':'按月数'}"
															name="settlePeriodRuleDTO.periodType" 
															id="settlePeriodRuleDTO.periodType"
															emptyOption="false"
															 headerKey=""
															 headerValue="--请选择--"
															 label="周期单位：" onchange="change1();"/>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.periodType
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td>
											
										</td>											
								      </tr>	
								      <tr id="box11">
								      	<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
													 <span class="no-empty">*</span> 周期：
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.period"
															id="settlePeriodRuleDTO.period" onchange="change2();"></s:textfield>
														<s:fielderror>
															<s:param>
																settlePeriodRuleDTO.period
															</s:param>
														</s:fielderror> 
													</td>
												
												</tr>
											</table>
										</td>
										<td></td>
								      </tr>
								      
									<tr id="box3">								        
										<td>
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														指定周期：
													</td>
													<td>
													<s:select list="#{'':'--请选择--'}" name="settlePeriodRuleDTO.appointPeriod"
															id="settlePeriodRuleDTO.appointPeriod"></s:select>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.appointPeriod
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
										<td id="box4">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														指定日：
													</td>
													<td>
										               <s:select list="#{'':'--请选择--'}" name="settlePeriodRuleDTO.appointDay" 
															id="settlePeriodRuleDTO.appointDay"/>
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.appointDay
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr id="box2">								        
										<td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														可结金额：
													</td>
													<td>
										               <s:textfield name="settlePeriodRuleDTO.amountMin" id="settlePeriodRuleDTO.amountMin"/> 元
                                                       <s:fielderror>
															<s:param>
																settlePeriodRuleDTO.amountMin
															</s:param>
														</s:fielderror> 
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr id="box51">	
								     	 <td colspan="2">
											<table style="text-align: left; width: 100%">
												<tr>
													<td style="width: 110px; text-align: right;">
														<span class="no-empty">*</span>分成几段：
													</td>
													<td>
														<s:select 
			                                            list="#{'2':'2','3':'3','4':'4','5':'5'}" 
			                                            id="settlePeriodRuleDTO.partAmount"
			                                            name="settlePeriodRuleDTO.partAmount" 
			                                            emptyOption="false"
			                                            label="state" 
			                                            headerKey="" 
			                                            headerValue="--请选择--"
			                                            onchange="change3();"
			                                           />
														<s:fielderror>
															<s:param>settlePeriodRuleDTO.partAmount</s:param>
														</s:fielderror>
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr id="box60">
										<td id="box601" width="25%" align="left">
											<table>
												<tr>
													<td width="45%" align="right">
														结算点一:
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.settleOne"
															id="settlePeriodRuleDTO.settleOne" required="true"  onfocus="dateClick3(this)"
															cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td id="box602" width="25%" align="left">
											<table>
												<tr>
													<td width="45%" align="right">
														结算点二:
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.settleTwo"
															id="settlePeriodRuleDTO.settleTwo" required="true"  onfocus="dateClick3(this)"
															cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td id="box603" width="25%" align="left">
											<table>
												<tr>
													<td width="45%" align="right">
														结算点三:
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.settleThree"
															id="settlePeriodRuleDTO.settleThree" required="true"  onfocus="dateClick3(this)"
															cssClass="Wdate"></s:textfield>
													</td>
												</tr>
											</table>
										</td>
										<td id="box604" width="25%" align="left">
											<table>
												<tr>
													<td width="45%" align="right">
														结算点四:
													</td>
													<td>
														<s:textfield name="settlePeriodRuleDTO.settleFour"
															id="settlePeriodRuleDTO.settleFour" required="true"  onfocus="dateClick3(this)"
															cssClass="Wdate"></s:textfield>
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
		<div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="addRule();" value="提 交"/>
                                </td>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="window.location = '${ctx}/settleRule/query.action';" value="返 回"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </s:form>
	</body>
</html>