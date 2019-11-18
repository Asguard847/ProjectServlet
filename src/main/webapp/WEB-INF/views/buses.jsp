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
            <h1>Buses inventory page</h1>
            <p class="lead">This is bus inventory page</p>
            <a href="<c:url value="/app/admin/buses/addBus" />"class="btn btn-primary">Add bus</a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Number</th>
                <th>model</th>
                <th>Driver</th>
                <th>Route Number</th>
                <th>Ready</th>
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
                                   <p style="color: green">ready</p>
                             </c:when>
                             <c:otherwise>
                                   <p style="color: crimson">not ready</p>
                             </c:otherwise>
                    </c:choose></td>
                    <td>
                        <a href="<c:url value="/app/admin/buses/deleteBus/${bus.id}"/>">
                            <button type="button" class="btn btn-danger">Delete</button></a>
                        <a href="<c:url value="/app/admin/buses/editBus/${bus.id}"/>">
                            <button type="button" class="btn btn-warning">Edit</button></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/buses/addBus" />"class="btn btn-primary">Add bus</a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>