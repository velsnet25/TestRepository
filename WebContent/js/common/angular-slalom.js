/*jslint browser: true*/
/*globals define, app, $, confirm*/

/**
* @class slalom.common
*/
angular.module('slalom.common', []);

/**
* @class slalom.common.browser
* @memberOf slalom.common
*/
angular.module('slalom.common.browser', [])
/**
 * Handles access to query param values
 * @namespace slalom.common.browser.QueryParamUtil
 * @memberOf slalom.common.browser
 * 
 * @param  {object} $location provides access to the browser URL
 * @return {object}           instance
 */
     .factory("QueryParamUtil", ["$location", function ($location) {
        var service = {
            /**
             * getParam Gets a value for a given key
             * @param  {string} key query string key
             * @return {string}     query string value
             */
            getParam: function (key) {
                var params = $location.search();
                if (params.hasOwnProperty(key)) { return params[key]; }
                return null;
            },
            /**
             * getParams Gets the array of parameters into a key value pair
             * @param  {array} keys array of key string values
             * @return {array}      object with string values matching the keys
             */
            getParams: function (keys) {
                var output = {};
                var key;
                for (key in keys) {
                    if (keys.hasOwnProperty(key)) {
                        output[key] = this.getParam(key);
                    }
                }
                return output;
            },
            /**
             * setParam Updates the querystring parameter and sets the location to the new value
             * @param {string} key             query string key
             * @param {string} value           new query string value
             * @param {bool} overrideHistory optional if the new value should be added to history or just replace it.
             */
            setParam: function (key, value, overrideHistory) {
                var params = $location.search();
                params[key] = value;
                $location.search(params);
                if (overrideHistory) { $location.replace(); }
            },
            /**
             * clearParam Removes a querystring parameter and sets the location to the new value
             * @param  {string} key             querystring key to remove
             * @param  {bool} overrideHistory optional if the new value should be added to history or just replace it.
             */
            clearParam: function (key, overrideHistory) {
                $location.search(key, null);
                if (overrideHistory) { $location.replace(); }
            }
        };
        return service;
    } ])

    /**
    * Handles construction of various URL's within an application
    * 
    * @namespace slalom.common.browser.UrlService
    * @memberOf slalom.common.browser
    */
    // TODO replace all the hardcoded values with constants
    .factory("UrlService", ["$window", function ($window) {
        var isReady = false;
        var observers = [];

        var notifyObservers = function () {
            var obs, scope;
            for (obs in observers) {
                if (observers.hasOwnProperty(obs)) {
                    observers[obs].method();
                    scope = observers[obs].scope;
                    if (!scope.$$phase) {
                        scope.$apply();
                    }
                }
            }
        };

        var fixPath = function (path) {
            if (path && path.indexOf('/') === 0) {
                path = path.substring(1);
            }
            return path;
        };

        //$window.root = $window.location.protocol + '//' + $window.location.host + "";
        $window.root = $window.location.origin + $window.location.pathname;

        isReady = true;

        var service = {
            /**
            * Sets the root url for the application
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method setRoot
            * @param {string}
            *            newRoot
            */
            setRoot: function (newRoot) {
                newRoot = (newRoot.lastIndexOf("/") === newRoot.length - 1) ? newRoot.substring(0, newRoot.length - 1) : newRoot;
                $window.root = newRoot;
                isReady = true;
                notifyObservers();
            },
            /**
            * Gets the root url for the application
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method root
            */
            root: function () {
                return $window.root;
            },
            /**
            * Builds a service url
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method createServiceUrl
            * @param {string}
            *            service
            * @param {string}
            *            action
            * @returns {string} The complete url to the service
            */
            createServiceUrl: function (service, action) {
                return $window.root + "/Services/" + service + "/" + action + "/";
            },
            /**
            * Builds a MVC url
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method createMvcUrl
            * @param {string}
            *            service
            * @param {string}
            *            action
            * @returns {string} The complete url
            */
            createMvcUrl: function (controller, action) {
                return $window.root + "/" + fixPath(controller) + "/" + action + "/";
            },
            /**
            * Builds a web api url
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method createWebApiUrl
            * @param {string}
            *            service
            * @param {string}
            *            action
            * @returns {string} The complete url
            */
            createWebApiUrl: function (controller, action) {
                return $window.root + "/api/" + controller + "/" + action + "/";
            },
            /**
            * Builds a url
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method createUrl
            * @param {string}   path
            * @param {string}   name
            * @returns {string} The complete url
            */
            createUrl: function (path, name) {
                if (path && name) {
                    return $window.root + "/" + fixPath(path) + "/" + name;
                }
                if (path) {
                    return $window.root + "/" + fixPath(path);
                }
                return undefined;
            },
            /**
            * Builds a url to an html template
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method createTemplateUrl
            * @param {string}
            *            folder
            * @param {string}
            *            name
            * @returns {string} The complete url
            */
            createTemplateUrl: function (folder, name) {
                if (folder && name) {
                    return $window.root + "/" + fixPath(folder) + "/" + name + ".html";
                }
                if (folder) {
                    return $window.root + "/" + fixPath(folder) + ".html";
                }
                return 'undefined';
            },
            /**
            * Adds an observer
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method addObserver
            * @param {function}
            *            callback
            * @param {object}
            *            scope
            */
            addObserver: function (callback, scope) {
                observers.push({
                    'method': callback,
                    'scope': scope
                });
            },
            /**
            * Gets the current value of isReady
            * 
            * @memberOf slalom.common.browser.UrlService
            * @method isReady
            * @returns {boolean}
            */
            isReady: function () {
                return isReady;
            }
        };

        return service;
    } ])
    /**
    * Directive: set's the root URL
    * 
    * @namespace slalom.common.browser.rootUrl
    * @memberOf slalom.common.browser
    */
    // TODO: why is this a directive?
    .directive("rootUrl", ["UrlService", function (UrlService) {
        return {
            link: function (scope, element, attrs) {
                UrlService.setRoot(scope.$eval(attrs.rootUrl));
            }
        };
    } ])
    /**
    * Directive: sets the page location to the link
    * 
    * @namespace slalom.common.browser.link
    * @memberOf slalom.common.browser
    */
    .directive("link", ["$window", "UrlService", function ($window, UrlService) {
        return {
            link: function (scope, element, attrs) {
                if (!element.hasClass('link')) {
                    element.addClass('link');
                }
                element.bind('click', function () {
                    //var temp = scope.$eval(attrs.link);
                    $window.location.href = UrlService.createUrl(scope.$eval(attrs.link));
                });
            }
        };
    } ])
    
    /**
     * start here - test
     */
    .factory('$localstorage', ['$window', function($window) {
  return {
		    set: function(key, value) {
		      $window.localStorage[key] = value;
		    },
		    get: function(key, defaultValue) {
		      return $window.localStorage[key] || defaultValue;
		    },
		    setObject: function(key, value) {
		      $window.localStorage[key] = JSON.stringify(value);
		    },
		    getObject: function(key) {
		      return JSON.parse($window.localStorage[key] || '{}');
		    }
  		};
    } ])
    
    /**
     * end here - test
     */
    /**
     * 
    * Handles CRUD operations for cookies.
    * 
    * @namespace slalom.common.browser.CookieFactory
    * @memberOf slalom.common.browser
    */
    // TODO $.cookie needs to be injected into this since it is required to function
    .factory('slalom.browser.CookieManager', [function () {

        var defaults = {
            path: '/'
        };

        return {

            /**
            * returns the cookie for the given name
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method getCookie
            * @param {string}
            *            name
            * @returns {object} Cookie object
            */
            getCookie: function (name) {
                return $.cookie(name);
            },
            /**
            * returns the cookie as a JSON for a given name
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method getCookieJSON
            * @param {string}
            *            name
            * @returns {object} Cookie object parsed
            */
            getCookieJSON: function (name) {
                var value = $.cookie(name);
                if (value !== undefined) {
                    return JSON.parse($.cookie(name));
                }
            },
            /**
            * returns all the cookies
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method getAllCookies
            * @returns {array} An array of all cookies
            */
            getAllCookies: function () {
                return $.cookie();
            },

            /**
            * set the value for a given cookie
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method setCookie
            * @returns {object}
            */
            setCookie: function (name, value, options) {
                options = $.extend({}, defaults, options);
                return $.cookie(name, value, options);
            },
            /**
            * Converts the value to a json then sets the cookie value
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method setCookieJSON
            * @returns {object}
            */
            setCookieJSON: function (name, value, options) {
                options = $.extend({}, defaults, options);
                return $.cookie(name, JSON.stringify(value), options);
            },
            /**
            * deletes a given cookie by name
            * 
            * @memberOf slalom.common.browser.CookieFactory
            * @method deleteCookie
            * @param {string}
            *            name
            * @returns {object}
            */
            deleteCookie: function (name) {
                return $.removeCookie(name, defaults);
            }
        };
    } ]);
