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
<!-- 自己书写js的验证规则 -->
<script type="text/javascript" src="/js/model/systemDictionaryType.js"></script>


</head>

<body>
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="/"
					target="_top">主页</a></li>
				<li><i class="icon-list"></i><a href="#">基础数据</a></li>
				<li class="active">数据字典类型管理</li>
			</ul>
			<!-- .breadcrumb -->
			<!-- #nav-search -->
		</div>
		<div class="page-content">
			<div class="page-header">
				<h1>
					数据字典类型管理 <small> <i class="icon-double-angle-right"></i> <s:if
							test="id==null">
添加数据字典类型</s:if> <s:else>
编辑数据字典类型</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
		</div>
		
		<div class="page-content">

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<s:form id="systemDictionaryTypeForm" action="systemDictionaryType_save"
						cssClass="form-horizontal" role="form">
						<s:hidden name="id" />
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 名称 </label> 
							<div class="col-sm-9">
								<s:textfield name="name" placeholder="数据字典类型名称"
									cssClass="col-xs-10 col-sm-5" id="name"/>
								<font size="5px" color="red">&nbsp; * &emsp;
								</font>
							</div>
						</div>
							<div class="space-4"></div>


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
								
								<a href="systemDictionaryType.action">
									<button class="btn" type="button">
									<i class="icon-no bigger-110"></i> 取消
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