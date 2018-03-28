function appendPostings(jsonData){

    var content = "";

    jsonData['postings'].forEach(function(element) {
        var card = "<div class='col-md-4 job-posting-card'> <h2 class='job-posting-card-title' >" + element['title'] + "</h2> <h4 class='job-posting-access-code'>Access Code : <span class='job-posting-access-code-digits'>" + element['accessKey'] +"</span></h3> <p></p>"+ element['description']+"<p><a class='btn btn-secondary view-applicants-btn' id='"+ element['accessKey'] +"'>View Applicants »</a></p> </div> ";
        content += card;
    });

    $( ".postings-row" ).append( $( content) );
}



function getApplicantsFromAccessKey(accessKey){

  window.location.replace("http://99.253.59.150/getApplicantsFromAccessKey/" + accessKey );


/*  $.ajax({
      type: "POST",
      url: "/getApplicantsFromAccessKey",
      data : {
        "accessKey" : accessKey
      },
      ContentType : "application/json",
      success: function(data, status) {
          console.log(data)
      }
  });*/


}

$(document).ready(function(){ 


    var accessToken = $("meta[name=accessToken]").attr("content");
    console.log(accessToken);
    $.ajax({
      type: "POST",
      url: "/getUserPostings",
      data : {
        "user" : accessToken
      },
      ContentType : "application/json",
      success: function(data, status) {

        appendPostings(data)

        $( ".view-applicants-btn" ).click(function(btn) {
            var accessKey = btn.target.id;
            getApplicantsFromAccessKey(accessKey)

        });


      }
    });








  });
        


