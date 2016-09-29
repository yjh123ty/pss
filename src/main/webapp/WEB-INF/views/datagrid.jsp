<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
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
	
	function domainFormat(value, row, index) {
		if (row.department) {
			return row.department.name;
		} else {
			return "";
		}
	}
	
	function ageFormat(value, row, index) {
		return row.age>25?'<font color="red">'+row.age+'</font>': row.age	;
	}
	
	var url;
	//添加用户
	function newUser(){
        $('#dlg').dialog('open').dialog('setTitle','添加用户');
        $('#fm').form('clear');
    }
	
	//保存用户
    function saveUser(){
		//获取easyui的表单，调用sumbit方法
        $('#fm').form('submit',{
        	//第二个参数是一个验证方法，在提交之前触发，返回false可以终止提交
        	onSubmit: function(){
                return $(this).form('validate');
            },
            //success:验证通过，点击保存按钮
            success: function(result){
                var result = $.parseJSON(result);
                if (!result.success){
                    $.messager.show({
                        title: 'Error',
                        msg: result.msg
                    });
                } else {
                    $('#dlg').dialog('close');        // close the dialog
                    $('#dataTable').datagrid('reload');    // reload the user data
                }
            }
        });
    }
	
	//编辑用户
	function editUser(){
		//获取用户选中的行数据
		var row = $("#dataTable").datagrid("getSelected");
		if(row){
			$("#dlg").dialog("open").dialog('setTitle','编辑用户');
			//在回显数据之前，清空表单
			$('#fm').form('clear');
			//数据加载到form中. 默认按照row的名称和form里面的控件名称进行对应匹配
			$("#fm").form("load",row);
			for(var p in row){
// 				console.debug(row[p]);
				$("#fm").find("input[name='employee."+ p +"']").val(row[p]);
			}
		}else{
			//提示信息
			$.messager.alert("温馨提示","你没有选中任何数据","info");
		}
	}
	
	//删除用户
    function destroyUser(){
        var row = $('#dataTable').datagrid('getSelected');
        if (row){
            $.messager.confirm('温馨提示','你确定要删除么?',function(r){
                if (r){
                    $.post('datagrid_delete.action',{id:row.id},function(result){
                    	//返回的是一个boolean值，在后台对应success这个变量传递的也是布尔值
                        if (result.success){
                            $('#dataTable').datagrid('reload');    // reload the user data
                        } else {
                            $.messager.show({    // show error message
                                title: 'msg',
                                msg: result.msg
                            });
                        }
                    },'json');
                }
            });
        }
    }
	
</script>

</head>
<body>
    <table id="dataTable" title="员工信息" class="easyui-datagrid" fitColumns="true" 
        data-options="url:'datagrid_json.action',method:'post'" pagination="true" toolbar="#toolbar" 
        rownumbers="true"  singleSelect="true">
		<thead>
			<tr>
				<th data-options="field:'id','width':10">编号</th>
				<th data-options="field:'username','width':10">用户名</th>
				<th data-options="field:'password','width':20">密码</th>
				<th data-options="field:'email','width':20">email</th>
				<th data-options="field:'age','formatter':ageFormat,'width':20">年龄</th>
				<th data-options="field:'roleString','width':30">角色</th>
				<th data-options="field:'department','formatter':domainFormat,'width':10">部门名称</th>
			</tr>
		</thead>

		<tbody>
		</tbody>
	</table>
    <div id="toolbar">
        <a id="addUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" 
       		 plain="true" onclick="newUser()">添加员工</a>
        <a id="editUser" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" 
       		 plain="true" onclick="editUser()">编辑员工</a>
        <a id="deleteUser" class="easyui-linkbutton" iconCls="icon-remove" 
       		 plain="true" onclick="destroyUser()">删除员工</a>
	 </div>
	 
     <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">员工信息</div>
        <form id="fm" method="post" action='datagrid_save.action'>
        <input type="hidden" name ="employee.id" />
            <div class="fitem">
                <label>用户名:</label>
                <input name="employee.username" class="easyui-validatebox" required="true" validType="length[2,10]">
            </div>
            <div class="fitem">
                <label>密码:</label>
                <input name="employee.password" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>Email:</label>
                <input name="employee.email" class="easyui-validatebox" validType="email">
            </div>
            <div class="fitem">
                <label>年龄:</label>
                <input name="employee.age">
            </div>
        </form>
    </div>
    		 <div id="dlg-buttons">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
		    </div>
	 
	 
	     <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</body>
</html>