<script type="text/javascript" src="${rc.contextPath}/js/custom/profile.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#joining_date').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#birthdate_real').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#birthdate_certificate').datepicker({ dateFormat: 'dd-mm-yy' });
});

</script>
<div class="container middle_page registration">
    <div class="col-sm-8 col-xs-12 hr-header">
        <a href="${rc.contextPath}/"><img src="images/profile_logo.png" alt="" class="logo header_logo" /></a>
    </div>
    <form class="form-horizontal" name="edit_employee" id="edit_employee" action="${rc.contextPath}/edit_employee" method="post">
    <div class="col-xs-4">
    	<input type="hidden" name="photo_path" value="<#if selectedemp.profPic.path??>${selectedemp.profPic.path!''}<#else>images/user_profile.png</#if>"/>
    	<input type="hidden" name="emp_id" id="emp_id" value="${selectedemp.emp_id!''}"/>
    </div>
    <div class="col-sm-2 col-sm-offset-2 col-xs-6 col-xs-offset-4">
    	<img src="<#if selectedemp.profPic.path??>${selectedemp.profPic.path!''}<#else>images/user_profile.png</#if>" alt="" class="logo" id="profile_img_show" name="profile_img_show"/>
    	
    </div>
    <div class="col-sm-2 col-sm-offset-2 col-xs-6 col-xs-offset-4">
    	<a href="#" data-target="#employee_image" data-toggle="modal" >Change Image</a>
    </div>
    <div class="col-xs-12 emp_profile"> 
            <div class="form-group">
            	<label for="designation1" class="col-sm-3 control-label">Name</label>
                <div class="col-sm-3">
                <input type="text" class="form-control" id="fname" name="fname" placeholder="Type first name" value="${selectedemp.first_name!''}">
                </div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="lname" name="lname" placeholder="Type last name" value="${selectedemp.last_name!''}">
                </div>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="avator" name="avator" placeholder="Type Alias name" value="${selectedemp.avator!''}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="designation1" class="col-sm-3 control-label">Designation</label>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" name="designation" id="designation">
	                    <option value="">Please select</option>
			            <#list designation as desig>
			                <option value="${desig.id}" <#if selectedemp.designation?? && desig.id==selectedemp.designation>selected</#if>>${desig.datavalue}</option>   
			            </#list>             
			            </select>
		            <#else>
		            	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list designation as desig><#if selectedemp.designation?? && desig.id==selectedemp.designation>${desig.datavalue}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="designation" name="designation" value="${selectedemp.designation!''}">
		            </#if>
                </div>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" name="designation2" id="designation2">
	                    <option value="">Please select</option>
			            <#list designation as desig>
			                <option value="${desig.id}" <#if selectedemp.designation2?? && desig.id==selectedemp.designation2>selected</#if>>${desig.datavalue}</option>   
			            </#list>             
			            </select>
			        <#else>
			        	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list designation as desig><#if selectedemp.designation2?? && desig.id==selectedemp.designation2>${desig.datavalue}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="designation2" name="designation2" value="selectedemp.designation2!''"> 
		            </#if>
                </div>
            </div>
            <div class="form-group">
                <label for="designation1" class="col-sm-3 control-label">Company</label>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" name="company" id="company">
	                    	<option value="">Please select</option>
	                        <#list company as com>
			                <option value="${com.id}" <#if selectedemp.company?? && com.id==selectedemp.company>selected</#if>>${com.datavalue}</option>   
			            	</#list>
	                    </select>
	                <#else>
	                	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list company as com><#if selectedemp.company?? && com.id==selectedemp.company>${com.datavalue}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="company" name="company" value="${selectedemp.company!''}">
		            </#if>
                </div>
                <label for="designation1" class="col-sm-3 control-label">Department</label>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" name="department" id="department">
	                    	<option value="">Please select</option>
	                        <#list department as dep>
	                        <option value="${dep.id}" <#if selectedemp.department?? && dep.id==selectedemp.department>selected</#if>>${dep.datavalue}</option>   
			            	</#list>
	                    </select>
	                <#else>
	                	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list department as dep><#if selectedemp.department?? && dep.id==selectedemp.department>${selectedemp.department!''}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="department" name="department" value="${selectedemp.department!''}">
		            </#if>
                </div>
            </div>
            <div class="form-group">
                <label for="official_mail" class="col-sm-3 control-label">Official Email</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="official_mail" id="official_mail" value="${selectedemp.email!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
                <label for="personal_mail" class="col-sm-3 control-label">Personal Email</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="personal_mail" id="personal_mail" value="${selectedemp.personal_email!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-3 control-label">Phone No</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="phone" id="phone" value="${selectedemp.mobile!''}"/>
                </div>
                <label for="referral" class="col-sm-3 control-label">Referral No</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="referral" id="referral" value="${selectedemp.refferal_no!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="joining_date" class="col-sm-3 control-label">Joining Date</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="joining_date" id="joining_date" value="${selectedemp.join_date!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
                <label for="skype_id" class="col-sm-3 control-label">Skype ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="skype_id" id="skype_id" value="${selectedemp.skype_id!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
            </div>
            <div class="form-group">
                <label for="previous_work" class="col-sm-3 control-label">Previous Work place</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="previous_work" id="previous_work" value="${selectedemp.prv_work_place!''}"/>
                </div>
                <label for="reporting_manager" class="col-sm-3 control-label">Reporting Manager</label>
                <div class="col-sm-3">
                    <select class="form-control" name="reporting_manager" id="reporting_manager">
                    	<option value="">Please select</option>
                        <#list allEmp as emp>
		                	<option value="${emp.emp_id}" <#if selectedemp.rpt_mgr=emp.emp_id>selected</#if>>${emp.employee_name}</option>   
		            	</#list>
                    </select>
                </div>
            </div> 
            <div class="form-group">
                <label for="birthdate_real" class="col-sm-3">Real Date of Birth</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="birthdate_real" id="birthdate_real" value="${selectedemp.birthdate_real!''}"/>
                </div>
                <label for="birthdate_certificate" class="col-sm-3">Certificate Date of Birth</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="birthdate_certificate" id="birthdate_certificate" value="${selectedemp.birthdate_certificate!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="education" class="col-sm-3">Education</label>
                <div class="col-sm-3">
                    <select class="form-control" name="education" id="education">
                    	<option value="">Please select</option>
                        <#list education as edu>
		                	<option value="${edu.id}" <#if selectedemp.education?? && edu.id==selectedemp.education>selected</#if>>${edu.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">NID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="nid" id="nid" value="${selectedemp.nid!''}"/>
                </div>
                <label class="col-sm-3">Passport</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="passport" id="passport" value="${selectedemp.passport!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Marital Status</label>
                <div class="col-sm-3">
                    <select class="form-control" id="maritial_status" name="maritial_status">
                    	<option value="">Please select</option>
                        <#list maritialStatus as mar>
		                	<option value="${mar.id}" <#if selectedemp.maritial_status?? && mar.id==selectedemp.maritial_status>selected</#if>>${mar.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label class="col-sm-3">No. Of Siblings</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="siblings" id="siblings" value="${selectedemp.no_siblings!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Hobbies</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="hobbies" id="hobbies" value="${selectedemp.hobbies!''}"/>
                </div>
                <label class="col-sm-3">Extra Activities</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="activities" id="activities" value="${selectedemp.extra_activitie!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Emergency Phone No.</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="emegency_phone" id="emegency_phone" value="${selectedemp.emergency_phone!''}"/>
                </div>
                <label class="col-sm-3">Relation</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="relation" id="relation" value="${selectedemp.relation!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="education" class="col-sm-3">Zodiac Sign</label>
                <div class="col-sm-3">
                    <select class="form-control" id="zodiac" name="zodiac">
                    	<option value="">Please select</option>
                        <#list sign as sig>
		                	<option value="${sig.id}" <#if selectedemp.zodiac?? && sig.id==selectedemp.zodiac>selected</#if>>${sig.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
                <label for="blood" class="col-sm-3">Blood Group</label>
                <div class="col-sm-3">
                    <select class="form-control" id="blood" name="blood">
                    	<option value="">Please select</option>
                        <#list blood as bl>
		                	<option value="${bl.id}" <#if selectedemp.bloodgroup?? && bl.id==selectedemp.bloodgroup>selected</#if>>${bl.datavalue}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Employee ID</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="emp_num" id="emp_num" value="${selectedemp.emp_number!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
                <label class="col-sm-3">Corporate Bank Name</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="bank_name" id="bank_name" value="${selectedemp.bank_name!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Corporate Bank Account</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="bank_account" id="bank_account" value="${selectedemp.bank_acc_no!''}" <#if usertype!="Admin" && usertype!="Talent Manager">readonly</#if>/>
                </div>
                <label class="col-sm-3">Official Phone No.</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="officie_phone" id="officie_phone" value="${selectedemp.offical_phone!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3">Extension</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="extension" id="extension" value="${selectedemp.extension!''}"/>
                </div>
                <label class="col-sm-3">Office Chat Nick</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="chat_nick" id="chat_nick" value="${selectedemp.chat_nick!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="present_address" class="col-sm-3">Present Address</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="present_address" id="present_address" value="${selectedemp.present_address!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="permanent_address" class="col-sm-3">Permanent Address</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" name="permanent_address" id="permanent_address" value="${selectedemp.permanent_address!''}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="" class="col-sm-3">System User Type</label>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" id="userType" name="userType">
	                    	<option value="">Please select</option>
	                        <#list utype as ut>
			                	<option value="${ut.id}" <#if selectedemp.usertype?? && ut.id==selectedemp.usertype>selected</#if>>${ut.datavalue}</option>   
			            	</#list>
	                    </select>
	                <#else>
	                	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list utype as ut><#if selectedemp.usertype?? && ut.id==selectedemp.usertype>${ut.datavalue}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="userType" name="userType" value="${selectedemp.usertype!''}">
		            </#if>
                </div>
                <label for="" class="col-sm-3">Team</label>
                <div class="col-sm-3">
                	<#if usertype=="Admin" || usertype=="Talent Manager">
	                    <select class="form-control" id="team" name="team">
	                    	<option value="">Please select</option>
	                        <#list allTeam as team>
			                	<option value="${team.teamId}" <#if selectedemp.teamId?? && team.teamId==selectedemp.teamId>selected</#if>>${team.teamName}</option>   
			            	</#list>
	                    </select>
	                <#else>
	                	<input type="text" class="form-control" id="" name="" placeholder="" value="<#list allTeam as team><#if selectedemp.teamId?? && team.teamId==selectedemp.teamId>${team.teamName}<#break></#if></#list>" disabled/>
		            	<input type="hidden" id="team" name="team"  value="${selectedemp.teamId!''}">
		            </#if>
                </div>
            </div>
            <div class="form-group">
                <label for="ablout_me" class="col-sm-3">About me</label>
                <div class="col-sm-9">
                    <textarea rows="3" cols="50" class="form-control" name="ablout_me" id="ablout_me">${selectedemp.about_me!''}</textarea>
                </div>
            </div>
            <div class="form-group">
            	<#if usertype=="Admin" || usertype=="Talent Manager">
                <label class="col-sm-3">Appointment Letter</label>
                <div class="col-sm-3" id="appointment">
                	<object data="<#if selectedemp.appointment.path??>${selectedemp.appointment.path}</#if>" type="application/pdf" width="100%" height="100%">
  						<p><a href="<#if selectedemp.appointment.path??>${selectedemp.appointment.path}</#if>">Faile to load file!</a></p>
					</object>
					<a href="#" data-target="#employee_appoinment" data-toggle="modal" >Change Appointment</a>
                </div>
                </#if>
                <input type="hidden" name="appointment_path" value="${selectedemp.appointment.path!''}"/>
                <#if usertype=="Admin" || usertype=="Talent Manager">
                <label class="col-sm-2">NDA</label>
                <div class="col-sm-3">
                	<object data="<#if selectedemp.nda.path??>${selectedemp.nda.path}</#if>" type="application/pdf" width="100%" height="100%">
  						<p><a href="<#if selectedemp.nda.path??>${selectedemp.nda.path}</#if>">Faile to load file!</a></p>
					</object>
					<a href="#" data-target="#employee_nda" data-toggle="modal" >Change NDA</a>
                </div>
                </#if>
                <input type="hidden" name="nda_path" value="${selectedemp.nda.path!''}"/>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-5 col-sm-2 col-xs-6 col-xs-offset-3">
                    <button type="button" class="btn btn-primary" onclick="editprofile('${rc.contextPath}')"><img src="images/submit_for_approval.png" alt="" class="" /> Submit</button>
                </div>
            </div>
    </div>
    </form>
    
<!-- Modal -->
<div class="modal fade" id="employee_image" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog image_modal" role="document">
    <div class="modal-content">
      <form class="form-horizontal" name="edit_employee_photo" id="edit_employee_photo" action="${rc.contextPath}/edit_employee_photo" method="post">
	      <div class="modal-body">
	      	<input type="file" accept="image/*" class="logo form-control header_logo" name="profile_img" id="profile_img"  onchange="readURL(this,'new_upload',200,200)"/>
	        <img src="<#if selectedemp.profPic.path??>${selectedemp.profPic.path!''}<#else>images/user_profile.png</#if>" alt="" class="image_hover" id="new_upload" width="300" height="300">
	        <input type="hidden" name="emp_id_ph" id="emp_id_ph" value="${selectedemp.emp_id!''}"/>
	      </div>
	      <div class="modal-footer" id="footer"><span></span>
	      	<button type="button" id="imageSave" class="btn btn-primary" onclick="saveprofileImage('${rc.contextPath}')">Save Image</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="employee_appoinment" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog image_modal" role="document">
    <div class="modal-content">
      <form class="form-horizontal" name="edit_employee_appoinment" id="edit_employee_appoinment" action="${rc.contextPath}/edit_employee_appoinment" method="post">
	      <div class="modal-body">
	      	<input type="file" accept=".pdf" class="logo form-control header_logo" name="appoinment" id="appoinment"  onchange="readPDF(this,'appointment_view')"/>
	        <object id="appointment_view" data="<#if selectedemp.appointment.path??>${selectedemp.appointment.path}</#if>" type="application/pdf" width="300px" height="300px"></object>
	        <input type="hidden" name="emp_id_ap" id="emp_id_ap" value="${selectedemp.emp_id!''}"/>
	      </div>
	      <div class="modal-footer" id="footerapp"><span></span>
	      	<button type="button" id="imageSave" class="btn btn-primary" onclick="saveAppointment('${rc.contextPath}')">Save Appointment</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="employee_nda" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog image_modal" role="document">
    <div class="modal-content">
      <form class="form-horizontal" name="edit_employee_nda" id="edit_employee_nda" action="${rc.contextPath}/edit_employee_nda" method="post">
	      <div class="modal-body">
	      	<input type="file" accept=".pdf" class="logo form-control header_logo" name="nda" id="nda"  onchange="readPDF(this,'nda_view')"/>
	        <object id="nda_view" data="<#if selectedemp.nda.path??>${selectedemp.nda.path}</#if>" type="application/pdf" width="300px" height="300px"></object>
	        <input type="hidden" name="emp_id_nda" id="emp_id_nda" value="${selectedemp.emp_id!''}"/>
	      </div>
	      <div class="modal-footer" id="footernda"><span></span>
	      	<div class="col-xs-6 modal-close">
	      	<button type="button" id="imageSave" class="btn btn-primary" onclick="saveNda('${rc.contextPath}')">Save NDA</button>
	      	</div>
	      	<div class="col-xs-6 modal-close">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	        </div>
	      </div>
      </form>
    </div>
  </div>
</div>