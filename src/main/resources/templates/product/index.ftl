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
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input  type="text" name="productName" value="${(product.productName)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >价格</label>
                            <input  type="text" name="productPrice" value="${(product.productPrice)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input  type="number" name="productStoct" value="${(product.productStoct)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >描述</label>
                            <input  type="text" name="productDescription" value="${(product.productDescription)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >图片</label>
                            <img src="${(product.productIcon)!''}" width="100" height="100">
                            <input  type="file" name="productIcon" value="${(product.productIcon)!''}" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >类目</label>
                            <select class="form-control" name="categoryType">
                                <#list categoryList as category>
                                    <#--?? 判断对象不为空-->
                                    <option value="${category.categoryType}" <#if (product.categoryType)?? && product.categoryType==category.categoryType >
                                    selected </#if>
                                    >${category.categoryName}</option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="hidden" name="productId" value="${(product.productId)!''}">
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