<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>采购报表列表</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 报表展示 -->
<script src="/js/highcharts/highcharts.js"></script>
<script src="/js/highcharts/highcharts-3d.js"></script>
<script src="/js/highcharts/modules/exporting.js"></script>
<!-- 第三张对话框所需要的两个引入文件 -->
<script src="js/highcharts/modules/data.js"></script>
<script src="js/highcharts/modules/drilldown.js"></script>

<!-- 时间控件 -->
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$("button").on(
				'click',
				function() {
					//alert(0);
					var url = $(this).data("url");
					//如果当前控件定义了data-url属性
					if (url) {
						//alert(url);
						$('#myModal').find(".modal-body").html("正在加载...");
						url += "?" + $("#domainForm").serialize() + "&"
								+ new Date().getTime();
						$(".modal-body").load(url);
						$('#myModal').modal({
							backdrop : false,// false:模式对话框，就是没有关闭模式对话框后面页面都不能点击。
							keyboard : true,// 当按下 esc键时关闭模态框，设置为 false 时则按键无效。默认为true
							show : true
						});
					}

				});

	});
</script>

</head>
<body>
	<s:form id="domainForm" action="purchaseBillItem">
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="icon-home home-icon"></i> <a href="/"
						target="_top">主页</a></li>
					<li><i class="icon-list"></i><a href="#">采购模块</a></li>
					<li class="active">采购报表列表</li>
				</ul>
				<!-- .breadcrumb -->

			</div>

			<div class="page-content">
				<div class="page-header">
					<h1>
						采购报表列表 <small> <i class="icon-double-angle-right"></i>
							采购报表 &amp; 列表
						</small>
					</h1>
					<div class="col-sm-6" style="width: 100%">

						交易时间从:
						<s:date name="baseQuery.beginDate" format="yyyy-MM-dd" var="bDate" />
						<s:date name="baseQuery.endDate" format="yyyy-MM-dd" var="eDate" />
						<s:textfield name="baseQuery.beginDate" value="%{bDate}" size="13"
							placeholder="交易开始时间" onclick="WdatePicker({maxDate:new Date()})"
							cssClass="Wdate" style="height:30px;" />
						到
						<s:textfield name="baseQuery.endDate" value="%{eDate}" size="13"
							onclick="WdatePicker({maxDate:new Date()})" cssClass="Wdate"
							style="height:30px;" placeholder="交易结束时间" />
						&emsp; 状态：
						<s:select list="#{-2:'--请选择--',-1:'已作废',0:'待审核',1:'已审核'}"
							name="baseQuery.status" onchange='$("#domainForm").submit();'></s:select>
						&emsp; 分组条件：
						<s:select
							list="#{'o.bill.supplier.name':'供应商','month(o.bill.vdate)':'月份','o.bill.buyer.username':'采购员'}"
							name="baseQuery.groupBy" onchange='$("#domainForm").submit();'></s:select>

						<button type="submit" class="btn btn-primary">
							<i class="icon-search align-top bigger-125"></i> 查询
						</button>

						<button data-url="purchaseBillItem_chart1.action"
							class="btn btn-primary" type="button">
							<i class="icon-beaker align-top bigger-125"></i> 3D饼图
						</button>

						<button data-url="purchaseBillItem_chart2.action"
							class="btn btn-info" type="button">
							<i class="icon-beaker align-top bigger-125">2D饼图</i>
						</button>

						<button data-url="purchaseBillItem_chart3.action"
							class="btn btn-success" type="button">
							<i class="icon-beaker align-top bigger-125">percent饼图</i>
						</button>

					</div>
				</div>
				<!-- /.page-header -->
				<!-- PAGE CONTENT BEGINS -->
				<div class="row">
					<div class="col-xs-12">

						<div class="table-responsive">
							<table id="itemTable"
								class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>明细编号</th>
										<th>供应商名称</th>
										<th>采购员</th>
										<th>产品名称</th>
										<th>交易时间</th>
										<th>采购数量</th>
										<th>采购价格</th>
										<th>小计</th>
										<th>产品类别</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="#list" var="objects">
										<tr style="color: blue">
											<td colspan="10">${objects[0]}-记录数${objects[1]}条</td>
										</tr>
										<!-- 定义变量,用来累加 -->
										<s:set var="totalAmount" value="0" />
										<s:set var="totalNum" value="0" />
										<s:iterator value="findItems(#objects[0])">
											<tr>
												<td>${id}</td>
												<td>${bill.supplier.name}</td>
												<td>${bill.buyer.username}</td>
												<td>${product.name}</td>
												<td>${bill.vdate}</td>
												<td>${num}</td>
												<td>${price}</td>
												<td>${amount}</td>
												<td>${product.type.name}</td>
												<td>
													<!--   状态:0待审,1已审，-1作废 --> <s:if test="bill.status==1">
														<span class="label label-sm label-success">已审核</span>
													</s:if> <s:elseif test="bill.status==-1">
														<span class="label label-danger arrowed-in">已作废</span
													</s:elseif> <s:else>
														<span class="label">待审核</span>
													</s:else>
												</td>
											</tr>
											<!-- 累加 -->
											<s:set var="totalAmount" value="#totalAmount+amount" />
											<s:set var="totalNum" value="#totalNum+num" />
										</s:iterator>
										<tr style="color: red">
											<th colspan="5">合计:</th>
											<th>${totalNum}</th>
											<th></th>
											<th>${totalAmount}</th>
											<th></th>
											<th></th>
										</tr>
									</s:iterator>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</s:form>
	<!-- Modal模态框 -->
	<div id="myModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">报表数据模型</h4>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</body>
</html>

