<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
function dataImport(){
	//导入索引库ajax
	$.ajax({
		type:'POST',
		url:'${pageContext.request.contextPath}/dataImport',
		success:function(data){
			if(data.status==200){
				$.messager.alert('提示','索引导入成功!');
			}else{
				$.messager.alert('提示','网络异常，请再次尝试!');
			}
		}
		
	})
	
}
</script>
<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="dataImport();">导入索引</a>
</div>
