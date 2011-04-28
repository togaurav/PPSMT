<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改资源</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>


	<div id="body">
		<h3>修改资源</h3>
		<div id="main">
			<form action="${pageContext.request.contextPath}/default/editPrivilege.action" method="post">
				<input name="privilegeId" value="<s:property value="#request.privilege.id" />" type="hidden" />
				<table>
					<tbody>
						<tr>
							<td class="form_head">所属分类：</td>
							<td class="form_field"><select name="cateId">
<s:iterator value="#request.catelists" id="cate">
									<option value="<s:property value="#request.cate.id" />" 
									<s:if test="%{#request.privilege.privilegeCate.id == #cate.id}">
									selected="selected"
									</s:if>
									>
									<s:property value="#request.cate.cateName" /></option>
</s:iterator>
							</select>
							</td>
						</tr>
						<tr>
							<td class="form_head">资源名称：</td>
							<td class="form_field"><input name="name" value="<s:property value="#request.privilege.name" />" type="text">
							</td>
						</tr>

						<tr>
							<td class="form_head">资源路径：</td>
							<td class="form_field"><input name="action" value="<s:property value="#request.privilege.action" />"
								type="text">
							</td>
						</tr>
						<tr>
							<td class="form_head">是否显示在导航中：</td>
							<td class="form_field"><input name="showNav" value="0"
								class="radio" 
								<s:if test="%{#request.privilege.showNav == 0}">
								checked="checked" 
								</s:if>
								type="radio">否&nbsp;<input
								name="showNav" value="1" class="radio" 
								<s:if test="%{#request.privilege.showNav == 1}">
								checked="checked" 
								</s:if>
								type="radio">是</td>
						</tr>
						<tr>
							<td class="form_center" colspan="2"><input class=""
								value="提交" type="submit">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>



</body>
</html>