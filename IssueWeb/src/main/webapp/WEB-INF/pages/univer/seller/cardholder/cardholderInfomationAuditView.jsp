<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/widgets/js/jquery.timers.js"></script>
<script type="text/javascript"
	src="${ctx}/widgets/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
function getImageInfo(){
	var type=document.getElementById('cardholderDTO.idType').value;
	var idNo=document.getElementById('cardholderDTO.idNo').value;
    if(type=='1'){
    	  var urlStr='${ctx}/cardholder/getImageInfo.action';
    	  $.ajax({ 

    	        type: "POST", 

    	        data : {id_No:idNo},

    	        dataType: "JSON", 

    	        async: true, 

    	        url: urlStr,

    	        success: function(data){
    	        	var respData=eval('('+data+')');
    	        	var imgfPath=respData[0]["imgfPath"];
    	        	var imgbPath=respData[0]["imgbPath"];
    	        	if(imgfPath!=null&&imgfPath!=''){
	        			  $("#imgfPath").empty();
	        			  $("#imgfPath").html("<img style='height:180px;width:300px' alt='暂无图片' src="+imgfPath+"/>");
	        		  }
    	        	if(imgbPath!=null&&imgbPath!=''){
	        			  $("#imgbPath").empty();
	        			  $("#imgbPath").html("<img style='height:180px;width:300px' alt='暂无图片' src="+imgbPath+"/>");
	        		  }	  
    	        }

    	    });
    }
  
}

