/*
 * Jasmine Ant support
 * Copyright (c) 2011, Peter Kofler, under the BSD License.
 * 
 * Parts have originally been taken from RhinoUnit 1.2.1
 * http://code.google.com/p/rhinounit/
 * Copyright (c) 2008, Tiest Vilee.
 */
var testfailed = false;

Load = {};
/**
 * Invoke a JavaScript file in the global scope relative to this Ant task's basedir.
 * @param {String} fileName
 */
Load.file = function(fileName) {

    function absoluteFile(fileName) {
        var file = new java.io.File(fileName);
        if (file.isAbsolute() === false) {
            file = new java.io.File(project.getProperty("basedir"), fileName);
        }
        return file;
    }

    function readTextFile(file) {
        var reader = new java.io.FileReader(file);
        return '' + new String(Packages.org.apache.tools.ant.util.FileUtils.readFully(reader));
    }
    
    function evalGlobal(code) {
        (function() {
            eval(code);
        })();
    }

    var file, code;
    
    file = absoluteFile(fileName);
    code = readTextFile(file);
    evalGlobal(code);
};

var jasmineSpecRunnerPath = "src/AntSpecRunner.js";
if (attributes.get("jasminespecrunnerpath")) {
    jasmineSpecRunnerPath = attributes.get("jasminespecrunnerpath");
}
Load.file(jasmineSpecRunnerPath);

function forEachElementOf(list, doThis) {
    var listLength, i;
    listLength = list.length;
    for (i = 0; i < listLength; i+=1) {
        doThis(list[i], i);
    }
}

function error(message) {
    var errorLogLevel = 0;
    self.log(message, errorLogLevel);
}

var ignoredGlobalVars = [];
if (attributes.get("ignoredglobalvars")) {
    ignoredGlobalVars = attributes.get("ignoredglobalvars").split(" ");
}
function ignoreGlobalVariableName(name) {
    var foundVariable = false;
    forEachElementOf(ignoredGlobalVars, function(ignoredGlobalVar) {
        if (ignoredGlobalVar === name) {
            foundVariable = true;
        }
    });
    return foundVariable;
}

var haltOnFirstFailure = false;
if (attributes.get("haltonfirstfailure")) {
    haltOnFirstFailure = (attributes.get("haltonfirstfailure") == "true");
    // use == instead of === because the attribute is a java.lang.String
}

function runTest(file) {

    function failingTestMessage(testName) {
        if (options.verbose) {
            error("Failed: " + testName);
            self.log("");
        }
    }

    function erroringTestMessage(testName, e) {
        if (options.verbose) {
            error("Error: " + testName + ", Reason: " + e);
            self.log("");
        }
    }
    
    var erroringTests, testCount, failingTests;
    
    erroringTests = 0;
    try {
        Load.file(file);
    } catch (e) {
        erroringTests += 1;
        erroringTestMessage(file, e);
        testfailed = true;
    }
    
    failingTests = this.jasmine.getEnv().countsReporter.failingTestNames();
    if (failingTests.length > 0) {
        forEachElementOf(failingTests, function(testName) {
            failingTestMessage(testName);
        });
        testfailed = true;
    }

    testCount = this.jasmine.getEnv().countsReporter.testCount();
    if (testCount === 0) {
        erroringTests += 1;
        testfailed = true;
        failingTestMessage(file, "No tests defined");
    }
    
    self.log("Tests run: " + testCount + ", Failures: " + failingTests.length + ", Errors: " + erroringTests);
    self.log("");
}

var options;
eval("options = " + attributes.get("options") + ";");

var filesets = elements.get("fileset");
for (var j = 0; j < filesets.size(); j += 1) {

    var fileset = filesets.get(j);
    var directoryScanner = fileset.getDirectoryScanner(project);
    var srcFiles = directoryScanner.getIncludedFiles();

    forEachElementOf(srcFiles, function(srcFile) {
        self.log("Spec: " + srcFile);
        var jsfile = new java.io.File(fileset.getDir(project), srcFile);

        var globalVars = {};
        var varName;
        for (varName in this) {
            globalVars[varName] = true;
        }
        var classUnderTestName = srcFile.match(/([A-Za-z0-9_]+)Spec\.js$/)[1];
        globalVars[classUnderTestName] = true;
        
        runTest(jsfile);

        for (varName in this) {
            if (!globalVars[varName]) {
                if (ignoreGlobalVariableName(varName)) {
                    delete this[varName];
                } else {
                    error("Warning: " + srcFile + ", Reason: Polluted global namespace with '" + varName + "'");
                    testfailed = true;
                }
            }
        }

        if (testfailed && haltOnFirstFailure) {
            self.fail("Jasmine failed.");
        }
    });
}

if (testfailed) {
    self.fail("Jasmine failed.");
}
