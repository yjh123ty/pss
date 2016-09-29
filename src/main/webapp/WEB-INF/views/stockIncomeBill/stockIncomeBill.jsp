<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>XX管理</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 时间控件 -->
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<!-- 自己书写js的验证规则 -->
<script type="text/javascript" src="/js/model/StockIncomeBill.js"></script>
</head>
<body>
	<s:form id="domainForm" action="stockIncomeBill">
		<div class="main-content">
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="icon-home home-icon"></i><a href="/"
						target="_top">Home</a></li>

					<li><a href="#">入库模块</a></li>
					<li class="active">入库单管理</li>
				</ul>
				<!-- .breadcrumb -->

			</div>

			<div class="page-content">
				<div class="page-header">
					<h1>
						入库模块 <small> <i class="icon-double-angle-right"></i> 入库单
							&amp; 列表
						</small>
					</h1>
					<div class="col-sm-6" style="width: 100%">
					
						<a class="btn btn-info" href="stockIncomeBill_input.action">新建 <i
							class="icon-plus "></i></a> &emsp; 
							
						交易时间从：
						<s:date name="baseQuery.beginDate" format="yyyy-MM-dd" var="bDate"/>
						<s:date name="baseQuery.endDate" format="yyyy-MM-dd" var="eDate"/>
						<s:textfield name="baseQuery.beginDate" value="%{bDate}" size="13" placeholder="交易开始时间"
							onclick="WdatePicker({maxDate:new Date()})" cssClass="Wdate" style="height:30px;" />
						到
						<s:textfield name="baseQuery.endDate" value="%{eDate}" size="13" placeholder="交易结束时间"
							onclick="WdatePicker({maxDate:new Date()})" cssClass="Wdate" style="height:30px;" />
						
							
						状态：
						<s:select list="#{'-1':'作废','0':'待审核','1':'已审核'}" name="baseQuery.status" 
						headerKey="-2" headerValue="--请选择--" />

						<button type="button" onclick="go(1);" class="btn btn-info">
							搜索<i class="icon-search align-top icon-on-right"></i>
						</button>
						
					</div>

				</div>
				<!-- /.page-header -->


				<div class="row">
					<div class="col-xs-12">
						<!-- PAGE CONTENT BEGINS -->

						<div class="row">
							<div class="col-xs-12">
								<div class="table-responsive">
									<table id="itemTable"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>总金额</th>
												<th>总数量</th>
												<th>交易时间</th>
												<th>供应商</th>
												<th>仓管员</th>
												<th>仓库</th>
												<th>状态</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<s:iterator value="pageList.rows">
												<tr <s:if test="id==#parameters['id'][0]">style="color:red"</s:if> >

													<td>${totalAmount}</td>
													<td>${totalNum}</td>
													<td>${vdate}</td>
													<td>${supplier.name}</td>
													<td>${keeper.username}</td>
													<td>${depot.name}</td>
													
													<td class="hidden-480" code="billStatus">
														<s:if test="status==1">
															<span class="label label-sm label-success">已审核</span>
														</s:if> <s:elseif test="status==-1">
															<span class="label label-danger arrowed-in">已作废</span>
														</s:elseif> <s:else>
															<span class="label">待审核</span>
														</s:else>
													</td>

													<td>
														<div
															class="visible-md visible-lg hidden-sm hidden-xs btn-group">
															<button type="button"
																onclick="updateDomain('stockIncomeBill',${id})"
																class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>

															<button type="button"
																onclick="deleteDomain(this,'stockIncomeBill_delete.action?id=${id}')"
																class="btn btn-xs btn-danger">
																<i class="icon-trash bigger-120"></i>
															</button>
														</div>
													</td>
												</tr>
											</s:iterator>

										</tbody>
									</table>

									<%@include file="/WEB-INF/views/common/page.jsp"%>
									<!-- /.table-responsive -->

								</div>
								<!-- /span -->
							</div>
							<!-- /row -->
						</div>
					</div>
				</div>
			</div>
			
			<!-- 右下角的提示信息需要的div -->
			<div id="dm-notif"></div>
			
			
			</div>
	</s:form>
</body>
</html>

