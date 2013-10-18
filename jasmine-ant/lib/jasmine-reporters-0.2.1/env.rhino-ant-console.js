/*
 * Envjs style Rhino Console using the Ant task logger.
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * parts from env.rhino.1.2.js
 * Copyright (c) 2008-2010 John Resig and the Envjs Team, under the MIT License.
 */
if (typeof(console) === 'undefined') {
    Console = function() {
        var logger = {

            log : function(message) {
                this.info(message);
            },

            debug : function(message) {
                self.log(message, Console.MSG_DEBUG);
            },
            info : function(message) {
                self.log(message, Console.MSG_INFO);
            },
            warn : function(message) {
                self.log(message, Console.MSG_WARN);
            },
            error : function(message) {
                self.log(message, Console.MSG_ERR);
            }
        };

        return logger;
    };
    Console.MSG_ERR = 0;
    Console.MSG_WARN = 1;
    Console.MSG_INFO = 2;
    Console.MSG_VERBOSE = 3;
    Console.MSG_DEBUG = 4;

    this.console = new Console();
}
