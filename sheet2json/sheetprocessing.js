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

function _convertLongCopyToJson(){
  
}

function _pullProcedureOptions(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.procedureOptions)
}

function _getAndStoreProcedureOptions() {
    _pullSingleTabFromSheet((response) => {
          console.log("--- Pulled Procedure Option Data ---");
          console.log("Pulled this many procedure options:" + response.values.length);
          
          var optionsList = [];
          var rows = response.values;
          
          var createOptionsObject = function(optionId, type, min_days_lmp, max_days_lmp, state){
              var cleanedState = state.replace(/\s/g,'');
              var singleOption = {
                  optionId: optionId,
                  type: type,
                  min_days_lmp: min_days_lmp,
                  max_days_lmp: max_days_lmp,
                  state: cleanedState.split(',')
              };
              return singleOption;
          };
          // Structure in the google sheet is
          // type | Min preg length (days LMP) | Max preg length (days LMP) | state
          for (var i = 0; i < rows.length; i++) {
              var row = rows[i];
              optionsList.push(
                  createOptionsObject(
                      i,
                      row[0],
                      row[1],
                      row[2],
                      row[3]
                  )
              );
          }
          globalData.options = optionsList;
          console.log(globalData.options);
      }, 
      config.gapi.tabs.procedureOptions
    )
}

function _pullAgeWarning(successCallback) {
    _pullSingleTabFromSheet(successCallback, config.gapi.tabs.ageWarning)
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