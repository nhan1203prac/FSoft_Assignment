<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 01/05/2026
  Time: 10:46 am
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
    <title>View content</title>

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
                    <a onclick="handleLogout(event)" class="dropdown-item" href="#"><i
                            class="fa fa-sign-out mr-2"></i>Logout</a>

                </div>
            </li>


        </ul>
    </div>
</nav>
<div class="row no-gutters">
    <div class="col-3 border-right pr-0" style="min-height: 100vh; background-color: #f8f9fa;">
        <div class="input-group mb-3 p-3 border-bottom">
            <input id="searchInput" type="text" class="form-control" placeholder="Search">
            <div class="input-group-append">
                <button id="searchBtn" class="btn btn-outline-secondary" type="button">
                    <i class="fa fa-search"></i>
                </button>
            </div>
        </div>
        <div class="list-group list-group-flush">
            <a href="#" class="list-group-item list-group-item-action text-primary active-sidebar">
                <i class="fa fa-th-list mr-2"></i> View contents
            </a>
            <a href="${ctx}/form-content" class="list-group-item list-group-item-action  text-primary">
                <i class="fa fa-edit mr-2"></i> Form content
            </a>
        </div>
    </div>

    <div class="flex-grow-1 col-9 p-4 mt-3 right-content">

        <div id="loader" class="position-absolute w-100 h-100 d-none justify-content-center align-items-center"
             style="top: 0; left: 0;">
            <div class="text-center">
                <h4 class="text-secondary">Loading contents...</h4>
            </div>
        </div>

        <h3>View Content</h3>
        <div class="card shadow-sm">
            <div class="card-body">
                <table class="table border">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Title</th>
                        <th>Brief</th>
                        <th>Created Date</th>
                        <th>Action</th>

                    </tr>
                    </thead>
                    <tbody id="table-body">
                    </tbody>
                </table>
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
    const deleteContent = (id) => {
        if (confirm("Are you sure you want to delete this content?")) {
            $.ajax({
                url: '${ctx}/delete-content',
                type: 'POST',
                data: { id: id },
                success: function(response) {
                    alert("Deleted successfully!");
                    // const keyword = $('#searchInput').val();
                    location.reload();
                },
                error: function() {
                    alert("Error while deleting content.");
                }
            });
        }
    };
    $(document).ready(function () {
        const loadData = (keyword = "") => {
            $('#table-body').empty();
            $.ajax({
                url: '${ctx}/search-content',
                type: 'GET',
                data: {keyword: keyword},
                success: (data) => {
                    let html = ""
                    if (data.length === 0) {
                        html = "<tr><td colspan='4' class='text-center'>No Content Found</td></tr>"
                    } else {
                        $.each(data, (index, item) => {
                            html += "<tr>" +
                                "<td>" + (index + 1) + "</td>" +
                                "<td class='text-break'>" + item.title + "</td>" +
                                "<td class='text-break'>" + item.brief + "</td>" +
                                "<td>" + item.createDate + "</td>" +
                                "<td>" +
                                    "<a href='form-content?id=" + item.id + "' class='btn btn-sm btn-outline-primary mr-1' title='Edit'>" +
                                        "<i class='fa fa-edit'></i>" +
                                    "</a>" +
                                    "<button onclick='deleteContent(" + item.id + ")' class='btn btn-sm btn-outline-danger' title='Delete'>" +
                                        "<i class='fa fa-trash'></i>" +
                                    "</button>" +
                                "</td>" +
                                "</tr>";
                        });
                    }
                    $('#table-body').html(html)
                },
                complete: () => {
                    $('#loader').addClass('d-none').removeClass('d-flex')
                }
            });

        }
        loadData();



        $('#searchBtn').click(() => {
            let keyword = $('#searchInput').val();
            loadData(keyword)
        })
        $('#searchInput').keypress((e) => {
            if (e.which == 13) {
                let keyword = $('#searchInput').val();
                loadData(keyword);
            }
        });
    })
</script>

</body>

</html>
