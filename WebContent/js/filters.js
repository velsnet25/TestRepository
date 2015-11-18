/*jslint browser: true*/
/*globals define, app, $, confirm*/

/**
* commonFilters used to extend Angular UI display functions
*/
angular.module('commonFilters', [])
     /**
    * Checkmark filter translates 'Y' values into a unicode checkmark to avoid needing to bind checkboxes that are not editable.
    *      Currently only 'Y' is supported. If other values need to be supported, consider adding an additional input parameter for 
    *      CheckedValue.
    * @param {string} input input value ('Y' or 'N')
    * @param {string} optional checked value. If supplied, item will be checked when the input value equals the supplied checked value.
    * @return {string}
    * @example
    * // returns check if value = 'Y'
    * {{ 'Y' | checkmark}}
    * // returns empty string
    * {{ 'N' | checkmark}}
    */
    .filter('checkmark', function () {
        return function (input, checkedValue) {
            var i = 0;

            if (!checkedValue) {
                checkedValue = ['Y'];
            }
            if (!$.isArray(checkedValue)) {
                checkedValue = [checkedValue];
            }

            //        return (input === 'Y') ? '\u2713' : '\u2718';

            for (i; i < checkedValue.length; i++) {
                if (checkedValue[i] === input) {
                    return '\u2713';
                }
            }
            return ' ';
        };
    })
    /**
    * Returns a unique list of values based on an input array
    * @param {array} input array of values
    * @param {object} key Key value to distinct the input array upon.
    * @return {array}
    */
    .filter('unique', function () {
        return function (input, key) {
            var i = 0;
            var unique = {};
            var uniqueList = [];
            if (input !== undefined) {
                for (i; i < input.length; i++) {
                    if (unique[input[i][key]] === undefined) {
                        unique[input[i][key]] = "";
                        uniqueList.push(input[i]);
                    }
                }
            }
            return uniqueList;
        };
    })
    /**
    * Returns a date from the input string or null if the value is not a date
    * @param {string} input string
    * @return {date}
    */
    .filter("asDate", function () {
        function parseISO8601(d) {
            // var isoExp = /^(\d{4})-(\d\d)-(\d\d)/,
            //     date = new Date(NaN),
            //     month,
            //     parts = isoExp.exec(dateStringInRange);

            // if (parts) {
            //     month = +parts[2];
            //     date.setFullYear(parts[1], month - 1, parts[3]);
            //     if (month !== date.getMonth() + 1) {
            //         date.setTime(NaN);
            //     }
            //     return date;
            // }
            // return null;
            if (d !== undefined && d !== null) {
                return d.replace(
                    /^(?:\d\d\d\d-\d\d-\d\dT)?\d\d:\d\d(?::\d\d(?:\.\d+)?)?$/,
                    function ($0) {
                        var offset = (new Date()).getTimezoneOffset(),
                            hours = Math.floor(Math.abs(offset) / 60),
                            minutes = Math.abs(offset) % 60,
                            sign = offset <= 0 ? '+' : '-',
                            tz = (sign + (hours * 100 + minutes)).replace(/^([-+])(\d\d\d)$/, '$10$2');
                        return $0 + tz;
                    }
                );
            }
        }

        return function (input) {

            if (input === undefined) {
                return null;
            }
            var result = moment(input).toDate();
            //var result = new Date(input);
            if (isNaN(result)) {
                // It is possible that IE didn't parse the date correctly. Try using a regex:
                return parseISO8601(input);
            }
            return result;
        };
    });