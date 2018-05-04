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
                <div class="row">
                    <div class="col-lg-12 col-sm-6 col-xs-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div id="registration-form">
                                    <form role="form">
                                        <div class="form-title">基本信息</div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>工号ID</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" disabled value="系统分配" class="form-control">
                                                    <i class="fa fa-user"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="anchorUser">主播（不可修改）</label>
                                                <span class="input-icon icon-right">
                                                    <select id="anchorUser" name="sex" style="width:100%;">
                                                        <#list anchorUser as user>
                                                             <option value="${user.getId()}">${user.getName()}</option>
                                                        </#list>
                                                    </select>
                                                    <i class="glyphicon glyphicon-fire"></i>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="platform">直播平台</label>
                                                <span class="input-icon icon-right">
                                                    <select id="platform" name="sex" style="width:100%;">
                                                        <#if edit ==0>
                                                        <#list platformInfoList as platform>
                                                             <option value="${platform.getId()}">${platform.getName()}</option>
                                                        </#list>
                                                        <#else >
                                                            <#list platformInfoList as platform>
                                                             <option value="${platform.getId()}"
                                                                     <#if platform.getId() == anchorInfo.getPlatformId()>selected</#if> >${platform.getName()}</option>
                                                            </#list>
                                                        </#if>

                                                    </select>
                                                    <i class="fa fa-flag"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="liveId">直播id（必填，用于工资对比）</label>
                                                <span class="input-icon icon-right">
                                                    <input name="liveId" id="liveId" type="text"
                                                           class="form-control" value="${anchorInfo.getLiveId()!}">
                                                    <i class="glyphicon glyphicon-earphone"></i>
                                                </span>
                                            </div>

                                        </div>
                                    </form>
                                    <div class="clear_float"></div>
                                <#--暂时不用上传身份证 要使用去掉最大的div-->

                                    <div class="form-group">
                                        <div class="text-right">
                                            <button class="btn btn-success" href="javascript:void(0);" id="submit"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i> 保存人员
                                            </button>
                                            <a class="btn btn-warning" href="/oa/anchor/list.html"><i
                                                    class="btn-label glyphicon glyphicon-th-list"></i> 返回列表
                                            </a>
                                        </div>
                                    </div>
                                    <div class="clear_float"></div>
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
<script type="text/javascript">

    var anchor = {
        id: "${anchorInfo.getId()!}",
        userId: "",
        platformId: "",
        liveId: "",

    }

    $("#submit").click(function () {
        anchor.userId = $("#anchorUser").val();
        anchor.platformId = $("#platform").val();
        anchor.liveId = $("#liveId").val();

        if (anchor.liveId == "") {
            layer.msg("直播平台的id不能为空", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/anchor/save",
                    {
                        id: "${anchorInfo.getId()!}",
                        userId: anchor.userId,
                        platformId: anchor.platformId,
                        liveId: anchor.liveId
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location = "/oa/anchor/index.html";
                            }, 1000);
                        }
                    }
            )
        }
        // $("#save").removeAttr("disabled")


    })
</script>

</body>
</html>