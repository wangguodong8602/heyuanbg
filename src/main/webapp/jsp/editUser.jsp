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
		<input type="hidden" name="id" value="${user.id }" id="id" />
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<input type="text" id="username" class="layui-input"
					name="username" value="${user.username }"
					disabled="disabled">
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-form-item">
				<label class="layui-form-label">激活码</label>
				<div class="layui-input-block">
					<input type="text" id="parentId" name="phone" class="layui-input"
						   lay-verify="required" placeholder="请输入激活码" value="${user.parentId }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">联系电话</label>
				<div class="layui-input-block">
					<input type="text" id="phone" name="phone" class="layui-input"
						   lay-verify="required" placeholder="请输入电话" value="${user.phone }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">银行卡号</label>
				<div class="layui-input-block">
					<input type="text" id="bankId" name="bankId" class="layui-input"
						   lay-verify="required" placeholder="请输入银行卡号" value="${user.bankId }">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">分润比率</label>
				<div class="layui-input-block">
					<input type="text" id="rate" name="rate" class="layui-input"
						   lay-verify="required" placeholder="请输入分润比率" value="${user.rate }">
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="updateUser">立即保存</button>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx }/jsp/editUser.js"></script>
</body>
</html>