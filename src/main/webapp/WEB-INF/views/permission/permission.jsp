<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>权限管理</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body>
	<s:form id="domainForm" action="permission">
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

					<li><a href="#">系统管理</a></li>
					<li class="active">权限管理</li>
				</ul>
				<!-- .breadcrumb -->

			</div>

			<div class="page-content">
				<div class="page-header">
					<h1>
						系统管理 <small> <i class="icon-double-angle-right"></i> 权限
							&amp; 列表
						</small>
					</h1>
					<div class="col-sm-6" style="width: 100%">
					
						<a class="btn btn-info" href="permission_input.action">新建 <i
							class="icon-plus "></i></a>
						&emsp;
						名称：
						<s:textfield name="baseQuery.name" size="15"
							cssClass="nav-search-input" placeholder="请输入名称" />
<!-- 						部门： -->
<%-- 						<s:select list="#allDepts" name="baseQuery.deptId" listKey="id" --%>
<%-- 							listValue="name" headerKey="-1" headerValue="--请选择--" /> --%>

						
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
												<th class="center"><label> <input
														type="checkbox" class="ace" /> <span class="lbl"></span>
												</label></th>
												<th>权限名称</th>
												<th>方法名</th>
												<th>描述</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<s:iterator value="pageList.rows">
												<tr <s:if test="id==#parameters['id'][0]">style="color:red"</s:if> >
													<td class="center"><label> <input
															type="checkbox" class="ace" /> <span class="lbl"></span>
													</label></td>

													<td>${name}</td>
													<td>${method}</td>
													<td>${descs}</td>

													<td>
														<div
															class="visible-md visible-lg hidden-sm hidden-xs btn-group">
															<button type="button"
																onclick="updateDomain('permission',${id})"
																class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>

															<button type="button"
																onclick="deleteDomain(this,'permission_delete.action?id=${id}')"
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

