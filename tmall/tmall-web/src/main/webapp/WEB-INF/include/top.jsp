<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {

        function foreloginAjax() {
            $.get("/forecheckLogin", function (data) {
                if (data.status == 200) {
                    $("a.hiHolder").attr({"id": "login", "href": "/loginPage"})
                    $("a.hiHolder").text(data.data.name)
                    $("a.loHolder").attr({"id": "logout", "href": "/forelogout"}).text("退出")
                } else {
                    $("a.hiHolder").attr({"href": "/loginPage"}).text("请登录")
                    $("a.loHolder").attr({"href": "/registerPage"}).text("免费注册")
                }
            })
        }

        foreloginAjax();


        $("#myOrder").click(function () {
            var page = "/forecheckLogin";
            $.get(
                    page,
                    function (result) {
                        if (200 == result.status) {
                            window.location.href = "/forebought";
                        }
                        else {
                            $("#loginModal").modal('show');
                        }
                    }
            );
            return false;
        });

    })

</script>
<nav class="top ">
    <a href="/index">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        天猫首页
    </a>

    <span>喵，欢迎来天猫</span>

    <a class="hiHolder"></a>
    <a class="loHolder"></a>

    <%--
        <c:if test="${!empty user}">
            <a id="login" href="/loginPage">${user.name}</a>
            <a id="logout" href="forelogout">退出</a>
        </c:if>

        <c:if test="${empty user}">
            <a href="/loginPage">请登录</a>
            <a href="/registerPage">免费注册</a>
        </c:if>
    --%>


		<span class="pull-right">
			<a href="javascript:void (0)" id="myOrder">我的订单</a>
			<a href="/forecart" id="myCart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
			购物车<strong>${cartTotalItemNumber}</strong>件</a>		
		</span>


</nav>



