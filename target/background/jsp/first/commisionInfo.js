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
            $.ajax({
                url: ctx + "/user/get_commision_account.do",
                type:"post",
                data:{
                    startTime:startTime,
                    endTime:endTime
                },
                success:function(res){
                    document.getElementById("totalAccount").value = res.data.totalAccount;
                    document.getElementById("zfbAccount").value = res.data.zfbAccount;
                    document.getElementById("wxAccount").value = res.data.wxAccount;
                    document.getElementById("ysfAccount").value = res.data.ysfAccount;

                    document.getElementById("totalCommision").value = res.data.totalCommision;
                    document.getElementById("zfbCommision").value = res.data.zfbCommision;
                    document.getElementById("wxCommision").value = res.data.wxCommision;
                    document.getElementById("ysfCommision").value = res.data.ysfCommision;
                },
                error:function(){
                    console.log("错误");
                }
            })
        }

    };

    $.ajax({
        url: ctx + "/user/get_commision_account.do",
        type:"post",
        data:{
            startTime:startTime,
            endTime:endTime
        },
        success:function(res){
            document.getElementById("totalAccount").value = res.data.totalAccount;
            document.getElementById("zfbAccount").value = res.data.zfbAccount;
            document.getElementById("wxAccount").value = res.data.wxAccount;
            document.getElementById("ysfAccount").value = res.data.ysfAccount;

            document.getElementById("totalCommision").value = res.data.totalCommision;
            document.getElementById("zfbCommision").value = res.data.zfbCommision;
            document.getElementById("wxCommision").value = res.data.wxCommision;
            document.getElementById("ysfCommision").value = res.data.ysfCommision;
        },
        error:function(){
            console.log("错误");
        }
    })


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