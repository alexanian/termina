(function($) {
function init() {
    $("#js-page-form").hide();

    $("#js-start").click(function () {
        $("#js-page-splash-screen").hide();
        $("#js-page-form").show();
    });
}

// good old document load
$(init);
})(jQuery)