am5.ready(function() {

// Create root element
// https://www.amcharts.com/docs/v5/getting-started/#Root_element
    var root = am5.Root.new("chartdiv");


// Set themes
// https://www.amcharts.com/docs/v5/concepts/themes/
    root.setThemes([
        am5themes_Animated.new(root)
    ]);


// Create chart
// https://www.amcharts.com/docs/v5/charts/xy-chart/
    var chart = root.container.children.push(am5radar.RadarChart.new(root, {
        panX: false,
        panY: false,
        wheelX: "none",
        wheelY: "none",
        innerRadius:am5.percent(40)
    }));

// We don't want zoom-out button to appear while animating, so we hide it
    chart.zoomOutButton.set("forceHidden", true);


// Create axes
// https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
    var xRenderer = am5radar.AxisRendererCircular.new(root, {
        minGridDistance: 30
    });

    xRenderer.grid.template.set("visible", false);

    var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
        maxDeviation: 0.3,
        categoryField: "sensorName",
        renderer: xRenderer
    }));

    var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
        maxDeviation: 0.3,
        min: 0,
        visible: false,
        renderer: am5radar.AxisRendererRadial.new(root, {
        })
    }));


// Add series
// https://www.amcharts.com/docs/v5/charts/xy-chart/series/
    var series = chart.series.push(am5radar.RadarColumnSeries.new(root, {
        name: "Sensors",
        xAxis: xAxis,
        yAxis: yAxis,
        valueYField: "value",
        categoryXField: "sensorName"
    }));

// Rounded corners for columns
    series.columns.template.setAll({
        cornerRadius: 5,
        tooltipText:"{categoryX}: {valueY}"
    });

// Make each column to be of a different color
    series.columns.template.adapters.add("fill", function (fill, target) {
        return chart.get("colors").getIndex(series.columns.indexOf(target ));
    });

    series.columns.template.adapters.add("stroke", function (stroke, target) {
        return chart.get("colors").getIndex(series.columns.indexOf(target ));
    });

// Set data
    fetch("/sensor/kwh/daily/value?tags=")
        .then(response => response.json())
        .then(res => getTop5ValueSensors(res))
        .then(data => {
            xAxis.data.setAll(data);
            series.data.setAll(data);
        })
        .then(setInterval(() => {
            updateData()
        }, 5000));

    function getTop5ValueSensors(response) {
        const data = [];

        response.data.sort((a, b) => b.value - a.value);
        for (let i = 0; i < 6; i++) {
            data.push({
                "sensorName": response.data[i].sensorName,
                "value": response.data[i].value
            });
        }

        return data;
    }

    function updateData() {
        fetch("/sensor/kwh/daily/value?tags=")
            .then(response => response.json())
            .then(res => getTop5ValueSensors(res))
            .then(data => {
                am5.array.each(series.dataItems, function (dataItem) {
                    const value = findData(data, dataItem.get("categoryX"));

                    if (value < 0) {
                        xAxis.data.setAll(data);
                        series.data.setAll(data);
                    } else {
                        console.log("value = " + value);
                        dataItem.set("valueY", value);
                        dataItem.animate({
                            key: "valueYWorking",
                            to: value,
                            duration: 600,
                            easing: am5.ease.out(am5.ease.cubic)
                        });
                    }
                })

                sortCategoryAxis();
            })
    }

    function findData(data, target) {
        for (let i = 0; i < data.length; i++) {
            if (data[i].sensorName === target) {
                return data[i].value;
            }
        }

        return -999;
    }

// Get series item by category
    function getSeriesItem(category) {
        for (var i = 0; i < series.dataItems.length; i++) {
            var dataItem = series.dataItems[i];
            if (dataItem.get("categoryX") == category) {
                return dataItem;
            }
        }
    }


// Axis sorting
    function sortCategoryAxis() {

        // Sort by value
        series.dataItems.sort(function (x, y) {
            return y.get("valueY") - x.get("valueY"); // descending
            //return y.get("valueY") - x.get("valueY"); // ascending
        })

        // Go through each axis item
        am5.array.each(xAxis.dataItems, function (dataItem) {
            // get corresponding series item
            var seriesDataItem = getSeriesItem(dataItem.get("category"));

            if (seriesDataItem) {
                // get index of series data item
                var index = series.dataItems.indexOf(seriesDataItem);
                // calculate delta position
                var deltaPosition = (index - dataItem.get("index", 0)) / series.dataItems.length;
                // set index to be the same as series data item index
                dataItem.set("index", index);
                // set deltaPosition instanlty
                dataItem.set("deltaPosition", -deltaPosition);
                // animate delta position to 0
                dataItem.animate({
                    key: "deltaPosition",
                    to: 0,
                    duration: 1000,
                    easing: am5.ease.out(am5.ease.cubic)
                })
            }
        });

        // Sort axis items by index.
        // This changes the order instantly, but as deltaPosition is set,
        // they keep in the same places and then animate to true positions.
        xAxis.dataItems.sort(function (x, y) {
            return x.get("index") - y.get("index");
        });
    }


// Make stuff animate on load
// https://www.amcharts.com/docs/v5/concepts/animations/
    series.appear(1000);
    chart.appear(1000, 100);

}); // end am5.ready()