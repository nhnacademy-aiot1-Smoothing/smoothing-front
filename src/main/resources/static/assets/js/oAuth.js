const githubLoginButton = document.getElementById("github-login");

if (githubLoginButton) {
    githubLoginButton.addEventListener("click", function (e) {
        fetch("/oauth?providerName=github", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            if (response.ok) {
                alert("Github 연동이 해제되었습니다.");
                location.reload();
            }
        });
    });
}

const kakaoLoginButton = document.getElementById("kakao-login");

if (kakaoLoginButton) {
    kakaoLoginButton.addEventListener("click", function (e) {
        fetch("/oauth?providerName=kakao", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            if (response.ok) {
                alert("카카오 연동이 해제되었습니다.");
                location.reload();
            }
        });
    });
}