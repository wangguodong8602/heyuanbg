<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<body>
<h2>广告投放文件上传</h2>

上传图片文件
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="图片文件上传">
</form>


上传视频文件
<form name="form1" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="视频文件上传">
</form>

</body>
</html>
