async function fetchData() {
    try {
        const response = await fetch("/sensor/three-phase")
        const data = await response.json();

        const first_time = parseInt(data.ThreePhase.phaseA.time);
        const first_value = parseInt(data.ThreePhase.phaseA.value);

        const second_time = parseInt(data.ThreePhase.phaseB.time);
        const second_value = parseInt(data.ThreePhase.phaseB.value);

        const third_time = parseInt(data.ThreePhase.phaseC.time);
        const third_value = parseInt(data.ThreePhase.phaseC.value);

        console.log(first_time);
        console.log(first_value);

        var chart = AmCharts.makeChart("three", {
            "type": "serial",
            "theme": "light",
            "marginTop": 0,
            "marginRight": 80,
            "dataProvider": [
                {"time": "00:00", "phaseA": 220, "phaseB": 221, "phaseC": 222},
                {"time": "01:00", "phaseA": 219, "phaseB": 220, "phaseC": 222},
                {"time": "02:00", "phaseA": 218, "phaseB": 219, "phaseC": 221},
                {"time": "03:00", "phaseA": 220, "phaseB": 221, "phaseC": 220},
                {"time": "04:00", "phaseA": 222, "phaseB": 223, "phaseC": 221},
            ],
            "valueAxes": [{
                "axisAlpha": 0,
                "position": "left",
                "title": "전압 (V)"
            }],
            "graphs": [{
                "id": "phaseA",
                "balloonText": "[[category]]<br><b><span style='font-size:14px;'>Phase A: [[value]] V</span></b>",
                "bullet": "round",
                "bulletSize": 8,
                "lineColor": "#FF0000",
                "lineThickness": 2,
                "type": "smoothedLine",
                "valueField": "phaseA"
            }, {
                "id": "phaseB",
                "balloonText": "[[category]]<br><b><span style='font-size:14px;'>Phase B: [[value]] V</span></b>",
                "bullet": "round",
                "bulletSize": 8,
                "lineColor": "#00FF00",
                "lineThickness": 2,
                "type": "smoothedLine",
                "valueField": "phaseB"
            }, {
                "id": "phaseC",
                "balloonText": "[[category]]<br><b><span style='font-size:14px;'>Phase C: [[value]] V</span></b>",
                "bullet": "round",
                "bulletSize": 8,
                "lineColor": "#0000FF",
                "lineThickness": 2,
                "type": "smoothedLine",
                "valueField": "phaseC"
            }],
            "chartScrollbar": {
                "graph": "phaseA",
                "gridAlpha": 0,
                "color": "#888888",
                "scrollbarHeight": 55,
                "backgroundAlpha": 0,
                "selectedBackgroundAlpha": 0.1,
                "selectedBackgroundColor": "#888888",
                "graphFillAlpha": 0,
                "autoGridCount": true,
                "selectedGraphFillAlpha": 0,
                "graphLineAlpha": 0.2,
                "graphLineColor": "#c2c2c2",
                "selectedGraphLineColor": "#888888",
                "selectedGraphLineAlpha": 1
            },
            "chartCursor": {
                "categoryBalloonDateFormat": "JJ:NN",
                "cursorAlpha": 0,
                "valueLineEnabled": true,
                "valueLineBalloonEnabled": true,
                "valueLineAlpha": 0.5,
                "fullWidth": true
            },
            "categoryField": "time",
            "categoryAxis": {
                "parseDates": false,
                "minorGridAlpha": 0.1,
                "minorGridEnabled": true,
                "title": "시간"
            },
            "export": {
                "enabled": true
            }
        });

    } catch (error) {
        console.error("ERROR: ", error)
    }
}

chart.addListener("rendered", zoomChart);
if (chart.zoomChart) {
    chart.zoomChart();
}

function zoomChart() {
    chart.zoomToIndexes(Math.round(chart.dataProvider.length * 0.4), Math.round(chart.dataProvider.length * 0.55));
}