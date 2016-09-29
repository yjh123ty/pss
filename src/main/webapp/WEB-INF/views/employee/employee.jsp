<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>员工管理</title>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 滑块 -->
<link rel="stylesheet" type="text/css" href="/assets/plugin/slider/css/bootstrap-slider.min.css">
<script type="text/javascript" src="/assets/plugin/slider/js/bootstrap-slider.min.js"></script>
<script type="text/javascript">
$(function(){
	  $("#ex2").slider({
		  min: 18,
	      max: 130
	  });
});
</script>
</head>
<body>

	<s:form id="domainForm" action="employee">
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

					<li><a href="#">组织机构</a></li>
					<li class="active">员工管理</li>
				</ul>
				<!-- .breadcrumb -->

			</div>

			<div class="page-content">
				<div class="page-header">
					<h1>
						组织机构 <small> <i class="icon-double-angle-right"></i> 员工
							&amp; 列表
						</small>
					</h1>
					<div class="col-sm-6" style="width: 100%">
					
						<a class="btn btn-info" href="employee_input.action">新建 <i
							class="icon-plus "></i></a>
						&emsp;
						用户名：
						<s:textfield name="baseQuery.username" size="15"
							cssClass="nav-search-input" placeholder="请输入用户名" />
						email：
						<s:textfield name="baseQuery.email" size="15"
							cssClass="nav-search-input" placeholder="请输入邮箱地址" />
						部门：
						<s:select list="#allDepts" name="baseQuery.deptId" listKey="id"
							listValue="name" headerKey="-1" headerValue="--请选择--" />

						年龄 <b>${baseQuery.ages[0]}</b>&nbsp;&nbsp;
						<s:textfield id="ex2" name="baseQuery.age" cssClass="span2" data-slider-min="18" data-slider-max="130"
							data-slider-step="1" data-slider-value="[%{baseQuery.ages[0]},%{baseQuery.ages[1]}]" />
						&nbsp;&nbsp;<b>到${baseQuery.ages[1]}</b>
						
						<button type="button" onclick="go(1);" class="btn btn-info">
							搜索<i class="icon-search align-top icon-on-right"></i>
						</button>
						
						<button type="button" onclick="downloadDomain('employee');" class="btn btn-primary">
							导出xlsx文件<i class="icon-beaker align-top bigger-125"></i>
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
												<th>用户名</th>
												<th>密码</th>
												<th>年龄</th>
												<th>邮箱</th>
												<th>头像</th>
												<th>所在部门</th>
												<th>角色</th>
												<th>操作</th>
											</tr>
										</thead>

										<tbody>
											<s:iterator value="pageList.rows">
												<tr <s:if test="id==#parameters['id'][0]">style="color:red"</s:if> >
													<td class="center"><label> <input
															type="checkbox" class="ace" /> <span class="lbl"></span>
													</label></td>

													<td>${username}</td>
													<td>${password}</td>
													<td>${age}</td>
													<td>${email}</td>
													<td><img src='${headImage}'></td>
													<td>${department.name}</td>
													<td>
														<s:iterator value="roles" status="sta">
															<s:if test="#sta.last">
														     ${name}
														   </s:if>
															<s:else>
														     ${name},
														   </s:else>
														</s:iterator>
													</td>

													<td>
														<div
															class="visible-md visible-lg hidden-sm hidden-xs btn-group">
															<button type="button"
																onclick="updateDomain('employee',${id})"
																class="btn btn-xs btn-info">
																<i class="icon-edit bigger-120"></i>
															</button>

															<button type="button"
																onclick="deleteDomain(this,'employee_delete.action?id=${id}')"
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