</script>
<title>补录信息审核</title>
</head>
<body onload="getImageInfo();">
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>补录信息审核</span>
    </div>
    <s:form id="cardholderForm" name="cardholderForm" action="cardholder/perView.action" method="post">
    
             <s:hidden name="pictureInfoQueryDTO.cardholderId"/>
	            <s:hidden name="pictureInfoQueryDTO.idNo"/>
	            <s:hidden name="pictureInfoQueryDTO.firstName"/>
	            <s:hidden name="pictureInfoQueryDTO.cardholderMobile"/>
	            <s:hidden name="pictureInfoQueryDTO.cardNo"/>
	            <s:hidden name="pictureInfoQueryDTO.idType"/>
	            <s:hidden name="pictureInfoQueryDTO.startTime"/>
	            <s:hidden name="pictureInfoQueryDTO.endTime"/>
	            <s:hidden name="pictureInfoQueryDTO.auditState"/>
	            
        <div id="customerInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="customerInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">所属信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="customerInfoTable" style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户号：</td>
                                    <td><s:label id="entityId" name="cardholderDTO.entityId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">客户名称：</td>
                                    <td><s:label id="customerName" name="cardholderDTO.customerDTO.customerName"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">部门：</td>
                                    <td>
                                        <s:select list="cardholderDTO.customerDTO.departmentList" listKey="departmentId" listValue="departmentName" name="cardholderDTO.departmentId" id="department" headerKey="" headerValue="" disabled="true"></s:select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="realNameInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="realNameInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">实名信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="realNameInfoTable" style="border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人编号：</td>
                                    <td><s:label name="cardholderDTO.cardholderId"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">姓名：</td>
                                    <td><s:label name="cardholderDTO.firstName"/></td>
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
                                    <s:hidden id="cardholderDTO.idType" name="cardholderDTO.idType"></s:hidden>
                                        <edl:entityDictList displayName="cardholderDTO.idType" dictValue="${cardholderDTO.idType}" dictType="140" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">证件号：</td>
                                    <s:hidden id="cardholderDTO.idNo" name="cardholderDTO.idNo"></s:hidden>
                                    <td><s:label name="cardholderDTO.idNo"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">证件失效日期：</td>
									<td><s:label name="cardholderDTO.validity"/></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">
										国籍：
									</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.country"
											dictValue="${cardholderDTO.country}" dictType="450" props="disabled=\"true\""
											tagType="1" defaultOption="false" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td style="width: 90px; text-align: right;">
											城市：
									</td>
									<td><s:label name="cardholderDTO.city"/></td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">
										 学历：
									</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.cardholderEducation" dictValue="${cardholderDTO.cardholderEducation}" dictType="116" tagType="1"/>
									 </td>
								</tr>
							</table>
						</td>
					</tr>						
					<tr>
						<td>
							<table>
								<tr>
									<td style="width: 90px; text-align: right;">
										民族：
									</td>
									<td>
									   <edl:entityDictList displayName="cardholderDTO.cardholderNation" dictValue="${cardholderDTO.cardholderNation}" dictType="451" props="id=\"type\"" tagType="1" />
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 90px; text-align: right;">
										 婚姻状况：
									</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.cardholderMarriage" dictValue="${cardholderDTO.cardholderMarriage}" dictType="117" tagType="1"/>
									 </td>
								</tr>
							</table>
						</td>
					</tr>						
					<tr>
						<td>
							<table>
								<tr>
									<td style="width: 90px; text-align: right;">
											职业：
									</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.profession"
										dictValue="${cardholderDTO.profession}" dictType="145" tagType="1" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
                    <tr>
                        <td >
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">手机号码：</td>
                                    <td>
                                    	<s:hidden id="cardholderDTO.cardholderMobile" name="cardholderDTO.cardholderMobile"></s:hidden>
                                    	<s:label name="cardholderDTO.cardholderMobile"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                       	<td >
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">邮寄地址：</td>
                                    <td><s:label name="cardholderDTO.mailingAddress"/></td>
                                </tr>
                            </table>
                       	</td> 
                   	</tr>
                </table>
            </div>
        </div>
        <div id="personalInfo" style="background-color: #FBFEFF; border: 1px solid #B9B9B9;">
            <div id="personalInfoTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">个人信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="personalInfoTable" style=" border-style: solid none none; border-width: 1px; border-color: #B9B9B9; ">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">性别：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderGender" dictValue="${cardholderDTO.cardholderGender}" dictType="10002" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">出生日期：</td>
                                    <td><s:label name="cardholderDTO.cardholderBirthday" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">邮箱地址：</td>
                                    <td><s:label name="cardholderDTO.cardholderEmail"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">持卡人分类：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderSegment" dictValue="${cardholderDTO.cardholderSegment}" dictType="103" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">称谓：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderSalutation" dictValue="${cardholderDTO.cardholderSalutation}" dictType="104" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">职位：</td>
                                    <td>
                                        <edl:entityDictList displayName="cardholderDTO.cardholderFunction" dictValue="${cardholderDTO.cardholderFunction}" dictType="105" tagType="1"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">失效时间：</td>
                                    <td><s:label name="cardholderDTO.closeDate" /></td>
                                </tr>
                            </table>
                        </td>
                          <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">状态：</td>
                                    <td>
                                       <s:property value="#attr[cardholderDTO.cardholderState]==1?'有效':'无效' "/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">车架号：</td>
                                    <td><s:label name="cardholderDTO.v_Id"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">车牌号：</td>
                                    <td><s:label name="cardholderDTO.plateNumber"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                     <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                   <td style="width: 90px; text-align: right;">驾驶证号：</td>
                                    <td><s:label name="cardholderDTO.driverLicence"/></td>
                                </tr>
                            </table>
                        </td>                        
                    </tr> 
                    <tr>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">黑名单标识：</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.isblacklist"
											dictValue="${cardholderDTO.isblacklist}" dictType="196"
											tagType="2" defaultOption="false" props="disabled=\"true\""/>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table style="text-align: left; width: 100%">
								<tr>
									<td style="width: 100px; text-align: right;">风险等级：</td>
									<td>
										<edl:entityDictList displayName="cardholderDTO.riskGrade"
											dictValue="${cardholderDTO.riskGrade}" dictType="197"
											tagType="2" defaultOption="false" props="disabled=\"true\""/>
									</td>
								</tr>
							</table>
						</td>
					</tr>                    
                    <tr>
                        <td >
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right; vertical-align: top;">备注：</td>
                                    <td><s:textarea name="cardholderDTO.cardholderComment" rows="5" cols="70" readonly="true" cssClass="readonly"></s:textarea></td>
                                </tr>
                            </table>
                        </td>
                    </tr>                   
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right; vertical-align: top;">证件照（正面）：</td>
                                    <td id="imgfPath"> <img style="height:180px;width:300px" alt="暂无图片" src=""/></td>
                                </tr>
                            </table>
                        </td>
                        <td >
                        <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right; vertical-align: top;">证件照（背面）：</td>
                                    <td id="imgbPath">
                                      <%-- <img src="${ctx }/images/img/${cardholderDTO.imgSrc}" alt="暂无照片" height="90px"	width="120px"/>   --%>
                                    <!-- <img src="../../D:\360Downloads\201601\img\362103198206103836.jpg"  alt="abdef" height="90px"	width="150px"/>  -->
                                    <img style="height:180px;width:300px" alt="暂无图片" src=""/>
                                    </td>
                                </tr>                                
                            </table>
                        </td>
                    </tr>                    
                </table>
            </div>            
        </div>
        
        <div id="buttonDiv" style="">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardholder/auditCheck.action?checkPeopleInfoDTO.checkState=1')" value="通过"/>
                                    <input type="button" class="bt" style="margin: 5px" onclick="submitForm('cardholderForm', '${ctx}/cardholder/auditCheck.action?checkPeopleInfoDTO.checkState=2')" value="不通过"/>
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