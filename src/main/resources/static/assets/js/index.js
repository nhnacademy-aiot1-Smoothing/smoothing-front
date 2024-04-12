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

messaging.requestPermission().then(() => {
    console.log('Notification permission granted.');
    return messaging.getToken();
}).then((token) => {
    console.log('Token:', token);
}).catch((err) => {
    console.log('Unable to get permission to notify.', err);
    if (Notification.permission === 'blocked') {
        console.log('Notifications have been blocked by the user.');
    } else {
        console.log('Error getting permission:', err);
    }
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
