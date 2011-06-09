<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源列表</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/frameset.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-1.6.1.min.js"></script>
<script type="text/javascript">
function showItem(i,obj){
	$('tr.item_'+i).toggle();
	obj.value=obj.value=='展开'?'收缩':'展开';
}
function psort(sid,obj){	// 修改资源排序
	$('#p_img_'+sid).show();
	obj.disabled = true;
	var sortIndex = $(obj).prev().val();
	$.ajax({
	   type: "POST",
	   url: "${pageContext.request.contextPath}/default/editPrivilegeSortIndex.action",
	   data: "sortIndex="+sortIndex+'&privilegeId='+sid,  // +'&rand='+ new Date().getTime()
	   success: function(data, textStatus){
		 alert(data);
		 $('#p_img_'+sid).hide();
		 obj.disabled = false;
	   }
	});
	
	
}
function pcsort(sid,obj){    // 资源分类修改排序
	$('#pc_img_'+sid).show();
	obj.disabled = true;
	var sortIndex = $(obj).prev().val();
	$.ajax({
	   type: "POST",
	   url: "${pageContext.request.contextPath}/default/editCateSortIndex.action",
	   data: "sortIndex="+sortIndex+'&cateId='+sid,  // +'&rand='+ new Date().getTime()
	   success: function(data, textStatus){
		 $('#pc_img_'+sid).hide();
		 obj.disabled = false;
	   }
	});
	
}


jQuery(function($){
	//$('#pngfix').pngFix();//IE6PNG透明显示插件
	$("input[type=button],input[type=submit]").each(function(){
		if (this.className == 'del_btn'){
			$(this).hover(
				  function () {
					 $(this).addClass('bg_red');
				  },
				  function () {
					 $(this).removeClass('bg_red');
				  }
			);
		}else{
			$(this).hover(
				  function () {
					 $(this).addClass('hl_input2');
				  },
				  function () {
					 $(this).removeClass('hl_input2');
				  }
			);			
		}
	});
	$("tr.data_rows:even").css({"background-color": "#EEEEFF" }); 
	var oldcolor='';
	$("tr.data_rows").hover(
		  function () {
			 oldcolor=$(this).css("background-color");
			 $(this).css({"background-color": "#D0E5F5" });  
		  },
		  function () {
			 $(this).css({"background-color": oldcolor });  
		  }
	); 
});
</script>
</head>
<body>

<h1 id="frame-header"></h1>
<h2 class="data-header">资源列表</h2>
<h2 class="data-header">
<input value="添加大分类" onclick="location.href='${pageContext.request.contextPath}/authority/addPrivilegeType.jsp'" type="button">
<input value="添加资源分类" onclick="location.href='${pageContext.request.contextPath}/default/addPrivilegeCateView.action'" type="button">&nbsp;
<input value="添加资源" onclick="location.href='${pageContext.request.contextPath}/default/addPrivilegeView.action'" type="button">
</h2>

<table class="data-table table-header">

<s:iterator value="#request.privilegeCates" id="cate">
	<tr style="background-color: rgb(238, 238, 255);" class="privilege_cate data_rows red">
		<td width="400"><s:property value="#cate.cateName" /></td>
		<td>
			<input value="展开" type="button" 
				onclick="showItem(<s:property value="#cate.id" />,this)"> 
			<input value="修改" onclick="location.href='${pageContext.request.contextPath}/default/editPrivilegeCateView.action?cateId=<s:property value="#cate.id" />'" type="button">&nbsp;
			<input class="del_btn" value="删除"
				 onclick="if(confirm('确认删除？'))location.href='${pageContext.request.contextPath}/default/deletePrivilegeCate.action?cateId=<s:property value="#cate.id" />'" 
				 type="button">&nbsp;
			<input value="添加资源" onclick="location.href='${pageContext.request.contextPath}/default/addPrivilegeView.action?cateId=<s:property value="#cate.id" />'" type="button">
			<input style="width: 30px;" value="<s:property value="#cate.sortIndex" />" type="text"> 
			<input value="修改排序"  
				onclick="pcsort(<s:property value="#cate.id" />,this)"  type="button">  
			<span id="pc_img_361" style="display: none;"><img src="${pageContext.request.contextPath}/images/loading.gif"></span>
		</td>
	</tr>

	<s:iterator value="#cate.privileges" id="privilege">
		<tr class="privilege_item  item_<s:property value="#cate.id" />" style="display: none;">
			<td><s:property value="#privilege.name" /></td>
			<td>
				<input value="修改" 
					onclick="location.href='${pageContext.request.contextPath}/default/editPrivilegeView.action?privilegeId=<s:property value="#privilege.id" />'" 
					type="button">&nbsp;
				<input class="del_btn" value="删除" 
					onclick="if(confirm('确认删除？'))location.href='${pageContext.request.contextPath}/default/deletePrivilege.action?privilegeId=<s:property value="#privilege.id" />'" 
					type="button" />
				<input style="width: 30px;" value="<s:property value="#privilege.sortIndex" />" type="text" /> 
				<input value="修改排序" 
					onclick="psort(<s:property value="#privilege.id" />,this)" type="button">
				<span id="p_img_1100" style="display: none;"><img src="${pageContext.request.contextPath}/images/loading.gif"></span>
			</td>
		</tr>
	</s:iterator>

</s:iterator>

</table>

</body>
</html>