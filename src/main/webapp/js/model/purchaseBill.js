$(function() {  //简单概括：将模块对话框中的控件中的值进行获取，再设置到input页面的tr里面的td上，td中的每一项是与对话框中的项一一对应的
	
	
	//弹框选择器
	$("#billTable").on('click', 'button[code="searchProduct"]', function() {
		//缓存的模式对话框必须放置到点击事件里面,不然添加新数据就会将明细里面之前的数据都变为现在这一条数据
		var myModal = $("#myModal");
		
		//在加载之前先拿到body，提示正在加载信息
		//相对于$(".modal-body")来说，使用  对象.find 方法查找起来更精确
		myModal.find('.modal-body').html("正在加载...");
		//载入远程HTML文件代码 并插入至DOM中
		myModal.find('.modal-body').load("/product_bill.action");
		myModal.modal();
		
		//获取bill_input页面的点击行(注意：这里的this是订单明细里点击“放大镜”的那一行的按钮)
		var tr =  $(this).closest("tr");
		
		//监控模式对话框是否被关闭（bootstrap的）
		myModal.on("shown.bs.modal",function(){
			//监控控件上的样式
			$(".choose").click(function(){
				//关闭模式对话框
				$('.close').click();
				//从"产品列表"点击的"选择按钮"获取到模式对话框的tr，注意这里的this与上面的不是同一个对象！！！！
				var tds = $(this).closest("tr").find("td");
				//先获取产品列表中每一项td的思路：先追踪到外层的tr，再拿tr中的每一个td
				tr.find("input[code=productId]").val($(tds[0]).html().trim());
				tr.find("input[code=productName]").val($(tds[1]).html().trim());
				tr.find("td[code=productColor]").html($(tds[2]).html().trim());
				tr.find("td[code=productPic]").html($(tds[3]).html().trim());
				tr.find("input[code=price]").val($(tds[4]).html().trim());
				
				//获取价格td和数量td的值，注意，若为input属性，则必须使用val()获取值，若为td属性，则就直接通过html()获取值
				tr.find("td[code=amount]").html(tr.find("input[code=num]").val()
						*tr.find("input[code=price]").val());
			});
		});
	});
	
	//保存:修改name索引,判断非法数据
	$("#saveBtn").click(function(){
		//添加标识，让错误信息值显示一次
		var flag = false;
		$("#itemTbody tr").each(function (index, item){
			
			
//			alert(index);
			
			var tr = $(item);
			tr.find("input[code=productId]").prop("name","billItems["+index+"].product.id");
			tr.find("input[code=price]").prop("name","billItems["+index+"].price");
			tr.find("input[code=num]").prop("name","billItems["+index+"].num");
			tr.find("input[code=descs]").prop("name","billItems["+index+"].descs");
			
			
			//判断必须选择产品id
			var productId = tr.find("input[code=productId]").val();
			if(!flag && !productId){
				alert("请选择产品");
				flag=true;
				tr.find("button[code=searchProduct]").focus();
				return false;
			}
			
			//判断填写采购数量
			var num = tr.find("input[code=num]").val();
			if(!flag && !num){
				alert("请填写采购数量");
				flag=true;
				tr.find("input[code=num]").focus();
				return false;
			}
			
			//判断采购数量必须为合法数据:产品:小数 ， 	正则表达式.test(要检验的数据)
			if(!flag && !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(num)||num<0){
				alert("采购数量非法");
				flag=true;
				tr.find("input[code=num]").focus();
				return false;
			}
			
		});
		
		//外面手动提交表单
		if(!flag){
			$("#purchaseBillForm").submit();
		}
	});
	
	// 删除明细，同样先获取Tbody，再在这个范围里面根据一个子条件去查找对应的控件
	$("#billTable").on('click', 'button[code="deleteItem"]', function() {
		if($("#billTable tr").length>2){
			$(this).closest("tr").remove();
		}
	});
	
	
	//自动计算小计:采购价格 	采购数量发送变化的时候自动计算小计(还是不提交到后台,只是给用户看)
	$("#billTable").on('blur',"input[code=num],input[code=price]",function(){
		//1.获取tr，失去焦点的这个事件所在的这一行
		//2.拿到price和num这两个input控件中的值，进行计算，并保留两位小数.toFixed(2)
		//3.最后回显到amount这个td中
		var tr = $(this).closest("tr");
		var price = tr.find("input[code=price]").val();
		var num = tr.find("input[code=num]").val();
		var amount = (price*num).toFixed(2);
		tr.find("td[code=amount]").html(amount);
	});
	
	//点击添加明细
	$("#addItem").click(function(){
		var tr = $("#itemTbody tr:last").clone(true);
		//新增一行的时候要先将里面的内容清空
		tr.find("input[code=productId]").val("");
		tr.find("input[code=productName]").val("");
		tr.find("input[code=price]").val("");
		tr.find("input[code=num]").val("");
		tr.find("input[code=descs]").val("");
		tr.find("td[code=productColor]").html("");
		tr.find("td[code=productPic]").html("");
		tr.find("td[code=amount]").html("");
		
		// append(content)	向每个匹配的元素内部追加内容。放置到最后
		// prepend(content)	向每个匹配的元素内部追加内容。放置到第一个
		$("#billTable").prepend(tr);
		
		//调用父窗体的代码修改高度...
		if(window.parent.autoHeight){
			window.parent.autoHeight();
		}
	});
	
	
	//当页面每次加载时，就应该将特定状态的订单的审核按钮做出屏蔽
	/**
	 * 首先通过上面的span找到这一行tr,
	 * 通过遍历得到每一行tr,再由tr找到这一行的button[code=auditBtn],
	 * 拿到这个button后，更改prop("disabled",true)
	 */
	$("#myBillTbody tr").each(function (index, item){
		var tr = $(item);
		var spanLoad = tr.find("td[code=billStatus]").find("span");
		var spanContext = spanLoad.html();
		if(spanContext == "已审核" || spanContext == "已作废"){
			//若为已审核或者已作废，则所有按钮都被禁
			spanLoad.closest("tr").find("button").prop("disabled",true);
		}
	});
	
	//审核
	$("#myBillTbody").on("click", "button[code='auditBtn']", function() {
		/**
		 * 1、首先通过按钮获取要改变的这一行tr，
		 * 2、再获得要发生改变的那一列，即是显示状态的那个图片样式，
		 * 3、再来判断，去拿td下面的span控件，若为“待审核”的订单，
		 * 4、拿到这个span并对其class设置为已审核的样式
		 * 5、审核按钮不能再点击
		 * 6、还要考虑持久化数据问题：提交表单，更改了数据（订单的状态status=-1  变为 1）
		 * 		拿到我点击这一行的status，把它提交上去
		 */
		var btn = $(this);
		//自定义一个属性保存id
		var id = btn.attr("billId");
		var tr = $(this).closest("tr");
		var td =tr.find("td[code=billStatus]");
		var span =td.find("span");
//		console.debug(id);
		if(span.html() == "待审核"){
			//确认是否审核
			if (confirm("是否审核通过？")) {
				span.html("已审核");
				span.prop("class","label label-sm label-success");
				btn.prop("disabled",true).html("审核");
				
				//禁用编辑和删除按钮
				$(this).closest("tr").find("button").prop("disabled",true);
				
				//发送请求，修改状态，进行数据持久化操作
				//$.get() 查看API，把check功能完成。action层需要提供一个方法，来更改status的值
				
				$.get("purchaseBill_check.action?id="+id, function(data){
					if (data instanceof Object) {// json格式,本质Object格式
						if (data.success) {
							// 处理右下角的提示信息
							dm_notification(data.msg, 'red', 2000);
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
					}
				});
				
			}
		}
	});
	
	//在点击审核按钮的同时，我们需要提交表单，更改status的值，做数据的持久化
	
	
	
//	//审核效果demo （还可以通过写一个function来实现，再给button注册一个onclick事件，调用这个JS函数）
//	$("#myTbody").on("click", "button[code='shenheBtn']", function() {
//		var btn = $(this);
//		var tr = $(this).closest("tr");
//		var td =tr.find("td[code=billStatus]");
//		var span =td.find("span");
//		
//		if(span.html() == "待审核"){
//			//确认是否审核
//			if (confirm("是否审核通过？")) {
//				span.html("已审核");
//				span.prop("class","label label-sm label-success");
//				btn.prop("disabled",true).html("审核");
//			}
//		}
//	});
	
	
});

