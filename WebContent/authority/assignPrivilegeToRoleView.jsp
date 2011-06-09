<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置角色权限</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
<script type="text/javascript">
function selectRev(cate_id)
{
    if (cate_id)
        $('input.privilege_cate_' + cate_id).each(function(){this.checked=!this.checked;});
    else
        $('input[type="checkbox"]').each(function(){this.checked=!this.checked});
}
function selectAll(cate_id)
{
    if (cate_id)
        $('input.privilege_cate_' + cate_id).attr('checked','checked');
    else
        $('input[type="checkbox"]').attr('checked','checked');
}
</script>
</head>
<body>

<h1 id="frame-header"></h1>
<h2 class="data-header">设置角色权限</h2>
<s:form action="assignPrivilegeToRole" id="assignPrivilegeToRole" validate="true" method="post">
<table class="data-table table-header">
	<tr>
		<td>
			<input value="全选" onclick="selectAll();" type="button">&nbsp;
			<input class="" value="反选" onclick="selectRev();" type="button">&nbsp;
			<input value="提交" type="submit">
		</td>
	</tr>
	<s:iterator value="#request.privilegeCates" id="cate">
		<!-- 二级权限 -->
		<tr style="background-color: rgb(238, 238, 255);" class="privilege_cate data_rows red">
			<td><s:property value="#cate.cateName" />&nbsp;
				<input value="全选" onclick="selectAll('<s:property value="#cate.id" />');" type="button">&nbsp;
				<input value="反选" onclick="selectRev('<s:property value="#cate.id" />');" type="button">
			</td>
		</tr>
		<s:iterator value="#cate.privileges" id="privileges">
			<!-- 权限,隔行换色      -->
			<tr style="background-color: transparent;" class="privilege_item data_rows">
				<td>
					<label for="item_id_<s:property value="#privileges.id" />">
						<input name="privilegeIds" value="<s:property value="#privileges.id" />"  
						<s:if test="%{#request.privilegeIds.contains(#privileges.id)}">
						checked
						</s:if>
							id="item_id_<s:property value="#privileges.id" />" 
							class="privilege_cate_<s:property value="#cate.id" />" type="checkbox">
						<s:property value="#privileges.name" />
					</label>
				</td>
			</tr>
		</s:iterator>
	</s:iterator>
	<tr>
		<td>
			<input class="" value="全选" onclick="selectAll();" type="button">&nbsp;
			<input class="" value="反选" onclick="selectRev();" type="button">&nbsp;
			
			<input name="roleId" value="${roleId}" type="hidden" />
			<input class="" value="提交" type="submit">
		</td>
	</tr>
</table>
</s:form>

</body>
</html>