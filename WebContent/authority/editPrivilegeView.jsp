<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改资源</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
</head>
<body>


	<h1 id="frame-header"></h1>
	<h2 class="data-header">修改资源</h2>
	<s:form action="editPrivilege" id="editPrivilege" validate="true"
		method="post">
		<table class="data-table table-header">
			<tr>
				<td>所属分类：</td>
				<td><select name="cateId">
						<s:iterator value="#request.catelists" id="cate">
							<option value="<s:property value="#request.cate.id" />"
								<s:if test="%{#request.privilege.privilegeCate.id == #cate.id}">
									selected="selected"
									</s:if>>
								<s:property value="#request.cate.cateName" />
							</option>
						</s:iterator>
				</select>
				</td>
			</tr>
			<tr>
				<td>资源名称：</td>
				<td><input name="name"
					value="<s:property value="#request.privilege.name" />" type="text">
				</td>
			</tr>

			<tr>
				<td>资源路径：</td>
				<td><input name="action"
					value="<s:property value="#request.privilege.action" />"
					type="text">
				</td>
			</tr>
			<tr>
				<td>是否显示在导航中：</td>
				<td><input name="showNav" value="0" class="radio"
					<s:if test="%{#request.privilege.showNav == 0}">
								checked="checked" 
								</s:if>
					type="radio">否&nbsp;<input name="showNav" value="1"
					class="radio"
					<s:if test="%{#request.privilege.showNav == 1}">
								checked="checked" 
								</s:if>
					type="radio">是</td>
			</tr>
			<tr>
				<td colspan="2"><input name="privilegeId"
					value="<s:property value="#request.privilege.id" />" type="hidden" />
					<input class="" value="提交" type="submit">
				</td>
			</tr>
		</table>
	</s:form>



</body>
</html>