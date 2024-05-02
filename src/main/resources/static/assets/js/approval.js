document.addEventListener('DOMContentLoaded', function() {

    let approveButtons = document.querySelectorAll(".approveButton");

    approveButtons.forEach(function (button) {
        button.addEventListener('click', function () {

            let userId = this.querySelector("input[name='userId']").value;

            let select = document.getElementById("selectRole_" + userId);
            let selectedOption = select.options[select.selectedIndex];
            let selectedRoleId = selectedOption.value;

            console.log(selectedRoleId);

            let approveRequest =  {
                userId: userId,
                roleIds: [selectedRoleId]
            };

            var jsonData = JSON.stringify(approveRequest);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/approve");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(jsonData);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        alert("승인되었습니다.");
                        location.reload();
                    }
                }
            };
        });
    });
});