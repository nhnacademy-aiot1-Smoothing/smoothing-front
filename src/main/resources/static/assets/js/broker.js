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

    let deleteButtons = document.querySelectorAll('.deleteButton');

    deleteButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            let row = button.closest('tr');
            let brokerTds = row.querySelectorAll('td');

            const findBrokerId = brokerTds[0].innerText;
            const brokerName = brokerTds[1];
            const brokerProtocol = brokerTds[2];
            const brokerIp = brokerTds[3];
            const brokerPort = brokerTds[4];

            document.querySelector('#deleteModalContent').innerHTML = '브로커 ' + brokerName.innerText + '를 삭제하시겠습니까?<br>' +
                '<span style="color: gray;font-size: small">프로토콜: ' + brokerProtocol.innerText + ', IP주소: ' + brokerIp.innerText + ', 포트: ' + brokerPort.innerText + '</span>';

            document.querySelector('#deleteModalDeleteButton').addEventListener('click', function () {
                alert('삭제되었습니다.');
            });
        });
    });

});