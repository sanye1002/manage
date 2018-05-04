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
                                <span class="widget-caption">添加部门</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form id="addform" class="form-horizontal form-bordered" role="form">
                                        <div class="form-group">
                                            <label for="typeName"
                                                   class="col-sm-2 control-label no-padding-right">部门名称</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="name" class="form-control" id="typeName">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="deptNo"
                                                   class="col-sm-2 control-label no-padding-right">部门编号</label>
                                            <div class="col-sm-10">
                                                <input type="text" name="deptNo" class="form-control" id="deptNo">
                                            </div>
                                        </div>
                                        <div class="form-group ">
                                            <div class="text-right" style="margin-right:16px;">
                                                <a class="btn btn-success" id="submit" href="javascript:void(0);"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 提交</a>
                                                <a class="btn btn-warning" id="dept_list" href="javascript:void(0);"><i
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
    $("#dept_list").click(function () {
        location = "/oa/dept/list.html"
    })

    $("#submit").click(function () {

        var nameDemo = $("input[name='name']").val();
        var deptNo = $("input[name='deptNo']").val();
        if (nameDemo == null || nameDemo == "") {
            layer.msg("部门名称不能为空，请输入！", {
                time: 1000
            });
        } else if (deptNo == null || deptNo == "") {
            layer.msg("部门编号不能为空，请输入！", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/dept/save",
                    {
                        deptNo: deptNo,
                        deptName: nameDemo
                    },
                    function (res) {
                        layer.msg(res.message);
                        if (res.code == 0) {
                            setTimeout(
                                    function () {
                                        location = "/oa/dept/index.html"
                                    }, 1000
                            )
                        }
                    }
            )
        }

    })
</script>
</body>
</html>