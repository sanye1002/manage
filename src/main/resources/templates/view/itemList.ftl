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
                                <a href="/oa/item/index.html" target="_self" class="btn btn-success">添加物品</a>
                            </div>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>编号</th>
                                    <th>名称</th>
                                    <th>颜色样式</th>
                                    <th>详细描述</th>
                                    <th>一共数量</th>
                                    <th>剩余数量</th>
                                    <th>添加时间</th>
                                    <th>更新时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getContent() as p>
                                <tr id="${p.getId()}">
                                    <td>${p.getNumber()}</td>
                                    <td>${p.getName()}</td>
                                    <td>${p.getColor()}</td>
                                    <td>${p.getDescription()!}</td>
                                    <td>${p.getSumAmount()}</td>
                                    <td>${p.getBeforeAmount()}</td>
                                    <td>${p.getCreateTime()}</td>
                                    <td>${p.getUpdateTime()}</td>
                                    <td>
                                        <a class="btn btn-yellow btn-xs shiny"
                                           onclick="showImg(${p.id})"><i
                                                class="fa fa-check"></i> 图片</a>

                                        <a class="btn btn-danger btn-xs" onclick="deleteInfo(${p.getId()})"><i class="fa fa-times"></i>
                                            删除</a>
                                        <a class="btn btn-info btn-xs" href="/oa/item/index?id=${p.getId()}"><i class="fa fa-edit"></i> 编辑
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
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
    function deleteInfo(id) {
        layer.msg("不能删除")
    }
</script>
<script>

    function showImg(id) {
        $.getJSON('/layer/item/' + id, function (json) {
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }
</script>
</body>
</html>