let slider = document.getElementById("goalRange");
let output = document.getElementById("kwh");
output.innerHTML = slider.value;

slider.oninput = function() {
    output.innerHTML = this.value;
}