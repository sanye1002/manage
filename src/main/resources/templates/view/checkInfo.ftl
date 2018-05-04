<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
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
                    <div class="col-lg-12 col-sm-12 col-xs-12">

                        <div id="tabbedwizard" class="wizard wizard-tabbed" data-target="#tabbedwizardsteps">
                            <ul class="steps">
                                <li data-target="#tabbedwizardstep1" class="active"><span class="step">1</span>申请人<span
                                        class="chevron"></span></li>
                                <li data-target="#tabbedwizardstep2" class=""><span class="step">2</span>申请类型<span
                                        class="chevron"></span></li>
                                <li data-target="#tabbedwizardstep3" class=""><span class="step">3</span>申请内容<span
                                        class="chevron"></span></li>
                                <li data-target="#tabbedwizardstep4" class=""><span class="step">4</span>抉择<span
                                        class="chevron"></span></li>
                            </ul>

                        </div>
                        <div class="step-content" id="tabbedwizardsteps" style="min-height: 255px">
                            <div class="step-pane active"
                                 id="tabbedwizardstep1">${checkInfo.getApplyPersonnelName()}</div>
                            <div class="step-pane " id="tabbedwizardstep2">${pageTitle}</div>
                            <div class="step-pane " id="tabbedwizardstep3">
                                <p>标题:${checkInfo.getTitle()}</p></br>
                                <p>金额:${checkInfo.getSalary()}</p></br>
                                <p>内容: ${checkInfo.getDescription()}</p></br>
                                <p>申请时间:${checkInfo.getApplyTime()!}</p></br>
                                <p><a class="btn btn-info btn-xs"
                                      onclick="showImg(${checkInfo.getApplyId()})"><i
                                        class="fa fa-edit"></i> 图片信息</a></p>
                            </div>
                            <div class="step-pane " id="tabbedwizardstep4">
                                <#if checkInfo.getCheckStatus()==0>
                                    <form id="addform" class="form-horizontal form-bordered" role="form">
                                        <div class="form-group">
                                            <label for="deptNo"
                                                   class="col-sm-2 control-label no-padding-right">审核结果：</label>
                                            <div class="col-sm-10">
                                                <label>
                                                    <input name="form-field-radio" value="1" type="radio"
                                                           class="colored-blue">
                                                    <span class="text">通过 </span>
                                                </label>
                                                <label>
                                                    <input name="form-field-radio" value="0" type="radio"
                                                           class="colored-success">
                                                    <span class="text">驳回</span>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="deptNo"
                                                   class="col-sm-2 control-label no-padding-right">操作备注：</label>
                                            <div class="col-sm-10">
                                                        <textarea class="form-control" name="description"
                                                                  id="description"
                                                                  rows="3"
                                                                  style="resize: none;"></textarea>
                                                <i>认真仔细填写备注信息，以便于审核!</i>
                                            </div>
                                        </div>
                                    </form>
                                </#if>
                            </div>

                        </div>
                        <div class="actions actions-footer" id="tabbedwizard-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-sm btn-prev" disabled="disabled"><i
                                        class="fa fa-angle-left"></i>上一步
                                </button>
                                <button type="button" class="btn btn-default btn-sm btn-next" data-last="完成">下一步<i
                                        class="fa fa-angle-right"></i></button>
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
        var check = {
            id: "${checkInfo.getId()}",
            resultStatus: "",
            description: "",
            checkStatus: "${checkInfo.getCheckStatus()}"
        }
        $("input[type=radio]").click(function () {
            check.resultStatus = this.value;
        })
        $('#simplewizardinwidget').wizard();
        $('#simplewizard').wizard();
        $('#tabbedwizard').wizard().on('finished', function (e) {
            if (check.checkStatus == 1) {
                Notify('你完成审核！', 'bottom-right', '1000', 'blue', 'fa-check', true);
                setTimeout(function () {
                    location = "/oa/check/list.html"
                }, 1000)
            } else {
                check.description = $("#description").val();
                if (check.resultStatus == "") {
                    Notify('请选择结果！', 'bottom-right', '1000', 'danger', 'fa-check', true);
                } else if (check.description == "") {
                    Notify('请输入内容！', 'bottom-right', '1000', 'danger', 'fa-check', true);
                } else {
                    $.post(
                            "/oa/check/save",
                            {
                                id: check.id,
                                resultStatus: check.resultStatus,
                                remark: check.description
                            },
                            function (res) {
                                if (res.code == 0) {
                                    Notify('谢谢你完成审核！', 'bottom-right', '1000', 'blue', 'fa-check', true);
                                    setTimeout(function () {
                                        location = "/oa/check/list.html"
                                    }, 1000)
                                }
                            }
                    )

                }
            }
        });
        $('#WiredWizard').wizard();

    })

</script>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    function showImg(id) {
        var type = "";
        var getType = "${checkInfo.getType()}"
        if (getType=="主播工资提现"||getType=="物品借记"){
            layer.msg('无图片信息', {
                time: 1200
            });
        }else {
            if(getType=="日常开支"){
                type = "spending"
            }

            if(getType =="工作人员工资预支"){
                type = "personnelSalaryAdvance"
            }
            if(getType =="主播工资预支"){
                type = "anchorSalaryAdvance"
            }
            $.getJSON('/layer/'+type+'/' + id, function (json) {
                layer.photos({
                    photos: json
                    , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                });
            });
        }

    }
</script>
</body>
</html>