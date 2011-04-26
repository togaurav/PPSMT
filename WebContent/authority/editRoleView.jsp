<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改角色</title>
</head>
<body>

	<div id="body">
		<h3>修改角色</h3>
		<div id="main">
			<form action="${pageContext.request.contextPath}/default/editRole.action" method="post">
				<input name="roleId" id="roleId" type="hidden" value="<s:property value="#request.role.id" />" />
				<table>
					<tr>
						<td class="form_head">角色名：</td>
						<td class="form_field">
							<input name="roleName" id="roleName" type="text" value="<s:property value="#request.role.roleName" />" />
						</td>
					</tr>
					<tr>
						<td class="form_center" colspan="2"><input class=""
							value="提交" type="submit"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

</body>
</html>