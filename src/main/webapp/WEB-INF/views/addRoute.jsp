<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Add route</h1>

            <p class="lead">Fill the below information to add route</p>
        </div>

         <form name="addRouteForm" action="<c:url value="/app/admin/routes/addRoute"/>" method="post">

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
        <input type="submit" value="Submit" class="btn btn-default">

        </form>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
