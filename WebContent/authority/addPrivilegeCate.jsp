<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加资源分类</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
</head>
<body>

	<h1 id="frame-header"></h1>
	<h2 class="data-header">添加资源分类</h2>
	<s:form action="addPrivilegeCate" id="addPrivilegeCate" validate="true"
		method="post">
		<table class="data-table table-header">
			<tr>
				<td>所属分类：</td>
				<td><select name="typeId">
						<s:iterator value="#request.privilegeTypes" id="privilegeType">
							<option value="${id}">${typeName}</option>
						</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>资源分类名称：</td>
				<td><input name="cateName" type="text">
				</td>
			</tr>
			<tr>
				<td>是否显示在导航中：</td>
				<td><input name="showNav" value="0" class="radio"
					checked="checked" type="radio">否&nbsp; <input
					name="showNav" value="1" class="radio" type="radio">是</td>
			</tr>
			<tr>
				<td colspan="2"><s:submit value="保存" id="submit"></s:submit>
				</td>
			</tr>
		</table>
	</s:form>

</body>
</html>