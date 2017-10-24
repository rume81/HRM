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

function registration(base) {
	
	var fname 				= jQuery('#fname').val();
	var lname 				= jQuery('#lname').val();
	var alias 				= jQuery('#alias').val();
	var designation1 		= jQuery('#designation1').val();
	var designation2 		= jQuery('#designation2').val();
	var company 			= jQuery('#company').val();
	var department 			= jQuery('#department').val();
	var official_mail 		= jQuery('#official_mail').val();
	var personal_mail 		= jQuery('#personal_mail').val();
	var phone 				= jQuery('#phone').val();
	var referral 			= jQuery('#referral').val();
	var joining_date 		= jQuery('#joining_date').val();
	var skype_id 			= jQuery('#skype_id').val();
	var previous_work 		= jQuery('#previous_work').val();
	var reporting_manager 	= jQuery('#reporting_manager').val();
	var birthdate_real 		= jQuery('#birthdate_real').val();
	var birthdate_certificate = jQuery('#birthdate_certificate').val();
	var education 			= jQuery('#education').val();
	var nid 				= jQuery('#nid').val();
	var passport 			= jQuery('#passport').val();
	var maritial_status 	= jQuery('#maritial_status').val();
	var siblings 			= jQuery('#siblings').val();
	var hobbies 			= jQuery('#hobbies').val();
	var activities 			= jQuery('#activities').val();
	var emegency_phone 		= jQuery('#emegency_phone').val();
	var relation 			= jQuery('#relation').val();
	var zodiac	 			= jQuery('#zodiac').val();
	var emp_num 			= jQuery('#emp_num').val();
	var bank_name 		= jQuery('#bank_name').val();
	var bank_account 		= jQuery('#bank_account').val();
	var officie_phone 		= jQuery('#officie_phone').val();
	var extension 			= jQuery('#extension').val();
	var chat_nick 			= jQuery('#chat_nick').val();
	var present_address 	= jQuery('#present_address').val();
	var permanent_address 	= jQuery('#permanent_address').val();
	var usertype 	= jQuery('#userType').val();
	var team 	= jQuery('#team').val();
	//var ablout_me 			= jQuery('#ablout_me').val();
	
	var fo = false;
	var msg = "Please fill required field:</br>";
	
	if (fname == "") {
		fo = true;
		msg = msg + "* First name</br>";
	}
	if (lname == "") {
		fo = true;
		msg = msg + "* Last name</br>";
	}
	if (alias == "") {
		fo = true;
		msg = msg + "* Alias name</br>";
	}
	
	if ((designation1 == "") && (designation2 == "")) {
		fo = true;
		msg = msg + "* Designation</br>";
	}
	if (joining_date == "") {
		fo = true;
		msg = msg + "* Joining Date</br>";
	}
	if (present_address == "") {
		fo = true;
		msg = msg + "* Present Address</br>";
	}
	
	if (permanent_address == "") {
		fo = true;
		msg = msg + "* Permanent Address</br>";
	}
	
	if (usertype == "") {
		fo = true;
		msg = msg + "* User Type</br>";
	}
	
	if (team == "") {
		fo = true;
		msg = msg + "* Team</br>";
	}
	
	if (fo) {
		showMsg(msg, "Error");
		return;
	}
	
	jQuery('#register_employee').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				showMsg("Registration was successfull");
				window.location.href = base + "/employeeselection";
			}

			if (res == 'ERROR') {
				showMsg("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function saveprofileImage(base){
	var fpath = jQuery('#profile_img').val();
	
	if (fpath=="") {
		jQuery('#footer span').html("Please select an image for save.");
		return;
	}
	
	jQuery('#edit_employee_photo').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				window.location.href = base + "/editprofile";
			}
			if (res == 'ERROR') {
				jQuery('#footer span').html("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function saveAppointment(base){
	var fpath = jQuery('#appoinment').val();
	
	if (fpath=="") {
		jQuery('#footerapp span').html("Please select a PDF for save.");
		return;
	}
	
	jQuery('#edit_employee_appoinment').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				window.location.href = base + "/editprofile";
			}
			if (res == 'ERROR') {
				jQuery('#footer span').html("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function saveNda(base){
	var fpath = jQuery('#nda').val();
	
	if (fpath=="") {
		jQuery('#footernda span').html("Please select a PDF for save.");
		return;
	}
	
	jQuery('#edit_employee_nda').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				window.location.href = base + "/editprofile";
			}
			if (res == 'ERROR') {
				jQuery('#footer span').html("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function editprofile(base) {
	
	var fname 				= jQuery('#fname').val();
	var lname 				= jQuery('#lname').val();
	var alias 				= jQuery('#alias').val();
	var designation1 		= jQuery('#designation1').val();
	var designation2 		= jQuery('#designation2').val();
	var company 			= jQuery('#company').val();
	var department 			= jQuery('#department').val();
	var official_mail 		= jQuery('#official_mail').val();
	var personal_mail 		= jQuery('#personal_mail').val();
	var phone 				= jQuery('#phone').val();
	var referral 			= jQuery('#referral').val();
	var joining_date 		= jQuery('#joining_date').val();
	var skype_id 			= jQuery('#skype_id').val();
	var previous_work 		= jQuery('#previous_work').val();
	var reporting_manager 	= jQuery('#reporting_manager').val();
	var birthdate_real 		= jQuery('#birthdate_real').val();
	var birthdate_certificate = jQuery('#birthdate_certificate').val();
	var education 			= jQuery('#education').val();
	var nid 				= jQuery('#nid').val();
	var passport 			= jQuery('#passport').val();
	var maritial_status 	= jQuery('#maritial_status').val();
	var siblings 			= jQuery('#siblings').val();
	var hobbies 			= jQuery('#hobbies').val();
	var activities 			= jQuery('#activities').val();
	var emegency_phone 		= jQuery('#emegency_phone').val();
	var relation 			= jQuery('#relation').val();
	var zodiac	 			= jQuery('#zodiac').val();
	var emp_num 			= jQuery('#emp_num').val();
	var bank_name 		= jQuery('#bank_name').val();
	var bank_account 		= jQuery('#bank_account').val();
	var officie_phone 		= jQuery('#officie_phone').val();
	var extension 			= jQuery('#extension').val();
	var chat_nick 			= jQuery('#chat_nick').val();
	var present_address 	= jQuery('#present_address').val();
	var permanent_address 	= jQuery('#permanent_address').val();
	//var ablout_me 			= jQuery('#ablout_me').val();
	
	var fo = false;
	var msg = "Please fill required field:</br>";
	
	if (fname == "") {
		fo = true;
		msg = msg + "* First name</br>";
	}
	if (lname == "") {
		fo = true;
		msg = msg + "* Last name</br>";
	}
	
	if (alias == "") {
		fo = true;
		msg = msg + "* Alias name</br>";
	}
	
	if ((designation1 == "") && (designation2 == "")) {
		fo = true;
		msg = msg + "* Designation</br>";
	}
	
	if (present_address == "") {
		fo = true;
		msg = msg + "* Present Address</br>";
	}
	
	if (permanent_address == "") {
		fo = true;
		msg = msg + "* Permanent Address</br>";
	}
	
	
	if (fo) {
		showMsg(msg, "Error");
		return;
	}
	
	jQuery('#edit_employee').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				showMsg("Successfull Edit Profile Data");
				window.location.href = base + "/empprofile";
			}

			if (res == 'ERROR') {
				showMsg("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}