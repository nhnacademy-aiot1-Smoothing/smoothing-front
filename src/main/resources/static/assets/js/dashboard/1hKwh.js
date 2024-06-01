function create1HKwhChart(tags) {
    Highcharts.chart('kwhHourContainer', {
        chart: {
            zoomType: 'xy',
            events: {
                load: function () {
                    // 차트가 로드되면 실행할 함수
                    const chart = this;  // 차트 참조
                    fetch("/sensor/kwh?unit=hour&per=1&tags=" + tags)
                        .then(response => response.json())
                        .then(res => {
                            const data = [];
                            const beforeData = [];

                            for (let i = 0, j = (res.data.length / 2); j < res.data.length; i++, j++) {
                                const d = res.data[j];
                                const beforeD = res.data[i];

                                d.value = Math.round(d.value * 10) / 10;
                                beforeD.value = Math.round(beforeD.value * 10) / 10;

                                beforeData.push([new Date(d.time).getTime(), beforeD.value]);
                                data.push([new Date(d.time).getTime(), d.value]);
                            }

                            chart.series.forEach((series, index) => {
                                const newData = index === 1 ? beforeData : data;
                                series.setData(newData, true, true, true);
                            });
                        })
                        .then(() => {
                            setInterval(() => {
                                fetch("/sensor/kwh?unit=hour&per=1&tags=" + tags)
                                    .then(response => response.json())
                                    .then(res => {
                                        const resData = res.data;

                                        if (resData.length !== 48) {
                                            return;
                                        }

                                        if (new Date(resData[resData.length / 2].time).getTime() !== chart.series[0].data[0].x) {
                                            let beforeValue = Math.round((resData[23].value * 10) / 10);
                                            const beforeData = [new Date(resData[resData.length - 1].time).getTime(), beforeValue];
                                            const data = [new Date(resData[resData.length - 1].time).getTime(), 0.0];

                                            chart.series.forEach((series, index) => {
                                                const newData = index === 1 ? beforeData : data;
                                                series.addPoint(newData, true, true);
                                            });

                                        } else {
                                            const data = [];
                                            const beforeData = [];

                                            for (let i = 0, j = (res.data.length / 2); j < res.data.length; i++, j++) {
                                                const d = res.data[j];
                                                const beforeD = res.data[i];

                                                d.value = Math.round(d.value * 10) / 10;
                                                beforeD.value = Math.round(beforeD.value * 10) / 10;

                                                beforeData.push([new Date(d.time).getTime(), beforeD.value]);
                                                data.push([new Date(d.time).getTime(), d.value]);
                                            }

                                            chart.series.forEach((series, index) => {
                                                const newData = index === 1 ? beforeData : data;
                                                series.setData(newData, true, true, true);
                                            });
                                        }

                                    })
                            }, 5000);
                        })
                }
            }
        },

        title: {
            text: null
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        plotOptions: {
            series: {
                animation: {
                    duration: 800,  // Adjusted duration
                    easing: 'easeOutQuad'  // Changed easing function
                }
            }
        },
        xAxis: [{
            type: 'datetime',
            tickPixelInterval: 150
        }],
        yAxis: [{
            title: null
        }],
        tooltip: {
            shared: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '전력량',
            type: 'column',
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0],
            tooltip: {
                valueSuffix: ' kwh'
            }

        }, {
            name: '1일전 전력량',
            type: 'spline',
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0],
            tooltip: {
                valueSuffix: ' kwh'
            }
        }]
    });
}

create1HKwhChart("");