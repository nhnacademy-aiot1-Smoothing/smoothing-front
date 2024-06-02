
function validateForm() {
    let userName = document.getElementById('userName').value;
    // let userEmail = document.getElementById('userEmail').value;
    // var currentPassword = document.getElementById('currentPassword').value;

    // if (!userName || !userEmail) {
    //     alert("유저 이름, 유저 이메일, 현재 비밀번호는 필수 입력 항목입니다.");
    //     return false;
    // }




    return true;
}

document.addEventListener('DOMContentLoaded', function () {

    let hookCreateButton = document.querySelector('.hookCreateButton');
    let hookModifyButton = document.querySelector('.hookModifyButton');
    let hookDeleteButton = document.querySelector('.hookDeleteButton');

    if (hookCreateButton) {
        hookCreateButton.addEventListener('click', function () {

            let selectedMessengerType = document.querySelector('input[name="messengerType"]:checked');

            if (!selectedMessengerType) {
                alert("메신저 타입을 선택해주세요.");
                return;
            }

            let hookTypeId = selectedMessengerType.value;
            let hookUrlInput = document.getElementById('hookUrl');
            let hookUrl = hookUrlInput.value;

            if (!hookUrl) {
                alert("URL 주소를 입력해주세요.");
                hookUrlInput.focus();
                return;
            }

            let hookCreateRequest = JSON.stringify({
                hookTypeId: hookTypeId,
                hookUrl: hookUrl
            });
            fetch('/createHook',{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: hookCreateRequest
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error.');
                }
            }).then(data => {
                alert('설정되었습니다.');
                location.reload();
            }).catch(error => {
                console.error('훅 생성 오류:', error);
            });
        });
    }

    if (hookModifyButton) {
        hookModifyButton.addEventListener('click', function () {

            let selectedMessengerType = document.querySelector('input[name="messengerType"]:checked');

            if (!selectedMessengerType) {
                alert("메신저 타입을 선택해주세요.");
                return;
            }

            let hookTypeId = selectedMessengerType.value;
            let hookUrlInput = document.getElementById('hookUrl');
            let hookUrl = hookUrlInput.value;

            if (!hookUrl) {
                alert("URL 주소를 입력해주세요.");
                hookUrlInput.focus();
                return;
            }

            let hookModifyRequest = JSON.stringify({
                hookTypeId: hookTypeId,
                hookUrl: hookUrl
            });
            fetch('/modifyHook',{
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: hookModifyRequest
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error.');
                }
            }).then(data => {
                alert('수정되었습니다.');
                location.reload();
            }).catch(error => {
                console.error('훅 수정 오류:', error);
            });
        });
    }

    if (hookDeleteButton) {
        hookDeleteButton.addEventListener('click', function () {

            fetch('deleteHook', {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error.');
                }
            }).then(data => {
                alert('삭제되었습니다.');
                location.reload();
            }).catch(error => {
                console.error('훅 삭제 오류:', error);
            });
        });
    }

    const emailModifyButton = document.getElementById('emailModifyButton');
    const emailEditSection = document.querySelector('.email-edit-section');

    emailModifyButton.addEventListener('click', function() {
        const isDisplayed = emailEditSection.style.display !== 'none';
        emailEditSection.style.display = isDisplayed ? 'none' : 'block';

        if (sendCertificationNumberButton.disabled) {
            sendCertificationNumberButton.disabled = false;
        }

        if (verificationNumberButton.disabled) {
            verificationNumberButton.disabled = false;
        }

        let certificationNumberInput = document.getElementById('certificationNumber');
        let certificationNumber = certificationNumberInput.value;

        if(certificationNumber.trim() !== "") {

            certificationNumberInput.value = "";
        }

        emailModifyButton.textContent = isDisplayed ? "변경" : "취소";
    });

    let sendCertificationNumberButton = document.getElementById('sendCertificationNumberButton');
    let timerText = document.createElement('span');
    let timerInterval;

    let domainSelect = document.getElementById('domainSelect');
    let domain = document.getElementById('emailDomain');

    // let selectedOption = domainSelect.options[domainSelect.selectedIndex];

    domainSelect.addEventListener('change', function () {
        let selectedOption = this.options[this.selectedIndex];
        domain.value = selectedOption.value;
    });

    sendCertificationNumberButton.addEventListener('click', function () {

        let email_rule =  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

        let email = document.getElementById('email').value;

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

        var url = '/requestCertificationNumber';
        var xhr = new XMLHttpRequest();
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
        // sendCertificationNumberButton.parentElement.appendChild(timerText);

        // 타이머를 포함하는 div 요소 선택
        let timerContainer = document.querySelector('.timer-container');

        // 타이머를 포함하는 div 요소에 타이머 텍스트 추가
        timerContainer.appendChild(timerText);

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

        let email = document.getElementById('email').value;
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
                    timerText.innerText = '';
                    emailEditSection.style.display = 'none';
                    emailModifyButton.textContent = "변경";

                    // let currentEmail = document.getElementById('userEmail');
                    // currentEmail.value = userEmail;

                    let userEmailModifyRequest = JSON.stringify({
                        userEmail: userEmail
                    });

                    let url = '/modify-email'
                    let xhr = new XMLHttpRequest();
                    xhr.open('PUT', url, true);
                    xhr.setRequestHeader('Content-Type', 'application/json');
                    xhr.send(userEmailModifyRequest);

                    xhr.onreadystatechange = function () {
                        if (xhr.readyState === XMLHttpRequest.DONE) {
                            if (xhr.status === 200) {

                                location.reload();
                            } else {
                                console.log("이메일 변경 중 오류가 발생했습니다.");
                            }
                        }
                    }


                } else if (xhr.status === 401) {
                    alert("인증번호를 다시 확인해주세요.");
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });

    let newPasswordInput = document.getElementById("newPassword");

    newPasswordInput.addEventListener("input", function() {
        let errorMessage = document.getElementById("pwdErrorMessage");
        if (newPasswordInput.value.trim() !== "") {
            errorMessage.textContent = "";
        }
    });

    let confirmNewPasswordInput = document.getElementById("confirmNewPassword");
    let passwordMatchMessage = document.getElementById("pwdConfirmMessage");

    newPasswordInput.addEventListener("input", function() {
        checkPasswordMatch();
    });

    confirmNewPasswordInput.addEventListener("input", function() {
        checkPasswordMatch();
    });

    function checkPasswordMatch() {
        let newPassword = newPasswordInput.value;
        let confirmNewPassword = confirmNewPasswordInput.value;

        if (newPassword === "" && confirmNewPassword === "") {
            passwordMatchMessage.textContent = "";
        } else if (newPassword === confirmNewPassword) {
            passwordMatchMessage.textContent = "비밀번호가 일치합니다.";
            passwordMatchMessage.style.color = "green";
        } else {
            passwordMatchMessage.textContent = "비밀번호가 일치하지 않습니다.";
            passwordMatchMessage.style.color = "red";
        }
    }

    let modifyPwdButton = document.getElementById('modifyPwdButton');

    modifyPwdButton.addEventListener('click', function () {

        let newPassword = document.getElementById('newPassword').value;
        let confirmNewPassword = document.getElementById('confirmNewPassword').value;

        if (newPassword !== confirmNewPassword) {
            return false;
        }

        if (!newPassword) {
            let errorMessage = document.getElementById("pwdErrorMessage");
            errorMessage.textContent = "새 비밀번호를 입력해주세요.";
            return false;
        }

        let modifyPwdRequest = JSON.stringify({
            userPassword: newPassword
        });

        let url = '/modify-pwd';
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(modifyPwdRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("변경되었습니다.");
                    location.reload();
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });

    let modifyNameButton = document.getElementById('modifyNameButton');

    modifyNameButton.addEventListener('click', function () {

        let userName = document.getElementById('userName').value;

        if (!userName) {
            let errorMessage = document.getElementById("errorMessage");
            errorMessage.textContent = "이름을 입력해주세요.";

            return false;
        }

        let userNameModifyRequest = JSON.stringify({
            userName: userName
        });

        let url = '/modify-name';
        let xhr = new XMLHttpRequest();
        xhr.open('PUT', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(userNameModifyRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("변경되었습니다.");
                    location.reload();
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });
});