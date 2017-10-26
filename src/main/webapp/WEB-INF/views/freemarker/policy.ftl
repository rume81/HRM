<div class="container middle_page">
	<div class="header-top">
	    <div class="col-sm-3 col-xs-12 hr-header">
	        <a href="${rc.contextPath}/">
	        	<img src="images/profile_logo.png" alt="" class="" />
	        </a>
	    </div>
	    <div class="col-sm-5 col-xs-12 attendance-date">
	    	<#if user.usertype=="Admin">
	    	<select class="form-control" name="policyname" id="policyname" onchange="">
		    	<option value="">Please select</option>
		    		<option value=""></option>
			</select>
			</#if>
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
            <button type="button" class="btn btn-default apply-leave-button" data-toggle="modal" data-target="#leaveModal">Add Policy</button>
            </#if>
            <button type="button" class="btn btn-default leave-button" onclick="">Download</button>
            
        </div>
        <div class="col-sm-9 col-xs-12 no-padding-mb" id="searchattendance">
            <object data="" type="application/pdf" width="100%" height="100%">
  						<p><a href="">Faile to load file!</a></p>
			</object>
        </div>
    </div>
    <div class="col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2">
        <img src="images/front_logo_bottom.png" alt="WebHawks IT logo" class="hidden-xs"/>
	    <img src="images/WHIT-Logo-Phone.png" alt="WebHawks IT logo mobile" class="visible-xs footer-logo-mobile"/>
        <p class="front_copyright">&copy; All Rights Reserved</p>
    </div>
</div>
