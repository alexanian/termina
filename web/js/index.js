(function($) {
function init() {
    $("#js-start").click(function () {
        $("html, body").animate({
            scrollTop: $("#js-page-form").offset().top
        });
    });
}

// good old document load
$(document).ready(init);
})(jQuery)