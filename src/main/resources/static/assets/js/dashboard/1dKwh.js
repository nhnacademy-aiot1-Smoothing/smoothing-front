const days = {
    1: {
        code: 'MON',
        name: '월요일',
        color: '#F72464'
    },
    2: {
        code: 'TUE',
        name: '화요일',
        color: '#FFAA80'
    },
    3: {
        code: 'WED',
        name: '수요일',
        color: '#FFC700'
    },
    4: {
        code: 'THU',
        name: '목요일',
        color: '#9DDE8B'
    },
    5: {
        code: 'FRI',
        name: '금요일',
        color: '#40A578'
    },
    6: {
        code: 'SAT',
        name: '토요일',
        color: '#1C74BD'
    },
    0: {
        code: 'SUN',
        name: '일요일',
        color: '#7469B6'
    }
};

function getData(data) {
    const date = new Date(data.time);
    return {
        x: date.getTime(),
        y: data.value,
        color: days[date.getDay()].color
    };
}

function getPrevData(data) {
    return {
        x: new Date(data.time).getTime(),
        y: data.value
    };
}

function create1DKwhChart(tags) {
    const chart = Highcharts.chart('kwhDayContainer', {
        chart: {
            type: 'column',
            events: {
                load: function() {
                    fetch("/sensor/kwh?unit=day&per=1&tags=" + tags)
                        .then(response => response.json())
                        .then(res => {
                            const data = [];
                            const beforeData = [];

                            for (let i = 0, j = (res.data.length / 2); j < res.data.length; i++, j++) {
                                const d = res.data[j];
                                const beforeD = res.data[i];

                                d.value = Math.round(d.value * 10) / 10;
                                beforeD.value = Math.round(beforeD.value * 10) / 10;
                                beforeD.time = d.time;

                                data.push(getData(d));
                                beforeData.push(getPrevData(beforeD));
                            }

                            chart.series.forEach((series, index) => {
                                const newData = index === 0 ? beforeData : data;
                                series.setData(newData, true, true, true);
                            });
                        })
                        .then(() => {
                            setInterval(() => {
                                fetch("/sensor/kwh?unit=day&per=1&tags=" + tags)
                                    .then(response => response.json())
                                    .then(res => {
                                        const resData = res.data;

                                        if (resData.length !== 14) {
                                            return;
                                        }

                                        if (new Date(resData[7].time).getTime() !== chart.series[1].data[0].x) {
                                            const beforeValue = Math.round((resData[6].value * 10) / 10);
                                            const beforeData = getPrevData({ ...resData[resData.length - 1], value: beforeValue });
                                            const data = getData({ ...resData[resData.length - 1], value: 0.0 });

                                            chart.series.forEach((series, index) => {
                                                const newData = index === 0 ? beforeData: { ...data, x: beforeData.x };
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
                                                beforeD.time = d.time;

                                                data.push(getData(d));
                                                beforeData.push(getPrevData(beforeD));
                                            }

                                            chart.series.forEach((series, index) => {
                                                const newData = index === 0 ? beforeData : data;
                                                series.setData(newData, true, true, true);
                                            });
                                        }
                                    })
                            }, 5000)
                        });
                }
            }
        },
        days: days,
        title: {
            text: null,
        },
        plotOptions: {
            series: {
                grouping: false,
                borderWidth: 0
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            shared: true,
            formatter: function() {
                const pointDate = new Date(this.x);
                const dayOfWeek = pointDate.getDay();
                const dayName = days[dayOfWeek].name;

                let header = `<span style="font-size: 15px">${dayName}</span><br/>`;
                let points = this.points.map(point =>
                    `<span style="color:${point.color}">\u25CF</span> ${point.series.name}: <b>${point.y} kwh</b><br/>`
                ).join('');

                return header + points;
            }
        },
        xAxis: {
            type: 'datetime',
            labels: {
                formatter: function() {
                    return days[new Date(this.value).getDay()].code;
                }
            }
        },
        exporting: {
            enabled: false
        },
        yAxis: [{
            title: {
                text: null
            },
            showFirstLabel: false
        }],
        credits: {
            enabled: false
        },
        series: [{
            color: 'rgba(158, 159, 163, 0.5)',
            pointPlacement: -0.2,
            linkedTo: 'main',
            data: [],
            name: '1주전 전력량'
        }, {
            name: '전력량',
            id: 'main',
            dataSorting: {
                matchByName: true
            },
            dataLabels: [{
                enabled: true,
                inside: true,
                style: {
                    fontSize: '16px',
                    color: '#FFF'
                }
            }],
            data: []
        }]
    });
}

create1DKwhChart("");