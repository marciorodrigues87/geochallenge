'use strict';

var app = require('./routes');

var logger = require('log4js').getLogger('default');

process.on('SIGTERM', function() {
        logger.info("exiting...");
        process.exit(0);

}).on('SIGINT', function() {
        logger.info("exiting...");
        process.exit(0);
});