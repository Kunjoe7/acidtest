<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
$(document).ready(function(){
	$("ul li:eq(1)").addClass("active");
	$('.form_date').datetimepicker({
	    language:  'en',
	    weekStart: 1,
	    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
	});
	$('.datatable').dataTable( {
		"oLanguage": {
			"sProcessing":   "处理中...",
			"sLengthMenu":   "_MENU_ 记录/页",
			"sZeroRecords":  "没有匹配的记录",
			"sInfo":         "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
			"sInfoEmpty":    "显示第 0 至 0 项记录，共 0 项",
			"sInfoFiltered": "(由 _MAX_ 项记录过滤)",
			"sInfoPostFix":  "",
			"sSearch":       "查找（所有信息）:",
		},
		"bPaginate": false ,//该参数为是否显示分页
		"bLengthChange": false, //改变每页显示数据数量
		"bFilter": true, //过滤功能
		"aoColumns": [
			null,
			null,
			null,
			null,
			null,
			{ "asSorting": [ ] },
			{ "asSorting": [ ] }
		]
	});
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
});

window.onload = function(){ 
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
};
</script>

<div class="data_list">
		<div class="data_list_title">
			核酸情况
		</div>
		<form name="myForm" class="form-search" method="post" action="record?action=search" style="padding-bottom: 0px">
				<span class="data_search">
					<span class="controls input-append date form_date" style="margin-right: 10px" data-date="" data-date-format="yyyy-mm-dd" data-link-format="yyyy-mm-dd">
                    	<input id="startDate" name="startDate" style="width:120px;height: 30px;" placeholder="起始日期" type="text" value="${startDate }" readonly >
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		</span>
					<span class="controls input-append date form_date" style="margin-right: 10px" data-date="" data-date-format="yyyy-mm-dd" data-link-format="yyyy-mm-dd">
                    	<input id="endDate" name="endDate" style="width:120px;height: 30px;" placeholder="终止日期" type="text" value="${endDate }" readonly>
                    	<span class="add-on"><i class="icon-remove"></i></span>
						<span class="add-on"><i class="icon-th"></i></span>
               		</span>
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover datatable">
				<thead>
					<tr>
					<th>日期</th>
					<th>学号</th>
					<th>姓名</th>
					<th>核酸地点</th>
					<th>核酸情况</th>
					<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach  varStatus="i" var="record" items="${recordList }">
					<tr>
						<td>${record.date }</td>
						<td>${record.studentNumber }</td>
						<td>${record.studentName }</td>
						<td>${record.actestPlaceName==null?"无":record.actestPlaceName }</td>
						<td>${record.actestStatus }</td>
						<td>${record.detail }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
</div>