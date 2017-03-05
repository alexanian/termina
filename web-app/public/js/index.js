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

function _createOptionElement(option, copy) {
    return [
        "<div class='panel panel-default'>",
            "<div class='panel-heading'>",
                _getAvailability(option.available),
            "</div>",
            "<div class='panel-body'>",
                "<h2>", _getReadableType(copy.type), "</h2>",
                copy.description, "<br/><br/>",
                copy.info_link, "<br/><br/>",
                copy.cost,
            "</div>",
        "</div>"
    ].join("");
}

function updateOptions(options, warning, optionsCopy) {
    var optionElements = [];
    console.log(optionsCopy);
    for (var i = 0; i < options.length; i++) {
        optionElements.push(_createOptionElement(options[i], optionsCopy[options[i]['type']]));
    }
    $("#js-options-display").empty().append(optionElements);
}

  function showOptions(e) {
    e.preventDefault();
    var data = $('form').serialize();
    var date = $('#date').datepicker('getDate');
    var oneDay = 24*60*60*1000;
    var today = new Date();

    var daysSince = Math.round(Math.abs((today.getTime() - date.getTime())/(oneDay)));

    var optionsCopy = e.data;

    data += "&days_since=" + daysSince;

    $.getJSON("http://localhost:3000/options?", data)
    .then(function(response) {
        updateOptions(response.options, response.age_warning, optionsCopy);
    });

    $("#js-section-options-display").show();
    $("html, body").animate({
        scrollTop: $("#js-section-options-display").offset().top - 200
    });
  }

function init() {
    var optionsCopy;
    var optionsCopy = $.getJSON("http://localhost:3000/options/copy").then(function(response) {
        $("#js-show-options").click(response, showOptions);
    });

    $("#js-start").click(startForm);

    $('.input-group').datepicker({
      format: 'mm/dd/yyyy',
      autclose: 'true',
      endDate: '+0d'
    }).on('change', function(){
      $('.datepicker').hide();
    });
}

// good old document load
$(document).ready(init);
})(jQuery)
