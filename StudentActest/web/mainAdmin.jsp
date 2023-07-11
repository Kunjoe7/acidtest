<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>学生核酸管理系统</title>

<link href="resources/style/Actest.css" rel="stylesheet">
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="resources/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link href="resources/style/dataTables.bootstrap.css" rel="stylesheet">
<link href="resources/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<script src="resources/bootstrap/js/bootstrap.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
   	 	$("#DataTables_Table_0_wrapper .row-fluid").remove();
	});
</script>
<style type="text/css">
	body{
		background-image: linear-gradient(to right, #c4d274, #6bc2e7);
	}
	.bs-docs-sidenav {
		background-image: linear-gradient(to right, #c4d274, #6bc2e7);
		border-radius: 6px;
		box-shadow: 0 1px 4px rgba(0, 0, 0, 0.067);
		padding: 0;
		width: 228px;
	}

	.bs-docs-sidenav > li > a {
		border: 1px solid #e5e5e5;
		display: block;
		padding: 8px 14px;
		margin: 0 0 -1px;
	}
	.bs-docs-sidenav > li:first-child > a {
		border-radius: 6px 6px 0 0;
	}
	.bs-docs-sidenav > li:last-child > a {
		border-radius: 0 0 6px 6px;
	}
	.bs-docs-sidenav > .active > a {
		border: 0 none;
		box-shadow: 1px 0 0 rgba(0, 0, 0, 0.1) inset, -1px 0 0 rgba(0, 0, 0, 0.1) inset;
		padding: 9px 15px;
		position: relative;
		text-shadow: 0 1px 0 rgba(0, 0, 0, 0.15);
		z-index: 2;
	}
	.bs-docs-sidenav .icon-chevron-right {
		float: right;
		margin-right: -6px;
		margin-top: 2px;
		opacity: 0.25;
	}
	.bs-docs-sidenav > li > a:hover {
		background-color: rgba(245, 245, 245, 0);
	}
	.bs-docs-sidenav a:hover .icon-chevron-right {
		opacity: 0.5;
	}
	.bs-docs-sidenav .active .icon-chevron-right, .bs-docs-sidenav .active a:hover .icon-chevron-right {
		opacity: 1;
	}
</style>

</head>
<body>
<div class="container-fluid" style="padding-right: 0px;padding-left: 0px;">
	<div region="north" style="height: 100px;">
		<div align="left" style="width: 80%;height:100px ;float: left;padding-top: 40px;padding-left: 30px;" ><span style=" font-size: 3em;" >核酸管理系统</span></div>
		<div style="padding-top: 70px;padding-right: 20px;">当前用户：&nbsp;<b style=" font-size: 1.5em;" >${currentUser.name }</b></div>
	</div>
</div>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span2 bs-docs-sidebar" >
			<ul class="nav nav-list bs-docs-sidenav">
				<li><a href="blank"><i class="icon-chevron-right"></i>首页</a></li>
				<li><a href="ActestManager?action=list"><i class="icon-chevron-right"></i>核酸管理员管理</a></li>
				<li><a href="student?action=list"><i class="icon-chevron-right"></i>学生管理</a></li>
				<li><a href="ActestPlace?action=list"><i class="icon-chevron-right"></i>核酸地点管理</a></li>
				<li><a href="record?action=list"><i class="icon-chevron-right"></i>核酸情况</a></li>
				<li><a href="password?action=preChange"><i class="icon-chevron-right"></i>修改密码</a></li>
				<li><a href="login.jsp"><i class="icon-chevron-right"></i>退出系统</a></li>
			</ul>
		</div>
		<div class="span10">
			<jsp:include page="${mainPage==null?'admin/blank.jsp':mainPage}"></jsp:include>
		</div>
	</div>
</div>
</body>
</html>