<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">
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
                                    <a id="" href="/oa/notice/message/index.html" class="btn btn-magenta">通知短信发送</a>
                                </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <div class="checkbox">
                                        <span>结果：</span>
                                        <label>
                                            <input type="checkbox" class="colored-blue" value="1"
                                                   <#if status==1>checked</#if>>
                                            <span class="text">发送成功</span>
                                        </label>
                                        <label>
                                            <input type="checkbox" value="0" class="colored-danger"
                                                   <#if status==0>checked</#if>>
                                            <span class="text">发送失败</span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>手机</th>
                                        <th>姓名</th>
                                        <th>内容</th>
                                        <th>发送时间</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getContent() as p>
                                <tr id="${p.getId()}"
                                    <#if p.getStatus()==1>class="success" </#if>
                                    <#if p.getStatus()==0>class="danger" </#if>
                                >
                                    <td>${p.getPhone()!}</td>
                                    <td>${p.getName()!}</td>
                                    <td>${p.getContent()!}</td>
                                    <td>${p.getTime()!}</td>
                                    <td>
                                            <#if p.getStatus()==0>
                                                失败
                                            </#if>
                                            <#if p.getStatus()==1>
                                                成功
                                            </#if>
                                    </td>
                                    <td>
                                        <a class="btn btn-info btn-xs"><i
                                                class="fa fa-edit"></i> 操作
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                                    </tbody>
                                </table>
                            </div>

                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li>
                                            <a href="${url}?page=1&size=${size}&status=${status}">首页</a>
                                        </li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li>
                                                    <a href="${url}?page=${currentPage-1}&size=${size}&status=${status}">上一页</a>
                                                </li>

                                            </#if>

                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li>
                                                            <a href="${url}?page=${index}&size=${size}&status=${status}">${index}</a>
                                                        </li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li>
                                                        <a href="${url}?page=${currentPage+1}&size=${size}&status=${status}">下一页</a>
                                                    </li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&status=${status}">尾页</a>
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
<script>
    $("input[type=checkbox]").click(function () {
        var status = $(this).val();
        location = "/oa/notice/message/list.html?&status="+status

    })
</script>
</body>
</html>