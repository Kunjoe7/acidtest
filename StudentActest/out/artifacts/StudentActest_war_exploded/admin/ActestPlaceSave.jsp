<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function checkForm(){
		var actestPlaceName=document.getElementById("ActestPlaceName").value;
		if(actestPlaceName==null||actestPlaceName==""){
			document.getElementById("error").innerHTML="名称不能为空！";
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		$("ul li:eq(3)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${ActestPlace.actestPlaceId!=null }">
				修改核酸地点
			</c:when>
			<c:otherwise>
				添加核酸地点
			</c:otherwise>
		</c:choose>
		</div>
		<form action="ActestPlace?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id=ActestPlaceId" name="ActestPlaceId" value="${ActestPlace.actestPlaceId }"/>
					<table align="center">
						<tr>
							<td><font color="red">*</font>名称：</td>
							<td><input type="text" id="ActestPlaceName"  name="ActestPlaceName" value="${ActestPlace.actestPlaceName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td>&nbsp;简介：</td>
							<td><textarea id="detail" name="detail" rows="10">${ActestPlace.detail }</textarea></td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
					<div align="center">
						<font id="error" color="red">${error }</font>
					</div>
			</div>
		</form>
</div>