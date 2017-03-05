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

function _createAlertElement(alertText) {
    return [
        "<div class='alert alert-warning'>",
            alertText,
        "</div>"
    ].join("");
}

function _createOptionAlertElement(warning) {
    if (!warning) return "";     
    return _createAlertElement("Restrictions may apply");
}

function _createRestrictionAlertElement(warning)
{
    if (!warning) return ""; 
    
    return _createAlertElement([
        "<h3>Restrictions</h3>",
        warning.display_text
    ].join(""));
}

function _createOptionElement(option, copy, warning) {
    return [
        "<div class='panel panel-default'>",
            "<div class='panel-body'>",
                "<h2>", _getReadableType(copy.type), "</h2>",
                _createOptionAlertElement(warning),
                copy.description, "<br/><br/>",
                _createRestrictionAlertElement(warning),
                "<h3>Financial Information</h3>",
                copy.cost,
                "<h3>Resources</h3>",
                copy.info_link,
            "</div>",
        "</div>"
    ].join("");
}

function updateOptions(options, warning, optionsCopy) {
    var optionElements = [];
    for (var i = 0; i < options.length; i++) {
        optionElements.push(_createOptionElement(options[i],
                                                 optionsCopy[options[i]['type']],
                                                 warning));
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

    data += "&days_since=";
    
    $.getJSON("http://localhost:3000/options?", data)
    .then(function(response) {
        updateOptions(response.options, response.age_warning, optionsCopy);
    });

    $(e.target).text("Refresh My Options");
    $("#js-section-options-display").show();
    scrollTo("#js-section-options-display");
  }

function showNextField(e) {
    $(e.target).closest(".options-form__item").next().removeClass('options-form__item--initial');
}

function init() {
    var optionsCopy;
    var optionsCopy = $.getJSON("http://localhost:3000/options/copy").then(function(response) {
        $("#js-show-options").click(response, showOptions);
    });

    $("#js-start").click(startForm);

    $(".options-form__item:not(:first-child)").addClass('options-form__item--initial');

    $(".options-form select, .options-form input")
        .on("change", showNextField);

    $('.input-group').datepicker({
      format: 'mm/dd/yyyy',
      autclose: 'true',
      endDate: '+0d'
    }).on('change', function(e){
      $('.datepicker').hide();
    });
}

// good old document load
$(document).ready(init);
})(jQuery)
