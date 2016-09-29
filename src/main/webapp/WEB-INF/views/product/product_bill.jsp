<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>产品管理</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
</head>

<!-- 采购订单的模式对话框，即订单的选择产品列表界面 -->

<body>
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
											<th>产品编号</th>
											<th>产品名称</th>
											<th>产品颜色</th>
											<th>缩略图</th>
											<th>成本价格</th>
											<th>产品类型</th>
											<th>选择</th>
										</tr>
									</thead>

									<tbody>
										<s:iterator value="pageList.rows">
											<tr>
												<td>${id}</td>
												<td>${name}</td>
												<td><span class="btn-colorpicker"
													style="background-color:${color};"></span></td>
												<td><img src="${smallPic}" /></td>
												<td>${costPrice}</td>
												<td>${type.name}</td>

												<td>
													<button type="button" class="btn btn-info choose">
														<i class="icon-edit bigger-110"></i>选择
													</button>
												</td>
											</tr>
										</s:iterator>

									</tbody>
								</table>


							</div>
							<!-- /span -->
						</div>
						<!-- /row -->
					</div>
				</div>
			</div>
</body>
</html>

