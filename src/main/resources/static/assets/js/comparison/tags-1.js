const tagCheckboxGroup = document.getElementById("tagCheckboxGroup");

// fetch('/api/device/tags')
//     .then(response => response.json())
//     .then(data => {
//         const tags = data.tags;
//
//         tags.forEach(tag => {
//             const checkbox = document.createElement("input");
//             checkbox.type = "checkbox";
//             checkbox.name = "tag";
//             checkbox.value = tag.tagName;
//
//             const label = document.createElement("label");
//             label.appendChild(document.createTextNode(tag.tagName));
//             label.appendChild(checkbox);
//
//             tagCheckboxGroup.appendChild(label);
//         });
//     })
//     .catch(error => {
//         console.error('태그 목록을 가져오는 중 오류 발생:', error);
//     });

fetch('/api/device/tags')
    .then(response => response.json())
    .then(data => {
        const tags = data.tags;

        const div = document.createElement("div");
        div.classList.add("list-container");
        div.style.maxHeight = "200px";
        div.style.overflowY = "auto";

        const ul = document.createElement("ul");
        ul.classList.add("list-group");

        tags.forEach(tag => {
            const li = document.createElement("li");
            li.classList.add("list-group-item");

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.name = "tag";
            checkbox.value = tag.tagName;
            checkbox.classList.add("form-check-input", "me-1");

            const label = document.createElement("label");
            label.classList.add("form-check-label");
            label.appendChild(checkbox);
            label.appendChild(document.createTextNode(tag.tagName));

            li.appendChild(label);
            ul.appendChild(li);
        });

        div.appendChild(ul);
        tagCheckboxGroup.appendChild(div);
    })
    .catch(error => {
        console.error('태그 목록을 가져오는 중 오류 발생:', error);
    });