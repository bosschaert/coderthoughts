// Taken from http://hg.code-cop.org/js-katas/src/tip/src/pair/romannumerals/a20130423/RomanNumeralsSpec.js
// Thank you, Code Cop

// idea for algorithm:
// * itertate from left to right and count M,D,C,L,X,V,I
// * special cases IV, IX, XL, XC, CM, MD
// * error handling for wrong numbers

Load.file('src/main/js/RomanNumerals.js');

describe('RomanNumerals', function() {

    var romanNumerals;
    beforeEach(function() {
        romanNumerals = new RomanNumerals();
    });

    it('should translate I', function() {
        expect(romanNumerals.romanToDecimal('I')).toEqual(1);
        // setup
    });

    it('should translate II', function() {
        expect(romanNumerals.romanToDecimal('II')).toEqual(2);
        // loop
    });

    it('should translate VI', function() {
        expect(romanNumerals.romanToDecimal('VI')).toEqual(6);
        // if in loop
    });

    it('should translate MDCXL', function() {
        expect(romanNumerals.romanToDecimal('MDCLX')).toEqual(1660);
        // add remaining, use map
    });

    it('should not translate wrong literals', function() {
        expect(function() {
            romanNumerals.romanToDecimal('Rudi');
        }).toThrow("unknown Roman number literal R");
        // add check
    });

    it('should translate IV', function() {
        expect(romanNumerals.romanToDecimal('IV')).toEqual(4);
        // add current and previous
    });

    it('should translate all special cases', function() {
        expect(romanNumerals.romanToDecimal('CMXCIX')).toEqual(999);
        // works as is, make class
    });

});

execJasmine();
