<!DOCTYPE html>
<html lang="en">
<head>
<#include "../common/header.ftl">
</head>
<body>
<div id="wrapper" class="toggled">
    <#--侧边栏sidebar-->
    <#include "../common/nav.ftl">
    <#--主要内容区content-->
    <div id="page-content-wrapper">
        <div class="container-fluid" style="margin-left: 50px">
        <div class="row clearfix">
            <div class="col-md-4 column">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>订单编号 </th>
                        <th>订单金额 </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>${orderDTO.orderId} </td>
                        <td>${orderDTO.orderAmount}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="col-md-12 column">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>商品编号 </th>
                        <th>商品名称 </th>
                        <th>价格 </th>
                        <th>数量 </th>
                        <th>总金额 </th>
                    </thead>
                    <tbody>
            <#list orderDTO.orderDetailList as detail>
            <tr>
                <td>${detail.productId} </td>
                <td>${detail.productName} </td>
                <td>${detail.productPrice} </td>
                <td>${detail.productQuantity} </td>
                <td>${detail.productPrice*detail.productQuantity}</td>
            </#list>
                    </tbody>
                </table>
            </div>
        <#--操作-->
            <div class="col-md-12 column">
        <#if orderDTO.orderStatusEnum.message=="新订单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-danger btn-default">取消订单</a>
        </#if>
            </div>
        </div>
    </div>
    </div>
</div>
</body>
</html>