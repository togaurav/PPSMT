<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>用户登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<link rel="shortcut icon" href="" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.8/jquery.validate.min.js"></script>
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

})
</script>
</head>
<body>
<s:property value="#request.loginTips"/> 
<form action="${pageContext.request.contextPath}/default/login.action" method="post" id="UserLogIn">
<table border="1" width="500px">
	<tr>
		<td>用户名</td>
		<td><input type="text" name="username" /></td>
	</tr>
	<tr>
		<td>密码</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td colspan="2">
		<input type="submit" class="submit" value="登录" /></td>
	</tr>
</table>

<!-- 
<div style="font-size: 20px; line-height: 50px; font-family: 黑体;">PPS运维监控系统</div>
<table id="login_form">
	<tr>
		<td class="form_head">用户名：</td>
		<td class="form_field"><input name="username" size="10" type="text"></td>
	</tr>
	<tr>
		<td class="form_head">密&nbsp;&nbsp;码：</td>

		<td class="form_field"><input name="password" size="10" type="password"></td>
	</tr>
	<tr>
		<td class="form_center" colspan="2"><input value="进入" name="Submit" type="submit"></td>
	</tr>
</table>
 -->

</form>
</body>
</html>