/**
* @class slalom.common.modals
* @memberOf slalom.common
*/
angular.module('slalom.common.modals', [])
    /**
    * 
    * @namespace slalom.common.modals.ModalService
    * @memberOf slalom.common.modals
    */
    // TODO describe this service
    .factory("ModalService", ["$modal", function ($modal) {
        var modals = {};

        var service = {
            /**
             * Open the modal
             * @param  {string} modalName Name of the modal to open
             * @return {object}           modal Instance that was opened if found.
             */
            open: function (modalName) {
                if (modals[modalName]) {
                    var instance = $modal.open(modals[modalName].options);
                    modals[modalName].modal = instance;
                    return instance;
                }
                return null;
            },
            /**
             * Close the modal instance
             * @param  {string} modalName name of the modal to close
             * @param  {object} argument  optional additional arguments for closing
             */
            close: function (modalName, argument) {
                if (modals[modalName] && modals[modalName].modal) {
                    modals[modalName].modal.close(argument);
                }
            },
            /**
             * Adds the modal by name
             * @param {string} modalName    modal name
             * @param {object} modalOptions modal options
             */
            addModal: function (modalName, modalOptions) {
                modals[modalName] = {
                    options: modalOptions
                };
            }
        };
        return service;
    } ])
    /**
    * Configures Modal settings
    * 
    * @namespace slalom.common.modals.modal
    * @memberOf slalom.common.modals
    */
    .directive("modal", ["ModalService", "UrlService", function (ModalService, UrlService) {

        return {
            link: function (scope, element, attr) {
                /**
                * creates a modal in the dom
                * 
                * @memberOf slalom.common.modals.modal
                * @function setModal
                */
                function setModal() {

                    var modalOptions = {
                        templateUrl: attr.modalTemplateFolder + "/" + attr.modalTemplate + ".html",
                        controller: attr.controller,
                        backdrop: (attr.modalBackdrop) || 'true',
                        keyboard: false,
                        windowClass: (attr.windowClass) || '',
                        resolve: {}
                    },
                        i = 0,
                        resolveCount,
                        resovlveArray = [],
                        resolveCallback = function (i) {
                            return function () {
                                return scope.$eval(this.resolveArray[i]);
                            };
                        };

                    if (attr.modalResolve !== undefined && attr.modalResolve.length > 0) {
                        this.resolveArray = attr.modalResolve.split(", ");
                        resolveCount = this.resolveArray.length;

                        for (i = 0; i < resolveCount; i = i + 1) {
                            modalOptions.resolve[this.resolveArray[i]] = resolveCallback(i);
                        }

                    }

                    var modalName = attr.modal;
                    ModalService.addModal(modalName, modalOptions);
                }
                if (attr.modalTemplate && UrlService.isReady()) {
                    setModal();
                } else {
                    scope.$watch(UrlService.root(), function () {
                        setModal();
                    });
                }
            }
        };
    } ])
    /**
    * Handles the error messages
    * 
    * @memberOf slalom.common.modal
    * @namespace slalom.common.modal.ConfirmModal
    */
    .factory("ConfirmModal", ["ModalService", function (ModalService) {
        var message;
        var title;
        var modalName = "confirmModal";
        var modalInstance;
        var isHtml = true;
        var service = {
            /**
            * Shows the confirm modal
            * 
            * @memberOf slalom.common.modal.ConfirmModal
            * @function show
            * @param {string}  msg - the message to be displayed
            * @param {string}  modalTitle - value to be displayed in the title bar. Defaults to "Confirm"
            * @param {boolean} showModal - whether or not to show the error modal
            * @param {boolean} html - is the message HTML format or not
            */
            show: function (msg, modalTitle, suppress, html) {
                if (html) {
                    isHtml = html;
                }
                message = msg;
                title = modalTitle || 'Confirm';

                if (!suppress && !modalInstance) {
                    modalInstance = ModalService.open(modalName);
                }
                return modalInstance;
            },
            /**
            * Hides the modal
            * 
            * @memberOf slalom.common.modal.ConfirmModal
            * @function hide
            */
            hide: function (result) {
                if (modalInstance) {
                    ModalService.close(modalName, result);
                    modalInstance = undefined;
                }
            },
            /**
            * Returns the message
            * 
            * @memberOf slalom.common.modal.ConfirmModal
            * @function message
            * @returns {string}
            */
            message: function () {
                return message;
            },
            /**
            * Returns the title message
            *
            * @member of slalom.common.modal.ConfirmModal
            * @function title
            * @returns {string}
            */
            title: function () {
                return title;
            },
            /**
            * Returns true if the error message is HTML
            * 
            * @memberOf slalom.common.modal.ConfirmModal
            * @function isHTML
            * @returns {boolean}
            */
            isHtml: function () {
                return isHtml;
            },
            /**
            * Returns the modalName
            * 
            * @memberOf slalom.common.modal.ConfirmModal
            * @function modalName
            * @returns {string}
            */
            modalName: function () {
                return modalName;
            }
        };
        return service;
    } ])
    /**
    * Controller for Confirmation messages
    * 
    * @memberOf slalom.common.modal
    * @namespace slalom.common.modal.ConfirmCtrl
    */
    .controller("ConfirmCtrl", ["$scope", 'ConfirmModal', function ($scope, ConfirmModal) {
        $scope.message = ConfirmModal.message();
        $scope.isHtml = ConfirmModal.isHtml();
        $scope.title = ConfirmModal.title();
        $scope.okText = "Ok";
        $scope.cancelText = "Cancel";
        /**
        * Hides the error message
        * 
        * @memberOf slalom.common.errors.ErrorCtrl
        * @method Close
        * @public
        */
        $scope.Ok = function () {
            ConfirmModal.hide(true);
        };
        $scope.Cancel = function () {
            ConfirmModal.hide(false);
        };
        return $scope;
    } ]);
