document.addEventListener("DOMContentLoaded", function () {

    let rows = document.querySelectorAll(".user-row");
    rows.forEach(function(row) {
        let userId = row.querySelector(".user-id").textContent;
        let roleCell = row.querySelector(".user-role");
        let deleteButton = row.querySelector(".deleteButton");
        let xhr = new XMLHttpRequest();
        xhr.open("GET", "/userRoleList?userId=" + userId);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onload = function () {
            if (xhr.status === 200) {
                let roleResponse = JSON.parse(xhr.responseText);
                if (roleResponse.length > 0) {
                    let roles = roleResponse.map(function (roleResponse) {
                        if (roleResponse.roleInfo === "ROLE_ADMIN") {
                            deleteButton.addEventListener('click', function () {
                                alert("관리자는 삭제할 수 없습니다.");
                                return false;
                            })
                            return "관리자";
                        } else if (roleResponse.roleInfo === "ROLE_USER") {
                            deleteButton.addEventListener('click', function () {
                                let xhr = new XMLHttpRequest();
                                xhr.open("DELETE", "/deleteUser/" + userId);
                                xhr.setRequestHeader("Content-Type", "application/json");

                                xhr.onreadystatechange = function () {
                                    if (xhr.readyState === XMLHttpRequest.DONE) {
                                        if (xhr.status === 200) {
                                            alert("삭제되었습니다.");
                                            location.reload();
                                        } else {
                                            console.log("오류 발생");
                                        }
                                    }
                                };
                                xhr.send();
                            });
                            return "회원";
                        } else  {
                            return roleResponse.roleInfo;
                        }
                    });
                    roleCell.textContent = roles.join(', ');
                } else {
                    roleCell.textContent = "권한 없음";
                }
            } else {
                console.error("오류 발생 " + xhr.status);
            }
        };
        xhr.send();
    });


    let roleModifyButtons = document.querySelectorAll(".roleModifyButton");

    roleModifyButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            let userId = this.querySelector("input[name='userId']").value;
            // console.log(userId);

            let checkboxes = document.querySelectorAll("input[name='roleCheckbox']");

            let checkedRoleIds = [];

            checkboxes.forEach(function (checkbox) {
                if (checkbox.checked) {
                    let roleId = checkbox.value;
                    checkedRoleIds.push(roleId);
                }
            });

            console.log(checkedRoleIds);

            let modifyRequest = {
                userId: userId,
                roleIds: checkedRoleIds
            }

            if (checkedRoleIds.length === 0) {
                alert("한 개 이상의 권한을 선택해주세요.");
            } else {
                var jsonData = JSON.stringify(modifyRequest);

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/modifyUserRole");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(jsonData);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState === XMLHttpRequest.DONE) {
                        if (xhr.status === 200) {
                            alert("변경되었습니다.");
                            location.reload();
                        }
                    }
                };
            }
        });
    });
});