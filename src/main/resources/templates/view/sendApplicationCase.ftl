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
                    <div class="col-xs-12 col-md-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">事件发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="form-title">基本内容</div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>标题</label>
                                        <span class="input-icon icon-right">
                                                    <input id="article_title" type="text" class="form-control" value="${applicationCase.getTitle()!}">
                                                    <i class="fa fa-user"></i>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>是否外出</label>
                                        <div class="radio">
                                            <label>
                                                <input name="form-field-radio" value="1" type="radio" <#if applicationCase.getGoOut()! == 1>checked</#if>
                                                       class="colored-blue">
                                                <span class="text">外出 </span>
                                            </label>
                                            <label>
                                                <input name="form-field-radio" value="0" type="radio" <#if applicationCase.getGoOut()! == 0>checked</#if>
                                                       class="colored-success">
                                                <span class="text">不外出</span>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="clear_float"></div>
                                <div class="form-title">详细内容信息</div>
                                <div class="col-sm-12">
                                    <textarea id="demo" style="display: none;">
                                        ${applicationCase.getContent()!}
                                    </textarea>
                                    <div style="float:right;margin-top:10px;">
                                        <button id="save" onclick="send()" class="btn btn-success" >保存</button>
                                    </div>
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
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    var Case = {
        content: "",
        goOut: "",
        id: "${applicationCase.getId()!}",
        title: ""
    }
    var index;
    var layedit;
    $(function () {

        layui.use('layedit', function () {
            layedit = layui.layedit;
            layedit.set({
                uploadImage: {
                    url: '/upload/img/notice' //接口url
                    , type: 'post' //默认post
                }
            });
            index = layedit.build('demo', {
                height: 600
            }); //建立编辑器
        });
        $("input[type=radio]").click(function () {
            Case.goOut = this.value;
        })
    })

    function send() {
        Case.content = layedit.getContent(index)
        Case.title = $("#article_title").val();
        Case.goOut = $("input[type=radio]:checked").val();
        if (Case.content.trim() == "") {
            layer.msg("通知内容不能为空！", {
                time: 1000
            });
        } else if (Case.title == "") {

            layer.msg("标题不能为空！", {
                time: 1000
            });

        } else {
            $("#save").attr("disabled", "disabled");
            $.post(
                    "/oa/case/application/save",
                    {
                        content: Case.content,
                        id: Case.id,
                        goOut: Case.goOut,
                        title: Case.title
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location = "/oa/case/application/index.html"
                            }, 1000)
                        } else {
                            $('#send-btn').removeAttr("disabled");
                            layer.msg(res.message, {
                                time: 1000
                            });
                        }
                    }
            )
        }
    }



</script>
</body>
</html>