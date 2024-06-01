const inputs = document.querySelectorAll(".input");


function addcl(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function remcl(){
    let parent = this.parentNode.parentNode;
    if(this.value == ""){
        parent.classList.remove("focus");
    }
}


inputs.forEach(input => {
    input.addEventListener("focus", addcl);
    input.addEventListener("blur", remcl);
});

//Source :- https://github.com/sefyudem/Responsive-Login-Form/blob/master/img/avatar.svg
document.addEventListener('DOMContentLoaded', function () {

    let loginButton = document.getElementById('loginButton');

    loginButton.addEventListener('click', function () {

        if (form_check()) {
            return;
        }

        if (!form_check()) {
            event.preventDefault();
        }

    });

    function form_check() {

        let userId = document.getElementById('userId');
        let userPassword = document.getElementById('userPassword');

        let idValidText = document.createElement('span');
        let passwordValidText = document.createElement('span');

        function hasPreviousValidText(field) {
            let previousValidText = field.parentElement.querySelector('span');
            return previousValidText && previousValidText.parentNode === field.parentElement;
        }

        function checkField(field, validText, message) {
            if (field.value === "") {
                if (!hasPreviousValidText(field)) {
                    validText.innerText = message;
                    field.parentElement.appendChild(validText);
                    validText.style.color = "red";
                    validText.style.fontSize = "14px";
                    field.focus();
                }
                return false;
            } else {
                if (hasPreviousValidText(field)) {
                    field.parentElement.removeChild(field.parentElement.querySelector('span'));
                }
                return true;
            }
        }
        let idValid = checkField(userId, idValidText, "아이디를 입력해주세요.");
        let passwordValid = checkField(userPassword, passwordValidText, "비밀번호를 입력해주세요.");

        return idValid && passwordValid;
    }

    const errorMessage = document.getElementById('errorMessage').textContent.trim();
    if (errorMessage) {
        alert(errorMessage);
    }
});