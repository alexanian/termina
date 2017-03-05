"use strict";
var gapi = require('./googleapi');
var config = require('./config');
var globalData = require('./globaldata');


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

var tempPrintOut = function(rows){
    if (rows.length == 0) {
            console.log('No data found.');
        } else {
            console.log('1st column, 2nd column');
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                // Print columns A and B, which correspond to indices 0 and 1.
                console.log('%s, %s', row[0], row[1]);
            }
        }
};

function _pullAndStoreLongCopy(){
    _pullLongCopy(
        (response) => {
            console.log("--- Pulled Long Text Copy ---");
            var rows = response.values;
            tempPrintOut(rows);
            globalData.optionCopy = _convertLongCopyToJson(rows);
        }
    );
};


function _pullProcedureOptions(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.procedureOptions)
}

function _pullAndStoreProcedureOptions() {
    _pullProcedureOptions((response) => {
          console.log("--- Pulled Procedure Option Data ---");
        
          var rows = response.values;
          rows.shift(); //first line is header line - toss it

          globalData.options = rows.map(
              (curr, ix) => {   
                var cleanedState = curr[3].replace(/\s/g,'');
                var singleOption = {
                    optionId: ix,
                    type: curr[0],
                    min_days_lmp: curr[1],
                    max_days_lmp: curr[2],
                    state: cleanedState.split(',')
                };
                return singleOption;  
              }
          );
          console.log("Pulled this many procedure options:" + globalData.options.length);
      })
}

function _pullParentalConsent(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.parentalConsent)
}

function _pullAndStoreParentalConsent() {
    _pullParentalConsent((response) => {
          console.log("--- Pulled Parental Consent Option Data ---");
    
          var rows = response.values;
          rows.shift(); //first line is header line - toss it

          globalData.parentalConsentRules = rows.map(
              (curr, ix) => {   
                return {
                    state: curr[0],
                    exists: (curr[1] === 'T')?true:false,
                    up_to_age: ( Number.isInteger(parseInt(curr[2])) ) ? parseInt(curr[2]) : null, 
                    consent_type: curr[3],
                    display_text: curr[4]
                };
              }
          );
          console.log("Pulled this many parental consent rules:" + globalData.parentalConsentRules.length);
      })
}

module.exports = {
    pullAndStoreLongCopy: _pullAndStoreLongCopy,
    pullAndStoreProcedureOptions: _pullAndStoreProcedureOptions,
    pullAndStoreParentalConsent: _pullAndStoreParentalConsent
};
