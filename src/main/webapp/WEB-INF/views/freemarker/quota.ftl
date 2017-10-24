<div class="form-group">
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
		Leave Available
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
        		${qto}
        	</div>
        	<div class="col-sm-2">
        		${usd}
        	</div>
        	<div class="col-sm-2">
        		${avail}
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