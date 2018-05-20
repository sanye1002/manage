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
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">反馈发布</span>
                            </div>
                            <div class="widget-body col-lg-12">
                                <div class="form-title">基本内容</div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>标题</label>
                                        <span class="input-icon icon-right">
                                                    <input id="title" type="text" class="form-control" <#if editStatus==1>value="${feedbackDTO.getFeedbackinfo().getTitle()!}"</#if>
                                                           <#if editStatus==1>disabled</#if>>
                                                    <i class="fa fa-user"></i>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label>类型</label>
                                        <span class="input-icon icon-right">
                                                <#if editStatus==1>
                                                        <select id="type" name="type" style="width:100%;" disabled>
                                                            <#list feedbackTypeList as type>
                                                                <option
                                                                    <#if feedbackDTO.getFeedbackinfo().getFeedbackTypeId() ==type.getId()>selected</#if>
                                                                        value="${type.getId()}">${type.getTypeName()}</option>
                                                            </#list>
                                                        </select>
                                                <#else >
                                                        <select id="type" name="type" style="width:100%;">
                                                            <#list feedbackTypeList as type>
                                                                <option value="${type.getId()}">${type.getTypeName()}</option>
                                                            </#list>
                                                        </select>
                                                </#if>

                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-12">
                                    <#if editStatus==1>
                                        <#list feedbackDTO.getDetailDTOList() as detailDTO >
                                            <div class="comment">
                                                <img src="${detailDTO.getSendUser().getAvatar()!}" alt="你的头像"
                                                     class="comment-avatar">
                                                <div class="comment-body">
                                                    <div class="comment-text">
                                                        <div class="comment-header">
                                                            <a href="#"
                                                               title="">${detailDTO.getSendUser().getName()!}</a><span>${detailDTO.getSendTime()!}</span>
                                                        </div>
                                                        ${detailDTO.getContent()!}
                                                    </div>
                                                </div>
                                                  <#if  detailDTO.getBackTime()! !="">
                                                      <div class="comment">
                                                          <img src="/layui/images/model.jpg" alt=""
                                                               class="comment-avatar">
                                                          <div class="comment-body">
                                                              <div class="comment-text">
                                                                  <div class="comment-header">
                                                                      <a href="#"
                                                                         title="">处理人</a><span>${detailDTO.getBackTime()!}</span>
                                                                  </div>
                                                                  ${detailDTO.getBackContent()!}
                                                              </div>
                                                          </div>
                                                      </div>
                                                  </#if>
                                            </div>
                                        </#list>
                                    </#if>

                                </div>
                                <div class="col-sm-12" style="margin-top: 10px">
                                    <#if editStatus==1>
                                        <#if feedbackDTO.getFeedbackinfo().getBackStatus()==0>

                                        <form class="well padding-bottom-10">
                                                                <span class="input-icon icon-right">
                                                                    <textarea rows="2" class="form-control"
                                                                              id="send-content"
                                                                              placeholder="请输入你的内容"></textarea>
                                                                </span>
                                            <div class="padding-top-10 text-align-right">
                                                <a class="btn btn-sm btn-primary" onclick="send('${feedbackDTO.getFeedbackinfo().getId()}')">
                                                    发送
                                                </a>
                                            </div>
                                        </form>
                                        </#if>
                                        <div style="float:right;margin-top:10px;">
                                        <#if feedbackDTO.getFeedbackinfo().getBackStatus()==0>
                                            <button id="finish" onclick="finish('${feedbackDTO.getFeedbackinfo().getId()}')" class="btn btn-success">完结</button>
                                        <#else>
                                            <button class="btn btn-success">已完结</button>
                                        </#if>
                                        </div>
                                    <#else >
                                        <form class="well padding-bottom-10">
                                                                <span class="input-icon icon-right">
                                                                    <textarea rows="2" class="form-control"
                                                                              id="send-content"
                                                                              placeholder="请输入你的内容"></textarea>
                                                                </span>
                                            <div class="padding-top-10 text-align-right">
                                                <a class="btn btn-sm btn-primary" onclick="save()">
                                                    发送
                                                </a>
                                            </div>
                                        </form>
                                    </#if>
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
    var detail = {
        id: "",
        sendContent: ""
    }

    var info = {
        title: "",
        typeId: "",
        content:""
    }

    function send(id) {
        detail.sendContent = $("#send-content").val()
        if (detail.sendContent == "") {
            layer.msg("发送内弄不能为空！", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/feedback/detail/send",
                    {
                        id: id,
                        sendContent: detail.sendContent
                    },
                    function (res) {
                        layer.msg(res.data.message, {
                            time: 1000
                        });
                        if (res.data.code == 0) {
                            var url = window.location.pathname;
                            var search = window.location.search;
                            setTimeout(function () {
                                location = url + search
                            }, 1000)
                        }
                    }
            )
        }
    }

    function save() {
        info.title = $("#title").val();
        info.typeId = $("#type").val();
        info.content = $("#send-content").val();

        if (info.title == "") {
            layer.msg("标题不能为空！！", {
                time: 1000
            });
        } else if (info.typeId == "") {
            layer.msg("必须选择反馈类型！！", {
                time: 1000
            });
        } else if (info.content == "") {
            layer.msg("必须输入内容！！", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/feedback/info/save",
                    {
                        title: info.title,
                        feedbackTypeId: info.typeId,
                        content:info.content
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location = res.data.url
                            }, 1000)
                        }
                    }
            )
        }
    }

    function finish(id) {
        $.post(
            "/oa/feedback/info/finish",
                {
                    id:id,
                    backStatus:1
                },
                function (res) {
                    layer.msg(res.message, {
                        time: 1000
                    });
                    if (res.code == 0) {
                        var url = window.location.pathname;
                        var search = window.location.search;
                        setTimeout(function () {
                            location = url + search
                        }, 1000)
                    }
                }
        )
    }
</script>
</body>
</html>