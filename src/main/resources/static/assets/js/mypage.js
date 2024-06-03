document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('attendanceButton').click();
});

document.getElementById('attendanceButton').addEventListener('click', function() {
    this.classList.add('active');

    document.getElementById('pointButton').classList.remove('active');

    var contentElement = document.getElementById('content');

    contentElement.innerHTML = '';

    var calendarContainer = document.createElement('div');
    calendarContainer.id = 'calendar';
    contentElement.appendChild(calendarContainer);

    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');
    var initialDate = yyyy + '-' + mm + '-' + dd;

    var calendar = new FullCalendar.Calendar(calendarContainer, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        initialDate: initialDate,
        navLinks: false,
        selectable: true,
        selectMirror: true,
        events: []
    });

    calendar.render();

    function fetchAttendanceDates(year, month) {
        fetch(`/attendance/list/${year}/${month}`, {
            method: 'GET'
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data){
            console.log(data.attendanceDate);

            calendar.removeAllEvents();

            data.attendanceDate.forEach(function (date) {
                calendar.addEvent({
                    title: '출석',
                    start: date,
                    color: '#02bdbd'
                });
            });
        }).catch(function (error) {
            console.error("에러 발생", error);
        });
    }

    var initialYear = today.getFullYear();
    var initialMonth = today.getMonth() + 1;
    fetchAttendanceDates(initialYear, initialMonth);

    calendar.on('datesSet', function (info) {
        var year = info.view.currentStart.getFullYear();
        var month = info.view.currentStart.getMonth() + 1;
        fetchAttendanceDates(year, month);
    });

    var checkButton = document.createElement('button');
    checkButton.innerHTML = '출석체크';
    checkButton.classList.add('check-button');

    checkButton.addEventListener('click', function () {

        fetch('/attendance', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({})
        })
            .then(function(response) {
                if (response.ok) {
                    alert('출석체크 되었습니다.');
                } else {
                    alert('이미 출석체크 되었습니다.');
                }
            }).then(function (data) {
            window.location.reload();
        })
            .catch(function(error) {
                console.error('요청 실패:', error);
                alert('요청에 문제가 발생했습니다.');
            });
    });

    calendarContainer.querySelector('.fc-header-toolbar').appendChild(checkButton);

});

document.getElementById('pointButton').addEventListener('click', function() {
    this.classList.add('active');

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