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

                           <#-- <div style="float:right;margin-right:2px;">
                                <a id="printExcel" class="btn btn-magenta">打印Excel</a>
                            </div>-->
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
                                            <input type="checkbox" class="colored-blue" value="1"
                                                   <#if resultStatus==1>checked</#if>>
                                            <span class="text">借款</span>
                                        </label>
                                        <label>
                                            <input type="checkbox" value="0" class="colored-danger"
                                                   <#if resultStatus==0>checked</#if>>
                                            <span class="text">未通过</span>
                                        </label>
                                    </div>
                                </div>
                            </div>

                            <div style="float:right;margin-right:2px;">

                                <div class="form-group">
                                    <select id="selectType">
                                        <option value="1" <#if checkStatus==1>selected</#if> >已借款 <span
                                                class="badge">${pass}</span></option>
                                        <option value="0" <#if checkStatus==0>selected</#if> >待审核 <span
                                                class="badge">${noPass}</span></option>
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
                                    <th>归还状态</th>
                                    <th>归还时间</th>
                                    <th>审核流程结果</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list pageContent.getPageContent() as p>
                                <tr id="${p.getAnchorSalaryAdvance().getId()}"
                                    <#if p.getAnchorSalaryAdvance().getBackStatus()==1>class="success" </#if>
                                    <#if p.getAnchorSalaryAdvance().getBackStatus()==0>class="warning" </#if>
                                >
                                    <td>${p.getAnchorSalaryAdvance().getMonth()}</td>
                                    <td>${p.getAnchorSalaryAdvance().getAnchorName()!}</td>
                                    <td>${p.getAnchorSalaryAdvance().getCreateTime()}</td>
                                    <td>${p.getAnchorSalaryAdvance().getTitle()}</td>
                                    <td style="max-width: 400px">${p.getAnchorSalaryAdvance().getDescription()}</td>
                                    <td>${p.getAnchorSalaryAdvance().getSalary()}</td>
                                    <td>
                                            <#if p.getAnchorSalaryAdvance().getBackStatus()==0>
                                                未归还
                                            </#if>
                                            <#if p.getAnchorSalaryAdvance().getBackStatus()==1>
                                                已归还
                                            </#if>
                                    </td>
                                    <td>${p.getAnchorSalaryAdvance().getBackTime()!}</td>

                                    <td>
                                             <#list p.getCheckInfoList() as check>
                                           <#if check.getCheckStatus()==1>
                                               审核人员：${check.getCheckPersonnelName()}
                                               /  <#if check.getResultStatus()==1>已通过 <#else>未通过 </#if>
                                           <#else >
                                                 待审核人员：${check.getAcceptPersonnelName()} /待审核
                                           </#if>
                                             </#list>
                                    </td>


                                    <td>
                                        <#if p.getAnchorSalaryAdvance().getCheckStatus()==0>
                                             <a class="btn btn-info btn-xs" onclick="grants(${p.getAnchorSalaryAdvance().getId()},1)"><i class="fa fa-edit"></i> 拨款
                                             </a>
                                            <a class="btn btn-danger btn-xs edit" onclick="grants(${p.getAnchorSalaryAdvance().getId()},0)"><i class="fa fa-frown-o"></i>
                                                撤回</a>
                                        <#else >
                                            <a class="btn btn-default btn-xs"><i class="fa fa-edit"></i> 已处理 </a>
                                        </#if>

                                        <a class="btn btn-yellow btn-xs shiny"
                                           onclick="showImg(${p.getAnchorSalaryAdvance().id})"><i
                                                class="fa fa-check"></i> 图片</a>

                                        <a class="btn btn-magenta btn-xs shiny" onclick="openPayMessage('${p.getUserInfo().aliPay!}','${p.getUserInfo().bankUserName!}','${p.getUserInfo().bankType!}','${p.getUserInfo().bankCardNumber!}')" ><i class="fa fa-shopping-cart"></i> 银行信息</a>

                                        <a class="btn btn-danger btn-xs"><i class="fa fa-times"></i>
                                            删除</a>
                                        <#if p.getAnchorSalaryAdvance().getBackStatus()==0>
                                             <a class="btn btn-info btn-xs" onclick="back(${p.getAnchorSalaryAdvance().getId()})"><i class="fa fa-edit"></i> 归还
                                             </a>
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                            <div class="margin-top-30 text-align-right">
                                <div class="next">
                                    <ul class="pagination">
                                        <li><a href="${url}?page=1&size=${size}&resultStatus=${resultStatus}&checkStatus=${checkStatus}">首页</a></li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li><a href="${url}?page=${currentPage-1}&size=${size}&resultStatus=${resultStatus}&checkStatus=${checkStatus}">上一页</a></li>

                                            </#if>

                                               <#list 1..pageContent.getTotalPages() as index>
                                                   <#if currentPage == index >
                                                         <li class="active"><a href="#">${index}</a></li>
                                                   <#else>
                                                        <li><a href="${url}?page=${index}&size=${size}&resultStatus=${resultStatus}&checkStatus=${checkStatus}">${index}</a></li>
                                                   </#if>
                                               </#list>
                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li><a href="${url}?page=${currentPage+1}&size=${size}&resultStatus=${resultStatus}&checkStatus=${checkStatus}">下一页</a></li>
                                                </#if>
                                        <li>
                                            <a href="${url}?page=${pageContent.getTotalPages()}&resultStatus=${resultStatus}&checkStatus=${checkStatus}">尾页</a>
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

    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#month'
            , type: 'month'
        });
    });
    function back(id) {
        layer.confirm('是否确定已归还？', {
                    btn: ['确认', '取消'] //按钮
                }, function () {

                    layer.msg('请稍等...', {
                        time: 1000
                    });
                    //执行POST请求
            $.post(
                    "/oa/anchorSalaryAdvance/back",
                    {
                        id: id
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        var url =  window.location.pathname;
                        var search = window.location.search;
                        if (res.code==0){
                            setTimeout(function () {
                                location=url+search
                            },1000)
                        }
                    }
            )
                }
        )
    }

    function grants(id, resultStatus) {
        layer.prompt({title: '输入备注，并确认', formType: 2}, function (remark, index) {
            if (remark == "") {
                layer.msg('备注必填！！！', {
                    time: 1200
                });
            } else {
                layer.close(index);
                $.post(
                        "/oa/anchorSalaryAdvance/grants",
                        {
                            id: id,
                            remark: remark,
                            resultStatus: resultStatus
                        },
                        function (res) {
                            layer.msg(res.message, {
                                time: 1000
                            });
                            var url =  window.location.pathname;
                            var search = window.location.search;
                            if (res.code==0){
                                setTimeout(function () {
                                    location=url+search
                                },1000)

                            }
                        }
                )

            }
        });
    }

    $(function () {

        $("#fa-search").click(function () {
            var month = $("#month").val();
            if (month != "") {
                location = "/oa/anchorSalaryAdvance/checkList.html?month=" + month
            }
        })
        $("input[type=checkbox]").click(function () {
            var resultStatus = $(this).val();
            var month = $("#month").val();
            location = "/oa/anchorSalaryAdvance/checkList?month=" + month + "&checkStatus=1&resultStatus=" + resultStatus

        })
        $("#selectType").change(function () {
            var month = $("#month").val();
            var checkStatus = $("#selectType").val();
            var resultStatus = $("input[type=checkbox]:checked").val();
            if (resultStatus==1){
                resultStatus =0
            }
            location = "/oa/anchorSalaryAdvance/checkList?month=" + month + "&resultStatus=" + resultStatus + "&checkStatus=" + checkStatus
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
<script>
    function openPayMessage(pay,username,type,number) {
        layer.open({
            title:'银行信息',
            skin: 'ayui-layer-molv', //样式类名
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            content: '开户姓名：'+username+';  <br>开户银行：'+type+';  <br>银行账户：'+number+';  <br>支付宝：'+pay

        });
    }
    function showImg(id) {
        $.getJSON('/layer/anchorSalaryAdvance/' + id, function (json) {
            layer.photos({
                photos: json
                , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
            });
        });
    }
</script>
</body>
</html>