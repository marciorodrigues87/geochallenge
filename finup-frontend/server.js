'use strict';

var app = require('./routes');

process.on('SIGTERM', function() {
        console.log("exiting...");
        process.exit(0);

}).on('SIGINT', function() {
        console.log("exiting...");
        process.exit(0);
});