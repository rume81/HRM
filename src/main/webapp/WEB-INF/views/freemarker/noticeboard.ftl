<script type="text/javascript">
$(document).ready(function() {
	$('#expdate').datepicker({ dateFormat: 'dd-mm-yy' });
});
</script>
<div class="container-fluid front_page">
    <div class="col-sm-4 col-xs-12 hr-header">
        <a href="${rc.contextPath}/"><img src="images/profile_logo.png" alt="" class="" /></a>
    </div>
    <div class="col-xs-2"></div>
    <div class="col-xs-8">
       <h4>Noticeboard  <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a></h4>
    </div>
    <#if user.usertype=="Admin" || user.usertype=="Talent Manager">
	<div class="create-notice">
	    <button type="button" class="btn btn-default btn-lg" data-toggle="modal" data-target="#myModal">
	        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
	    </button>
	    <p>Create Notice</p>
	</div>
	</#if>
	<div id="carousel-example-generic" class="carousel slide notification-slider" data-ride="carousel">
	    <!-- Indicators -->
	    <ol class="carousel-indicators">
	    	<#assign dataslide=0>
	    	<#list notice?chunk(2) as page>
		        <li data-target="#carousel-example-generic" data-slide-to="${dataslide}" class="<#if dataslide==0>active</#if>"></li>
		        <#assign dataslide=dataslide+1>
			</#list>
	    </ol>
	
	    <!-- Wrapper for slides -->
	    <div class="carousel-inner" role="listbox">
	    	<#assign order=0>
	    	<#assign active=0>
	        <#list notice?chunk(2) as page>
	        <div class="<#if active==0>item active<#else>item</#if>">
	            <div class="col-xs-12">
	            	<#list page as cell>
	                <div class="col-sm-4 col-sm-offset-1 col-xs-12 <#if order==0>notice-left<#else>notice-right</#if>">
	                	<h3>${cell.title!''}</h3>
	                    <p>
	                        ${cell.details!''}
	                    </p>
	                </div>
	                <#if order == 0>
	                	<#assign order=1>
	                <#else>
	                	<#assign order=0>
	                </#if>
	                <#assign active=active+1>
	                </#list>
	            </div>
	        </div>
	  		</#list>
	    </div>
	
	    <!-- Controls -->
	    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
	        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
	        <span class="sr-only">Previous</span>
	    </a>
	    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
	        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
	        <span class="sr-only">Next</span>
	    </a>
	</div>
	<!-- Button trigger modal -->
	
	<#if user.usertype=="Admin" || user.usertype=="Talent Manager">
	<!-- Modal -->
	<div class="modal fade notice-modal" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">Create Notice</h4>
	            </div>
	            <form name="addnotice" id="addnotice" action="${rc.contextPath}/add_notice" method="post">
	                <div class="modal-body">
	                    <div class="col-xs-12 no-padding-mb">
			            	<div class="col-xs-12 col-sm-8 no-padding-mb">
			                    <div class="form-group">
			                        <label for="title">Title</label>
			                        <input type="text" class="form-control" id="title" name="title" placeholder="Notice Title">
			                    </div>
			                    <div class="form-group">
			                        <label for="details">Details</label>
			                        <textarea rows="5" class="form-control" id="details" name="details" placeholder="Notice Details"></textarea> 
			                    </div>
			                    <div class="form-group">
			                    	<label for="title">Expire Date</label>
			                        <input type="text" class="form-control" id="expdate" name="expdate" placeholder="Expire Date">
			                    </div>
		                    </div>
		                    <div class="col-xs-4 form-group notice_access_master">
		                    	<label for="title">Notice Access</label>
			                    <div class="notice_access">		                  
			                    	<input type="checkbox" id="chkAll" onclick="toggle(this,'chk')"/> All<br>
			                    	<#list emps as emp>
			                    		<input type="checkbox" id="chk${emp.emp_id}" name="chk" value="${emp.emp_id}" onclick="toggleselect('chkAll','chk')"/> ${emp.employee_name!''}<br>
			                    	</#list>
			                    </div>
		                    </div>
	                    </div>
	                    
	                </div>
	                <div class="modal-footer">
	                    <div class="col-xs-6 modal-close">
	                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Cancel</button>
	                    </div>
	                    <div class="col-xs-6 modal-accept">
	                        <button type="button" class="btn btn-default access_close" onclick="addNotice('${rc.contextPath}');"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>Done</button>
	                    </div>
	                    
	                </div>
	            </form>
	        </div>
	    </div>
	</div>
	</#if>
</div>