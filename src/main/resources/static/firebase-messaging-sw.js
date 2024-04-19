importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js');

firebase.initializeApp({
    apiKey: 'AIzaSyBYjQ_3ClLSfoS7pQLJMfhJSAHbmoNlUJ0',
    authDomain: 'smoothing-4e445.firebaseapp.com',
    projectId: 'smoothing-4e445',
    storageBucket: "smoothing-4e445.appspot.com",
    messagingSenderId: '1008788884422',
    appId: '1:1008788884422:web:b003901891b30682cf00c7',
    measurementId: "G-WSZGQMJW1N"
});

const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler(function(payload) {
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
        body: payload.notification.body,
    };

    return self.registration.showNotification(notificationTitle, notificationOptions);
});