<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <title>Create Poll</title>
</head>
<body>
<c:set var="currentPage" value="create" scope="request"/>

<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content p-4 w-80">
            <div class="modal-body">
                <form id="loginForm" class="mb-5">
                    <div class="form-group">
                        <label for="loginUsername" class="font-weight-bold">Alias:</label>
                        <input type="text" class="form-control" id="loginUsername" placeholder="Username" required>
                        <div class="text-danger small" id="err-username"></div>
                    </div>
                    <div class="form-group">
                        <label for="loginPassword" class="font-weight-bold">Password:</label>
                        <input type="password" class="form-control" id="loginPassword" placeholder="Password" required>
                        <div class="text-danger small" id="err-password"></div>
                    </div>
                    <div class="form-group form-check">
                        <input type="checkbox" class="form-check-input" id="rememberMe">
                        <label class="form-check-label font-weight-bold" for="rememberMe">Remember me</label>
                    </div>
                    <div class="text-danger small mb-2 d-none" id="login-error"></div>
                    <button type="button" id="loginBtn" class="btn btn-success">Sign in</button>
                </form>
                <hr>
            </div>
            <div class="modal-footer border-0 justify-content-center mt-2">
                <button class="btn btn-light pl-3 pr-3 pt-1 pb-1 border-black" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="navbar.jsp" %>

<div class="container mt-5">
    <h1>Create interview</h1>

    <div id="form-error" class="alert alert-danger d-none mt-3"></div>
    <div id="form-success" class="alert alert-success d-none mt-3"></div>

    <div class="list-question">
        <div class="row mb-4 mt-5">
            <div class="col-3"></div>
            <div class="col-8">
                <input type="text" placeholder="Name poll" name="name-poll" class="input-field w-100 form-control" id="poll-name-input">
                <div class="text-danger small error-name" id="error-name"></div>
            </div>
        </div>
        <hr>

        <div id="question-wrapper">
        </div>
    </div>

    <button class="btn btn-success" id="add-question">+ Add question</button>

    <div class="btn-wrap mt-4">
        <button class="btn btn-success btn-retain mb-3" id="btn-retain">Retain</button>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>

