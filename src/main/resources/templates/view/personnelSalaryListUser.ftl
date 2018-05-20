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

                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>月份</th>
                                        <th>姓  名</th>
                                        <th>手机号码</th>
                                        <th>基本工资</th>
                                        <th>绩效考核</th>
                                        <th>补贴</th>
                                        <th>全勤</th>
                                        <th>奖金</th>
                                        <th>考勤扣款</th>
                                        <th>应发合计</th>
                                        <th>个税</th>
                                        <th>应扣小计</th>
                                        <th>实发金额</th>
                                        <th>备注</th>
                                        <th>拨款状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getMonth()!}</td>
                                            <td>${mm.getPersonnelName()!}</td>
                                            <td>${mm.getPhone()!}</td>
                                            <td>${mm.getBaseSalary()!}</td>
                                            <td>${mm.getJIXIAO()!}</td>
                                            <td>${mm.getBUTIE()!}</td>
                                            <td>${mm.getQUANQING()!}</td>
                                            <td>${mm.getJIANGJING()!}</td>
                                            <td>${mm.getKAOQINGKOUKUAN()!}</td>
                                            <td>${mm.getYINGFAHEJI()!}</td>
                                            <td>${mm.getGESUI()!}</td>
                                            <td>${mm.getYINGKOUXIAOJI()!}</td>
                                            <td>${mm.getRealSalary()!}</td>
                                            <td>${mm.getRemark()!}</td>
                                            <#if mm.getGrantsStatus() == 0>
                                                    <td>未拨款</td>
                                            <#else >
                                                    <td>已拨款</td>
                                            </#if>
                                            <td>
                                                <#if mm.getConfirmStatus() ==0>
                                                    <a onclick="confirm('${mm.getId()}')" class="btn btn-sky btn-sm  btn-follow">
                                                        <i class="fa fa-arrow-circle-o-right"></i>
                                                        点击确认
                                                    </a>
                                                    <#else >
                                                    <a onclick="alter()" class="btn btn-sky btn-sm  btn-follow">
                                                        <i class="fa fa-arrow-circle-o-right"></i>
                                                        已经确认
                                                    </a>
                                                </#if>

                                            </td>

                                        </tr>
                                        </#list>

                                    </tbody>
                                </table>
                            </div>
                           <#include "../common/page.ftl">
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
    function confirm(id) {

        $.post(
            "/oa/personnelSalary/confirm",
            {
                id:id
            }, function (res) {
                    layer.msg(res.message,{
                        time:1200
                    });
                    if (res.code==0){
                        setTimeout(function () {
                            location="/oa/personnelSalary/list/user.html"
                        },1000)
                    }
                }

        )
    }
    function alter() {
        layer.msg("已经确认！",{
            time:1200
        });
    }
</script>

</body>
</html>