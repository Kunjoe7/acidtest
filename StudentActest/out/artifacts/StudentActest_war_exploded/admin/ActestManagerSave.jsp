<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function checkForm(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		var rPassword=document.getElementById("rPassword").value;
		var name=document.getElementById("name").value;
		var sex=document.getElementById("sex").value;
		var tel=document.getElementById("tel").value;
		if(userName==""||password==""||rPassword==""||name==""||sex==""||tel==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		} else if(password!=rPassword){
			document.getElementById("error").innerHTML="密码填写不一致！";
			return false;
		}else {
			var reg= /^1[3|4|5|7|8][0-9]{9}$/;
			if(!reg.test(tel)){
				document.getElementById("error").innerHTML="请输入正确的手机号";
				return false;
			}
			return true;
		}
	}
	
	$(document).ready(function(){
		$("ul li:eq(1)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${ActestManager.actestManagerId!=null }">
				修改管理员
			</c:when>
			<c:otherwise>
				添加管理员
			</c:otherwise>
		</c:choose>
		</div>
		<form action="ActestManager?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="actestManagerId" name="actestManagerId" value="${ActestManager.actestManagerId }"/>
					<table align="center">
						<tr>
							<td><font color="red">*</font>用户名：</td>
							<td><input type="text" id="userName"  name="userName" value="${ActestManager.userName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>密码：</td>
							<td><input type="password" id="password"  name="password" value="${ActestManager.password }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>重复密码：</td>
							<td><input type="password" id="rPassword"  name="rPassword" value="${ActestManager.password }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>姓名：</td>
							<td><input type="text" id="name"  name="name" value="${ActestManager.name }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>性别：</td>
							<td>
								<select id="sex" name="sex" style="width: 90px;">
									<option value="">请选择...</option>
									<option value="男" ${ActestManager.sex eq "男"?'selected':'' }>男</option>
									<option value="女" ${ActestManager.sex eq "女"?'selected':'' }>女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><font color="red">*</font>联系电话：</td>
							<td><input type="text" id="tel"  name="tel" value="${ActestManager.tel }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
					<div align="center">
						<font id="error" color="red">${error } </font>
					</div>
			</div>
		</form>
</div>