/**
* @memberof slalom.common
* @class slalom.common.errors
*/
angular.module('slalom.common.errors', ['slalom.common.modals'])
    /**
    * Handles the error messages
    * 
    * @memberOf slalom.common.errors 
    * @namespace slalom.common.errors.ErrorModal
    */
    .factory("ErrorModal", ["ModalService", function (ModalService) {
        var message;
        var activityName = "ErrorModal";
        var modalName = "errorMessage";
        var modalInstance;
        var isHtml = true;
        var service = {
            /**
            * Shows the error modal
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function show
            * @param {string}    msg - the message to be displayed
            * @param {boolean}   showModal - whether or not to show the error modal
            * @param {boolean}   html - is the message HTML format or not
            */
            show: function (msg, suppress, html) {
                if (html) {
                    isHtml = html;
                }
                message = msg;
                if (!suppress && !modalInstance) {
                    modalInstance = ModalService.open(modalName);
                }
            },
            /**
            * Shows the common generic system error modal
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function showSystemError
            */
            showSystemError: function () {
                this.show("A system error has occurred. Please contact the help desk for support.");
            },
            /**
            * Hides the error modal
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function hide
            */
            hide: function () {
                if (modalInstance) {
                    ModalService.close(modalName);
                    modalInstance = undefined;
                }
            },
            /**
            * Returns the error message
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function message
            * @returns {string}
            */
            message: function () {
                return message;
            },
            /**
            * Returns true if the error message is HTML
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function isHTML
            * @returns {boolean}
            */
            isHtml: function () {
                return isHtml;
            },
            /**
            * Returns the activityName
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function activityName
            * @returns {string}
            */
            activityName: function () {
                return activityName;
            },
            /**
            * Returns the modalName
            * 
            * @memberOf slalom.common.errors.ErrorModal
            * @function modalName
            * @returns {string}
            */
            modalName: function () {
                return modalName;
            },
            modalTitle: "Error"
        };
        return service;
    } ])
    /**
    * Controller for Error messages
    * 
    * @memberOf slalom.common.errors
    * @namespace slalom.common.errors.ErrorCtrl
    */
    .controller("ErrorCtrl", ["$scope", 'ErrorModal', function ($scope, ErrorModal) {
        $scope.message = ErrorModal.message();
        $scope.isHtml = ErrorModal.isHtml();
        $scope.modalTitle = ErrorModal.modalTitle;
        /**
        * Hides the error message
        * 
        * @memberOf slalom.common.errors.ErrorCtrl
        * @method Close
        * @public
        */
        $scope.Close = function () {
            ErrorModal.hide();
        };

        return $scope;
    } ])
    /**
    * This service captures any js errors within angular and passes them back to the server so they can be logged
    * 
    * @memberOf slalom.common.errors
    * @namespace slalom.common.errors.errorLogService
    */
    .factory("errorLogService", ["$log", "$window", function ($log, $window) {
        /**
        * Log the given error to the remote server.
        * 
        * @memberOf slalom.common.errors.errorLogService
        * @method log
        * @param {object}
        *            exception
        * @param {object}
        *            cause
        * @public
        */
        function log(exception, cause) {
            // Pass off the error to the default error handler
            // on the AngualrJS logger. This will output the
            // error to the console (and let the application
            // keep running normally for the user).
            $log.error.apply($log, arguments);
            // Now, we need to try and log the error the server.
            try {
                var errorMessage = exception.toString(),
                    url = "rs/DataService/setErrorMessage", 
                    errorObject = {
                        url: $window.location.href,
                        message: errorMessage,
                        cause: (cause || "")
                    };
                if (console) {
                    console.log("Custom Error handling");
                    console.log(errorObject);
                }
                // Log the JavaScript error to the server. We can't uses angular's
                // $http module because it itself can throw errors which would cause
                // a circular reference. We use jQuery.ajax to avoid this.
                $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/json",
                    data: JSON.stringify(errorObject)
                });
            } catch (loggingError) {
                // For Developers - log the log-failure.
                $log.warn("Error logging failed");
                $log.log(loggingError);
            }
        }
        // Return the logging function.
        return log;

    } ])
    /**
    * By default, AngularJS will catch errors and log them to the Console. We want to keep that behavior; however, we want
    * to intercept it so that we can also log the errors to the server for later analysis.
    * 
    * @memberOf slalom.common.errors
    * @namespace slalom.common.errors.$exceptionHandler
    */
    .factory("$exceptionHandler", ['errorLogService', function (errorLogService) {
        return errorLogService;
    } ]);

