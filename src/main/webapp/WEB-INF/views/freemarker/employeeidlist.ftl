<select id="empid" name="empid" placeholder="Employee ID" class="col-sm-5">
	<option value="">Select Employee</option>
	<#list emp as e>
	<option value="${e.emp_id}">${e.employee_name!''}</option>
	</#list>
</select>