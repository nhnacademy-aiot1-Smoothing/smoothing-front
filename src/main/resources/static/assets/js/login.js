function login() {
    var id = document.getElementById("loginId").value;
    var pwd = document.getElementById("loginPassword").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.result === "success") {
                alert("로그인 성공");
                location.href = "/main";
            } else {
                alert("로그인 실패");
            }
        }
    };
    xhr.send(JSON.stringify({
        id: id,
        pwd: pwd
    }));
}