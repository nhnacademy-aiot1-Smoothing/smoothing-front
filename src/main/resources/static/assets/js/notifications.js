$(document).ready(function () {
    $('#notificationDropdown').click(function (event) {
        event.preventDefault();
        $.ajax({
            url: '/notifications',
            type: 'GET',
            success: function (data) {
                let notifications = data;
                let dropdown = $('.dropdown-menu-right.navbar-dropdown.preview-list');
                dropdown.empty();
                dropdown.append('<h6 class="p-3 mb-0">Notifications</h6>');
                dropdown.append('<div class="dropdown-divider"></div>');

                if (notifications.length === 0) {
                    dropdown.append('<p class="text-center text-muted m-3">No notifications</p>');
                } else {
                    notifications.forEach(function (notification) {
                        var hashKey = notification.title + '_' + notification.timestamp;
                        dropdown.append(
                            `<a class="dropdown-item preview-item" href="#" data-hashkey="${hashKey}">
                                <div class="preview-thumbnail">
                                    <div class="preview-icon bg-success">
                                        <i class="mdi mdi-calendar"></i>
                                    </div>
                                </div>
                                <div class="preview-item-content d-flex align-items-start flex-column justify-content-center">
                                    <h6 class="preview-subject font-weight-normal mb-1">${notification.title}</h6>
                                    <p class="text-gray ellipsis mb-0">${notification.body}</p>
                                </div>
                            </a>
                            <div class="dropdown-divider"></div>`
                        );
                    });
                }
            },
            error: function (xhr, status, error) {
                console.error("Error fetching notifications:", status, error);
                dropdown.append('<p class="text-center text-muted m-3">알림을 불러오는데 실패했습니다</p>');
            }
        });
    });

    $('.dropdown-menu-right').on('click', '.dropdown-item', function() {
        var hashKey = $(this).data('hashkey');
        var item = this;
        $.ajax({
            url: `/notifications/${hashKey}`,
            type: 'DELETE',
            success: function(result) {
                console.log('Notification deleted:', hashKey);
                $(item).closest('.dropdown-item').remove();
            },
            error: function(xhr, status, error) {
                console.error("Error deleting notification:", status, error);
            }
        });
    });
});