/**
* @class slalom.common.dom
* @memberOf slalom.common
*/
angular.module('slalom.common.dom', [])
    /**
    * Loads and inserts html partial templates
    * 
    * @namespace slalom.common.dom.template
    * @memberOf slalom.common.dom
    */
    .directive("template", ["$compile", '$http', '$templateCache', 'UrlService', function ($compile, $http, $templateCache, UrlService) {
        return {

            link: function (scope, element, attrs) {
                /**
                * loads a template into the dom
                * 
                * @memberOf slalom.common.dom.template
                * @function loadTemplate
                * @param {string}
                *            template
                * @param {string}
                *            folder
                */
                function loadTemplate(template, folder) {
                    // cahnge back to createTemplateUrl once the root is
                    // configurable
                    var url = (!!folder) ? UrlService.createTemplateUrl(folder, template) : UrlService.createTemplateUrl(template);
                    $http.get(url, {
                        cache: $templateCache
                    }).success(function (templateContent) {
                        element.append($compile(templateContent)(scope));
                    });
                }
                scope.$watch(attrs.template, function () {
                    if (!UrlService.isReady()) {
                        scope.$watch(UrlService.root(), function () {
                            loadTemplate(attrs.template, attrs.templateFolder);
                        });
                    } else {
                        loadTemplate(attrs.template, attrs.templateFolder);
                    }
                });

            }
        };
    } ])
    /**
    * Watches the attribute value and updates the css visibility attribute. Works just like ng-show except uses visibility
    * instead of display
    * 
    * @namespace slalom.common.dom.ngVisible
    * @memberOf slalom.common.dom
    */
    .directive('ngVisible', function () {
        return function (scope, element, attr) {
            scope.$watch(attr.ngVisible, function (visible) {
                element.css('visibility', visible ? 'visible' : 'hidden');
            });
        };
    })
    /**
    * Set a regex pattern on an input field. Doesn't allow entry of characters that don't match the regex
    * 
    * @namespace slalom.common.dom.inputPatternFilter
    * @memberOf slalom.common.dom
    */
    .directive('inputPatternFilter', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attr, ngModelCtrl) {
                /**
                * loads a template into the dom
                * 
                * @memberOf slalom.common.dom.inputPatternFilter
                * @function fromUser
                * @param {string}
                *            text
                * @returns {string}
                */
                function fromUser(text) {
                    if (text !== '' && typeof attr.ngPattern === "string" && attr.ngPattern !== '') {
                        var transformedInput = text.match(scope.$eval(attr.ngPattern));

                        if (transformedInput === null) {
                            text = '';
                            ngModelCtrl.$setViewValue(text);
                            ngModelCtrl.$render();
                        } else if (transformedInput.join("") !== text) {
                            text = transformedInput.join("");
                            ngModelCtrl.$setViewValue(text);
                            ngModelCtrl.$render();
                        }
                    }
                    return text;
                }
                ngModelCtrl.$parsers.unshift(fromUser);
            }
        };
    })
    /**
    * filters a value to localtime. Helps when server returns timezone info
    * 
    * @namespace slalom.common.dom.localtime
    * @memberOf slalom.common.dom
    */
    .filter('localtime', function () {
        return function (d) {
            if (d !== undefined && d !== null) {
                return d.replace(/^(?:\d\d\d\d-\d\d-\d\dT)?\d\d:\d\d(?::\d\d(?:\.\d+)?)?$/, function ($0) {
                    var offset = (new Date()).getTimezoneOffset(),
                        hours = Math.floor(Math.abs(offset) / 60),
                        minutes = Math.abs(offset) % 60,
                        sign = offset <= 0 ? '+' : '-',
                        tz = (sign + (hours * 100 + minutes)).replace(/^([-+])(\d\d\d)$/, '$10$2');

                    return $0 + tz;
                });
            }
        };
    })
    /**
    * filters a value to currency with no decimal places.
    * 
    * @namespace slalom.common.dom.noFractionCurrency
    * @memberOf slalom.common.dom
    */
    .filter('noFractionCurrency', ['$filter', '$locale', function (filter, locale) {
        var currencyFilter = filter('currency');
        var formats = locale.NUMBER_FORMATS;
        return function (amount, currencySymbol) {
            var value = currencyFilter(amount, currencySymbol);
            var sep = value.indexOf(formats.DECIMAL_SEP);
            if (amount >= 0) {
                return value.substring(0, sep);
            }
            return value.substring(0, sep) + ')';
        };
    } ])
    /**
    * allows us to override angulars default html cleansing when we have valid data that contains html markup
    * 
    * @namespace slalom.common.dom.unsafe
    * @memberOf slalom.common.dom
    */
    .filter('unsafe', ["$sce", function ($sce) {
        return function (val) {
            return $sce.trustAsHtml(val);
        };
    } ]);
