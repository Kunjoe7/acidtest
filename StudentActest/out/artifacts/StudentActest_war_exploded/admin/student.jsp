<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">

$(document).ready(function(){
	$("ul li:eq(2)").addClass("active");
	$('.dataTable').dataTable( {
        "oLanguage": {
            "sProcessing":   "处理中...",
            "sLengthMenu":   "_MENU_ 记录/页",
            "sZeroRecords":  "没有匹配的记录",
            "sInfo":         "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
            "sInfoEmpty":    "显示第 0 至 0 项记录，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项记录过滤)",
            "sInfoPostFix":  "",
            "sSearch":       "查找（所有信息）:",
            "sUrl":          ""
        },

        "bPaginate": false ,//该参数为是否显示分页
		"bLengthChange": false, //改变每页显示数据数量
        "bStateSave": true,
		"bFilter": true, //过滤功能
		"aoColumns": [
			null,
			null,
			null,
			null,
			null,
            {"asSorting":[]},
			{ "asSorting": [ ] }
		]
	});
});

window.onload = function(){ 
	$("#DataTables_Table_0_wrapper .row-fluid").remove();
};
	function studentDelete(studentId) {
		if(confirm("您确定要删除这个学生吗？")) {
			window.location="student?action=delete&studentId="+studentId;
		}
	}
</script>
<style type="text/css">
	.span6 {
		width:0px;
		height: 0px;
		padding-top: 0px;
		padding-bottom: 0px;
		margin-top: 0px;
		margin-bottom: 0px;
	}

</style>
<div class="data_list">
		<div class="data_list_title">
			学生管理
		</div>
		<form name="myForm" class="form-search" method="post" action="student?action=search" style="padding-bottom: 0px">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='student?action=preSave'">添加</button>
				<span class="data_search">
					<select id="PlaceToSelect" name="PlaceToSelect" style="width: 110px;">
					<option value="">全部核酸地点</option>
					<c:forEach var="actestPlace" items="${actestPlaceList }">
						<option value="${actestPlace.actestPlaceId }" ${PlaceToSelect==actestPlace.actestPlaceId?'selected':'' }>${actestPlace.actestPlaceName }</option>
					</c:forEach>
					</select>
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="number" ${searchType eq "number"?'selected':'' }>学号</option>
					<option value="actest" ${searchType eq "actest"?'selected':'' }>核酸情况</option>
					</select>
					&nbsp;<input id="s_studentText" name="s_studentText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_studentText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover dataTable" >
				<thead>
					<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>核酸地点</th>
					<th>核酸情况</th>
					<th>电话</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach  varStatus="i" var="student" items="${studentList }">
					<tr>
						<td>${student.userName }</td>
						<td>${student.name }</td>
						<td>${student.sex }</td>
						<td>${student.actestPlaceName==null?"无":student.actestPlaceName }</td>
						<td>${student.actestStatus }</td>
						<td>${student.tel }</td>
						<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='student?action=preSave&studentId=${student.studentId }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="studentDelete(${student.studentId })">删除</button></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><span style="color:red;">${error }</span></div>
		<div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div>
</div>