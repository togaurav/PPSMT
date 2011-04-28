<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/admin.css">
<script type="text/javascript"
	src="http://jquery.bassistance.de/treeview/jquery.treeview.js"></script>
<link rel="stylesheet"
	href="http://jquery.bassistance.de/treeview/demo/screen.css"
	type="text/css" />
<link rel="stylesheet"
	href="http://jquery.bassistance.de/treeview/jquery.treeview.css"
	type="text/css" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/javascript/jquery-easyui-1.2.3/themes/default/easyui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/jquery-easyui-1.2.3/jquery.easyui.min.js"></script>
<title>PPS运维监控系统</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#lefttree").treeview({
			persist : "location",
			collapsed : true,
			unique : true
		});
	});
</script>
</head>
<body  class="easyui-layout">

		<div region="north" border="false" style="height: 80px;">
			<div id="admin-header">
				<div id="admin-header-logo"><img src="${pageContext.request.contextPath}/images/logo.png"/></div>
				<div id="admin-header-menu">
				<div id="admin-header-userinfo"><s:property value="#session.roleName" />：<s:property value="#session.userName" />/<s:property value="#session.nickName" /></div>
				<div id="admin-header-ip">IP：<s:property value="#session.ip" /> 
				<a href="${pageContext.request.contextPath}/default/logout.action" target="_parent">[ 退出 ]</a></div>
				<div id="admin-header-refer">
					<a href="javascript:void(0)" 
						onclick="window.frames['main-iframe'].location.href += (window.frames['main-iframe'].location.href.indexOf('?') == -1 ? '?' : '&').concat(Math.random());">刷新内页</a> 
					<a href="" target="main-iframe" >返回登录首页</a></div>
				</div>
			</div>
		</div>
		<div region="west" split="true" title="导航菜单"
			style="width: 200px; padding1: 1px;">
	 
			
			<ul id="lefttree" class="filetree">
				<s:iterator value="#session.maps.keySet()" id="typeKey">
					<li><span class="folder"><s:property value="#typeKey" />
					</span>
						<ul>
							<s:iterator value="#session.maps.get(#typeKey).keySet()"
								id="cateKey">
								<li><span class="folder"><s:property
											value="#cateKey" />
								</span>
									<ul>
										<s:iterator value="#session.maps.get(#typeKey).get(#cateKey)"
											id="privileteSet">
											<s:if test="%{#privileteSet.showNav == 1 }">
											<li>
											<span class="file">
												<a href="${pageContext.request.contextPath}<s:property value="#privileteSet.action" />" target="main-iframe">
													<s:property	value="#privileteSet.name" />
												</a>
											</span>
											</li>
											</s:if>
										</s:iterator>
									</ul></li>
							</s:iterator>
						</ul></li>
				</s:iterator>
			</ul>
			
	
		</div>

		<div region="center" title="PPS运维监控系统"
			style="padding1: 5px; background: #aaa; overflow: hidden">
			<iframe id="main-iframe" name="main-iframe" scrolling="auto"
				frameborder="0" src="${pageContext.request.contextPath}/default.jsp" style="width: 100%; height: 100%;"></iframe>
		</div>

</body>
</html>