<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<!-- 引入bootstrapvalidator验证的内容 -->
<link rel="stylesheet"
	href="/assets/plugin/validator/css/bootstrapValidator.css" />
<!-- 核心验证的js,依赖于jQuery.js -->
<script type="text/javascript"
	src="/assets/plugin/validator/js/bootstrapValidator.js"></script>
<!-- 国际化js提示信息文件,必须在bootstrapValidator.js下面 -->
<script type="text/javascript"
	src="/assets/plugin/validator/js/language/zh_CN.js"></script>
<!-- 自己书写js的验证规则 -->
<script type="text/javascript" src="/js/model/employee.js"></script>

</head>

<body>
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="/"
					target="_top">主页</a></li>
				<li><i class="icon-list"></i><a href="#">组织机构</a></li>
				<li class="active">员工管理</li>
			</ul>
			<!-- .breadcrumb -->
			<!-- #nav-search -->
		</div>
		<div class="page-content">
			<div class="page-header">
				<h1>
					员工管理 <small> <i class="icon-double-angle-right"></i> <s:if
							test="id==null">
添加员工</s:if> <s:else>
编辑员工</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
		</div>

		<div class="page-content">

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<s:form id="employeeForm" action="employee_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" id="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<s:hidden name="baseQuery.username"/>
						<s:hidden name="baseQuery.email"/>
						<s:hidden name="baseQuery.deptId"/>
						

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 用户名 </label>
							<div class="col-sm-9">
								<s:textfield name="username" placeholder="用户名"
									cssClass="col-xs-10 col-sm-5" id="username" />
								<font size="5px" color="red">&nbsp; * &emsp; <%-- 									<s:property value="fieldErrors['nameError'][0]" /> --%>
								</font>
							</div>
						</div>
						<s:if test="id==null">
							<div class="space-4"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-2">密码</label>
								<div class="col-sm-9">
									<s:password name="password" placeholder="密码"
										cssClass="col-xs-10 col-sm-5" />
									<font size="5px" color="red">&nbsp; * &emsp; <%-- 										<s:property value="fieldErrors['pwdError'][0]" /> --%>
									</font>
								</div>
							</div>
							<div class="space-4"></div>
							<div class="form-group">
								<label class="col-sm-3 control-label no-padding-right"
									for="form-field-2">确认密码</label>
								<div class="col-sm-9">
									<s:password name="confirmPassword" placeholder="确认密码"
										cssClass="col-xs-10 col-sm-5" />
								</div>
							</div>
						</s:if>
						<div class="space-4"></div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">邮箱</label>
							<div class="col-sm-9">
								<s:textfield name="email" placeholder="邮箱"
									cssClass="col-xs-10 col-sm-5" />
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">年龄</label>
							<div class="col-sm-9">
								<s:textfield name="age" placeholder="年龄"
									cssClass="col-xs-10 col-sm-5" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">所在部门</label>
							<div class="col-sm-9">
								<s:select list="#allDepts" name="department.id" listKey="id"
									listValue="name" headerKey="-1" headerValue="--请选择--" />
							</div>
						</div>
						
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">拥有角色</label>
							<div class="col-sm-9">
								<s:checkboxlist list="#allRoles" name="ids" listKey="id"
									listValue="name"/>
							</div>
						</div>

						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">

								<button class="btn" type="submit">
									<i class="icon-ok bigger-110"></i> 保存
								</button>

								&nbsp;&nbsp; &nbsp;
								<button class="btn" type="button" id="resetBtn">
									<i class="icon-undo bigger-110"></i> 重置
								</button>

								&nbsp;&nbsp; &nbsp; <a href="employee.action">
									<button class="btn" type="button" id="cancelBtn">
										<i class="icon-undo bigger-110"></i> 取消
									</button>

								</a>
							</div>
						</div>
					</s:form>
				</div>
			</div>
		</div>
	</div>




</body>
</html>