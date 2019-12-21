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
					<label class="layui-form-label">设备SN号码：</label>
					<div class="layui-input-block">
						<input id = "deviceId" type="text" name="deviceId" autocomplete="off" class="layui-input" placeholder="请输入设备SN号码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备密钥：</label>
					<div class="layui-input-block">
						<input id = "deviceKey" type="text" name="deviceKey" autocomplete="off" class="layui-input" placeholder="请输入设备密钥">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备激活码：</label>
					<div class="layui-input-block">
						<input  id="active_code" type="text" name="active_code" autocomplete="off" class="layui-input" placeholder="请输入设备激活码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备类型：</label>
					<div class="layui-input-block">
						<input  id="device_type" type="text" name="device_type" autocomplete="off" class="layui-input" placeholder="请输入设备类型">
					</div>
				</div>

				<div class="layui-input-inline">
					<button id="addDeviceSubmitBt" class="layui-btn">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>

<script type="text/javascript">
	$(function() {
		$('#addDeviceSubmitBt').on('click', function() {
			var deviceId = $('#deviceId').val();
			var deviceKey = $('#deviceKey').val();
			var device_type = $('#device_type').val();
			var active_code = $('#active_code').val();;
			var userId = "";
			var agentId = "";
			$.ajax({
				url:ctx + "/user/add_device.do",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					deviceId:deviceId,
					deviceKey:deviceKey,
					deviceType:device_type,
					activeCode:active_code,
					userId:userId,
					agentId:agentId
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