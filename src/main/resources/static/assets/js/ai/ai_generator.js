// 요일 배열 생성 함수 (최근 7일)
function getDayArray(days) {
    const dayNames = ['일', '월', '화', '수', '목', '금', '토'];
    const dayArray = [];
    const currentDate = new Date();
    for (let i = 0; i < days; i++) {
        dayArray.unshift(dayNames[currentDate.getDay()]);
        currentDate.setDate(currentDate.getDate() - 1);
    }
    return dayArray;
}

// 임의의 발전량 데이터 생성 함수
function getRandomData(size, min, max) {
    const data = [];
    for (let i = 0; i < size; i++) {
        data.push(Math.floor(Math.random() * (max - min + 1)) + min);
    }
    return data;
}

// 최근 7일간의 요일 생성
const categories = getDayArray(7);

// 임의의 발전량 데이터 생성 (단위: kWh)
const data = getRandomData(7, 10000, 500000);

Highcharts.chart('monitoring-graph', {
    chart: {
        type: 'column'
    },
    title: {
        text: '발전량 모니터링 그래프',
        align: 'left'
    },
    xAxis: {
        categories: categories,
        crosshair: true,
        accessibility: {
            description: 'Days'
        },
        labels: {
            useHTML: true,
            style: {
                textAlign: 'center'
            }
        }
    },
    yAxis: [{
        title: {
            text: '발전량 (kWh)'
        },
        showFirstLabel: false
    }],
    tooltip: {
        shared: true,
        headerFormat: '<span style="font-size: 15px">{point.key}</span><br/>',
        pointFormat: '<span style="color:{point.color}">\u25CF</span> ' +
            '{series.name}: <b>{point.y} kWh</b><br/>'
    },
    plotOptions: {
        series: {
            grouping: false,
            borderWidth: 0
        }
    },
    legend: {
        enabled: false
    },
    series: [{
        name: '발전량',
        id: 'main',
        dataSorting: {
            enabled: true,
            matchByName: true
        },
        dataLabels: [{
            enabled: false,
            inside: false,
            style: {
                fontSize: '16px'
            },
            formatter: function() {
                return Highcharts.numberFormat(this.y, 0, '.', ',');
            }
        }],
        data: data
    }],
    exporting: {
        allowHTML: true
    }
});
