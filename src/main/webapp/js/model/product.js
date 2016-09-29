$(function() {
	//二级联动
	$("#parent").bind("change",	function() {
		$("#children").empty();
		//拿到当前select改变过后中的内容（即是一级菜单选中的值）
		var pid = $(this).val();
		//若一级菜单选中的是-1：
		if (pid == -1) {
			$("#children").html("<option value='-1'>--请选择--</option>");
		} else {
			//若一级菜单选中的不为-1，那么在缓存中查找，若没有找到匹配的值，则去后台查找
			// 先从缓存取
			var cacheData = $("#children").data(pid);
			if (cacheData) {
				for (var i = 0; i < cacheData.length; i++) {
					$("#children").append(
							"<option value='" + cacheData[i].id + "'>"
									+ cacheData[i].name + "</option>");
				}
			} else {// 从后台取
				$.get("product_findChildren.action", {id : pid}, function(data) {
					if (!data instanceof Object) {
						alert("登录超时或者出现异常...");
						return;
					}
					// alert(data.length);
					$("#children").data(pid, data)// 放入缓存
					for (var i = 0; i < data.length; i++) {
						$("#children").append(
								"<option value='" + data[i].id + "'>"
										+ data[i].name + "</option>");
					}
				});
			}
		}
	});
	
	//添加产品的验证
	$('#productForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			name : {
				validators : {
					notEmpty : true
				}
			},
			costPrice : {
				validators : {
					notEmpty : true,
					numeric : true//数值
				}
			},
			salePrice : {
				validators : {
					notEmpty : true,
					numeric : true
				}
			},
			"type.id" : {//二级类型
				validators : {
					notEmpty : true
				}
			},
			"type.parent.id" : {//一级类型
				validators : {
					different : {
						field : "type.id",
						message : '必须选择正确产品类型'
					}
				}
			},
			upload : {
				validators : {
					file : {
						extension : 'gif,png,jpg,jpeg',
						type : 'image/gif,image/png,image/jpeg',
						maxSize : 5 * 1024 * 1024, // 5 MB
						message : '必须上传图片而且图片不能超过5MB'
					}
				}
			}
		}
		});
	});


