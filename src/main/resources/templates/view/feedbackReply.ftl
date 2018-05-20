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
                                                    <input id="title" type="text" class="form-control" value="${feedbackDTO.getFeedbackinfo().getTitle()!}"
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
                                                <img src="${detailDTO.getSendUser().getAvatar()!}" alt="ta的头像"
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
                                                <#if detailDTO.getBackTime()! == "">
                                                    <div class="comment-footer">
                                                        <a onclick="reply('${detailDTO.getId()}')">回复</a>
                                                    </div>
                                                </#if>

                                                  <#if  detailDTO.getBackTime()! !="">
                                                      <div class="comment">
                                                          <img src="/layui/images/model.jpg" alt=""
                                                               class="comment-avatar">
                                                          <div class="comment-body">
                                                              <div class="comment-text">
                                                                  <div class="comment-header">
                                                                      <a href="#"
                                                                         title="">你发送的</a><span>${detailDTO.getBackTime()!}</span>
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
                                    <form class="well padding-bottom-10" id="reply-box" style="display: none">
                                                                <span class="input-icon icon-right">
                                                                    <textarea rows="2" class="form-control"
                                                                              id="back-content"
                                                                              placeholder="请输入你的内容"></textarea>
                                                                </span>
                                        <div class="padding-top-10 text-align-right">
                                            <a onclick="detailReply()" class="btn btn-sm btn-primary">
                                                发送
                                            </a>
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
<script>
    var feedbackDetail = {
        id: "",
        backContent: ""

    }

    function reply(id) {
        $("#reply-box").show(100);
        feedbackDetail.id = id
    }

    function detailReply() {
        feedbackDetail.backContent = $("#back-content").val();
        console.log(feedbackDetail.id)
        if (feedbackDetail.backContent == "") {
            layer.msg("必须输入内容！！", {
                time: 1000
            });
        } else {
            $.post(
                    "/oa/feedback/detail/reply",
                    {
                        id: feedbackDetail.id,
                        backContent: feedbackDetail.backContent
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
    }

</script>
</body>
</html>