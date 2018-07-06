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
                                        <th>姓名</th>
                                        <th>手机号码</th>
                                        <th>基本工资</th>
                                        <th>绩效考核</th>
                                        <th>补贴</th>
                                        <th>全勤</th>
                                        <th>奖金</th>
                                        <th>考勤扣款</th>
                                        <th>应发合计</th>
                                        <th>社保</th>
                                        <th>个税</th>
                                        <th>应扣小计</th>
                                        <th>实发金额</th>
                                        <th>备注</th>
                                        <th>确认状态</th>
                                        <th>银行信息</th>
                                        <th>拨款状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getPageContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getMonth()}</td>
                                            <td>${mm.getPersonnelName()}</td>
                                            <td>${mm.getPhone()}</td>
                                            <td>${mm.getBaseSalary()}</td>
                                            <td>${mm.getJIXIAO()}</td>
                                            <td>${mm.getBUTIE()!}</td>
                                            <td>${mm.getQUANQING()!}</td>
                                            <td>${mm.getJIANGJING()}</td>
                                            <td>${mm.getKAOQINGKOUKUAN()}</td>
                                            <td>${mm.getYINGFAHEJI()}</td>
                                            <td>${mm.getSHEBAO()}</td>
                                            <td>${mm.getGESUI()}</td>
                                            <td>${mm.getYINGKOUXIAOJI()}</td>
                                            <td>${mm.getRealSalary()}</td>
                                            <td>${mm.getRemark()}</td>
                                            <#if mm.getConfirmStatus() == 0>
                                                    <td>未确认</td>
                                                <#else >
                                                    <td>已确认</td>
                                            </#if>
                                            <td>
                                                <a class="btn btn-magenta btn-xs shiny" onclick="openPayMessage('${mm.getUserInfo().aliPay!}','${mm.getUserInfo().bankUserName!}','${mm.getUserInfo().bankType!}','${mm.getUserInfo().bankCardNumber!}')" ><i class="fa fa-shopping-cart"></i> 银行信息</a>
                                            </td>
                                            <#if mm.getGrantsStatus() == 0>
                                                    <td>未拨款</td>
                                                    <td>
                                                        <a class="btn btn-info btn-xs" onclick="grants(${mm.getId()})">
                                                            <i class="fa fa-edit"></i> 拨款
                                                        </a>
                                                    </td>
                                                <#else >
                                                    <td>已拨款</td>
                                                    <td>
                                                        <a class="btn btn-info btn-xs" onclick="granted()">
                                                            <i class="fa fa-edit"></i> 已拨款
                                                        </a>
                                                    </td>
                                            </#if>

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
    function openPayMessage(pay,username,type,number) {
        layer.open({
            title:'银行信息',
            skin: 'ayui-layer-molv', //样式类名
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            content: '开户姓名：'+username+';  <br>开户银行：'+type+';  <br>银行账户：'+number+';  <br>支付宝：'+pay

        });
    }
    function grants(id) {
        layer.confirm('确定已给员工拨款？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.closeAll();
            layer.msg('请稍等...', {
                icon: 16
                , shade: 0.01
            });
            //执行POST请求
            $.post(
                    "/oa/personnelSalary/grants",
                    {id: id},
                    function (res) {
                        if (res.code==0){
                            layer.msg(res.message+"----"+res.data.message, {
                                time: 1000
                            });
                        }else {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                        if (res.code == 0) {
                            setTimeout(function () {
                                layer.closeAll();
                                var month = $("#month").val();
                                if (month != "") {
                                    location = "/oa/personnelSalary/list.html?month=" + month
                                }
                            }, 1000);
                        }
                    }
            )

        });
    }
    function granted() {
        layer.msg("已拨款", {
            time: 1000
        });
    }
    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;
        //执行上传
        var uploadInst = upload.render({
            elem: '#importExcel' //绑定元素
            , url: '/oa/personnelSalary/save' //上传接口
            , method: 'POST'
            , accept: 'file'
            , before: function (obj) {
                layer.load();
            }
            , done: function (res) {//上传完毕回调
                layer.closeAll('loading');
                if (res.code == 0) {
                    layer.msg(res.message);
                   setTimeout(function () {
                       location="/oa/personnelSalary/list.html"
                   },1000)

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
                location = "/oa/personnelSalary/list.html?month=" + month
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
                            location = "/oa/personnelSalary/downLoad/${month}/100"
                        }, 1000)
                    }
            )

        });
    })

</script>

</body>
</html>