$(document).ready(function() {
  $('.input-daterange').each(function() {
    $(this).datepicker('setStartDate', '01/01/2017');
    $(this).datepicker('clearDates');
  });
});
