importScripts("https://www.gstatic.com/firebasejs/10.8.0/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging-compat.js");

self.addEventListener("install", function (e) {
    self.skipWaiting();
});

self.addEventListener("activate", function (e) {
    console.log("fcm service worker가 실행되었습니다.");
});

const firebaseConfig = {
    apiKey: "AIzaSyASZ2bBr3yDAkbR5JmUtRskDU8anEFxhFI",
    authDomain: "smoothing-test2.firebaseapp.com",
    projectId: "smoothing-test2",
    storageBucket: "smoothing-test2.appspot.com",
    messagingSenderId: "265747573760",
    appId: "1:265747573760:web:a9bb536c87896e3c29fce5"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
    const notificationTitle = payload.title;
    const notificationOptions = {
        body: payload.body
    };
    self.registration.showNotification(notificationTitle, notificationOptions);
});