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
	<title>添加商户</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css" />
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
					<label class="layui-form-label">商店名称：</label>
					<div class="layui-input-block">
						<input id = "shoppername" type="text" name="shoppername" autocomplete="off" class="layui-input" placeholder="请输入商店名称">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">联系电话：</label>
					<div class="layui-input-block">
						<input id = "phone" type="text" name="phone" autocomplete="off" class="layui-input" placeholder="请输入联系电话">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">商店地址：</label>
					<div class="layui-input-block">
						<input  id="address" type="text" name="address" autocomplete="off" class="layui-input" placeholder="请输入商店地址">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">营业执照：</label>
					<div class="layui-input-block">
						<input  id="bussinessLicense" type="text" name="bussinessLicense" autocomplete="off" class="layui-input" placeholder="请输入营业执照号码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">支付宝账号：</label>
					<div class="layui-input-block">
						<input  id="zfbId" type="text" name="zfbId" autocomplete="off" class="layui-input" placeholder="请输入支付宝账号">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">微信账号：</label>
					<div class="layui-input-block">
						<input  id="wxId" type="text" name="wxId" autocomplete="off" class="layui-input" placeholder="请输入微信账号">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">云闪付账号：</label>
					<div class="layui-input-block">
						<input  id="ysfId" type="text" name="ysfId" autocomplete="off" class="layui-input" placeholder="请输入云闪付账号">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">授权码：</label>
					<div class="layui-input-block">
						<input  id="authcode" type="text" name="authcode" autocomplete="off" class="layui-input" placeholder="请输入授权码">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备ID：</label>
					<div class="layui-input-block">
						<input  id="deviceId" type="text" name="deviceId" autocomplete="off" class="layui-input" placeholder="请输入设备ID">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备KEY：</label>
					<div class="layui-input-block">
						<input  id="deviceKey" type="text" name="deviceKey" autocomplete="off" class="layui-input" placeholder="请输入设备KEY">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备类型：</label>
					<div class="layui-input-block">
						<input  id="deviceType" type="text" name="deviceType" autocomplete="off" class="layui-input" placeholder="请输入设备类型">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">设备激活码：</label>
					<div class="layui-input-block">
						<input  id="activeCode" type="text" name="activeCode" autocomplete="off" class="layui-input" placeholder="请输入设备激活码">
					</div>
				</div>

				<div class="layui-input-inline">
					<button id="addShopperSubmitBt" class="layui-btn">提交</button>
				</div>
			</form>
		</div>
	</div>
</div>
</body>


<script type="text/javascript">
	$(function() {
		$('#addShopperSubmitBt').on('click', function() {
			var shoppername = $('#shoppername').val();
			var phone = $('#phone').val();
			var address = $('#address').val();
			var bussinessLicense = $('#bussinessLicense').val();
			var zfbId = $('#zfbId').val();
			var wxId = $('#wxId').val();
			var ysfId = $('#ysfId').val();
			var authcode = $('#authcode').val();
			var deviceId = $('#deviceId').val();
			var deviceKey = $('#deviceKey').val();
			var deviceType = $('#deviceType').val();
			var activeCode = $('#activeCode').val();

			$.ajax({
				url: ctx + "/user/addShopper.do",
				type:"post",
				dataType:"json",
				async:false,
				data:{
					shoppername:shoppername,
					phone:phone,
					address:address,
					bussinessLicense:bussinessLicense,
					zfbId:zfbId,
					wxId:wxId,
					ysfId:ysfId,
					authcode:authcode,
					deviceId:deviceId,
					deviceKey:deviceKey,
					deviceType:deviceType,
					activeCode:activeCode
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