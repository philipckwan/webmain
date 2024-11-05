<!DOCTYPE html>
<html lang="en">
<head>
<title>Photos of the Day Portal</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="./bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="./bower_components/jquery/dist/jquery-3.4.0.min.js"></script>
<script src="./bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<script>

function go() {
	alert("go: 1.0;");
	var meta;
	
	$.ajax
	({
	  type: "GET",		
	  url: "https://dl.dropboxusercontent.com/s/g4f3v1ecy9i2b89/metadata.json", 
	  //url: "abc", 
	  mimeType: 'application/json',
      contentType: "application/json",
	  success: function(data){
		  meta = data;
		  alert("go: 1.3;");		  
	  },
	  error: function(XMLHttpRequest, textStatus, errorThrown) { 
          alert("Status: " + textStatus); alert("Error: " + errorThrown); 
      } 
	});
	
}


</script>
  
<div class="container">
  <div class="row">
  	<div class="col-md-12"><h3 class="text-center"><b>Photos of the Day Portal</b></h3></div>
  </div>
  <div class="row">
  	<div class="col-md-12"><img src="images/go.jpg" onclick="go()" style="width:80px;height:80px;" /></div>
  </div>
</div>

</body>
</html>