document.addEventListener('DOMContentLoaded', function () {

    let verifyPasswordButton = document.getElementById('verifyPasswordButton');

    verifyPasswordButton.addEventListener('click', function () {

        let userPassword = document.getElementById('userPassword').value;

        let userPasswordRequest = JSON.stringify({
            userPassword: userPassword
        });

        console.log(userPassword);

        let url = '/verify-pwd';
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(userPasswordRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    window.location.href = "/user-modify";
                } else if (xhr.status === 401) {
                    let errorMessage = document.getElementById("errorMessage");
                    errorMessage.textContent = "비밀번호가 일치하지 않습니다.";
                } else {
                    console.log("오류 발생");
                }
            }
        };
    });
});