(function($) {
function startForm() {
    $("html, body").animate({
        scrollTop: $("#js-section-options-form").offset().top
    });
}

function _getAvailability(available) {
    if (available) {
        return 'This is available for you.';
    } else {
        return 'This is not available for you.' + '<br />' + 'Reason: Lorem ipsum doler sit'
    }
}

function _getReadableType(optionType) {
    return optionType;
}

function _createOptionElement(option) {
    return [
        "<div class='panel panel-default'>",
            "<div class='panel-heading'>",
                _getAvailability(option.available),
            "</div>",
            "<div class='panel-body'>",
                "<h2>", _getReadableType(option.type), "</h2>",
                "Here is a truncated version of the full description. It can be really long at first...",
            "</div>",
        "</div>"
    ].join("");
}

function updateOptions(options) {
    var optionElements = [];
    for (var i = 0; i < options.length; i++) {
        optionElements.push(_createOptionElement(options[i]));
    }
    $("#js-options-display").empty().append(optionElements);
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

    updateOptions(window.options);
    $("#js-section-options-display").show();
    $("html, body").animate({
        scrollTop: $("#js-section-options-display").offset().top - 200
    });

}

function init() {
    $("#js-start").click(startForm);
    $("#js-show-options").click(showOptions);

    $('.input-daterange').each(function() {
      $(this).datepicker('setStartDate', '01/01/2017');
      $(this).datepicker('clearDates');
    });
}

// good old document load
$(document).ready(init);
})(jQuery)