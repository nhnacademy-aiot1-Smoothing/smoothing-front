$.get("/sensor/kwh/usage/weekly/value/total", { tags: "" }, function(response) {
    var sortedData = response.data.sort(function(a, b) {
        return b.value - a.value;
    });

    var top7Data = sortedData.slice(0, 7);

    var colorValueRange = top7Data.map(function(item, index) {
        return index + 1;
    }).reverse();

    var chartData = top7Data.map(function(item, index) {
        return {
            name: item.sensorName,
            value: item.value,
            colorValue: colorValueRange[index]
        };
    });

    Highcharts.chart('statistics-sensor', {
        colorAxis: {
            minColor: '#FFFFFF',
            maxColor: Highcharts.getOptions().colors[0]
        },
        series: [{
            type: 'treemap',
            layoutAlgorithm: 'squarified',
            clip: false,
            data: chartData
        }],
        title: {
            text: null
        },
        credits: {
            enabled: false
        }
    });
})
    .catch(error => {
        console.error('Error fetching data:', error);
    });
