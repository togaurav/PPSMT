<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="shortcut icon" href="" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery.validate.js"></script>
<script type="text/javascript">
$(document).ready(function(){

	$("#UserLogIn").validate({
		rules: {
			username: {
				required: true
			},
			password: {
				required: true
			}
		},
		messages: {
			username: {
				required: "Please enter a username"
			},
			password: {
				required: "Please provide a password"
			}
		}
	});

});
</script>
</head>
<body>
<s:property value="#request.loginTips"/> 
<form action="${pageContext.request.contextPath}/default/login.action" method="post" id="UserLogIn">

<div style="font-size: 20px; line-height: 50px; font-family: 黑体;">PPS运维监控系统</div>
<table id="login_form">
	<tr>
		<td class="form_head">用户名：</td>
		<td class="form_field"><input name="username" size="10" type="text" value="刘鹏"></td>
	</tr>
	<tr>
		<td class="form_head">密&nbsp;&nbsp;码：</td>

		<td class="form_field"><input name="password" size="10" type="password" value="123456"></td>
	</tr>
	<tr>
		<td class="form_center" colspan="2"><input value="进入" name="Submit" type="submit"></td>
	</tr>
</table>


</form>
</body>
</html>