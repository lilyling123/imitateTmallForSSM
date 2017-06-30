<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<style>
    .redButton {
        color: white;
        background-color: #C40000;
        font-size: 14px;
        font-weight: bold;
    }

    .btn-block {
        display: block;
        width: 100%;
    }

    .btn {
        display: inline-block;
        padding: 6px 12px;
        margin-bottom: 0;
        font-size: 14px;
        font-weight: 400;
        line-height: 1.42857143;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        background-image: none;
        border: 1px solid transparent;
        border-radius: 4px;
    }
</style>
<script>
    $(function () {
        $("#login_sub").click(function () {
            $.post("/forelogin", $("#loginForm").serialize(), function (data) {
                if (data.status == 200) {
                    location.href = "/forehome";
                } else {
                    $("span.errorMessage").html("账号名和密码不匹配");
                    $("div.loginErrorMessageDiv").show();
                }
            })
        });

        $("form#loginForm").submit(function () {
            if (0 == $("#name").val().length || 0 == $("#password").val().length) {
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }
            return true;
        });

        $("form#loginForm input").keyup(function () {
            $("div.loginErrorMessageDiv").hide();
        });


        var left = window.innerWidth / 2 + 162;
        $("div.loginSmallDiv").css("left", left);
    })
</script>


<div id="loginDiv" style="position: relative">

    <div class="simpleLogo">
        <a href="${contextPath}"><img src="img/site/simpleLogo.png"></a>
    </div>


    <img id="loginBackgroundImg" class="loginBackgroundImg" src="img/site/loginBackground.png">

    <form id="loginForm" method="post">
        <div id="loginSmallDiv" class="loginSmallDiv">
            <div class="loginErrorMessageDiv">
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                    <span class="errorMessage"></span>
                </div>
            </div>

            <div class="login_acount_text">账户登录</div>
            <div class="loginInput ">
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-user"></span>
				</span>
                <input id="name" name="name" placeholder="手机/会员名/邮箱" type="text">
            </div>

            <div class="loginInput ">
				<span class="loginInputIcon ">
					<span class=" glyphicon glyphicon-lock"></span>
				</span>
                <input id="password" name="password" type="password" placeholder="密码" type="text">
            </div>
            <span class="text-danger">不要输入真实的天猫账号密码</span><br><br>


            <div>
                <a class="notImplementLink" href="#nowhere">忘记登录密码</a>
                <a href="register.jsp" class="pull-right">免费注册</a>
            </div>
            <div style="margin-top:20px">
                <input class="btn btn-block redButton" type="hidden">登录</input>
            </div>
            <div style="margin-top:20px">
                <a class="btn btn-block redButton" id="login_sub">
                    登录
                </a>
            </div>
        </div>
    </form>


</div>	