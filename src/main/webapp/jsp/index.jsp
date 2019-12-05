<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/layui/css/layui.css">
<link rel="stylesheet"
	href="//at.alicdn.com/t/font_tnyc012u2rlwstt9.css" media="all" />
<link rel="stylesheet" href="${ctx }/css/main.css" media="all" />
<script>
	var ctx = "${ctx}";
</script>
</head>
<body class="main_body">
	<div class="layui-layout layui-layout-admin">
		<!-- 顶部 -->
		<div class="layui-header header">
			<div class="layui-main">
				<a href="#" class="logo">和源创后台管理系统</a>
				<!-- 显示/隐藏菜单 -->
				<a href="javascript:;" class="iconfont hideMenu icon-menu1"></a>
				<!-- 顶部右侧菜单 -->
				<ul class="layui-nav top_menu">
					<li class="layui-nav-item"><a href="${ctx }/user/logout.do"
						class="signOut"><i class="iconfont icon-loginout"></i><cite>退出</cite></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 左侧导航 -->
		<div class="layui-side layui-bg-black">
			<div class="navBar">
				<ul class="layui-nav layui-nav-tree">
					<li class="layui-nav-item">
						<a href="javascript:;">
							<cite>我的面板</cite>
							<span class="layui-nav-more"></span>
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" data-url="userInfo.jsp">
									<cite>个人信息</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="userInfo.jsp">
									<cite>我的分润</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="updateUserInfo.jsp">
									<cite>更新信息</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="changepass.jsp">
									<cite>修改密码</cite>
								</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<cite>业务员管理</cite>
							<span class="layui-nav-more"></span>
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" data-url="userlist.jsp">
									<cite>业务员列表</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="adduser.jsp">
									<cite>添加业务员</cite>
								</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<cite>商家管理</cite>
							<span class="layui-nav-more"></span>
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" data-url="shopperList.jsp">
									<cite>商家列表</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="addShopper.jsp">
									<cite>添加商家</cite>
								</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<cite>流水查询</cite>
							<span class="layui-nav-more"></span>
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" data-url="orderList.jsp">
									<cite>时间查询</cite>
								</a>
							</dd>
							<dd>
								<a href="javascript:;" data-url="orderInfoMonth.html">
									<cite>当月查询</cite>
								</a>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a href="javascript:;">
							<cite>广告管理</cite>
							<span class="layui-nav-more"></span>
						</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" data-url="adver.jsp">
									<cite>申请投放</cite>
								</a>
							</dd>
						</dl>
					</li>
				</ul>
            </div>
		</div>
		<!-- 右侧内容 -->
		<div class="layui-body layui-form">
			<div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
				<ul class="layui-tab-title top_tab" id="top_tabs">
					<li class="layui-this" lay-id=""><i class="iconfont icon-computer"></i><cite>后台首页</cite></li>
				</ul>
				<!-- 当前页面操作 -->
				<ul class="layui-nav closeBox">
					<li class="layui-nav-item">
						<a href="javascript:;"><i class="iconfont icon-caozuo"></i> 页面操作</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a>
							</dd>
							<dd>
								<a href="javascript:;" class="closePageOther"><i class="iconfont icon-prohibit"></i> 关闭其他</a>
							</dd>
							<dd>
								<a href="javascript:;" class="closePageAll"><i class="iconfont icon-guanbi"></i> 关闭全部</a>
							</dd>
						</dl>
					 </li>
				</ul>
				<!-- 中间内容区域 -->
				<div class="layui-tab-content clildFrame">
					<div class="layui-tab-item layui-show">
						<iframe src="${ctx }/jsp/main.jsp"></iframe>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx }/layui/layui.js"></script>
	<!--<script type="text/javascript" src="${ctx }/js/leftNav.js"></script>-->
	<script type="text/javascript" src="${ctx }/js/index.js"></script>
</body>
</html>