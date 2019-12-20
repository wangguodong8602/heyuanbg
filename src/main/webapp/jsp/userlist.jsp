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
</head>
<body class="childrenBody">
	<blockquote class="layui-elem-quote news_search">
		<form class="layui-form">
			<div class="layui-inline">
				搜索用户
				<div class="layui-input-inline">
					<input type="text" id="realname" value="" placeholder="请输入用户名"
						   class="layui-input search_input ">
				</div>
				<div class="layui-input-inline">
					<input type="text" id="role" value="" placeholder="请输入角色"
						   class="layui-input search_input ">
				</div>
				<a class = "layui-btn search_btn" data-type="search" lay-filter="search">搜索/刷新</a>
			</div>

		</form>
	</blockquote>

	<div class="layui-form">
		<table id="userList" lay-filter="userList"></table>
	</div>



	<script type="text/html" id="barDemo">
		<div class="layui-btn-group">
			<button type="button" class="layui-btn layui-btn-sm" lay-event="edit">
				<i class="layui-icon">&#xe642;</i>
			</button>
			<button type="button" class="layui-btn layui-btn-sm" lay-event="delete">
				<i class="layui-icon">&#xe640;</i>
			</button>
			<button type="button" class="layui-btn layui-btn-sm" lay-event="resetpassword">
				<i class="layui-icon">&#x1002;</i>
			</button>
		</div>
    </script>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx }/jsp/userList.js"></script>
</body>

</html>
<!--
<a class="layui-btn layui-btn-sm" lay-event="edit">
	<i class="layui-icon">&#xe642;</i>
</a>
<a class="layui-btn layui-btn-sm" lay-event="delete">
	<i class="layui-icon">&#xe640;</i>
</a>
-->