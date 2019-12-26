<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
<link rel="stylesheet" href="${ctx }/css/list.css" media="all" />
<link rel="stylesheet" href="${ctx }/css/font_eolqem241z66flxr.css"
	media="all" />
<script>
	var ctx = "${ctx}";
</script>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<form class="layui-form">
			<div class="layui-inline">
				我的佣金信息
				<div class="layui-input-inline">
					<input type="text" id="startTime" value="" placeholder="选择起始日期"
						   class="layui-input search_input ">
				</div>
				<div class="layui-input-inline">
					<input type="text" id="endTime" value="" placeholder="请选择截止日期"
						   class="layui-input search_input ">
				</div>
				<a class = "layui-btn search_btn" data-type="search" lay-filter="search">搜索/刷新</a>
			</div>
		</form>
		<hr class="layui-bg-red">
		<hr class="layui-bg-red">
		<form class="layui-form">
			<div class="layui-row layui-col-space5">
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md12 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							流水总额
						</div>
						<div class="layui-col-md12 layui-inline layui-input-inline">
							<input type="text" id="totalAccount" name="totalAccount" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							支付宝
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="zfbAccount" name="zfbAccount" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							微信
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="wxAccount" name="wxAccount" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							云闪付
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="ysfAccount" name="ysfAccount" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>

			</div>
		</form>
		<hr class="layui-bg-red">
		<hr class="layui-bg-red">
		<form class="layui-form">
			<div class="layui-row layui-col-space5">
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md12 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							佣金总额
						</div>
						<div class="layui-col-md12 layui-inline layui-input-inline">
							<input type="text" id="totalCommision" name="totalCommision" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							支付宝
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="zfbCommision" name="zfbCommision" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							微信
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="wxCommision" name="wxCommision" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="layui-col-md3">
					<div class="layui-row grid-demo layui-form-item layui-form-label" style="font-size: 15px">
						<div class="layui-col-md10 layui-inline">
							<span class="layui-badge-dot layui-bg-green"></span>
							云闪付
						</div>
						<div class="layui-col-md10 layui-inline layui-input-inline">
							<input type="text" id="ysfCommision" name="ysfCommision" class="layui-input" value="0" disabled="disabled">
						</div>
					</div>
				</div>

			</div>
		</form>

	</blockquote>



	<div class="layui-form">

	</div>

	<script type="text/javascript" src="${ctx }/jsp/isv/commisionInfo.js"></script>
</body>

</html>
