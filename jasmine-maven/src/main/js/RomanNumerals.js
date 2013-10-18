// Taken from http://hg.code-cop.org/js-katas/src/tip/src/pair/romannumerals/a20130423/RomanNumerals.js
// Thank you, Code Cop

RomanNumerals = function() {
    this.decimalByLiteral = {
        'M' : 1000,
        'D' : 500,
        'C' : 100,
        'L' : 50,
        'X' : 10,
        'V' : 5,
        'I' : 1
    };
};

RomanNumerals.prototype.lookup = function(romanLiteral) {
    var decimal = this.decimalByLiteral[romanLiteral];
    if (typeof (decimal) === 'undefined') {
        throw {
            name : "Error",
            message : "unknown Roman number literal " + romanLiteral
        };
    }
    return decimal;
};

RomanNumerals.prototype.romanToDecimal = function(roman) {
    var i, current = 1000, previous, sum = 0;
    for (i = 0; i < roman.length; i += 1) {
        previous = current;
        current = this.lookup(roman[i]);
        if (current > previous) {
            sum = sum - previous + (current - previous);
        } else {
            sum += current;
        }
    }
    return sum;
};
