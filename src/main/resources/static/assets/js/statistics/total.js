(async () => {

    const response = await fetch('/sensor/kwh/usage/hourly/total?tags=');
    const data = await response.json();
    const convertedData = data.data.map(entry => {
        return {
            x: new Date(entry.time).getTime(),
            y: entry.value
        };
    });

    Highcharts.setOptions({
        lang: {
            rangeSelectorZoom: 'Granularity'
        }
    });

    Highcharts.stockChart('statistics-total', {

        rangeSelector: {
            allButtonsEnabled: true,
            buttons: [{
                type: 'day',
                count: 1,
                text: 'Hour',
                dataGrouping: {
                    forced: true,
                    units: [['hour', [1]]]
                }
            }, {
                type: 'week',
                count: 1,
                text: 'Day',
                dataGrouping: {
                    forced: true,
                    approximation: 'sum',
                    units: [['day', [1]]]
                }
            }, {
                type: 'all',
                text: 'Week',
                dataGrouping: {
                    forced: true,
                    approximation: 'sum',
                    units: [['week', [1]]]
                }
            }],
            buttonTheme: {
                width: 60
            },
            selected: 2
        },

        navigator: {
            enabled: false
        },

        plotOptions: {
            area: {
                turboThreshold: 10000,
                fillColor: {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },

    series: [{
            name: '전력 사용량',
            data: convertedData,
            marker: {
                enabled: null,
                radius: 3,
                lineWidth: 1,
                lineColor: '#FFFFFF'
            },
            type: 'area',
            tooltip: {
                valueDecimals: 2
            }
        }],
        credits: {
            enabled: false
        }
    });
})();