/**
* @class slalom.common.notifications
* @memberOf slalom.common
*/
angular.module('slalom.common.notifications', [])
    /**
    * Contains all the logic for Observer pattern implementations
    * 
    * @namespace slalom.common.notifications.ObserverService
    * @memberOf slalom.common.notifications
    */
    // TODO more accurate description
    .factory("ObserverService", ["SpinnerService", function (SpinnerService) {
        var services = [];
        /**
        * notifies all observers for a particular service
        * 
        * @memberOf slalom.common.notifications.ObserverService
        * @function notifyObservers
        * @param {string}
        *            serviceName
        * @param {object}
        *            data
        * @param {int}
        *            currentPage
        * @param {int}
        *            pageSize
        * @private
        */
        var notifyObservers = function (serviceName, data, currentPage, pageSize) {
            if (services[serviceName]) {
                var observers = services[serviceName].observers,
                    o,
                    scope;
                for (o in observers) {
                    if (observers.hasOwnProperty(o)) {
                        if (observers[o].hasPaging) {
                            observers[o].method(data.Items, currentPage, data.Count, Math.ceil(data.Count / pageSize));
                        } else {
                            observers[o].method(data);
                        }
                        scope = observers[o].scope;
                        if (!scope.$$phase) {
                            scope.$apply();
                        }
                    }
                }
            }
            SpinnerService.hide(serviceName);
        };

        return {
            /**
            * adds an observer
            * 
            * @memberOf slalom.common.notifications.ObserverService
            * @method addObserver
            * @param {string}
            *            serviceName
            * @param {function}
            *            callback
            * @param {object}
            *            scope
            * @param {boolean}
            *            hasPaging
            */
            addObserver: function (serviceName, callback, scope, hasPaging) {
                if (!services[serviceName]) {
                    services[serviceName] = { observers: [] };
                }
                services[serviceName].observers.push({
                    'method': callback,
                    'scope': scope,
                    'hasPaging': hasPaging
                });
            },
            /**
            * exposes the private NotifyObservers function
            * 
            * @memberOf slalom.common.notifications.ObserverService
            * @method notifyObservers
            * @param {string}
            *            serviceName
            * @param {object}
            *            data
            * @param {int}
            *            currentPage
            * @param {int}
            *            pageSize
            */
            notifyObservers: function (serviceName, data, currentPage, pageSize) {
                notifyObservers(serviceName, data, currentPage, pageSize);
            }
        };
    } ])
    /**
    * Common methods related to loading spinners
    * 
    * @namespace slalom.common.notifications.SpinnerService
    * @memberOf slalom.common.notifications
    */
    .factory("SpinnerService", function () {
        var spinners = {};
        /**
        * creates a spinner if it doesn't already exist
        * 
        * @memberOf slalom.common.notifications.SpinnerService
        * @function createIfNeeded
        * @param {string}
        *            field
        * @private
        */
        var createIfNeeded = function (field) {
            if (!(spinners[field])) {
                spinners[field] = { count: 0, spinners: [] };
            }
        };

        var service = {
            /**
            * creates a spinner if it doesn't already exist
            * 
            * @memberOf slalom.common.notifications.SpinnerService
            * @method addSpinner
            * @param {string}
            *            spinner
            * @param {string}
            *            field
            * @param showAtStart
            */
            addSpinner: function (spinner, field, showAtStart, ignoreSpinnerCount) {
                createIfNeeded(field);
                spinners[field].ignoreCount = ignoreSpinnerCount;
                spinners[field].spinners.push(spinner);
                if (showAtStart !== false) {
                    service.show(field, true);
                } else {
                    service.hide(field, true);
                }
            },
            /**
            * shows a spinner
            * 
            * @memberOf slalom.common.notifications.SpinnerService
            * @method show
            * @param {string}
            *            field
            * @param {boolean}
            *            doNotIncrement
            */
            show: function (field, doNotIncrement) {
                var s;
                createIfNeeded(field);
                if (!(doNotIncrement === true)) {
                    spinners[field].count++;
                }
                for (s in spinners[field].spinners) {
                    if (spinners[field].spinners.hasOwnProperty(s)) {
                        spinners[field].spinners[s].show();
                    }
                }
            },
            /**
            * hides a spinner
            * 
            * @memberOf slalom.common.notifications.SpinnerService
            * @method hide
            * @param {string}
            *            field
            * @param {boolean}
            *            force
            */
            hide: function (field, force) {
                var s;
                if (spinners[field]) {
                    spinners[field].count--;
                    if (force === true || spinners[field].ignoreCount === true) {
                        spinners[field].count = 0;
                    }
                    if (spinners[field].count <= 0) {
                        for (s in spinners[field].spinners) {
                            if (spinners[field].spinners.hasOwnProperty(s)) {
                                spinners[field].spinners[s].hide();
                            }
                        }
                    }
                }
            }
        };

        return service;
    })
    /**
    * Loads the spinner template into the DOM
    * 
    * @namespace slalom.common.notifications.spinner
    * @memberOf slalom.common.notifications
    */
    .directive("spinner", ["SpinnerService",
        function (SpinnerService) {
            return {
                template: '<div class="spinner"><img class="ajax-loader" src="img/ajax-loader.gif" /></div>',
                link: function (scope, element, attrs) {
                    var field = attrs.spinner; // scope.$eval(attrs.spinner);
                    var showOnStart = (attrs.initiallyVisible !== undefined) ? attrs.initiallyVisible === 'true' : true;
                    var ignoreCount = (attrs.ignoreCount !== undefined) ? attrs.ignoreCount === 'true' : false;

                    if (!scope.spinners) { scope.spinners = {}; }
                    scope.spinners[field] = element;
                    SpinnerService.addSpinner(element, field, showOnStart, ignoreCount);
                }
            };
        } ])
        /**
    * Sets the image src for the spinner image
    * 
    * @namespace slalom.common.notifications.spinnerImage
    * @memberOf slalom.common.notifications
    */
    // TODO Remove the hardcoded path and put it into a constant
    .directive("spinnerImage", ["UrlService", function (UrlService) {
        return {
            link: function (scope, element, attrs) {
                if (UrlService.isReady()) {
                    attrs.$set("src", UrlService.createUrl("img/ajax-loader.gif"));
                } else {
                    scope.$watch(UrlService.root(), function () {
                        attrs.$set("src", UrlService.createUrl("img/ajax-loader.gif"));
                    });
                }
            }
        };
    } ])
    /**
    * Central notification service for messages and errors to be shared between components
    * 
    * @namespace slalom.common.notifications.MessageService
    * @memberOf slalom.common.notifications
    */
    .factory("MessageService", ["$window", function ($window) {
        var messages = [];
        var errors = [];

        // creates an instance on the global namespace
        $window.messageObservers = $window.messageObservers || [];

        // checks if the service already exists, returns it if it does
        if (!!$window.sharedService) { return $window.sharedService; }

        /**
        * function to call registered callbacks
        * 
        * @memberOf slalom.common.notifications.MessageService
        * @function notifyObservers
        * @private
        */
        var notifyObservers = function () {
            var o, scope;
            for (o in $window.messageObservers) {
                if ($window.messageObservers.hasOwnProperty(o)) {
                    $window.messageObservers[o].method(messages, errors);
                    scope = $window.messageObservers[o].scope;
                    if (!scope.$$phase) {
                        scope.$apply();
                    }
                }
            }
        };
        /**
        * checks to see if the new message already exists in the message array
        * 
        * @memberOf slalom.common.notifications.MessageService
        * @function isDuplicate
        * @param {string}
        *            newMessage
        * @param {array}
        *            messageArray
        * @returns {boolean}
        * @private
        */
        var isDuplicate = function (newMessage, messageArray) {
            var duplicate = false;
            var messagesLength = messageArray.length;
            var i = 0;

            for (i; i < messagesLength; i++) {
                if (messageArray[i].message === newMessage.message) {
                    duplicate = true;
                    break;
                }
            }
            return duplicate;
        };

        $window.sharedService = {
            /**
            * adds a new message and notifies the observers
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method addMessage
            * @param {string}
            *            newMessage
            * @returns {boolean}
            */
            addMessage: function (newMessage) {
                if (!isDuplicate(newMessage, messages)) {
                    messages.push(newMessage);
                }

                notifyObservers();
            },
            /**
            * clears all messages and notifies the observers
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method clearMessage
            * @param {string}
            *            newMessage
            */
            clearMessage: function () {
                messages = [];
                notifyObservers();
            },
            /**
            * adds a callback function
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method addObserver
            * @param {function}
            *            callback
            * @param {object}
            *            scope
            */
            addObserver: function (callback, scope) {
                $window.messageObservers.push({
                    'method': callback,
                    'scope': scope
                });
            },
            /**
            * returns the current array of messages
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method messages
            * @returns {array}
            */
            messages: function () {
                return messages;
            },
            /**
            * adds a new error messsage and notifies observers
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method messages
            * @param {string}
            *            newMessage
            */
            addError: function (newMessage) {
                if (!isDuplicate(newMessage, errors)) {
                    errors.push(newMessage);
                }
                notifyObservers();
            },
            /**
            * clears the error messages and notifies observers
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method clearErrors
            */
            clearErrors: function () {
                errors = [];
                notifyObservers();
            },
            /**
            * return the array of current error messages
            * 
            * @memberOf slalom.common.notifications.MessageService
            * @method clearErrors
            */
            errors: function () {
                return errors;
            }
        };

        return $window.sharedService;
    } ]);
