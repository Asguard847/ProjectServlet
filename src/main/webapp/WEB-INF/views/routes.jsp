<%@include file="/WEB-INF/views/template/header.jsp"%>

<script>
    $(document).ready(function() {

        $('.table').DataTable({
            "lengthMenu": [[5,10, -1], [5,10,"All"]]
             });
    });
</script>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "routes"/></h1>
            <p class="lead"><fmt:message key = "routes.page"/></p>
            <a href="<c:url value="/app/admin/routes/addRoute" />"class="btn btn-primary"><fmt:message key = "button.add"/></a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th><fmt:message key = "routes.number"/></th>
                <th><fmt:message key = "routes.start"/></th>
                <th><fmt:message key = "routes.end"/></th>
                <th><fmt:message key = "routes.buses"/></th>
                <th><fmt:message key = "routes.interval"/></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${routes}" var="route">
                <tr>
                    <td>${route.number}</td>
                    <td>${route.startPoint}</td>
                    <td>${route.endPoint}</td>
                    <td><c:choose>
                            <c:when test="${route.buses.size() == 0}">
                                   <p style="color: crimson">${route.buses.size()}</p>
                            </c:when>
                            <c:otherwise>
                                    <p>${route.buses.size()}</p>
                            </c:otherwise>
                    </c:choose></td>
                    <td>${route.timeInterval}</td>
                    <td>
                        <a href="<c:url value="/app/admin/routes/deleteRoute/${route.id}"/>">
                            <button type="button" class="btn btn-danger"><fmt:message key = "button.delete"/></button></a>
                        <a href="<c:url value="/app/admin/routes/editRoute/${route.id}"/>">
                            <button type="button" class="btn btn-warning"><fmt:message key = "button.edit"/></button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/routes/addRoute" />"class="btn btn-primary"><fmt:message key = "button.add"/></a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>