document.addEventListener('DOMContentLoaded', function() {
    let currentGeneratorIndex = 1; // 숫자 인덱스로 관리
    const maxGeneratorIndex = 3;   // 최대 발전기 숫자

    function fetchGeneratorLog(generatorIndex) {
        let generatorId = `generator_${generatorIndex}`; // ID를 문자열 형태로 조합
        fetch("/ai/power-generator/log?generatorId=" + generatorId)
            .then(response => response.json())
            .then(data => updateLogDisplay(data))
            .catch(error => console.error('Error fetching data:', error));
    }

    function updateLogDisplay(logs) {
        const logContainer = document.getElementById('generator-content');
        logContainer.innerHTML = '';

        logs.forEach(log => {
            const logEntry = document.createElement('div');
            logEntry.classList.add('log-entry');
            const formattedTime = new Date(log.time).toLocaleString('ko-KR', {
                year: 'numeric', month: '2-digit', day: '2-digit',
                hour: '2-digit', minute: '2-digit', second: '2-digit'
            });
            logEntry.innerHTML = `<span class="log-time"><strong>${formattedTime}</strong></span> <span class="log-message">${log.message}</span>`;
            logContainer.appendChild(logEntry);
        });
    }

    window.prevGenerator = function() {
        currentGeneratorIndex = (currentGeneratorIndex - 1 < 1) ? maxGeneratorIndex : currentGeneratorIndex - 1;
        fetchGeneratorLog(currentGeneratorIndex);
        document.getElementById('generator-label').textContent = `발전기 ${currentGeneratorIndex}`;
    };

    window.nextGenerator = function() {
        currentGeneratorIndex = (currentGeneratorIndex + 1 > maxGeneratorIndex) ? 1 : currentGeneratorIndex + 1;
        fetchGeneratorLog(currentGeneratorIndex);
        document.getElementById('generator-label').textContent = `발전기 ${currentGeneratorIndex}`;
    };

    fetchGeneratorLog(currentGeneratorIndex);
});