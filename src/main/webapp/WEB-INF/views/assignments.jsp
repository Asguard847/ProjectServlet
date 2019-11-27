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
            <h1><fmt:message key = "assignments.page"/></h1>
            <p class="lead"><fmt:message key = "assignments.this"/></p>
        </div>


        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th><fmt:message key = "assignments.created"/></th>
                <th><fmt:message key = "routes.driver"/></th>
                <th><fmt:message key = "user.bus"/></th>
                <th><fmt:message key = "user.route"/></th>
                <th><fmt:message key = "assignments.approved"/></th>
                <th><fmt:message key = "assignments.cancelled"/></th>
            </tr>
            </thead>
            <c:forEach items="${assignments}" var="assignment">
                <tr>
                    <td>${assignment.created}</td>
                    <td>${assignment.driver.firstName} ${assignment.driver.lastName}</td>
                    <td>${assignment.bus.number}</td>
                    <td>${assignment.route.number}</td>
                    <td>${assignment.approved}</td>
                    <td>${assignment.cancelled}</td>
                    </tr>
            </c:forEach>
        </table>




<%@include file="/WEB-INF/views/template/footer.jsp"%>