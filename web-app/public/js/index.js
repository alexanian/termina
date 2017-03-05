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

function _getReadableDate(date) {
  var monthNames = [
    "January", "February", "March",
    "April", "May", "June", "July",
    "August", "September", "October",
    "November", "December"
  ];

  var day = date.getDate();
  var monthIndex = date.getMonth();
  var year = date.getFullYear();

  return monthNames[monthIndex] + ' ' + day + ', ' + year;
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

function _createFirstTrimesterWarningElement(date, daysSince) {    
    var daysInFirstTrimester = 90;
    if (daysSince > daysInFirstTrimester) return "";
    
    var endOfTrimesterDate = new Date();
    endOfTrimesterDate.setDate(date.getDate() + daysInFirstTrimester);
    
    return [
        "<div id='first-trimester-warning'>",
        "Abortion care will be easiest for you to access before your first trimester ends ",
        "(around ",
        "<span class='glyphicon glyphicon-time'></span> ",
        "<strong id='first-trimester-date'>",
        _getReadableDate(endOfTrimesterDate),
        ")</strong>.",
        "</div>"
    ].join("");
}

function updateFirstTrimesterWarning(date, daysSince) {
    var warningElement = _createFirstTrimesterWarningElement(date, daysSince);
    $("#js-warning-display").empty().append(warningElement);
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

function updateURL(args) {
    if ('history' in window) {
        window.history.pushState({}, "", "?" + args)
    }
}

function showOptions(optionsCopy) {
  var data = $('form').serialize();
  var date = new Date($('#date').val());
  var oneDay = 24*60*60*1000;
  var today = new Date();
  var urlData = data;
  urlData += "&date=" + (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
  updateURL(urlData);
  var daysSince = Math.round(Math.abs((today.getTime() - date.getTime())/(oneDay)));
  data += "&days_since=" + daysSince;
  $.getJSON("http://localhost:3000/options?", data)
  .then(function(response) {
      updateFirstTrimesterWarning(date, daysSince);
      updateOptions(response.options, response.age_warning, optionsCopy);

      $('.expandOption').on('click', function(e){
        e.preventDefault();
        $(e.target).hide();
        var hiddenSection = $(e.target).data('target');
        $(hiddenSection).toggleClass('hidden');
      });
  });
  $("#js-show-options").text("Refresh My Options");
  $("#js-section-options-display").show();
  scrollTo("#js-section-options-display");
}

function onShowOptions(e) {
    e.preventDefault();
    showOptions(e.data);
}

function showNextField(e) {
    $(e.target).closest(".options-form__item").next().removeClass('options-form__item--initial');
}

function initFormFromURL() {
    var query = window.location.search;
    var queryArray = query.slice(1).split("&");
    var today = new Date();
    for (var i = 0; i < queryArray.length; i++) {
        var argArray = queryArray[i].split("=");
        var key = argArray[0];
        var value = argArray[1];
        if (key === "date") {
            $("#date").datepicker('update', value);
        } else {
            $("[name='" + key+ "']").val(value).trigger('change');
        }
    }
}

function init() {
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

    var optionsCopy = $.getJSON("http://localhost:3000/options/copy").then(function(response) {
        initFormFromURL();
        if ($('form').serializeArray().length === 2 && $("#date").datepicker('getDate')) {
            showOptions(response);
        }
        $("#js-show-options").click(response, onShowOptions);
    });
}

// good old document load
$(document).ready(init);
})(jQuery)
