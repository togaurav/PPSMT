<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
</head>
<body>

<h3>员工列表</h3>
<div id="main">
<div class="toolbar">
<input value="添加" onclick="location.href=''" type="button">
</div>
<table>

<tr class="table_head">
<td>ID</td>
<td>登陆名</td>
<td>姓名</td>
<td>角色</td>
<td>所属部门</td>
<td>部门主管</td>
<td>邮件地址</td>
<td>分机号</td>
<td>是否启用</td>
<td>操作</td>
</tr>



<tr style="background-color: rgb(238, 238, 255);" class="data_rows">
<td>696</td>
<td>刘鹏</td>
<td>刘鹏</td>
<td>超级管理员</td>
<td>开发二部</td>
<td>否</td>
<td></td>
<td></td>
<td>是</td>
<td>
<input class="" value="修改" onclick="location.href='/index.php/user/edit/user_id/696'" type="button">
&nbsp;<input class="" value="停用" onclick="if(confirm('确定停用？'))location.href='/index.php/user/del/user_id/696'" type="button">
&nbsp;<input class="" value="设置权限" onclick="location.href='/index.php/role/set_privilege/user_id/696'" type="button">
&nbsp;<input class="" value="设置业务" onclick="location.href='/index.php/role/set_business/user_id/696'" type="button">
</td>
</tr>


</table>
</div>
</body>
</html>