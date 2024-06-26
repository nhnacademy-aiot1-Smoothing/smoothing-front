function create10MWattChart(tags) {
    Highcharts.chart('container1', {
        chart: {
            type: 'spline',
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    const series = this.series[0];
                    fetch("/sensor/watt?unit=min&per=10&type=watt&tags=" +tags)
                        .then(response => response.json())
                        .then(res => {
                            for (const d of res.data) {
                                series.addPoint([new Date(d.time).getTime(), d.value], true, true);
                            }
                        })
                        .then(
                            setInterval(() =>
                                    fetch("/sensor/watt?unit=min&per=10&type=watt&tags=" + tags)
                                        .then(response => response.json())
                                        .then(res => {
                                            const resData = res.data;

                                            if (resData.length !== 12) {
                                                return;
                                            }

                                            if (new Date(resData[0].time).getTime() !== series.data[0].x) {
                                                series.addPoint([new Date(resData[resData.length - 1].time).getTime(), 0], true, true, true);
                                            } else {
                                                const data = [];

                                                for (const d of resData) {
                                                    data.push(d.value);
                                                }

                                                series.setData(data, true, true, true);
                                            }
                                        })
                                , 5000)
                        )
                }
            },
            zoomType: 'x'
        },
        title: {
            visible: false,
            text: null
        },
        legend: {
            enabled: false
        },
        time: {
            useUTC: true
        },
        plotOptions: {
            series: {
                color: '#FFC700'
            },
            spline: {
                marker: {
                    radius: 0
                }
            }
        },
        exporting: {
            enabled: false
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        credits: {
            enabled: false
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: '전력(W)',
            lineWidth: 3,
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        }],
    });
}

function create1HWattChart(tags) {
    Highcharts.chart('container2', {
        chart: {
            type: 'spline',
            events: {
                load: function () {

                    // set up the updating of the chart each second
                    const series = this.series[0];
                    fetch("/sensor/watt?unit=hour&per=1&type=watt&tags=" + tags)
                        .then(response => response.json())
                        .then(res => {
                            for (const d of res.data) {
                                series.addPoint([new Date(d.time).getTime(), d.value], true, true);
                            }
                        })
                        .then(
                            setInterval(() =>
                                    fetch("/sensor/watt?unit=hour&per=1&type=watt&tags=" + tags)
                                        .then(response => response.json())
                                        .then(res => {
                                            const resData = res.data;

                                            if (resData.length !== 24) {
                                                return;
                                            }

                                            if (new Date(resData[0].time).getTime() !== series.data[0].x) {
                                                series.addPoint([new Date(resData[resData.length - 1].time).getTime(), 0], true, true, true);
                                            } else {
                                                const data = [];

                                                for (const d of resData) {
                                                    data.push(d.value);
                                                }

                                                series.setData(data, true, true, true);
                                            }
                                        })
                                , 5000)
                        )
                }
            },
            zoomType: 'x'
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
                color: '#FFC700'
            },
            spline: {
                marker: {
                    radius: 0
                }
            }
        },
        time: {
            useUTC: true
        },
        xAxis: {
            type: 'datetime',
            tickPixelInterval: 150
        },
        credits: {
            enabled: false
        },
        yAxis: {
            title: {
                text: null
            }
        },
        legend: {
            enabled: false
        },
        series: [{
            name: '전력(W)',
            lineWidth: 3,
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0,]
        }],
    });
}

create10MWattChart("");
create1HWattChart("");
