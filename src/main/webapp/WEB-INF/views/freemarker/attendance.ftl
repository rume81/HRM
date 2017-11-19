<script>
$(document).ready(function() {
	$('#fdate').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#tdate').datepicker({ dateFormat: 'dd-mm-yy' });
	$('#lodate').datepicker({ dateFormat: 'dd-mm-yy' });
	
	var text_max = 2000;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#reason').keyup(function() {
        var text_length = $('#reason').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });
    
    $('#copydate').hide();
    $('#fdate').change(function() {
    	var t_length = $('#fdate').val().length;
    	if(t_length>0){
    		$('#copydate').show();
    	}
    });
    
    $("#resign_reason").redactor({
				fixed: true
	});
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
	    	<#if user.usertype=="Admin" || user.usertype=="Talent Manager">
	    	<select class="form-control" name="monthyear" id="monthyear" onchange="getQueryData('${rc.contextPath}','Attendance')">
		    	<option value="">Please select</option>
		    	<#list monyear as value>
		    		<option value="${value}" <#if selectedVal==value>selected='selected'</#if>>${value}</option>
		    	</#list>
			</select>
			</#if>
		</div>

	    <div class="col-sm-3 col-xs-6 attendance-header">
	    	<h3>Attendance</h3>
	    </div>
	    <div class="col-sm-1 col-xs-6 back-button">
	       <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
	    </div>
	</div>    
    <div class="col-xs-12 emp_profile">
        <div class="col-sm-3 col-xs-12 leave-button-group no-padding-mb">
            <button type="button" class="btn btn-default leave-button" onclick="showLeave('${rc.contextPath}');">Leave</button>
            <button type="button" class="btn btn-default apply-leave-button" data-toggle="modal" data-target="#leaveModal">Apply for Leave</button>
            <button type="button" class="btn btn-default leave-button" data-toggle="modal" data-target="#exitprocessModal">Exit Process</button>
        </div>
        <div class="col-sm-9 col-xs-12 no-padding-mb" id="searchattendance">
            <table class="table table-bordered table-responsive attendance_table">
                <thead>
                    <tr>
                        <th>Punch IN</th>
                        <th>Punch OUT</th>
                        <th>Status</th>
	        		</tr>
                </thead>
	    		<tbody>
			    	<#list att as atdn>
			        <tr>
		                <td><span class="attendante-time">${atdn.att_in!''}</span> <br /> ${atdn.att_weekday!''} | ${atdn.att_date!''}</td>
			            <td><span class="attendante-time">${atdn.att_out!''}</span> <br /> ${atdn.att_weekday!''} | ${atdn.att_date!''}</td>
			            <td>${atdn.status!''}</td>
			        </tr>
			        </#list>   
		    	</tbody>
            </table>
        </div>
    </div>
 
