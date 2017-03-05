// SETUP GOOGLE API
var fs = require('fs');
var readline = require('readline');
var google = require('googleapis');
var googleAuth = require('google-auth-library');

// If modifying these scopes, delete your previously saved credentials
// at ~/.credentials/
var SCOPES = ['https://www.googleapis.com/auth/spreadsheets','https://www.googleapis.com/auth/drive'];
var TOKEN_DIR = (process.env.HOME || process.env.HOMEPATH ||
    process.env.USERPROFILE) + '/.credentials/';
var TOKEN_PATH = TOKEN_DIR + 'sheets.googleapis.aah.data.json';


var _buildClientSecret = function() {
    var env_vars_exist = process.env.client_id && process.env.project_id && process.env.auth_uri && process.env.token_uri && process.env.auth_provider_x509_cert_url && process.env.client_secret && process.env.redirect_uris;

    if (!env_vars_exist) return null;

    return {
        installed: {
            client_id: process.env.client_id,
            project_id: process.env.project_id,
            auth_uri: process.env.auth_uri,
            token_uri: process.env.token_uri,
            auth_provider_x509_cert_url: process.env.auth_provider_x509_cert_url,
            client_secret: process.env.client_secret,
            redirect_uris: process.env.redirect_uris.split(',')
        }
    }
};

var _buildCredentials = function() {
    var env_vars_exist = process.env.access_token && process.env.refresh_token && process.env.token_type && process.env.expiry_date;

    if (!env_vars_exist) return null;

    return {
        access_token: process.env.access_token,
        refresh_token: process.env.refresh_token,
        token_type: process.env.token_type,
        expiry_date: process.env.expiry_date
    }
}

/**
 * The method that will be called from the outside to perform an API request with
 * being authorzied.
 */
var _withGoogleAuth = function(callback){
    fs.exists('client_secret.json', (exists) => {
        if (exists) {
            // Load client secrets from a local file.
            fs.readFile('client_secret.json', function processClientSecrets(err, content) {
                if (err) {
                    console.log('Error loading client secret file: ' + err);
                    return;
                }
                // Authorize a client with the loaded credentials, then call the
                // Google Sheets API.
                _authorize(JSON.parse(content), function(auth){
                    callback(auth);
                });
            });
        } else {
            var secret = _buildClientSecret();
            if (secret === null) {
                console.log('Error parsing client secret envs.');
                return;
            }
            _authorize(secret, function(auth) {
                callback(auth);
            });
        }
    })
};


/**
 * Create an OAuth2 client with the given credentials, and then execute the
 * given callback function.
 *
 * @param {Object} credentials The authorization client credentials.
 * @param {function} callback The callback to call with the authorized client.
 */
function _authorize(credentials, callback) {
    var clientSecret = credentials.installed.client_secret;
    var clientId = credentials.installed.client_id;
    var redirectUrl = credentials.installed.redirect_uris[0];
    var auth = new googleAuth();
    var oauth2Client = new auth.OAuth2(clientId, clientSecret, redirectUrl);

    fs.exists(TOKEN_PATH, (exists) => {
        if (exists) {
            // Check if we have previously stored a token.
            fs.readFile(TOKEN_PATH, function(err, token) {
                if (err) {
                    _getNewToken(oauth2Client, callback);
                } else {
                    oauth2Client.credentials = JSON.parse(token);
                    callback(oauth2Client);
                }
            });
        } else {
            var token = _buildCredentials();
            if (secret === null) {
                console.log('Error parsing credentials envs.');
                return;
            }
            oauth2Client.credentials = token;
            callback(oauth2Client);
        }
    });
}

/**
 * Get and store new token after prompting for user authorization, and then
 * execute the given callback with the authorized OAuth2 client.
 *
 * @param {google.auth.OAuth2} oauth2Client The OAuth2 client to get token for.
 * @param {getEventsCallback} callback The callback to call with the authorized
 *     client.
 */
function _getNewToken(oauth2Client, callback) {
    var authUrl = oauth2Client.generateAuthUrl({
        access_type: 'offline',
        scope: SCOPES
    });
    console.log('Authorize this app by visiting this url: ', authUrl);
    var rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });
    rl.question('Enter the code from that page here: ', function(code) {
        rl.close();
        oauth2Client.getToken(code, function(err, token) {
            if (err) {
                console.log('Error while trying to retrieve access token', err);
                return;
            }
            oauth2Client.credentials = token;
            _storeToken(token);
            callback(oauth2Client);
        });
    });
}

/**
 * Store token to disk be used in later program executions.
 *
 * @param {Object} token The token to store to disk.
 */
function _storeToken(token) {
    try {
        fs.mkdirSync(TOKEN_DIR);
    } catch (err) {
        if (err.code != 'EEXIST') {
            throw err;
        }
    }
    fs.writeFile(TOKEN_PATH, JSON.stringify(token));
    console.log('Token stored to ' + TOKEN_PATH);
}

module.exports = {
    withGoogleAuth : function(callback){
        _withGoogleAuth(callback);
    },
    google : google
};
