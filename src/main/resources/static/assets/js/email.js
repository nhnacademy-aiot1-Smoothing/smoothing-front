document.addEventListener('DOMContentLoaded', function () {

    let sendCertificationNumberButton = document.getElementById('sendCertificationNumber');

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
                    console.log(xhr.responseText);
                    alert("인증번호가 발급되었습니다.");
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });

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
                } else if (xhr.status === 401) {
                    alert("인증번호를 다시 확인해주세요.");
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });
});