<!-- Leave Modal Start -->
<div class="modal fade notice-modal leave_modal" id="leaveModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Apply for Leave</h4>
            </div>
            <form name="leaveInfo" id="leaveInfo" action="${rc.contextPath}/addleaverequest" method="post">
                <div class="modal-body">
                    <div class="form-group">
                    	<input type="hidden" id="emp_id" name="emp_id" value="${emp.emp_id!''}"/>
                        <label class="control-label col-sm-3">Leave Type</label>
                        <div class="col-sm-3">          
                        <select name="leavetype" id="leavetype" class="form-control">
	                    	<option value="">Select Leave Types</option>
	                    	<#list leavetype as leave>
	                        	<option value="${leave.id}" >${leave.leavetype}</option>
	                        </#list>          
	                    </select>
                        </div>
                        <label class="control-label col-sm-3">Quota Year</label>
                        <div class="col-sm-3">          
                            <select name="year" id="year" class="form-control" onChange="getQuota('${rc.contextPath}')">
		                    	<option value="">Select Year</option>
		                    	<#list quotaYear as y>
		                        <option value="${y.year}" <#if selectYear==y.year>selected="selected"</#if>>${y.year}</option>
		                        </#list>          
		                    </select>
                        </div>
                    </div>
                    <div  id="quotadisplay" class="quota-list">
	                    <div class="form-group quota-row">
	                    	<div class="col-sm-3">
	                    		Leave Type
	                    	</div>
	                    	<div class="col-sm-2">
	                    		Leave Quota
	                    	</div>
	                    	<div class="col-sm-2">
	                    		Leave Used
	                    	</div>
	                    	<div class="col-sm-2">
	                    		Available
	                    	</div>
	                    	<div class="col-sm-3">
	                    		Expire Date
	                    	</div>
	                    </div>
	                    <#list allleavetype as type>
			        		<#if type.isquota>
			        			<#assign qto = 0.0>
			        			<#assign usd = 0.0>
			        			<#assign exd = "">
			        			<#list quotas as quota>
			        				<#if type.id==quota.type_id>
			        					<#assign qto = quota.quota?c>
			        					<#assign usd = quota.used?c>
			        					<#assign avail = quota.quota - quota.used>
			        					<#assign exd = quota.expdate>
			        					<#break>
			        				</#if>	
			        			</#list>
			                    <div class="form-group">
			                    	<div class="col-sm-3">
			                    		${type.leavetype!''}<input type="hidden" id="type_id" name="type_id" value="${type.id!''}"/>
			                    	</div>
			                    	<div class="col-sm-2">
			                    		<#if qto??>${qto}<#else>0</#if>
			                    	</div>
			                    	<div class="col-sm-2">
			                    		<#if usd??>${usd}<#else>0</#if>
			                    	</div>
			                    	<div class="col-sm-2">
			                    		<#if avail??>${avail}<#else>0</#if>
			                    	</div>
			                    	<div class="col-sm-3">
			                    		<#if type.iscontinue==false>
					                		${exd}
					                	<#else>
					                		
					                	</#if>
			                    	</div>
			                    </div>
	                    	</#if>
						</#list>
					</div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Start Date</label>
                        <div class="col-sm-3">          
                            <input type="text" id="fdate" name="fdate" class="form-control" />
                            <div id="copydate"><input type="checkbox" id="oneday" name="oneday" onclick="copyValue(this);" value="1"> One day leave</div>
                        </div>
                        <label class="control-label col-sm-3">End Date</label>
                        <div class="col-sm-3">
                            <input type="text" id="tdate" name="tdate" class="form-control" />    
                        </div>
                    </div>
                    <div class="form-group">
	                    <label class="control-label col-sm-3">Day List</label>
	                    <div id='leavelist' style='overflow:auto;' class="col-sm-3">
                        
                        </div>
                        <div class="col-sm-6">
		                	<button type="button" class="btn btn-primary col-xs-6 col-xs-offset-6" onclick="noOfLeaveCalculation('${rc.contextPath}',0)">
		                    	<img src="images/submit_for_approval.png" alt="" /> Leave Validate</button> <br>
			                <div class="col-sm-12 leave-days">
			                    <label class="control-label col-xs-4">Days</label>
			                    <label class="control-label col-xs-8" id="days_disp"></label>
			                    <input type="hidden" id="days" name="days" value=""/>
	                        </div>
	                    </div>
                    </div>
                    <div class="form-group">
                    	<label class="control-label col-sm-2">Reason</label>
                    	<div class="col-sm-9">
	                    	<textarea rows="4" cols="50" id="reason" name="reason" class="form-control" maxlength="2000"></textarea>
		                	<div id="textarea_feedback"></div>
		                </div>
                    </div>
                    <div class="form-group">
                    	<div id="approverList" class="col-sm-12">
				    		<input type="hidden" id="appsize" name="appsize" value="${approver?size}"/>
				    		<#if (approver?size > 0)>
				    		<table class="table table-responsive">
					    		<#list 1..approver?size as i>
						    		<tr id="app${i}">
						                <td class="name">
						                	<#if i==1>
						                		First Approvar *
						                	<#elseif i==2>
						                		Second Approvar
						                	<#elseif i==3>
						                		Thierd Approvar
						                	<#elseif i==4>
						                		Fourth Approvar
						                	<#else>
						                		Fifth Approvar
						                	</#if>
						                </td>
						                <td>
						                	<select name="approvar${i}" id="approvar${i}" class="form-control SlectBox" onchange="checkDuplicate(${i},${approver?size});">
						                    	<option value="">
						                    		<#if i==1>
								                		Select First Approvar
								                	<#elseif i==2>
								                		Select Second Approvar
								                	<#elseif i==3>
								                		Select Thierd Approvar
								                	<#elseif i==4>
								                		Select Fourth Approvar
								                	<#else>
								                		Select Fifth Approvar
								                	</#if>
						                    	</option>
						                    	<#list approver as apr>
						                			<#list allEmp as appemp>
						                				<#if apr.approver_id==appemp.emp_id>
						                					<option value="${appemp.emp_id}">${appemp.employee_name}(${appemp.avator})</option>
						                					<#break>
						                				</#if>
						                			</#list>
						                		</#list>          
						                    </select>
						                    <!--<input type="hidden" id="approverSize" name="approverSize" value="approver?size"/>-->
						                </td>
						            </tr>
						        </#list>
					        </table>
					        </#if>
			            </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-xs-6 modal-close">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Cancel</button>
                    </div>
                    <div class="col-xs-6 modal-accept">
                        <button type="button" class="btn btn-default" onclick="addLeave('${rc.contextPath}')"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Done</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- Exit process Modal Start -->
