<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>

<script>
  $(document).ready(function(){
    $("#lefttree").treeview();
  });
  </script>
</head>
<body>

<ul id="lefttree" class="filetree">

<li>
	<span class="folder">Folder 2</span>
	<ul>
		<li><span class="folder">Subfolder 2.1</span>
			<ul>
				<li><span class="file">File 2.1.1</span></li>
				<li><span class="file">File 2.1.2</span></li>
			</ul>
		</li>
		<li><span class="file">File 2.2</span></li>
	</ul>
</li>

</ul>

</body>
</html>