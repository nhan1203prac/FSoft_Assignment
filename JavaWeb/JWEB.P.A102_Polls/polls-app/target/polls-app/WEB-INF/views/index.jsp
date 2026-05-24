<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <title>Vote - Polls</title>
</head>
<body>
<c:set var="currentPage" value="vote" scope="request"/>
<%@ include file="navbar.jsp" %>

<div class="container mt-5">

    <c:if test="${empty polls}">
        <div class="alert alert-info">No active polls available at the moment.</div>
    </c:if>

    <form id="vote-form">
    <div id="polls-wrapper">
        <c:forEach items="${polls}" var="poll" varStatus="pStatus">
        <div class="poll-item mb-5 p-4 border rounded" data-poll-id="${poll.id}">
            <h2 class="font-weight-bold mb-4 border-bottom pb-2">${poll.name}</h2>
            <ol>
                <c:forEach items="${poll.pollQuestions}" var="pq" varStatus="qStatus">
                <c:set var="q" value="${pq.question}"/>
                <li class="mb-4">
                    <p class="h5 font-weight-normal">
                        ${q.content}
                        <c:if test="${q.required}">
                            <span class="text-danger required">*</span>
                        </c:if>
                    </p>
                    <div class="opt-res mt-2 ml-3"
                         data-required="${q.required}"
                         data-question-id="${q.id}">
                        <c:forEach items="${q.questionAnswers}" var="qa">
                        <c:set var="inputType" value="${q.multiple ? 'checkbox' : 'radio'}"/>
                        <div class="custom-control custom-${inputType} mb-2">
                            <input type="${inputType}"
                                   id="ans_${qa.answer.id}"
                                   name="q_${q.id}"
                                   value="${qa.answer.id}"
                                   class="custom-control-input answer-input">
                            <label class="custom-control-label" for="ans_${qa.answer.id}" style="cursor:pointer;">
                                ${qa.answer.content}
                            </label>
                        </div>
                        </c:forEach>
                    </div>
                    <div class="text-danger small err-question" id="err_q_${q.id}"></div>
                </li>
                </c:forEach>
            </ol>
        </div>
        </c:forEach>
    </div>

    <c:if test="${not empty polls}">
    <div class="btn-wrap mb-4">
        <button type="button" class="btn btn-success btn-retain" id="submitVoteBtn">Submit Vote</button>
    </div>
    </c:if>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
<script>
const CTX = '${pageContext.request.contextPath}';

$('#loginBtn').click(function() {
    $('.text-danger.small').text('');
    $('#login-error').addClass('d-none');

    const username = $('#loginUsername').val().trim();
    const password = $('#loginPassword').val().trim();

    let valid = true;
    if (!username) { $('#err-username').text('Username is required'); valid = false; }
    else if (username.length < 3) { $('#err-username').text('Username must be at least 3 characters'); valid = false; }
    if (!password) { $('#err-password').text('Password is required'); valid = false; }
    if (!valid) return;

    $.ajax({
        url: CTX + '/login',
        method: 'POST',
        data: { username, password },
        success: function(res) {
            alert('Login successful!');
            location.reload();
        },
        error: function(xhr) {
            const msg = JSON.parse(xhr.responseText).message || 'Login failed';
            $('#login-error').removeClass('d-none').text(msg);
        }
    });
});

$('#logoutBtn').click(function() {
    if (confirm('Do you want to logout?')) {
        window.location.href = CTX + '/login';
    }
});

$('#submitVoteBtn').click(function() {
    $('.err-question').text('');

    let answerIds = [];
    let hasError = false;

    $('.opt-res').each(function() {
        const required = $(this).data('required');
        const qId = $(this).data('question-id');
        const checked = $(this).find('input:checked');

        if (required === true && checked.length === 0) {
            $('#err_q_' + qId).text('This question is required, please select an answer.');
            hasError = true;
        }
        checked.each(function() {
            answerIds.push($(this).val());
        });
    });

    if (hasError) {
        $('html, body').animate({ scrollTop: $('.text-danger:visible').first().offset().top - 100 }, 400);
        return;
    }

    if (answerIds.length === 0) {
        alert('Please select at least one answer!');
        return;
    }

    $.ajax({
        url: CTX + '/vote',
        method: 'POST',
        data: { 'answerIds[]': answerIds },
        success: function(res) {
            alert(res.message || 'Thank you for voting!');
            location.reload();
        },
        error: function(xhr) {
            const msg = JSON.parse(xhr.responseText).message || 'Error submitting vote';
            alert(msg);
        }
    });
});
</script>
</body>
</html>
