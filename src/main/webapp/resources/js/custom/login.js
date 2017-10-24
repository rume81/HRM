function myKeyPress(base,e){
    if(e.keycode == 13){
    	loginValided(base);
    }
}
function showMsg(msg,tit) {
	$("#dialog").dialog({
		title:tit,
	    show: {
	    	effect: "blind",
	        duration: 500
	    },
	    hide: {
	        effect: "blind",
	        duration: 500
	    }
	}).html(msg);
}
function loginValided(base){
	
	var uname = jQuery('#username').val();
	var pass = jQuery('#password').val();
	
	if(uname == '')
	{
		showMsg("Please insert user id","Input Error");
		return false;
	} 
	
	if(pass == '')
	{
		showMsg("Please insert password","Input Error");
		return false;
	} 
	
	var pars =  uname+"~"+pass;
	ajaxHelper.complexAjaxExecute(base+"/users/valideduser", pars, function(res){
	   	if(res!="-1"){
	   		if((res=="Admin")||(res=="Talent Manager"))
	   			window.location.href=base+"/employeeselection";
	   		else
	   			window.location.href=base+"/dashboard";
	    }else{
	    	showMsg("Invalid user id or password, try with valid information.","Login Error");
	    }
	});
}

function logout(base){
	
	var pars =  "";
	ajaxHelper.complexAjaxExecute(base+"/user/logout", pars, function(res){
	   	if(res!="-1"){
	   		window.location.href=base+"/user/login";
	    }
	 });
}