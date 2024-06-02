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
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('센서가 추가되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('센서 추가에 실패하였습니다.');
            }
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
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('센서가 수정되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('센서 수정에 실패하였습니다.');
            }
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
                throw new Error(response.status.toString());
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
                throw new Error(response.status.toString());
            }
        }).then(data => {
            alert('태그가 추가되었습니다.');
            location.reload();
        }).catch(error => {
            if(error.message === '403') {
                alert('권한이 없습니다.');
                location.reload();
            }else {
                alert('태그 추가에 실패하였습니다.');
            }
        });
    });

    document.querySelectorAll('.input-tag').forEach(function(input){
        input.addEventListener('input', function(e){
            if(e instanceof InputEvent) {
                return;
            }
            let options = document.querySelectorAll('datalist')[0].options;
            let input = this.value;
            for(let i = 0; i < options.length; i++){
                if(options[i].value === input){
                    console.log(options[i].value);
                    console.log(document.querySelector('#tagIdName').querySelector('#tag_'+options[i].value).value);
                    console.log(this.getAttribute('data-sensor-id'));

                    fetch('/addSensorTag', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            sensorId: this.getAttribute('data-sensor-id'),
                            tagId: document.querySelector('#tagIdName').querySelector('#tag_'+options[i].value).value
                        })
                    }).then(response => {
                        if(!response.ok) {
                            throw new Error("Server responded with an error.");
                        }
                    }).then(data => {
                        location.reload();
                    }).catch(error => {
                        console.error("오류 발생:", error);
                    });
                }
            }
        });
    });

    document.querySelectorAll('.input-tag').forEach(function (item) {
        item.addEventListener('keydown', function (e) {
            if(e.key === 'Enter') {
                let tagIdNames = document.querySelectorAll('.tagIdNames');
                for(let i = 0;i<tagIdNames.length;i++) {
                    if(item.value.toUpperCase() === tagIdNames[i].id.split('_')[1].toUpperCase()) {
                        fetch('/addSensorTag', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({
                                sensorId: item.getAttribute('data-sensor-id'),
                                tagId: tagIdNames[i].value
                            })
                        }).then(response => {
                            if(!response.ok) {
                                throw new Error("Server responded with an error.");
                            }
                        }).then(data => {
                            location.reload();
                        }).catch(error => {
                            console.error("오류 발생:", error);
                        });
                        return;
                    }
                }
                let value = item.value;

                fetch('/addTag', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        tagName: value
                    })
                }).then(response => {
                    if(!response.ok) {
                        throw new Error("Server responded with an error.");
                    }
                }).then(data => {
                    fetch('/tags', {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(response => {
                        if(!response.ok) {
                            throw new Error("Server responded with an error.");
                        }
                        return response.json();
                    }).then(data => {
                        for(let i = 0; i < data.length; i++) {
                            if(data[i].tagName.toUpperCase() === value.toUpperCase()) {
                                fetch('/addSensorTag', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/json'
                                    },
                                    body: JSON.stringify({
                                        sensorId: item.getAttribute('data-sensor-id'),
                                        tagId: data[i].tagId
                                    })
                                }).then(response => {
                                    if(!response.ok) {
                                        throw new Error("Server responded with an error.");
                                    }
                                }).then(data => {
                                    location.reload();
                                }).catch(error => {
                                    console.error("오류 발생:", error);
                                });
                            }
                        }
                    }).catch(error => {
                        console.error("오류 발생:", error);
                    });
                }).catch(error => {
                    console.error("오류 발생:", error);
                });
            }
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
                // alert("센서 태그 추가 완료");
                location.reload();
            }).catch(error => {
                console.error("오류 발생:", error);
            })
        });
    });

    let tagSelect = document.getElementById("tagSelect");
    let tagUpdateName = document.getElementById("tagUpdateName");

    let selectedOption = tagSelect.options[tagSelect.selectedIndex];
    tagUpdateName.value = selectedOption.textContent.trim();

    tagSelect.addEventListener('change', function () {
        let selectedOption = this.options[this.selectedIndex];
        tagUpdateName.value = selectedOption.textContent.trim();
    });


    let tagDeleteButton = document.getElementById('tagDeleteButton');
    let tagUpdateButton = document.getElementById('tagUpdateButton');

    tagUpdateButton.addEventListener('click', function () {

        let selectedOption = tagSelect.options[tagSelect.selectedIndex];
        let tagId = selectedOption.value;
        let tagName = tagUpdateName.value;

        let tagRequest = JSON.stringify({
            tagName: tagName
        });

        fetch('/updateTag/' + tagId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: tagRequest
        }).then(response => {
            if (!response.ok) {
                throw new Error('Server responded with an error.');
            }
        }).then(data => {
            alert('태그가 수정되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('태그 수정 오류:', error);
        });

    });

    tagDeleteButton.addEventListener('click', function () {

        let selectedOption = tagSelect.options[tagSelect.selectedIndex];
        let tagId = selectedOption.value;

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

    let sensorTagDeleteButtons = document.querySelectorAll('.sensorTagDeleteButton');

    sensorTagDeleteButtons.forEach(function (button) {
        button.addEventListener('click', function () {

            let inputWrap = button.closest('.inputWrap');

            let sensorId = inputWrap.querySelector('.sensorIdInput').value;
            let tagId = inputWrap.querySelector('.tagIdInput').value;

            fetch('/deleteSensorTag/' + sensorId + '/' + tagId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (!response.ok) {
                    throw new Error('Server responded with an error.');
                }
            }).then(data => {
                location.reload();
            }).catch(error => {
                console.error("센서 태그 삭제 오류:", error);
            });

        });
    });
});