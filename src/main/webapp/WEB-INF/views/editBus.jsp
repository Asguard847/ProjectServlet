<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Edit bus</h1>

            <p class="lead">Fill the below forms to edit bus information</p>
        </div>


         <form name="addDriverForm" action="<c:url value="/app/admin/buses/editBus/${bus.id}"/>" method="post">

            <c:if test="${not empty numberVal}">
                <div class="error" style="color: #ff0000">${numberVal}</div>
         </c:if>

         <div class="form-group">
            <label for="number">Number</label>
            <input type="text" name="number" class="form-Control" value="${bus.number}"/>
        </div>

         <c:if test="${not empty modelVal}">
              <div class="error" style="color: #ff0000">${modelVal}</div>
         </c:if>

        <div class="form-group">
            <label for="model">Model</label>
            <input type="text" name="model" class="form-Control" value="${bus.model}"/>
        </div>

        <br>

         <label class="radio-inline">
              <input type="radio" name="ready" value="true"checked>Ready
            </label>
            <label class="radio-inline">
              <input type="radio" name="ready" value="false">Not ready
            </label>

            <br><br>

        <h4>Driver: </h4>

        <c:choose>
              <c:when test="${bus.driver==null}">
                    <p style="color: crimson">None</p>
              </c:when>
              <c:otherwise>
                   <p>${bus.driver.firstName} ${bus.driver.lastName}</p>
              </c:otherwise>
        </c:choose>

    <br>

     <div class="form-group">
           <label for="sel1">Assign driver :</label>
           <select class="form-control" id="driverSelect" name="driverSelect">
             <option value="none">none</option>
                <c:forEach items="${drivers}" var="driver">
                         <option value="${driver.id}">${driver.firstName} ${driver.lastName}</option>
                </c:forEach>
           </select>
     </div>

     <br><br>

        <input type="submit" value="Submit" class="btn btn-default">
        </form>

        <br><br>




<%@include file="/WEB-INF/views/template/footer.jsp" %>
