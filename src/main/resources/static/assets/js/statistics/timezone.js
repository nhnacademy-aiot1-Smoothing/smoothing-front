am5.ready(function() {
    let root = am5.Root.new("statistics-timezone");

    root.setThemes([am5themes_Animated.new(root)]);

    $.get("/sensor/kwh/usage/weekly/timezone", function(response) {
        let chartData = response.data;

        let chart = root.container.children.push(am5percent.PieChart.new(root, {
            layout: root.verticalLayout
        }));

        let series = chart.series.push(am5percent.PieSeries.new(root, {
            alignLabels: true,
            calculateAggregates: true,
            valueField: "value",
            categoryField: "label"
        }));

        series.slices.template.setAll({
            strokeWidth: 3,
            stroke: am5.color(0xffffff)
        });

        series.labelsContainer.set("paddingTop", 30);

        series.slices.template.adapters.add("radius", function (radius, target) {
            let dataItem = target.dataItem;
            let high = series.getPrivate("valueHigh");

            if (dataItem) {
                let value = target.dataItem.get("valueWorking", 0);
                return radius * value / high;
            }
            return radius;
        });

        series.data.setAll(chartData);

        let legend = chart.children.push(am5.Legend.new(root, {
            centerX: am5.p50,
            x: am5.p50,
            marginTop: 15,
            marginBottom: 15
        }));

        legend.data.setAll(series.dataItems);

        series.appear(1000, 100);
    })
        .catch(error => {
            console.error('Error fetching data:', error);
        });

});
