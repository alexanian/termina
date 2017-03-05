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
}


function _pullAndStoreLongCopy(){
    _pullLongCopy(
        (response) => {
            console.log("--- Pulled Option Copy ---");


            var rows = response.values;
            rows.shift(); //first line is header line

            let copyObject = {};
            let i = 0;
            var result = rows.map(
                (curr, ix) => {
                    let type = curr.shift();
                    let row = {
                        type: curr[0],
                        description: curr[1],
                        cost: curr[2],
                        common: curr[3],
                        info_link: curr[4]
                    };
                    copyObject[type] = row;
                    i++;
                }
            )

            globalData.optionCopy = copyObject;
            console.log("Pulled this many pieces of option copy:" + i);
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
