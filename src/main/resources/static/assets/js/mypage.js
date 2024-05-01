document.getElementById('attendanceButton').addEventListener('click', function() {
    this.classList.add('active');

    document.getElementById('achievementButton').classList.remove('active');
    document.getElementById('pointButton').classList.remove('active');

    fetch('/point-details')
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('achievementButton').addEventListener('click', function() {
    this.classList.add('active');

    document.getElementById('attendanceButton').classList.remove('active');
    document.getElementById('pointButton').classList.remove('active');

    fetch('/achievement')
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('pointButton').addEventListener('click', function() {
    this.classList.add('active');

    document.getElementById('achievementButton').classList.remove('active');
    document.getElementById('attendanceButton').classList.remove('active');

    fetch('/point-details')
        .then(response => response.text())
        .then(data => {
            document.getElementById('content').innerHTML = data;
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('pointBalanceLink').addEventListener('click', function() {
    document.getElementById('pointButton').click();
});