<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
    <head>
        <title>登录界面</title>
        <%@ include file="/commons/meta.jsp"%>
        <script src="${ctx}/widgets/jquery/jquery-1.3.2.min.js"
                type="text/javascript"></script>
        <script type="text/javascript">
        
        
        $(function(){ 
        	$("#PWD").focus(function(){ 
        	$(this).hide(); 
        	$("#j_password").val("").show().css("backgroundColor","#fff").focus(); 
        	}); 
        	$("#j_password").blur(function(){ 
        	$(this).show().css("backgroundColor","#fff"); 
        	$("#PWD").hide(); 
        	}); 
        	
        	}); 
        function sendCode() {
    			
    			var name= document.getElementById("j_username").value;
    			if(name==null||name==""){
            		alert("请填写用户名");
            		return false;
            	}
    			$.ajax( {
    				aysnc : true,
    				type : 'POST',
    				dataType : 'text',
    				url : '${ctx}/verificationCodeForPhone.png',
    				data : {'username':name},
    				success : function(data) {
    					alert(data);
    				}
    			});
        };

            function doSign(){
            	var verificationCode = document.getElementById("verificationCode").value;
            	if(verificationCode==null||verificationCode==""){
            		alert("请填写短信验证码");
            		return false;
            	}
                maskDocAllWithMessage("正在登录，请稍侯......");
                loginForm.submit();
                return true;
            }

            //响应回车事件
            function bindEnter(obj) {
                if(obj.keyCode == 13){
                    doSign();
                    obj.returnValue = false;
                }
            }
            
            if (top != window)  
                top.location.href = window.location.href;
        </script>
    </head>
    <object id="reader" classid="clsid:4806AD0E-57B8-471F-B210-F3FABB9B3ABB"> </object>
    <body onkeyDown="bindEnter(event)" style="height: 100%; width: 100%">
        <table width="100%" height="100%" border="0" cellpadding="0"
            cellspacing="0" id="bodyTable">
            <tr>
                <td valign="bottom" background="images/login/bjt.GIF">
                    <table width="100%" height="441" border="0" cellpadding="0"
                        cellspacing="0">
                        <tr>
                            <td align="center" valign="top" background="images/login/bj.GIF">
                                <table width="492" height="399" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td align="center" valign="top"
                                            background="images/login/c01.GIF">
                                            <table width="400" height="90" border="0" cellpadding="0"
                                                cellspacing="0">
                                                <tr>
                                                    <td>
                                                        &nbsp;
                                                    </td>
                                                </tr>
                                            </table>
                                            <s:form method="post" id="loginForm"
                                                action="j_spring_security_check" autocomplete="off">
                                                <table width="450" height="132" border="0" cellpadding="0"
                                                    cellspacing="0">
                                                    <tr>
                                                        <td width="204" height="132">
                                                            &nbsp;
                                                        <br></td>
                                                        <td width="227">
                                                            <table width="227" height="74" border="0" cellpadding="0"
                                                                cellspacing="0">
                                                                <c:if test="${param.error == true && not empty SPRING_SECURITY_LAST_EXCEPTION}">
                                                                    <tr>
                                                                        <td colspan='3'>
                                                                            &nbsp;&nbsp;
                                                                            <font color="red">
                                                                                登录失败:<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"></c:out>
                                                                            </font>
                                                                            <br>
                                                                            <br>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                                <tr>
                                                                    <td width="70" height="20" align="center">
                                                                        <span class="style1">用户名：</span>
                                                                    <br></td>
                                                                    <td width="157" colspan=2>
                                                                        <input name="j_username" id="j_username" type="text" autocomplete="off"
                                                                            size="16" value="${SPRING_SECURITY_LAST_USERNAME}" onfocus="this.select();">
                                                                    <br></td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="70" height="7"><br></td>
                                                                    <td width="157" height="7" colspan=2><br></td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="70" height="20" align="center">
                                                                        <span class="style1">口 令：</span>
                                                                    <br></td>
                                                                    <td width="157" colspan=2>
                                                                    	<input type="text" id="PWD"  size="16"  onfocus="this.select();" />
                                                                        <input type="password" name="j_password"
                                                                            id="j_password" size="16"  style="display:none" autocomplete="off"/>
                                                                      <input type="password" style="display:none" />
                                                                    <br></td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="70" height="7"><br></td>
                                                                    <td width="157" height="7" colspan=2><br></td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="70" height="20" align="center">
                                                                        <span class="style1">验证码：</span>
                                                                    </td>
                                                                    <td >
                                                                        <input name="checkCode" id="checkCode" type="text"
                                                                            size="8" onfocus="this.select();">
                                                                    </td>
                                                                    <td>
                                                                        <table border=0>
                                                                            <tr>
                                                                                <td>
                                                                                    <img src="${ctx}/widgets/ext/resources/images/default/grid/loading.gif" id="codeImg" title="单击刷新验证码" style="cursor: pointer;"
                                                                                        onclick="this.src='${ctx}/captcha.png?'+Math.random();"/>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                 <tr>
                                                                    <td width="70" height="20" align="center">
                                                                        <span class="style1">短信验证：</span>
                                                                    <br></td>
                                                                    <td >
                                                                        <input type="text" name="verificationCode"
                                                                            id="verificationCode" size="8" onfocus="this.select();">
                                                                    <br></td>
                                                                    <td >
                                                                    <img src="${ctx}/images/login/huoquduanxin.jpg" id="codeImg" title="单击获取短信验证码" style="cursor: pointer;"
                                                                                        onclick="sendCode();"/>
                                                                   </td>
                                                                </tr>
                                                            </table>
                                                           <!--  <table width="200" height="10" border="0" cellpadding="0"
                                                                cellspacing="0">
                                                                <tr>
                                                                    <td height="10"></td>
                                                                </tr>
                                                            </table> -->
                                                            <table width="200" height="22" border="0" cellpadding="0"
                                                                cellspacing="0">
                                                                <tr>
                                                                    <td width="68">
                                                                        &nbsp;
                                                                    </td>
                                                                    <td width="49" align="right" valign="middle">
                                                                        <img src="images/login/dl.GIF" width="40" height="22"
                                                                            onclick="doSign();">
                                                                    </td>
                                                                    <td width="83" align="left" valign="middle">
                                                                        <img src="images/login/cz.GIF" width="41" height="22"
                                                                            onClick="loginForm.reset();">
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                        <td width="19">
                                                            &nbsp;
                                                        </td>
                                                    </tr>
                                                </table>
                                            </s:form>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
    <script type="text/javascript">
        window.onload = function() {
            userAgent=window.navigator.userAgent.toLowerCase();
            if(userAgent.indexOf("firefox")>=1){
                if(window.parent.length>1) {
                    window.top.location.href=location.href;
                    return;
                }
            } else {
                var name=navigator.appName;
                if(name=="Microsoft Internet Explorer"){
                    if(window.parent.length>0) {
                        window.top.location.href=location.href;
                        return;
                    }
                }
            }
            document.getElementById('j_username').focus();


            document.getElementById('codeImg').src='${ctx}/captcha.png?'+Math.random();
        }
    </script>
</html>
