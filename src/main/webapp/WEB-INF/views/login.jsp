<%@include file="/WEB-INF/views/template/header.jsp"%>

<div class="container-wrapper">
    <div class="container">
        <div id="login-box">


            <h2> <fmt:message key = "login.greeting"/></h2>

            <c:if test="${not empty msg}">
                <div class="msg">${msg}</div>
            </c:if>

            <form name="loginForm" action="<c:url value="/app/login" />" method="post">
                <c:if test="${not empty error}">
                    <div class="error" style="color: #ff0000">${error }</div>
                </c:if>
                 <div class="form-group">
                     <label for="username"> <fmt:message key = "login.user"/> </label>
                     <input type="text" id="username" name="username" class="form-control" />
                 </div>

                <div class="form-group">
                    <label for="password"> <fmt:message key = "login.password"/></label>
                    <input type="password" id="password" name="password" class="form-control" />
                </div>

                <input type="submit" value=<fmt:message key = "login.submit"/> class="btn btn-default">

                </form>
 </div>
    </div>
</div>


<%@include file="/WEB-INF/views/template/footer.jsp" %>