const tagCheckboxGroup = document.getElementById("tagCheckboxGroup");

fetch('/api/device/tags')
    .then(response => response.json())
    .then(data => {
        const tags = data.tags;
        console.log(tags)
        tags.forEach(tag => {
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.name = "tag";
            checkbox.value = tag.tagName;

            const label = document.createElement("label");
            label.appendChild(document.createTextNode(tag.tagName));
            label.appendChild(checkbox);

            tagCheckboxGroup.appendChild(label);
        });
    })
    .catch(error => {
        console.error('태그 목록을 가져오는 중 오류 발생:', error);
    });

document.getElementById("tagSelectButton").addEventListener("click", function() {
    const selectedTags = Array.from(document.querySelectorAll("input[name=tag]:checked")).map(checkbox => checkbox.value);
});
