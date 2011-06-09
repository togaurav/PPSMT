<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加角色</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
</head>
<body>
	<h1 id="frame-header"></h1>
	<h2 class="data-header">添加角色</h2>
	<s:form action="addRole" id="addRole" validate="true" method="post">
		<table class="data-table table-header">
			<tr>
				<td>角色名：</td>
				<td><input name="roleName" id="roleName" type="text">
				</td>
			</tr>
			<tr>
				<td colspan="2"><s:submit value="保存" id="submit"></s:submit>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>