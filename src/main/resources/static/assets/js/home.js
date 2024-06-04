let firebaseConfig = {
        apiKey: "AIzaSyASZ2bBr3yDAkbR5JmUtRskDU8anEFxhFI",
        authDomain: "smoothing-test2.firebaseapp.com",
        projectId: "smoothing-test2",
        storageBucket: "smoothing-test2.appspot.com",
        messagingSenderId: "265747573760",
        appId: "1:265747573760:web:a9bb536c87896e3c29fce5"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
let fcmToken;

function handleToken() {
    messaging.requestPermission().then(() => {
        return messaging.getToken();
    }).then(token => {
        fcmToken = token;
        sendTokenToServer(token);
    }).catch(err => {
        console.error('알림 권한을 얻을 수 없습니다.', err);
    });
}

function sendTokenToServer(token) {
    const data = JSON.stringify({fcmToken: token});
    fetch('/api/tokens', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: data
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        })
        .catch(error => console.error('서버로부터 응답 오류:', error));
}

document.addEventListener('DOMContentLoaded', function () {
    handleToken();
});

messaging.onMessage((payload) => {
    console.log('Message received. ', payload);
    let notificationTitle = payload.notification.title;
    let notificationOptions = {
        body: payload.notification.body,
    };

    let notification = new Notification(notificationTitle, notificationOptions);
});

if ('serviceWorker' in navigator) {
    window.addEventListener('load', function () {
        navigator.serviceWorker.register('/firebase-messaging-sw.js')
            .then(function (registration) {
            }).catch(function (err) {
            console.log('Service worker registration failed, error:', err);
        });
    });
}
document.getElementById('leftButton').addEventListener('click', function() {
    let container1 = document.getElementById('container1');
    let container2 = document.getElementById('container2');

    container1.style.display = 'block';
    container2.style.display = 'block';
    container1.style.transform = 'translateX(0)';
    container2.style.transform = 'translateX(100%)';

    setTimeout(() => {
        container1.style.transform = 'translateX(0)';
        container2.style.transform = 'translateX(100%)';
    }, 10);

    setTimeout(() => {
        container2.style.display = 'none';
        document.getElementById('container1-title').style.display = 'block';
        document.getElementById('container2-title').style.display = 'none';
    }, 500);
});

document.getElementById('rightButton').addEventListener('click', function() {
    let container1 = document.getElementById('container1');
    let container2 = document.getElementById('container2');

    container1.style.display = 'block';
    container2.style.display = 'block';
    container1.style.transform = 'translateX(0)';
    container2.style.transform = 'translateX(100%)';

    setTimeout(() => {
        container1.style.transform = 'translateX(-100%)';
        container2.style.transform = 'translateX(0)';
    }, 10);

    setTimeout(() => {
        container1.style.display = 'none';
        document.getElementById('container1-title').style.display = 'none';
        document.getElementById('container2-title').style.display = 'block';
    }, 500);
});

document.querySelector("#goalButton").addEventListener("click", function() {
    let value = document.querySelector("#goalRange").value;
    fetch("api/sensor/goal",{
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            goalAmount: value
        })
    }).then(response => {
        if(response.ok){
            alert("목표량이 설정되었습니다.");
            location.reload();
        }else {
            alert("목표량 설정에 실패했습니다.");
            location.reload();
        }
    }).catch(error => {
        console.error('서버로부터 응답 오류:', error);
    })
});