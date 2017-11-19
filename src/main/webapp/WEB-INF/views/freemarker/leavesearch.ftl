<h4> <span class="leave-number"><#if pl??>${pl?size}<#else>0</#if></span> Paid Leave</h4>
<hr>
<#list pl as paid>
<div class="date">${paid.tdate!''} | ${paid.fdate!''}</div><div class="leave-reason">${paid.reason!''}</div>
<hr>
</#list>

<h4> <span class="leave-number"><#if wl??>${wl?size}<#else>0</#if></span> Non paid Leave</h4>
<hr>
<#list wpl as wl>
<div class="date">${wl.tdate!''} | ${wl.fdate!''}</div><div class="leave-reason">${wl.reason!''}</div>
<hr>
</#list>     