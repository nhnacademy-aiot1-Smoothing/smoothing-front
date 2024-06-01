document.addEventListener('DOMContentLoaded', function () {

    let signupButton = document.getElementById('signupButton');

    let sendCertificationNumberButton = document.getElementById('sendCertificationNumberButton');
    let timerText = document.createElement('span');
    let timerInterval;

    let duplicationButton = document.getElementById('duplicationButton');

    duplicationButton.addEventListener('click', function () {

        let userIdInput = document.getElementById('userId');

        userIdInput.addEventListener('input', function () {

            duplicationButton.disabled = userIdInput.value.trim() === '';
        });
        let userId = document.getElementById('userId').value;

        if(!userId || /\s/.test(userId)) {
            alert("아이디에 공백을 입력할 수 없습니다.");
            return false;
        }

        let url = "/existUser" + "?userId=" + userId;
        let xhr = new XMLHttpRequest();

        xhr.open('GET', url, true);
        xhr.send();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log(xhr.responseText);
                    alert("사용 가능한 아이디 입니다.");
                    duplicationButton.disabled = true;
                } else if (xhr.status === 409) {
                    alert("사용 불가능한 아이디 입니다.")
                } else {
                    console.log("오류 발생");
                }
            }
        };

    });


    let domainSelect = document.getElementById('domainSelect');
    let domain = document.getElementById('emailDomain');

    // let selectedOption = domainSelect.options[domainSelect.selectedIndex];

    domainSelect.addEventListener('change', function () {
        let selectedOption = this.options[this.selectedIndex];
        domain.value = selectedOption.value;
    });

    sendCertificationNumberButton.addEventListener('click', function () {

        let email_rule =  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

        let email = document.getElementById('userEmail').value;

        if (!email) {
            alert("이메일을 입력해주세요.");
            return false;
        }

        if (!domain.value) {
            alert("도메인을 입력해주세요.");
            return false;
        }


        let userEmail = email + "@" + domain.value;

        if (!email_rule.test(userEmail)) {
            alert("이메일을 형식에 맞게 입력해주세요.")
            return false;
        }

        let emailCertificationRequest = JSON.stringify({
            userEmail: userEmail
        });

        let url = '/requestCertificationNumber';
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(emailCertificationRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("인증번호가 발급되었습니다.");
                    sendCertificationNumberButton.disabled = true;
                    timerInterval = startTimer();
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });

    function startTimer() {

        let totalTime = 180;
        let minutes, seconds;
        timerText.innerText = '3:00';
        sendCertificationNumberButton.parentElement.appendChild(timerText);

        return setInterval(function () {
            minutes = Math.floor(totalTime / 60);
            seconds = totalTime % 60;

            minutes = minutes < 10 ? '0' + minutes : minutes;
            seconds = seconds < 10 ? '0' + seconds : seconds;

            timerText.innerText = minutes + ':' + seconds;

            if (totalTime <= 0) {
                clearInterval(timerInterval);
                timerText.innerText='';
                sendCertificationNumberButton.disabled = false;
            } else {
                totalTime--;
            }
        }, 1000);

    }

    let verificationNumberButton = document.getElementById('verificationButton');

    verificationNumberButton.addEventListener('click', function () {

        let email = document.getElementById('userEmail').value;
        let certificationNumber = document.getElementById('certificationNumber').value;

        let userEmail = email + "@" + domain.value;

        if (certificationNumber === "") {
            alert("인증번호를 입력해주세요.")
            return false;
        }

        let verificationRequest = JSON.stringify({
            userEmail: userEmail,
            certificationNumber: certificationNumber
        });


        let url = '/verifyCertificationNumber';
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(verificationRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("인증 완료 되었습니다.");
                    verificationNumberButton.disabled = true;
                    clearInterval(timerInterval);
                } else if (xhr.status === 401) {
                    alert("인증번호를 다시 확인해주세요.");
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });

    signupButton.addEventListener('click', function () {

        let isFormValid = form_check();

        if (isFormValid) {
            if (!isCertificationCompleted()) {
                alert("이메일 인증을 먼저 완료해주세요.");
                return false;
            }

            if (!isDuplicationCompleted()) {
                alert("아이디 중복 확인을 해주세요.");
                return false;
            }

            let userId = document.getElementById("userId").value;
            let userPassword = document.getElementById("userPassword").value;
            let userName = document.getElementById("userName").value;
            let email = document.getElementById("userEmail").value;

            let userEmail = email + "@" + domain.value;

            let userCreateRequest = JSON.stringify({
                userId: userId,
                userPassword: userPassword,
                userName: userName,
                userEmail: userEmail
            });

            let url = "/register";
            let xhr = new XMLHttpRequest();
            xhr.open('POST', url, true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(userCreateRequest);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert("회원가입이 완료 되었습니다.");
                        window.location.href = "/login";

                    } else {
                        console.log("오류 발생");
                    }
                }
            };
        }
    });

    function isCertificationCompleted() {

        return verificationNumberButton.disabled;
    }

    function isDuplicationCompleted() {

        return duplicationButton.disabled;
    }

    function form_check() { // 회원가입 폼 유효성 검사

        let userId = document.getElementById("userId");
        let userPassword = document.getElementById("userPassword");
        let userName = document.getElementById("userName");
        let userEmail = document.getElementById("userEmail");

        let idValidText = document.createElement('span');
        let passwordValidText = document.createElement('span');
        let nameValidText = document.createElement('span');
        let emailValidText = document.createElement('span');

        function hasPreviousValidText(field) {
            let previousValidText = field.parentElement.querySelector('span');
            return previousValidText && previousValidText.parentNode === field.parentElement;
        }

        function checkField(field, validText, message) {
            if (field.value === "") {
                if (!hasPreviousValidText(field)) {
                    validText.innerText = message;
                    field.parentElement.appendChild(validText);
                    validText.style.color = "red";
                    validText.style.fontSize = "12px";
                    field.focus();
                }
                return false;
            } else {
                if (hasPreviousValidText(field)) {
                    field.parentElement.removeChild(field.parentElement.querySelector('span'));
                }
                return true;
            }
        }

        let idValid = checkField(userId, idValidText, "아이디를 입력해주세요.");
        let passwordValid = checkField(userPassword, passwordValidText, "비밀번호를 입력해주세요.");
        let nameValid = checkField(userName, nameValidText, "이름을 입력해주세요.");
        // let emailValid = checkField(userEmail, emailValidText, "이메일을 입력해주세요.");

        return idValid && passwordValid && nameValid;
    }
});
