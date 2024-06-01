const tagCheckboxGroup2 = document.getElementById("tagCheckboxGroup2");

fetch('/api/device/tags')
    .then(response => response.json())
    .then(data => {
        const tags = data.tags;

        tags.forEach(tag => {
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.name = "tag";
            checkbox.value = tag.tagName;

            const label = document.createElement("label");
            label.appendChild(document.createTextNode(tag.tagName));
            label.appendChild(checkbox);

            tagCheckboxGroup2.appendChild(label);
        });
    })
    .catch(error => {
        console.error('태그 목록을 가져오는 중 오류 발생:', error);
    });