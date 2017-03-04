var express = require('express');
var pullSheets = require('./index.js');
var app = express()
var sheetprocessing = require('./sheetprocessing');

var procedureOptions = [];
sheetprocessing.pullProcedureOptions(
    (response) => {
        console.log("--- Pulled Long Text Copy ---");
        var rows = response.values;
        procedureOptions = rows;
    }
);


app.get('/', function (req, res) {
  res.send('Hello World!')
})

app.get('/options', function(req, res) {
	res.send(procedureOptions);

})


app.listen(3000, function () {
	
  console.log('Example app listening on port 3000!');
})