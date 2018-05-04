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
                    <div class="col-lg-12 col-sm-12 col-xs-12">

                        <div id="tabbedwizard" class="wizard wizard-tabbed" data-target="#tabbedwizardsteps">
                            <ul class="steps">
                                <li data-target="#tabbedwizardstep1" class="active"><span class="step">1</span>基本信息<span class="chevron"></span></li>
                                <li data-target="#tabbedwizardstep2" class=""><span class="step">2</span>审核信息<span class="chevron"></span></li>
                            </ul>

                        </div>
                        <div class="step-content" id="tabbedwizardsteps">
                            <div class="step-pane active" id="tabbedwizardstep1">This is step 1</div>
                            <div class="step-pane " id="tabbedwizardstep2">
                            </div>
                        </div>
                        <div class="actions actions-footer" id="tabbedwizard-actions">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-sm btn-prev" disabled="disabled"> <i class="fa fa-angle-left"></i>上一步</button>
                                <button type="button" class="btn btn-default btn-sm btn-next" data-last="完成">下一步<i class="fa fa-angle-right"></i></button>
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
        $('#tabbedwizard').wizard().on('finished', function (e) {
            Notify('Thank You! All of your information saved successfully.', 'bottom-right', '5000', 'blue', 'fa-check', true);
        });
        $('#WiredWizard').wizard();
    })

</script>
</body>
</html>