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
                    <div class="col-lg-12 col-sm-6 col-xs-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div id="registration-form">

                                        <div class="form-title">基本信息</div>
                                        <#--<div class="col-sm-12">
                                            <div class="form-group">
                                                <label>工号ID</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" disabled value="系统分配" class="form-control">
                                                    <i class="fa fa-user"></i>
                                                </span>
                                            </div>
                                        </div>-->
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>工号ID</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" disabled value="系统分配" class="form-control">
                                                    <i class="fa fa-user"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="name">姓名（不可修改）</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" name="name" id="name" class="form-control"
                                                           value="${personnelInfo.getName()!}">
                                                    <i class="glyphicon glyphicon-fire"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="sex">性别</label>
                                                <span class="input-icon icon-right">
                                                    <select id="sex" name="sex" style="width:100%;">
                                                            <option value="0"
                                                                    <#if personnelInfo.getSex() == 0>selected</#if> >女</option>
                                                            <option value="1"
                                                                    <#if personnelInfo.getSex() == 1>selected</#if>>男</option>
                                                    </select>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="dept">部门</label>
                                                <span class="input-icon icon-right">
                                                    <select id="dept" name="dept" style="width:100%;">
                                                        <#list deptInfoList as dept >
                                                              <option
                                                                  <#if personnelInfo.getDeptNo()! == dept.deptNo>selected</#if> value="${dept.deptNo}">${dept.getDeptName()}</option>
                                                        </#list>
                                                    </select>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="status">状态</label>
                                                <span class="input-icon icon-right">
                                                    <select id="status" name="status" style="width:100%;">
                                                            <option value="1"
                                                                    <#if personnelInfo.getStatus()! == 1>selected</#if>>在职</option>
                                                            <option value="0"
                                                                    <#if personnelInfo.getStatus()! == 0>selected</#if>>离职</option>
                                                    </select>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label for="age">年龄</label>
                                                <span class="input-icon icon-right">
                                                    <input id="age" name="age" type="number" class="form-control"
                                                           value="${personnelInfo.getAge()!}">
                                                    <i class="fa fa-flag"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="phone">电话号码（必填，用于登录）</label>
                                                <span class="input-icon icon-right">
                                                    <input maxlength="11" name="phone" id="phone" type="number"
                                                           class="form-control" value="${personnelInfo.getPhone()!}">
                                                    <i class="glyphicon glyphicon-earphone"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="role">角色</label>
                                                <select id="role" name="role" style="width:100%;">
                                                        <#list roleList as role >
                                                            <option
                                                                <#if personnelInfo.getRoleId()! == role.getId()>selected</#if>
                                                                value="${role.getId()}">${role.getName()}</option>
                                                        </#list>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="joinTime">入职时间</label>
                                                <span class="input-icon icon-right">
                                                    <input type="text" value="${personnelInfo.getJoinTime()!}" id="joinTime" class="form-control">
                                                   <i class="fa fa-calendar"></i>
                                                </span>
                                            </div>
                                            <div class="form-group">
                                                <label for="password">密码（用于登录）</label>
                                                <span class="input-icon icon-right">
                                                    <input id="password" type="password" class="form-control"
                                                           value="${personnelInfo.getPassword()!}">
                                                    <i class="fa fa-unlock-alt"></i>
                                                </span>
                                            </div>
                                        </div>

                                    <div class="clear_float"></div>
                                <#--暂时不用上传身份证 要使用去掉最大的div-->

                                    <div class="form-title">身份认证</div>
                                    <div class="alert alert-info fade in">
                                        <button class="close" data-dismiss="alert">×</button>
                                        <i class="fa-fw fa fa-info"></i>
                                        图片仅支持gif/jpeg/jpg/bmp/png格式，图片大小最大为2M
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="col-sm-6">
                                            <form action="" id="ajaxUpload" method="post" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <div class="control-group js_upFile_box">
                                                        <div class="btn-upload">
                                                            <button id="image-select-btn-one" class="btn-sm btn btn-info" type="button">选择</button>
                                                            <button id="image-upload-btn-one" class="btn-sm btn btn-info" type="button">上传</button>
                                                        </div>
                                                        <div class="upload_showBox js_upFile_show">
                                                            <img <#if userInfo.getIdCardPositive()! =="">src="/assets/img/upload/idcard_z.png"<#else >src="${userInfo.getIdCardPositive()}"</#if> id="image-upload-box-one" width="340px" height="216px" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="col-sm-6">
                                            <form action="" id="ajaxUpload" method="post" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <div class="control-group js_upFile_box">
                                                        <div class="btn-upload">
                                                            <button id="image-select-btn-two" class="btn-sm btn btn-info" type="button">选择</button>
                                                            <button id="image-upload-btn-two" class="btn-sm btn btn-info" type="button">上传</button>
                                                        </div>
                                                        <div class="upload_showBox js_upFile_show">
                                                            <img <#if userInfo.getIdCardPositive()! =="">src="/assets/img/upload/idcard_f.png"<#else >src="${userInfo.getIdCardPositive()}"</#if> id="image-upload-box-two" width="340px" height="216px" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="col-sm-6">
                                            <form action="" method="post" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <div class="control-group js_upFile1_box">
                                                        <div class="btn-upload">
                                                            <button id="image-select-btn-three" class="btn-sm btn btn-info" type="button">选择</button>
                                                            <button id="image-upload-btn-three" class="btn-sm btn btn-info" type="button">上传</button>
                                                        </div>
                                                        <div class="upload_showBox js_upFile1_show">

                                                            <img <#if userInfo.getIdCardPositive()! =="">src="/assets/img/upload/shouchi_idcard.png"<#else >src="${userInfo.getIdCardPositive()}"</#if> id="image-upload-box-three" width="340px" height="216px" >
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="text-right">
                                            <button class="btn btn-success" href="javascript:void(0);" id="submit"><i
                                                    class="btn-label glyphicon glyphicon-ok"></i> 保存人员
                                            </button>
                                        </div>
                                    </div>
                                    <div class="clear_float"></div>
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
            elem: '#joinTime'
        });
    });
