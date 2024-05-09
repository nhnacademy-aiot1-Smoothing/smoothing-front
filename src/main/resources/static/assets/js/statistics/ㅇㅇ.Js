$.get("/sensor/kwh/usage/weekly/value/total", { tags: "" }, function(response) {
    var chartData = response.data.map(function(item, index) {
        return {
            name: item.sensorName,
            value: item.value,
            colorValue: index + 1
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
            text: 'Highcharts Treemap'
        }
    });
})
    .catch(error => {
        console.error('Error fetching data:', error);
    });
