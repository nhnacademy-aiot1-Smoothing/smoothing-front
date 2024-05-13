document.addEventListener("DOMContentLoaded", function() {
    fetchDataAndDrawChart();
});

let currentYear = new Date().getFullYear();

document.getElementById("prevYearBtn").addEventListener("click", goToPreviousYear);
document.getElementById("nextYearBtn").addEventListener("click", goToNextYear);

function goToPreviousYear() {
    currentYear--;
    fetchDataAndDrawChart();
}

function goToNextYear() {
    let thisYear = new Date().getFullYear();
    if (currentYear < thisYear) {
        currentYear++;
        fetchDataAndDrawChart();
    } else {
        alert("이미 최신 연도입니다.");
    }
}

function fetchDataAndDrawChart() {
    $.ajax({
        url: '/sensor/goals/history?year=' + currentYear,
        method: 'GET',
        success: function (data) {
            console.log(data);
            let columnData = [];
            let lineData = [];
            let months = [];
            let colors = [];

            data.forEach(function (item) {
                let date = new Date(item.date);
                let month = date.toLocaleString('default', {month: 'short'});

                months.push(month);
                columnData.push(item.amount);
                lineData.push(item.goalAmount);

                if (item.amount > item.goalAmount) {
                    colors.push('red');
                } else {
                    colors.push(Highcharts.getOptions().colors[0]);
                }
            });

            let chart = Highcharts.chart('statistics-goal', {
                xAxis: {
                    categories: months
                },
                yAxis: {
                    title: {
                        text: 'Amount'
                    }
                },
                title: {
                    text: ''
                },
                series: [{
                    type: 'column',
                    name: 'Amount',
                    data: columnData,
                    colorByPoint: true,
                    colors: colors,
                }, {
                    type: 'line',
                    name: 'Goal',
                    data: lineData,
                    marker: {
                        lineWidth: 2,
                        lineColor: Highcharts.getOptions().colors[3],
                        fillColor: 'white'
                    }
                }]
            });
        },
        error: function (xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
}
