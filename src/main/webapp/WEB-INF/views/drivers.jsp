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
            <h1><fmt:message key = "driver.header"/></h1>
            <p class="lead"><fmt:message key = "driver.this"/></p>
            <a href="<c:url value="/app/admin/drivers/addDriver" />"class="btn btn-primary"><fmt:message key = "driver.add"/></a>
        </div>



        <table class="table table-striped table-hover">
            <thead>
            <tr class="bg-success">
                <th><fmt:message key = "driver.photo"/></th>
                <th><fmt:message key = "driver.first"/></th>
                <th><fmt:message key = "driver.last"/></th>
                <th><fmt:message key = "driver.phone"/></th>
                <th><fmt:message key = "driver.ready"/></th>
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
                                  <p style="color: green"><fmt:message key = "driver.ready"/></p>
                             </c:when>
                             <c:otherwise>
                              <a href="<c:url value="/app/admin/drivers/setReady/${driver.id}"/>">
                                  <p style="color: crimson"><fmt:message key = "driver.notready"/></p>
                             </c:otherwise>
                    </c:choose></td>
                    <td>
                        <a href="<c:url value="/app/admin/drivers/deleteDriver/${driver.id}"/>">
                            <button type="button" class="btn btn-danger"><fmt:message key = "button.delete"/></button></a>
                        <a href="<c:url value="/app/admin/drivers/editDriver/${driver.id}"/>">
                            <button type="button" class="btn btn-warning"><fmt:message key = "button.edit"/></button></a>
                    </td>

                </tr>
            </c:forEach>
        </table>

        <a href="<c:url value="/app/admin/drivers/addDriver" />"class="btn btn-primary"><fmt:message key = "driver.add"/></a>


<%@include file="/WEB-INF/views/template/footer.jsp"%>