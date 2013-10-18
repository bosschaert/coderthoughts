/*
 * Provide missing globals for Jasmine when running under Rhino.
 * https://github.com/activelylazy/Rescripter
 * Copyright (c) 2011, David Green, under the GPL License.
 */
(function(global) {
    
    // timer functions from global for "jasmine.Clock"
    var timer = new java.util.Timer();
    var counter = 1;
    var ids = {};

    global.setTimeout = function(fn, delay) {
        var id = counter;
        counter += 1;
        ids[id] = new JavaAdapter(java.util.TimerTask, { run : fn });
        timer.schedule(ids[id], delay);
        return id;
    };

    global.clearTimeout = function(id) {
        ids[id].cancel();
        timer.purge();
        delete ids[id];
    };

    global.setInterval = function(fn, delay) {
        var id = counter;
        counter += 1;
        ids[id] = new JavaAdapter(java.util.TimerTask, { run : fn });
        timer.schedule(ids[id], delay, delay);
        return id;
    };

    global.clearInterval = global.clearTimeout;

    // exports object in case of "isCommonJS"
    global.exports = {};

})(this);
