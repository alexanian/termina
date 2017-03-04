require('./index');
var express = require('express');
var app = express();
var globalData = require('./globaldata');
var url = require('url');

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.get('/options', function(req, res) {
	var fakeProcedureOptions = [
		{
			'id': 'medical_1',
			'type': 'medical',
			'min_pregnancy_length': 0,
			'max_pregnancy_length': 70,
			'states': 'ALL',
		},
		{
			'id': 'surgical_1',
			'type': 'surgical',
			'min_pregnancy_length': 0,
			'max_pregnancy_length': 140,
			'states': 'ALL',
		},
	]

	var url_parts = url.parse(req.url, true);
	var query = url_parts.query;
	var userAge = parseInt(query.age);
	var userState = query.state;
	var userDaysSince = parseInt(query.days_since)

	var optionsToReturn = [];

	var allTypes = fakeProcedureOptions.map(function(x) { return x.type});

	var optionIsAvailable = function(option, userState, userDaysSince) {
		var availableDueToState = (option.states === 'ALL' || option.states.indexOf(userState) !== -1);
		var availableDueToDaysSince = (option.min_pregnancy_length <= userDaysSince && userDaysSince <= option.max_pregnancy_length);
		return availableDueToState && availableDueToDaysSince;
	}

	var optionsToReturn = [];
	allTypes.forEach(function(type) {
		var optionsForType = fakeProcedureOptions.filter(function(option) { return option.type == type});
		var availableOptions = optionsForType.filter(function(option) { return optionIsAvailable(option, userState, userDaysSince)});
		console.log(availableOptions);

		var isAvailable = (availableOptions.length >= 1);

		if (isAvailable) {
			optionData = availableOptions[0];
		} else {
			optionData = optionsForType[0];
		}
		optionsToReturn.push({
			'type': optionData.type,
			'min': optionData.min_pregnancy_length,
			'maxPregnancyLength': optionData.max_pregnancy_length,
			'available': isAvailable,
		})
	});
	res.send(optionsToReturn);
})

app.get('/options/copy', function(req, res) {
  res.send(globalData.optionCopy);
})


app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
})
