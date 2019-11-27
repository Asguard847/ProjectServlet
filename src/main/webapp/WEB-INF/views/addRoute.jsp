<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "routes.add"/></h1>

            <p class="lead"><fmt:message key = "add.routes.fill"/></p>
        </div>

         <form name="addRouteForm" action="<c:url value="/app/admin/routes/addRoute"/>" method="post">

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
           <label for="length"><fmt:message key = "routes.length"/>(meters)</label>
           <input type="text" name="length" class="form-Control" value="${route.length}"/>
        </div>

        <br><br>
        <input type="submit" value="<fmt:message key = "button.submit"/>" class="btn btn-default">

        </form>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
