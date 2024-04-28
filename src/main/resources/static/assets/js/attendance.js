document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var today = new Date();
    var yyyy = today.getFullYear();
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var dd = String(today.getDate()).padStart(2, '0');
    var initialDate = yyyy + '-' + mm + '-' + dd;

    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        initialDate: initialDate,
        navLinks: false, // can click day/week names to navigate views
        selectable: true,
        selectMirror: true,
        // select: function(arg) {
        //   var title = prompt('Event Title:');
        //   if (title) {
        //     calendar.addEvent({
        //       title: title,
        //       start: arg.start,
        //       end: arg.end,
        //       allDay: arg.allDay
        //     })
        //   }
        //   calendar.unselect()
        // },
        // eventClick: function(arg) {
        //   if (confirm('Are you sure you want to delete this event?')) {
        //     arg.event.remove()
        //   }
        // },
        // editable: true,
        // dayMaxEvents: true, // allow "more" link when too many events
        events: [

        ]
    });

    calendar.render();


    var checkButton = document.createElement('button');
    checkButton.innerHTML = '출석체크';
    checkButton.classList.add('custom-button');
    checkButton.addEventListener('click', function () {
        var xhr = new XMLHttpRequest();

        var url = "http://localhost:8003/api/user/attendance";

        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');

        var userId = 'user'
        xhr.setRequestHeader('X-USER-ID', userId);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200)  {
                    console.log(xhr.responseText);
                    alert("출석 체크되었습니다.");
                } else if(xhr.status === 400) {
                    alert("이미 출석체크 되었습니다.");
                } else {
                    console.log("오류 발생");
                }
            }
        };

        xhr.send();
    });

    calendarEl.querySelector('.fc-header-toolbar').appendChild(checkButton);

});