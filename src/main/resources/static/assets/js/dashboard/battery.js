function battery(charge) {
    var power = Math.ceil(charge / 2);
    $(".battery .bar").removeClass("active red yellow green"); // 모든 막대 리셋

    $(".battery .bar").each(function(index) {
        if (index < power) {
            $(this).addClass("active"); // 막대 활성화
            if (charge <= 20) {
                $(this).addClass("red"); // 20% 이하면 빨간색
            } else if (charge <= 40) {
                $(this).addClass("yellow"); // 40% 이하면 노란색
            } else {
                $(this).addClass("green"); // 그 이상은 녹색
            }
        }
    });
}

function getPercent(goal, current) {
    return Math.floor(current / goal * 100);
}

fetch("/sensor/goal/current")
    .then(response => response.json())
    .then(res => {
        const percent = getPercent(res.goalAmount, res.currentAmount);
        battery(100 - percent);
    })
    .then(() => {
        setInterval(() => {
            fetch("/sensor/goal/current")
                .then(response => response.json())
                .then(res => {
                    const percent = getPercent(res.goalAmount, res.currentAmount);
                    battery(100 - percent);
                })
        }, 5000);
    })