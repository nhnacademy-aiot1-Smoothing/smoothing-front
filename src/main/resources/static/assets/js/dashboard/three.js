
let currentIndex = 0;
let data = [];
let llchart;
let lnchart;

function fetchDataAndCreateChart() {
    fetch('/sensor/three-phase')
        .then(response => response.json())
        .then(fetchedData => {
            data = fetchedData.threePhases;
            createCharts();
        })
        .catch(error => console.error('Error fetching data:', error));
}

function update(chart1, chart2) {
    fetch('/sensor/three-phase')
        .then(response => response.json())
        .then(fetchedData => {
            data = fetchedData.threePhases;
            chart1.series[0].points[0].update(data[currentIndex].top.value);
            chart2.series[0].points[0].update(data[currentIndex].bottom.value);
        })
        .catch(error => console.error('Error fetching data:', error));
}

function createCharts() {
    if (data.length === 0) return;

    const currentData = data[currentIndex];
    const topValue = currentData.top.value;
    const bottomValue = currentData.bottom.value;

    const maxValueChart2 = Math.max(topValue, bottomValue, 200) * 1.1;
    const maxValueChart1 = maxValueChart2 * 2;

    document.getElementById('locationTitle').innerText = `장소별 전압 (${currentData.place})`;

    llchart = Highcharts.chart('chart1', {
        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false,
            height: '305px',
            marginLeft: 50,
            marginRight: 50
        },
        title: {
            text: ''
        },
        credits: {
            enabled: false
        },
        pane: {
            startAngle: -90,
            endAngle: 89.9,
            background: null,
            center: ['50%', '75%'],
            size: '110%'
        },
        yAxis: {
            min: 0,
            max: maxValueChart1,
            tickPixelInterval: 72,
            tickPosition: 'inside',
            tickColor: Highcharts.defaultOptions.chart.backgroundColor || '#FFFFFF',
            tickLength: 20,
            tickWidth: 2,
            minorTickInterval: null,
            labels: {
                distance: 20,
                style: {
                    fontSize: '14px'
                }
            },
            lineWidth: 0,
            plotBands: [{
                from: 0,
                to: maxValueChart1 * 0.2,
                color: '#F72464',
                thickness: 20,
                borderRadius: '50%'
            }, {
                from: maxValueChart1 * 0.8,
                to: maxValueChart1,
                color: '#F72464',
                thickness: 20,
                borderRadius: '50%'
            }, {
                from: maxValueChart1 * 0.15,
                to: maxValueChart1 * 0.4,
                color: '#FFC700',
                thickness: 20
            }, {
                from: maxValueChart1 * 0.4,
                to: maxValueChart1 * 0.6,
                color: '#40A578',
                thickness: 20
            }, {
                from: maxValueChart1 * 0.6,
                to: maxValueChart1 * 0.85,
                color: '#FFC700',
                thickness: 20,
            }]
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'LL',
            data: [topValue],
            tooltip: {
                valueSuffix: ' V'
            },
            dataLabels: {
                format: 'LL',
                borderWidth: 0,
                color: (
                    Highcharts.defaultOptions.title &&
                    Highcharts.defaultOptions.title.style &&
                    Highcharts.defaultOptions.title.style.color
                ) || '#333333',
                style: {
                    fontSize: '16px'
                }
            },
            dial: {
                radius: '80%',
                backgroundColor: 'gray',
                baseWidth: 12,
                baseLength: '0%',
                rearLength: '0%'
            },
            pivot: {
                backgroundColor: 'gray',
                radius: 6
            }
        }]
    });

    lnchart = Highcharts.chart('chart2', {
        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false,
            height: '305px',
            marginLeft: 50,
            marginRight: 50
        },
        title: {
            text: ''
        },
        credits: {
            enabled: false
        },
        pane: {
            startAngle: -90,
            endAngle: 89.9,
            background: null,
            center: ['50%', '75%'],
            size: '110%'
        },
        yAxis: {
            min: 0,
            max: maxValueChart2,
            tickPixelInterval: 72,
            tickPosition: 'inside',
            tickColor: Highcharts.defaultOptions.chart.backgroundColor || '#FFFFFF',
            tickLength: 20,
            tickWidth: 2,
            minorTickInterval: null,
            labels: {
                distance: 20,
                style: {
                    fontSize: '14px'
                }
            },
            lineWidth: 0,
            plotBands: [{
                from: 0,
                to: maxValueChart2 * 0.2,
                color: '#F72464',
                thickness: 20,
                borderRadius: '50%'
            }, {
                from: maxValueChart2 * 0.8,
                to: maxValueChart2,
                color: '#F72464',
                thickness: 20,
                borderRadius: '50%'
            }, {
                from: maxValueChart2 * 0.15,
                to: maxValueChart2 * 0.4,
                color: '#FFC700',
                thickness: 20
            }, {
                from: maxValueChart2 * 0.4,
                to: maxValueChart2 * 0.6,
                color: '#40A578',
                thickness: 20
            }, {
                from: maxValueChart2 * 0.6,
                to: maxValueChart2 * 0.85,
                color: '#FFC700',
                thickness: 20,
            }]
        },
        exporting: {
            enabled: false
        },
        series: [{
            name: 'LN',
            data: [bottomValue],
            tooltip: {
                valueSuffix: ' V'
            },
            dataLabels: {
                format: 'LN',
                borderWidth: 0,
                color: (
                    Highcharts.defaultOptions.title &&
                    Highcharts.defaultOptions.title.style &&
                    Highcharts.defaultOptions.title.style.color
                ) || '#333333',
                style: {
                    fontSize: '16px'
                }
            },
            dial: {
                radius: '80%',
                backgroundColor: 'gray',
                baseWidth: 12,
                baseLength: '0%',
                rearLength: '0%'
            },
            pivot: {
                backgroundColor: 'gray',
                radius: 6
            }
        }]
    });
}

document.getElementById('leftButton2').addEventListener('click', () => {
    currentIndex = (currentIndex > 0) ? currentIndex - 1 : data.length - 1;
    createCharts();
});

document.getElementById('rightButton2').addEventListener('click', () => {
    currentIndex = (currentIndex < data.length - 1) ? currentIndex + 1 : 0;
    createCharts();
});

fetchDataAndCreateChart();
setTimeout(() => {
    setInterval(() => update(llchart, lnchart), 5000);
}, 3000);