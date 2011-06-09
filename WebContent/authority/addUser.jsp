<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/ui-lightness/jquery-ui-1.8.13.custom.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery.validate.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#addUserForm").validate({
			rules : {
				userName : {
					required : true
				},
				nickName : {
					required : true
				},
				password : {
					required : true
				}
			},
			messages : {
				userName : {
					required : "Please enter a login name"
				},
				nickName : {
					required : "Please enter a nick name"
				},
				password : {
					required : "Please provide a password"
				}
			}
		});

	});
</script>
</head>
<body>
	<h1 id="frame-header"></h1>
	<h2 class="data-header">添加用户</h2>
	<s:form action="addUser" id="addUserForm" validate="true" method="post">
		<table class="data-table table-header">
			<tr>
				<td>登陆名：</td>
				<td><input name="userName" id="userName" type="text">
				</td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td><input name="nickName" id="nickName" type="text">
				</td>
			</tr>
			<tr>
				<td>角色：</td>
				<td><s:iterator value="#request.roles" id="role">
						<label for="role_id_<s:property value="#role.id"/>"><input
							value="<s:property value="#role.id"/>" name="roleIds"
							id="role_id_<s:property value="#role.id"/>" type="checkbox">
							<s:property value="#role.roleName" /> </label>&nbsp;
							</s:iterator>
				</td>
			</tr>
			<tr>
				<td>所属部门：</td>
				<td><select name="groupId">
						<s:iterator value="#request.groups" id="group">
							<option value="<s:property value="#group.id"/>">
								<s:property value="#group.groupName" />
							</option>
						</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>是否部门主管：</td>
				<td><label for="group_leader_0"> <input value="0"
						name="groupLeader" id="group_leader_0" type="radio">不是 </label>&nbsp;

					<label for="group_leader_1"> <input value="1"
						name="groupLeader" id="group_leader_1" type="radio">是 </label>&nbsp;
				</td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input name="password" id="password" type="password">
				</td>
			</tr>

			<tr>
				<td>邮件地址：</td>
				<td><input name="email" id="email" type="text">
				</td>
			</tr>
			<tr>
				<td>分机号：</td>
				<td><input name="subPhone" id="subPhone" type="text">
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="hidden" name="repeatForm"
					value="<s:property value="#session.repeatForm"/>" /> <s:submit value="保存" id="submit"></s:submit>
				</td>
			</tr>
		</table>
	</s:form>

</body>
</html>