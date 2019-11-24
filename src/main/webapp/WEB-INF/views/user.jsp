<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Hello, ${driver.firstName} ${driver.lastName}</h1>
            <p class="lead">Here you can check your assignments</p>
        </div>

      <c:choose>
         <c:when test="${assignment == null}">
            <p class="lead">No assignment for you now. Contact your administrator.</p>
      </c:when>
       <c:otherwise>
    <c:choose>
       <c:when test="${assignment.approved == null}">
            <p class="lead">You have new assignment!</p>
       </c:when>
        <c:otherwise>
            <p class="lead">Your current assignment: </p>
        </c:otherwise>
    </c:choose>
              <table class="table table-bordered">
                        <thead>
                        <tr class="bg-success">
                            <th>Created</th>
                            <th>Bus</th>
                            <th>Route</th>
                            <th>Approved</th>
                          </tr>
                        </thead>
                        <tr>
                           <td>${assignment.created}</td>
                           <td>${assignment.bus.number} ${assignment.bus.model}</td>
                           <td>${assignment.route.number}</td>
                           <td>
                            <c:choose>
                            <c:when test="${assignment.approved == null}">
                                    <a href="<c:url value="/app/user/approve/${assignment.id}"/>">
                                   <button type="button" class="btn btn-danger">Approve</button></a>
                                 </c:when>
                                 <c:otherwise>
                                  ${assignment.approved}
                                 </c:otherwise>
                                 </c:choose>
                           </td>
                        </tr>

</c:otherwise>
 </c:choose>

<%@include file="/WEB-INF/views/template/footer.jsp"%>