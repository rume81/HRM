<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	       
		<title>Hawks HR</title>
	        
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="HawksHR">
		<meta name="keywords" content="HawksHR">
		<meta name="author" content="Abdulla Al Monsur, Ole Ul Islam">
	
		<!-- Bootstrap Css -->
	    <link href="${rc.contextPath}/css/common/bootstrap.min.css" rel="stylesheet">
	    <link href="${rc.contextPath}/css/common/font-awesome.min.css" rel="stylesheet">
	    <!-- Style -->
	
	    <link href="${rc.contextPath}/css/common/style.css" rel="stylesheet">
	        
		<link rel="shortcut icon" href="${rc.contextPath}/css/common/images/favicon.ico"/>
	
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.10.2.js"></script>
		<!-- Icons Font -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    <script type="text/javascript">
	    function callnext()
		{
			location.href='${rc.contextPath}/login';
		}
		$(document).ready(function() {
   			setTimeout("callnext()", 3000);
		});
		</script>
	</head>
	<body class="front_page">
		<div class="container-fluid">
		    <div class="col-xs-8 col-xs-offset-2">
		        <img src="${rc.contextPath}/images/front_logo.png" alt="" class="logo_front" />
		    </div>
		    <div class="col-xs-2 col-xs-offset-5">
		        <img src="${rc.contextPath}/images/front_logo_bottom.png" />
		        <p class="text-right front_copyright">&copy; All Rights Reserved</p>
		    </div>
		</div>
		<script src="${rc.contextPath}/js/jquery.min.js"></script>
		<script src="${rc.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>