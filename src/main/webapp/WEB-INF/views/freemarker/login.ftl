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
		<!-- jQuery Dialog box css -->
		<link href="${rc.contextPath}/css/common/jquery-ui.css" rel="stylesheet">
		<link href="${rc.contextPath}/css/common/dialog-style.css" rel="stylesheet">
		<link href="${rc.contextPath}/css/common/responsive.css" rel="stylesheet">
		
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script type="text/javascript" src="${rc.contextPath}/js/jquery-1.12.4.js"></script>
		<script src="${rc.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jquery-ui.js"></script>
		<!-- Icons Font -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    <script type="text/javascript" src="${rc.contextPath}/js/ajaxHelper.js"></script>
	    <script type="text/javascript" src="${rc.contextPath}/js/custom/login.js"></script>
	</head>
	<body class="front_page">
		<div id="dialog" title="Basic dialog"></div>
		<div class="container-fluid">
	        <div class="col-xs-8 col-xs-offset-2">
	            <img src="images/logo.png" alt="" class="logo hidden-xs" />
	            <img src="images/logo-phone.png" alt="" class="logo visible-xs" />
	        </div>
	        <div class="col-sm-10 col-sm-offset-1 col-xs-8 col-xs-offset-2 login_form">
	            <input type="text" id="username" name="username" placeholder="User ID"/>
	            <input type="password" id="password" name="password" placeholder="Password" onkeydown="if (event.keyCode == 13) document.getElementById('login').click()"/>
	            <p class="text-right forgot_password"><i class="fa fa-clock-o forgot_icon" aria-hidden="true"></i>Forgot password?</p>
	        </div>
	        
	        <div class="col-sm-4 col-sm-offset-4 col-xs-8 col-xs-offset-2">
	            <button type="button" class="login_button" id="login" onclick="loginValided('${rc.contextPath}');">
	            	<img src="images/login.png" class="login-button-img"/>  Login
	            </button>
	        </div>
	        <div class="col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2">
	            <img src="images/front_logo_bottom.png" alt="WebHawks IT logo" class="hidden-xs"/>
	            <img src="images/WHIT-Logo-Phone.png" alt="WebHawks IT logo mobile" class="visible-xs footer-logo-mobile"/>
	            
	            <p class="front_copyright">&copy; All Rights Reserved</p>
	        </div>
	    </div>
	</body>
</html>