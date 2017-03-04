var gapi = require('./googleapi');
var config = require('./config');



    gapi.withGoogleAuth(
        (authObject) => {
            var sheets = gapi.google.sheets('v4');
            sheets.spreadsheets.values.get({
                "auth": authObject,
                "spreadsheetId": config.gapi.masterDataSheet,
                "range": "'Procedure options logic'!A1:F500"
            },  (err, response) => {
                if(err){
                    console.log(err);
                } else {
                    var rows = response.values;
                    if (rows.length == 0) {
                    console.log('No data found.');
                    } else {
                    console.log('Result, Your Deadline Is::');
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        // Print columns A and E, which correspond to indices 0 and 4.
                        console.log('%s, %s', row[0], row[2]);
                    }
                    }
                    console.log(response);
                }
            }
            );
        }
    );
