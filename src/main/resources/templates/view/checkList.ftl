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
                <div class="page-body no-padding">
                    <div class="mail-container">
                    <#--mail-header-->
                        <div class="mail-header">
                            <ul class="header-buttons pull-right">
                                <li class="search">
                                    <span class="input-icon">
                                        <input type="text" class="form-control input-sm" id="fontawsome-search">
                                        <i class="glyphicon glyphicon-search lightgray"></i>
                                    </span>
                                </li>
                            </ul>
                            <div class="pages">

                            </div>
                        </div>
                    <#--mail-body-->
                        <div class="mail-body">
                            <ul class="mail-list">
                                <#list pageContent.getContent() as check>
                                     <li class="list-item <#if check.getSalary() gte 500>unread</#if>">
                                         <div class="item-sender">
                                             <a href="/oa/check/index/${check.getId()}.html" class="col-name">${check.getApplyPersonnelName()!}</a>
                                         </div>
                                         <div class="item-subject">
                                             <span class="label
                                                 <#if check.getResultStatus()==1>label-palegreen
                                                   <#elseif check.getCheckStatus() ==0>label-yellow
                                                   <#elseif check.getResultStatus() == 0>label-darkorange
                                                 </#if>">${check.getType()}</span>
                                             <a href="/oa/check/index/${check.getId()}.html">
                                                 ${check.getDescription()}
                                             </a>
                                         </div>
                                         <div class="item-options">
                                             <a href="/oa/check/index/${check.getId()}.html"><i class="fa fa-paperclip"></i></a>
                                         </div>
                                         <div class="item-time">
                                             ${check.getApplyTime()!}
                                         </div>
                                     </li>
                                </#list>
                            </ul>
                        </div>
                    <#--mail-sidebar-->
                        <div class="mail-sidebar">
                            <ul class="mail-menu">
                                <li <#if checkStatus==2>class="active"</#if>>
                                    <a href="/oa/check/list.html">
                                        <i class="fa fa-inbox"></i>
                                        <span class="badge badge-default badge-square pull-right">${allCheck}</span>
                                        所有记录
                                    </a>
                                </li>
                                <li <#if checkStatus==1>class="active"</#if>>
                                    <a href="/oa/check/list.html?checkStatus=1">
                                        <i class="fa fa-star"></i>
                                        <span class="badge badge-default badge-square pull-right">${checked}</span>
                                        已经审核
                                    </a>
                                </li>
                                <li <#if checkStatus==0>class="active"</#if>>
                                    <a href="/oa/check/list.html?checkStatus=0">
                                        <i class="glyphicon glyphicon-share"></i>
                                        <span class="badge badge-default badge-square pull-right">${checking}</span>
                                        未审核
                                    </a>
                                </li>
                                <li class="divider">
                                </li>
                            </ul>
                            <ul class="mail-menu">
                                <li class="menu-title">
                                    <h6>标签</h6>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="badge badge-palegreen badge-tag badge-square"></span>
                                        已通过
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="badge badge-darkorange badge-tag badge-square"></span>
                                        未通过
                                    </a>
                                </li>
                                <li>
                                    <a href="#">
                                        <span class="badge badge-yellow badge-tag badge-square"></span>
                                        待审核
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <#include "../common/page.ftl">

                </div>
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
</body>
</html>