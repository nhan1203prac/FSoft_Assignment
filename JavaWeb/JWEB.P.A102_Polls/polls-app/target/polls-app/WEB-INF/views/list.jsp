<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <title>Poll List</title>
</head>
<body>
<c:set var="currentPage" value="list" scope="request"/>
<%@ include file="navbar.jsp" %>

<div class="container mt-5">
    <div class="btn-group mr-2 mb-4" role="group">
        <a href="${pageContext.request.contextPath}/polls/list" class="btn btn-success ${currentStatus == 'ALL' ? 'active' : ''}">All</a>
        <a href="${pageContext.request.contextPath}/polls/list?status=ACTIVE" class="btn btn-success ${currentStatus == 'ACTIVE' ? 'active' : ''}">Active</a>
        <a href="${pageContext.request.contextPath}/polls/list?status=DRAFT" class="btn btn-success ${currentStatus == 'DRAFT' ? 'active' : ''}">Drafts</a>
        <a href="${pageContext.request.contextPath}/polls/list?status=CLOSED" class="btn btn-success ${currentStatus == 'CLOSED' ? 'active' : ''}">Closed</a>
    </div>

    <div id="alert-box"></div>

    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Poll Name</th>
            <th scope="col">Status</th>
            <th scope="col">Created At</th>
            <th scope="col">Management</th>
        </tr>
        </thead>
        <tbody id="table-body">
        <c:choose>
            <c:when test="${empty polls}">
                <tr><td colspan="5" class="text-center">No polls found.</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${polls}" var="poll">
                <tr data-id="${poll.id}">
                    <th scope="row">${poll.id}</th>
                    <td>${poll.name}</td>
                    <td>
                        <span class="badge badge-${poll.status == 'ACTIVE' ? 'success' : poll.status == 'CLOSED' ? 'secondary' : 'warning'}">
                            ${poll.status}
                        </span>
                    </td>
                    <td>${poll.createdAt}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/polls/results/${poll.id}"
                           class="btn btn-sm btn-info">View Results</a>
                        <c:if test="${sessionScope.loggedUser != null && sessionScope.loggedUser.role == 'ADMIN'}">
                            <c:if test="${poll.status != 'CLOSED'}">
                            <button class="btn btn-sm btn-warning btn-close-poll"
                                    data-id="${poll.id}">Close</button>
                            </c:if>
                            <button class="btn btn-sm btn-danger btn-delete"
                                    data-id="${poll.id}">Delete</button>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
<script>
const CTX = '${pageContext.request.contextPath}';

function showAlert(msg, type) {
    $('#alert-box').html('<div class="alert alert-' + type + '">' + msg + '</div>');
    setTimeout(() => $('#alert-box').html(''), 3000);
}

$(document).on('click', '.btn-delete', function() {
    const pollId = $(this).data('id');
    const $row = $(this).closest('tr');

    if (!confirm('Are you sure you want to delete this poll?')) return;

    $.ajax({
        url: CTX + '/polls/delete/' + pollId,
        method: 'POST',
        success: function(res) {
            $row.remove();
            showAlert('Poll deleted successfully!', 'success');
            if ($('#table-body tr').length === 0) {
                $('#table-body').html('<tr><td colspan="5" class="text-center">No polls found.</td></tr>');
            }
        },
        error: function(xhr) {
            const msg = JSON.parse(xhr.responseText).message || 'Delete failed';
            showAlert(msg, 'danger');
        }
    });
});

// Close poll via Ajax
$(document).on('click', '.btn-close-poll', function() {
    const pollId = $(this).data('id');
    const $btn = $(this);

    if (!confirm('Close this poll? Users will no longer be able to vote.')) return;

    $.ajax({
        url: CTX + '/polls/close/' + pollId,
        method: 'POST',
        success: function() {
            $btn.closest('tr').find('.badge').removeClass('badge-success').addClass('badge-secondary').text('CLOSED');
            $btn.remove();
            showAlert('Poll closed.', 'success');
        },
        error: function() {
            showAlert('Error closing poll.', 'danger');
        }
    });
});

// Login / Logout
$('#loginBtn').click(function() {
    $('.text-danger.small').text('');
    $('#login-error').addClass('d-none');
    const username = $('#loginUsername').val().trim();
    const password = $('#loginPassword').val().trim();
    let valid = true;
    if (!username) { $('#err-username').text('Username is required'); valid = false; }
    else if (username.length < 3) { $('#err-username').text('Min 3 chars'); valid = false; }
    if (!password) { $('#err-password').text('Password is required'); valid = false; }
    if (!valid) return;
    $.ajax({
        url: CTX + '/login', method: 'POST', data: { username, password },
        success: () => { alert('Login successful!'); location.reload(); },
        error: (xhr) => {
            $('#login-error').removeClass('d-none').text(JSON.parse(xhr.responseText).message || 'Login failed');
        }
    });
});
$('#logoutBtn').click(function() {
    if (confirm('Do you want to logout?')) window.location.href = CTX + '/login';
});
</script>
</body>
</html>
