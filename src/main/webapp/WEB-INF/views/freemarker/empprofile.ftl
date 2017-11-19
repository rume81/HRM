<div class="container-fluid front_page">
    <div class="col-sm-4 col-xs-12 hr-header">
        <a href="${rc.contextPath}/"><img src="images/profile_logo.png" alt="" class="img-responsive" /></a>
    </div>
    <div class="col-sm-4">
        <img src="<#if selectedemp.profPic.path??>${selectedemp.profPic.path}<#else>images/roundedprofile.png</#if>" alt="" class="profile_round_img circleBase circleprofileimage" />
    </div>
    <div class="col-sm-3 col-xs-6 employee_type">
        <!--<button class="btn btn-primary dropdown-toggle" id="menu1" type="button" data-toggle="dropdown">Employee Profile<span class="caret"></span></button>-->
        <a class="dropdown-toggle" data-toggle="dropdown"><img src="images/toggle_up.png" alt=""/><span id="proftype">Personal Profile</span></a>
        <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">
	      <li role="empprofile"><a role="menuitem" tabindex="-1" href="${rc.contextPath}/empprofile">Personal Profile</a></li>
	      <li role="officialprofile"><a role="menuitem" tabindex="-1" href="${rc.contextPath}/officialprofile">Official Profile</a></li>    
	    </ul>
    </div>
    <div class="col-sm-1 col-xs-6">
        <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
    </div>
    <div class="col-xs-12 emp_profile">
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.first_name!''} ${selectedemp.last_name!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.avator!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <P class="p_designation">I am creating profile as</P>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.designation!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.designation2!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Company</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.company!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Department</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.department!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Official Email</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.email!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Personal Email</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.personal_email!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Phone No.</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.mobile!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Referral No.</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.refferal_no!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Joining Date</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.join_date!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Skype ID</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.skype_id!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Previous Work place</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.prv_work_place!''}</p>
            </div>
            <div class="col-sm-3">
                <p class="p_designation">Reporting Manager</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${rptEmp.employee_name!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Real Date of Birth</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.birthdate_real!''}</p>
            </div> 
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Education</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.education!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Present Address</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.present_address!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">Permanent Address</p>
            </div>
            <div class="col-sm-3">
                <p class="p_value">${selectedemp.permanent_address!''}</p>
            </div>
        </div>
        <div class="col-sm-12">
            <div class="col-sm-3">
                <p class="p_designation">About Me</p>
            </div>
            <div class="col-sm-6">
                <p class="p_value">${selectedemp.about_me!''}</p>
            </div> 
        </div>
        <div class="col-sm-offset-1 col-sm-10">
            <button type="submit" class="btn btn-primary" onclick="EditProfile('${rc.contextPath}')">Edit Profile</button>
        </div>                    
    </div>