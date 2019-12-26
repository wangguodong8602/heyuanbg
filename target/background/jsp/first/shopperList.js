layui.use([ 'form','layer','jquery','table','laydate'], function() {
	var layer = layui.layer,form = layui.form, $ = layui.jquery, table=layui.table,laydate = layui.laydate;
	active = {
			search : function(){
				var hostname = $('#hostname');
				var agentname = $('#agentname');
				table.reload('shopperList',{
					page : {
						curr : 1
					},
					where : {
						hostname : hostname.val(),
						agentname : agentname.val()
					}
				});
			}
	};
	table.render({
		id:'shopperList'
	    ,elem: '#shopperList'
	    ,url: ctx+'/user/get_shopper_list.do'// 数据接口
		,method:'post'
	    ,limit:10// 每页默认数
	    ,limits:[10,20,30,40]
	    ,cols: [[
				{field: 'id', title: 'ID',align:'center',width:100}
				,{field: 'hostname', title: '商家姓名',align:'center', width:90}
				,{field: 'agentname', title: '代理姓名',align:'center', width:90}
				,{field: 'shoppername', title: '店铺名称',align:'center', width:90}
				,{field: 'phone', title: '联系电话',align:'center', width:150}
				,{field: 'zfbId', title: '支付宝账号',align:'center', width:150}
				,{field: 'wxId', title: '微信账号',align:'center', width:150}
				,{field: 'ysfId', title: '云闪付账号',align:'center', width:150}
				,{field: 'address', title: '店铺地址',align:'center', width:100}
				,{field: 'bussinessLicense', title: '营业执照',align:'center', width:150}
				,{field: 'authcode', title: '支付宝授权码',align:'center', width:150}
				,{field: 'createTime', title: '创建时间',align:'center',templet : '<div>{{ formatTime(d.createTime,"yyyy-MM-dd hh:mm:ss")}}</div>', width:160}
				//,{field: 'updateTime', title: '修改时间', align:'center',templet : '<div>{{ formatTime(d.updateTime,"yyyy-MM-dd hh:mm:ss")}}</div>',width:180}
				,{field: 'right',title: '操作', align:'center', toolbar: "#barDemo"}
			]]
			,page: true // 开启分页
			,loading:true
			,where: {timestamp: (new Date()).valueOf()}
	  });

	table.on('tool(shopperList)', function (obj) {
	    var data = obj.data;
	    if (obj.event === 'delete') {
	        layer.confirm('确定要删除 '+data.shoppername+' 么？', function (index) {
	        	$.ajax({
					url : ctx + '/user/deleteShopperById.do',
					type : "POST",
					data: {"id": data.id},
					success : function(d) {
						if (d.code == 0) {
							layer.msg("删除成功！",{icon: 1});
							obj.del();
						} else {
							layer.msg("权限不足，删除失败！", {
								icon : 5
							});
						}
					},
					error:function(){
						layer.msg("删除失败！网络错误！",{icon: 5});
					}
				})
				layer.close(index);
	        });
	    }else if (obj.event === 'edit') {
			var editIndex = layer.open({
				type : 2,
				title : "编辑用户",
				area : [ '450px', '600px' ],
				content : ctx+'/user/editShopper.do?id='+data.id,
				success : function() {

				}
			});
		}
	});

	$(".search_btn").click(function() {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	})

});


// 格式化时间
function formatTime(datetime, fmt) {
	if (datetime == null || datetime == 0) {
		return "";
	}
	if (parseInt(datetime) == datetime) {
		if (datetime.length == 10) {
			datetime = parseInt(datetime) * 1000;
		} else if (datetime.length == 13) {
			datetime = parseInt(datetime);
		}
	}
	datetime = new Date(datetime);
	var o = {
		"M+" : datetime.getMonth() + 1, // 月份
		"d+" : datetime.getDate(), // 日
		"h+" : datetime.getHours(), // 小时
		"m+" : datetime.getMinutes(), // 分
		"s+" : datetime.getSeconds(), // 秒
		"q+" : Math.floor((datetime.getMonth() + 3) / 3), // 季度
		"S" : datetime.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1,
					(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
							.substr(("" + o[k]).length)));
	return fmt;
}
