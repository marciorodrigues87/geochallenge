'use strict';

var request = require('request');
var sprintf = require("sprintf-js").sprintf

var backend_url = process.env.BACKEND_URL || 'http://backend:8080';

var exports = module.exports = {};

exports.signup = function(email, callback) {
  request.post({
  	url: sprintf('%s/v1/signups', backend_url),
  	method: 'POST',
  	headers: {
  		'ContentType' : 'application/json'
  	},
  	json: {
  		email : email
  	}
  }, callback);
};