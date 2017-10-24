<script type="text/javascript" src="${rc.contextPath}/js/custom/profile.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#joining_date').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#birthdate_real').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#birthdate_certificate').datepicker({ dateFormat: 'dd-mm-yy' });
});
</script>
<div class="container middle_page registration">
    <div class="col-xs-4">
        <a href="${rc.contextPath}/"><img src="images/profile_logo.png" alt="" class="logo header_logo"/></a>
    </div>
    <form class="form-horizontal" name="register_employee" id="register_employee" action="${rc.contextPath}/register_employee" method="post">
    <!--<div class="col-xs-4">
    	<input type="file" class="logo form-control header_logo" name="profile_img" id="profile_img"  onchange="readURL(this,'profile_img_show',100,100)"/>
    	<input type="hidden" name="photo_path" value=""/>
    </div>
    <div class="col-xs-2 col-xs-offset-2">
    	<img src="images/user_profile.png" alt="" class="logo" id="profile_img_show" name="profile_img_show"/>
    </div>
    <div class="col-xs-2 col-xs-offset-10">
    <a href="#" data-target="#employee_image" data-toggle="modal" >Add Image</a>
    </div>-->
    <!--<div class="col-xs-2">
        <select class="form-control">
            <option value="">Employee 1</option>
            <option value="">Employee 2</option>
            <option value="">Employee 3</option>
        </select>
    </div>
    <div class="col-sm-1">
        <a href="dashboard.html"><img src="images/back_button.png" alt="" /></a>
    </div>-->
    <div class="col-xs-12 emp_profile"> 
            <div class="form-group">
                <div class="col-sm-3">
                <input type="text" class="form-control" id="fname" name="fname" placeholder="Type first name">
                </div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="lname" name="lname" placeholder="Type last name">
                </div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="alias" name="alias" placeholder="Type Alias name">
                </div>
            </div>
            <div class="form-group">
                <label for="designation1" class="col-sm-3 control-label">Designation</label>
                <div class="col-sm-3">
                    <select class="form-control" name="designation1" id="designation1">
                    <option value="">Please select</option>
		            <#list designation as desig>
		                <option value="${desig.id}">${desig.datavalue}</option>   
		            </#list>             
		            </select>
                </div>
                <div class="col-sm-3">
                    <select class="form-control" name="designation2" id="designation2">
                    <option value="">Please select</option>
		            <#list designation as desig>
		                <option value="${desig.id}">${desig.datavalue}</option>   
		            </#list>             
		            </select>
                </div>
            </div>
            <div class="form-group">
                <label for="designation1" class="col-sm-3 control-label">Company</label>
                <div class="col-sm-3">
                    <select class="form-control" name="company" id="company">
                    	<option value="">Please select</option>
                        <#list company as com>
		                <option value="${com.id}" >${com.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label for="designation1" class="col-sm-3 control-label">Department</label>
                <div class="col-sm-3">
                    <select class="form-control" name="department" id="department">
                    	<option value="">Please select</option>
                        <#list department as dep>
                        <option value="${dep.id}" >${dep.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <!--<div class="form-group">
                
            </div> -->
            <div class="form-group">
                <label for="official_mail" class="col-sm-3 control-label">Official Email</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="official_mail" id="official_mail" />
                </div>
                <label for="personal_mail" class="col-sm-3 control-label">Personal Email</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="personal_mail" id="personal_mail" />
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-3 control-label">Phone No:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="phone" id="phone" />
                </div>
                <label for="referral" class="col-sm-3 control-label">Referral No:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="referral" id="referral" />
                </div>
            </div>
            <div class="form-group">
                <label for="joining_date" class="col-sm-3 control-label">Joining Date</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="joining_date" id="joining_date" />
                </div>
                <label for="skype_id" class="col-sm-3 control-label">Skype ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="skype_id" id="skype_id" />
                </div>
            </div>
            <div class="form-group">
                <label for="previous_work" class="col-sm-3 control-label">Previous Work place</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="previous_work" id="previous_work" />
                </div>
                <label for="reporting_manager" class="col-sm-3 control-label">Reporting Manager</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="reporting_manager" id="reporting_manager" />
                </div>
            </div> 
            <div class="form-group">
                <label for="birthdate_real" class="col-sm-3">Real Date of Birth</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="birthdate_real" id="birthdate_real" />
                </div>
                <label for="birthdate_certificate" class="col-sm-3">Certificate Date of Birth</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="birthdate_certificate" id="birthdate_certificate" />
                </div>
            </div>
            <div class="form-group">
                <label for="education" class="col-sm-3">Education</label>
                <div class="col-sm-3">
                    <select class="form-control" name="education" id="education">
                    	<option value="">Please select</option>
                        <#list education as edu>
		                	<option value="${edu.id}" >${edu.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">NID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="nid" id="nid" />
                </div>
                <label class="col-sm-3">Passport</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="passport" id="passport" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Marital Status</label>
                <div class="col-sm-3">
                    <select class="form-control" id="maritial_status" name="maritial_status">
                    	<option value="">Please select</option>
                        <#list maritialStatus as mar>
		                	<option value="${mar.id}">${mar.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label class="col-sm-3">No. Of Siblings</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="siblings" id="siblings" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Hobbies</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="hobbies" id="hobbies" />
                </div>
                <label class="col-sm-3">Extra Activities</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="activities" id="activities" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Emergency Phone No.</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="emegency_phone" id="emegency_phone" />
                </div>
                <label class="col-sm-3">Relation</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="relation" id="relation" />
                </div>
            </div>
            <div class="form-group">
                <label for="education" class="col-sm-3">Zodiac Sign</label>
                <div class="col-sm-3">
                    <select class="form-control" id="zodiac" name="zodiac">
                    	<option value="">Please select</option>
                        <#list sign as sig>
		                	<option value="${sig.id}">${sig.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label for="blood" class="col-sm-3">Blood Group</label>
                <div class="col-sm-3">
                    <select class="form-control" id="blood" name="blood">
                    	<option value="">Please select</option>
                        <#list blood as bl>
		                	<option value="${bl.id}">${bl.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Employee ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="emp_num" id="emp_num" />
                </div>
                <label class="col-sm-3">Bank Name</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="bank_name" id="bank_name" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Corporate Bank Account</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="bank_account" id="bank_account" />
                </div>
                <label class="col-sm-3">Official Phone No.</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="officie_phone" id="officie_phone" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Extension</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="extension" id="extension" />
                </div>
                <label class="col-sm-3">Office Chat Nick</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="chat_nick" id="chat_nick" />
                </div>
            </div>
            <div class="form-group">
                <label for="present_address" class="col-sm-3">Present Address</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="present_address" id="present_address" />
                </div>
            </div>
            <div class="form-group">
                <label for="permanent_address" class="col-sm-3">Permanent Address</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="permanent_address" id="permanent_address" />
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-3">System User Type</label>
                <div class="col-sm-3">
                    <select class="form-control" id="userType" name="userType">
                    	<option value="">Please select</option>
                        <#list utype as ut>
		                	<option value="${ut.id}">${ut.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label for="" class="col-sm-3">Team</label>
                <div class="col-sm-3">
                    <select class="form-control" id="team" name="team">
                    	<option value="">Please select</option>
                        <#list allTeam as team>
		                	<option value="${team.teamId}">${team.teamName}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <!--<div class="form-group">
                <label for="ablout_me" class="col-sm-2">About me</label>
                <div class="col-sm-8">
                    <textarea rows="3" cols="50" class="form-control" name="ablout_me" id="ablout_me" ></textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Appointment Letter</label>
                <div class="col-sm-3">
                    <input type="file" class="" name="appointment" id="appointment" />
                    <input type="hidden" name="appointment_path" value=""/>
                </div>
                <label class="col-sm-2">NDA</label>
                <div class="col-sm-3">
                    <input type="file" class="" name="nda" id="nda" />
                    <input type="hidden" name="nda_path" value=""/>
                </div>
            </div>-->
            <div class="form-group">
                <div class="col-sm-offset-5 col-sm-2">
                    <button type="button" class="btn btn-primary" onclick="registration('${rc.contextPath}')"><img src="images/submit_for_approval.png" alt="" /> Submit</button>
                </div>
            </div>
    </div>
    </form>
    <div class="col-xs-3 col-xs-offset-5">
        <img src="images/front_logo_bottom.png" />
        <p class="front_copyright">&copy; All Rights Reserved</p>
    </div>
</div>