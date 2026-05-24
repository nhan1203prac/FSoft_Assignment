<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <title>Results - ${poll.name}</title>
</head>
<body>
<c:set var="currentPage" value="list" scope="request"/>
<%@ include file="navbar.jsp" %>

<div class="container mt-5">
    <c:choose>
        <c:when test="${poll == null}">
            <div class="alert alert-warning">Poll not found.</div>
        </c:when>
        <c:otherwise>
        <h2 class="mb-2">${poll.name}</h2>
        <span class="badge badge-${poll.status == 'ACTIVE' ? 'success' : 'secondary'} mb-4">${poll.status}</span>

            <c:forEach items="${poll.pollQuestions}" var="pq" varStatus="qStatus">
                <c:set var="q" value="${pq.question}"/>
                <div class="card mb-4">
                    <div class="card-header">
                        <strong>Q${qStatus.count}:</strong> ${q.content}
                        <c:if test="${q.required}"><span class="text-danger"> *</span></c:if>
                    </div>
                    <div class="card-body">
                        <c:set var="totalVotes" value="0"/>

                        <c:forEach items="${q.questionAnswers}" var="qa">
                            <c:if test="${not empty qa.answer}">
                                <c:set var="vc" value="${qa.answer.voteCount != null ? qa.answer.voteCount : 0}"/>
                                <c:set var="totalVotes" value="${totalVotes + vc}"/>
                            </c:if>
                        </c:forEach>

                        <c:forEach items="${q.questionAnswers}" var="qa">
                            <c:if test="${not empty qa.answer}">
                                <c:set var="answer" value="${qa.answer}"/>
                                <c:set var="voteCount" value="${answer.voteCount != null ? answer.voteCount : 0}"/>

                                <c:choose>
                                    <c:when test="${totalVotes > 0}">
                                        <c:set var="pct" value="${(voteCount * 100) / totalVotes}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="pct" value="0"/>
                                    </c:otherwise>
                                </c:choose>

                                <!-- Làm tròn phần trăm cho đẹp -->
                                <c:set var="pctDisplay" value="${pct}"/>

                                <div class="mb-3">
                                    <div class="d-flex justify-content-between">
                                        <span>${answer.content}</span>
                                        <span>${voteCount} vote(s) (${pctDisplay}%)</span>
                                    </div>
                                    <div class="progress mt-1">
                                        <div class="progress-bar bg-success" role="progressbar"
                                             style="width: ${pct}%"
                                             aria-valuenow="${pct}" aria-valuemin="0" aria-valuemax="100">
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>

                        <small class="text-muted">Total votes for this question: ${totalVotes}</small>
                    </div>
                </div>
            </c:forEach>

        <a href="${pageContext.request.contextPath}/polls/list" class="btn btn-secondary">Back to List</a>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
<script>
const CTX = '${pageContext.request.contextPath}';
$('#loginBtn').click(function() {
    $('.text-danger.small').text('');
    const username = $('#loginUsername').val().trim();
    const password = $('#loginPassword').val().trim();
    if (!username || username.length < 3) { $('#err-username').text('Min 3 chars'); return; }
    if (!password) { $('#err-password').text('Required'); return; }
    $.ajax({ url: CTX + '/login', method: 'POST', data: { username, password },
        success: () => { alert('Login successful!'); location.reload(); },
        error: (xhr) => { $('#login-error').removeClass('d-none').text(JSON.parse(xhr.responseText).message); }
    });
});
$('#logoutBtn').click(function() {
    if (confirm('Do you want to logout?')) window.location.href = CTX + '/login';
});
</script>
</body>
</html>
