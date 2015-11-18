/*jslint browser: true*/
/*globals define, app, $, confirm, alert*/

/**
* Mock services that return stored sample json files.
* Used for testing when not connected to the actual services
* and through the development process when the actual services are not
* yet ready for use.
*/
angular.module("app.services", [])
    .factory("SharedServices", ["$http", "$q", function ($http, $q) {

        // privates
        var getSimple = function (target, options) {
            var deferred = $q.defer();
            $http.get(target, options)
                .success(function (data) {
                    deferred.resolve(data);
                })
                .error(function () {
                    deferred.reject("Failed to retrieve " + target);
                });
            return deferred.promise;
        };

        var postSimple = function (target, body, expectedResult) {
            var deferred = $q.defer();
            alert(target + "<br />" + JSON.stringify(body));
            deferred.resolve(expectedResult);

            return deferred.promise;
        };

        // public 
        return {
            attachApplicant: function (store, reqNumber, applId, applName) {
                return postSimple("attach",
                    { "store": store, "req": reqNumber, "appId": applId, "name": applName },
                    { data: { status: "SUCCESS"} }
                    );
            },
            closeJob: function (job) {
                return postSimple("close", job);
            },
            getAccessInfo: function () {
                return getSimple("testData/AccessInfo.json");
            },
            getAddress: function(){
            	return getSimple("rs/AssociateService/getAddressDetails?associateId=" + associateId);
            },
            getApplicant: function () {
                return getSimple("testData/appProfileFull.json");
            },
            getBackgroundChecks: function () {
                return getSimple('testData/BackgroundCheck.json');
            },
            getCandidates: function () {
                return getSimple('testData/find.json');
            },
            getWorkHistory: function () {
                return getSimple('testData/workHistory.json');
            },
            getRating: function () {
                return getSimple('testData/rating.json');
            },
            getNextPosition: function () {
                return getSimple('testData/nextPosition.json');
            },
            getCandidateList: function () {
                return getSimple('testData/CandidateList.json');
            },
            getSearchDetails:  function () {
                return getSimple('testData/search.json');
            },
            getCandidateSearchDetails: function () {
                return getSimple('testData/SearchDetails.json');
            },
            getCourses: function(){
            	return getSimple('testData/CoursesList.json');
            },
            getDepartments: function () {
                return getSimple('testData/deptJobTitle.json', { cache: true });
            },
            getDispositionReasons: function () {
                return getSimple('testData/DispositionReasons.json', { cache: true });
            },
            getDrugTests: function () {
                return getSimple('testData/DrugTest1.json');
            },
            getDrugHistory: function () {
                return getSimple('testData/DrugTest1.json');
            },
            getEducation: function(){
            	return getSimple('testData/EducationList.json');
            },
            getJobs: function () {
                return getSimple('testData/JobList.json');
            },
            getLanguages: function () {
                return getSimple('testData/languages.json');
            },
            getQualifiedPool: function (poolFor) {
                return getSimple('testData/QualifiedPool-' + poolFor + '.json');
            },
            getStates: function () {
                return getSimple('testData/States.json', { cache: true });
            },
            navBasicInfo : function () {
                $location.path('/basicInfo/');
            },navWorkHistory : function () {
                $location.path('/workHistory/');
            },navRating : function () {
                $location.path('/rating/');
            },navNextPosition : function () {
                $location.path('/nextPosition/');
            },navReport1 : function () {
                $location.path('/report1/');
            },navReport2 : function () {
                $location.path('/report2/');
            },navReport3 : function () {
                $location.path('/report3/');
            },
            

            /**
            * Gets the store name and other details used on the main screen
            * @param {string} Store number to fetch
            * @return {object}
            */
            getStoreDetails: function () {
                return getSimple('testData/StoreDetails.json', { cache: true });
            },

            getStores: function () {
                return getSimple('testData/StoreList.json', { cache: true });
            },
            markConsidered: function (reqNumber, applicants) {
                var applicantList = [];
                var currentApplicant = null;
                var i = 0;
                for (i; i < applicants.length; i++) {
                    currentApplicant = applicants[i];
                    if (currentApplicant.consideredFlg !== "Y") {
                        applicantList.push({ applicantId: currentApplicant.id, reqNbr: reqNumber, consideredCode: "0" });
                        // UI will flip pendings to Y if page back
                        // We can't just set it to Y here because the record will display as checked
                        // immediately. They don't want it to be checked until they page off and back.
                        // Since we are marking as considered as soon as the page is loaded, we have to
                        // make the mark pending another user action here.
                        // By marking it as "pending", we can queue up the change in the UI tier and fake out the actual values.
                        currentApplicant.consideredFlg = "pending";
                    }
                }
                //var url = "rs/StaffingService/markApplicantsAsConsideredInQP";
                //return $http.post(url, {'applicantList': applicantList});
                if (applicantList.length > 0) {
                    console.log("Marking: " + JSON.stringify(applicantList));
                }
            },

            moveCandidate: function (fromReqNumber, toReqNumber, applicantId) {
                var url = "rs/StaffingService/moveCandidate?" +
                    "fromReqNumber=" + fromReqNumber +
                    "&toReqNumber=" + toReqNumber +
                    "&applicantID=" + applicantId;
                alert("Moving: " + url);
            },

            rejectApplicant: function (reqNumber, applicantId, rejectionCode) {
                var url = "rs/StaffingService/createRejectDetails";
                var body = { "reqNbr": reqNumber + "aa", "candID": applicantId, "rejectionCode": rejectionCode };
                return postSimple(url, body);
            },
            releaseCandidate: function (reqNumber, applicantId) {
                var deferred = $q.defer();
                alert("Releasing: " + reqNumber + " appId: " + applicantId);
                deferred.resolve({ status: "SUCCESS" });

                return deferred.promise;

                // var url = 'rs/StaffingService/releaseCandidate?' +
                //     'reqNumber=' + reqNumber +
                //     '&applicantID=' + applicantId;
                // return postSimple(url);
            },
            saveBackgroundCheckDetails: function (backgroundItem) {
                var url = 'rs/StaffingService/updateBackgroundCheckDetails';
                var body = {
                    "overrideAlertStatusCode": backgroundItem.overrideAlertStatusCode,
                    "backgroundCheckId": backgroundItem.backgroundCheckId.toString(),
                    "backgroundCheckComponents": backgroundItem.backgroundCheckComponents,
                    "backgroundAlertStatusCode": backgroundItem.backgroundCheckSystemAlertStatusCode.toString()
                };
                return postSimple(url, body);
            },
            /**
            * Saves a job
            * @param  {object} job Job instance to save
            */
            saveJob: function (job) {
                return postSimple("saveJob", job);
            }

        };
    } ]);