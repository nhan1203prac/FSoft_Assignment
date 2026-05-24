<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/05/2026
  Time: 10:49 am
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctx}/css/styles.css">
    <title>Edit Profile</title>

</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom shadow-sm">
    <a class="navbar-brand" href="#">CMS</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link dropdown-toggle text-primary" href="#" data-toggle="dropdown"
                   aria-expanded="false">
                    <i class="fa fa-user"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a class="dropdown-item border-bottom" href="#"><i class="fa fa-user mr-2"></i>User Profile</a>
                    <a onclick="handleLogout(event)" class="dropdown-item" href="#"><i class="fa fa-sign-out mr-2"></i>Logout</a>

                </div>
            </li>


        </ul>
    </div>
</nav>
<div class="row no-gutters">
    <div class="col-3 border-right pr-0" style="min-height: 100vh; background-color: #f8f9fa;">
        <div class="input-group mb-3 p-3 border-bottom">
            <input type="text" class="form-control" placeholder="Search">
            <div class="input-group-append">
                <span class="input-group-text bg-light"><i class="fa fa-search"></i></span>
            </div>
        </div>
        <div class="list-group list-group-flush">
            <a href="view-content.jsp"
               class="list-group-item list-group-item-action  text-primary border-bottom  ">
                <i class="fa fa-th-list mr-2"></i> View contents
            </a>
            <a href="form-content.jsp" class="list-group-item list-group-item-action  text-primary">
                <i class="fa fa-edit mr-2"></i> Form content
            </a>
        </div>
    </div>

    <div class="flex-grow-1 p-4 mt-3">
        <h3>Edit Profile</h3>
        <div class="card shadow-sm">
            <div class="card-header ">Profile Form Elements</div>
            <div class="card-body">
                <form action="edit-profile" method="POST" id="profileForm" style="max-width: 80%;"
                      onsubmit="return handleSubmit(event)">
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <div class=" form-group">
                        <label for="firstName">First Name</label>
                        <input value="${member.firstName}" name="firstName" type="text" class="form-control"
                               id="firstName" placeholder="First Name">
                        <div class="invalid-feedback" id="firstNameError"></div>
                    </div>
                    <div class=" form-group">
                        <label for="lastName">Last Name</label>
                        <input value="${member.lastName}" name="lastName" type="text" class="form-control" id="lastName"
                               placeholder="Last Name">
                        <div class="invalid-feedback" id="lastNameError"></div>
                    </div>


                    <div class="form-group">
                        <label for="email">Email</label>
                        <br>
                        <span id="email">${member.email}</span>

                    </div>

                    <div class="form-group">
                        <label for="phoneInput">Phone</label>
                        <input value="${member.phone}" name="phoneNumber" type="text" class="form-control"
                               id="phoneInput" placeholder="Phone Number">
                        <div class="invalid-feedback" id="phoneError"></div>
                    </div>

                    <div class="form-group">
                        <label for="descriptionInput">Description</label>
                        <textarea name="desc" class="form-control" id="descriptionInput" rows="4"
                                  maxlength="200">${member.description}</textarea>
                    </div>

                    <div class="button-group mt-3">
                        <button type="submit" class="btn btn-outline-dark">Submit Button</button>
                        <button type="reset" class="btn btn-outline-dark" id="resetBtn">Reset Button</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
<script>
    const handleLogout = (event) => {
        event.preventDefault();
        if (confirm("Bạn có chắc chắn muốn đăng xuất?")) {
            window.location.href = "${ctx}/logout";
        }
    };
    const validateForm = () => {
        var fName = document.getElementById('firstName').value.trim();
        var lName = document.getElementById('lastName').value.trim();
        var phone = document.getElementById('phoneInput').value.trim();
        var desc = document.getElementById('descriptionInput').value.trim();

        var isValid = true;

        document.querySelectorAll('.form-control').forEach(function (element) {
            element.classList.remove('is-invalid');
        });

        if (fName.length === 0) {
            document.getElementById('firstNameError').textContent = "First Name không được để trống!";
            document.getElementById('firstName').classList.add('is-invalid');
            isValid = false;
        } else if (fName.length < 3 || fName.length > 30) {
            document.getElementById('firstNameError').textContent = "First Name phải từ 3-30 ký tự!";
            document.getElementById('firstName').classList.add('is-invalid');
            isValid = false;
        }

        if (lName.length === 0) {
            document.getElementById('lastNameError').textContent = "Last Name không được để trống!";
            document.getElementById('lastName').classList.add('is-invalid');
            isValid = false;
        } else if (lName.length < 3 || lName.length > 30) {
            document.getElementById('lastNameError').textContent = "Last Name phải từ 3-30 ký tự!";
            document.getElementById('lastName').classList.add('is-invalid');
            isValid = false;
        }

        if (phone.length === 0) {
            document.getElementById('phoneError').textContent = "Số điện thoại không được để trống!";
            document.getElementById('phoneInput').classList.add('is-invalid');
            isValid = false;
        } else if (phone.length < 9 || phone.length > 13) {
            document.getElementById('phoneError').textContent = "Số điện thoại phải từ 9-13 số!";
            document.getElementById('phoneInput').classList.add('is-invalid');
            isValid = false;
        }
    }
</script>

</body>

</html>
