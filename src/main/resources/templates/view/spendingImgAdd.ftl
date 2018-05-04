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
                    <div class="widget" id="imgBox">
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
                                    <a class="btn btn-success" id="delete" href="javascript:void(0);"><i
                                            class="btn-label glyphicon glyphicon-ok"></i> 取消</a>
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
    var flog = true;
    var id =  ${spendingInfo.getId()};
    $(function () {
        $("#delete").click(function () {

        })
        $("#submit").click(function () {
            if (flog) {
                $.post(
                        "/oa/spending/check",
                        {id:id},
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            if (res.code == 0) {
                                setTimeout(function () {
                                    location = "/oa/spending/index.html"
                                }, 1000)
                            }
                            //如果上传失败
                            if (res.code > 0) {
                                setTimeout(function () {
                                    location = "/oa/spending/index.html"
                                }, 1000)
                            }
                        }
                )
            } else {
                layer.msg("图片上传失败！");
            }
        })
        layui.use('upload', function () {

            var $ = layui.jquery;
            upload = layui.upload;
            //选完文件后不自动上传
            upload.render({
                elem: '#image-select'
                , url: '/upload/imgs/spending/${spendingInfo.getId()}'
                , auto: false
                , multiple: true
                , acceptMime: 'image'
                , bindAction: '#image-upload'
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                   /* $('#imgShow').html("");*/
                    obj.preview(function (index, file, result) {
                        $('#imgShow').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                    });
                }
                , done: function (res) {
                    if (res.code == 0) {
                        flog = true;
                        layer.msg(res.message);
                    }
                    //如果上传失败
                    if (res.code > 0) {
                        flog = false;
                        return layer.msg(res.message);
                    }
                }
            });

        });


    })

</script>
</body>
</html>