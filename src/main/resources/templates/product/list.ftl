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
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>商品编号 </th>
                            <th>名称 </th>
                            <th>图片 </th>
                            <th>单价 </th>
                            <th>库存 </th>
                            <th>描述 </th>
                            <th>类目 </th>
                            <th>创建时间 </th>
                            <th>修改时间</th>
                            <th>商品状态</th>
                            <th colspan="2">操作 </th>
                        </tr>
                        </thead>
                        <tbody>
                <#list productList as product>
                <tr>
                    <td>${product.productId} </td>
                    <td>${product.productName} </td>
                    <td><img src="${product.productIcon} " width="100px" height="100px"></td>
                    <td>${product.productPrice} </td>
                    <td>${product.productStoct}</td>
                    <td>${product.productDescription} </td>
                    <td>${product.categoryType} </td>
                    <td>${product.createTime} </td>
                    <td>${product.updateTime} </td>
                    <td>${product.getProductStatusEnum().message} </td>
                    <td> <a href="/sell/seller/product/index?productId=${product.productId}">修改</a></td>
                    <td>
                        <#if product.getProductStatusEnum().message=="下架" >
                            <a href="/sell/seller/product/on_sale?productId=${product.productId}">重新上架</a>
                            <#else >
                            <a href="/sell/seller/product/off_sale?productId=${product.productId}">下架</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if curPage lte 1>
                    <li class="disabled">
                        <a href="#">上一页</a>
                    </li>
                <#else >
                <li>
                    <a href="http://localhost:8080/sell/seller/product/list?page=${curPage-1}&size=${size}">上一页</a>
                </li>
                </#if>

                <#list 1..totalPage as index>
                    <#if curPage==index>
                        <li class="disabled">
                            <a href="#">${index}</a>
                        </li>
                    <#else >
                         <li >
                             <a href="http://localhost:8080/sell/seller/product/list?page=${index}&size=${size}">${index}</a>
                         </li>
                    </#if>
                </#list>

                <#if curPage lte totalPage>
                    <li>
                        <a href="http://localhost:8080/sell/seller/product/list?page=${curPage+1}&size=${size}">下一页</a>
                    </li>
                <#else>
                    <li class="disabled">
                        <a href="#">下一页</a>
                    </li>
                </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>