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
                <div class="mail-container">
                    <div class="mail-header" style="margin-left: 5px">
                        <div class="mail-title">
                            <strong>标题: ${noticeUserDTO.getDetail().getTitle()!}</strong>
                        </div>
                        <ul class="header-buttons pull-right">
                            <li>
                                <a class="tooltip-primary" href="/oa/notice/system/list/user.html" data-toggle="tooltip" data-original-title="返回"><i class="fa fa-mail-reply"></i></a>
                            </li>
                            <li>
                                <a class="tooltip-primary" data-toggle="tooltip" onclick="deleteUserRecording('${noticeUserDTO.getId()}')" data-original-title="删除"><i class="glyphicon glyphicon-remove"></i></a>
                            </li>
                        </ul>
                    </div>
                    <div class="mail-body" style="margin-left: 5px">
                        <div class="mail-info">
                            <div class="mail-sender">
                                <a href="#">
                                    <img src="/layui/images/model.jpg" class="img-circle" width="30">
                                    <span>${noticeUserDTO.getDetail().getUserName()!}</span>
                                     to <span>你</span>
                                </a>
                            </div>
                            <div class="mail-date">
                                ${noticeUserDTO.getDetail().getCreateTime()!}
                            </div>
                        </div>
                        <div class="mail-text">
                        ${noticeUserDTO.getDetail().getContent()!}
                        </div>

                    </div>
                </div>



                <#--<div class="page-body no-padding">

                </div>-->
            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<#include "../common/footjs.ftl">
<script>
    function deleteUserRecording(id) {
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
                    "/oa/notice/delete/user",
                    {id: id},
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                layer.closeAll();
                                location="/oa/notice/system/list/user.html"
                            }, 1000);
                        }
                    }
            )

        });
    }
</script>
</body>
</html>