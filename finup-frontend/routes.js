'use strict';

// modules
var express = require('express');
var body_parser = require('body-parser');
var method_override = require('method-override');
var sprintf = require("sprintf-js").sprintf;
var log4js = require('log4js');
log4js.configure({
 appenders: [
   {  'type': 'console',
      'category': 'default'
   },
   {
      'host': 'log',
      'port': 9999,
      'type': 'logstashUDP',
      'layout': {
        'type': 'pattern',
        'pattern': '%m'
      },
      'category': 'default'
    }
  ]
});
var logger = log4js.getLogger('default');
var backend = require('./backend.js');

// init express
var app = express();
var port = process.env.PORT || 8081;
app.use(express.static(__dirname + '/public'));
app.use(body_parser.urlencoded({'extended':'true'}));
app.use(body_parser.json());
app.use(method_override());
app.use(log4js.connectLogger(logger, { level: log4js.levels.INFO }));

// routes
app.post('/v1/signups', function(req, res) {

	logger.info(sprintf('receiving signup %s', req.body.email));

	backend.signup(req.body.email, function(error, response, body) {
  		if (error) {
  			logger.error(sprintf('error calling backend for signup %s', error))
  			res.status(500).send(error.code);
  		} else {
        res.status(response.statusCode).send(body);
  		}
  	});
});

app.post('/v1/surveys', function(req, res) {

	logger.info(sprintf('receiving survey %s', req.body.key));

	backend.survey(req.body, function(error, response, body) {
  		if (error) {
  			logger.error(sprintf('error calling backend for survey %s', error))
  			res.status(500).send(error.code);
  		} else {
        res.status(response.statusCode).send(body);
  		}
  	});
});

// start app
app.listen(port);