<div class="modal fade notice-modal leave_modal" id="exitprocessModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Apply for Resignation</h4>
            </div>
            <form name="leaveInfo" id="leaveInfo" action="${rc.contextPath}/resignationrequest" method="post">
                <div class="modal-body">
                    <div class="form-group">
                    	<input type="hidden" id="emp_id" name="emp_id" value="${emp.emp_id!''}"/>
                        
                    </div>
                    <div class="form-group">
                    	<label class="control-label col-sm-3">Reason</label>
                    	<div class="col-sm-9">
	                    	<textarea rows="500" cols="700" id="resign_reason" name="resign_reason"  maxlength="2000"></textarea>
		                	<div id="reasign_feedback"></div>
		                </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-4">Last Office Date</label>
                        <div class="col-sm-8">          
                            <input type="text" id="lodate" name="lodate" class="form-control" />
                        </div>
                        
                    </div>
                    <div class="form-group">
                    	<div id="approverList" class="col-sm-12">
				    		<input type="hidden" id="appsize" name="appsize" value="${approver?size}"/>
				    		<#if (approver?size > 0)>
				    		<table class="table table-responsive">
					    		<#list 1..approver?size as i>
						    		<tr id="app${i}">
						                <td class="name">
						                	<#if i==1>
						                		First Approvar *
						                	<#elseif i==2>
						                		Second Approvar
						                	<#elseif i==3>
						                		Thierd Approvar
						                	<#elseif i==4>
						                		Fourth Approvar
						                	<#else>
						                		Fifth Approvar
						                	</#if>
						                </td>
						                <td>
						                	<select name="approvar${i}" id="approvar${i}" class="form-control SlectBox" onchange="checkDuplicate(${i},${approver?size});">
						                    	<option value="">
						                    		<#if i==1>
								                		Select First Approvar
								                	<#elseif i==2>
								                		Select Second Approvar
								                	<#elseif i==3>
								                		Select Thierd Approvar
								                	<#elseif i==4>
								                		Select Fourth Approvar
								                	<#else>
								                		Select Fifth Approvar
								                	</#if>
						                    	</option>
						                    	<#list approver as apr>
						                			<#list allEmp as appemp>
						                				<#if apr.approver_id==appemp.emp_id>
						                					<option value="${appemp.emp_id}">${appemp.employee_name}(${appemp.avator})</option>
						                					<#break>
						                				</#if>
						                			</#list>
						                		</#list>          
						                    </select>
						                    <!--<input type="hidden" id="approverSize" name="approverSize" value="approver?size"/>-->
						                </td>
						            </tr>
						        </#list>
					        </table>
					        </#if>
			            </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-xs-6 modal-close">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Cancel</button>
                    </div>
                    <div class="col-xs-6 modal-accept">
                        <button type="button" class="btn btn-default" onclick="addLeave('${rc.contextPath}')"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Done</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>