<script>
    const CTX = '${pageContext.request.contextPath}';
    let questionCount = 0;

    function createQuestionBlock(index) {
        return `
    <div class="question-block border rounded p-4 mb-4" data-index="${index}">
        <div class="d-flex justify-content-between align-items-center mb-2">
            <strong>Question ${index + 1}</strong>
            <button class="btn btn-sm btn-danger remove-question-btn">Remove</button>
        </div>
        <div class="row mt-4 mb-4">
            <div class="col-3"></div>
            <div class="col-8">
                <input type="text" placeholder="Enter your question" class="question-input input-field form-control">
                <div class="text-danger small error-question"></div>
                <div class="mt-4">
                    <input type="checkbox" id="mandatory_${index}" class="mandatory mr-2">
                    <label for="mandatory_${index}">Mandatory</label>
                </div>
                <div class="mt-4">
                    <input type="checkbox" id="multiple_${index}" class="mul-select mr-2">
                    <label for="multiple_${index}">You can select multiple options</label>
                </div>
            </div>
        </div>
        <div class="row mt-4 mb-4">
            <div class="col-3 text-right">Possible answers</div>
            <div class="col-8">
                <div class="answers-container">
                    <div class="input-group mb-2">
                        <input type="text" class="input-field form-control answer-item w-50" placeholder="Type your answer">
                        <div class="input-group-append">
                            <button class="btn btn-success add-answer-btn h-100" type="button">+</button>
                        </div>
                    </div>
                </div>
                <div class="text-danger small error-answer"></div>
            </div>
        </div>
    </div>`;
    }

    function addQuestion() {
        const block = createQuestionBlock(questionCount);
        $('#question-wrapper').append(block);
        questionCount++;
        renumberQuestions();
    }

    function renumberQuestions() {
        $('.question-block').each(function(i) {
            $(this).attr('data-index', i);
            $(this).find('strong').first().text('Question ' + (i + 1));
            $(this).find('.mandatory').attr('id', 'mandatory_' + i);
            $(this).find('.mandatory').next('label').attr('for', 'mandatory_' + i);
            $(this).find('.mul-select').attr('id', 'multiple_' + i);
            $(this).find('.mul-select').next('label').attr('for', 'multiple_' + i);
        });
    }

    addQuestion();

    $('#add-question').click(addQuestion);

    $(document).on('click', '.remove-question-btn', function() {
        if ($('.question-block').length <= 1) {
            alert('A poll must have at least one question.');
            return;
        }
        $(this).closest('.question-block').remove();
        renumberQuestions();
    });

    $(document).on('click', '.add-answer-btn', function(e) {
        e.preventDefault();
        const container = $(this).closest('.question-block').find('.answers-container');
        container.append(`
        <div class="input-group mb-2">
            <input type="text" class="input-field form-control answer-item w-50" placeholder="Type your answer">
            <div class="input-group-append">
                <button class="btn btn-danger remove-answer-btn h-100" type="button">-</button>
            </div>
        </div>`);
    });

    $(document).on('click', '.remove-answer-btn', function() {
        $(this).closest('.input-group').remove();
    });

    $('#btn-retain').click(function(e) {
        e.preventDefault();

        $('.text-danger').text('');
        $('#form-error').addClass('d-none');
        $('#form-success').addClass('d-none');

        let valid = true;

        const pollName = $('#poll-name-input').val().trim();
        if (!pollName || pollName.length < 3 || pollName.length > 255) {
            $('#error-name').text('Poll name must be between 3 and 255 characters');
            valid = false;
        }

        const questions = [];

        $('.question-block').each(function(i) {
            const $block = $(this);
            const question = $block.find('.question-input').val().trim();
            const mandatory = $block.find('.mandatory').is(':checked') ? 'on' : 'off';
            const multiple = $block.find('.mul-select').is(':checked') ? 'on' : 'off';

            if (!question || question.length < 3 || question.length > 255) {
                $block.find('.error-question').text('Question must be between 3 and 255 characters');
                valid = false;
            }

            const answers = [];
            $block.find('.answer-item').each(function(aIdx) {
                const val = $(this).val().trim();
                if (val) {
                    if (val.length < 3 || val.length > 200) {
                        $block.find('.error-answer').text('Answer ' + (aIdx + 1) + ' must be between 3 and 200 characters');
                        valid = false;
                    } else {
                        answers.push(val);
                    }
                }
            });

            if (answers.length === 0) {
                $block.find('.error-answer').text('Question ' + (i + 1) + ': At least one answer is required');
                valid = false;
            }

            questions.push({ question, mandatory, multiple, answers });
        });

        if (!valid) {
            $('html, body').animate({ scrollTop: $('.text-danger:visible').first().offset().top - 80 }, 400);
            return;
        }

        let params = 'pollName=' + encodeURIComponent(pollName);
        questions.forEach((q, i) => {
            params += '&question[]=' + encodeURIComponent(q.question);
            params += '&mandatory[]=' + encodeURIComponent(q.mandatory);
            params += '&multiple[]=' + encodeURIComponent(q.multiple);
            q.answers.forEach(a => {
                params += '&answers%5B' + i + '%5D%5B%5D=' + encodeURIComponent(a); // tương đương mảng answers[i][]
            });
        });

        $.ajax({
            url: CTX + '/polls/create',
            method: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: params,
            success: function(res) {
                $('#form-success').removeClass('d-none').text('Poll created successfully!');
                setTimeout(() => { window.location.href = CTX + '/polls/list'; }, 1000);
            },
            error: function(xhr) {
                let msg = 'Error creating poll';
                try {
                    msg = JSON.parse(xhr.responseText).message || msg;
                } catch(e) {}
                $('#form-error').removeClass('d-none').text(msg);
            }
        });
    });

    $('#loginBtn').click(function() {
        $('.text-danger.small').text('');
        $('#login-error').addClass('d-none');

        const username = $('#loginUsername').val().trim();
        const password = $('#loginPassword').val().trim();
        let valid = true;

        if (!username) { $('#err-username').text('Username is required'); valid = false; }
        if (!password) { $('#err-password').text('Password is required'); valid = false; }
        if (!valid) return;

        $.ajax({
            url: CTX + '/login',
            method: 'POST',
            data: { username, password },
            success: () => {
                alert('Login successful!');
                location.reload();
            },
            error: (xhr) => {
                let msg = 'Login failed';
                try {
                    msg = JSON.parse(xhr.responseText).message || msg;
                } catch(e) {}
                $('#login-error').removeClass('d-none').text(msg);
            }
        });
    });

    $('#logoutBtn').click(function() {
        if (confirm('Do you want to logout?')) {
            window.location.href = CTX + '/login';
        }
    });
</script>
</body>
</html>