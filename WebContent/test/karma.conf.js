module.exports = function(config) {
  config.set({
    basePath: '../',
    // list of files / patterns to load in the browser
    files: [
      'js/libs/require.js',
      'js/libs/angular.min.js',
      'js/libs/angular-resource.min.js',
      'js/libs/angular-mocks.js',
      'js/libs/jquery-1.11.0.min.js',
      'js/libs/jasmine-jquery.js',
      'js/common/*.js',
      'js/controllers/*.js',
      'test/common/*.js'
    ],
    singleRun: false,
    autoWatch: true,
    frameworks: ['jasmine'],
    browsers : ['PhantomJS'],
    plugins: [
      'karma-jasmine',
      // 'karma-chrome-launcher',
      'karma-phantomjs-launcher',
      'karma-coverage',
    ],
    logLevel: config.LOG_DEBUG,
    // coverage reporter generates the coverage
    reporters: ['progress', 'coverage'],
    coverageReporter : {
      type : 'html',
      dir : 'test/coverage/'
    },
    preprocessors: {
      // source files, that you wanna generate coverage for
      // do not include tests or libraries
      // (these files will be instrumented by Istanbul)
      '**/js/common/**/*.js': ['coverage']
    }
  });
};