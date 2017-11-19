<script type="text/javascript">
$(document).ready(function() {
	$('#appdate').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#salappdate').datepicker({ dateFormat: 'dd-mm-yy' });
});
</script>
<div class="container middle_page">
	<div class="header-top">
	    <div class="col-sm-3 col-xs-12 hr-header">
	        <a href="${rc.contextPath}/">
	        	<img src="images/profile_logo.png" alt="" class="" />
	        </a>
	    </div>
	    <div class="col-sm-5 col-xs-12 attendance-date">
	    	<select class="form-control" name="docname" id="docname" onchange="getOnboardingLetter('${rc.contextPath}')">
		    	<option value="">Please select</option>
		    	<option value="OFFER">Offer Letter</option>
		    	<option value="APPOINTMENT">Appointment Letter</option>
		    	<option value="NDA">NDA</option>
		    	<option value="RELEASE">Release Letter</option>
		    	<option value="EXPERIENCE">Experience Certificate</option>
		    	<option value="RECOMMENDATION">Recommendation Letter</option>
			</select>
		</div>

	    <div class="col-sm-3 col-xs-6 attendance-header">
	    	<h3>On Boarding</h3>
	    </div>
	    <div class="col-sm-1 col-xs-6 back-button">
	       <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
	    </div>
	</div>    
    <div class="col-xs-12 emp_profile">
        <div class="col-sm-3 col-xs-12 leave-button-group no-padding-mb">
        	<button type="button" class="btn btn-default leave-button" onclick="saveOnboardingInfo('${rc.contextPath}')">Save Information</button>
            <button type="button" class="btn btn-default  color-button" onclick="">Download</button>
        </div>   
        <div class="col-sm-9 col-xs-12 no-padding-mb" id="bodydisplay">
        	<form class="form-horizontal" name="onboarding_info" id="onboarding_info" action="${rc.contextPath}/onboarding_info" method="post">
        	<div class="onboarding">
                <label for="job_title" class="col-sm-2">Job Title:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="jobtitle" id="jobtitle" value="${selecteduser.jobtitle!''}"/>
                    <input type="hidden" name="emp_id" id="emp_id" value="${selecteduser.emp_id!''}"/>
                </div>
                <label for="noa" class="col-sm-4 control-label">Nature of the Appointment:</label>
                <div class="col-sm-3">
                    <select class="form-control" name="noa" id="noa">
	                	<option value="">Please select</option>
	                	<option value="Part Time" <#if selecteduser.jobnature?? && selecteduser.jobnature=="Part Time">Selected</#if>>Part Time</option>
			        	<option value="Full Time" <#if selecteduser.jobnature?? && selecteduser.jobnature=="Full Time">Selected</#if>>Full Time</option>             
			        </select>
                </div>
            </div>
            <div class="onboarding">
                <label for="designation" class="col-sm-2 control-label">Designation:</label>
                <div class="col-sm-3">
                    <select class="form-control" name="designation" id="designation">
	                	<option value="">Please select</option>
			        	<#list designation as desig>
			            	<option value="${desig.id}" <#if selecteduser.designation?? && desig.id==selecteduser.designation>selected</#if>>${desig.datavalue}</option>   
			            </#list>             
			        </select>
                </div>
                <label for="personal_mail" class="col-sm-4 control-label">Place(Workstation location):</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="loc" id="loc" value="${selecteduser.workstation!''}"/>
                </div>
            </div>
            <div class="onboarding">
                <label for="responsible" class="col-sm-2 control-label">Responsible for:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="responsible" id="responsible" value="${selecteduser.responsiblefor!''}"/>
                </div>
                <label for="rptmanager" class="col-sm-4 control-label">Reporting Manager:</label>
                <div class="col-sm-3">
                    <select class="form-control" name="rptmanager" id="rptmanager">
                    	<option value="">Please select</option>
                        <#list allEmp as emp>
		                	<option value="${emp.emp_id}" <#if selecteduser.rpt_mgr=emp.emp_id>selected</#if>>${emp.employee_name}</option>   
		            	</#list>
                    </select>
                </div>
            </div>
            <div class="onboarding">
                <label for="dept" class="col-sm-2 control-label">Department:</label>
                <div class="col-sm-3">
                    <select class="form-control" name="department" id="department">
	                	<option value="">Please select</option>
	                    <#list department as dep>
	                        <option value="${dep.id}" <#if selecteduser.department?? && dep.id==selecteduser.department>selected</#if>>${dep.datavalue}</option>   
			            </#list>
	                </select>
                </div>
                <label for="appdate" class="col-sm-4 control-label">Effective date of Appoinment:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" name="appdate" id="appdate" value="${selecteduser.join_date!''}"/>
                </div>
            </div>
            <div class="onboarding">
                <label for="probationary" class="col-sm-2 control-label">Probationary Period:</label>
                <div class="col-sm-3">
                    <select class="form-control" name="probationary" id="probationary">
	                	<option value="">Please select</option>
	                	<option value="One Months" <#if selecteduser.probation_period?? && selecteduser.probation_period=="One Months">selected</#if>>One Months</option>
	                	<option value="Two Months" <#if selecteduser.probation_period?? && selecteduser.probation_period=="Two Months">selected</#if>>Two Months</option>
	                	<option value="Three Months" <#if selecteduser.probation_period?? && selecteduser.probation_period=="Three Months">selected</#if>>Three Months</option>
			        	<option value="Six Months" <#if selecteduser.probation_period?? && selecteduser.probation_period=="Six Months">selected</#if>>Six Months</option>             
			        </select>
                </div>
                <label for="package" class="col-sm-4 control-label">Compensation Package:</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control" name="package" onkeypress="validate(event)" id="package" value="<#if activeSal??>${activeSal.gross!''}</#if>" readonly/>
                    
                </div>
                <button type="button" class="btn btnaddsal col-sm-1 " data-toggle="modal" data-target="#salaryModal"><i class="fa fa-pencil-square-o"></i></button>
            </div>
            <div class="onboarding">
                <label for="jobdesc" class="col-sm-2 control-label">Job Description:</label>
                <div class="col-sm-10">
                    <textarea rows="4" cols="50" class="form-control jobdesc" name="jobdesc" id="jobdesc">${selecteduser.job_desc!''}</textarea>
                </div>
            </div>
            </form>
        </div>
    </div>
