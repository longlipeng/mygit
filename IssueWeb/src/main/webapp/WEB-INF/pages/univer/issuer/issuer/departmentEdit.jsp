<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/commons/meta.jsp"%>
<script type="text/javascript" src="${ctx}/widgets/js/common2.js"></script>
<base target="_self"></base>
<title>编辑部门信息</title>
</head>
<body>
    <%@ include file="/commons/messages.jsp"%>
    <div class="TitleHref">
        <span>编辑部门</span>
    </div>
    <s:form id="departmentForm" name="departmentForm" action="departmentUpdate.action" method="post">
      <s:hidden name="departmentDTO.entityId"></s:hidden>
        <div id="department" style="border: 1px solid #B9B9B9; margin: 20px 8px 0px">
            <div id="departmentTitle" style="font-weight: bold; height: 19px; background-color: #DFE8F6;">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="TableTitleFront">
                            <span class="TableTop">部门信息</span>
                        </td>
                        <td class="TableTitleEnd">
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </div>
            <div id="departmentTable" style="background-color: #FBFEFF; padding: 5px; border-style: solid none none; border-width: 1px; border-color: #B9B9B9;">
                <table width="100%" style="table-layout: fixed;">
                    <tr>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;">部门号：</td>
                                    <td><s:textfield name="departmentDTO.departmentId" readonly="true" cssClass="readonly"/></td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <table style="text-align: left; width: 100%">
                                <tr>
                                    <td style="width: 90px; text-align: right;"><span class="no-empty">*</span>部门名称：</td>
                                    <td><s:textfield name="departmentDTO.departmentName"/>
                                        <s:fielderror>
                                            <s:param>departmentDTO.departmentName</s:param>
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
                                    <td style="width: 90px; text-align: right;">设为默认部门</td>
                                    <td>
<!--                                        <s:hidden id="defaultFlag" name="departmentDTO.defaultFlag" value="%{departmentDTO.defaultFlag}"></s:hidden>-->
                                       <!-- <s:hidden id="defaultFlag" name="departmentDTO.defaultFlag" ></s:hidden> -->
                                        <s:radio list="#{'0':'否','1':'是'}" name="departmentDTO.defaultFlag"></s:radio>
                                        <!--<s:checkbox name="defaultFlag" value="departmentDTO.defaultFlag"  onchange="document.getElementById('defaultFlag').value = this.checked ? 1 : 0"/>
                                    -->
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <s:hidden name="departmentDTO.customerId"></s:hidden>
            </div>
        </div>
        <div id="buttonDiv" style="margin: 5px 8px 0px;">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr>
                    <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <input type="submit" class="bt" style="margin: 5px" value="提 交" onclick="maskDocAll();"/>
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