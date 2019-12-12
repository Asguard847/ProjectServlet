<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="app"/>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>AutoPark management system</title>


    <!-- Angular JS -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.1/angular.min.js"> </script>

    <%--JQuery--%>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.4.min.js"></script>

    <%--Data Table--%>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"  rel="stylesheet">

    <!-- Main CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/main.css" rel="stylesheet">

    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">





</head>
<!-- NAVBAR
================================================== -->
<body>
<div class="navbar-wrapper">
    <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                            aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                      <c:if test="${user.authority == 'ROLE_ADMIN'}">
                         <li><a href="${pageContext.request.contextPath}/app/admin"/> <fmt:message key = "header.admin"/></a></li>
                      </c:if>
                    </ul>
                    <ul class="nav navbar-nav pull-right">

                           <li><a><fmt:message key = "header.choose"/></a></li>
                           <li><a href="${pageContext.request.contextPath}/app/setRu"/> rus</a></li>
                           <li><a href="${pageContext.request.contextPath}/app/setEn"/> en</a></li>
                        <c:if test="${user != null}">
                            <li><a href="${pageContext.request.contextPath}/app/logout"/> <fmt:message key = "header.logout"/></a></li>
                        </c:if>

                    </ul>
                </div>
            </div>
        </nav>

    </div>
</div>