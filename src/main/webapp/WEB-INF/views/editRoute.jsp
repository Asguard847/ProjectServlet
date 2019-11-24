<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Add route</h1>

            <p class="lead">Fill the below information to edit route</p>
        </div>

         <form name="addRouteForm" action="<c:url value="/app/admin/routes/editRoute/${route.id}"/>" method="post">

        <c:if test="${not empty numberVal}">
            <div class="error" style="color: #ff0000">${numberVal}</div>
        </c:if>

            <div class="form-group">
            <label for="number">Number</label>
            <input type="text" name="number" class="form-Control" value="${route.number}"/>
        </div>

        <c:if test="${not empty startVal}">
             <div class="error" style="color: #ff0000">${startVal}</div>
        </c:if>

            <div class="form-group">
            <label for="start">Start Point</label>
            <input type="text" name="start" class="form-Control" value="${route.startPoint}"/>
        </div>

        <c:if test="${not empty endVal}">
            <div class="error" style="color: #ff0000">${endVal}</div>
        </c:if>

            <div class="form-group">
            <label for="end">End Point</label>
            <input type="text" name="end" class="form-Control" value="${route.endPoint}"/>
        </div>

         <c:if test="${not empty lengthVal}">
            <div class="error" style="color: #ff0000">${lengthVal}</div>
          </c:if>

        <div class="form-group">
           <label for="length">Length(meters)</label>
           <input type="text" name="length" class="form-Control" value="${route.length}"/>
        </div>

        <br><br>

        <div class="page-header">
            <h1>Buses: </h1>
            <p class="lead">Add or remove buses from this route</p>
        </div>

          <table class="table table-striped table-hover">
                <thead>
                    <tr class="bg-success">
                        <th>Number</th>
                        <th>Model</th>
                        <th>Driver</th>
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
                                    <button type="button" class="btn btn-danger">Delete</button></a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

               <br><br>

               <div class="form-group">
                   <label for="driverSelect">Assign bus :</label>
                   <select class="form-control" id="busSelect" name="busSelect">
                       <option value="none">none</option>
                       <c:forEach items="${buses}" var="bus">
                           <option value="${bus.id}">${bus.model}    ${bus.driver.firstName} ${bus.driver.lastName}</option>
                       </c:forEach>
                   </select>
               </div>
        <br><br>
        <input type="submit" value="Submit" class="btn btn-default">

        </form>

        <br><br>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
