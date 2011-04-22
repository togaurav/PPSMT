<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
</head>
<body>
<div id="body">
<h3>资源列表</h3>
<div id="main">
<input value="添加大分类" onclick="location.href=''" type="button">
<input value="添加资源分类" onclick="location.href=''" type="button">&nbsp;
<input value="添加资源" onclick="location.href=''" type="button">
<table>

<s:iterator value="#request.privilegeCates" id="cate">
<tr style="background-color: rgb(238, 238, 255);" class="privilege_cate data_rows red">
	<td width="400"><s:property value="#cate.cateName" /></td>
	<td>
		<input value="展开" type="button">  <!-- onclick="showItem(${cateId},this)"  -->
		<input value="修改" onclick="location.href=''" type="button">&nbsp;
		<input class="del_btn" value="删除" onclick="if(confirm('确认删除？'))location.href=''" type="button">&nbsp;
		<input value="添加资源" onclick="location.href=''" type="button">
		<input style="width: 30px;" value="<s:property value="#cate.sortIndex" />" type="text"> 
		<input value="修改排序"  type="button">  <!-- onclick="pcsort(${cateId},this)" -->
		<span id="pc_img_361" style="display: none;"><img src="/images/loading.gif"></span>
	</td>
</tr>
</s:iterator>

</table>
</div>
</div>

</body>
</html>