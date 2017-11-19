<div class="container middle_page">
	<div class="header-top">
	    <div class="col-sm-3 col-xs-12 hr-header">
	        <a href="${rc.contextPath}/">
	        	<img src="images/profile_logo.png" alt="" class="" />
	        </a>
	    </div>
	    <div class="col-sm-5 col-xs-12 attendance-date">
	    	<select class="form-control" name="policyname" id="policyname" onchange="showPolicy();">
		    	<#list policys as p>
		    		<option value="${p.id}~${p.policy_file.path}" <#if selpolicy?? && p.id==selpolicy.id>selected="selected"</#if>>${p.policy_name!''}</option>
		    	</#list>
			</select>
		</div>

	    <div class="col-sm-3 col-xs-6 attendance-header">
	    	<h3>HR Policy</h3>
	    </div>
	    <div class="col-sm-1 col-xs-6 back-button">
	       <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
	    </div>
	</div>    
    <div class="col-xs-12 emp_profile">
        <div class="col-sm-3 col-xs-12 leave-button-group no-padding-mb">
        	<#if user.usertype=="Admin" || user.usertype=="Talent Manager">
            <button type="button" class="btn btn-default leave-button" data-toggle="modal" data-target="#policy">Add Policy</button>
            <button type="button" class="btn btn-default leave-button" onclick="deletPolicy('${rc.contextPath}')">Delete Policy</button>
            </#if>
        </div>
        <div class="col-sm-9 col-xs-12 no-padding-mb" id="searchattendance">
        	<input type="hidden" id="domainname" value="${sys!''}"/>
            <object id="pdf_policy" data="<#if selpolicy.policy_file??>${sys!''}${selpolicy.policy_file.path}</#if>" type="application/pdf" width="800" height="500">
  				
			</object>
        </div>
    </div>
    

<!-- Leave Modal Start -->
<div class="modal fade notice-modal leave_modal" id="policy" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Upload Policy</h4>
            </div>
            <form name="policyInfo" id="policyInfo" action="${rc.contextPath}/uploadpolicy" method="post">
                <div class="modal-body">
                	<div class="form-group">
                    	<label class="control-label col-sm-3">Policy Name</label>
                        <div class="col-sm-9"> 
                        	<input type="text" id="pname" name="pname" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Browse File</label>
                        <div class="col-sm-9">
                    		<input type="file" accept=".pdf" class="logo form-control header_logo" name="pdfdoc" id="pdfdoc"  onchange="readPDF(this,'pdf_view')"/>
                    		<object id="pdf_view" data="" type="application/pdf" width="500px" height="250px"></object>
                    	</div>
	        		</div>
                </div>
                <div class="modal-footer"  id="footerpdf"><span></span></div>
                <div class="modal-footer">
                    <div class="col-xs-6 modal-close">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Cancel</button>
                    </div>
                    <div class="col-xs-6 modal-accept">
                        <button type="button" class="btn btn-default" onclick="addPolicy('${rc.contextPath}');"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Done</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
