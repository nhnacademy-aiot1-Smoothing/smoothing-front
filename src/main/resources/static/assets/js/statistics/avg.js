async function fetchData() {
    try {
        const response = await fetch("/sensor/avg/usage");
        const data = await response.json();

        // 전국 평균 전력 사용량 계산
        const wholeCountryUsage = parseInt(data.wholeCountry.powerUsage);
        const wholeCountryCount = parseInt(data.wholeCountry.custCnt);
        const wholeCountryAverage = wholeCountryUsage / wholeCountryCount;

        // 김해시 평균 전력 사용량 계산
        const kimCityUsage = parseInt(data.kimCity.powerUsage);
        const kimCityCount = parseInt(data.kimCity.custCnt);
        const kimCityAverage = kimCityUsage / kimCityCount;

        // 차트 생성
        Highcharts.chart('avg', {
            chart: {
                type: 'column'
            },
            title: {
                text: '평균 비교',
                align: 'center'
            },
            subtitle: {
                text: 'Source: indexmundi',
                align: 'left'
            },
            xAxis: {
                categories: ['전국 평균', '지역 평균', '우리 회사 🏭'],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '전기 사용량 (kWh)'
                }
            },
            tooltip: {
                valueSuffix: ' kWh'
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [
                {
                    name: '전국 평균',
                    data: [wholeCountryAverage]
                },
                {
                    name: '김해 평균',
                    data: [kimCityAverage]
                },
                {
                    name: '우리 회사',
                    data: [3800] // 예시 데이터
                }
            ]
        });

    } catch (error) {
        console.error("ERROR: ", error)
    }
}

document.addEventListener('DOMContentLoaded', fetchData);
