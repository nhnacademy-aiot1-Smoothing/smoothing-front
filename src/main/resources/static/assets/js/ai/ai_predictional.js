Highcharts.chart('prediction-graph', {
    chart: {
        type: 'line',
        events: {
            load: function () {
                const chart = this;
                fetch("/ai/power-usage/prediction?measurement=power_usage&field=socket_power")
                    .then(response => response.json())
                    .then(res => {
                            const data = [];
                            for (let i = 0; i < res.length; i++) {
                                data.push([new Date(res[i].time), res[i].value]);
                            }

                            chart.series[0].setData(data, true, true, true);
                        }
                    )
                    .then(fetch("/ai/power-usage/actual?location=class_a_floor_heating&description=w")
                        .then(response => response.json())
                        .then(res => {
                                const data = [];
                                for (let i = 0; i < res.length; i++) {
                                    data.push([new Date(res[i].time), res[i].value]);
                                }

                                console.log(data);

                                chart.series[1].setData(data, true, true, true);
                            }
                        ))
            }
        }
    },
    title: {
        text: '예측량 그래프'
    },
    xAxis: {
        categories: []
    },
    yAxis: {
        title: {
            text: '예측량(Wh)'
        }
    },
    plotOptions: {
        line: {
            dataLabels: {
                enabled: true
            },
            enableMouseTracking: false
        }
    },
    series: [{
        name: 'Reggane',
        data: []
    }, {
        name: 'Tallinn',
        data: []
    }]
});
