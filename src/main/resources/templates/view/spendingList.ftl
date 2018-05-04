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
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                            </div>

                                <div style="float:right;margin-right:2px;">
                                    <a id="printExcel" class="btn btn-magenta">打印Excel</a>
                                </div>
                                <div class="col-sm-2">
                                    <div class="form-group">
                                            <span class="input-icon icon-right">
                                                <input type="text" value="${month}" id="month" class="form-control">
                                                <i id="fa-search" class="fa fa-search"></i>
                                            </span>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <div class="checkbox">
                                            <span>结果：</span>
                                            <label>
                                                <input type="checkbox" class="colored-blue" value="1" <#if resultStatus==1>checked</#if>>
                                                <span class="text">通过</span>
                                            </label>
                                            <label>
                                                <input type="checkbox" value="0" class="colored-danger" <#if resultStatus==0>checked</#if>>
                                                <span class="text">未通过</span>
                                            </label>
                                        </div>
                                    </div>
                                </div>

                                <div style="float:right;margin-right:2px;">

                                    <div class="form-group">
                                        <select id="selectType">
                                            <option value="1" <#if resultStatus==1>selected</#if> >已通过 <span class="badge">${pass}</span></option>
                                            <option value="0" <#if resultStatus==0>selected</#if> >待审核 <span class="badge">${noPass}</span></option>
                                        </select>
                                    </div>
                                </div>

                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>月份</th>
                                    <th>申请人</th>
                                    <th>申请时间</th>
                                    <th>标题</th>
                                    <th>内容</th>
                                    <th>金额</th>
                                    <th>审核状态</th>
                                    <th>审核人员</th>
                                    <th>审核结果</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getContent() as p>
                                <tr id="${p.id}" <#if p.getResultStatus()==1>class="success" </#if>>
                                    <td>${p.getMonth()}</td>
                                    <td>${p.getPersonnelName()}</td>
                                    <td>${p.getTitle()}</td>
                                    <td style="max-width: 400px;overflow: scroll">${p.getDescription()}</td>
                                    <td>${p.getSalary()}</td>
                                    <td>
                                           <#if p.getCheckStatus()==0>
                                               未审核
                                           <#else>
                                                已审核
                                           </#if>
                                    </td>
                                    <td>${p.checkPersonnelName!}</td>
                                    <#if p.getCheckStatus()==1>
                                        <#if p.getResultStatus()==0>
                                                    驳回
                                        <#else>
                                                    通过
                                        </#if>
                                    <#else>
                                                未审核
                                    </#if>

                                     <#if p.getResultStatus()==0>
                                        <td>
                                            <a class="btn btn-info btn-xs"><i class="fa fa-edit"></i> 通过 </a>
                                            <a class="btn btn-danger btn-xs edit"><i class="fa fa-frown-o"></i>
                                                撤回</a>
                                            <a class="btn btn-default btn-xs shiny" onclick="showImg(${p.id})"><i
                                                    class="fa fa-check"></i> 图片</a>
                                            <a class="btn btn-success btn-xs edit"
                                               onclick="showRemark('${p.description}')"><i class="fa fa-comment"></i>
                                                详细内容</a>
                                        </td>
                                        <#else >

                                     </#if>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
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

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#month'
            , type: 'month'
        });
    });
    $(function () {
        $("#fa-search").click(function () {
            var month = $("#month").val();
            if (month != "") {
                location = "/oa/spending/list.html?month=" + month
            }
        })
        $("input[type=checkbox]").click(function () {
            var checkStatus = $(this).val();
            var month = $("#month").val();
            var resultStatus = $("#selectType").val();
            location = "/oa/spending/list?month="+month+"&checkStatus="+checkStatus+"&resultStatus="+resultStatus

        })
        $("#selectType").change(function () {
            var month = $("#month").val();
            var resultStatus = $("#selectType").val();
            var checkStatus = $("input[type=checkbox]:checked").val();
            location="/oa/spending/list?month="+month+"&resultStatus="+resultStatus+"&checkStatus="+checkStatus
        })
        $("#printExcel").click(function () {
            layer.confirm('是否下载Excel？', {
                        btn: ['确认', '取消'] //按钮
                    }, function () {

                        layer.msg('请稍等...', {
                            time: 1000
                        });
                        //执行POST请求
                        setTimeout(function () {
                            location = "/excel/export/${month}/1"
                        }, 1000)
                    }
            )

        });
    })

</script>

</body>
</html>