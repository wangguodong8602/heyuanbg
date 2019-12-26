layui.use([ 'form','layer','jquery','table','laydate'], function() {
    var layer = layui.layer,form = layui.form, $ = layui.jquery, table=layui.table,laydate = layui.laydate;
    var startTime;
    var endTime;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime'
        ,btns: ['clear', 'confirm']
        ,type: 'datetime'
        ,done: function(value, date, endDate){
            startTime = value;
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        }
    });

    laydate.render({
        elem: '#endTime'
        ,btns: ['clear', 'confirm']
        ,type:'datetime'
        ,done: function(value, date, endDate){
            endTime = value;
            console.log(value); //得到日期生成的值，如：2017-08-18
            console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
            console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
        }
    });


    active = {
        search : function(){
            table.reload('noticeList',{
                page : {
                    curr : 1
                },
                where : {
                    startTime:startTime,
                    endTime:endTime
                }
            });
        }

    };

    table.render({
        id:'noticeList'
        ,elem: '#noticeList'
        ,url: ctx+'/user/get_notice_list.do'// 数据接口
        ,method:'post'
        ,limit:10// 每页默认数
        ,limits:[10,20,30,40]
        ,cols: [[
            {field: 'id', title: '序号',align:'center',width:100}
            ,{field: 'noticeTitle', title: '公告标题',align:'center', width:200}
            ,{field: 'createTime', title: '发布时间', align:'center', width:200}
            ,{field: 'right',title: '操作', align:'center', toolbar: "#barDemo"}
        ]]
        ,page: true // 开启分页
        ,loading:true
        ,where: {timestamp: (new Date()).valueOf()}
        ,done:function(res){
                console.log(res);
        }
    });

    table.on('tool(noticeList)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delete') {
            layer.confirm('确定要删除 '+data.noticeTitle+' 么？', function (index) {
                $.ajax({
                    url : ctx + '/user/deleteNoticeById.do',
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
                title : "查看公告",
                area : [ '450px', '600px' ],
                content : ctx+'/user/readNotice.do?id='+data.id,
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