/*
 * Envjs style Rhino Console (pure JS browser console)
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * parts from env.rhino.1.2.js
 * Copyright (c) 2008-2010 John Resig and the Envjs Team, under the MIT License.
 * 
 * borrowed 99%-ish with love from firebug-lite
 * http://wiki.commonjs.org/wiki/Console
 */
if (typeof(console) === 'undefined') {
    Console = function(module, level) {
        var logger = {
    
            log : function(message) {
                if (message === "Failed.") {
                    Packages.java.lang.System.err.println("[" + module + "] " + message);
                } else {
                    print("[" + module + "] " + message);
                }
            },
    
            debug : function(message) {
                print("[" + module + "] DEBUG " + message);
            },
            info : function(message) {
                print("[" + module + "] INFO " + message);
            },
            warn : function(message) {
                print("[" + module + "] WARN " + message);
            },
            error : function(message) {
                Packages.java.lang.System.err.println("[" + module + "] ERROR " + message);
            }
        };
    
        return logger;
    };
    
    this.console = new Console("jasmine", 1);
}
