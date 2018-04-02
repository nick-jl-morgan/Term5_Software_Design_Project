



//On button click retrieve all applicants and redirect to page
$(document).ready(function(){ 

		$( ".view-applicant-btn" ).click(function(btn) {
            var applicationID = btn.target.id;
           
            window.location.replace("http://99.253.59.150/viewApplicant/" + applicationID );

        });



});