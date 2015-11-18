// Karma configuration
// Generated on Tue Jun 03 2014 11:44:37 GMT-0400 (Eastern Daylight Time)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '../',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine', 'requirejs'],


        // list of files / patterns to load in the browser
        files: [
            'js/libs/require.js',
            'js/libs/angular.min.js',
            'js/libs/angular-resource.min.js',
            'js/libs/angular-mocks.js',
            'js/libs/jquery-1.11.0.min.js',
            'js/libs/jquery-ui-1.8.16.custom.min.js',
            'js/libs/jasmine-jquery.js',
            'js/libs/jasmine.async.js',
            'js/libs/moment.js',
            'js/common/*.js',
            'js/filters.js',
            //'js/testServices.js',
            'js/services.js',
            'js/app.js',
            //'js/services.js',
            'js/controllers/*.js',
            'test/common/*.js',
            'test/*.js',
            //'test/controllers/*.js'
        ],


        // list of files to exclude
        exclude: [
        ],


        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
        },


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress'],


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['Chrome'],
        //browsers: ['PhantomJS'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false
    });
};