<!-- salary Modal Start -->
<#if user.usertype=="Talent Manager">
	<div class="modal fade notice-modal leave_modal" id="salaryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">Salary Breakdown</h4>
	            </div>
	            <form name="salarydetails" id="salarydetails" action="${rc.contextPath}/salarybrackdown" method="post">
	                <div class="modal-body">
	                    <div class="form-group">
	                    	<input type="hidden" id="salemp_id" name="salemp_id" value="${selecteduser.emp_id?string["0"]}"/>
	                    	<input type="hidden" id="salid" name="salid" value="<#if activeSal?? && activeSal.salid??>${activeSal.salid}<#else>-1</#if>"/>
	                        <label class="control-label col-sm-4">Employee ID</label>
	                        <div class="col-sm-6">          
	                            <input type="text" class="form-control" id="salfinid" name="salfinid" value="<#if activeSal?? && activeSal.emp_finance_id??>${activeSal.emp_finance_id}</#if>"/>
	                            <input type="hidden" id="hidsalfinid" value="<#if activeSal?? && activeSal.emp_finance_id??>${activeSal.emp_finance_id}</#if>"/>
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rfinid" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Gross Salary</label>
	                        <div class="col-sm-6">          
	                            <input type="text" class="form-control salary-breakdown-input" id="salgross" name="salgross" onkeypress='validate(event)' onblur="salaryBreakdown()" value="<#if activeSal?? && activeSal.gross??>${activeSal.gross}</#if>"/>
	                            <input type="hidden" id="hidsalgross" value="<#if activeSal?? && activeSal.gross??>${activeSal.gross}</#if>"/>
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rgross" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Basic Salary</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="salbasic" name="salbasic" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.basic??>${activeSal.basic}</#if>"/>
	                            <input type="hidden" id="hidsalbasic" value="<#if activeSal?? && activeSal.basic??>${activeSal.basic}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rbasic" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Housing allowance</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="salhousing" name="salhousing" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.housing??>${activeSal.housing}</#if>"/>
	                            <input type="hidden" id="hidsalhousing" value="<#if activeSal?? && activeSal.housing??>${activeSal.housing}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rhousing" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Medical allowance</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="salmedical" name="salmedical" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.medical??>${activeSal.medical}</#if>"/>
	                            <input type="hidden" id="hidsalmedical" value="<#if activeSal?? && activeSal.medical??>${activeSal.medical}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rmedical" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Transportation</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="saltransport" name="saltransport" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.transport??>${activeSal.transport}</#if>"/>
	                            <input type="hidden" id="hidsaltransport" value="<#if activeSal?? && activeSal.transport??>${activeSal.transport}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rtransport" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Lunch Deduction</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="sallunch" name="sallunch" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.lunch??>${activeSal.lunch}</#if>"/>
	                            <input type="hidden" id="hidsallunch" value="<#if activeSal?? && activeSal.lunch??>${activeSal.lunch}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rlunch" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Tax Deduction</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control salary-breakdown-input" id="saltax" name="saltax" onkeypress='validate(event)' value="<#if activeSal?? && activeSal.tax??>${activeSal.tax}</#if>"/>
	                            <input type="hidden" id="hidsaltax" value="<#if activeSal?? && activeSal.tax??>${activeSal.tax}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rtax" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="control-label col-sm-4">Applied Date</label>
	                        <div class="col-sm-6">
	                            <input type="text" class="form-control" id="salappdate" name="salappdate" value="<#if activeSal?? && activeSal.applieddate??>${activeSal.applieddate}</#if>"/>
	                            <input type="hidden" id="hidsalappdate" value="<#if activeSal?? && activeSal.applieddate??>${activeSal.applieddate}</#if>"/>    
	                        </div>
	                        <div class="col-sm-2">
	                        	<span id="rappdate" class="salary-breakdown-required">* Required</span>
	                        </div>
	                    </div>
	                </div>
	                <div class="modal-footer">
	                    <div class="col-xs-6 modal-close">
	                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Cancel</button>
	                    </div>
	                    <div class="col-xs-6 modal-accept">
	                        <button type="button" class="btn btn-default" onclick="addSalary('${rc.contextPath}','onboarding')"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Done</button>
	                    </div>
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
</#if>
<!--Salary Modal End -->