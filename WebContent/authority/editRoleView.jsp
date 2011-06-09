<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改角色</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
</head>
<body>

	<h1 id="frame-header"></h1>
	<h2 class="data-header">修改角色</h2>
	<s:form action="editRole" id="editRole" validate="true" method="post">
		<table class="data-table table-header">
			<tr>
				<td>角色名：</td>
				<td><input name="roleName" id="roleName"
					type="text" value="<s:property value="#request.role.roleName" />" />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input name="roleId"
					id="roleId" type="hidden"
					value="<s:property value="#request.role.id" />" /> <input class=""
					value="提交" type="submit"></td>
			</tr>
		</table>
	</s:form>

</body>
</html>