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
                            <div class="form-group">
                                <div class="text-right">
                                    <a href="/oa/platform/index.html" class="btn btn-success"
                                       name="pullModel-add_area_row">添加平台</a>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>#ID</th>
                                    <th>直播平台</th>
                                    <th>直播前缀</th>
                                    <th>负责人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list pageContent.getContent() as platform>
                                    <tr id="${platform.getId()}" <#if platform.id%2!=0>class="success"</#if>>
                                        <td>${platform.getId()}</td>
                                        <td id="name${platform.getId()}">${platform.getName()}</td>
                                        <td id="deptNo${platform.getId()}">${platform.getLiveUrl()}<a></a></td>
                                        <td>${platform.getUsername()!}</td>
                                        <td>
                                            <a href="/oa/platform/index?id=${platform.getId()}"
                                               name="pullModel-edit_area_row"
                                               class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                            <a id="remove-area-1" onclick="deleteDept(${platform.getId()})"
                                               class="btn btn-danger btn-xs edit"><i
                                                    class="glyphicon glyphicon-trash"></i> 删除</a>
                                        </td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                            </div>

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
                    "/oa/platform/delete",
                    {id: id},
                    function (res) {
                        layer.msg(res.data.message, {
                            time: 1000
                        });
                        if (res.data.code == 0) {
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