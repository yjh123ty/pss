// 高级修改：带额外参数的修改
function updateDomain(url, id) {
	$("#domainForm").attr("action", url + "_input.action?id=" + id);// 修改地址
	$("#domainForm").submit();
}

// 高级删除：带额外参数的删除
function deleteDomain(src, url) {
	if (confirm("是否要删除？")) {
		// 参数顺序:url,额外json格式参数,回调函数
		$.post(url, function(data) {
			// 默认情况下data是什么格式?json格式,本质Object格式
			if (data instanceof Object) {// json格式,本质Object格式
				if (data.success) {
					// $(this)==获取的是$.post对象
					// console.debug($(this));
					// 获取itemTable的里面有多少tr
					// console.debug($("#itemTable tr").size());
					if ($("#itemTable tr").size() < 3) {// 最后一条记录,上一页
						$("#domainForm").submit();
					} else {
						// button->td->tr
						// 从子元素追溯到祖先元素
						$(src).closest("tr").remove();
						// 处理右下角的提示信息
						dm_notification(data.msg, 'red', 2000);
					}
					// 修改总的条数
					$("#totalCount").html($("#totalCount").html() - 1);
					$("#end").html($("#end").html() - 1);
				} else {
					// 用bootstrap插件的模式对话框，提示删除异常
					$("#myModalError").html(data.msg);
					$('#myModal').modal({
						backdrop : false,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
						keyboard : true,// 当按下 esc键时关闭模态框，设置为 false
						// 时则按键无效。默认为true
						show : true
					});
				}
			} else {// 不是json格式,没有权限
				$("#myModalError").html("没有权限");
				$('#myModal').modal({
					backdrop : false,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
					keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
					show : true
				});
			}
		});
	}

}


//下载
function downloadDomain(url){
	//修改表单提交的url地址
	$("#domainForm").attr("action",url+"_download.action");
	$("#domainForm").submit();
	//变回查询地址
	$("#domainForm").attr("action",url+".action");
}


// 页面跳转
function go(no) {
	$("#pageNo").val(no);
	$("#domainForm").submit();
}

$().ready(function() {

	// 1.输入查询页面的时候,只要用户输入非数字,就把非数字退掉
	$("#pageNo").keyup(function() { // keyup事件处理
		$(this).val($(this).val().replace(/\D|^0/g, ''));
	}).bind("paste", function() { // CTR+V事件处理
		$(this).val($(this).val().replace(/\D|^0/g, ''));
	}).css("ime-mode", "disabled"); // CSS设置输入法不可用

	// 2.实现重置的功能,新建和修改
	$("#resetBtn").click(function() {
		// 不让id,当前页，和页长为空，若重置为空，则修改完成过后，跳转不回当前修改的页面
		$(
				"input[name!='id'][name!='baseQuery.currentPage'][name!='baseQuery.pageSize']")
				.val("");
		$("select").val(-1);
	});

});