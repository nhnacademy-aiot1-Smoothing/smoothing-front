let target = 1;
const duration = 1500; // 애니메이션의 전체 소요 시간 (밀리초)

function setupFlip(tick) {
    let startTime = null;

    function updateTick(timestamp) {
        let currentTarget = target;

        if (!startTime) {
            startTime = timestamp;
        }

        const progress = timestamp - startTime;
        const newValue = Math.min(currentTarget, Math.floor(progress / duration * currentTarget)); // 애니메이션 중간값 계산
        tick.value = newValue.toLocaleString() + "₩"; // 쉼표 추가

        if (newValue < currentTarget) {
            requestAnimationFrame(updateTick); // 다음 프레임 요청
        }
    }

    function update() {
        Tick.helper.interval(function() {
            fetch("/sensor/cost")
                .then(response => response.json())
                .then((response) => {
                    tick.value = response.cost.toLocaleString() + "₩";
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
