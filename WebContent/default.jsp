<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin.css">
<title>欢迎使用PPS运维监控系统！ </title>
</head>
<body>
<s:if test="%{#request.privilegeTips == null && #request.exceptionTips == null}">
	欢迎使用PPS运维监控系统！
</s:if> 
<s:else>
	<s:property value="#request.exceptionTips"/>  
	<s:property value="#request.privilegeTips"/> 
</s:else>
</body>
</html>