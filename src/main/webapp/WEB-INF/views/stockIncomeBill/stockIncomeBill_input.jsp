<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 引入bootstrapvalidator验证的内容 -->
<link rel="stylesheet" href="/assets/plugin/validator/css/bootstrapValidator.css"/>
<!-- 核心验证的js,依赖于jQuery.js -->
<script type="text/javascript" src="/assets/plugin/validator/js/bootstrapValidator.js"></script>
<!-- 国际化js提示信息文件,必须在bootstrapValidator.js下面 -->
<script type="text/javascript" src="/assets/plugin/validator/js/language/zh_CN.js"></script>
<!-- 时间控件 -->
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<!-- 自己书写js的验证规则 -->
<script type="text/javascript" src="/js/model/stockIncomeBill.js"></script>


</head>

<body>
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="/"
					target="_top">主页</a></li>
				<li><i class="icon-list"></i><a href="#">入库管理</a></li>
				<li class="active">入库单</li>
			</ul>
			<!-- .breadcrumb -->
			<!-- #nav-search -->
		</div>
		<div class="page-content">
			<div class="page-header">
				<h1>
					入库管理 <small> <i class="icon-double-angle-right"></i> <s:if
							test="id==null">
添加入库单</s:if> <s:else>
编辑入库单</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
		</div>
		
		<div class="page-content">

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<s:form id="stockIncomeBillForm" action="stockIncomeBill_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">交易时间</label>
							<div class="col-sm-9">
								<s:date name="vdate" format="yyyy-MM-dd" var="date" />
								<s:textfield name="vdate" placeholder="交易时间" value="%{date}"
									size="13" onclick="WdatePicker({maxDate:new Date()})"
									cssClass="Wdate"
									style="height:30px;background:#fff url(/js/My97DatePicker/skin/datePicker.gif) no-repeat right;" />
							</div>
						</div>
						
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">供应商</label>
							<div class="col-sm-9">
								<s:select list="#allSuppliers" name="supplier.id" listKey="id"
									listValue="name" />
							</div>
						</div>
						<div class="space-4"></div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">仓管员</label>
							<div class="col-sm-9">
								<s:select list="#allKeepers" name="keeper.id" listValue="username"
									listKey="id" />
							</div>
						</div>
						
						<!-- 入库单子项 -->
						<div class="row">
							<div class="col-xs-12">
								<div class="table-responsive">
									<table id="billTable"
										class="table table-striped table-bordered table-hover">
										<thead>
											<tr>
												<th>产品名称</th>
												<th>&emsp;</th>
												<th>产品颜色</th>
												<th>产品图片</th>
												<th>产品价格</th>
												<th>入库数量</th>
												<th>小计</th>
												<th>备注</th>
												<th><button class="btn" type="button" id="addItem">
														<i class="icon-edit bigger-110 hidden-480"></i> 添加
													</button></th>
											</tr>
										</thead>
										
										<tbody id="itemTbody">
										<s:if test="items.size()==0">
												<tr>
													<s:hidden name="product.id" code="productId" />
													<td><s:textfield name="product.name"
														code="productName" disabled="true" 
															cssClass="form-control" /></td> 

													<td>
														<button type="button" class="btn" code="searchProduct">
															<i class="glyphicon glyphicon-search"></i>查看
														</button>
													</td>

													<td code="productColor"></td>
													<td code="productPic"></td>
													<td><s:textfield cssClass="form-control" code="price"
															name="price" /></td> 
													<td><s:textfield cssClass="form-control" code="num" 
															name="num" /></td> 
													<td code="amount"></td>
													<td><s:textfield cssClass="form-control" code="descs" 
 															name="descs" /></td> 

													<td>
 														<button class="btn btn-sm" type="button" code="deleteItem"> 
															<i class="glyphicon glyphicon-remove"></i>删除
														</button>
													</td>
												</tr>
											</s:if>
											
										<s:else>
												<s:iterator value="items">
													<tr>
													<s:hidden name="product.id" code="productId" />
													<td><s:textfield name="product.name"
														code="productName" disabled="true" 
															cssClass="form-control" value="%{product.name}"/></td> 

													<td>
														<button type="button" class="btn" code="searchProduct">
															<i class="glyphicon glyphicon-search"></i>查看
														</button>
													</td>

													<td code="productColor"><span class="btn-colorpicker" style="background-color:${product.color};"></td>
													<td code="productPic"><img src="${product.smallPic}" alt="150x150"/></td>
													<td><s:textfield cssClass="form-control" code="price"
															name="price" /></td> 
													<td><s:textfield cssClass="form-control" code="num" 
															name="num" /></td> 
													<td code="amount">${amount}</td>
													<td><s:textfield cssClass="form-control" code="descs" 
 															name="descs" /></td> 

													<td>
 														<button class="btn btn-sm" type="button" code="deleteItem"> 
															<i class="glyphicon glyphicon-remove"></i>删除
														</button>
													</td>
												</tr>
												</s:iterator>
											</s:else>
										</tbody>
									</table>
									<!-- /.table-responsive -->
								</div>
								<!-- /span -->
							</div>
							<!-- /row -->
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
							
								<button class="btn" type="submit">
									<i class="icon-ok bigger-110"></i> 保存
								</button>
								
								&nbsp;&nbsp; &nbsp;
								<button class="btn" type="button" id="resetBtn" >
									<i class="icon-undo bigger-110"></i> 重置
								</button>
								
								&nbsp;&nbsp; &nbsp;
								
								<a href="stockIncomeBill.action">
									<button class="btn" type="button">
									<i class="icon-no bigger-110"></i> 取消
									</button>
									
								</a>
							</div>
						</div>
					</s:form>
					
					<!-- modal -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
						aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">产品列表</h4>
								</div>
								<div class="modal-body" id="myModalError"></div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default close"
										data-dismiss="modal">关闭</button>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
					</div>
					<!-- modal -->
					
				</div>
			</div>
		</div>
	</div>
	
	


</body>
</html>