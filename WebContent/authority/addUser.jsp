<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
</head>
<body>
	<div id="body">
		<h3>添加用户</h3>
		<div id="main">
			<form
				action="${pageContext.request.contextPath}/default/addUser.action"
				method="post">
				<table>

					<tr>
						<td class="form_head">登陆名：</td>
						<td class="form_field"><input name="userName" id="userName"
							type="text"></td>
					</tr>
					<tr>
						<td class="form_head">姓名：</td>

						<td class="form_field"><input name="nickName" id="nickName"
							type="text"></td>
					</tr>
					<tr>
						<td class="form_head">角色：</td>
						<td class="form_field">
							<s:iterator value="#request.roles" id="role">
								<label for="role_id_<s:property value="#role.id"/>"><input
									value="<s:property value="#role.id"/>" name="roleIds" 
									id="role_id_<s:property value="#role.id"/>" type="checkbox"><s:property value="#role.roleName"/></label>&nbsp;
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td class="form_head">所属部门：</td>
						<td class="form_field"><select name="groupId">
								<s:iterator value="#request.groups" id="group">
									<option value="<s:property value="#group.id"/>"><s:property value="#group.groupName"/></option>
								</s:iterator>
						</select>
						</td>
					</tr>
					<tr>
						<td class="form_head">是否部门主管：</td>
						<td class="form_field">
						
							<label for="group_leader_0"><input
								value="0" name="groupLeader" id="group_leader_0" type="radio">不是</label>&nbsp;
								
							<label for="group_leader_1"><input value="1"
								name="groupLeader" id="group_leader_1" type="radio">是</label>&nbsp;
						</td>
					</tr>
					<tr>
						<td class="form_head">密码：</td>
						<td class="form_field"><input name="password" id="password"
							type="password">
						</td>
					</tr>

					<tr>
						<td class="form_head">邮件地址：</td>
						<td class="form_field"><input name="email" id="email"
							type="text"></td>
					</tr>
					<tr>
						<td class="form_head">分机号：</td>
						<td class="form_field"><input name="subPhone" id="subPhone"
							type="text"></td>
					</tr>
					<tr>
						<td class="form_center" colspan="2"><input value="提交"
							type="submit">
						</td>
					</tr>


				</table>
			</form>
		</div>
	</div>
</body>
</html>