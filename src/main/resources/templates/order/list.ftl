<!DOCTYPE html>
<html lang="en">
 <#include "../common/header.ftl">
<script src="../../webjars/jquery/3.3.1/jquery.min.js"></script>
<script src="../../webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>
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
                            <th>订单编号</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderList as order>
                <tr>
                    <td>${order.orderId} </td>
                    <td>${order.buyerName} </td>
                    <td>${order.buyerPhone} </td>
                    <td>${order.buyerAddress} </td>
                    <td>${order.orderAmount}</td>
                    <td>${order.orderStatusEnum.message} </td>
                    <td>${order.orderPayStatusEnum.message} </td>
                    <td>${order.createTime} </td>
                    <td><a href="/sell/seller/order/detail?orderId=${order.orderId}">详情</a></td>
                    <td>
                            <#if order.orderStatusEnum.message!="已取消" >
                                <a href="/sell/seller/order/cancel?orderId=${order.orderId}">取消</a>
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
                    <a href="http://localhost:8080/sell/seller/order/list?page=${curPage-1}&size=${size}">上一页</a>
                </li>
                </#if>

                <#list 1..totalPage as index>
                    <#if curPage==index>
                        <li class="disabled">
                            <a href="#">${index}</a>
                        </li>
                    <#else >
                         <li>
                             <a href="http://localhost:8080/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                         </li>
                    </#if>
                </#list>

                <#if curPage lte totalPage>
                    <li>
                        <a href="http://localhost:8080/sell/seller/order/list?page=${curPage+1}&size=${size}">下一页</a>
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
<#--弹窗-->
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>
            </div>
        </div>
    </div>
</div>

<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>
</body>
<script>
    var websocket = null;
    if('WebSocket' in window) {
        websocket = new WebSocket('ws://localhost:8080/sell/webSocket');
    }else {
        alert('该浏览器不支持websocket!');
    }

    websocket.onopen = function (event) {
        console.log('建立连接');
    }

    websocket.onclose = function (event) {
        console.log('连接关闭');
    }

    websocket.onmessage = function (event) {
        console.log('收到消息:' + event.data)
        //弹窗提醒, 播放音乐
        $('#myModal').modal('show');

        document.getElementById('notice').play();
    }

    websocket.onerror = function () {
        alert('websocket通信发生错误！');
    }

    window.onbeforeunload = function () {
        websocket.close();
    }
</script>
</html>