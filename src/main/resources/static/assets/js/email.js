document.addEventListener('DOMContentLoaded', function () {

    let sendCertificationNumberButton = document.getElementById('sendCertificationNumber');
    let timerText = document.createElement('span');
    let timerInterval;

    sendCertificationNumberButton.addEventListener('click', function () {

        let userEmail = document.getElementById('userEmail').value;

        let requestBody = JSON.stringify({
            userEmail: userEmail
        });

        console.log(requestBody);

        var url = '/requestCertificationNumber';
        var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(requestBody);

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

    let verificationNumberButton = document.getElementById('verification');

    verificationNumberButton.addEventListener('click', function () {

        let userEmail = document.getElementById('userEmail').value;
        let certificationNumber = document.getElementById('certificationNumber').value;

        let requestBody = JSON.stringify({
            userEmail: userEmail,
            certificationNumber: certificationNumber
        });

        let url = '/verifyCertificationNumber';
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(requestBody);

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
});
