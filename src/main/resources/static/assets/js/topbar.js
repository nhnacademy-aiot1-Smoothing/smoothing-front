function handleLogout() {
    fetch('/api/tokens', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json' // 요청 본문의 데이터 타입 지정
        },
        body: JSON.stringify({fcmToken: fcmToken}) // JSON 데이터를 문자열로 변환하여 본문에 추가
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '#';
            } else {
                throw new Error('Failed to logout.');
            }
        })
        .catch(error => console.error('Error during logout:', error));
}


document.addEventListener('DOMContentLoaded', function () {
    const logoutButton = document.getElementById('logoutButton');
    logoutButton.addEventListener('click', handleLogout);
});