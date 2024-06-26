function parsingTags() {
    const checkboxes = document.querySelectorAll('#tagCheckboxGroup input[type="checkbox"]:checked');
    const selectedTags = Array.from(checkboxes).map(checkbox => checkbox.value).join(",");
    return selectedTags;
}

document.getElementById('tagSelectButton').addEventListener('click', function() {
    const tags = parsingTags();

    if (tags === '') {
        document.getElementById('tagName').textContent = '전체 데이터';
    } else {
        let tagNames = tags.replaceAll(',', ', ');
        document.getElementById('tagName').textContent = tagNames;
    }

    create1DKwhChart(tags);
    create1HKwhChart(tags);
    create10MWattChart(tags);
    create1HWattChart(tags);
    createRaderChart("chartdiv2", tags);

    $('#tagSelectModal').modal('hide');
});