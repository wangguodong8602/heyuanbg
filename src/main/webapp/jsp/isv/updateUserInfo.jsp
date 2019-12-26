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
	<title>用户信息</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css" />
	<script>
		var ctx = "${ctx}";
	</script>
</head>

<body>
<div class="layui-tab page-content-wrap">

	<div class="layui-tab-content">
		<!--站点配置-->
		<div class="layui-tab-item layui-show">
			<form class="layui-form layui-form-pane" style="width: 90%;padding-top: 20px;">
				<div class="layui-form-item">
					<label class="layui-form-label">用户ID：</label>
					<div class="layui-input-block">
						<input id = "id" type="text" name="id" autocomplete="off" class="layui-input" disabled="disabled" value="userid">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">用户名称：</label>
					<div class="layui-input-block">
						<input id = "username" type="text" name="username" autocomplete="off" class="layui-input" disabled="disabled" value="username">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">激活码：</label>
					<div class="layui-input-block">
						<input id = "parentId" type="text" name="parentId" autocomplete="off" class="layui-input" disabled="disabled" value="activeid">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">真实姓名：</label>
					<div class="layui-input-block">
						<input id = "realname" type="text" name="realname" autocomplete="off" class="layui-input" disabled="disabled" value="realname">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">联系电话：</label>
					<div class="layui-input-block">
						<input  id="phone" type="text" name="phone" autocomplete="off" class="layui-input" value="phone">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">身份证号：</label>
					<div class="layui-input-block">
						<input id="identityId" type="text" name="identityId" autocomplete="off" class="layui-input" value="identityId">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">银行卡号：</label>
					<div class="layui-input-block">
						<input id="bankId" type="text" name="bankId" autocomplete="off" class="layui-input" value="bankId">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">角色信息：</label>
					<div class="layui-input-block">
						<input id="role" type="text" name="role" autocomplete="off" class="layui-input" disabled="disabled" value="role">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">分润比率：</label>
					<div class="layui-input-block">
						<input id = "rate" type="text" name="rate" autocomplete="off" class="layui-input" disabled="disabled" value="rate">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">密码问题：</label>
					<div class="layui-input-block">
						<input id="question" type="text" name="question" autocomplete="off" class="layui-input" value="question">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">密码答案：</label>
					<div class="layui-input-block">
						<input id="answer" type="text" name="answer" autocomplete="off" class="layui-input" value="答案">
					</div>
				</div>
				<div class="layui-input-inline">
					<button id="updateUserInfoSubmitBt" class="layui-btn">提交修改</button>
				</div>
			</form>>
		</div>
	</div>
</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
	$.ajax({
		url:ctx + "/user/get_user_info.do",
		type:"post",
		data:{},
		success:function(res){
			document.getElementById("id").value = res.data.id;
			document.getElementById("username").value = res.data.username;
			document.getElementById("realname").value = res.data.realname;
			document.getElementById("parentId").value = res.data.parentId;
			document.getElementById("phone").value = res.data.phone;
			document.getElementById("identityId").value = res.data.identityId;
			document.getElementById("bankId").value = res.data.bankId;
			document.getElementById("question").value = res.data.question;
			document.getElementById("answer").value = res.data.answer;
			if(res.data.role == 0){
				document.getElementById("role").value = "服务商";
			}else if(res.data.role == 1){
				document.getElementById("role").value = "一级代理商";
			}else if(res.data.role == 2){
				document.getElementById("role").value = "二级代理商";
			}else if(res.data.role == 3){
				document.getElementById("role").value = "三级代理商";
			}else{
				document.getElementById("role").value = "商户";
			}
			document.getElementById("rate").value = res.data.rate;
		},
		error:function(){
			console.log("错误");
		}

	})
</script>

<script>
	$(function() {
		$('#updateUserInfoSubmitBt').on('click', function() {
			var username = $('#username').val();
			var realname = $('#realname').val();
			var parentId = $('#parentId').val();
			var phone = $('#phone').val();
			var identityId = $('#identityId').val();
			var bankId = $('#bankId').val();
			var question = $('#question').val();
			var answer = $('#answer').val();
			var rate = $('#rate').val();

			$.ajax({
				url:ctx + "/user/update_information.do",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					username:username,
					parentId:parentId,
					realname:realname,
					phone:phone,
					identityId:identityId,
					bankId:bankId,
					question:question,
					answer:answer,
					rate:rate
				},
				success:function(result){
					if(result.code == 0){
						alert(result.msg);
						window.location.href=ctx + '/login.jsp';
					}else{
						alert(result.msg)
					}

				},
				error:function(){
					alert("修改失败！");
				}

			})
		})
	})
</script>


</html>