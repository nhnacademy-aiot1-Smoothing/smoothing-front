fetch(`/sensor/kwh/usage/daily/period/total?tags=NHN,Office&start=2024-04-16&end=2024-05-04`)
    .then(response => {
        return response.json();
    }).then(data => {

        Highcharts.chart('comparison-sum', {
            title: {
                text: ''
            },
            chart: {
                type: 'column'
            },
            xAxis: {
                categories: data.data.map(item => item.time.split('T')[0]),
                crosshair: true,
                accessibility: {
                    description: 'Dates'
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'kWh'
                }
            },
            tooltip: {
                valueSuffix: ' kWh'
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [
                {
                    name: 'NHN Office',
                    data: data.data.map(item => item.value)
                }
            ]
        });
    })
    .catch(error => {
        console.error('요청 실패:', error);
    });
