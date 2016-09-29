<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>进销存</title>
<%@include file="/WEB-INF/views/common/head.jsp" %>
<!-- easyui的样式主题文件 -->
<link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
<!-- easyui的系统图标-->
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<!-- easyui依赖的jquery库-->
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<!-- easyui的插件库-->
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<!-- easyui的汉化包 -->
<script type="text/javascript" src="/easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
$(function(){
	//调用组件的tree方法，创建并初始化一棵树
	$("#menuTree").tree({
		animate : true,
		closed : true,
		onClick : function(node) {
// 			alert(node.text);  // 在用户点击的时候提示
			//在这里，每当点击了菜单树，则main框架中应该显示出对应的对话框tabs
			// 添加一个新的模板    ,在此之前应该先进行判断，若为第一次打开，则才新建选项卡面板
			var text = node.text;
			var url = node.url;
			var tt = $('#tt');
			//如果没有配置url,无法打开
			if (!url) {
				return;
			}
			//若存在，则不进行添加，直接跳转到该选项卡上
			if(tt.tabs('exists',text)){
				tt.tabs("select", text);
			}else{
				tt.tabs('add',{    
				    title:text,    
				    content:"<iframe frameborder='0' src='"+ url +"' style='width:100%;height:99.1%' </iframe>",    
				    closable:true
				});
			}
		}
	});
});
</script>
</head>
 <body>
 
     <div class="easyui-layout" fit="true">
        <div data-options="region:'north'" style="height:50px"></div>
        <div data-options="region:'south',split:true" style="height:50px; text-align: center; color:blue">
        	XXX有限公司<br /> @2009-2016<br />
        </div>
        <div data-options="region:'east',split:true" title="East" style="width:180px;"></div>
        <div data-options="region:'west',split:true" title="West" style="width:200px;">
        	菜单
        	<ul id="menuTree" url="/main_json.action"></ul>
        </div>
        
        <!-- 中间区域 -->
        <div data-options="region:'center'">
       	    <div id="tt" class="easyui-tabs" fit="true">
		        <div title="Welcome" style="padding:10px">
		        	<h1>欢迎使用CRM客户关系管理系统</h1>
					<h1>版本:V1.0</h1>
		        </div>
   			</div>
        </div>
        
    </div>
 
 </body>
</html>
