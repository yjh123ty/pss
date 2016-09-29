$(function() {
	
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
});
