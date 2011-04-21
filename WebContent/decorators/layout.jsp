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
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.8/jquery.validate.min.js"></script>

<link rel="stylesheet" href="http://jquery.bassistance.de/treeview/demo/screen.css" type="text/css" />
<link rel="stylesheet" href="http://jquery.bassistance.de/treeview/jquery.treeview.css" type="text/css" />
<script type="text/javascript" src="http://jquery.bassistance.de/treeview/jquery.treeview.js"></script>
<style type="text/css">
#header ,#centers ,#footer{ width:100%; margin:0 auto; clear:both;font-size:18px; line-height:68px; font-weight:bold;}
#header{ height:68px; border:1px solid #CCCCCC; }
#centers{ padding:8px 0;}
#footer{ border-top:1px solid #CCCCCC; background:#F2F2F2;}

#centers .c_left{ float:left; width:230px; border:1px solid #00CC66; background:#F7F7F7; margin-right:5px; }
#centers .c_right{ float:left; width:500px;border:1px solid #00CC66; background:#F7F7F7}
</style>
<title><decorator:title default="PPS运维监控系统"/></title>
<decorator:head/>
</head>
<body>
<div id="header">头部</div>
<div id="centers">
<div class="c_left">中左</div>
<div class="c_right">
中右
<decorator:body />
</div>
<div class="clear">&nbsp;</div>
</div>
<div id="footer">底部</div> 
</body>
</html>