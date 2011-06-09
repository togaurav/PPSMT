<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
</head>
<body>

	<h1 id="frame-header"></h1>
	<h2 class="data-header">员工列表</h2>
	<h2 class="data-header">
		<input type="button" value="添加"
			onclick="location.href='${pageContext.request.contextPath}/default/addUserView.action'" />
	</h2>

	<table class="data-table table-header">
		<tr>
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

		<s:iterator value="#request.users" id="user">
			<tr style="background-color: rgb(238, 238, 255);" class="data_rows">
				<td><s:property value="#user.id"></s:property></td>
				<td><s:property value="#user.userName"></s:property></td>
				<td><s:property value="#user.nickName"></s:property></td>
				<td><s:iterator value="#user.roles" id="role">
						<s:property value="#role.roleName"></s:property>
					</s:iterator>
				</td>
				<td><s:property value="#user.group.groupName"></s:property></td>
				<td><s:if test="%{#user.groupLeader == 1}">是</s:if> <s:else>否</s:else>
				</td>
				<td><s:property value="#user.eamil"></s:property></td>
				<td><s:property value="#user.subPhone"></s:property></td>
				<td><s:if test="%{#user.status == 1}">启用</s:if> <s:else>停用</s:else>
				</td>
				<td><input class="" value="修改"
					onclick="location.href='${pageContext.request.contextPath}/default/editUserView.action?userId=<s:property value="#user.id"/>'"
					type="button">&nbsp; <s:if test="%{#user.status == 1}">
						<input class="" value="停用"
							onclick="if(confirm('确定停用？'))location.href='${pageContext.request.contextPath}/default/changeUserStatusToStop.action?userId=<s:property value="#user.id"/>'"
							type="button"> &nbsp;
						</s:if> <s:else>
						<input class="" value="启用"
							onclick="if(confirm('确定启用？'))location.href='${pageContext.request.contextPath}/default/changeUserStatusToUse.action?userId=<s:property value="#user.id"/>'"
							type="button"> &nbsp;
						</s:else> <input class="" value="设置权限"
					onclick="location.href='${pageContext.request.contextPath}/default/assignPrivilegeToUserView.action?userId=<s:property value="#user.id"/>'"
					type="button">&nbsp;</td>
			</tr>
		</s:iterator>

	</table>

	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<s:if test="%{#request.pginfo.pageNo > 1 }">
				<a
					href="${pageContext.request.contextPath}/default/userList.action?currentPage=1"
					target="main-iframe">首页</a>
			</s:if>
			<s:if test="%{#request.pginfo.pageNo >= 3 }">
				<a
					href="${pageContext.request.contextPath}/default/userList.action?currentPage=<s:property value="#request.pginfo.pageNo - 1" />"
					target="main-iframe">前一页</a>
			</s:if>

			第
			<s:property value="#request.pginfo.pageNo" />
			/
			<s:property value="#request.pginfo.sumPage" />
			页

			<s:if
				test="%{#request.pginfo.pageNo <= #request.pginfo.sumPage - 2 }">
				<a
					href="${pageContext.request.contextPath}/default/userList.action?currentPage=<s:property value="#request.pginfo.pageNo + 1" />"
					target="main-iframe">下一页</a>
			</s:if>
			<s:if test="%{#request.pginfo.pageNo <  #request.pginfo.sumPage}">
				<a
					href="${pageContext.request.contextPath}/default/userList.action?currentPage=<s:property value="#request.pginfo.sumPage" />"
					target="main-iframe">末页</a>
			</s:if>
		</tr>
	</table>

</body>
</html>