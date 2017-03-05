require('./index');
var express = require('express');
var cors = require('cors');
var app = express();
var globalData = require('./globaldata');
var url = require('url');
var bizLogic = require('./bizlogic');

app.use(cors());

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.get('/options', function(req, res) {

	// Parse Query
	var baseOptions = globalData.options;
	console.log('global data');
	console.log(baseOptions)
	var url_parts = url.parse(req.url, true);
	var query = url_parts.query;
	var userAge = parseInt(query.age);
	var userState = query.state;
	var userDaysSince = parseInt(query.days_since)

	// Array of options to return to the user
	var optionsToReturn = bizLogic.filterAvailableOptions(baseOptions, userState, userDaysSince);
	res.send(optionsToReturn);
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
