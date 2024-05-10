document.addEventListener('DOMContentLoaded', function () {

    let deleteButtons = document.querySelectorAll('.deleteButton');

    for(let i = 0; i < deleteButtons.length; i++) {
        deleteButtons[i].addEventListener('click', function () {
            let row = deleteButtons[i].closest('tr');
            let errorTds = row.querySelectorAll('td');

            const findErrorId = errorTds[0].innerText;
            document.querySelector('#errorDeleteId').innerHTML = findErrorId;

            document.querySelector('#deleteModalContent').innerHTML = '에러 ' + findErrorId + '을/를 삭제하시겠습니까?<br>삭제된 데이터는 복구할 수 없습니다.';
        });
    }

    document.querySelector('#deleteModalDeleteButton').addEventListener('click', function () {
        let errorId = document.querySelector('#errorDeleteId').innerHTML;
        fetch('/api/error-monitoring/' + errorId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류');
            }
            return response;
        }).then(data => {
            alert('에러가 삭제되었습니다.');
            location.reload();
        }).catch(error => {
            console.error('에러 삭제 오류:', error);
        });
    });
});