
<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "admin.page"/></h1>
            <p class="lead"><fmt:message key = "admin.this.page"/></p>
        </div>

        <h3>
           <a href="${pageContext.request.contextPath}/app/admin/assignments" ><fmt:message key = "admin.assignments"/></a>
        </h3>

        <p><fmt:message key = "admin.assignments.view"/></p>

        <br><br>

        <h3>
            <a href="${pageContext.request.contextPath}/app/admin/routes"  ><fmt:message key = "admin.routes"/></a>
        </h3>

        <p><fmt:message key = "admin.routes.view"/></p>

        <br><br>

        <h3>
            <a href="${pageContext.request.contextPath}/app/admin/buses" ><fmt:message key = "admin.buses"/></a>
        </h3>

        <p><fmt:message key = "admin.buses.view"/></p>

        <br><br>

        <h3>
           <a href="${pageContext.request.contextPath}/app/admin/drivers"><fmt:message key = "admin.drivers"/></a>
        </h3>

        <p><fmt:message key = "admin.drivers.view"/></p>

        <br><br>

<%@include file="/WEB-INF/views/template/footer.jsp"%>