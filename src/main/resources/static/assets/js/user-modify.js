
function validateForm() {
    var userName = document.getElementById('userName').value;
    var userEmail = document.getElementById('userEmail').value;
    var currentPassword = document.getElementById('currentPassword').value;

    if (!userName || !userEmail || !currentPassword) {
        alert("유저 이름, 유저 이메일, 현재 비밀번호는 필수 입력 항목입니다.");
        return false;
    }

    var newPassword = document.getElementById('newPassword').value;
    var confirmNewPassword = document.getElementById('confirmNewPassword').value;

    if (newPassword !== confirmNewPassword) {
        alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }

    return true;
}