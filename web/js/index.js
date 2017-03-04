(function($) {
function init() {
    $("#js-start").click(function () {
        $("html, body").animate({
            scrollTop: $("#js-page-form").offset().top
        });
    });
  $('.input-daterange').each(function() {
    $(this).datepicker('setStartDate', '01/01/2017');
    $(this).datepicker('clearDates');
  });
}

// good old document load
$(document).ready(init);
})(jQuery)
