<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="loginModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content p-4">
            <div class="modal-body">
                <div id="login-error" class="alert alert-danger d-none"></div>
                <div class="form-group">
                    <label class="font-weight-bold">Username:</label>
                    <input type="text" class="form-control" id="loginUsername" placeholder="Enter username">
                    <div class="text-danger small" id="err-username"></div>
                </div>
                <div class="form-group">
                    <label class="font-weight-bold">Password:</label>
                    <input type="password" class="form-control" id="loginPassword" placeholder="Password">
                    <div class="text-danger small" id="err-password"></div>
                </div>
                <button type="button" class="btn btn-success" id="loginBtn">Sign in</button>
            </div>
            <div class="modal-footer border-0 justify-content-center">
                <button class="btn btn-light border" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container align-items-center">
        <a class="navbar-brand font-weight-bold" href="${pageContext.request.contextPath}/vote">Polls</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item ${currentPage == 'vote' ? 'active' : ''}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vote">Vote</a>
                </li>
                <c:if test="${sessionScope.loggedUser != null && sessionScope.loggedUser.role == 'ADMIN'}">
                <li class="nav-item ${currentPage == 'create' ? 'active' : ''}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/polls/create">Create</a>
                </li>
                </c:if>
                <li class="nav-item ${currentPage == 'list' ? 'active' : ''}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/polls/list">List</a>
                </li>
            </ul>

            <c:choose>
                <c:when test="${sessionScope.loggedUser != null}">
                    <button class="btn btn-outline-danger my-2 my-sm-0" id="logoutBtn">
                        Logout (${sessionScope.loggedUser.username})
                    </button>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-outline-light my-2 my-sm-0" data-toggle="modal" data-target="#loginModal">
                        Login
                    </button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
