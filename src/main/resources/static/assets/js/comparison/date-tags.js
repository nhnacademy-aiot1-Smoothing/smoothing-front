const startDatePicker = new Pikaday({field: document.getElementById('startDate'), format: 'YYYY-MM-DD'});
const endDatePicker = new Pikaday({field: document.getElementById('endDate'), format: 'YYYY-MM-DD'});

const defaultStartDate = new Date();
defaultStartDate.setDate(defaultStartDate.getDate() - 7);
startDatePicker.setDate(defaultStartDate);

const defaultEndDate = new Date();
defaultEndDate.setDate(defaultEndDate.getDate() - 1);
endDatePicker.setDate(defaultEndDate);

let data1 = null;
let data1Label = null;

document.getElementById("tagSelectButton").addEventListener("click", function () {
    const selectedTags = Array.from(document.querySelectorAll("#tagCheckboxGroup input[name=tag]:checked")).map(checkbox => checkbox.value);

    const startDate = formatDate(new Date(document.getElementById("startDate").value));
    const endDate = formatDate(new Date(document.getElementById("endDate").value));

    const tagsQueryString = selectedTags.join(',');

    fetchDataForComparisonSum(tagsQueryString, startDate, endDate);

    fetchDataForComparisonIndividual(tagsQueryString, startDate, endDate);
});

document.getElementById("tagSelectButton2").addEventListener("click", function () {
    const selectedTags2 = Array.from(document.querySelectorAll("#tagCheckboxGroup2 input[name=tag]:checked")).map(checkbox => checkbox.value);

    const startDate = formatDate(new Date(document.getElementById("startDate").value));
    const endDate = formatDate(new Date(document.getElementById("endDate").value));

    const tagsQueryString2 = selectedTags2.join(',');

    fetchDataForComparisonGroup(tagsQueryString2, startDate, endDate);
});

function fetchDataForComparisonSum(tagsQueryString, startDate, endDate) {
    fetch(`/sensor/kwh/usage/daily/period/total?tags=${tagsQueryString}&start=${startDate}&end=${endDate}`)
        .then(response => response.json())
        .then(data => {
            data1 = data;
            let label;
            if (data.tags[0] === '') {
                label = '전체 데이터';
            } else {
                data.tags.forEach((tag, index) => {
                    if (index === 0) {
                        label = tag;
                    } else {
                        label += `, ${tag}`;
                    }
                })
            }
            data1Label = label;

            Highcharts.chart('comparison-sum', {
                title: {
                    text: ''
                },
                chart: {
                    type: 'column'
                },
                xAxis: {
                    categories: data.data.map(item => item.time.split('T')[0]),
                    crosshair: true,
                    accessibility: {
                        description: 'Dates'
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'kWh'
                    }
                },
                tooltip: {
                    valueSuffix: ' kWh'
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [
                    {
                        name: data1Label,
                        data: data.data.map(item => item.value),
                        color: '#5FBDFF'
                    }
                ]
            });

            const modalElement = document.getElementById('tagSelectModal');
            const modal = bootstrap.Modal.getInstance(modalElement);
            modal.hide();

        })
        .catch(error => {
            console.error('요청 실패:', error);
        });
}

function fetchDataForComparisonIndividual(tagsQueryString, startDate, endDate) {
    fetch(`/sensor/kwh/usage/daily/period/?tags=${tagsQueryString}&start=${startDate}&end=${endDate}`)
        .then(response => response.json())
        .then(data => {
            const groupedData = {};
            data.data.forEach(item => {
                item.powerMetrics.forEach(metric => {
                    if (!groupedData[item.sensorName]) {
                        groupedData[item.sensorName] = [];
                    }
                    groupedData[item.sensorName].push({
                        time: metric.time.split('T')[0],
                        value: metric.value
                    });
                });
            });
            renderChart(groupedData);
        })
        .catch(error => {
            console.error('요청 실패:', error);
        });
}

function renderChart(groupedData) {
    const seriesData = [];
    for (const sensorName in groupedData) {
        if (groupedData.hasOwnProperty(sensorName)) {
            seriesData.push({
                name: sensorName,
                data: groupedData[sensorName].map(item => parseFloat(item.value.toFixed(2)))
            });
        }
    }

    Highcharts.chart('comparison-individual', {
        chart: {
            type: 'spline'
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: Object.values(groupedData)[0].map(item => item.time),
            accessibility: {
                description: 'Dates'
            }
        },
        yAxis: {
            title: {
                text: 'kWh'
            }
        },
        credits: {
            enabled: false
        },
        tooltip: {
            crosshairs: true,
            shared: true
        },
        plotOptions: {
            spline: {
                marker: {
                    radius: 4,
                    lineColor: '#666666',
                    lineWidth: 1
                }
            }
        },
        series: seriesData
    });
}

function fetchDataForComparisonGroup(tagsQueryString2, startDate, endDate) {
            fetch(`/sensor/kwh/usage/daily/period/total?tags=${tagsQueryString2}&start=${startDate}&end=${endDate}`)
                .then(response => response.json())
                .then(data2 => {
                    let label;
                    if (data2.tags[0] === '') {
                        label = '전체 데이터';
                    } else {
                        data2.tags.forEach((tag, index) => {
                            if (index === 0) {
                                label = tag;
                            } else {
                                label += `, ${tag}`;
                            }
                        })
                    }

                    Highcharts.chart('comparison-group', {
                        title: {
                            text: ''
                        },
                        chart: {
                            type: 'column'
                        },
                        xAxis: {
                            categories: data1.data.map(item => item.time.split('T')[0]),
                            crosshair: true,
                            accessibility: {
                                description: 'Dates'
                            }
                        },
                        yAxis: {
                            min: 0,
                            title: {
                                text: 'kWh'
                            }
                        },
                        tooltip: {
                            valueSuffix: ' kWh'
                        },
                        credits: {
                            enabled: false
                        },
                        plotOptions: {
                            column: {
                                pointPadding: 0.2,
                                borderWidth: 0
                            }
                        },
                        series: [{
                            name: data1Label,
                            data: data1.data.map(item => item.value),
                            color: '#5FBDFF'
                        }, {
                            name: label,
                            data: data2.data.map(item => item.value),
                            color: '#7267CB'
                        }]
                    });

                    const modalElement2 = document.getElementById('tagSelectModal2');
                    const modal2 = bootstrap.Modal.getInstance(modalElement2);
                    modal2.hide();
                })
                .catch(error => {
                    console.error('두 번째 데이터 요청 실패:', error);
        });
}


function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}
