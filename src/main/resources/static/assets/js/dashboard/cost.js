

let target = 1;
const duration = 1500;

function setupFlip(tick) {
    let startTime = null;

    function updateTick(timestamp) {
        let currentTarget = target;

        if (!startTime) {
            startTime = timestamp;
        }

        const progress = timestamp - startTime;
        const newValue = Math.min(currentTarget, Math.floor(progress / duration * currentTarget));
        tick.value = "₩" + newValue.toLocaleString() ;

        if (newValue < currentTarget) {
            requestAnimationFrame(updateTick);
        }
    }

    function update() {
        Tick.helper.interval(function() {
            fetch("/sensor/cost")
                .then(response => response.json())
                .then((response) => {
                    tick.value = "₩" + response.cost.toLocaleString();
                })

        }, 5000);
    }

    fetch("/sensor/cost")
        .then(response => response.json())
        .then(response => {
            target = response.cost;
        })
        .then(requestAnimationFrame(updateTick))
        .then(update())
}
