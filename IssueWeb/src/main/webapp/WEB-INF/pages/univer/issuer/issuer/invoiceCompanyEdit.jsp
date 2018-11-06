<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<script type="text/javascript">
  function subedit(){
		//var s=document.getElementById('invoiceCompanyNameId').value+','+document.getElementById('defaultFlagId').value;
		var temp=document.getElementById('invoiceCompanyNameId').value;
		if(temp==''){
			alert("发票公司名称不能为空！");
			return false;
		   }
 	  document.invoiceCompanyForm.action="invoiceCompanyUpdate.action";
      document.invoiceCompanyForm.submit(); 
		//window.returnValue=s;
		//window.close();  
		}
</script>


<base target="_self"></base>
<title>编辑发票公司</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>编辑发票公司</span>
    </div>
    <s:form id="invoiceCompanyForm" name="invoiceCompanyForm" action="" method="post">
        <s:hidden name="invoiceCompanyDTO.invoiceCompanyId"></s:hidden>
        
        <div id="invoiceCompany" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
            <div id="invoiceCompanyTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">发票公司信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="invoiceCompanyTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;"><span class="no-empty">*</span>发票公司名称：</td>
                                    <td><s:textfield id="invoiceCompanyNameId" name="invoiceCompanyDTO.invoiceCompanyName" />
                                    	<s:fielderror>
                                            <s:param>invoiceCompanyDTO.invoiceCompanyName</s:param>
                                        </s:fielderror>
                                   
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 110px; text-align: right;">设为默认发票公司</td>
                                    <td>
                                       <s:radio list="#{'0':'否','1':'是'}" name="invoiceCompanyDTO.defaultFlag"></s:radio>
                                        <!--<s:checkbox id="defaultFlagId" name="defaultFlag"  fieldValue="0" onchange="document.getElementById('defaultFlagId').value = this.checked ? 1 : 0"/>
                                   -->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
           <table width="100%" height="20" border="0" cellpadding="0"
									cellspacing="0" style="border-top: 1px solid silver;">
									<tr>
										<td align="right">
											<!-- <div id="buttonCRUD"
												style="text-align: right; width: 100%; height: 30px;"
												valign="middle">
												
												<div id="deleteBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/delete.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="window.close();">
													关闭
												</div>
												<div id="btnDiv" style="text-align: right; width: 100%">
												<div id="addBtn" class="btn"
													style="width: 60px; height: 18px; float: right; background: url(${ctx}/images/icon/add133.gif) no-repeat; margin: 5px 5px 0; padding: 2px 15px 0 0"
													onclick="subadd()">
													确定
												</div>
												<div style="clear: both"></div>
											</div>-->
											<table border="0" cellpadding="0" cellspacing="0">
					                            <tr>
					                                <td>
					                                    <input type="button" class="bt" style="margin: 5px" value="提 交" onclick="subedit();"/>
					                                </td>
					                                <td>
					                                    <input type="button" class="bt" style="margin: 5px" onclick="window.close()" value="返 回"/>
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


