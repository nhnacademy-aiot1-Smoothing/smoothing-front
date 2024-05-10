document.addEventListener('DOMContentLoaded', function () {

    document.querySelector('#sensorAddButton').addEventListener('click', function () {
        let sensorName = document.getElementById('sensorName').value;
        let sensorType = document.querySelector('#add-form-select').value;

        if (sensorName == null || sensorName === "" || sensorType == null || sensorType === "" || sensorType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let sensorAddRequest = JSON.stringify({
            sensorName: sensorName,
            sensorType: sensorType,
            brokerId: window.location.pathname.split('/broker/')[1].split('/sensor')[0]
        });

        fetch('/api/device/sensors', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: sensorAddRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('센서가 추가되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('센서 추가 오류:', error);
        });
    });

    let updateButtons = document.querySelectorAll('.updateButton');
    for(let i = 0; i < updateButtons.length; i++) {
        updateButtons[i].addEventListener('click', function () {
            const tds = updateButtons[i].parentElement.parentElement.querySelectorAll('td');
            document.querySelector('#updateSensorName').value = tds[1].innerText;
            document.querySelector('#update_type_' + tds[2].innerText).selected = true;
            document.querySelector('#updateSensorId').innerHTML = tds[0].innerText;
        });
    }

    document.querySelector('#sensorUpdateButton').addEventListener('click', function () {
        let sensorName = document.querySelector('#updateSensorName').value;
        let sensorType = document.querySelector('#update-form-select').value;
        let sensorId = document.querySelector('#updateSensorId').innerHTML;

        if (sensorName == null || sensorName === "" || sensorType == null || sensorType === "" || sensorType === "선택") {
            alert("모든 값을 입력해주세요");
            return;
        }

        let sensorUpdateRequest = JSON.stringify({
            sensorType: sensorType,
            sensorName: sensorName
        });
        fetch('/api/device/sensors/' + sensorId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: sensorUpdateRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('센서가 수정되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('센서 수정 오류:', error);
        });
    });

    let deleteButtons = document.querySelectorAll('.deleteButton');

    for(let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener('click', function () {
            let row = deleteButtons[i].closest('tr');
            let sensorTds = row.querySelectorAll('td');

            const findSensorId = sensorTds[0].innerText;
            const sensorName = sensorTds[1];
            const sensorType = sensorTds[2];
            document.querySelector('#sensorDeleteId').innerHTML = findSensorId;

            document.querySelector('#deleteModalContent').innerHTML = '센서 ' + sensorName.innerText + '을/를 삭제하시겠습니까?<br>' +
                '<span style="color: gray;font-size: small">타입: ' + sensorType.innerText + '</span>';
        });
    }

    document.querySelector('#deleteModalDeleteButton').addEventListener('click', function () {
        let sensorId = document.querySelector('#sensorDeleteId').innerHTML;
        fetch('/api/device/sensors/' + sensorId, {
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
            alert('센서가 삭제되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('센서 삭제 오류:', error);
        });
    });

    let tagDropdownButtons = document.querySelectorAll('.tagDropdownButton');

    tagDropdownButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            let dropdownMenu = button.nextElementSibling;
            dropdownMenu.classList.toggle('show');
        });
    });

    let tagAddButton = document.getElementById('tagAddButton');

    tagAddButton.addEventListener('click', function () {

        let tagName = document.getElementById('tagName').value;

        if (tagName === "") {
            alert("태그명을 입력해주세요.");
            return;
        }

        let tagRequest = JSON.stringify({
            tagName: tagName
        });

        fetch('/addTag', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: tagRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('태그가 추가되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('태그 추가 오류:', error);
        });
    });


    let dropdownItems = document.querySelectorAll('.dropdown-menu .dropdown-item');

    dropdownItems.forEach(function (item) {
        item.addEventListener('click', function () {
            let tagId = item.getAttribute('data-tag-id');
            console.log(tagId);

            let sensorId = item.getAttribute('data-sensor-id');
            console.log(sensorId);

            let sensorTagAddRequest = JSON.stringify({
                sensorId: sensorId,
                tagId: tagId
            })

            fetch('/addSensorTag', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: sensorTagAddRequest
            }).then(response => {
                if(!response.ok) {
                    throw new Error("Server responded with an error.");
                }
            }).then(data => {
                alert("센서 태그 추가 완료");
                location.reload();
            }).catch(error => {
                console.error("오류 발생:", error);
            })
        });
    });

    // let inputSensorIds = document.querySelectorAll('.sensorId');
    // let sensorIds = [];
    //
    // inputSensorIds.forEach(function (input) {
    //     let sensorId = input.value;
    //     sensorIds.push(sensorId)
    // });
    //
    // console.log(sensorIds);
    //
    //
    // let sensorIdListRequest = JSON.stringify({
    //     sensorIds: sensorIds
    // });
    //
    // fetch('/getSensorTag', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: sensorIdListRequest
    // }).then(response => {
    //     if (!response.ok) {
    //         throw new Error('Server responded with an error.');
    //     }
    //     return response.json();
    // }).then(data => {
    //     // Object.entries(data.sensorTags).forEach(([sensorId, tags]) => {
    //     //     let tagNameDisplay = document.querySelector('.tagNameDisplay[data-sensor-id="${sensorId}"]');
    //     //     if (tagNameDisplay) {
    //     //         tagNameDisplay.innerText = tags[0].tagName;
    //     //     }
    //     // })
    //     console.log(data.sensorTags);
    // }).catch(error => {
    //     console.error('오류 발생', error);
    // });

    let tagSelect = document.getElementById("tagSelect");
    let tagUpdateName = document.getElementById("tagUpdateName");

    tagSelect.addEventListener('change', function () {
        let selectedOption = this.options[this.selectedIndex];
        tagUpdateName.value = selectedOption.textContent.trim();

        let tagId = selectedOption.value;
        let tagDeleteButton = document.getElementById('tagDeleteButton');

        tagDeleteButton.addEventListener('click', function () {
            fetch('/deleteTag/' + tagId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error.');
                }
            }).then(data => {
                alert('태그가 삭제되었습니다.');
                location.reload();
            }).catch(error => {
                console.error('태그 삭제 오류:', error);
            });
        });
    });
});