<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
  <title>车享付商户管理平台</title>
  <style type="text/css">
.login {
	width: 675px;
	height: 286px;
	margin-left: -332px;
	margin-top: -143px;
	top: 50%;
	left: 50%;
	position: absolute;
	background: url("<c:url value = "./resources/pic/loginbj.jpg"/>");
	background-repeat: no-repeat;
}
</style>
  <link href="<c:url value="./resources/css/login.css" />" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<c:url value="./resources/js/jquery-1.7.js" />" ></script>
  <script type="text/javascript">
       if(window!=top){
          top.location.href = location.href;
       }
       $(function(){
            $("#loginName").focus(function(){
               var val = $("#msg").html("");
            });
            
            $("#login").click(function(){
            	var oprid = $("#oprid").val();
            	var password = $("#password").val();
            	$.ajax({
            		  type: 'POST',
            		  url: 'login.asp',
            		  data: {'oprid':oprid,'password':password},
            		  success: function(data){
            			  if (data) {
            				  window.location.href = 'redirect.asp';
            			  }else{
            				  alert("账户或密码有误");
            			  }
            		  },
            		  dataType: 'html'
            		});
            	
            });
            
            $("#cancel").click(function(){
               $("#loginName").val("");
               $("#orgId").val("");
               $("#password").val("");
            });
       })
       function   checkpsd() { 
           if(event.keyCode=='13') {     
              loginForm.submit();
           }
       }
  </script>
</head>
<body>
<form name="loginForm" method="post" action="">
<div class="login">
	<div class="main">
		<div class="login_">
			<div class="inputbox">
	       	  <span class="text">用户名：</span>
	            <span class="inp"><input type="text" id="oprid" name="oprid" style="color:#778288;">	</span>
	        </div>
			<div class="inputbox">
	       	  <span class="text">密码：</span>
	        	<span class="inp"><input type=password id="password" name="password" style="color:#778288;" ></span>
	        </div>
        </div>
        <div class="buttonbox">
        	<img id="login" style="cursor: pointer;" src="<c:url value="./resources/pic/login_1.png" />" />
            <img id="cancel" style="cursor: pointer;" src="<c:url value="./resources/pic/login_2.png" />" />
		</div >
		<div ><span id="msg"><font color="red">${errorMsg}</font></span></div>
    </div>
    <div class="copy">Copyright 上汽版权所有</div>
</div>
		</form>
<script>
function charLength(str) {
    if( str == null || str ==  "" ) return 0;
    var totalCount = 0;
    for (i = 0; i< str.length; i++) {
        if (str.charCodeAt(i) > 127) 
            totalCount += 2;
        else
            totalCount++ ;
    }
    return totalCount;
}
function validate(){
  var frm = document.loginForm;
  if(frm.elements["oprid"].value == null || frm.elements["oprid"].value =="") {
    alert("请输入用户登录名");
    frm.elements["oprid"].focus();
    
    return false;
  }
  if(charLength(frm.elements["oprid"])>64) {
    alert("用户登录名不能超过64个字节，请重新输入！");
    return false;
  }
  if(frm.elements["password"].value == null || frm.elements["password"].value =="") {
    alert("请输入密码");
    frm.elements["password"].focus();
    return false;
  }
  return true;
}
function login() {
	if(loginForm.getForm().isValid()) {
		loginForm.getForm().submit({
			url: 'login.asp',
			success: function(form, action) {
				window.location.href = 'redirect.asp';
			},
			failure: function(form, action) {
				showErrorMsg(action.result.msg,loginForm,function() {
					// 重置授权码
					if(action.result.code != undefined && action.result.code == '00') {
						resetPwdWin.show();
						resetPwdForm.getForm().reset();
						resetPwdForm.get('resetOprId').setValue(loginForm.get('oprid').getValue());
					}
				});
			},
			waitMsg: '登录中......'
		});
	}
}
function doLogin(){
 	if(!validate())
 		return false;
  return true;
}
</script>
</body>
</html>
