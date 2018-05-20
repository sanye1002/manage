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
                        <div class="well with-header with-footer">
                            <div class="header bordered-sky">
                            ${pageTitle}
                            </div>
                            <div class="col-sm-2">
                                <div class="form-group">
                                    <select id="platform">
                                            <#list anchorInfoList as plat>
                                                <option <#if plat.getPlatformId()==platform> selected </#if> value="${plat.getPlatformId()}">${plat.getLivePlatform()}</option>
                                            </#list>
                                    </select>
                                </div>
                            </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>月份</th>
                                        <th>播主昵称</th>
                                        <th>陌陌号</th>
                                        <th>播主等级</th>
                                        <th>总陌币</th>
                                        <th>结算方式</th>
                                        <th>税前收益</th>
                                        <th>税后收益</th>
                                        <th>职业返点</th>
                                        <th>节奏代刷</th>
                                        <th>借款</th>
                                        <th>最终工资</th>
                                        <th>已提现</th>
                                        <th>剩余可提</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list pageContent.getContent() as mm>
                                        <tr id="${mm.getId()}">
                                            <td>${mm.getMonth()!}</td>
                                            <td>${mm.getName()!}</td>
                                            <td>${mm.getLiveId()!}</td>
                                            <td>${mm.getGrade()!}</td>
                                            <td>${mm.getAllMoBi()!}</td>
                                            <td>${mm.getBillingMethod()!}</td>
                                            <td>${mm.getBeforeTax()!}</td>
                                            <td>${mm.getAfterTax()!}</td>
                                            <td>${mm.getFanDian()!}</td>
                                            <td>${mm.getDaiShua()!}</td>
                                            <td>${mm.getJieKuan()!}</td>
                                            <td>${mm.getSalary()!}</td>
                                            <td>${mm.getSystemTX()!}</td>
                                            <td>${mm.getMentionable()!}</td>
                                            <td>
                                                <a id="tiXian" onclick="tiXian('${userInfo.getPassword()}',${mm.getId()})" class="btn btn-sky btn-sm  btn-follow">
                                                    <i class="fa fa-arrow-circle-o-right"></i>
                                                    提现
                                                </a>
                                            </td>

                                        </tr>
                                        </#list>

                                    </tbody>
                                </table>
                            </div>
                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&platform=${platform}">首页</a></li>
                                                <#if currentPage lte 1>
                                                    <li class="disabled"><a>上一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage-1}&size=${size}&platform=${platform}">上一页</a></li>

                                                </#if>

                                           <#list 1..pageContent.getTotalPages() as index>
                                               <#if currentPage == index >
                                                     <li class="active"><a href="#">${index}</a></li>
                                               <#else>
                                                    <li><a href="${url}?page=${index}&size=${size}&platform=${platform}">${index}</a></li>
                                               </#if>
                                           </#list>
                                            <#if currentPage gte pageContent.getTotalPages()>
                                                <li class="disabled"><a>下一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage+1}&size=${size}&platform=${platform}">下一页</a></li>
                                            </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&platform=${platform}">尾页</a>
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
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    function tiXian(password,id) {
        layer.prompt({title: '输入登录密码，并确认', formType: 1}, function(pass, index){
            if ($.md5(pass)!=password){
                layer.msg('密码错误！！！',{
                    time:1200
                });
            }else {
                layer.close(index);

                layer.prompt({title: '请输入提现金额，并确认', formType: 0}, function(text, index){

                    layer.close(index);
                    var platformId = "${platform}"
                    /* 提现present remark advanceSalary*/
                    $.post(
                            "/oa/withdraw/salary",
                            {
                                "salary":text,"id":id,"platformId":platformId

                            },
                            function (res) {

                                if (res.code == 0){
                                    layer.msg('金额：'+text+',提现申请中！;',{
                                        time:1500
                                    });

                                }
                                if (res.code > 0){
                                    layer.msg(res.message,{
                                        time:1500
                                    });
                                }
                            }
                    )

                });
            }
        });
    }
</script>

</body>
</html>