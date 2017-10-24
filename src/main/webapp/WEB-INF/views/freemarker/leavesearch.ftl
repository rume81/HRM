<h4> <span class="leave-number"><#if pl??>${pl?size}<#else>0</#if></span> Paid Leave</h4>
<hr>
<#list pl as paid>
<p> <span class="date">${paid.tdate!''} | ${paid.fdate!''}</span> <span class="leave-reason">${paid.reason!''}</span></p>
<hr>
</#list>

<h4> <span class="leave-number"><#if wl??>${wl?size}<#else>0</#if></span> Non paid Leave</h4>
<hr>
<#list wpl as wl>
<p> <span class="date">${wl.tdate!''} | ${wl.fdate!''}</span> <span class="leave-reason">${wl.reason!''}</span></p>
<hr>
</#list>     