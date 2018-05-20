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
                                <span class="widget-caption">信息通知发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="form-title">基本内容</div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>人员类型</label>
                                        <span class="input-icon icon-right">
                                                    <select id="userType" name="userType" style="width:100%;">
                                                                <option value="all">全部</option>
                                                                <option value="anchor">主播</option>
                                                                <option value="personnel">工作人员</option>
                                                    </select>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>平台/部门</label>
                                        <span class="input-icon icon-right">
                                           <select id="area" name="area" style="width:100%;">
                                               <option value="0">全部</option>
                                           </select>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>人员选择</label>
                                        <span class="input-icon icon-right">
                                           <select id="user" name="user" style="width:100%;">
                                               <option value="0">全部</option>
                                           </select>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <div class="form-group">
                                        <label>标题</label>
                                        <span class="input-icon icon-right">
                                                    <input id="article_title" type="text" class="form-control">
                                                    <i class="fa fa-user"></i>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <textarea id="demo" style="display: none;"></textarea>
                                    <div style="float:right;margin-top:10px;">
                                        <button id="send-btn" onclick="send()" class="btn btn-success">保存</button>
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
    var notice={
        content:"",
        aid:"",
        value:"",
        uid:"",
        title:""
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
            index = layedit.build('demo'); //建立编辑器
        });

    })

    function send() {

        notice.aid = $("#area").val();
        notice.value = $("#userType").val();
        notice.uid = $("#user").val();
        notice.content = layedit.getContent(index)
        notice.title = $("#article_title").val()
        if (notice.content == "") {
            layer.msg("通知内容不能为空！", {
                time: 1000
            });
        } else if(notice.title==""){
            layer.msg("标题不能为空！", {
                time: 1000
            });
        }else {
            $("#send-btn").attr("disabled","disabled");
            $.post(
                    "/oa/notice/system/send",
                    {
                        content: notice.content,
                        aid: notice.aid,
                        value: notice.value,
                        uid: notice.uid,
                        title: notice.title
                    },
                    function (res) {
                        if (res.code == 0) {
                            layer.msg(res.data.message, {
                                time: 2000
                            });
                            if (res.data.code == 0) {

                                setTimeout(function () {
                                    location = "/oa/notice/system/list.html"
                                }, 2000)
                            }else {
                                $('#send-btn').removeAttr("disabled");
                            }
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
    $(function () {
        $("#userType").change(function () {
            var userType = $("#userType").val();
            $("#user").html("<option value=0>全部</option>")
            if (userType != "all") {
                $("#area").html("<option value=0>全部</option>")
                $.post(
                        "/oa/notice/findArea",
                        {value: userType},
                        function (res) {
                            if (res.code == 0) {
                                layer.msg("请进行下一个选择！", {
                                    time: 1000
                                });
                                var area = eval(res.data.area)
                                if (userType == "personnel") {
                                    console.log(area)
                                    for (var i in area) {
                                        $("#area").append("<option value=" + area[i].id + ">" + area[i].deptName + "</option>")
                                    }
                                } else {
                                    console.log(area)
                                    for (var i in area) {
                                        $("#area").append("<option value=" + area[i].id + ">" + area[i].name + "</option>")
                                    }
                                }
                            } else {
                                layer.msg(res.message, {
                                    time: 1000
                                });
                            }

                        }
                )
            }
        })
        $("#area").change(function () {
            var userType = $("#userType").val();
            var id = $("#area").val();
            if (id != "0") {
                if (userType != "all") {
                    $("#user").html("<option value=0>全部</option>")
                    $.post(
                            "/oa/notice/findUser",
                            {
                                value: userType,
                                id: id
                            },
                            function (res) {
                                if (res.code == 0) {
                                    layer.msg("请进行下一个选择！", {
                                        time: 1000
                                    });
                                    var user = eval(res.data.user)
                                    for (var i in user) {
                                        $("#user").append("<option value=" + user[i].id + ">" + user[i].name + "</option>")
                                    }
                                } else {
                                    layer.msg(res.message, {
                                        time: 1000
                                    });
                                }

                            }
                    )
                }
            }
        })
    })


</script>
</body>
</html>