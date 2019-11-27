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
            <h1><fmt:message key = "bus.header"/></h1>
            <p class="lead"><fmt:message key = "bus.this"/></p>
            <a href="<c:url value="/app/admin/buses/addBus" />"class="btn btn-primary"><fmt:message key = "button.add"/></a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th><fmt:message key = "routes.number"/></th>
                <th><fmt:message key = "routes.model"/></th>
                <th><fmt:message key = "routes.driver"/></th>
                <th><fmt:message key = "bus.route"/></th>
                <th><fmt:message key = "driver.ready"/></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${buses}" var="bus">
                <tr>
                    <td>${bus.number}</td>
                    <td>${bus.model}</td>
                    <td>${bus.driver.firstName} ${bus.driver.lastName}</td>
                    <td>${bus.route.number}</td>
                    <td><c:choose>
                             <c:when test="${bus.ready==true}">
                                   <p style="color: green"><fmt:message key = "driver.ready"/></p>
                             </c:when>
                             <c:otherwise>
                                   <p style="color: crimson"><fmt:message key = "driver.notready"/></p>
                             </c:otherwise>
                    </c:choose></td>
                    <td>
                        <a href="<c:url value="/app/admin/buses/deleteBus/${bus.id}"/>">
                            <button type="button" class="btn btn-danger"><fmt:message key = "button.delete"/></button></a>
                        <a href="<c:url value="/app/admin/buses/editBus/${bus.id}"/>">
                            <button type="button" class="btn btn-warning"><fmt:message key = "button.edit"/></button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/buses/addBus" />"class="btn btn-primary"><fmt:message key = "button.add"/></a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>