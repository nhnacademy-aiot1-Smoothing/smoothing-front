//
// fetch(`/sensor/kwh/usage/daily/period/total?tags=NHN,Office&start=2024-04-16&end=2024-05-04`)
//     .then(response => {
//         return response.json();
//     }).then(data1 => {
//     fetch(`/sensor/kwh/usage/daily/period/total?tags=Office&start=2024-04-16&end=2024-05-04`)
//         .then(response => {
//             return response.json();
//         }).then(data2 => {
//         Highcharts.chart('comparison-group', {
//             title: {
//                 text: ''
//             },
//             chart: {
//                 type: 'column'
//             },
//             xAxis: {
//                 categories: data1.data.map(item => item.time.split('T')[0]),
//                 crosshair: true,
//                 accessibility: {
//                     description: 'Dates'
//                 }
//             },
//             yAxis: {
//                 min: 0,
//                 title: {
//                     text: 'kWh'
//                 }
//             },
//             tooltip: {
//                 valueSuffix: ' kWh'
//             },
//             credits: {
//                 enabled: false
//             },
//             plotOptions: {
//                 column: {
//                     pointPadding: 0.2,
//                     borderWidth: 0
//                 }
//             },
//             series: [{
//                 name: 'First data',
//                 data: data1.data.map(item => item.value)
//             }, {
//                 name: 'Second Data',
//                 data: data2.data.map(item => item.value)
//             }]
//         });
//     }).catch(error => {
//         console.error('두 번째 데이터 요청 실패:', error);
//     });
// })
//     .catch(error => {
//         console.error('첫 번째 데이터 요청 실패:', error);
//     });
