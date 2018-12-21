<!DOCTYPE html>
<html lang="en">
 <#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
    <#--侧边栏sidebar-->
      <#include "../common/nav.ftl">
    <#--主要内容区content-->
    <div id="page-content-wrapper">

        <div class="container-fluid" style="margin-left: 50px">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input  type="text" name="categoryName" value="${(category.categoryName)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >type</label>
                            <input  type="number" name="categoryType" value="${(category.categoryType)!''}" class="form-control" />
                        </div>
                        <input hidden type="hidden" name="categoryId" value="${(category.categoryId)!''}">
                        <div class="col-md-12 column" style="text-align: center">
                            <button type="submit" class="btn btn-default btn-primary">提交</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>