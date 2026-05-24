<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Visitor - HR Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<style>
    #visitorTable thead th {
        background-color: #e9ecef !important;
        color: #000000 !important;
        font-weight: bold !important;
        border: 1px solid #dee2e6 !important;
        text-align: left;
    }

    #visitorTable tbody td {
        background-color: #ffffff !important;
        color: #000000 !important;
        border: 1px solid #dee2e6 !important;
        font-size: 14px;
    }

    .visitor-link {
        color: #28a745 !important;
        font-weight: bold;
        text-decoration: underline;
    }
    .visitor-link:hover {
        color: #1e7e34 !important;
    }

    #visitorTable tbody tr:hover,
    #visitorTable tbody tr:hover td {
        background-color: #ffffff !important;
        color: #000000 !important;
    }
    .form-control, input.form-control {
        background-color: #ffffff !important;
        color: #000000 !important;
    }
</style>
<body class="bg-light">

<div class="container bg-white mt-4 pb-5 shadow-sm px-0">
    <img src="${pageContext.request.contextPath}/image/banner.jpg" alt="Banner" class="img-fluid mb-4 w-100 banner-top">

    <div class="px-4 pt-4">
        <c:if test="${param.registered eq 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Registration successful! New visitor has been added.
                <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
            </div>
        </c:if>
        <c:if test="${param.updated eq 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Visitor information updated successfully.
                <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
            </div>
        </c:if>
        <c:if test="${param.deleted eq 'true'}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                Visitor deleted successfully.
                <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
            </div>
        </c:if>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-success font-weight-bold mb-0">Search Visitor</h2>
            <a href="${pageContext.request.contextPath}/person" class="btn btn-success btn-sm px-3">+ Register New</a>
        </div>

        <form id="searchForm" action="${pageContext.request.contextPath}/search" method="get" class="form-inline mb-4 border-bottom pb-4">
            <label for="searchInput" class="mr-2 text-dark">First name:</label>
            <input type="text" class="form-control mr-3" id="searchInput" name="firstName" value="<c:out value='${keyword}'/>" style="width: 200px;">

            <button type="submit" class="btn btn-light px-4 py-1 border">
                Search...
            </button>
            <small class="ml-2 text-muted">(Type First name Visitor and Click Search button)</small>
        </form>



        <div class="table-responsive">
            <table class="table table-bordered" id="visitorTable">
                <thead>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Gender</th>
                    <th>Telephone</th>
                    <th>You're in</th>
                    <th>Hobbies</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty visitors}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">No visitors found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="v" items="${visitors}">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/amend?id=${v.id}" class="visitor-link">
                                        <c:out value="${v.firstName}"/>
                                    </a>
                                </td>
                                <td><c:out value="${v.lastName}"/></td>
                                <td><c:out value="${v.gender}"/></td>
                                <td><c:out value="${v.telephone}"/></td>
                                <td><c:out value="${v.region}"/></td>
                                <td><c:out value="${v.hobbies}"/></td>
                                <td style="max-width:250px; word-break:break-word;">
                                    <c:out value="${v.description}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
</body>
</html>