/**
* @class slalom.common.forms
* @memberOf slalom.common
*/
angular.module('slalom.common.forms', [])
    /**
    * Common Methods used by several custom validation directives
    * 
    * @namespace slalom.common.forms.CommonValidationMethods
    * @memberOf slalom.common.forms
    */
    .factory("CommonValidationMethods", function () {
        /**
        * Checks to see if a variable is undefined
        * 
        * @memberOf slalom.common.forms.CommonValidationMethods
        * @function isUndefined
        * @param {string}
        *            value
        * @returns {boolean}
        * @private
        */
        function isUndefined(value) {
            return value === undefined;
        }
        return {
            /**
            * Turns a string into an integer
            * 
            * @memberOf slalom.common.forms.CommonValidationMethods
            * @method int
            * @param {string}
            *            str
            * @returns {int}
            */
            int: function (str) {
                return parseInt(str, 10);
            },
            /**
            * Checks if a variable is empty, null or undefined
            * 
            * @memberOf slalom.common.forms.CommonValidationMethods
            * @method isEmpty
            * @param {string}
            *            value
            * @returns {boolean}
            */
            isEmpty: function (value) {
                return isUndefined(value) || value === '' || value === null;
            }
        };
    })
    /**
    * Holds the current global state to determine if a form is dirty
    * 
    * @namespace slalom.common.forms.GlobalFormStateService
    * @memberOf slalom.common.forms
    */
    .factory("GlobalFormStateService", ["$window", function ($window) {
        // indicator whether the global form is dirty
        var dirtyForm = false;

        // creates a variables in the global window to hold form state
        if (!!$window.GlobalFormStateService) {return $window.GlobalFormStateService; }

        $window.GlobalFormStateService = {
            getDirtyForm: function () {
                return dirtyForm;
            },
            setDirtyForm: function (dirty) {
                dirtyForm = dirty;
            }
        };
        return $window.GlobalFormStateService;
    } ])
    /**
     * Injects a JQuery UI Date Picker control into the DOM
     * @namespace slalom.common.forms.jdatepicker
     * @memberOf slalom.common.forms
     */
    .directive('jdatepicker', function () {
        return {
            restrict: 'A',
            require : 'ngModel',
            link : function (scope, element, attrs, ngModelCtrl) {
                $(function () {
                    element.datepicker({
                        dateFormat: 'mm/dd/yy',
                        onSelect: function (date) {
                            scope.$apply(function () {
                                ngModelCtrl.$setViewValue(date);
                            });
                        }
                    });
                });
            }
        };
    })
    /**
     * Injects an Angular-UI Bootstrap Date Picker control into the DOM
     * @namespace slalom.common.forms.datePicker
     * @memberOf slalom.common.forms
     */
    .directive("datePicker", ["$filter", function ($filter) {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ctrl) {
                var isDate = function (dateStr) {
                    var s = dateStr.split('/');
                    var d = new Date(+s[2], s[0] - 1, +s[1]);
                    if (+s[2] >= 1900 && Object.prototype.toString.call(d) === "[object Date]") {
                        if (!isNaN(d.getTime()) && d.getDate() == s[1] &&
                                d.getMonth() == (s[0] - 1)) {
                            return true;
                        }
                    }
                    return false;
                };

                var setValidMsgs = function () {
                    var date = new Date(element.val());
                    var today = new Date();

                    ctrl.$setValidity('validDate', true);
                    ctrl.$setValidity('futureDate', true);

                    if (element.val().length > 0 && !isDate(element.val())) {
                        ctrl.$setValidity('validDate', false);
                    } else if (date > today) {
                        ctrl.$setValidity('futureDate', false);
                    }
                };

                $();
                element.datepicker({
                    changeMonth: true,
                    changeYear: true,
                    onSelect: function (dateText) {
                        var expression = attrs.ngModel + " = " + "'" + dateText + "'";
                        scope.$apply(expression);
                        ctrl.$setViewValue(dateText);
                    },
                    maxDate: "+0d"
                });
                ctrl.$formatters.unshift(function (modelValue) {
                    setValidMsgs();
                    return $filter('date')($filter('localtime')(modelValue), 'M/dd/yyyy');
                });

                ctrl.$parsers.unshift(function (viewValue) {
                    setValidMsgs();
                    return $filter('date')(viewValue, 'M/dd/yyyy');
                });
            }
        };
    }])
    /**
     * computes the localtime for the given server date time
     * @namespace slalom.common.forms.localtime
     * @memberOf slalom.common.forms
     */
    .filter('localtime', function () {
        return function (d) {
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
        };
    })
    /**
     * Fix for IE select menus getting stuck when their underlying list changes.
     * Original code: http://kkurni.blogspot.com.au/2013/10/angularjs-ng-option-with-ie8.html
     * 
     * Set the `ie-select-fix` attribute to the model expression that should trigger the list to re-render.
     * 
     * @namespace slalom.common.forms.ieSelectFix
     * @memberOf slalom.common.forms
     * @example <select ng-model="modelValue" ie-select-fix="itemList" ng-options="item.label for item in itemList">
     */
    .directive('ieSelectFix', ['$document', function ($document) {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attributes) {
                var isIE = $document[0] && $document[0].attachEvent;
                if (!isIE) { return; }

                var control = element[0];
                //to fix IE8 issue with parent and detail controller, we need to depend on the parent controller
                scope.$watch(attributes.ieSelectFix, function () {
                    //this will add and remove the options to trigger the rendering in IE8
                    var option = document.createElement("option");
                    control.add(option, null);
                    control.remove(control.options.length - 1);
                });
            }
        };
    } ]);

