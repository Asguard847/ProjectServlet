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
            <h1>Assignments page</h1>
            <p class="lead">This is assignments page</p>
        </div>


        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Created</th>
                <th>Driver</th>
                <th>Bus</th>
                <th>Route</th>
                <th>Approved</th>
                <th>Cancelled</th>
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