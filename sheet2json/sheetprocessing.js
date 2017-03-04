var gapi = require('./googleapi');
var config = require('./config');


function _singleTabFromSheet(successCallback, tabId) {
  gapi.withGoogleAuth(
        (authObject) => {
            var sheets = gapi.google.sheets('v4');
            sheets.spreadsheets.values.get({
                "auth": authObject,
                "spreadsheetId": config.gapi.masterDataSheet,
                "range": "'" + tabId + "'!A1:H500" //Just pulling a large number of rows and columns here for now
            },  (err, response) => {
                if(err){
                    console.log(err);
                } else {
                  successCallback(response);
                }
            }
            );
        }
    );
}


function _pullLongCopy(successCallback) {
    _singleTabFromSheet(successCallback, config.gapi.tabs.longCopy)
}

function _pullProcedureOptions(successCallback) {
    _singleTabFromSheet(successCallback, config.gapi.tabs.procedureOptions)
}

function _pullAgeWarning(successCallback) {
    _singleTabFromSheet(successCallback, config.gapi.tabs.ageWarning)
}
    
module.exports = {
    pullLongCopy : function(successCallback){
        _pullLongCopy(successCallback);
    },
    pullProcedureOptions: function(successCallback){
      _pullProcedureOptions(successCallback);
    },
    pullAgeWarning: function(successCallback){
      _pullAgeWarning(successCallback);
    }
};