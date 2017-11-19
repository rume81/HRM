<div class="container-fluid front_page">

    <div class="col-sm-4 col-xs-12 hr-header">
        <a href="${rc.contextPath}/"><img src="images/profile_logo.png" alt="" class="img-responsive" /></a>
    </div>
    <div class="col-xs-4">
        <img src="<#if selectedemp.profPic.path??>${selectedemp.profPic.path}<#else>images/roundedprofile.png</#if>" alt="" class="profile_round_img circleBase circleprofileimage" />
    </div>
    <div class="col-xs-2 employee_type">
        <!--<button class="btn btn-primary dropdown-toggle" id="menu1" type="button" data-toggle="dropdown">Employee Profile<span class="caret"></span></button>-->
        <a class="dropdown-toggle" data-toggle="dropdown"><img src="images/toggle_up.png" alt=""/><span id="proftype">Official Profile</span></a>
        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
	      <li role="presentation"><a role="menuitem" tabindex="-1" href="${rc.contextPath}/empprofile">Personal Profile</a></li>
	      <li role="presentation"><a role="menuitem" tabindex="-1" href="${rc.contextPath}/officialprofile">Official Profile</a></li>    
	    </ul>
    </div>
    <div class="col-sm-1 col-xs-6">
        <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
    </div>
    <div class="col-xs-12 emp_profile">
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Joining Date:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.join_date!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Employee ID:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.emp_number!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Corporate Bank account:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.bank_acc_no!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Bank Name:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.bank_name!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Official Phone Number:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.offical_phone!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Extension:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.extension!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Official email:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.official_mail!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Skype ID:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.skype_id!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Official Chat Nick:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.chat_nick!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Present Address:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.present_address!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Permanent Address:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.permanent_address!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Reporting Manager:</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.rpt_mgr!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
        	<div class="col-sm-3">
        		<p class="p_designation">Appointment Letter</p>
        	</div>
        	<div class="col-sm-3">
        		<p class="p_downloadling"><a target="_blank" href="<#if selectedemp.appointment.path??>${selectedemp.appointment.path}<#else>#</#if>">Download Appointment Letter</a></p>
        	</div>
        	<div class="col-sm-3">
        		<p class="p_designation">NDA</p>
        	</div>
        	<div class="col-sm-3">
        		<p class="p_downloadling"><a target="_blank" href="<#if selectedemp.nda.path??>${selectedemp.nda.path}<#else>#</#if>">Download NDA</a></p>
        	</div>
        </div>
        <div class="col-sm-offset-1 col-sm-10 col-xs-6 col-xs-offset-3">
            <button type="submit" class="btn btn-primary" onclick="EditProfile('${rc.contextPath}')">Edit Profile</button>
        </div>  
	</div>
    