document.addEventListener('DOMContentLoaded', function () {

    document.querySelector('#brokerAddButton').addEventListener('click', function () {
        let brokerIp = document.getElementById('brokerIp').value;
        let brokerPort = document.getElementById('brokerPort').value;
        let brokerName = document.getElementById('brokerName').value;
        let protocolType = document.querySelector('#add-form-select').value;

        if (brokerIp == null || brokerIp === "" || brokerPort == null || brokerPort === "" || brokerName == null || brokerName === "" || protocolType == null || protocolType === "" || protocolType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let brokerAddRequest = JSON.stringify({
            brokerIp: brokerIp,
            brokerPort: brokerPort,
            brokerName: brokerName,
            protocolType: protocolType
        });

        fetch('api/device/brokers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: brokerAddRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('브로커가 추가되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('브로커 추가에 실패하였습니다.');
            }
        });
    });

    let updateButtons = document.querySelectorAll('.updateButton');
    for(let i = 0; i < updateButtons.length; i++) {
        updateButtons[i].addEventListener('click', function () {
            const tds = updateButtons[i].parentElement.parentElement.querySelectorAll('td');
            document.querySelector('#updateBrokerName').value = tds[1].innerText;
            document.querySelector('#update_type_' + tds[2].innerText).selected = true;
            document.querySelector('#updateBrokerIp').value = tds[3].innerText;
            document.querySelector('#updateBrokerPort').value = tds[4].innerText;
            document.querySelector('#updateBrokerId').innerHTML = tds[0].innerText;
        });
    }

    document.querySelector('#brokerUpdateButton').addEventListener('click', function () {
        let brokerIp = document.querySelector('#updateBrokerIp').value;
        let brokerPort = document.querySelector('#updateBrokerPort').value;
        let brokerName = document.querySelector('#updateBrokerName').value;
        let protocolType = document.querySelector('#update-form-select').value;
        let brokerId = document.querySelector('#updateBrokerId').innerHTML;

        if (brokerIp == null || brokerIp === "" || brokerPort == null || brokerPort === "" || brokerName == null || brokerName === "" || protocolType == null || protocolType === "" || protocolType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let brokerUpdateRequest = JSON.stringify({
            brokerIp: brokerIp,
            brokerPort: brokerPort,
            brokerName: brokerName,
            protocolType: protocolType
        });
        fetch('/api/device/brokers/' + brokerId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: brokerUpdateRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('브로커가 수정되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('브로커 수정에 실패하였습니다.');
            }
        });
    });

    let deleteButtons = document.querySelectorAll('.deleteButton');

    for(let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener('click', function () {
            let row = deleteButtons[i].closest('tr');
            let brokerTds = row.querySelectorAll('td');

            const findBrokerId = brokerTds[0].innerText;
            const brokerName = brokerTds[1];
            const brokerProtocol = brokerTds[2];
            const brokerIp = brokerTds[3];
            const brokerPort = brokerTds[4];
            document.querySelector('#brokerDeleteId').innerHTML = findBrokerId;

            document.querySelector('#deleteModalContent').innerHTML = '브로커 ' + brokerName.innerText + '를 삭제하시겠습니까?<br>' +
                '<span style="color: gray;font-size: small">프로토콜: ' + brokerProtocol.innerText + ', IP주소: ' + brokerIp.innerText + ', 포트: ' + brokerPort.innerText + '</span>';
        });
    }

    document.querySelector('#deleteModalDeleteButton').addEventListener('click', function () {
        let brokerId = document.querySelector('#brokerDeleteId').innerHTML;
        fetch('/api/device/brokers/' + brokerId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('브로커가 삭제되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('브로커 삭제에 실패하였습니다.');
            }
        });
    });
});