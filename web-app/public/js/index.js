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
      "<div class='text-warning'>",
      "<i class='fa fa-warning'></i>",
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

    return [
      "<div class='text-warning'>",
      "<i class='fa fa-warning'></i><strong>Restrictions</strong><br/>",
      warning.display_text,
      "</div>"
    ].join("");
  }

  function _displayAgeWarningForType(type) {
    var ageIndependentOptions = ["parenthood", "adoption"];
    return !ageIndependentOptions.includes(type)
  }

  function _createOptionElement(option, copy, warning, id) {
    var financialInfo = "";
    if(copy && copy.cost && copy.cost.length > 0)
      financialInfo = "<h3>Financial Information</h3>" + copy.cost;


    var ageAlert = "";
    var hiddenSection = "<div class='hidden-section hiddenSection" + id + " hidden'>";
    if(_displayAgeWarningForType(option.type)) {
      hiddenSection += _createRestrictionAlertElement(warning);
      ageAlert = _createOptionAlertElement(warning);
    }
    hiddenSection += financialInfo + "<h3>Resources</h3>" + copy.info_link + "</div>";

    var learnMoreToggle = "<a href='#' class='expandOption' data-target='.hiddenSection" + id + "'> Learn more...</a>";

    var typeToIconMap = {
      "medication":"medkit",
      "surgical":"user-md",
      "surgical_travel":"user-md",
      "later_care":"user-md",
      "parenthood":"users",
      "adoption":"child"
    };
    var optionIcon = typeToIconMap[option.type] || "adjust";

    return [
      "<div class='panel panel-default'>",
      "<div class='panel-body'>",
      "<h3>", "<i class='fa fa-" + optionIcon + "'></i> ", _getReadableType(copy.type), "</h3>",
      ageAlert,
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
    $("#js-available-options-display, #js-unavailable-options-display").empty()
    var pluralizedCopy = (unavailableElements.length > 1)? "these options are" : "this option is"
    var splitText = "<h2><div>Based on your information, " + pluralizedCopy + " not available:</div></h2>";
    $("#js-available-options-display").append(availableElements);
    if(unavailableElements.length > 0)
      $("#js-unavailable-options-display").append(splitText).append(unavailableElements);

    $("#js-page-link").text(getURL());
  }

  function getURL() {
    // Use to get the URL to this set of options; can be shown in a save
    var date = new Date($('#date').val());
    var data = $('form').serialize();
    data += "&date=" + (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
    return window.location.origin + window.location.pathname + "?" + data
  }

  function showOptions(optionsCopy) {
    var data = $('form').serialize();
    var date = new Date($('#date').val());
    var oneDay = 24*60*60*1000;
    var today = new Date();

    var daysSince = Math.round(Math.abs((today.getTime() - date.getTime())/(oneDay)));
    data += "&days_since=" + daysSince;
    $.getJSON("/options?", data)
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
    $("#js-page-link").on("click", "input", function() {
      this.setSelectionRange(0, this.value.length);
    });

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

    var optionsCopy = $.getJSON("/options/copy").then(function(response) {
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
