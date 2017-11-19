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

function validate(evt) {
  var theEvent = evt || window.event;
  var key = theEvent.keyCode || theEvent.which;
  key = String.fromCharCode( key );
  var regex = /^[0-9._]+|[\b]+$/;
  if( !regex.test(key) ) {
    theEvent.returnValue = false;
    if(theEvent.preventDefault) theEvent.preventDefault();
  }
}

function showEmployee(base) {
	var team = $("#team").val();
	var pars =  team;
	ajaxHelper.complexAjaxExecute(base+"/getteammember", pars, function(res){
		$("#teamemp").html(res);
	});
}

function showLeave(base) {
	window.location.href = base+"/leave";
}


function viewDetails(base){
	var team = $("#team").val();
	var emp = $("#empid").val();
	var pars =  team+"~"+emp;
	/*if((team=="")||(emp==""))
	{
		showMsg("Please Select Team and Employee","Input Error");
		return false;
	} else{*/
		ajaxHelper.complexAjaxExecute(base+"/viewDetails", pars, function(res){
			if(res!="-1"){
				location.href=base+"/dashboard";
			}
		});
	//}
}

function EmployeeRegister(base) {
	window.location.href=base+"/registration";
}

function EditProfile(base) {
	window.location.href=base+"/editprofile";
}
function readURL(input,id,width,height) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#'+id)
                .attr('src', e.target.result)
                .width(width)
                .height(height);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function readPDF(input,id) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#'+id)
                .attr('data', e.target.result);
        };
        
        reader.readAsDataURL(input.files[0]);
    }
}


function toggle(source,elname) {
  checkboxes = document.getElementsByName(elname);
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].checked = source.checked;
  }
}

function toggleselect(source,elname) {
  checkboxes = document.getElementsByName(elname);
  var se = 1;
  for(var i=0, n=checkboxes.length;i<n;i++) {
	  if(!checkboxes[i].checked)
	  {
		  se = 0;
		  break;
	  }
  }
  if(se==0)
  {
	  document.getElementById(source).checked  = false;
  }else{
	  document.getElementById(source).checked  = true;
  }
}

