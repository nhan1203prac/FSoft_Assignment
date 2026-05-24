<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 28/04/2026
  Time: 9:15 am
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <title>Login - CMS</title>
</head>
<body>
<div class="container mt-5 d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div class="card" style="width: 400px">
        <div class="card-header">
            <span class="text-left w-100">Please Sign In</span>
        </div>
        <div class="card-body p-4">
            <c:if test="not empty errorMessage">
                <div class="alert alert-danger text-center">
                    ${errorMessage}
                </div>
            </c:if>
            <form id="loginForm" action="login" onsubmit="return validateForm()" method="POST">
                <div class="form-group">
                    <input name="email" id="emailInput" value="${rememberedEmail}" type="email" class="form-control" maxlength="50" placeholder="E-mail">
                    <div class="invalid-feedback" id="emailError"></div>
                </div>

                <div class="form-group">
                    <input name="password" id="passwordInput" type="password" class="form-control" maxlength="50"
                           placeholder="Password">
                    <div class="invalid-feedback" id="passwordError"></div>
                </div>

                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="rememberMe"
                    ${not empty rememberedEmail ? 'checked' : ''}>
                    <label class="form-check-label" for="rememberMe">Remember me</label>
                </div>

                <button type="submit" class="mb-2 btn btn-success btn-block">Login</button>
                <a href="register" class="text-decoration-none">Click here to register</a>
                <p id="mainError" class="text-danger text-center mt-2" style="font-size: 0.9rem;"></p>
            </form>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<script>

    function validateForm() {
        const email = document.getElementById('emailInput');
        const password = document.getElementById('passwordInput');
        const emailError = document.getElementById('emailError');
        const passwordError = document.getElementById('passwordError');

        let isValid = true;

        email.classList.remove('is-invalid');
        password.classList.remove('is-invalid');
        emailError.textContent = '';
        passwordError.textContent = '';

        if (email.value.trim() === "") {
            emailError.textContent = 'Email is required';
            email.classList.add('is-invalid');
            isValid = false;
        }


        if (password.value.trim() === "") {
            passwordError.textContent = 'Password is required';
            password.classList.add('is-invalid');
            isValid = false;
        }

        if (!isValid) {
            if (email.classList.contains('is-invalid')) {
                email.focus();
            } else {
                password.focus();
            }
        }

        return isValid;
    }
</script>
</body>
</html>