</script>
<script type="text/javascript">

    var personnel = {
        id: "${personnelInfo.getId()!}",
        name: "",
        phone: "",
        sex: "",
        age: "",
        password: "",
        status: "",
        roleId: "",
        deptNo: "",
        joinTime:"",
        idCardPositive:"",//正面
        idCardSide:"" ,//身份证反面
        idCardHold:"" //身份证手持照
    }
    var flog = false
    $("#phone").blur(function () {
        if ($("#phone").val() != "") {
            $.post(
                    "/oa/personnel/phone",
                    {
                        phone: $("#phone").val()
                    },
                    function (res) {

                        if (res.data.code != 0) {
                            flog = true
                            layer.msg(res.data.message, {
                                time: 1000
                            });
                        }
                        if (res.data.code == 0) {
                            flog = false
                            layer.msg(res.data.message, {
                                time: 1000
                            });
                        }

                    }
            )
        }

    })
    $("#submit").click(function () {
        personnel.name = $("#name").val();
        personnel.phone = $("#phone").val();
        personnel.sex = $("#sex").val();
        personnel.age = $("#age").val();
        var length = document.getElementById("password").value.length;
        if (length < 20){

            personnel.password = $.md5($("#password").val());

        }else {

            personnel.password = $("#password").val();
        }
        console.log(length)
        personnel.status = $("#status").val();
        personnel.roleId = $("#role").val();
        personnel.deptNo = $("#dept").val();
        personnel.joinTime = $("#joinTime").val();
        personnel.idCardPositive=$("#image-upload-box-one").attr("src");
        personnel.idCardSide=$("#image-upload-box-two").attr("src");
        personnel.idCardHold=$("#image-upload-box-three").attr("src");
        if (personnel.name == "") {
            layer.msg("姓名不能为空", {
                time: 1000
            });
        } else if (personnel.phone == "") {
            layer.msg("手机号码不能为空", {
                time: 1000
            });
        } else if (personnel.age == "") {
            layer.msg("年龄不能为空", {
                time: 1000
            });
        } else if (personnel.password == "") {
            layer.msg("密码不能为空", {
                time: 1000
            });
        } else if (flog) {
            layer.msg("手机号码已经重复！", {
                time: 1000
            });
        } else if (personnel.joinTime ==""){
            layer.msg("请选择入职时间！", {
                time: 1000
            });
        } else {

            $.post(
                    "/oa/personnel/save",
                    {
                        personnelId: "${personnelInfo.getId()!}",
                        name: personnel.name,
                        phone: personnel.phone,
                        sex: personnel.sex,
                        age: personnel.age,
                        joinTime: personnel.joinTime,
                        password: personnel.password,
                        status: personnel.status,
                        roleId: personnel.roleId,
                        deptNo: personnel.deptNo,
                        idCardPositive:personnel.idCardPositive,
                        idCardSide:personnel.idCardSide,
                        idCardHold:personnel.idCardHold
                    },
                    function (res) {
                        layer.msg(res.message, {
                            time: 1000
                        });
                        if (res.code == 0) {
                            setTimeout(function () {
                                location = "/oa/personnel/list.html";
                            }, 1000);
                        }
                    }
            )
        }
        // $("#save").removeAttr("disabled")


    })
</script>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('upload', function(){
        var $ = layui.jquery
                ,upload = layui.upload;
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-one'
            ,url: '/upload/img/personnel'
            ,auto: false
            //,multiple: true
            ,bindAction: '#image-upload-btn-one'
            ,done: function(res){
                if (res.code == 0) {
                    $("#image-upload-box-one").attr('src',res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-two'
            ,url: '/upload/img/personnel'
            ,auto: false
            //,multiple: true
            ,bindAction: '#image-upload-btn-two'
            ,done: function(res){
                if (res.code == 0) {
                    $("#image-upload-box-two").attr('src',res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
        //选完文件后不自动上传
        upload.render({
            elem: '#image-select-btn-three'
            ,url: '/upload/img/personnel'
            ,auto: false
            //,multiple: true
            ,bindAction: '#image-upload-btn-three'
            ,done: function(res){
                if (res.code == 0) {
                    $("#image-upload-box-three").attr('src',res.data.src)
                    return layer.msg(res.message);
                }
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg(res.message);
                }
            }
        });
    });
</script>

</body>
</html>