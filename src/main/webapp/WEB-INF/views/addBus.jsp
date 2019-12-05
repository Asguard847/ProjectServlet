<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "add.bus.header"/></h1>

            <p class="lead"><fmt:message key = "add.bus.fill"/></p>
        </div>

         <form name="addBusForm" action="<c:url value="/app/admin/buses/addBus"/>" method="post">

        <c:if test="${not empty numberVal}">
            <div class="error" style="color: #ff0000">${numberVal}</div>
        </c:if>

            <div class="form-group">
            <label for="number"><fmt:message key = "routes.number"/></label>
            <input type="text" name="number" class="form-Control"/>
        </div>

        <c:if test="${not empty modelVal}">
             <div class="error" style="color: #ff0000">${modelVal}</div>
        </c:if>

            <div class="form-group">
            <label for="model"><fmt:message key = "routes.model"/></label>
            <input type="text" name="model" class="form-Control"/>
        </div>

        <br><br>
        <input type="submit" value="<fmt:message key = "button.submit"/>" class="btn btn-default">

        </form>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
