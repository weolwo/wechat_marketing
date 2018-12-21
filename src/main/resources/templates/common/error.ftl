<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>商家后台错误页面</title>
    <link rel='stylesheet' href='../../webjars/bootstrap/3.0.1/css/bootstrap.min.css'>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    错误!
                </h4> <strong>错误提示!</strong>${msg!""} <a href="${url!''}" class="alert-link">3秒后将自动返回</a>
            </div>
        </div>
    </div>
</div>
</body>
    <script>
        setTimeout('location.href="${url}"',3000);//3秒后执行跳转到商品列表页面
    </script>
</html>