const days = {
    1: {
        code: 'MON',
        name: '월요일',
        color: '#FE2371'
    },
    2: {
        code: 'TUE',
        name: '화요일',
        color: '#544FC5'
    },
    3: {
        code: 'WED',
        name: '수요일',
        color: '#2CAFFE'
    },
    4: {
        code: 'THU',
        name: '목요일',
        color: '#FE6A35'
    },
    5: {
        code: 'FRI',
        name: '금요일',
        color: '#6B8ABC'
    },
    6: {
        code: 'SAT',
        name: '토요일',
        color: '#1C74BD'
    },
    0: {
        code: 'SUN',
        name: '일요일',
        color: '#00A6A6'
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

function create1DPowerGeneration() {
    const chart = Highcharts.chart('monitoring-graph', {
        chart: {
            type: 'column',
            events: {
                load: function () {
                    fetch("/ai/power-generation?measurement=generation&field=charge_power")
                        .then(response => response.json())
                        .then(res => {
                            const data = [];
                            res.forEach(item => {
                                const chartData = getData({ time: item.time, value: item.value });
                                data.push(chartData);
                            });

                            chart.series[0].setData(data, true, true, true);
                        })
                }
            }
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime',
            labels: {
                formatter: function () {
                    return days[new Date(this.value).getDay()].code;
                }
            }
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
            name: '발전량',
            id: 'main',
            dataSorting: {
                matchByName: true
            },
            dataLabels: [{
                enabled: false,
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

create1DPowerGeneration();