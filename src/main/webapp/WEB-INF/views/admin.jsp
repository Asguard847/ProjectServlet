<%--
  Created by IntelliJ IDEA.
  User: Asguard
  Date: 10.06.2019
  Time: 21:59
  To change this template use File | Settings | File Templates.
--%>

<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>administrator page</h1>
            <p class="lead">This is the administrator page</p>
        </div>

        <h3>
            <a href="<c:url value="/app/admin/routes" />" >Manage routes</a>
        </h3>

        <p>Here you can view, check and modify routes</p>

        <br><br>

        <h3>
            <a href="<c:url value="/app/admin/buses" />" >Manage buses</a>
        </h3>

        <p>Here you can view, check and modify buses</p>

        <br><br>

        <h3>
           <a href="<c:url value="/app/admin/drivers" />" >Manage drivers</a>
        </h3>

        <p>Here you can view, check and modify drivers</p>

        <br><br>

<%@include file="/WEB-INF/views/template/footer.jsp"%>