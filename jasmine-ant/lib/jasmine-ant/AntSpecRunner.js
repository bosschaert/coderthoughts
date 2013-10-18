/*
 * Load Jasmine code as global to execute in Rhino using Ant.
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * Derived from SpecRunner.html 
 * https://github.com/pivotal/jasmine
 * Copyright (c) 2008-2011 Pivotal Labs, under the MIT License.
 */

// load global timers
Load.file('lib/jasmine-1.3.1/jasmine-rhino.js');

// load Jasmine core
Load.file('lib/jasmine-1.3.1/jasmine.js');

// make Jasmine's exports global
for (var e in exports) {
    this[e] = exports[e];
}

// change api reporter to be reuseable
jasmine.JsApiReporter.prototype.reset = function() {
    this.started = false;
    this.finished = false;
    this.suites_ = [];
    this.results_ = {};
};

// load primitive commonJS console
Load.file('lib/jasmine-reporters-0.2.1/env.rhino-ant-console.js');

// prepare the test report location

// load JUnit XML reporter
Load.file('lib/jasmine-reporters-0.2.1/jasmine.junit_reporter.js');
jasmine.getEnv().addReporter(new jasmine.JUnitXmlReporter('test-reports/'));

// add global api reporter
var apiReporter = new jasmine.JsApiReporter();
jasmine.getEnv().addReporter(apiReporter);

// add global counting reporter
Load.file('lib/jasmine-ant/jasmineCounts_reporter.js');
jasmine.getEnv().countsReporter = new jasmine.CountsReporter();
jasmine.getEnv().addReporter(jasmine.getEnv().countsReporter);

this.execJasmine = function() {
    var jasmineEnv = jasmine.getEnv();
    jasmineEnv.updateInterval = 100;

    apiReporter.reset();

    jasmineEnv.execute();

    while (!apiReporter.finished) {
        java.lang.Thread.sleep(100);
    }
};
