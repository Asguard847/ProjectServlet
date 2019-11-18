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
            <h1>Driver inventory page</h1>
            <p class="lead">This is driver inventory page</p>
            <a href="<c:url value="/app/admin/drivers/addDriver" />"class="btn btn-primary">Add driver</a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th>Photo Thumb</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Phone number</th>
                <th>Ready</th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${drivers}" var="driver">
                <tr>
                    <td><img src="<c:url value="/resources/images/${driver.id}.png"/>" alt="image" style="width: 100%" /></td>
                    <td>${driver.firstName}</td>
                    <td>${driver.lastName}</td>
                    <td>${driver.phoneNumber}</td>
                    <td><c:choose>
                             <c:when test="${driver.ready==true}">
                             <a href="<c:url value="/app/admin/drivers/setNotReady/${driver.id}"/>">
                                  <p style="color: green">ready</p>
                             </c:when>
                             <c:otherwise>
                              <a href="<c:url value="/app/admin/drivers/setReady/${driver.id}"/>">
                                  <p style="color: crimson">not ready</p>
                             </c:otherwise>
                    </c:choose></td>
                    <td>
                        <a href="<c:url value="/app/admin/drivers/deleteDriver/${driver.id}"/>">
                            <button type="button" class="btn btn-danger">Delete</button></a>
                        <a href="<c:url value="/app/admin/drivers/editDriver/${driver.id}"/>">
                            <button type="button" class="btn btn-warning">Edit</button></a>
                    </td>

                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/drivers/addDriver" />"class="btn btn-primary">Add driver</a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>