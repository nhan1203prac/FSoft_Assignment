<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 28/04/2026
  Time: 9:28 am
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <title>Register - CMS</title>
</head>
<body>
<div class="container mt-5 d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div class="card" style="width: 400px">
        <div class="card-header ">
            <span class="text-left w-100">Register</span>

        </div>
        <div class="card-body p-4">
            <form id="registerForm" action="register" method="post" onsubmit="return validateForm()">
                <div class="form-group">
                    <input id="usernameInput" name="username" type="text" class="form-control" placeholder="Username">
                    <div class="invalid-feedback" id="usernameError"></div>
                </div>

                <div class="form-group">
                    <input id="emailInput" name="email" type="email" class="form-control" placeholder="E-mail">
                    <div class="invalid-feedback" id="emailError"></div>
                </div>

                <div class="form-group">
                    <input id="passwordInput" name="password" type="password" class="form-control" placeholder="Password">
                    <div class="invalid-feedback" id="passwordError"></div>
                </div>

                <div class="form-group">
                    <input id="rePasswordInput" name="rePassword" type="password" class="form-control" placeholder="Re-enter Password">
                    <div class="invalid-feedback" id="rePasswordError"></div>
                </div>

                <button type="submit" class="btn btn-success btn-block">Register</button>
                <a href="login" class="text-decoration-none">Login</a>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<script>
    function validateForm() {
        const username = document.getElementById('usernameInput');
        const email = document.getElementById('emailInput');
        const password = document.getElementById('passwordInput');
        const rePassword = document.getElementById('rePasswordInput');

        const usernameError = document.getElementById('usernameError');
        const emailError = document.getElementById('emailError');
        const passwordError = document.getElementById('passwordError');
        const rePasswordError = document.getElementById('rePasswordError');

        let valid = true;

        [username, email, password, rePassword].forEach(el => {
            el.classList.remove('is-invalid');
        });

        const userVal = username.value.trim();
        if (userVal === "") {
            usernameError.textContent = "Username is required";
            username.classList.add('is-invalid');
            valid = false;
        } else if (userVal.length < 3 || userVal.length > 30) {
            usernameError.textContent = "Username must be between 3 and 30 characters";
            username.classList.add('is-invalid');
            valid = false;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const emailVal = email.value.trim();
        if (emailVal === "") {
            emailError.textContent = "Email is required";
            email.classList.add('is-invalid');
            valid = false;
        } else if (!emailRegex.test(emailVal)) {
            emailError.textContent = "Please enter a valid email address (e.g., example@gmail.com)";
            email.classList.add('is-invalid');
            valid = false;
        }

        const passVal = password.value.trim();
        if (passVal === "") {
            passwordError.textContent = "Password is required";
            password.classList.add('is-invalid');
            valid = false;
        } else if (passVal.length < 8 || passVal.length > 30) {
            passwordError.textContent = "Password must be between 8 and 30 characters";
            password.classList.add('is-invalid');
            valid = false;
        }

        const rePassVal = rePassword.value.trim();
        if (rePassVal === "") {
            rePasswordError.textContent = "Re-enter password is required";
            rePassword.classList.add('is-invalid');
            valid = false;
        } else if (rePassVal !== passVal) {
            rePasswordError.textContent = "Passwords do not match";
            rePassword.classList.add('is-invalid');
            valid = false;
        }

        if (!valid) {
            const firstError = document.querySelector('.is-invalid');
            if (firstError) firstError.focus();
        }

        return valid;
    }
</script>
</body>
</html>