function addNotice(base) {
	
	var title 	= jQuery('#title').val();
	var details = jQuery('#details').val();
	var expdate = jQuery('#expdate').val();
	//var chk 	= jQuery('#chk').val();
		
	var fo = false;
	var msg = "Please fill required field:</br>";
	
	if (title == "") {
		fo = true;
		msg = msg + "* Title</br>";
	}
	if (details == "") {
		fo = true;
		msg = msg + "* Details</br>";
	}
	
	if (expdate == "") {
		fo = true;
		msg = msg + "* Expie Date</br>";
	}
		
	if (fo) {
		showMsg(msg, "Error");
		return;
	}
	
	jQuery('#addnotice').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				window.location.href = base + "/noticeboard";
			}

			if (res == 'ERROR') {
				showMsg("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function getQuota(base)
{
	var quotayear = jQuery('#year').val();
	var emp_id = jQuery('#emp_id').val();
	
	if(quotayear!="" && emp_id != "") {
		var pars =  emp_id+"~"+quotayear;
		ajaxHelper.complexAjaxRequest(base+"/getQuota", pars, function(res){
			 if(res!="-1"){
				// alert(res);
			 	jQuery('#quotadisplay').html(res);
			 }
		});
	}	
}

function copyValue(cb) {
  if(cb.checked)
  {
	  var fromdate = jQuery('#fdate').val();
	  if(fromdate != "")
	  {
		  jQuery('#tdate').val(fromdate);
	  }
  } else{
	  jQuery('#tdate').val('');
  }
}

function changeDateFormat(date)
{
	var spdate = date.split("-");
	var fdate = spdate[1]+"/"+spdate[0]+"/"+spdate[2];
	
	return fdate;
}

function noOfLeaveCalculation(base,excludeLeave)//0 for includeleave, 1 for excludeLeave
{
	var employee_id = jQuery('#emp_id').val();
	var fdate = jQuery('#fdate').val();
	var tdate = jQuery('#tdate').val();
	var leavetypeid = jQuery('#leavetype').val();
	var selectedyear= jQuery('#year').val();
	var days=0;
	if(excludeLeave==1){
		days= jQuery('#prevdays').val();
	}
	if(fdate !="" && tdate != "" && employee_id !="" && leavetypeid != "" && selectedyear != "")
	{
		var date1 = new Date(changeDateFormat(fdate));
		var date2 = new Date(changeDateFormat(tdate));
		var timeDiff = date2.getTime() - date1.getTime();
		var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		
		if(diffDays<0)
		{	
			alert("Please select valid date.");
			return;
		}
				
		var pars =  fdate+"~"+tdate+"~"+employee_id+"~"+excludeLeave+"~"+leavetypeid+"~"+selectedyear+"~"+days;
		
		ajaxHelper.complexAjaxRequest(base+"/calculateLeave", pars, function(res){
		   	if((res!="-1") && (!res.includes("Not"))){
		   		var data = res.split('~');
		   		jQuery('#days_disp').html(data[1]);
		   		jQuery('#days').val(data[1]);
		   		jQuery('#leavelist').html(data[0]);
		    } else if(res.includes("Not")){ 
		    	jQuery('#days_disp').html('');
		    	jQuery('#days').val('');
		   		jQuery('#leavelist').html('');
		    	alert(res);
		    } else{
		    	jQuery('#days_disp').html('');
		    	jQuery('#days').val('');
		   		jQuery('#leavelist').html('');
		    	alert("Invalid live date, Select valid date.");
		    }
		 });
	} else{
		alert("Please fill the Required field.");
	}
}


function addLeave(base){
	var employee_id = jQuery('#emp_id').val();
	var leavetype = jQuery('#leavetype').val();
	var fdate = jQuery('#fdate').val();
	var tdate = jQuery('#tdate').val();
	var reason = jQuery('#reason').val();
	var days = jQuery('#days').val();
	var approverSize = jQuery('#appsize').val();
	var fo = false;
	var msg = "Please fill required field:\n";
	if(employee_id == "")
	{
		fo = true;
		msg = msg + "* Employee Name\n";
	}
	if(leavetype == "")
	{
		fo = true;
		msg = msg + "* Leave Type\n";
	}
	if(fdate == "")
	{
		fo = true;
		msg = msg + "* Leave From Date\n";
	}
	if(tdate == "")
	{
		fo = true;
		msg = msg + "* Leave To Date\n";
	}
	if(reason == "")
	{
		fo = true;
		msg = msg + "* Reason\n";
	}
	if(days == "")
	{
		fo = true;
		msg = msg + "* Leave Validate\n";
	}
	if(approverSize>=1){
		var approvar1 = jQuery('#approvar1').val();
		if(approvar1 == "")
		{
			fo = true;
			msg = msg + "* First Approvar\n";
		}
	}
	if(approverSize>=2){
		var approvar2 = jQuery('#approvar2').val();
		if(approvar2 == "")
		{
			fo = true;
			msg = msg + "* Second Approvar\n";
		}
	}
	if(approverSize>=3){
		var approvar3 = jQuery('#approvar3').val();
		if(approvar3 == "")
		{
			fo = true;
			msg = msg + "* Thierd Approvar\n";
		}
	}
	if(approverSize>=4){
		var approvar4 = jQuery('#approvar4').val();
		if(approvar4 == "")
		{
			fo = true;
			msg = msg + "* Fourth Approvar\n";
		}
	}
	if(approverSize>=5){
		var approvar5 = jQuery('#approvar5').val();
		if(approvar5 == "")
		{
			fo = true;
			msg = msg + "* Fifth Approvar\n";
		}
	}
	
	if(fo)
	{
		alert(msg);
		return;
	}
	
	jQuery('#leaveInfo').ajaxForm({
    success:function(res) {
    	if(res == employee_id){
	 	   	window.location.href=base+"/leave";
	    }
     },
     dataType:"text"
   }).submit();
}

function getQueryData(base,type)
{
	var searchvalue = jQuery('#monthyear').val();
	var emp_id = jQuery('#emp_id').val();
		
	if(searchvalue!="" && emp_id != "") {
		var pars =  emp_id+"~"+searchvalue;
		if(type=="Attendance"){
			ajaxHelper.complexAjaxRequest(base+"/searchattendance", pars, function(res){
				 if(res!="-1"){
					// alert(res);
				 	jQuery('#searchattendance').html(res);
				 }
			});
		} else if(type=="PaySlip"){
			ajaxHelper.complexAjaxRequest(base+"/searchpayslip", pars, function(res){
				 if(res!="-1"){
				 	jQuery('#payslipdiv').html(res);
				 	
				 	var empid = jQuery('#slip_emp_id').val();
				 	
				 	if(empid==0){
				 		jQuery('#btn_download').prop( "disabled", true);
				 	} else{
				 		jQuery('#btn_download').prop( "disabled", false);
				 	}
				 }
			});
		} else{
			ajaxHelper.complexAjaxRequest(base+"/searchleave", pars, function(res){
				 if(res!="-1"){
					// alert(res);
				 	jQuery('#searchleave').html(res);
				 }
			});
		}
	}	
	
	if(monyear!=''){
		var monyear = searchvalue.split(', ');
		var year = monyear[1];
		var mon = getIntMonth(monyear[0]);
		
		jQuery('#dow_month').val(mon);
		jQuery('#dow_year').val(year);
	}
}

function getIntMonth(mon){
	if(mon=='January')
		return 1;
	else if(mon=='February')
		return 2;
	else if(mon=='March')
		return 3;
	else if(mon=='April')
		return 4;
	else if(mon=='May')
		return 5;
	else if(mon=='June')
		return 6;
	else if(mon=='July')
		return 7;
	else if(mon=='August')
		return 8;
	else if(mon=='September')
		return 9;
	else if(mon=='October')
		return 10;
	else if(mon=='November')
		return 11;
	else 
		return 12;
}

function salaryBreakdown(){
	var gross = jQuery('#salgross').val();
	if((gross=='') || (gross==0))
		return;
	var basic = ((gross * 60)/100);
	var housing = ((basic * 40)/100);
	var medical = ((gross * 9)/100);
	var transport = ((gross * 7)/100);
	var lunch = jQuery('#sallunch').val();
	if(lunch=="")
		lunch = 0.0;
	var tax = jQuery('#saltax').val();
	if(tax=="")
		tax = 0.0;
	
	jQuery('#salbasic').val(basic);
	jQuery('#salhousing').val(housing);
	jQuery('#salmedical').val(medical);
	jQuery('#saltransport').val(transport);
	jQuery('#sallunch').val(lunch);
	jQuery('#saltax').val(tax);
	
	
}
function addSalary(base,callfrom){
	var finid 				= jQuery('#salfinid').val();
	var gross 				= jQuery('#salgross').val();
	var basic 				= jQuery('#salbasic').val();
	var housing 		= jQuery('#salhousing').val();
	var medical 		= jQuery('#salmedical').val();
	var transport 			= jQuery('#saltransport').val();
	var lunch 		= jQuery('#sallunch').val();
	var tax 			= jQuery('#saltax').val();
	var appdate 			= jQuery('#salappdate').val();
	
	var hidfinid 				= jQuery('#hidsalfinid').val();
	var hidgross 				= jQuery('#hidsalgross').val();
	var hidbasic 				= jQuery('#hidsalbasic').val();
	var hidhousing 		= jQuery('#hidsalhousing').val();
	var hidmedical 		= jQuery('#hidsalmedical').val();
	var hidtransport 			= jQuery('#hidsaltransport').val();
	var hidlunch 		= jQuery('#hidsallunch').val();
	var hidtax 			= jQuery('#hidsaltax').val();
	var hidappdate 			= jQuery('#hidsalappdate').val();
	
	var fo = false;
	
	if (finid == "") {
		fo = true;
		jQuery('#rfinid').show();
	} else{
		jQuery('#rfinid').hide();
	}
		
	
	if (gross == "") {
		fo = true;
		jQuery('#rgross').show();
	} else{
		jQuery('#rgross').hide();
	}
	
	if (basic == "") {
		fo = true;
		jQuery('#rbasic').show();
	} else{
		jQuery('#rbasic').hide();
	}
	
	if (housing == "") {
		fo = true;
		jQuery('#rhousing').show();
	} else{
		jQuery('#rhousing').hide();
	}
	
	if (medical == "") {
		fo = true;
		jQuery('#rmedical').show();
	} else{
		jQuery('#rmedical').hide();
	}
	
	if (transport == "") {
		fo = true;
		jQuery('#rtransport').show();
	} else{
		jQuery('#rtransport').hide();
	}
	
	if (lunch == "") {
		jQuery('#lunch').val('0.0');
	}
	
	if (tax == "") {
		jQuery('#tax').val('0.0');
	}
	
	if (appdate == "") {
		fo = true;
		jQuery('#rappdate').show();
	} else{
		jQuery('#rappdate').hide();
	}
	var change = false;
	if ((finid != hidfinid) || (gross != hidgross) || (basic != hidbasic) || (housing != hidhousing) || (medical != hidmedical) || (transport != hidtransport) || (lunch!=hidlunch) || (tax!=hidtax) || (appdate!=hidappdate)){
		change = true;
	}
	
	if ((!fo) && (change)) {	
		jQuery('#salarydetails').ajaxForm({
			success : function(res) {
				if (res == 'OK') {
					if(callfrom=='payslip')
						window.location.href = base + "/payslip";
					else{
						jQuery('#package').val(gross);
						jQuery('#salaryModal').modal('hide');
					}
				}
	
				if (res == 'ERROR') {
					alert("Have some error.");
				}
			},
			dataType : "text"
		}).submit();
	} else{
		window.location.href = base + "/payslip";
	}
}

function CalculateGross(){
	var bstbp = jQuery('#bstbp').val();
	var soow = jQuery('#soow').val();
	var soho = jQuery('#soho').val();
	var pftnh = jQuery('#pftnh').val();
	var sfapl = jQuery('#sfapl').val();
	var bta = jQuery('#bta').val();
	var ha = jQuery('#ha').val();
	var ma = jQuery('#ma').val();
	var ta = jQuery('#ta').val();
	
	if(bstbp=="")
		bstbp = 0;
	if(soow=="")
		soow = 0;
	if(soho=="")
		soho = 0;
	if(pftnh=="")
		pftnh = 0;
	if(sfapl=="")
		sfapl = 0;
	if(bta=="")
		bta = 0;
	if(ha=="")
		ha = 0;
	if(ma=="")
		ma = 0;
	if(ta=="")
		ta = 0;
	
	var grosesal = parseFloat(bstbp) + parseFloat(soow) + parseFloat(soho) + parseFloat(pftnh) + parseFloat(sfapl) + parseFloat(bta) + parseFloat(ha) + parseFloat(ma) + parseFloat(ta);
	
	jQuery('#total').val(grosesal);
	
	var deduct = jQuery('#deduct').val();
	var arrear = jQuery('#arrears').val();
	
	if(deduct=="")
		deduct = 0;
	
	if(arrear=="")
		arrear = 0;
	
	var netsal = (grosesal - parseFloat(deduct)) + parseFloat(arrear);
	
	jQuery('#netsal').val(netsal);
	
	var bonus = jQuery('#bonus').val();

	if(bonus=="")
		bonus = 0;
	
	var bank = netsal + parseFloat(bonus);
	
	jQuery('#bank').val(bank);
}

function CalculateDeduction(){
	var hma = jQuery('#hma').val();
	var cfhi = jQuery('#cfhi').val();
	var cfhoi = jQuery('#cfhoi').val();
	var lwp = jQuery('#lwp').val();
	var lunch = jQuery('#lunch').val();
	var tax = jQuery('#tax').val();
		
	if(hma=="")
		hma = 0;
	if(cfhi=="")
		cfhi = 0;
	if(cfhoi=="")
		cfhoi = 0;
	if(lwp=="")
		lwp = 0;
	if(lunch=="")
		lunch = 0;
	if(tax=="")
		tax = 0;
		
	var deduct = parseFloat(hma) + parseFloat(cfhi) + parseFloat(cfhoi) + parseFloat(lwp) + parseFloat(lunch) + parseFloat(tax);
	
	jQuery('#deduct').val(deduct);
	
	
	var grosesal = jQuery('#total').val();
	var arrear = jQuery('#arrears').val();
	
	if(total=="")
		total = 0;
	
	if(arrear=="")
		arrear = 0;
	
	var netsal = (parseFloat(grosesal) - deduct) + parseFloat(arrear);
	
	jQuery('#netsal').val(netsal);
	
	var bonus = jQuery('#bonus').val();

	if(bonus=="")
		bonus = 0;
	
	var bank = netsal + parseFloat(bonus);
	
	jQuery('#bank').val(bank);
}


function updatePayslip(base){
	jQuery('#payslipdetails').ajaxForm({
	    success:function(res) {
	    	if(res == "OK"){
	    		showMsg("Pay Slip Updated.","Message");
		    }
	     },
	     dataType:"text"
	}).submit();
}

function deletePayslip(base){
	var r = confirm("Are you sure you want to permanently delete these Pay Slip?");
	if (r == true) {
		var emp_id = jQuery('#emp_id').val();
		var mon = jQuery('#month').val();
		var year = jQuery('#year').val();
		
		var pars =  emp_id+"~"+mon+"~"+year;
		
		ajaxHelper.complexAjaxRequest(base+"/deletepayslip", pars, function(res){
			 if(res!="-1"){
				 window.location.href = base + "/payslip";
			 }
		});
	}
}

function addPolicy(base) {
	var pname = jQuery('#pname').val();
	var fpath = jQuery('#pdfdoc').val();
	
	if(pname==""){
		jQuery('#footerpdf span').html("Please insert name for policy.");
		return;
	}
	if (fpath=="") {
		jQuery('#footerpdf span').html("Please select a PDF for save the policy.");
		return;
	}
	
	jQuery('#policyInfo').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				jQuery('#pname').val('');
				jQuery('#pdfdoc').val();
				window.location.href = base + "/policy";
			}
			if (res == 'ERROR') {
				jQuery('#footer span').html("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function showPolicy() {
	var val = jQuery('#policyname').val();
	var domain = jQuery('#domainname').val();
	var path = val.split('~')
	jQuery('#pdf_policy').attr({
		data: domain+path[1]
	});
}

function deletPolicy(base){
	var r = confirm("Are you sure you want to permanently delete these Policy?");
	if (r == true) {
		var val = jQuery('#policyname').val();
		var path = val.split('~')
		
		var id = path[0];
		
		var pars =  id;
		
		ajaxHelper.complexAjaxRequest(base+"/deletepolicy", pars, function(res){
			 if(res!="-1"){
				 window.location.href = base + "/policy";
			 }
		});
	}
}

function saveOnboardingInfo(base) {
	
	var jobtitle 	= jQuery('#jobtitle').val();
	var noa = jQuery('#noa').val();
	var designation = jQuery('#designation').val();
	var loc = jQuery('#loc').val();
	var responsiblefor = jQuery('#responsible').val();
	var rptmanager = jQuery('#rptmanager').val();
	var department = jQuery('#department').val();
	var appdate =  jQuery('#appdate').val();
	var probationary =  jQuery('#probationary').val();
	var compackage =  jQuery('#package').val();
	var jobdesc =  jQuery('#jobdesc').val();
		
	var fo = false;
	var msg = "Please fill required field:</br>";
	
	if (jobtitle == "") {
		fo = true;
		msg = msg + "* Job Title</br>";
	}
	if (noa == "") {
		fo = true;
		msg = msg + "* Nature of the Appointment</br>";
	}
	
	if (designation == "") {
		fo = true;
		msg = msg + "* Designation</br>";
	}
	
	if (loc == "") {
		fo = true;
		msg = msg + "* loc</br>";
	}
	
	if (responsiblefor == "") {
		fo = true;
		msg = msg + "* Responsible for</br>";
	}
	
	if (rptmanager == "") {
		fo = true;
		msg = msg + "* Reporting Manager</br>";
	}
	
	if (department == "") {
		fo = true;
		msg = msg + "* Department</br>";
	}
	
	if (appdate == "") {
		fo = true;
		msg = msg + "* Effective date of Appoinment</br>";
	}
	
	if (probationary == "") {
		fo = true;
		msg = msg + "* Probationary Period</br>";
	}
	
	if (compackage == "") {
		fo = true;
		msg = msg + "* Compensation Package</br>";
	}
	
	if(jobdesc == ""){
		fo = true;
		msg = msg + "* Job Description</br>";
	}
		
	if (fo) {
		showMsg(msg, "Error");
		return;
	}
	
	jQuery('#onboarding_info').ajaxForm({
		success : function(res) {
			if (res == 'OK') {
				window.location.href = base + "/onboarding";
			}

			if (res == 'ERROR') {
				showMsg("Have some error.");
			}
		},
		dataType : "text"
	}).submit();
}

function getOnboardingLetter(base)
{
	var docname = jQuery('#docname').val();
	
	if(docname!="") {
		var pars =  docname;
		ajaxHelper.complexAjaxRequest(base+"/onboardingletter", pars, function(res){
			 jQuery('#bodydisplay').html(res);
			 
		});
	}	
}
