<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "edit.bus.header"/></h1>

            <p class="lead"><fmt:message key = "edit.bus.fill"/></p>
        </div>


         <form name="addDriverForm" action="<c:url value="/app/admin/buses/editBus/${bus.id}"/>" method="post">

            <c:if test="${not empty numberVal}">
                <div class="error" style="color: #ff0000">${numberVal}</div>
         </c:if>

         <div class="form-group">
            <label for="number"><fmt:message key = "routes.number"/></label>
            <input type="text" name="number" class="form-Control" value="${bus.number}"/>
        </div>

         <c:if test="${not empty modelVal}">
              <div class="error" style="color: #ff0000">${modelVal}</div>
         </c:if>

        <div class="form-group">
            <label for="model"><fmt:message key = "routes.model"/></label>
            <input type="text" name="model" class="form-Control" value="${bus.model}"/>
        </div>

        <br>

         <label class="radio-inline">
              <input type="radio" name="ready" value="true"checked><fmt:message key = "driver.ready"/>
            </label>
            <label class="radio-inline">
              <input type="radio" name="ready" value="false"><fmt:message key = "driver.notready"/>
            </label>

            <br><br>

        <h4><fmt:message key = "bus.driver"/></h4>

        <c:choose>
              <c:when test="${bus.driver==null}">
                    <p style="color: crimson"><fmt:message key = "option.none"/></p>
              </c:when>
              <c:otherwise>
                   <p>${bus.driver.firstName} ${bus.driver.lastName}</p>
              </c:otherwise>
        </c:choose>

    <br>

     <div class="form-group">
           <label for="sel1"><fmt:message key = "bus.assign"/></label>
           <select class="form-control" id="driverSelect" name="driverSelect">
             <option value="none"><fmt:message key = "option.none"/></option>
                <c:forEach items="${drivers}" var="driver">
                         <option value="${driver.id}">${driver.firstName} ${driver.lastName}</option>
                </c:forEach>
           </select>
     </div>

     <br><br>

        <input type="submit" value="<fmt:message key = "button.submit"/>" class="btn btn-default">
        </form>

        <br><br>




<%@include file="/WEB-INF/views/template/footer.jsp" %>