/**
 * @class slalom.common.collections
 * @memberOf slalom.common
 */
angular.module('slalom.common.collections', [])
    /**
    * Filtered/paged base prototype to abstract the paging and filtering 
    * of collection objects
    *
    * @namespace slalom.common.collections.CollectionBase
    * @memberOf slalom.common.collections
    */
    .factory("CollectionBase", function ($filter) {
        var base = function () {
            this.items = [];
            this.currentPage = 1;
            this.currentItem = null;
            this.pageSize = 10;
        };
        base.prototype.rowFilter = function (item) { return true; };
        base.prototype.itemCount = function () {
            if (!this.items) {
                return 0;
            }
            return $filter('filter')(this.items, this.rowFilter, false).length;
        };

        base.prototype.goToPage = function (page) {
            // Optional intercepter for page changes
            if (this.onPageChange) {
                this.onPageChange(this.currentPage, page);
            }
            this.currentPage = page;
            this.currentItem = null; // deselect previously selected item.
        };
        base.prototype.setCurrentItem = function (item) {
            this.currentItem = item;
        };

        return base;
    })
    /**
     * Validation helper used to maintain a list of validation rules with their associated display text
    * @namespace slalom.common.collections.ValidatorHelper
    * @memberOf slalom.common.collections
     */
    .factory("ValidatorHelper", function () {
        /**
         * Constructor injecting the object that will be validated
         * @param  {object} Instance instance object to be validated
         */
        var base = function (Instance) {
            this.instance = Instance;
            /**
             * Array of rules with the associated field name and display message. 
             * The validator function should be a predicate taking the instance and returning
             * a boolean if the rule passes.
             * @type {Array}
             */
            this.rules = [
            // {"fieldName":"x", "message":"validated message", "validator":function() { return true;}}
            ];

            /**
             * Determines if the instance object is valid (all rules pass)
             * @return {Boolean} true if all rules pass. false if any rule fails.
             */
            this.isValid = function () {
                var i = 0;
                for (i; i < this.rules.length; i++) {
                    if (this.rules[i] && !this.rules[i].validator(this.instance)) {
                        return false;
                    }
                }
                return true;
            };
            /**
             * Determines if the given field is valid for the instance object
             * @param  {string} fieldName name of the field to check
             * @return {boolean}          true if all rules associated with that field pass. False if any rule fails.
             */
            this.fieldValid = function (fieldName) {
                var i = 0;
                for (i; i < this.rules.length; i++) {
                    if (this.rules[i] && this.rules[i].fieldName === fieldName && !this.rules[i].validator(this.instance)) {
                        return false;
                    }
                }
                return true;
            };
            /**
             * Descriptive message of failing rule for a field
             * @param  {string} fieldName fieldname to test
             * @return {string}           error message of first rule that fails. Empty string if rules succeed.
             */
            this.fieldValidMessage = function (fieldName) {
                var i = 0;
                for (i; i < this.rules.length; i++) {
                    if (this.rules[i] && this.rules[i].fieldName === fieldName && !this.rules[i].validator(this.instance)) {
                        return this.rules[i].message;
                    }
                }
                return "";
            };

        };

        return base;
    })
    /**
     * Validation helper used to maintain a list of validation rules with their associated display text
     * @namespace slalom.common.collections.pagingFilter
     * @memberOf slalom.common.collections
     */
    .filter("pagingFilter", function () {
        return function (input, pageSize, currentPage) {
            return input ? input.slice((currentPage - 1) * pageSize, currentPage * pageSize) : [];
        };
    });