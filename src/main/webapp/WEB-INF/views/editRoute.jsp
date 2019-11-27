<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "edit.routes.header"/></h1>

            <p class="lead"><fmt:message key = "edit.routes.fill"/></p>
        </div>

         <form name="addRouteForm" action="<c:url value="/app/admin/routes/editRoute/${route.id}"/>" method="post">

        <c:if test="${not empty numberVal}">
            <div class="error" style="color: #ff0000">${numberVal}</div>
        </c:if>

            <div class="form-group">
            <label for="number"><fmt:message key = "routes.number"/></label>
            <input type="text" name="number" class="form-Control" value="${route.number}"/>
        </div>

        <c:if test="${not empty startVal}">
             <div class="error" style="color: #ff0000">${startVal}</div>
        </c:if>

            <div class="form-group">
            <label for="start"><fmt:message key = "routes.start"/></label>
            <input type="text" name="start" class="form-Control" value="${route.startPoint}"/>
        </div>

        <c:if test="${not empty endVal}">
            <div class="error" style="color: #ff0000">${endVal}</div>
        </c:if>

            <div class="form-group">
            <label for="end"><fmt:message key = "routes.end"/></label>
            <input type="text" name="end" class="form-Control" value="${route.endPoint}"/>
        </div>

         <c:if test="${not empty lengthVal}">
            <div class="error" style="color: #ff0000">${lengthVal}</div>
          </c:if>

        <div class="form-group">
           <label for="length"><fmt:message key = "routes.length"/></label>
           <input type="text" name="length" class="form-Control" value="${route.length}"/>
        </div>

        <br><br>

        <div class="page-header">
            <h1><fmt:message key = "routes.buses"/></h1>
            <p class="lead"><fmt:message key = "edit.routes.add"/></p>
        </div>

          <table class="table table-striped table-hover">
                <thead>
                    <tr class="bg-success">
                        <th><fmt:message key = "routes.number"/></th>
                        <th><fmt:message key = "routes.model"/></th>
                        <th><fmt:message key = "routes.driver"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${route.buses}" var="bus">
                        <tr>
                            <td>${bus.number}</td>
                            <td>${bus.model}</td>
                            <td>${bus.driver.firstName} ${bus.driver.lastName}</td>

                            <td>
                                <a href="<c:url value="/app/admin/routes/deleteBusFromRoute/${bus.id}"/>">
                                    <button type="button" class="btn btn-danger"><fmt:message key = "button.delete"/></button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

               <br><br>

               <div class="form-group">
                   <label for="driverSelect"><fmt:message key = "routes.assign"/></label>
                   <select class="form-control" id="busSelect" name="busSelect">
                       <option value="none"><fmt:message key = "option.none"/></option>
                       <c:forEach items="${buses}" var="bus">
                           <option value="${bus.id}">${bus.model}    ${bus.driver.firstName} ${bus.driver.lastName}</option>
                       </c:forEach>
                   </select>
               </div>
        <br><br>
        <input type="submit" value="<fmt:message key = "button.submit"/>" class="btn btn-default">

        </form>

        <br><br>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
