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
                    <li class="active"> ${pageTitle}</li>
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
                                <span class="badge badge-sky badge-square">
                                     提示：点击身份证可查看身份证信息
                                    </span>
                            </div>
                            <form class="search" method="get">
                                <div style="float:right;margin-right:2px;">
                                    <a href="/oa/case/application/index.html" target="_self" class="btn btn-success">发布申请</a>
                                </div>
                            </form>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>月份</th>
                                        <th>姓名</th>
                                        <th>申请时间</th>
                                        <th>申请标题</th>
                                        <th>申请内容</th>
                                        <th>出行状态</th>
                                        <th>申请审核结果</th>
                                        <th>申请审核时间</th>
                                        <th>申请审核备注</th>
                                        <th>回执时间</th>
                                        <th>回执标题</th>
                                        <th>回执内容</th>
                                        <th>回执审核结果</th>
                                        <th>回执审核时间</th>
                                        <th>回执审核备注</th>
                                        <th colspan="3">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                <#list pageContent.getPageContent() as items>
                                <tr id="${items.id}"
                                    <#if items.getApplicationCase().getResultStatus()==1>class="success"</#if>
                                    style="height: 50px;text-align: center">

                                    <td>${items.getApplicationCase().getMonth()!}</td>
                                    <td>${items.getApplicationCase().getUsername()!}</td>
                                    <td>${items.getApplicationCase().getCreateTime()!}</td>
                                    <td>${items.getApplicationCase().getTitle()!}</td>
                                    <td>
                                        <div id="application${items.getId()}" style="display: none">
                                            ${items.getApplicationCase().getContent()!}
                                        </div>
                                        <a class="btn btn-sky btn-xs"
                                           onclick="showApplication(${items.getId()})"><i class="fa fa-square"></i>
                                            申请内容</a>
                                    </td>
                                    <td><#if items.getApplicationCase().getGoOut()==0>不外出</#if><#if items.getApplicationCase().getGoOut()==1>外出</#if></td>
                                    <td>
                                        <#if items.getApplicationCase().getCheckStatus()==1>
                                                <#if items.getApplicationCase().getResultStatus()==0>
                                                    驳回
                                                <#else>
                                                    通过
                                                </#if>
                                        <#else>
                                                未审核
                                        </#if>
                                    </td>
                                    <td>${items.getApplicationCase().getCheckTime()!}</td>
                                    <td>${items.getApplicationCase().getResultRemark()!}</td>
                                    <#if items.getBackCase().getId()==0>
                                        <td>回执时间</td>
                                        <td>回执标题</td>
                                        <td>回执内容</td>
                                        <td>回执审核结果</td>
                                        <td>回执审核时间</td>
                                        <td>回执审核备注</td>
                                    <#else >
                                        <td>${items.getBackCase().getCreateTime()!}</td>
                                        <td>${items.getBackCase().getTitle()!}</td>
                                        <td>
                                            <div id="backCase${items.getId()}" style="display: none">
                                                ${items.getBackCase().getContent()!}
                                            </div>
                                            <a class="btn btn-sky btn-xs"
                                               onclick="showBackCase(${items.getId()})"><i class="fa fa-square"></i>
                                                回执内容</a>
                                        </td>
                                        <td>
                                            <#if items.getBackCase().getCheckStatus()==1>
                                                <#if items.getBackCase().getResultStatus()==0>
                                                    驳回
                                                <#else>
                                                    通过
                                                </#if>
                                        <#else>
                                                未审核
                                        </#if>
                                        </td>
                                        <td>${items.getBackCase().getCheckTime()!}</td>
                                        <td>${items.getBackCase().getResultRemark()!}</td>
                                    </#if>
                                    <td>
                                        <#if  items.getApplicationCase().getCheckStatus()==0>
                                            <a href="/oa/case/application/index.html?id=${items.getApplicationCase().getId()}" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑申请</a>
                                            <#else >
                                            <a  class="btn btn-blueberry btn-xs"><i class="fa fa-check-square"></i> 已审核</a>
                                        </#if>
                                    </td>
                                    <td>
                                        <#if  items.getApplicationCase().getResultStatus()==1>
                                            <a href="/oa/case/back/index.html?caseId=${items.getId()}" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 添加回执</a>
                                        <#elseif items.getApplicationCase().getCheckStatus()==0>
                                           <a  class="btn btn-warning btn-xs"><i class="fa fa-times"></i> 申请未审核</a>
                                         <#elseif items.getApplicationCase().getResultStatus()==1&&items.getBackCase().getCheckStatus()==0&&items.getBackCase().getId()!=0>
                                           <a href="/oa/case/back/index.html?id=${items.getBackCase().getId()}&caseId=${items.getId()}" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑回执</a>
                                        <#elseif items.getApplicationCase().getResultStatus()==0&&items.getBackCase().getId()==0>
                                           <a  class="btn btn-warning btn-xs"><i class="fa fa-edit"></i> 申请未通过</a>
                                        <#else  >
                                            <a  class="btn btn-blueberry btn-xs"><i class="fa fa-check-square"></i> 已审核</a>
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
<script>
    function showApplication(id) {
        //页面层
        var content = $("#application" + id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['1280px', '768px'], //宽高
            content: content
        });
    }
    function showBackCase(id) {
        //页面层
        var content = $("#backCase" + id).html();
        layer.open({
            type: 1,
            skin: 'layui-layer-lan', //加上边框
            area: ['1280px', '768px'], //宽高
            content: content
        });
    }

    $(function () {


    })

    function dele(id) {
        layer.confirm('客官确定要进行删除？', {
            btn: ['确定', '再想想'] //按钮
        }, function () {
            $.post(
                    "/oa/anchor/delete",
                    {id: id},
                    function (res) {
                        if (res.code == 0) {
                            $("#" + id).remove();
                            layer.msg('已删除', {icon: 1});
                        }
                        if (res.code != 0) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                    }
            )

        });
    }
</script>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    function showSFZ(id) {
        $.getJSON('/layer/photos/' + id, function (json) {
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }
</script>
</body>
</html>