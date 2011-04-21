<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="http://jquery.bassistance.de/treeview/jquery.treeview.js"></script>
<link rel="stylesheet" href="http://jquery.bassistance.de/treeview/demo/screen.css" type="text/css" />
<link rel="stylesheet" href="http://jquery.bassistance.de/treeview/jquery.treeview.css" type="text/css" />
<title>PPS运维监控系统</title>
<script type="text/javascript" >
  $(document).ready(function(){
     $("#lefttree").treeview({
		persist: "location",
		collapsed: true,
		unique: true
	});
  });
  </script>
</head>
<body>

<ul id="lefttree" class="filetree">
<s:iterator value = "#request.maps.keySet()" id="typeKey">
<li>
	<span class="folder"><s:property value="#typeKey"/></span>
	<ul>
	<s:iterator value="#request.maps.get(#typeKey).keySet()" id="cateKey">  
		<li><span class="folder"><s:property value="#cateKey"/></span>
			<ul>
			<s:iterator value="#request.maps.get(#typeKey).get(#cateKey)" id="privileteSet">  
				<li><span class="file"><s:property value="#privileteSet.name"/></span></li>
			</s:iterator>	
			</ul>
		</li>
	</s:iterator>
	</ul>
</li>
</s:iterator>
</ul>

</body>
</html>