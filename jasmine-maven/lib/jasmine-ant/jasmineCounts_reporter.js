/*
 * Jasmine reporter to count number of executed tests and messages of failed ones.
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * Derived from jasmine.console_reporter.js 
 * https://github.com/larrymyers/jasmine-reporters
 * Copyright (c) 2010 Larry Myers, under the MIT License.
 */
(function() {
    if (!jasmine) {
        throw new Exception('jasmine library does not exist in global namespace!');
    }

    var CountsReporter = function() {
    };

    CountsReporter.prototype = {
        reportRunnerStarting : function(runner) {
            this.executed_specs = 0;
            this.failing_tests = [];
        },

        reportRunnerResults : function(runner) {
        },

        reportSuiteResults : function(suite) {
        },

        reportSpecStarting : function(spec) {
            this.executed_specs += 1;
        },

        reportSpecResults : function(spec) {
            if (!spec.results().passed()) {
                var failed_results, items, i;

                failed_results = [];
                items = spec.results().getItems();

                for (i = 0; i < items.length; i += 1) {
                    if (!items[i].passed()) {
                        failed_results.push(items[i].message);
                    }
                }
                this.failing_tests.push(spec.suite.description + ' : ' + spec.description + ', ' + failed_results);
            }
        },

        log : function(str) {
        },

        testCount : function() {
            return this.executed_specs;
        },

        failingTestNames : function() {
            return this.failing_tests;
        }

    };

    // export public
    jasmine.CountsReporter = CountsReporter;
})();
