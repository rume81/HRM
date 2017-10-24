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
					// alert(res);
				 	jQuery('#payslipdiv').html(res);
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
}

function salaryBreakdown(){
	var gross = jQuery('#salgross').val();
	if((gross=='') || (gross==0))
		return;
	var basic = ((gross * 60)/100);
	var housing = ((basic * 40)/100);
	var medical = ((gross * 9)/100);
	var transport = ((gross * 7)/100);
	var lunch = 0.0;
	var tax = 0.0
	
	jQuery('#salbasic').val(basic);
	jQuery('#salhousing').val(housing);
	jQuery('#salmedical').val(medical);
	jQuery('#saltransport').val(transport);
	jQuery('#sallunch').val(lunch);
	jQuery('#saltax').val(tax);
	
	
}
function addSalary(base){
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
					window.location.href = base + "/payslip";
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