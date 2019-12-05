<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1><fmt:message key = "edit.driver.header"/></h1>

            <p class="lead"><fmt:message key = "edit.driver.fill"/></p>
        </div>

         <form name="addDriverForm" action="<c:url value="/app/admin/drivers/editDriver/${driver.id}"/>" method="post" enctype="multipart/form-data">



         <c:if test="${not empty fNameVal}">
               <div class="error" style="color: #ff0000">${fNameVal}</div>
         </c:if>

        <div class="form-group">
            <label for="firstName"><fmt:message key = "driver.first"/></label>
            <input type="text" name="firstName" class="form-Control" value="${driver.firstName}"/>
        </div>

        <c:if test="${not empty lNameVal}">
            <div class="error" style="color: #ff0000">${lNameVal}</div>
        </c:if>

        <div class="form-group">
            <label for="lastName"><fmt:message key = "driver.last"/></label>
            <input type="text" name="lastName" class="form-Control" value="${driver.lastName}"/>
        </div>

        <c:if test="${not empty phoneVal}">
            <div class="error" style="color: #ff0000">${phoneVal}</div>
        </c:if>

        <div class="form-group">
            <label for="phone"><fmt:message key = "driver.phone"/></label>
            <input type="text" name="phone" class="form-Control" value="${driver.phoneNumber}"/>
        </div>

        <c:if test="${not empty emailVal}">
             <div class="error" style="color: #ff0000">${emailVal}</div>
        </c:if>

        <div class="form-group">
            <label for="email"><fmt:message key = "driver.email"/></label>
            <input type="text" name="email" class="form-Control" value="${driver.email}"/>
        </div>

        <div class="form-group">
            <label class="control-label" for="driverImage"><fmt:message key = "driver.image"/></label>
            <input type="file" name="driverImage" class="form:input-large"/>
        </div>

        <br><br>
        <input type="submit" value="<fmt:message key = "button.submit"/>" class="btn btn-default">

        </form>





        <%@include file="/WEB-INF/views/template/footer.jsp" %>
