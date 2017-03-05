(function($) {
function scrollTo(selector) {
    $("html, body").animate({
      scrollTop: $(selector).offset().top
    });

}
  function startForm() {
    scrollTo("#js-section-options-form");
  }

function _getReadableType(optionType) {
    return optionType;
}

function _createOptionElement(option, copy) {
    return [
        "<div class='panel panel-default'>",
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

    $(e.target).text("Refresh My Options");
    $("#js-section-options-display").show();
    scrollTo("#js-section-options-display");
  }

var NUM_EXPECTED_FIELDS = 3;
function init() {
    var optionsCopy;
    var optionsCopy = $.getJSON("http://localhost:3000/options/copy").then(function(response) {
        $("#js-show-options").click(response, showOptions);
    });

    $("#js-start").click(startForm);

    $(".options-form select, .options-form input").on("change", function () {
        var count = 0;
        if ($('#date').datepicker('getDate')) {
            count += 1;
        }

        var elements = $("form").serializeArray();
        for (var i = 0; i < elements.length; i++) {
            if (elements[i].value) {
                count+=1;
            }
        }

        $("#js-show-options").attr("disabled", count !== NUM_EXPECTED_FIELDS);
    });

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
