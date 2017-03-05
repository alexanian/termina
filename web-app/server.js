require('./index');
var express = require('express');
var cors = require('cors');
var app = express();
var globalData = require('./globaldata');
var url = require('url');
var bizLogic = require('./bizlogic');

app.use(cors());
app.use(express.static('public'));

app.get('/options', function(req, res) {

	// Parse Query
	var baseOptions = globalData.options;
  var parentalRules = globalData.parentalConsentRules;
	console.log('global data');
	console.log(baseOptions)
	var url_parts = url.parse(req.url, true);
	var query = url_parts.query;
	var userAge = parseInt(query.age);
	var userState = query.state;
	var userDaysSince = parseInt(query.days_since);

	// Array of options to return to the user
	var optionsToReturn = bizLogic.filterAvailableOptions(baseOptions, userState, userDaysSince);
	var parentalRule = bizLogic.filterParentalRules(parentalRules, userState, userAge);
	res.send({
		'options': optionsToReturn,
		'age_warning': parentalRule
	});
})

app.get('/options/copy', function(req, res) {
  res.send(globalData.optionCopy);
})

app.get('/parentalConsent', function(req, res) {
  res.send(globalData.parentalConsentRules);
})

app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
})
