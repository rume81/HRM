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