/*jslint browser: true*/
/*globals define, app, $, confirm*/
/**
* @memberOf app
* @class ApplicantController ViewModel for ApplicantInfo view.
* @constructor
*/
define("controllers/BasicInfoController", ['app'], function (app) {
    app.controller('BasicInfoController', ['$scope', '$modal', '$routeParams', '$location', '$window', 'SharedServices', 'MessageService', 'SpinnerService', 'slalom.browser.CookieManager', 'ErrorModal','$localstorage','CollectionBase',
        function ($scope, $modal, $routeParams, $location, $window, services, messageService, SpinnerService, CookieManager, ErrorModal,$localstorage, CollectionBaseFactory) {
          
    	document.getElementById("subnav1").style.display = 'block';
    	
    	if (!CookieManager.getCookie('associateId') || !CookieManager.getCookie('firstName') || !CookieManager.getCookie('lastName') || !CookieManager.getCookie('jobTitle')) {
            $location.path("/main");
        }
        
        /**
         * Model instance
         */
         $scope.model = null;
        // $scope.associateId = $routeParams.Id;
        $scope.associateId = null;
    	$scope.firstName = null;
    	$scope.lastName = null;
    	$scope.jobTitle = null;
    	$scope.hasPrevious = false;
    	$scope.hasNext = false;
    	
    	$scope.model = { records: new CollectionBaseFactory() };
        $scope.model.records.items = [{}];
        $scope.count = 0;
        $scope.index = 0;
        
        
        
        
    	   $scope.ShowPhotoUploadModal = function () {
               var modalInstance = $modal.open({
                   templateUrl: 'Templates/PhotoUploadDialog.html',
                   controller: PhotoUploadModalCtrl,
                   backdrop: 'static',
                   keyboard: false,
                   size: 'lg',
                   resolve: {
                	   
                	   item: function (){
                		   return $scope.associateId;
                	   }                     

                   }
               });
            
           };
           
        
        /**
        * @private
        */
        var index;
        var spinnerField = "basicInfoSpinner";
        
     // ********** Need this information while page loads - starts here 
        $scope.model.records = $localstorage.getObject('acount');  
    	$scope.count = $scope.model.records.length;
    	for (var i = 0; i < $scope.model.records.length; i++) { 
    	    if (CookieManager.getCookie('associateId') == $scope.model.records[i].zeroEmployeeId) {
    	    	$scope.index = i;
    	    	break;
    	    }
    	}
    	       	
    	if ($scope.count > 0 && $scope.count > ($scope.index + 1))
    		$scope.hasNext = true;
    	else
    		$scope.hasNext = false;
    	
    	if ($scope.count > 0 && ($scope.index - 1) > -1)
    		$scope.hasPrevious = true;
    	else
    		$scope.hasPrevious = false;
    	
    	CookieManager.setCookie('prv',$scope.hasPrevious);
        CookieManager.setCookie('nxt',$scope.hasNext);
        
    	// ******** Need this information while page loads - end here
			
       $scope.loadBasicInfo = function () {
             // Fetch the values for the supplied applicant
       SpinnerService.show(spinnerField);
        services.getBasicInformation(CookieManager.getCookie('associateId'))
            .then(function (data) {
                $scope.model = data;
                if ($scope.model.basicInformationDTO) {

                	$scope.associateId = CookieManager.getCookie('associateId');
                 	$scope.firstName = CookieManager.getCookie('firstName');
                 	$scope.lastName = CookieManager.getCookie('lastName');
                 	$scope.jobTitle = CookieManager.getCookie('jobTitle');
                 	
                 	
                 	var str1 = "servlet/getImage?ID=";
       		 	 	var d = new Date() ;
       		 	 	var n = d.getTime();
       		 	 	photo.src = str1.concat($scope.associateId, "&date=",n);
                 	
                } else {
                   /* var job = CookieManager.getCookieJSON('currentJob');
                    var reqNumber = 0;
                    if (job) {
                        reqNumber = job.reqNbr;
                    }
                    var applicantId = $scope.model.appPersonalInfoRes.applPersonalInfoList[0].applID;*/
                    
                }
                SpinnerService.hide(spinnerField);
            }, function () {
                SpinnerService.hide(spinnerField);
            });
        };
        
        $scope.loadBasicInfo();

        // Select button    
 	    $scope.selectAssociate = function () {
 	    	$location.path("/select");
 		};
 		
 		// Prev Buton
        $scope.prevAssociate = function () {
       	 services.getPageInfo(true, false);        	 
            $scope.loadBasicInfo();
        };
        
        // Next Button
        $scope.nextAssociate = function () {
       	 services.getPageInfo(false, true);        	 
            $scope.loadBasicInfo();
        };        
       
       
        /**
         * Controller for the PhotoUploadModalCtrl dialog
         * @param {object} $scope         model to bind to
         * @param {object} $modalInstance handler to the modal dialog instance
         */
         var PhotoUploadModalCtrl = function ($scope, $modalInstance, item) {

        	 /* Determines if photoupload dialog should close on certain certain
        	  * responses
        	  */
        	$scope.firstload = true;
        	$scope.assocID = item;
        	
     

             /** 
              * Resets the job to the original value before edits were applied
              * @return {[type]} [description]
              */
             var undoEdit = function () {
                 angular.copy($scope.originalValue, $scope.currentItem);
             };  
                      
             $scope.main = { user: app.user };
           

             /** 
              * Handler for close requisition button
              */
             $scope.closeRequisition = function () {
                 if ($scope.currentItem.candidateCount > 0) {
                     ErrorModal.show("Unable to close. Must fill in an interview status for all candidates.");
                     return;
                 }
                 services.closeJob($scope.currentItem)
                     .then(function (data) {
                         if (data && data.status && data.status === "SUCCESS") {
                             $modalInstance.close("jobClosed");
                         } else {
                             ErrorModal.show(data);
                         }
                     });
             };

             
             /**
              * Handler for clear button. Resets job to unedited state
              */
             $scope.clearValues = function () {
                 var dlg = ConfirmModal.show("You have requested to reset this job requisition. Do you wish to continue?");
                 dlg.result.then(function (result) {
                     if (result) {
                         undoEdit();
                     }
                 });
             };

             /**
             * Handler for the ok button click. Saves the changes and returns back to the calling form.
             */
             $scope.ok = function () {
                 $scope.currentItem.strNbr = CookieManager.getCookie('currentStore');
                 var jobtitle = getJobTitle();
                 if (jobtitle) {
                     $scope.currentItem.jobTtl = jobtitle.shortDesc;
                 }
                 $scope.currentItem.lastUpdatedUserId = "";
                 $scope.currentItem.lastUpdatedTimestamp = new Date();

                 services.saveJob($scope.currentItem)
                     .then(function (data) {
                         if (data && data.status && data.status === "SUCCESS") {
                             $modalInstance.close('jobSaved');
                         } else {
                             ErrorModal.show('Update failed.');
                         }
                     });
             };

             /**
             * Handler for the cancel button click. Reverts changes and returns back to the calling form.
             */
             $scope.cancel = function () {
                 
                 $modalInstance.dismiss('cancel');
             };
             
             /**
              * Handler for the Upload button click.  Posts image file and returns back to the calling form
              */
             $scope.uploadPhoto = function () {
             
            	 var input = document.getElementById("fileinput");
            	 
            	 
            	 if (window.File)
            		 {
            		 	 var file = input.files[0];
            		 	 
            		 	if (file.size > 1048576)
               		 {
               		 	alert("The picture is too large to upload, please use an image file less than or equal to 1MB");
               		 }else
               			 {
   	            			 var str1 = "servlet/uploadImage?ID=";
   	                    	 var url = str1.concat($scope.assocID);
   	                    	 
   	                    	document.photoUpload.action = url ; 
   	                     	document.photoUpload.submit();

               			 }
            		 }else
            			 {
            			 	var str1 = "servlet/uploadImage?ID=";
	                    	 var url = str1.concat($scope.assocID);
	                    	 
	                    	document.photoUpload.action = url ; 
	                     	document.photoUpload.submit();
            			 
            			 }
            	 
            	 
            	 
            	 
            	                  //$modalInstance.dismiss('cancel');
                 
                 
             };
             
             /*
              * If comes back as error, set firstload to true so window doesn't close.
              */
             window.notDone = function(){
            	 $scope.firstload = true ;
            	 
             };
             
             window.uploadDone = function(){
            	 
            	 if($scope.firstload == false)
            		 {
            		 
	            	 	 var str1 = "servlet/getImage?ID=";
	        		 	 var d = new Date() ;
	        		 	 var n = d.getTime();
	        		 	parent.photo.src = str1.concat($scope.assocID, "&date=",n);
        		 	
            		 	$modalInstance.dismiss('cancel');
            		
            		 	
            		 }
            	 
            	 $scope.firstload = false ;
             };
             
             window.getAction = function(){
            	 var str1 = "servlet/uploadImage?ID=";
            	 var url = str1.concat($scope.assocID);
            	 return url;
             };

         };//END PHOTOUPLOADCONTROL
        
    }]);
});