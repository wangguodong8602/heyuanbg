<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${ctx }/layui/css/layui.css">
    <link rel="stylesheet" href="${ctx }/css/style.css">
    <script>
        var ctx = "${ctx}";
    </script>

    <style>
        #divId{
            width: 100%;
            height: 100%;
            background: url(${ctx}/image/back_10.jpg) bottom center no-repeat #efeff4;
            background-size: 100% 100%;
        }
    </style>

</head>
<body>
    <div id="divId" align="center">
        <h2>广告投放文件上传</h2>
        <h2>上传图片文件</h2>
        <form name="form1" action="" method="post" enctype="multipart/form-data">
            <input type="file" name="upload_file">
            <input type="submit" value="图片文件上传">
        </form>
        <h2>上传视频文件</h2>
        <form name="form1" action="" method="post" enctype="multipart/form-data">
            <input type="file" name="upload_file">
            <input type="submit" value="视频文件上传">
        </form>
    </div>

</body>
</html>
