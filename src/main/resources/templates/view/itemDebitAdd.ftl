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
                                            <label for="content"
                                                   class="col-sm-2 control-label no-padding-right">商品名称</label>
                                            <div class="col-sm-10">
                                                <select name="search" id="itemInfoId" style="width: 100%">
                                                    <#list itemInfoList as item >
                                                        <option value="${item.getId()}">${item.getName()}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="number"
                                                   class="col-sm-2 control-label no-padding-right">借记数量</label>
                                            <div class="col-sm-10">
                                                <input type="number" class="form-control" name="number" id="number"
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
                                                        class="btn-label glyphicon glyphicon-ok"></i> 提交</a>
                                            </div>
                                        </div>
                                    </div>
                                </form>
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
    var itemDebit = {
        userId: "${userInfo.getId()}"
        , number: ""
        , itemId: ""
        , userName: "${userInfo.getName()}"
        , description: ""
    }
    $(function () {
        $("#next").click(function () {
            itemDebit.number = $("#number").val();
            itemDebit.description = $("#description").val();
            itemDebit.itemId = $("#itemInfoId").val();

            $.post(
                    "/oa/itemDebit/save",
                    {
                        userId: itemDebit.userId
                        , itemId: itemDebit.itemId
                        , number: itemDebit.number
                        , userName: itemDebit.userName
                        , remark: itemDebit.description
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location="/oa/itemDebit/index.html"
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