<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div class="col-sm-6">
		<div id="sample-table-2_info" class="dataTables_info">
			显示第 ${pageList.begin} 到第<span id="end"> ${pageList.end }</span> 条数据 &emsp;共
			<span id="totalCount">${pageList.totalCount }</span> 条数据
			<s:select name="baseQuery.pageSize" list="{5,10,20}"
				onchange='javascript:$("#domainForm").submit();'></s:select>
			&emsp; 第
			<s:textfield name="baseQuery.currentPage"
				onchange='javascript:$("#domainForm").submit();' size="1"
				id="pageNo" value="%{pageList.currentPage}" />
			页
		</div>
	</div>
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_bootstrap">
			<ul class="pagination">${pageList.page}
			</ul>
		</div>
	</div>
	
	<div class="col-sm-6">
		<div class="dataTables_paginate paging_bootstrap">
			<ul class="pagination">${pageResult.page}
			</ul>
		</div>
	</div>
</div>



<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel" style="color: red">错误提示</h4>
			</div>
			<div class="modal-body" id="myModalError"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
</div>

