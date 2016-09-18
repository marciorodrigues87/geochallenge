'use strict';

// modules
var express = require('express');
var morgan = require('morgan');
var body_parser = require('body-parser');
var method_override = require('method-override');
var sprintf = require("sprintf-js").sprintf
var request = require('request');

// init express
var app = express();
var port = process.env.PORT || 8000;
app.use(express.static(__dirname + '/public'));
app.use(morgan('dev'));
app.use(body_parser.urlencoded({'extended':'true'}));
app.use(body_parser.json());
app.use(method_override());

// routes
app.post('/v1/signups', function(req, res) {

	console.log(req.body);
	// JAVAAAAA
	res.status(200).send("OK");

});

app.post('/v1/surveys', function(req, res) {

	console.log(req.body);
	// JAVAAAAA
	res.status(200).send("OK");

});

// start app
app.listen(8080);