function battery(charge) {
    var power = Math.ceil(charge / 4);
    $(".battery .bar").removeClass("active red yellow green");

    $(".battery .bar").each(function(index) {
        if (index < power) {
            $(this).addClass("active");
            if (charge >= 80) {
                $(this).addClass("red");
            } else if (charge >= 60) {
                $(this).addClass("yellow");
            } else {
                $(this).addClass("green");
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
        battery(percent);
    })
    .then(() => {
        setInterval(() => {
            fetch("/sensor/goal/current")
                .then(response => response.json())
                .then(res => {
                    const percent = getPercent(res.goalAmount, res.currentAmount);
                    battery(percent);
                })
        }, 5000);
    })