var gapi = require('./googleapi');
var config = require('./config');


function _pullSingleTabFromSheet(successCallback, tabId) {
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
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.longCopy)
}

function _convertLongCopyToJson(rawObject){
    let headers = rawObject.shift();
    let rows = rawObject.map(_convertLongCopyRow);
    let copyObject = {};
    rows.forEach(function(rowArray) {
      copyObject[rowArray[0]] = rowArray[1];
    });
    return copyObject;
}

function _convertLongCopyRow(rawRow){
    const keys = ['title', 'description', 'cost', 'common', 'info_link'];
    let type = rawRow.shift();
    let rowJson = {};
    rawRow.forEach((value, index) => {
      rowJson[keys[index]] = value;
    })
    return [type, rowJson];
}

function _pullProcedureOptions(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.procedureOptions)
}

function _pullAgeWarning(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.ageWarning)
}



module.exports = {
    pullLongCopy : function(successCallback){
        _pullLongCopy(successCallback);
    },
    convertLongCopyToJson: _convertLongCopyToJson,
    pullProcedureOptions: function(successCallback){
      _pullProcedureOptions(successCallback);
    },
    pullAgeWarning: function(successCallback){
      _pullAgeWarning(successCallback);
    }
};
