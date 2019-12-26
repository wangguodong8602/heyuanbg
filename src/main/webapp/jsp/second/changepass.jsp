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

			<form class="layui-form" style="width: 90%;padding-top: 20px;">
				<div class="layui-form-item">
					<label class="layui-form-label">旧密码：</label>
					<div class="layui-input-block">
						<input id = "passwordOld" type="password" name="passwordOld" autocomplete="off" class="layui-input" placeholder="请输入旧密码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">新密码：</label>
					<div class="layui-input-block">
						<input id = "passwordNew1" type="password" name="passwordNew1" autocomplete="off" class="layui-input" placeholder="请输入新密码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">新密码：</label>
					<div class="layui-input-block">
						<input  id="passwordNew2" type="password" name="passwordNew2" autocomplete="off" class="layui-input" placeholder="请再次输入新密码">
					</div>
				</div>

				<div class="layui-input-inline">
					<button id="changePassSubmitBt" class="layui-btn">提交修改</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx }/layui/layui.js"></script>

<script type="text/javascript">
	$(function() {
		$('#changePassSubmitBt').on('click', function() {
			var passwordOld = $('#passwordOld').val();
			var passwordNew1 = $('#passwordNew1').val();
			var passwordNew2 = $('#passwordNew2').val();
			$.ajax({
				url:ctx + "/user/reset_password.do",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					passwordOld:passwordOld,
					passwordNew1:passwordNew1,
					passwordNew2:passwordNew2
				},
				success:function(result){
					if(result.code == 0){
						alert(result.msg);
					}else{
						$("#passwordOld").val('');
						$("#passwordNew1").val('');
						$("#passwordNew2").val('');
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