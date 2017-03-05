Array.prototype.unique = function() {
  return this.filter(function (value, index, self) { 
    return self.indexOf(value) === index;
  });
}


var optionIsAvailable = function(option, userState, userDaysSince) {
	var availableDueToState = (option.state.indexOf(userState) !== -1 || option.state.indexOf("All") !== -1);
	var availableDueToDaysSince = (option.min_days_lmp <= userDaysSince && userDaysSince <= option.max_days_lmp);
	return availableDueToState && availableDueToDaysSince;
}

var filterAvailableOptions = function(baseOptions, userState, userDaysSince) {
	var allTypes = baseOptions.map(function(x) { return x.type });
	var allTypes = allTypes.filter(function (value, index, self) { 
    	return self.indexOf(value) === index;
  	});
	var optionsToReturn = [];

	// For every set of rules, return 1 set of option data, and whether it's available or not.
	allTypes.forEach(function(type) {
		var optionsForType = baseOptions.filter(function(option) { return option.type == type});
		var availableOptions = optionsForType.filter(function(option) { return optionIsAvailable(option, userState, userDaysSince)});
		var isAvailable = (availableOptions.length >= 1);

		if (isAvailable) {
			optionData = availableOptions[0];
		} else {
			optionData = optionsForType[0];
		}
		optionsToReturn.push({
			'type': optionData.type,
			'min': optionData.min_days_lmp,
			'max': optionData.max_days_lmp,
			'available': isAvailable,
		})
	});
	return optionsToReturn;
}

module.exports = {
	'filterAvailableOptions': filterAvailableOptions
}