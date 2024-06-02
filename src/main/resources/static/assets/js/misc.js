$(document).ready(function() {
    $('.nav-item .nav-link', '.sidebar').on('click', function(e) {
        var submenu = $(this).parent().find('.sub-menu');
        if (submenu.length > 0) {
            e.preventDefault();
            var firstSubmenuItem = submenu.find('.nav-item:first-child .nav-link');
            window.location.href = firstSubmenuItem.attr('href');
        }
    });
});

(function ($) {
    'use strict';
    $(function () {
        var body = $('body');
        var contentWrapper = $('.content-wrapper');
        var scroller = $('.container-scroller');
        var footer = $('.footer');
        var sidebar = $('.sidebar');

        function addActiveClass(element) {

            if (current === "") {
                if (element.attr('href').indexOf("sidebar.html") !== -1) {
                    $('.nav-item').removeClass('active');
                    element.parents('.nav-item').last().addClass('active');
                    if (element.parents('.sub-menu').length) {
                        element.closest('.collapse').addClass('show');
                        element.addClass('active');
                    }

                }
            } else {
                if (element.attr('href').indexOf(current) !== -1) {
                    $('.nav-item').removeClass('active');
                    element.parents('.nav-item').last().addClass('active');
                    if (element.parents('.sub-menu').length) {
                        element.closest('.collapse').addClass('show');
                        element.addClass('active');
                    }
                    if (element.parents('.submenu-item').length) {
                        element.addClass('active');
                    }

                }
            }
        }


        var current = location.pathname.split("/").slice(-1)[0].replace(/^\/|\/$/g, '');

        $('.nav li a', sidebar).each(function () {
            var $this = $(this);
            addActiveClass($this);
        })

        $('.horizontal-menu .nav li a').each(function () {
            var $this = $(this);
            addActiveClass($this);
        })

        sidebar.on('show.bs.collapse', '.collapse', function () {
            sidebar.find('.collapse.show').collapse('hide');
        });

        applyStyles();

        function applyStyles() {
            if (!body.hasClass("rtl")) {
                if (body.hasClass("sidebar-fixed")) {
                    var fixedSidebarScroll = new PerfectScrollbar('#sidebar .nav');
                }
            }
        }

        $('[data-toggle="minimize"]').on("click", function () {
            if ((body.hasClass('sidebar-toggle-display')) || (body.hasClass('sidebar-absolute'))) {
                body.toggleClass('sidebar-hidden');
            } else {
                body.toggleClass('sidebar-icon-only');
            }
        });

        $(".form-check label,.form-radio label").append('<i class="input-helper"></i>');

        $("#fullscreen-button").on("click", function toggleFullScreen() {
            if ((document.fullScreenElement !== undefined && document.fullScreenElement === null) || (document.msFullscreenElement !== undefined && document.msFullscreenElement === null) || (document.mozFullScreen !== undefined && !document.mozFullScreen) || (document.webkitIsFullScreen !== undefined && !document.webkitIsFullScreen)) {
                if (document.documentElement.requestFullScreen) {
                    document.documentElement.requestFullScreen();
                } else if (document.documentElement.mozRequestFullScreen) {
                    document.documentElement.mozRequestFullScreen();
                } else if (document.documentElement.webkitRequestFullScreen) {
                    document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
                } else if (document.documentElement.msRequestFullscreen) {
                    document.documentElement.msRequestFullscreen();
                }
            } else {
                if (document.cancelFullScreen) {
                    document.cancelFullScreen();
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                } else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
            }
        })

        if ($(".navbar").hasClass("fixed-top")) {
            document.querySelector('.page-body-wrapper').classList.remove('pt-0');
            document.querySelector('.navbar').classList.remove('pt-5');
        } else {
            document.querySelector('.page-body-wrapper').classList.add('pt-0');
            document.querySelector('.navbar').classList.add('pt-5');
            document.querySelector('.navbar').classList.add('mt-3');

        }
    });
})(jQuery);