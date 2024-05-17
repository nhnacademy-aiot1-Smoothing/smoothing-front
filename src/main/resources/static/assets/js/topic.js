document.addEventListener('DOMContentLoaded', function () {

    document.querySelector('#topicAddButton').addEventListener('click', function () {
        let topic = document.getElementById('topic').value;
        let topicType = document.querySelector('#add-form-select').value;

        if (topic == null || topic === ""  || topicType == null || topicType === "" || topicType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let topicAddRequest = JSON.stringify({
            topic: topic,
            topicType: topicType,
            sensorId: window.location.pathname.split('/sensor/')[1].split('/topic')[0]
        });

        fetch('/api/device/topics', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: topicAddRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('토픽이 추가되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('토픽 추가 오류:', error);
        });
    });

    let updateButtons = document.querySelectorAll('.updateButton');
    for(let i = 0; i < updateButtons.length; i++) {
        updateButtons[i].addEventListener('click', function () {
            const tds = updateButtons[i].parentElement.parentElement.querySelectorAll('td');
            document.querySelector('#updateTopic').value = tds[2].innerText;
            document.querySelector('#update_type_' + tds[1].innerText).selected = true;
            document.querySelector('#updateTopicId').innerHTML = tds[0].innerText;
        });
    }

    document.querySelector('#topicUpdateButton').addEventListener('click', function () {
        let topic = document.querySelector('#updateTopic').value;
        let topicType = document.querySelector('#update-form-select').value;
        let topicId = document.querySelector('#updateTopicId').innerHTML;

        if (topic == null || topic === "" || topicType == null || topicType === "" || topicType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let topicUpdateRequest = JSON.stringify({
            topic: topic,
            topicType: topicType
        });
        fetch('/api/device/topics/' + topicId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: topicUpdateRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('토픽이 수정되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('토픽 수정 오류:', error);
        });
    });

    let deleteButtons = document.querySelectorAll('.deleteButton');

    for(let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener('click', function () {
            let row = deleteButtons[i].closest('tr');
            let topicTds = row.querySelectorAll('td');

            const findTopicId = topicTds[0].innerText;
            const topicType = topicTds[1];
            const topic = topicTds[2];
            document.querySelector('#topicDeleteId').innerHTML = findTopicId;

            document.querySelector('#deleteModalContent').innerHTML = '토픽 ' + topic.innerText + '를 삭제하시겠습니까?<br>' +
                '<span style="color: gray;font-size: small">타입: ' + topicType.innerText + '</span>';
        });
    }

    document.querySelector('#deleteModalDeleteButton').addEventListener('click', function () {
        let topicId = document.querySelector('#topicDeleteId').innerHTML;
        fetch('/api/device/topics/' + topicId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류');
            }
            return response;
        }).then(data => {
            alert('토픽이 삭제되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('토픽 삭제 오류:', error);
        });
    });
});