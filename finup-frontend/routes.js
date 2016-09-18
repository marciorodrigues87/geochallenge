'use strict';

// modules
var express = require('express');
var morgan = require('morgan');
var body_parser = require('body-parser');
var method_override = require('method-override');
var sprintf = require("sprintf-js").sprintf
var backend = require('./backend.js');

// init express
var app = express();
var port = process.env.PORT || 8081;
app.use(express.static(__dirname + '/public'));
app.use(morgan('dev'));
app.use(body_parser.urlencoded({'extended':'true'}));
app.use(body_parser.json());
app.use(method_override());

// routes
app.post('/v1/signups', function(req, res) {

	console.log(sprintf('receiving signup %s', req.body.email));

	backend.signup(req.body.email, function(error, response, body) {
  		if (error) {
  			console.log(sprintf('error calling backend for signup %s', error))
  			res.status(500).send(error.code);
  		} else {
			res.status(response.statusCode).send(body);
  		}
  	});
});

app.post('/v1/surveys', function(req, res) {

	console.log(sprintf('receiving survey %s', req.body.key));

	backend.survey(req.body, function(error, response, body) {
  		if (error) {
  			console.log(sprintf('error calling backend for survey %s', error))
  			res.status(500).send(error.code);
  		} else {
			res.status(response.statusCode).send(body);
  		}
  	});
});

// start app
app.listen(port);