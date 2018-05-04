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
                                                   class="col-sm-2 control-label no-padding-right">物品名称</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="name" class="form-control"  value="${itemInfo.getName()!}" placeholder="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="number"
                                                   class="col-sm-2 control-label no-padding-right">物品编号</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="number" class="form-control" value="${itemInfo.getNumber()!}" placeholder="" required="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="color"
                                                   class="col-sm-2 control-label no-padding-right">物品颜色</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="color" class="form-control" value="${itemInfo.getColor()!}" placeholder="" required="">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="allCount"
                                                   class="col-sm-2 control-label no-padding-right">物品个数</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="allCount" class="form-control" value="${itemInfo.getSumAmount()!}" placeholder="" required="" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="description"
                                                   class="col-sm-2 control-label no-padding-right">物品描述</label>
                                            <div class="col-sm-10">
                                                <textarea class="form-control" name="description"  id="description"
                                                          rows="3"
                                                          style="resize: none;">${itemInfo.getDescription()!}</textarea>
                                                <i>认真仔细填写描述信息,如颜色，成色!</i>
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

                </div>
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>
<#include "../common/footjs.ftl">
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    var item = {
        userId: "${user.getId()}"
        , salary: ""
        , id: "${itemInfo.getId()!}"
        , name: ""
        ,color:""
        ,number:""
        ,sumAmount:""
        , userName: "${user.getName()}"
        , description: ""
    }
    var flog = true;
    var upload;
    $(function () {
        $("#next").click(function () {
            item.name = $("#name").val();
            item.number = $("#number").val();
            item.sumAmount = $("#allCount").val();
            item.description = $("#description").val();
            item.color = $("#color").val();
            $.post(
                    "/oa/item/save",
                    {
                        id: item.id
                        , userId: item.userId
                        , userName: item.userName
                        , name: item.name
                        , number: item.number
                        , color: item.color
                        , sumAmount: item.sumAmount
                        , description: item.description
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location="/oa/item/img/"+res.data.id
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