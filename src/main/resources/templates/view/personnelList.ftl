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
                    <li class="active"> ${pageTitle}</li>
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
                    <div class="col-xs-12 col-md-12">
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                                <span class="badge badge-sky badge-square">
                                     提示：点击编辑可查看详细信息
                                    </span>
                            </div>
                            <form class="search" method="get">
                                <div style="float:right;margin-right:2px;">
                                    <a href="/oa/personnel/index.html" target="_self" class="btn btn-success">添加人员</a>
                                </div>
                                <div style="float:left;margin-right:2px;">
                                    <div class="form-group">
                                        <select name="search">
                                            <option value="name">姓名</option>
                                            <option value="nickname">昵称</option>
                                            <option value="phone">手机号码</option>
                                            <option value="qq">QQ</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                            <span class="input-icon icon-right">
                                                <input type="text" value="" name="searchText" class="form-control"
                                                       placeholder="按回车搜索">
                                                <i class="fa fa-search"></i>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <span>状态：</span>
                                            <label>
                                                <input type="checkbox" class="colored-blue" value="1"
                                                <#if status == 1>checked="checked"</#if>>
                                                <span class="text">正常</span>
                                            </label>
                                            <label>
                                                <input type="checkbox" value="0" class="inverted"
                                                <#if status == 0>checked="checked"</#if>>
                                                <span class="text">离职</span>
                                            </label>

                                        </div>


                                    </div>
                                </div>
                            </form>
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>

                                    <th>头像</th>
                                    <th>姓名</th>
                                    <th>年龄</th>
                                    <th>电话</th>
                                    <th>性别</th>
                                    <th>角色</th>
                                    <th>部门</th>
                                    <th>添加时间</th>
                                    <th colspan="3">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getPageContent() as items>
                                <tr id="${items.id}" <#if items.id== items.getDeptDTO().getPersonnelInfo().getId()!>class="success"</#if> style="height: 50px;text-align: center">

                                    <td><img src="${items.getUserInfo().getAvatar()!}" height="40px"></td>
                                    <td>${items.getUserInfo().getName()}</td>
                                    <td>${items.getUserInfo().getAge()}</td>
                                    <td>${items.getUserInfo().getPhone()}</td>
                                    <td>
                                        <#if items.getUserInfo().getSex() == 1>男</#if>
                                        <#if items.getUserInfo().getSex() == 0>女</#if>
                                    </td>
                                    <td>${items.getRole().getName()}</td>
                                    <td>${items.getDeptDTO().getDeptName()}</td>
                                    <td>${items.getUserInfo().getCreateDate()}</td>
                                    <td>
                                        <a href="/oa/personnel/index.html?id=${items.getId()}"
                                           class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 编辑</a>
                                    </td>
                                    <td>
                                        <a class="btn btn-danger btn-xs edit" onclick="dele(${items.id})"><i
                                                class="glyphicon glyphicon-trash"></i> 删除</a>
                                    </td>
                                    <td>
                                        <a class="btn btn-info btn-xs" onclick="showSFZ(${items.getUserInfo().getId()})"><i class="fa fa-edit"></i> 身份证</a>
                                    </td>

                                </tr>
                                </#list>

                                </tbody>
                            </table>
                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&status=${status}">首页</a></li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage-1}&size=${size}&status=${status}">上一页</a></li>
                                            </#if>
                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li><a href="${url}?page=${index}&size=${size}&status=${status}">${index}</a></li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage+1}&size=${size}&status=${status}">下一页</a></li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&status=${status}">尾页</a>
                                        </li>
                                    </ul>
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
<script>
    $(function () {
        $("input[type=checkbox]").click(function () {
            var id = $(this).val();
            location = "/oa/personnel/list.html?status=" + id;

        })
    })

    function dele(id) {
        layer.confirm('客官确定要进行删除？确定该员工无下级员工？', {
            btn: ['确定', '再想想'] //按钮
        }, function () {
            $.post(
                    "/oa/personnel/delete",
                    {id: id},
                    function (res) {
                        if (res.code == 0) {

                            $("#" + id).remove();
                            layer.msg('已删除', {icon: 1});

                        }
                        if (res.code != 0) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                    }
            )

        });
    }
</script>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
 function showSFZ(id) {
     $.getJSON('/layer/photos/'+id, function(json){
         layer.photos({
             photos: json
             ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
         });
     });
 }
</script>
</body>
</html>