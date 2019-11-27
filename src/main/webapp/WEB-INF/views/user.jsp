<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "user.hello"/>, ${driver.firstName} ${driver.lastName}</h1>
            <p class="lead"><fmt:message key = "user.check.assignments"/></p>
        </div>

      <c:choose>
         <c:when test="${assignment == null}">
            <p class="lead"><fmt:message key = "user.noassignment"/></p>
      </c:when>
       <c:otherwise>
    <c:choose>
       <c:when test="${assignment.approved == null}">
            <p class="lead"><fmt:message key = "user.newassignment"/></p>
       </c:when>
        <c:otherwise>
            <p class="lead"><fmt:message key = "user.currentassignment"/></p>
        </c:otherwise>
    </c:choose>
              <table class="table table-bordered">
                        <thead>
                        <tr class="bg-success">
                            <th><fmt:message key = "user.created"/></th>
                            <th><fmt:message key = "user.bus"/></th>
                            <th><fmt:message key = "user.route"/></th>
                            <th><fmt:message key = "user.approved"/></th>
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
                                   <button type="button" class="btn btn-danger"><fmt:message key = "user.approve"/></button></a>
                                 </c:when>
                                 <c:otherwise>
                                  ${assignment.approved}
                                 </c:otherwise>
                                 </c:choose>
                           </td>
                        </tr>
                        </table>

            </c:otherwise>
            </c:choose>
<br><br>

<%@include file="/WEB-INF/views/template/footer.jsp"%>