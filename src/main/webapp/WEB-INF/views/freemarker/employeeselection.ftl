<div class="container-fluid front_page">
    <div class="col-xs-8 col-xs-offset-2">
        <img src="images/logo.png" alt="" class="logo" />
    </div>
    <div class="col-sm-10 col-sm-offset-1 login_admin">
        <div class="col-sm-5">
        <select id="team" name="team" onchange="showEmployee('${rc.contextPath}');">
            <option value="">Select Team</option>
            <#list teams as team>
            <option value="${team.teamId}">${team.teamName}</option>
            </#list>
        </select>
        </div>
        <div id="teamemp" class="col-sm-5">
        <select id="empid" name="empid" placeholder="Employee ID">
        	<option value="">Select Employee</option>
        </select> 
        </div>
    </div>
    <div class="col-sm-4 col-sm-offset-4 col-xs-8 col-xs-offset-2">
        <button type="button" class="login_button" onclick="viewDetails('${rc.contextPath}');">View Details</button>
    </div>
    <div class="col-sm-4 col-sm-offset-4 col-xs-8 col-xs-offset-2">
        <button type="button" class="login_button" onclick="EmployeeRegister('${rc.contextPath}');">Create Employee</button>
    </div>
    