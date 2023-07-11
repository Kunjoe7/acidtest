<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function actestManagerDelete(actestManagerId) {
		if(confirm("您确定要删除这个核酸管理员吗？")) {
			window.location="ActestManager?action=delete&ActestManagerId="+actestManagerId;
		}
	}
	
	$(document).ready(function(){
		$("ul li:eq(1)").addClass("active");
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
				null,
				null,
				null,
				null,
				{ "asSorting": [ ] }
			]
		});
	});
</script>
<div class="data_list">
		<div class="data_list_title">
			核酸管理员管理
		</div>
		<form name="myForm" class="form-search" method="post" action="ActestManager?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='ActestManager?action=preSave'">添加</button>
				<span class="data_search">
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="userName" ${searchType eq "userName"?'selected':'' }>用户名</option>
					</select>
					&nbsp;<input id="s_ActestManagerText" name="s_ActestManagerText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_ActestManagerText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered dataTable">
				<thead>
					<tr>
						<th>编号</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>性别</th>
						<th>电话</th>
						<th>核酸地点</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach  varStatus="i" var="actestManager" items="${ActestManagerList }">
						<tr>
							<td>${i.count+(page-1)*pageSize }</td>
							<td>${actestManager.userName }</td>
							<td>${actestManager.name }</td>
							<td>${actestManager.sex }</td>
							<td>${actestManager.tel }</td>
							<td>${actestManager.actestPlaceName==null?"无":actestManager.actestPlaceName }</td>
							<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='ActestManager?action=preSave&actestManagerId=${actestManager.actestManagerId }'">修改</button>&nbsp;
								<button class="btn btn-mini btn-danger" type="button" onclick="actestManagerDelete(${actestManager.actestManagerId})">删除</button></td>
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