/*
 * Load Jasmine code as global to execute in Rhino.
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * Derived from SpecRunner.html 
 * https://github.com/pivotal/jasmine
 * Copyright (c) 2008-2011 Pivotal Labs, under the MIT License.
 */
if (typeof (execJasmine) === 'undefined') {

    // load global timers
    Load.file('lib/jasmine-1.3.1/jasmine-rhino.js');

    // load Jasmine core
    Load.file('lib/jasmine-1.3.1/jasmine.js');

    // load primitive commonJS console
    Load.file('lib/jasmine-reporters-0.2.1/env.rhino-console.js');

    // load nice console reporter
    Load.file('lib/jasmine-reporters-0.2.1/jasmine.console_reporter.js');
    jasmine.getEnv().addReporter(new jasmine.ConsoleReporter());

    this.execJasmine = function() {
        var jasmineEnv, apiReporter;
        
        jasmineEnv = jasmine.getEnv();
        jasmineEnv.updateInterval = 100;

        // add api reporter
        apiReporter = new jasmine.JsApiReporter();
        jasmineEnv.addReporter(apiReporter);

        jasmineEnv.execute();

        while (!apiReporter.finished) {
            java.lang.Thread.sleep(100);
        }
    };

    console.log('Jasmine loaded.');
}
