(function($) {
function startForm() {
    $("html, body").animate({
        scrollTop: $("#js-page-form").offset().top
    });
}

function showOptions(e) {
    e.preventDefault();
    var data = $(e.target).serialize();
    $.getJSON("http://localhost:3000/options",
    {
        data: data
    }).then(function(response) {
        console.log("response")
    });
}

function init() {
    $("#js-start").click(startForm);
    $("#js-form").submit(showOptions);

    $('.input-daterange').each(function() {
      $(this).datepicker('setStartDate', '01/01/2017');
      $(this).datepicker('clearDates');
    });
}

// good old document load
$(document).ready(init);
})(jQuery)