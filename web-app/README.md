# converting google sheet to JSON

Things you need to do:

GET YOUR CLIENT SECRET
Get your Google Sheet API Access set up with the wizard as laid out [here](https://developers.google.com/sheets/api/quickstart/nodejs).\
That will produce the `client_secret.json` that you need in order to run the script.


RUN THE APP
Run: `npm install`
Then run: `npm start` or `node server.js`

When you run this the first time it will require you to go to the printed URL, then enter the token from that URL.
That's it - the OAuth is set up and you can call the APIs.


SETTING UP HEROKU

Install Heroku Toolbelt (via homebrew or apt-get or direct install)

Login via the command line:
  `heroku login`

If you haven't already created an app on heroku you'll need to either create a
new app via the web ui or the command line:
  `heroku create`

Once heroku has created the app they'll provide a git remote location
like `https://git.heroku.com/tranquil-retreat-70199.git` that you'll need
to add with either `heroku git:remote -a <project name>` (e.g. `heroku
git:remote -a tranqui-retreat-70199`) or `git remote add heroku <git remote>`
(e.g. `git remote add heroku https://git.heroku.com/tranquil-retreat-70199.git`)

The last thing you'll need to do before deploying the app is populating some
of the app environment variables to cover some of the client secrets and credentials
provided to us by google since we don't want to check them in to the repo. To do
this you'll need to have generated the `client_secret.json` and logged in via
OAuth which will create a `./credentials` folder in your usr HOME directory with
a json file in it corresponding to google sheets. Each of these json files
contain key value pairs that we'll need to setup on the app. You can add
environment vars for a heroku app via the web ui or the commandline
(instructions here: `https://devcenter.heroku.com/articles/config-vars`). The
vars that we'll need to setup are:
  From the credentials json:
    `access_token`,
    `refresh_token`,
    `token_type`,
    `expiry_date`
  From the client_secret.json:
    `client_id`,
    `project_id`,
    `auth_uri`,
    `token_uri`,
    `auth_provider_x509_cert_url`,
    `client_secret`,
    `redirect_uris`

For each of these keys you'll need to create an environment variable in Heroku
with the same name as they key and with the value the same without any
whitespace or quotes.
** NOTE for `redirect_uris` the array of values should be passed in as a
comma separated list

Once all of this is setup you're ready to deploy.


DEPLOY TO HEROKU (current app)

Login:
  `heroku login`

Make sure you have the heroku remote added to git:
  `git remote add heroku https://git.heroku.com/tranquil-retreat-70199.git`

From the root directory push the web-app directory:
  `git subtree push --prefix web-app heroku master`
