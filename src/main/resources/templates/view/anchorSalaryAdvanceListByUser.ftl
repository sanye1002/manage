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
                <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header ">
                                <span class="widget-caption">个人预支记录</span>

                            </div>
                            <div class="widget-body">
                                <div class="widget-header bordered-bottom bordered-palegreen">
                                    <span class="widget-caption">预支记录</span>
                                </div>
                                <div class="table-scrollable">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>月份</th>
                                            <th>申请人</th>
                                            <th>申请时间</th>
                                            <th>标题</th>
                                            <th>内容</th>
                                            <th>金额</th>
                                            <th>你的备注</th>
                                            <th>审核状态</th>
                                            <th>审核结果</th>
                                            <th>批注</th>
                                            <th>操作</th>

                                        </tr>
                                        </thead>
                                        <tbody>
                                   <#list pageContent.getContent() as page>
                                   <tr id="${page.getId()}" <#if page.getResultStatus()==1>class="success" </#if>>
                                       <td>${page.getMonth()}</td>
                                       <td>${page.getAnchorName()}</td>
                                       <td>${page.getCreateTime()}</td>
                                       <td>${page.getTitle()}</td>
                                       <td>${page.getDescription()}</td>
                                       <td>${page.getSalary()}</td>
                                       <td>${page.getDescription()}</td>
                                       <td>
                                           <#if page.getCheckStatus()==0>
                                               未审核
                                           <#else>
                                                已审核
                                           </#if>
                                       </td>
                                       <td>
                                           <#if page.getCheckStatus()==1>
                                                <#if page.getResultStatus()==0>
                                                    驳回
                                                <#else>
                                                    通过
                                                </#if>
                                           <#else>
                                                未审核
                                           </#if>
                                       </td>
                                       <td>${page.getResultRemark()!}</td>
                                       <td>
                                           <#if page.getCheckStatus()==1 >
                                               <a class="btn btn-default btn-xs"><i class="fa fa-edit"></i> 已处理 </a>
                                           <#else >
                                               <a class="btn btn-danger btn-xs" onclick="revoke('${page.getId()}')"><i class="fa fa-times"></i>撤销</a>
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
    function revoke(id) {

        layer.confirm('是否撤回本次申请？撤销以后记录将不存在！', {
                    btn: ['确认', '取消'] //按钮
                }, function () {

                    layer.msg('请稍等...', {
                        time: 1000
                    });
                    //执行POST请求
                    $.post(
                            "/oa/anchorSalaryAdvance/revoke",
                            {
                                id: id
                            },
                            function (res) {
                                layer.msg(res.data.message, {
                                    time: 1000
                                });
                                var url =  window.location.pathname;
                                var search = window.location.search;
                                if (res.code==0){
                                    setTimeout(function () {
                                        location=url+search
                                    },1000)
                                }
                            }
                    )
                }
        )

    }
</script>
</body>
</html>