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
                <!--管理-->
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                            </div>
                            <form class="search" method="get">
                                <div style="float:right;margin-right:2px;">
                                    <a href="/oa/dept/index.html" class="btn btn-success" name="pullModel-add_area_row">添加部门</a>
                                </div>
                                <div style="float:left;margin-right:2px;">
                                    <div class="form-group">
                                        <select name="search">
                                            <option value="id">部门列表</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="input-icon icon-right">
                                            <input type="text" value="" name="searchText" class="form-control"
                                                   placeholder="搜索">
                                            <i class="fa fa-search"></i>
                                        </span>
                                    </div>
                                </div>
                            </form>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>#ID</th>
                                    <th>部门名称</th>
                                    <th>部门编号</th>
                                    <th>部门领导</th>
                                    <th>上级编号</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list pageContent.getContent() as dept>
                                    <tr id="${dept.getId()}">
                                        <td>${dept.getId()}</td>
                                        <td id="name${dept.getId()}">${dept.getDeptName()}</td>
                                        <td id="deptNo${dept.getId()}">${dept.getDeptNo()}<a></a></td>
                                        <td>${dept.getPersonnelName()!}</td>
                                        <td>${dept.getNextNo()!}</td>
                                        <td>
                                            <a href="/oa/dept/edit?id=${dept.getId()}" name="pullModel-edit_area_row"
                                               class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                            <a id="remove-area-1" onclick="deleteDept(${dept.getId()})"
                                               class="btn btn-danger btn-xs edit"><i
                                                    class="glyphicon glyphicon-trash"></i> 删除</a>
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
<script type="text/javascript">
    function deleteDept(id) {
        layer.confirm('此操作为不可逆操作，客官已确认？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.closeAll();
            layer.msg('请稍等...', {
                icon: 16
                , shade: 0.01
            });
            //执行POST请求
            $.post(
                    "/oa/dept/delete",
                    {id: id},
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                $("#" + id).remove();
                                layer.closeAll();
                            }, 1000);
                        }
                    }
            )

        });
    }

</script>
</body>
</html>