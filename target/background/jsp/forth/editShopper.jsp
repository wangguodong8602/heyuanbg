<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
<script>
	var ctx = "${ctx}";
</script>
</head>
<body>
	<form class="layui-form" style="width: 80%;">
		<!-- 管理员id -->
		<input type="hidden" name="id" value="${shopper.id }" id="id" />
		<div class="layui-form-item">
			<label class="layui-form-label">店铺名称</label>
			<div class="layui-input-block">
				<input type="text" id="shoppername" class="layui-input"
					name="shoppername" value="${shopper.shoppername }"
					disabled="disabled">
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-form-item">
				<label class="layui-form-label">联系电话</label>
				<div class="layui-input-block">
					<input type="text" id="phone" name="phone" class="layui-input"
						   lay-verify="required" placeholder="请输入电话" value="${shopper.phone }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">支付宝账号</label>
				<div class="layui-input-block">
					<input type="text" id="zfbId" name="zfbId" class="layui-input"
						   lay-verify="required" placeholder="请输入支付宝账号" value="${shopper.zfbId }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">微信账号</label>
				<div class="layui-input-block">
					<input type="text" id="wxId" name="wxId" class="layui-input"
						   lay-verify="required" placeholder="请输入微信账号" value="${shopper.wxId }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">云闪付账号</label>
				<div class="layui-input-block">
					<input type="text" id="ysfId" name="ysfId" class="layui-input"
						   lay-verify="required" placeholder="请输入云闪付账号" value="${shopper.ysfId }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">授权码</label>
				<div class="layui-input-block">
					<input type="text" id="authcode" name="authcode" class="layui-input"
						   lay-verify="required" placeholder="请输入授权码" value="${shopper.authcode }">
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="updateShopper">立即保存</button>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx }/jsp/forth/editShopper.js"></script>
</body>
</html>