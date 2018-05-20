<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
<link href="/layui/css/layui.css" rel="stylesheet"/>
<body>
<!-- 导航 -->

<#include "../common/nav.ftl">

<!-- 主体内容 -->
<div class="main-container container-fluid">
    <!-- 页面内容 -->
    <div class="page-container">
        <!-- 侧边栏导航 -->
       <#include "../common/sider.ftl">
        <!-- /侧边栏导航 -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- 页面面包屑 -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="#">主页</a>
                    </li>
                    <li class="active">${pageTitle}</li>
                </ul>
            </div>
            <!-- /页面面包屑 -->
            <!-- Page Header -->
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                    ${pageTitle}
                    </h1>
                </div>
                <!--Header Buttons-->
                <div class="header-buttons">
                    <a class="sidebar-toggler" href="#">
                        <i class="fa fa-arrows-h"></i>
                    </a>
                    <a class="refresh" id="refresh-toggler" href="">
                        <i class="glyphicon glyphicon-refresh"></i>
                    </a>
                    <a class="fullscreen" id="fullscreen-toggler" href="#">
                        <i class="glyphicon glyphicon-fullscreen"></i>
                    </a>
                </div>
                <!--Header Buttons End-->
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body">
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                            </div>

                            <div style="float:right;margin-right:2px;">
                                <a href="/excel/downLoad/momo" class="btn btn-success">下载模板</a>
                            </div>
                            <div style="float:right;margin-right:2px;">
                                <a id="importExcel" class="btn btn-warning">上传Excel</a>
                            </div>
                            <div style="float:right;margin-right:2px;">
                                <a id="printExcel" class="btn btn-magenta">打印Excel</a>
                            </div>
                            <div class="col-sm-2">
                                <div class="form-group">
                                            <span class="input-icon icon-right">
                                                <input type="text" value="${month}" id="month" class="form-control">
                                                <i id="fa-search" class="fa fa-search"></i>
                                            </span>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>月份</th>
                                        <th>播主昵称</th>
                                        <th>陌陌号</th>
                                        <th>播主等级</th>
                                        <th>所属经纪人</th>
                                        <th>经纪人陌陌号</th>
                                        <th>连麦陌币</th>
                                        <th>非连麦陌币</th>
                                        <th>总陌币</th>
                                        <th>结算方式</th>
                                        <th>播主分成金额(元)</th>
                                        <th>公会分成金额(元)</th>
                                        <th>播主奖励(元)</th>
                                        <th>其他奖励(元)</th>
                                        <th>当月入会前收益(元)</th>
                                        <th>个税(元)</th>
                                        <th>提现金额(元)</th>
                                        <th>风控扣款(元)</th>
                                        <th>结算金额(元)</th>
                                        <th>实际收入(元)</th>
                                        <th>税前收益</th>
                                        <th>税后收益</th>
                                        <th>职业返点</th>
                                        <th>节奏代刷</th>
                                        <th>借款</th>
                                        <th>最终工资</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getMonth()!}</td>
                                            <td>${mm.getName()!}</td>
                                            <td>${mm.getLiveId()!}</td>
                                            <td>${mm.getGrade()!}</td>
                                            <td>${mm.getId()!}</td>
                                            <td>${mm.getBrokerName()!}</td>
                                            <td>${mm.getBrokerId()!}</td>
                                            <td>${mm.getLianMaiMoBi()!}</td>
                                            <td>${mm.getAllMoBi()!}</td>
                                            <td>${mm.getBillingMethod()!}</td>
                                            <td>${mm.getBoZhuFenChen()!}</td>
                                            <td>${mm.getGongHuiFenChen()!}</td>
                                            <td>${mm.getBoZhuJiangLi()!}</td>
                                            <td>${mm.getQiTaJiangLi()!}</td>
                                            <td>${mm.getRuHuiQian()!}</td>
                                            <td>${mm.getGeSui()!}</td>
                                            <td>${mm.getTiXian()!}</td>
                                            <td>${mm.getFengKongKouKuan()!}</td>
                                            <td>${mm.getBillingSalary()!}</td>
                                            <td>${mm.getRealSalary()!}</td>
                                            <td>${mm.getBeforeTax()!}</td>
                                            <td>${mm.getAfterTax()!}</td>
                                            <td>${mm.getFanDian()!}</td>
                                            <td>${mm.getDaiShua()!}</td>
                                            <td>${mm.getJieKuan()!}</td>
                                            <td>${mm.getSalary()!}</td>
                                            <td>操作</td>
                                        </tr>
                                        </#list>

                                    </tbody>
                                </table>
                            </div>
                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&month=${month}">首页</a></li>
                                                <#if currentPage lte 1>
                                                    <li class="disabled"><a>上一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage-1}&size=${size}&month=${month}">上一页</a></li>

                                                </#if>

                                           <#list 1..pageContent.getTotalPages() as index>
                                               <#if currentPage == index >
                                                     <li class="active"><a href="#">${index}</a></li>
                                               <#else>
                                                    <li><a href="${url}?page=${index}&size=${size}&month=${month}">${index}</a></li>
                                               </#if>
                                           </#list>
                                            <#if currentPage gte pageContent.getTotalPages()>
                                                <li class="disabled"><a>下一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage+1}&size=${size}&month=${month}">下一页</a></li>
                                            </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&month=${month}">尾页</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Page Body -->

        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;
        //执行上传
        var uploadInst = upload.render({
            elem: '#importExcel' //绑定元素
            , url: '/excel/import/momo/1' //上传接口
            , method: 'POST'
            , accept: 'file'
            , before: function (obj) {
                layer.load();
            }
            , done: function (res) {//上传完毕回调
                layer.closeAll('loading');
                if (res.code == 0) {
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
            , error: function () {//请求异常回调
                layer.closeAll('loading');
                layer.msg('网络异常，请稍后重试！');
            }
        });
        /*upload.render({
            elem: '#importExcel'
            ,url: '/excel/import/momo/1'
            ,accept: 'file' //普通文件
            ,done: function(res){
                if (res.code == 0) {
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });*/

    });
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#month'
            , type: 'month'
        });
    });
    $(function () {
        $("#fa-search").click(function () {
            var month = $("#month").val();
            if (month != "") {
                location = "/oa/anchorSalary/list/momo.html?month=" + month
            }
        })
        $("#printExcel").click(function () {
            layer.confirm('是否下载Excel？', {
                        btn: ['确认', '取消'] //按钮
                    }, function () {

                        layer.msg('请稍等...', {
                            time: 1000
                        });
                        //执行POST请求
                        setTimeout(function () {
                            location = "/excel/export/${month}/1"
                        }, 1000)
                    }
            )

        });
    })

</script>

</body>
</html>