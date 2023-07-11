<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function actestPlaceDelete(actestPlaceId) {
		if(confirm("您确定要删除这个核酸地点吗？")) {
			window.location="ActestPlace?action=delete&ActestPlaceId="+actestPlaceId;
		}
	}
	$(document).ready(function(){
		$("ul li:eq(3)").addClass("active");
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
			},

			"bPaginate": false,//该参数为是否显示分页
			"bLengthChange": false, //改变每页显示数据数量
			"bStateSave": true,
			"bFilter": true, //过滤功能
			"aoColumns": [
				null,
				null,
				{ "asSorting": [ ] },
				{ "asSorting": [ ] }
			]
		});
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			核酸地点管理
		</div>
		<form name="myForm" class="form-search" method="post" action="ActestPlace?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='ActestPlace?action=preSave'">添加</button>
				<span class="data_search">
					地点:&nbsp;&nbsp;<input id="s_ActestPlaceName" name="s_ActestPlaceName" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_ActestPlaceName }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-striped table-bordered table-hover dataTable">
				<thead>
					<tr>
						<th width="15%">编号</th>
						<th>地点</th>
						<th>简介</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach  varStatus="i" var="actestPlace" items="${ActestPlaceList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${actestPlace.actestPlaceName }</td>
						<td>${actestPlace.detail==null||actestPlace.detail==""?"无":actestPlace.detail }</td>
						<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='ActestPlace?action=manager&actestPlaceId=${actestPlace.actestPlaceId }'">管理员</button>&nbsp;
							<button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='ActestPlace?action=preSave&actestPlaceId=${actestPlace.actestPlaceId }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="actestPlaceDelete(${actestPlace.actestPlaceId})">删除</button></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div>
</div>