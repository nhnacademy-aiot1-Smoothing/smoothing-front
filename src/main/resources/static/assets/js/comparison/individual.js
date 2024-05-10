fetch(`/sensor/kwh/usage/daily/period/?tags=NHN,Office&start=2024-04-16&end=2024-05-04`)
    .then(response => {
        return response.json();
    }).then(data => {
    console.log('응답 받은 데이터:', data);

    const groupedData = {};
    data.data.forEach(item => {
        item.powerMetrics.forEach(metric => {
            if (!groupedData[item.sensorName]) {
                groupedData[item.sensorName] = [];
            }
            groupedData[item.sensorName].push({
                time: metric.time.split('T')[0],
                value: metric.value
            });
        });
    });

    renderChart(groupedData);
})
    .catch(error => {
        console.error('요청 실패:', error);
    });

function renderChart(groupedData) {
    const seriesData = [];
    for (const sensorName in groupedData) {
        if (groupedData.hasOwnProperty(sensorName)) {
            seriesData.push({
                name: sensorName,
                data: groupedData[sensorName].map(item => parseFloat(item.value.toFixed(2)))
            });
        }
    }

    Highcharts.chart('comparison-individual', {
        chart: {
            type: 'spline'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: Object.values(groupedData)[0].map(item => item.time),
            accessibility: {
                description: 'Dates'
            }
        },
        yAxis: {
            title: {
                text: 'kWh'
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: seriesData
    });
}
