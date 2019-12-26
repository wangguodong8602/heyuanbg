layui.use([ 'form','layer','jquery','table','laydate'], function() {
	var layer = layui.layer,form = layui.form, $ = layui.jquery, table=layui.table,laydate = layui.laydate;

	table.render({
		id:'shoppperList'
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
				,{field: 'authcode', title: '授权码',align:'center', width:150}
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


	form.on("submit(updateShopper)",function(){
		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.2});
		var index1 = parent.layer.getFrameIndex(window.name);
		var msg,flag=false;

		var shoppername = $('#shoppername').val();
		var phone = $('#phone').val();
		var zfbId = $('#zfbId').val();
		var wxId = $('#wxId').val();
		var ysfId = $('#ysfId').val();
		var authcode = $('#authcode').val();
		var id = $('#id').val();
		$.ajax({
			type: "post",
			async:false,
			url: ctx+"/user/updateShopper.do",
			data:{
				id:id,
				shoppername:shoppername,
				zfbId:zfbId,
				phone:phone,
				wxId:wxId,
				ysfId:ysfId,
				authcode:authcode,
			},
			dataType:"json",
			success:function(d){
				if(d.code==0){
					msg="用户信息更新成功！";
					flag=true;
				}else{
					msg="用户信息更新失败！";
				}
			},
			error:function(){
				alert("进入错误过程！");
				layer.msg("用户信息更新失败！",{icon:2});
				layer.closeAll();
				return false;
			}

		});
		setTimeout(function () {
			parent.layer.close(index1);
			top.layer.close(index);
			if(flag){
				top.layer.msg(msg,{icon: 1});
			}else{
				top.layer.msg(msg,{icon: 5});
			}
			parent.location.reload();
		}, 2000);
		return false;
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
