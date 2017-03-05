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

function _createOptionElement(option, copy, warning, id) {
    var financialInfo = "";
    if(copy.cost && copy.cost.length > 0)
      financialInfo = "<h3>Financial Information</h3>" + copy.cost;
    var hiddenSection = "<div class='hiddenSection" + id + " hidden'>" + "<br/><br/>" +
                _createRestrictionAlertElement(warning) +
                financialInfo + "<h3>Resources</h3>" + copy.info_link + "</div>";

    var learnMoreToggle = "<a href='#' class='expandOption'  data-target='.hiddenSection"+id+"'> Learn more...</a>";
    return [
        "<div class='panel panel-default'>",
            "<div class='panel-body'>",
                "<h2>", _getReadableType(copy.type), "</h2>",
                _createOptionAlertElement(warning),
                copy.description,
                learnMoreToggle,
                hiddenSection,
            "</div>",
        "</div>"
    ].join("");
}

function updateOptions(options, warning, optionsCopy) {
    var availableElements = [];
    var unavailableElements = [];
    for (var i = 0; i < options.length; i++) {
      if(options[i].available)
        availableElements.push(_createOptionElement(options[i],
              optionsCopy[options[i]['type']], warning, i));
      else
        unavailableElements.push(_createOptionElement(options[i],
              optionsCopy[options[i]['type']], warning));
    }
    var pluralizedCopy = (unavailableElements.length > 1)? "these options are" : "this option is"
    var splitText = "<h1><div>Based on your information, " + pluralizedCopy + " not available</div></h1>";
    $("#js-available-options-display").empty().append(availableElements);
    if(unavailableElements.length > 0)
      $("#js-unavailable-options-display").empty().append(splitText).append(unavailableElements);
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
      $('.expandOption').on('click', function(e){
        e.preventDefault();
        $(e.target).hide();
        var hiddenSection = $(e.target).data('target');
        $(hiddenSection).toggleClass('hidden');
      });
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
