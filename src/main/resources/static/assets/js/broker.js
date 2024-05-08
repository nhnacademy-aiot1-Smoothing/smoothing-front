document.addEventListener('DOMContentLoaded', function () {

    let brokerAddButton = document.getElementById('brokerAddButton');

    brokerAddButton.addEventListener('click', function () {

        let brokerIp = document.getElementById('brokerIp').value;
        let brokerPort = document.getElementById('brokerPort').value;
        let brokerName = document.getElementById('brokerName').value;

        let selectElement = document.querySelector('.form-select');
        let protocolType = selectElement.value;


        let brokerAddRequest = JSON.stringify({
            brokerIp: brokerIp,
            brokerPort: brokerPort,
            brokerName: brokerName,
            protocolType: protocolType
        });

        let url = '/add-broker'
        let xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(brokerAddRequest);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert("브로커가 추가되었습니다.");
                    window.location.reload();
                } else {
                    console.log("오류 발생")
                }
            }
        }
    });

    let updateButtons = document.querySelectorAll('.updateButton');

    updateButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            let row = button.closest('tr');
            let cells = row.querySelectorAll('td');
            document.getElementById('updateBrokerName').value = cells[1].innerText.trim();
            document.getElementById('updateProtocolType').value = cells[2].innerText.trim();
            document.getElementById('updateBrokerIp').value = cells[3].innerText.trim();
            document.getElementById('updateBrokerPort').value = cells[4].innerText.trim();
        });
    });
});