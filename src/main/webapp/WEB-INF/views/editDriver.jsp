<%@include file="/WEB-INF/views/template/header.jsp"%>


<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Add driver</h1>

            <p class="lead">Fill the below information to add driver</p>
        </div>

         <form name="addDriverForm" action="<c:url value="/app/admin/drivers/editDriver/${driver.id}"/>" method="post" enctype="multipart/form-data">



         <c:if test="${not empty fNameVal}">
               <div class="error" style="color: #ff0000">${fNameVal}</div>
         </c:if>

        <div class="form-group">
            <label for="firstName">First name</label>
            <input type="text" name="firstName" class="form-Control" value="${driver.firstName}"/>
        </div>

        <c:if test="${not empty lNameVal}">
            <div class="error" style="color: #ff0000">${lNameVal}</div>
        </c:if>

        <div class="form-group">
            <label for="lastName">Last name</label>
            <input type="text" name="lastName" class="form-Control" value="${driver.lastName}"/>
        </div>

        <c:if test="${not empty phoneVal}">
            <div class="error" style="color: #ff0000">${phoneVal}</div>
        </c:if>

        <div class="form-group">
            <label for="phone">Phone Number</label>
            <input type="text" name="phone" class="form-Control" value="${driver.phoneNumber}"/>
        </div>

        <c:if test="${not empty emailVal}">
             <div class="error" style="color: #ff0000">${emailVal}</div>
        </c:if>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" name="email" class="form-Control" value="${driver.email}"/>
        </div>

        <div class="form-group">
            <label class="control-label" for="driverImage">Upload Image</label>
            <input type="file" name="driverImage" class="form:input-large"/>
        </div>

        <br><br>
        <input type="submit" value="Submit" class="btn btn-default">

        </form>





        <%@include file="/WEB-INF/views/template/footer.jsp" %>
