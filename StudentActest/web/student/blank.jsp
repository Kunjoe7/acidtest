<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<script type="text/javascript">
		$(document).ready(function(){
			$("ul li:eq(0)").addClass("active");
		});
	</script>

	<div style="padding-top: 100px;padding-left: 100px;">
		<span style="color:gray; font-size:3em;">欢迎您,<b >${currentUser.name }</b>同学!</span>

	</div>