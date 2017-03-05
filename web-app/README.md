# converting google sheet to JSON

Things you need to do:

Get your Google Sheet API Access set up with the wizard as laid out [here](https://developers.google.com/sheets/api/quickstart/nodejs).\
That will produce the `client_secret.json` that you need in order to run the script.


Run:

```
npm install googleapis --save
npm install google-auth-library --save
npm install express --save
npm install cors --save
```


Then run:
  `npm start`
or
  `node server.js`

When you run this the first time it will require you to go to the printed URL, then enter the token from that URL.
That's it - the OAuth is set up and you can call the APIs.
