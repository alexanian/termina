var sheetprocessing = require('./sheetprocessing');


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

sheetprocessing.pullLongCopy(
    (response) => {
        console.log("--- Pulled Long Text Copy ---");
        var rows = response.values;
        // tempPrintOut(rows);
    }
);

sheetprocessing.pullProcedureOptions(
    (response) => {
        console.log("--- Pulled Procedure Option Data ---");
        var rows = response.values;
        // tempPrintOut(rows);
    }
);

sheetprocessing.pullAgeWarning(
    (response) => {
        console.log("--- Pulled Age Warning Data ---");
        var rows = response.values;
        // tempPrintOut(rows);
    }
);