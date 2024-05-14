document.addEventListener('DOMContentLoaded', function() {
    const gaugeOptions = {
        chart: {
            type: 'solidgauge'
        },
        title: null,
        credits: {
            enabled: false
        },
        pane: {
            center: ['50%', '50%'],
            size: '50%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor:
                    Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },
        exporting: {
            enabled: false
        },
        tooltip: {
            enabled: false
        },
        yAxis: {
            stops: [
                [0.1, '#55BF3B'], // green
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#DF5353'] // red
            ],
            lineWidth: 0,
            tickWidth: 0,
            minorTickInterval: null,
            tickAmount: 2,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },
        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        }
    };

    window.first_top = Highcharts.chart('three-test1', Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 500,
            title: {
                text: 'Class-A LL'
            }
        },
        series: [{
            name: '전압',
            data: [0],
            dataLabels: {
                format:
                    '<div style="text-align:center">' +
                    '<span style="font-size:25px">{y:.1f}</span>V<br/>' +
                    // '<span style="font-size:12px;opacity:0.4">V</span>' +
                    '</div>'
            },
            tooltip: {
                valueSuffix: 'V'
            }
        }]
    }));

    window.first_bottom = Highcharts.chart('three-test2', Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 320,
            title: {
                text: 'Class-A LN'
            }
        },
        series: [{
            name: '전압',
            data: [0],
            dataLabels: {
                format:
                    '<div style="text-align:center">' +
                    '<span style="font-size:25px">{y:.1f}</span>V<br/>' +
                    // '<span style="font-size:15px;opacity:0.4">V</span>' +
                    '</div>'
            },
            tooltip: {
                valueSuffix: 'V'
            }
        }]
    }));

    window.second_top = Highcharts.chart('ThreePhase-B-LN', Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 500,
            title: {
                text: 'Office LL'
            }
        },
        series: [{
            name: '전압',
            data: [0],
            dataLabels: {
                format:
                    '<div style="text-align:center">' +
                    '<span style="font-size:25px">{y:.1f}</span>V<br/>' +
                    // '<span style="font-size:12px;opacity:0.4">V</span>' +
                    '</div>'
            },
            tooltip: {
                valueSuffix: 'V'
            }
        }]
    }));

    window.second_bottom = Highcharts.chart('ThreePhase-B-LL', Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: 0,
            max: 320,
            title: {
                text: 'Office LN'
            }
        },
        series: [{
            name: '전압',
            data: [0],
            dataLabels: {
                format:
                    '<div style="text-align:center">' +
                    '<span style="font-size:25px">{y:.1f}</span>V<br/>' +
                    // '<span style="font-size:15px;opacity:0.4">V</span>' +
                    '</div>'
            },
            tooltip: {
                valueSuffix: 'V'
            }
        }]
    }));


    fetchData();
    setInterval(fetchData, 1000);
});

async function fetchData() {
    try {
        const response = await fetch("/sensor/three-phase");
        const data = await response.json();

        const classLL = parseFloat(data.classA.top.value);
        const classLN = parseFloat(data.classA.bottom.value);

        const officeLL = parseFloat(data.office.top.value);
        const officeLN = parseFloat(data.office.bottom.value);

        if (window.first_top) {
            let pointTop = window.first_top.series[0].points[0];
            pointTop.update(classLL);
        }
        if (window.first_bottom) {
            let pointBtm = window.first_bottom.series[0].points[0];
            pointBtm.update(classLN);
        }
        if (window.second_top) {
            let pointTop = window.second_top.series[0].points[0];
            pointTop.update(officeLL);
        }
        if (window.second_bottom) {
            let pointBtm = window.second_bottom.series[0].points[0];
            pointBtm.update(officeLN);
        }

    } catch (error) {
        console.error("ERROR: ", error);
    }
}