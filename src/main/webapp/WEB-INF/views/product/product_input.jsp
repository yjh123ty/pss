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
	src="assets/plugin/validator/js/bootstrapValidator.js"></script>
<!-- 国际化js提示信息文件,必须在bootstrapValidator.js下面 -->
<script type="text/javascript"
	src="assets/plugin/validator/js/language/zh_CN.js"></script>
<!-- 自己书写js的验证规则 -->
<script type="text/javascript" src="/js/model/product.js"></script>

</head>

<body>
<s:actionerror />
	<div class="main-content">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li><i class="icon-home home-icon"></i> <a href="/"
					target="_top">主页</a></li>
				<li><i class="icon-list"></i><a href="#">基础数据</a></li>
				<li class="active">产品管理</li>
			</ul>
			<!-- .breadcrumb -->
			<!-- #nav-search -->
		</div>
		<div class="page-content">
			<div class="page-header">
				<h1>
					产品管理 <small> <i class="icon-double-angle-right"></i> <s:if
							test="id==null">
添加产品</s:if> <s:else>
编辑产品</s:else>
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
		</div>

		<div class="page-content">

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->

					<s:form id="productForm" action="product_save"
						cssClass="form-horizontal" role="form"
						enctype="multipart/form-data">
						<s:hidden name="id" id="id"/>
						<s:hidden name="baseQuery.currentPage" />
						<s:hidden name="baseQuery.pageSize" />
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1"> 名称 </label>
							<div class="col-sm-9">
								<s:textfield name="name" placeholder="产品名称"
									cssClass="col-xs-10 col-sm-5" id="name" />
								<font size="5px" color="red">&nbsp; * &emsp; </font>
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-input-readonly">颜色</label>
							<div class="col-sm-9">
								<s:select id="simple-colorpicker-1" name="color"
									list="{'#000000','#FFFFFF','#ac725e','#d06b64','#f83a22','#fa573c','#ff7537','#ffad46','#42d692','#16a765','#7bd148','#b3dc6c','#fbe983','#fad165','#92e1c0','#9fe1e7','#9fc6e7','#4986e7','#9a9cff','#b99aff','#c2c2c2','#cabdbf','#cca6ac','#f691b2','#cd74e6'}" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">成本价格</label>
							<div class="col-sm-9">
								<s:textfield name="costPrice" placeholder="成本价格"
									cssClass="col-xs-10 col-sm-5 input-medium input-mask-eyescript" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-1">销售价格</label>
							<div class="col-sm-9">
								<s:textfield name="salePrice" placeholder="销售价格"
									cssClass="col-xs-10 col-sm-5 input-medium input-mask-eyescript" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">品牌</label>
							<div class="col-sm-9">
								<s:select list="#allBrands" name="brand.id" listKey="id"
									listValue="name" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">单位</label>
							<div class="col-sm-9">
								<s:select list="#allUnits" name="unit.id" listKey="id"
									listValue="name" />
							</div>
						</div>

						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-4">产品类型</label>
							<div class="col-sm-9">
								<s:select id="parent" list="#allParents" name="type.parent.id" headerKey="-1" headerValue="--请选择--"
									listValue="name" listKey="id" />
								<s:select id="children" list="#allChildrens" name="type.id" listValue="name" listKey="id" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								for="form-field-4">图片</label>
							<div class="col-sm-9" style="width: 35%">
								<input id="id-input-file-2" name="upload" type="file"> <label
									class="file-label" data-title="Choose"> <span
									class="file-name" data-title="No File ..."> </span>
								</label>
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

								&nbsp;&nbsp; &nbsp; <a href="product.action">
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

	<script src="assets/js/ace-elements.min.js"></script>
	<script type="text/javascript">
		$(function() {
			//处理颜色选择框
			$('#simple-colorpicker-1').ace_colorpicker();

			//处理图片上传控件
			$('#id-input-file-2').ace_file_input({
				no_file : '选择图片 ...',
				btn_choose : '选择',
				btn_change : '改变',
				droppable : false,
				onchange : null,
				thumbnail : false
			});
		});
	</script>

</body>
</html>