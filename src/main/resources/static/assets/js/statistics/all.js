(async () => {

    const data = await fetch(
        'https://demo-live-data.highcharts.com/aapl-c.json'
    ).then(response => response.json());

    Highcharts.setOptions({
        lang: {
            rangeSelectorZoom: 'Granularity'
        }
    });

    Highcharts.stockChart('container', {

        rangeSelector: {
            allButtonsEnabled: true,
            buttons: [{
                type: 'week',
                count: 1,
                text: 'Day',
                dataGrouping: {
                    forced: true,
                    units: [['day', [1]]]
                }
            }, {
                type: 'year',
                count: 1,
                text: 'Week',
                dataGrouping: {
                    forced: true,
                    units: [['week', [1]]]
                }
            }, {
                type: 'all',
                text: 'Month',
                dataGrouping: {
                    forced: true,
                    units: [['month', [1]]]
                }
            }],
            buttonTheme: {
                width: 60
            },
            selected: 2
        },

        title: {
            text: 'AAPL Stock Price'
        },

        subtitle: {
            text: 'Custom data grouping tied to range selector'
        },

        _navigator: {
            enabled: false
        },

        plotOptions: {
            area: {
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
            name: 'AAPL',
            data: data,
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
        }]
    });
})();