<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Add bus</h1>

            <p class="lead">Fill the below information to add bus</p>
        </div>

         <form name="addBusForm" action="<c:url value="/app/admin/buses/addBus"/>" method="post">

        <c:if test="${not empty numberVal}">
            <div class="error" style="color: #ff0000">${numberVal}</div>
        </c:if>

            <div class="form-group">
            <label for="number">Number</label>
            <input type="text" name="number" class="form-Control"/>
        </div>

        <c:if test="${not empty modelVal}">
             <div class="error" style="color: #ff0000">${modelVal}</div>
        </c:if>

            <div class="form-group">
            <label for="model">Model</label>
            <input type="text" name="model" class="form-Control"/>
        </div>

        <br><br>
        <input type="submit" value="Submit" class="btn btn-default">

        </form>

        <%@include file="/WEB-INF/views/template/footer.jsp" %>
