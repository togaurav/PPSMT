<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑资源类别</title>
</head>
<body>


	<div id="body">
		<h3>修改资源分类</h3>
		<div id="main">
			<form action="${pageContext.request.contextPath}/default/editPrivilegeCate.action" method="post">
				<input name="cateId" value="${cateId}" type="hidden">
				<table>
					<tbody>
						<tr>
							<td class="form_head">所属分类：</td>
							<td class="form_field"><select name="typeId">
								<s:iterator value="#request.privilegeTypes" id="privilegeType">
									<option value="${id }"
									<s:if test="%{#request.privilegeCate.privilegeType.id == #privilegeType.id}">
									selected="selected"
									</s:if>
									>${typeName }</option>
								</s:iterator>
							</select>
							</td>
						</tr>
						<tr>
							<td class="form_head">资源分类名称：</td>
							<td class="form_field"><input name="cateName"
								value="<s:property value="#request.privilegeCate.cateName"/>" type="text">
							</td>

						</tr>
						<tr>
							<td class="form_head">是否显示在导航中：</td>
							<td class="form_field"><input name="showNav" value="0"
								class="radio" 
								<s:if test="%{#request.privilegeCate.showNav == 1}">
								checked="checked" 
								</s:if>
								type="radio">否&nbsp;<input name="showNav"
								value="1" class="radio" 
								<s:if test="%{#request.privilegeCate.showNav == 1}">
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