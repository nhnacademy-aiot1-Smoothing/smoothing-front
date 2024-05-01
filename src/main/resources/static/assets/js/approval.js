document.addEventListener('DOMContentLoaded', function() {

    var approveButton = document.getElementById("approveButton");

    approveButton.addEventListener('click', function () {
        var select = document.getElementById("selectRole");
        var selectedOption = select.options[select.selectedIndex];
        var selectedRoleId = selectedOption.value;
        var userId = document.getElementById("userId").value;

        var approveRequest =  {
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