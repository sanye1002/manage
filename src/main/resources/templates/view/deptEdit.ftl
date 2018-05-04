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
            <#-- 编辑-->
                <div class="row edit_area_row" id="editDome">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-palegreen">
                                <span class="widget-caption">编辑部门信息</span>
                            </div>
                            <div class="widget-body">
                                <div>
                                    <form class="form-horizontal form-bordered" id="EditFrom" action="/rysj/region_Edit"
                                          method="post" role="form">
                                        <div class="form-group">
                                            <label for="editId"
                                                   class="col-sm-2 control-label no-padding-right">ID</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="editId" name="id" class="form-control"
                                                       value="${deptInfo.getId()}" disabled>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editName"
                                                   class="col-sm-2 control-label no-padding-right">部门名称</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="editName" name="name"
                                                       value="${deptInfo.getDeptName()}" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="editDeptNo"
                                                   class="col-sm-2 control-label no-padding-right">部门编号</label>
                                            <div class="col-sm-10">
                                                <input type="text" id="editDeptNo" name="deptNo"
                                                       value="${deptInfo.getDeptNo()}" class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="personnel"
                                                   class="col-sm-2 control-label no-padding-right">部门领导</label>
                                            <div class="col-sm-10">
                                                <select id="personnel" name="personnel" style="width:100%;">
                                                    <option value="${deptInfo.getId()!}">${deptInfo.getPersonnelName()!}</option>
                                                        <#list personnelInfoList! as personnel >
                                                            <option value="${personnel.getId()!}">${personnel.getName()!}</option>
                                                        </#list>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group ">
                                            <div class="text-right" style="margin-right:16px;">
                                                <a class="btn btn-success" id="EditSubmit" href="javascript:void(0);"><i
                                                        class="btn-label glyphicon glyphicon-ok"></i> 更新</a>
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

    $(function () {

        $("#EditSubmit").click(function () {

            var editId = $("#editId").val();
            var editName = $("#editName").val();
            var editDeptNo = $("#editDeptNo").val();
            var personnelId = $("#personnel").val();
            var personnelName = $("#personnel").find("option:selected").text();
            $.post(
                    "/oa/dept/save",
                    {
                        id:editId,
                        deptName:editName,
                        deptNo:editDeptNo,
                        personnelId:personnelId,
                        personnelName:personnelName
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location="/oa/dept/list.html";
                            },1000);
                        }
                    }
            )


        })
    })
</script>
</body>
</html>