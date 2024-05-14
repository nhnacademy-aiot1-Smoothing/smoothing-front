
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
});