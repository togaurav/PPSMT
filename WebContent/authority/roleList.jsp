<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
</head>
<body>

<div id="body">
<h3>角色列表</h3>
<div id="main">
<div class="toolbar">
<input value="添加" onclick="location.href=''" type="button">
</div>
<table>
<tr class="table_head">
<td>角色ID</td>
<td>角色名</td>
<td>操作</td>
</tr>
<s:iterator value="#request.roles" id="role">
<tr style="background-color: rgb(238, 238, 255);" class="data_rows">
<td><s:property value="#role.id" /></td>
<td><s:property value="#role.roleName" /></td>
<td>
<input value="修改" onclick="location.href=''" type="button">&nbsp;
<input class="del_btn" value="删除" onclick="if(confirm('确认删除？'))location.href=''" type="button">&nbsp;
<input value="设置角色权限" 
	onclick="location.href='${pageContext.request.contextPath}/default/configRolePrivilege.action?roleId=<s:property value="#role.id" />'" type="button">
</td>
</tr>
</s:iterator>
</table>
</div>
</div>
</body>
</html>