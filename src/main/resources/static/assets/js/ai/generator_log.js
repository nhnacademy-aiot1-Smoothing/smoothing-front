let generatorIndex = 1;

function prevGenerator() {
    generatorIndex = (generatorIndex - 1 < 1) ? 3 : generatorIndex - 1;
    updateGeneratorContent();
}

function nextGenerator() {
    generatorIndex = (generatorIndex + 1 > 3) ? 1 : generatorIndex + 1;
    updateGeneratorContent();
}

function updateGeneratorContent() {
    const content = document.getElementById('generator-content');
    const label = document.getElementById('generator-label');
    content.innerHTML = ''; // 기존 내용 제거
    label.textContent = '발전기 ' + generatorIndex;

    for (let i = 0; i < 6; i++) { // 로그 항목 6개로 제한
        const logEntry = document.createElement('div');
        logEntry.classList.add('log-entry');

        const time = document.createElement('time');
        time.textContent = `2024-05-27 ${14 + i}:00`;

        const logText = document.createElement('span');
        logText.textContent = `로그 내용`;

        const status = document.createElement('span');
        status.textContent = `운영 상태`;

        logEntry.appendChild(time);
        logEntry.appendChild(logText);
        logEntry.appendChild(status);

        content.appendChild(logEntry);
    }
}

// 페이지 로드 시 초기 내용 설정
document.addEventListener('DOMContentLoaded', updateGeneratorContent);








// let generatorIndex = 1;
//
// function prevGenerator() {
//     generatorIndex = (generatorIndex - 1 < 1) ? 3 : generatorIndex - 1;
//     updateGeneratorContent();
// }
//
// function nextGenerator() {
//     generatorIndex = (generatorIndex + 1 > 3) ? 1 : generatorIndex + 1;
//     updateGeneratorContent();
// }
//
// function updateGeneratorContent() {
//     const content = document.getElementById('generator-content');
//     const label = document.getElementById('generator-label');
//     content.innerHTML = ''; // 기존 내용 제거
//     label.textContent = '발전기 ' + generatorIndex;
//
//     // fetch('/api/generator/${generatorIndex}/logs') // 엔드포인트 일치하도록 작성할 것
//     // .then(response => response.json())
//     // .then(data => {
//     //      data.logs.forEach(log => {
//     for (let i = 0; i < 6; i++) { // 로그 항목 6개로 제한
//         const logEntry = document.createElement('div');
//         logEntry.classList.add('log-entry');
//
//         const time = document.createElement('time');
//         time.textContent = `2024-05-27 ${14 + i}:00`;
//
//         const logText = document.createElement('span');
//         logText.textContent = `로그 내용`;
//
//         const status = document.createElement('span');
//         status.textContent = `운영 상태`;
//
//         logEntry.appendChild(time);
//         logEntry.appendChild(logText);
//         logEntry.appendChild(status);
//
//         content.appendChild(logEntry);
//     }
// }
// //  });
// // })
// // .catch(error => console.error('로그 생성 오류', error));
//
// // 페이지 로드 시 초기 내용 설정
// document.addEventListener('DOMContentLoaded', updateGeneratorContent);
