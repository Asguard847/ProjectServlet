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
            <h1>Routes inventory page</h1>
            <p class="lead">This is route inventory page</p>
            <a href="<c:url value="/app/admin/routes/addRoute" />"class="btn btn-primary">Add route</a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Number</th>
                <th>Start point</th>
                <th>End point</th>
                <th>buses</th>
                <th>Time interval</th>
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
                            <button type="button" class="btn btn-danger">Delete</button></a>
                        <a href="<c:url value="/app/admin/routes/editRoute/${route.id}"/>">
                            <button type="button" class="btn btn-warning">Edit</button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/routes/addRoute" />"class="btn btn-primary">Add route</a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>