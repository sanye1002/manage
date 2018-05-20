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
            <#--添加-->
                <div class="row add_area_row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">反馈类型信息</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form id="addform" class="form-horizontal form-bordered" role="form">
                                        <div class="form-group">
                                            <label for="platformName"
                                                   class="col-sm-2 control-label no-padding-right">类型名称</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="name" value="${feedbackType.getTypeName()!}" class="form-control" id="typeName">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="acceptPersonnel"
                                                   class="col-sm-2 control-label no-padding-right">处理人</label>
                                            <div class="col-sm-10">
                                                <select id="acceptPersonnel" name="acceptPersonnel" style="width:100%;">
                                                    <option value="${feedbackType.getAcceptPersonnelId()!}">${feedbackType.getAcceptPersonnelName()!}</option>
                                                        <#list personnelInfoList! as personnel >
                                                            <option value="${personnel.getId()!}">${personnel.getName()!}</option>
                                                        </#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group ">
                                            <div class="text-right" style="margin-right:16px;">
                                                <a class="btn btn-success" id="typeSubmit"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 提交</a>
                                                <a class="btn btn-warning" id="type_list" ><i
                                                        class="btn-label glyphicon glyphicon-th-list"></i>列表</a>
                                            </div>
                                        </div>
                                    </form>
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
    $("#type_list").click(function () {
        location = "/oa/feedback/type/list.html"
    })

    $(function () {
        $("#typeSubmit").click(function () {
            var id = "${feedbackType.getId()!}";
            var typeName = $("#typeName").val();
            var acceptPersonnelId = $("#acceptPersonnel").val();
            var acceptPersonnelName = $("#acceptPersonnel").find("option:selected").text();
            $.post(
                    "/oa/feedback/type/save",
                    {
                        id:id,
                        typeName:typeName,
                        acceptPersonnelId:acceptPersonnelId,
                        acceptPersonnelName:acceptPersonnelName
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location="/oa/feedback/type/list.html";
                            },1000);
                        }
                    }
            )
        })
    })
</script>
</body>
</html>