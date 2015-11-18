/*jslint browser: true*/
/*globals describe, it, expect, beforeEach, inject*/

describe('commonFilters', function () {
    var checked = '\u2713';

    beforeEach(module('commonFilters'));

    //checkmark filter tests
    describe('checkmark', function () {
        var checkmark;
        beforeEach(function () {
            inject(function (checkmarkFilter) {
                checkmark = checkmarkFilter;
            });
        });

        it('should be checked if Y supplied', function () {
            expect(checkmark('Y')).toEqual(checked);
        });
        it('should be blank if default not supplied', function () {
            expect(checkmark('N')).not.toEqual(checked);
        });
        it('should be checked if equal to expected checked value', function () {
            expect(checkmark('foo', 'foo')).toEqual(checked);
        });
        it('should be checked if equal to any item in array of expected values', function () {
            expect(checkmark('foo', ['foo', 'bar'])).toEqual(checked);
        });
        it('should bot be checked if not in item in array of expected values', function () {
            expect(checkmark('foo', ['x', 'y', 'z'])).not.toEqual(checked);
        });
    });

    describe('unique', function () {
        var uniqueFilter;

        beforeEach(function () {
            inject(function (_uniqueFilter_) {
                uniqueFilter = _uniqueFilter_;
            });
        });

        it('should remove non-unique items', function () {
            var source = [
                { key: 1, value: '1' },
                { key: 1, value: '1a' },
                { key: 2, value: '2' }
            ];
            var filtered = uniqueFilter(source, 'key');
            expect(filtered.length).toBe(2);
        });
    });

    describe('asDate', function () {
        var dateFilter;
        beforeEach(inject(function (_asDateFilter_) {
            dateFilter = _asDateFilter_;
        }));

        it('should return date if valid input supplied', function () {
            expect(dateFilter('2000/01/01').toString()).toEqual(new Date(2000, 0, 1).toString());
            expect(dateFilter('2000-01-01').toString()).toEqual(new Date(2000, 0, 1).toString());
            expect(dateFilter('2014-01-27 16:28:36.413402').toString()).toEqual(new Date(2014, 0, 27, 16, 28, 36.413402).toString());
            expect(dateFilter('2013-10-07T13:40:58.120').toString()).toEqual(new Date(2013, 9, 7, 13, 40, 58.120).toString());
            expect(dateFilter('2014-05-07 14:17:38.323').toString()).toEqual(new Date(2014, 4, 7, 14, 17, 38.323).toString());
        });
        it('should return input if invalid input supplied', function () {
            expect(dateFilter('')).toEqual('');
            expect(dateFilter()).toBeNull();
            expect(dateFilter('invalid entry')).toEqual('invalid entry');
        });
    });
});