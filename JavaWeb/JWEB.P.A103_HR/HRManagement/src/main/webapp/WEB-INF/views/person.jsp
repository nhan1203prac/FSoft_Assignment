<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visitor Information - HR Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        body {
            background-color: #f8f9fa !important;
            color: #000000 !important;
            font-family: Arial, sans-serif;
        }

        .main-card-container {
            background-color: #ffffff !important;
            box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075) !important;
            padding: 30px !important;
            margin-top: 24px;
            border-radius: 4px;
        }

        .banner-header {
            background: linear-gradient(135deg, #1e5631 0%, #2d7f47 100%) !important;
            color: #ffffff !important;
            text-align: center;
            padding: 20px;
            font-weight: bold;
            font-size: 22px;
            letter-spacing: 1px;
            border-radius: 4px;
        }

        .page-title-box {
            border-bottom: 2px solid #28a745;
            padding-bottom: 8px;
            margin-bottom: 25px;
        }

        .form-inner-box {
            max-width: 750px;
            width: 100%;
            margin: 0 auto;
            padding: 20px;
        }

        .form-group row label,
        .col-form-label,
        label {
            color: #000000 !important;
            font-weight: 500;
        }

        .form-control,
        input[type="text"].form-control,
        select.form-control,
        textarea.form-control {
            background-color: #ffffff !important;
            color: #000000 !important;
            border: 1px solid #ced4da !important;
        }

        .form-control:focus {
            border-color: #28a745 !important;
            box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.25) !important;
        }

        .custom-control-label {
            color: #000000 !important;
            cursor: pointer;
        }

        .text-muted {
            color: #6c757d !important;
        }

        .text-info {
            color: #17a2b8 !important;
        }
    </style>
</head>
<body>
<div class="container main-card-container">

    <img src="${pageContext.request.contextPath}/image/banner.jpg" alt="" class="img-fluid mb-4 w-100  banner-top">

    <div class="border p-2 mb-4">
        <h2 class="text-success">Visitor Information</h2>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show mb-3" role="alert">
            <strong>Error:</strong> ${errorMessage}
            <button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>
        </div>
    </c:if>

    <form id="registrationForm" action="${pageContext.request.contextPath}/person" method="post" class="d-flex justify-content-center">
        <div class="form-inner-box">

            <div class="form-group row">
                <label for="firstName" class="col-sm-3 col-form-label">First name: </label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="firstName" name="firstName"
                           value="<c:out value='${param.firstName}'/>" maxlength="20">
                    <div class="invalid-feedback">First name is mandatory (max 20), letters only.</div>
                </div>
            </div>

            <div class="form-group row">
                <label for="lastName" class="col-sm-3 col-form-label">Last name: </label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="lastName" name="lastName"
                           value="<c:out value='${param.lastName}'/>" maxlength="20">
                    <div class="invalid-feedback">Last name is mandatory (max 20), letters only.</div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">Gender:</label>
                <div class="col-sm-9">
                    <select class="form-control" id="gender" name="gender">
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>
            </div>

            <div class="form-group row">
                <label for="telephone" class="col-sm-3 col-form-label">Telephone:</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="telephone" name="telephone"
                           value="<c:out value='${param.telephone}'/>" maxlength="11">
                    <div class="invalid-feedback">Must be numbers only (max 11 digits).</div>
                </div>
            </div>

            <div class="form-group row">
                <label for="email" class="col-sm-3 col-form-label">Email:</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="email" name="email"
                           value="<c:out value='${param.email}'/>" maxlength="50">
                    <div class="invalid-feedback">Invalid email format (max 50 characters).</div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">You are in: </label>
                <div class="col-sm-9 d-flex flex-wrap align-items-center">
                    <c:forEach var="reg" items="${['Europe','Africa','Australia','Asia','America']}">
                        <div class="custom-control custom-radio mr-3 mb-1">
                            <input type="radio" id="region${reg}" name="region" class="custom-control-input" value="${reg}">
                            <label class="custom-control-label" for="region${reg}">${reg}</label>
                        </div>
                    </c:forEach>
                    <div id="error-region" class="text-danger w-100" style="display:none; font-size:80%;">
                        This field is mandatory.
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">What are your hobbies?</label>
                <div class="col-sm-9">
                    <div class="row">
                        <c:forEach var="hobby" items="${['Swimming','Cooking','Shopping','Sport','Dance','Sing']}" varStatus="status">
                            <div class="col-sm-4 mb-1">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" class="custom-control-input"
                                           id="hobby${status.index}" name="hobbies" value="${hobby}">
                                    <label class="custom-control-label" for="hobby${status.index}">${hobby}</label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <label class="col-sm-3 col-form-label">Description:</label>
                <div class="col-sm-9">
                    <textarea class="form-control" id="description" name="description"
                              rows="5" maxlength="200"><c:out value='${param.description}'/></textarea>
                    <div class="invalid-feedback">Description cannot exceed 200 characters.</div>
                    <small class="text-muted"><span id="descCount">0</span>/200</small>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-9 offset-sm-3">
                    <a href="#" id="helpLink" class="text-info">
                        <small>Help to register</small>
                    </a>
                    <div id="helpBox"></div>
                </div>
            </div>

            <div class="text-center mt-4">
                <button type="submit" class="btn btn-success px-5">Register</button>
                <a href="${pageContext.request.contextPath}/search" class="btn btn-secondary ml-3 px-5">Search</a>
            </div>

        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function () {

        const $desc = $('#description');
        function updateCount() { $('#descCount').text($desc.val().length); }
        $desc.on('input', updateCount);
        updateCount();

        $('#helpLink').on('click', function (e) {
            e.preventDefault();
            const $box = $('#helpBox');
            if ($box.is(':visible')) {
                $box.slideUp();
                return;
            }
            $.ajax({
                url: '${pageContext.request.contextPath}/help',
                type: 'GET',
                success: function (data) {
                    $box.html(data).slideDown();
                },
                error: function () {
                    $box.html('<p class="text-danger">Could not load help content.</p>').slideDown();
                }
            });
        });

        const namePattern  = /^[a-zA-ZÀ-ỹ\s]+$/;
        const phonePattern = /^[0-9]+$/;
        const emailPattern = /^[a-zA-Z0-9._%+\-]+@[a-zA-Z0-9.\-]+\.[a-zA-Z]{2,}$/;

        $('#registrationForm').on('submit', function (e) {
            e.preventDefault();

            $('.form-control').removeClass('is-invalid');
            $('#error-region').hide();
            let isValid = true;

            const firstName   = $('#firstName').val().trim();
            const lastName    = $('#lastName').val().trim();
            const telephone   = $('#telephone').val().trim();
            const email       = $('#email').val().trim();
            const region      = $('input[name="region"]:checked').val();
            const description = $('#description').val().trim();

            if (!firstName || firstName.length > 20 || !namePattern.test(firstName)) {
                $('#firstName').addClass('is-invalid'); isValid = false;
            }
            if (!lastName || lastName.length > 20 || !namePattern.test(lastName)) {
                $('#lastName').addClass('is-invalid'); isValid = false;
            }
            if (telephone && (telephone.length > 11 || !phonePattern.test(telephone))) {
                $('#telephone').addClass('is-invalid'); isValid = false;
            }
            if (email && (email.length > 50 || !emailPattern.test(email))) {
                $('#email').addClass('is-invalid'); isValid = false;
            }
            if (!region) {
                $('#error-region').show(); isValid = false;
            }
            if (description.length > 200) {
                $('#description').addClass('is-invalid'); isValid = false;
            }

            if (isValid) {
                this.submit();
            }
        });
    });
</script>
</body>
</html>