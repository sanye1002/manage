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
                                <span class="widget-caption">个人申请记录</span>
                                <div class="widget-buttons">
                                    <a href="#" data-toggle="maximize">
                                        <i class="fa fa-expand"></i>
                                    </a>
                                    <a href="#" data-toggle="collapse">
                                        <i class="fa fa-minus"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-header bordered-bottom bordered-palegreen">
                                    <span class="widget-caption">申请记录</span>
                                </div>
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
                                    </tr>
                                    </thead>
                                    <tbody>
                                   <#list pageContent.getContent() as page>
                                   <tr id="${page.getId()}" <#if page.getResultStatus()==1>class="success" </#if>>
                                       <td>${page.getMonth()}</td>
                                       <td>${page.getPersonnelName()}</td>
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

    var spending = {
        personnelId: "${personnelInfo.getId()}"
        , salary: ""
        , title: ""
        , personnelName: "${personnelInfo.getName()!}"
        , img: ""
        , deptNo: "${personnelInfo.getDeptNo()!}"
        , description: ""
    }
    $("#submit").click(function () {
        spending.salary = $("#salary").val();
        spending.description = $("#description").val();
        spending.title = $("#title").val();

        $.post(
                "/oa/spending/save",
                {
                    salary:spending.salary
                    ,title:spending.title
                    ,personnelId:spending.personnelId
                    ,personnelName:spending.personnelName
                    ,img:spending.img
                    ,deptNo:spending.deptNo
                    ,description:spending.description
                },
                function (res) {
                    layer.msg(res.message,{
                        time:1000
                    });
                    if (res.code == 0) {
                       setTimeout(function () {
                           location="/oa/spending/index.html"
                       },1000)
                    }
                    //如果上传失败
                    if (res.code > 0) {

                    }
                }
        )
    })
    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select'
            , url: '/upload/img/spending'
            , auto: false
            , acceptMime: 'image'
            , bindAction: '#image-upload'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                $('#imgShow').html("");
                obj.preview(function (index, file, result) {
                    $('#imgShow').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                });
            }
            , done: function (res) {
                if (res.code == 0) {
                    spending.img = res.data.src
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });

    });
</script>
</body>
</html>