function handleLogout() {
    fetch('/api/tokens', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({fcmToken: fcmToken})
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