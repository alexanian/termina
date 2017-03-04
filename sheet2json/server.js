require('./index');
var express = require('express');
var app = express();
var globalData = require('./globaldata');

app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.get('/options', function(req, res) {
	res.send(procedureOptions);

})

app.get('/options/copy', function(req, res) {
  res.send(globalData.optionCopy);
})


app.listen(3000, function () {
  console.log('Example app listening on port 3000!');
})
