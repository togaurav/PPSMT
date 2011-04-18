<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="keywords" content="">
<meta http-equiv="description" content="">
<link rel="shortcut icon" href="" />
<script src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
<title><decorator:title default="Layout"/></title>
<!-- 从被装饰页面获取head标签内容 -->
<decorator:head/>
</head>
<body>
Layout
<decorator:body />
</body>
</html>