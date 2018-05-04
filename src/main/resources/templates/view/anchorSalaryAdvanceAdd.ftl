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
                    <div class="widget" id="textBox">
                        <div class="widget-header ">
                            <span class="widget-caption">基本信息</span>
                        </div>
                        <div class="widget-body">
                            <div>
                                <form class="form-horizontal form-bordered" id="myform" role="form">
                                    <div>
                                        <div class="form-group">
                                            <label for="name"
                                                   class="col-sm-2 control-label no-padding-right">申请人</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="name" class="form-control" id="name"
                                                       placeholder="${userInfo.getName()}" disabled>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="platform"
                                                   class="col-sm-2 control-label no-padding-right">平台选择</label>
                                            <div class="col-sm-10">
                                                <select id="platform" name="platform" style="width:100%;">
                                                            <#list anchorList as platform>
                                                             <option value="${platform.getPlatformId()}">${platform.getLivePlatform()}</option>
                                                            </#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="content"
                                                   class="col-sm-2 control-label no-padding-right">标题</label>
                                            <div class="col-sm-10">
                                                <textarea id="title" name="title" class="form-control" rows="3"
                                                          style="resize: none;"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="money"
                                                   class="col-sm-2 control-label no-padding-right">预支金额</label>
                                            <div class="col-sm-10">
                                                <input type="number" class="form-control" name="salary" id="salary"
                                                       placeholder="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="remark"
                                                   class="col-sm-2 control-label no-padding-right">内容</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" name="description" id="description"
                                                          rows="3"
                                                          style="resize: none;"></textarea>
                                                <i>认真仔细填写备注信息包括归还时间，以便于审核!</i>
                                            </div>
                                        </div>

                                        <div class="form-group ">
                                            <div class="text-right" style="margin-right:16px;">
                                                <a class="btn btn-success" id="next" href="javascript:void(0);"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 下一步</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                    <div class="widget" id="imgBox" style="display: none">
                        <div class="widget-header ">
                            <span class="widget-caption">图片信息</span>
                        </div>
                        <div class="widget-body">
                            <div class="form-group ">
                                <form class="form-horizontal form-bordered" role="form" id="gravaForm"
                                      enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label no-padding-right">必须上传照片</label>
                                        <div class="control-group js_upFile2_box col-sm-10">
                                            <div class="layui-upload">
                                                <input id="imgId" value="" style="display: none">
                                                <button type="button" class="layui-btn" id="image-select">
                                                    多图片选择
                                                </button>
                                                <button type="button" class="layui-btn" id="image-upload">
                                                    多图片上传
                                                </button>
                                                <blockquote class="layui-elem-quote layui-quote-nm"
                                                            style="margin-top: 10px;">
                                                    预览图：
                                                    <div class="layui-upload-list" id="imgShow"></div>
                                                </blockquote>
                                            </div>

                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="form-group ">
                                <div class="text-right" style="margin-right:16px;">
                                    <a class="btn btn-success" id="submit" href="javascript:void(0);"><i
                                            class="btn-label glyphicon glyphicon-ok"></i> 提交</a>
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
    var salaryAdvance = {
        userId: "${userInfo.getId()}"
        , salary: ""
        , id: ""
        , title: ""
        , anchorName: "${userInfo.getName()}"
        , img: ""
        , platformId: ""
        , description: ""
    }
    var flog = true;
    var upload;
    $(function () {
        $("#next").click(function () {
            salaryAdvance.salary = $("#salary").val();
            salaryAdvance.description = $("#description").val();
            salaryAdvance.title = $("#title").val();
            salaryAdvance.platformId=$("#platform").val();
            $.post(
                    "/oa/anchorSalaryAdvance/save",
                    {
                        salary: salaryAdvance.salary
                        , title: salaryAdvance.title
                        , userId: salaryAdvance.userId
                        , anchorName: salaryAdvance.anchorName
                        , platformId: salaryAdvance.platformId
                        , description: salaryAdvance.description
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location="/oa/anchorSalaryAdvance/img/"+res.data.id
                            }, 1000)

                        }
                        //如果上传失败
                    }
            )
        })


    })

</script>
</body>
</html>