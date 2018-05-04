<!--Basic Scripts-->
<script src="/assets/js/jquery-2.0.3.min.js"></script>
<script src="/assets/js/layer/layer.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<!--Beyond Scripts-->
<script src="/assets/js/beyond.min.js"></script>
<script src="/assets/js/md5.js"></script>
<script src="/assets/js/toastr/toastr.js"></script>
<script src="/assets/js/select2/select2.js"></script>
<script src="/assets/js/laydate/laydate.js"></script>
<script src="/assets/js/editors/summernote/summernote.js"></script>
<!--此处补充js文件-->
<script src="/assets/js/fuelux/wizard/wizard-custom.js"></script>

<script type="text/javascript">
    $(function () {
        function LifeShow() {
            /* $.ajax({
             type          : 'get',
             async         : false,
             url           : 'http://api.k780.com:88/?app=ip.get&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
             dataType      : 'jsonp',
             jsonp         : 'callback',
             jsonpCallback : 'data',
             success       : function(data){
             if(data.success!='1'){
             alert(data.msgid+' '+data.msg);
             exit;
             }
             //遍历


             $("#address").html(data.result.att);
             }
             });*/
            $.ajax({
                type: 'get',
                async: false,
                url: 'http://api.k780.com:88/?app=weather.today&weaid=265&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
                dataType: 'jsonp',
                jsonp: 'callback',
                jsonpCallback: 'data',
                success: function (data) {
                    if (data.success != '1') {
                        alert(data.msgid + ' ' + data.msg);
                        exit;
                    }
                    //遍历天气
                    $("#address").html("中国 " + data.result.citynm);
                    $("#weather").html("温度:" + data.result.temperature_curr + "状况：" + data.result.weather + "风级：" + data.result.wind)
                    $("#currentTime").html(data.result.days);
                    $("#temperature").html(data.result.temperature);
                    $("#currentTimes").html(data.result.week);
                    $("#weekTime").html(data.result.week);
                }
            });
            /* $.ajax({
             type          : 'get',
             async         : false,
             url           : 'http://api.k780.com:88/?app=life.time&appkey=25043&sign=67722d3b24a93459e930c6df81540f02&format=json&jsoncallback=data',
             dataType      : 'jsonp',
             jsonp         : 'callback',
             jsonpCallback : 'data',
             success       : function(data){
             if(data.success!='1'){
             alert(data.msgid+' '+data.msg);
             exit;
             }
             //遍历



             $("#currentTimes").html(data.result.week_2);
             $("#weekTime").html(data.result.week_4);
             }
             });*/
        }

        LifeShow();

    });

    $(function () {
        function getUser() {
            $.post(
                    "/oa/user/info",
                    function (res) {
                        if (res.code==0){
                            $("#user-username-1").html( "用户名称:"+res.data.user.name);
                            $("#user-username-2").html( res.data.user.name);
                            $("#user-phone").html("手机号码:" +res.data.user.phone);
                            var avatar = res.data.user.avatar;
                            if (avatar == null){
                                avatar = "/layui/images/model.jpg";
                            }
                            $("#user-avatar-1").attr('src',avatar);
                            $("#user-avatar-2").attr('src',avatar);
                        }
                    }
            )
        }
        getUser()
    })

</script>