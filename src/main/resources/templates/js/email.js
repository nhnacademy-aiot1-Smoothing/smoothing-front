var sendCertificationNumberButton = document.getElementById('sendCertificationNumber');

sendCertificationNumberButton.addEventListener('click', function () {

    var userEmail = document.getElementById('userEmail').value;

    var xhr = new XMLHttpRequest();

    var url = 'http://localhost:8002/api/auth/email';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200)  {
                console.log(xhr.responseText);
                alert("인증번호가 발급되었습니다.");
            } else {
                console.log("오류 발생")
            }
        }
    };

    var requestBody = JSON.stringify({
        userEmail: userEmail
    });


    xhr.send(requestBody);
});

var verificationNumberButton = document.getElementById('verification');

verificationNumberButton.addEventListener('click', function () {

    var userEmail = document.getElementById('userEmail').value;
    var certificationNumber = document.getElementById('certificationNumber').value;

    var xhr = new XMLHttpRequest();

    var url = 'http://localhost:8002/api/auth/email/verify';

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("인증 완료 되었습니다.");
            } else if (xhr.status === 401) {
                alert("인증번호를 다시 확인해주세요.");
            } else {
                console.log("오류 발생");
            }
        }
    };

    var requestBody = JSON.stringify({
        userEmail: userEmail,
        certificationNumber: certificationNumber
    });

    xhr.send(requestBody);
});