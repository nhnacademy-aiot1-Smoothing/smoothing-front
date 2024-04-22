let firebaseConfig = {
    apiKey: "AIzaSyBYjQ_3ClLSfoS7pQLJMfhJSAHbmoNlUJ0",
    authDomain: "smoothing-4e445.firebaseapp.com",
    projectId: "smoothing-4e445",
    storageBucket: "smoothing-4e445.appspot.com",
    messagingSenderId: "1008788884422",
    appId: "1:1008788884422:web:b003901891b30682cf00c7",
    measurementId: "G-WSZGQMJW1N"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
let fcmToken;

function handleToken() {
    messaging.requestPermission().then(() => {
        console.log('알림 권한이 허용되었습니다.');
        return messaging.getToken();
    }).then(token => {
        console.log('토큰:', token);
        fcmToken = token;
        sendTokenToServer(token);
    }).catch(err => {
        console.error('알림 권한을 얻을 수 없습니다.', err);
    });
}

function sendTokenToServer(token) {
    const data = JSON.stringify({ fcmToken: token });
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

document.addEventListener('DOMContentLoaded', function() {
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
    window.addEventListener('load', function() {
        navigator.serviceWorker.register('/firebase-messaging-sw.js')
            .then(function(registration) {
                console.log('Registration successful, scope is:', registration.scope);
            }).catch(function(err) {
            console.log('Service worker registration failed, error:', err);
        });
    });
}


