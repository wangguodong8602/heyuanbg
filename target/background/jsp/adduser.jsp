<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>添加用户</title>
	<link rel="stylesheet" type="text/css" href="../static/admin/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="../static/admin/css/admin.css" />
	<script>
		var ctx = "${ctx}";
	</script>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
</head>

<body>
<div class="layui-tab page-content-wrap">

	<div class="layui-tab-content">
		<!--站点配置-->
		<div class="layui-tab-item layui-show">

			<form class="layui-form" style="width: 90%;padding-top: 20px;">
				<div class="layui-form-item">
					<label class="layui-form-label">用户名称：</label>
					<div class="layui-input-block">
						<input id = "usernameID" type="text" name="username" autocomplete="off" class="layui-input" placeholder="请输入登录名">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">真实姓名：</label>
					<div class="layui-input-block">
						<input id = "realname" type="text" name="realname" autocomplete="off" class="layui-input" placeholder="请输入真实姓名">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">登录密码：</label>
					<div class="layui-input-block">
						<input  id="passwordID" type="text" name="password" autocomplete="off" class="layui-input" placeholder="请输入登录密码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">角色信息：</label>
					<div class="layui-input-block">
						<select name="roleInfo" id="roleInfoID" lay-verify="required">
							<option value="">请选择用户角色</option>
							<option value="21">一级代理员</option>
							<option value="22">二级代理员</option>
							<option value="23">三级代理员</option>
							<option value="24">商家</option>
						</select>
					</div>
				</div>
				<div class="layui-input-inline">
					<button id="addUserSubmitBt" class="layui-btn">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>

<script src="adduser.js"></script>   <!--用来显示下拉框-->
<script type="text/javascript">
	$(function() {
		$('#addUserSubmitBt').on('click', function() {
			var username = $('#usernameID').val();
			var password = $('#passwordID').val();
			var parentId = "";
			var realname = $('#realname').val();;
			var phone = "";
			var identityId = "";
			var bankId = "";
			var question = "我的用户名";
			var answer = username;
			var role;
			var rate=0;
			if($('#roleInfoID').val() == 21){
				role = 1;
			}else if($('#roleInfoID').val() == 22){
				role = 2;
			}else if($('#roleInfoID').val() == 23){
				role = 3;
			} else if($('#roleInfoID').val() == 24){
				role = 4;
			}
			$.ajax({
				url:ctx + "/user/register.do",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					parentId:parentId,
					username:username,
					password:password,
					phone:phone,
					identityId:identityId,
					bankId:bankId,
					question:question,
					answer:answer,
					role:role,
					realname:realname,
					rate:rate
				},
				success:function(result){
					if(result.code == 0){
						alert(result.msg);
					}else{
						alert(result.msg);
					}
				},
				error:function(){
					alert("添加失败！");
				}

			})
		})
	})


</script>

</html>