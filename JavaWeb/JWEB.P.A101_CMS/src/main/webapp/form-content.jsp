<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/05/2026
  Time: 10:44 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">
    <link rel="stylesheet" href="${ctx}/css/styles.css">
    <title>Form content</title>
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
                    <a class="dropdown-item border-bottom" href="${ctx}/edit-profile"><i
                            class="fa fa-user mr-2"></i>User Profile</a>
                    <a onclick="handleLogout(event)" class="dropdown-item" href="#"><i class="fa fa-sign-out mr-2"></i>Logout</a>

                </div>
            </li>



        </ul>
    </div>
</nav>

<div class="row">
    <div class="col-3 border-right pr-0" style="min-height: 100vh; background-color: #f8f9fa;">
        <div class="">
            <div class="input-group mb-3 p-3 border-bottom">
                <input id="searchInput" type="text" class="form-control" placeholder="Search">
                <div class="input-group-append">
                    <button id="searchBtn" class="btn btn-outline-secondary" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>

            <div class="list-group list-group-flush ">
                <a href="./view-content.jsp" class="list-group-item list-group-item-action  text-primary ">
                    <i class="fa fa-th-list mr-2"></i> View contents
                </a>
                <a href="${ctx}/form-content"
                   class="list-group-item list-group-item-action    text-primary active-sidebar">
                    <i class="fa fa-edit mr-2"></i> Form content
                </a>
            </div>
        </div>
    </div>

    <div class="flex-grow-1 p-4 mt-3">
        <h3>${formTitle}</h3>
        <div class="card shadow-sm">
            <div class="card-header">Content Form Elements</div>
            <div class="card-body">
                <c:if test="not empty errorMessage">
                    <div class="alert alert-danger text-center">
                            ${errorMessage}
                    </div>
                </c:if>
                <form action="form-content" method="POST" style="max-width: 80%;" onsubmit="return validateForm()">
                    <input type="hidden" name="id" value="${content.id}">
                    <div class="form-group">
                        <label for="titleInput">Title</label>
                        <input name="title" value="${content.title}" type="text" class="form-control" id="titleInput" placeholder="Enter the title">
                        <div class="invalid-feedback" id="titleError"></div>
                    </div>

                    <div class="form-group">
                        <label for="briefInput">Brief</label>
                        <textarea name="brief" class="form-control" id="briefInput" rows="3">${content.brief}</textarea>
                        <div class="invalid-feedback" id="briefError"></div>
                    </div>

                    <div class="form-group">
                        <label for="contentInput">Content</label>
                        <textarea name="content" class="form-control" id="contentInput" rows="5">${content.content}</textarea>
                        <div class="invalid-feedback" id="contentError"></div>
                    </div>
                    <div class="button-group">
                        <button type="submit" class="btn btn-outline-dark ">Submit Button</button>
                        <button type="reset" class="btn btn-outline-dark">Reset Button</button>

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
        const title = document.getElementById('titleInput');
        const brief = document.getElementById('briefInput');
        const content = document.getElementById('contentInput');

        const titleError = document.getElementById('titleError');
        const briefError = document.getElementById('briefError');
        const contentError = document.getElementById('contentError');

        let valid = true;

        [title, brief, content].forEach(el => el.classList.remove('is-invalid'));

        const tVal = title.value.trim();
        if (tVal.length === 0) {
            titleError.textContent = "Title is required!";
            title.classList.add('is-invalid');
            valid = false;
        } else if (tVal.length < 10 || tVal.length > 200) {
            titleError.textContent = "Title must be at least 10 and maximum 200 characters.";
            title.classList.add('is-invalid');
            valid = false;
        }

        const bVal = brief.value.trim();
        if (bVal.length === 0) {
            briefError.textContent = "Brief is required!";
            brief.classList.add('is-invalid');
            valid = false;
        } else if (bVal.length < 30 || bVal.length > 150) {
            briefError.textContent = "Brief must be at least 30 and maximum 150 characters.";
            brief.classList.add('is-invalid');
            valid = false;
        }

        const cVal = content.value.trim();
        if (cVal.length === 0) {
            contentError.textContent = "Content is required!";
            content.classList.add('is-invalid');
            valid = false;
        } else if (cVal.length < 50 || cVal.length > 1000) {
            contentError.textContent = "Content must be at least 50 and maximum 1000 characters.";
            content.classList.add('is-invalid');
            valid = false;
        }

        if (!valid) {
            document.querySelector('.is-invalid').focus();
        }

        return valid;
    };

    $(document).ready(function (){

        const loadData = (keyword = "")=>{

            $('#table-body').empty();
            $.ajax({
                url: '${ctx}/search-content',
                type:'GET',
                data: {keyword: keyword},
                success: (data)=>{
                    let html = ""
                    if(data.length ===0){
                        html = "<tr><td colspan='4' class='text-center'>No Content Found</td></tr>"
                    }else{
                        $.each(data,(index,item)=>{
                            html += "<tr>" +
                                "<td>" + (index + 1) + "</td>" +
                                "<td>" + item.title + "</td>" +
                                "<td>" + item.brief + "</td>" +
                                "<td>" + item.createDate + "</td>";
                        });
                    }
                    $('table-body').html(html)
                },
                complete: ()=>{
                    $('loader').addClass('d-none').removeClass('d-flex')
                }
            });

        }
        loadData()

        $('#searchBtn').click(()=>{
            let keyword = $('#searchInput').val();
            validateForm()
            loadData(keyword)
        })
        $('#searchInput').keypress((e) => {
            if (e.which == 13) {
                validateForm()
                loadData($(this).val());
            }
        });
    })
</script>

</body>



</html>
