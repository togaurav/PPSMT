<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加角色</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
	<div id="body">
		<h3>添加角色</h3>
		<div id="main">
			<form action="${pageContext.request.contextPath}/default/addRole.action" method="post">
				<table>
						<tr>
							<td class="form_head">角色名：</td>
							<td class="form_field"><input name="roleName"
								id="roleName" type="text"></td>
						</tr>
						<tr>
							<td class="form_center" colspan="2"><input class=""
								value="提交" type="submit">
							</td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>