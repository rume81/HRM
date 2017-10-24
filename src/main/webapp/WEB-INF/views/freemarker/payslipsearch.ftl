<form name="payslipdetails" id="payslipdetails" action="${rc.contextPath}/editpayslip" method="post">
    <table class="table table-bordered table-responsive">
	    <tr class="payslip-header">
        	<td colspan='4'><img src="images/front_logo_bottom.png" /></br>
        	House No: 1170, Road No: 50</br>
	    	Flat No: 1st,5th,6th Floor,Avenue-12, Mirpur DOHS, Dhaka-1216,</br>
	    	Bangladesh.  Phone: +880258070348</br>
	    	Email: info@webhawksit.com
	    	
	    	<input type="hidden" id="emp_id" name="emp_id" value="${emp.emp_id}"/>
	    	<input type="hidden" id="month" name="month" value="${curmon}"/>
	    	<input type="hidden" id="year" name="year" value="${curyear}"/>
	    	</td>
	    </tr>
	    <tr>
	    	<#if user.usertype!="Talent Manager" && slip.slipstate!=0>
	    		<#setting number_format=",##0.00">
				<#setting locale="en_US">
			</#if>
	    	<#assign total=0>
	    	<#assign deduct=0>
	    	<#assign netsal=0>
	    	<#assign bank=0>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Name of the employee</span></td>
        	<td colspan='2' style="text-align:center">${slip.emp_name!''}</td>
        	<td class='payslip-tdlast'></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Employee ID</span></td>
        	<td colspan='2' style="text-align:center">${slip.emp_finance_id!''}</td>
        	<td class='payslip-tdlast'></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-bold">Pay Slip</span></td>
        	<td colspan='2' style="text-align:center">For the Month of 
        		<#if curmon?? && curmon==1>January
        		<#elseif curmon?? && curmon==2>February
        		<#elseif  curmon?? && curmon==3>March
        		<#elseif  curmon?? && curmon==4>April
        		<#elseif  curmon?? && curmon==5>May
        		<#elseif  curmon?? && curmon==6>June
        		<#elseif  curmon?? && curmon==7>July
        		<#elseif  curmon?? && curmon==8>August
        		<#elseif  curmon?? && curmon==9>September
        		<#elseif  curmon?? && curmon==10>October
        		<#elseif  curmon?? && curmon==11>November
        		<#else>December 
        		</#if><#if curyear??>${curyear?string["0"]}</#if>
        	</td>
        	<td class='payslip-tdlast'></td>
	    </tr>
	    <tr>
        	<td colspan='4'><span class="payslip-text-bold">Scale of Payment</span></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-bold">Description</span></td>
	    	<td class='payslip-tdsect'><span class="payslip-text-bold">Days/Hr</span></td>
	    	<td><span class="payslip-text-bold">Description</span></td>
	    	<td class='payslip-tdlast'><span class="payslip-text-bold">Amount</span></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Standard working days in the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
	    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center" type="text" id="swdm" name="swdm" value="${slip.swdm?string["0"]}"/>
	    		<#else>
	    		<span class="payslip-text-normal-val">
	    			${slip.swdm?string["0"]}
	    		</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Gross Salary</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
		    	<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="gross" name="gross" value="${slip.gross!''}"/>
		    	<#else>
		    		<span class="payslip-text-normal-val">${slip.gross!''}</span>
		    	</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Actual working days in the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center" type="text" id="awdm" name="awdm" value="${slip.awdm?string["0"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.awdm?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Basic pay for a month</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="basic" name="basic" value="${slip.basic!''}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.basic!''}</span>
	    		</#if>	
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Approved Leave</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center" type="text" id="appleave" name="appleave" value="${slip.appleave?string["0"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.appleave?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Daily Basic Pay Rate</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="dbpr" name="dbpr" value="${slip.dbpr!''}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.dbpr!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Absent</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center" type="text" id="absent" name="absent" value="${slip.absent?string["0"]}"/>
		    	<#else>	
	    			<span class="payslip-text-normal-val">${slip.absent?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Hourly Basic Pay Rate</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="hbpr" name="hbpr" value="${slip.hbpr!''}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.hbpr!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Actual working hour of the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center;width:120px" type="text" id="awhm" name="awhm" value="${slip.awhm?string["0.##"]}"/> hrs
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.awhm?string["0.##"]} hrs</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdlast' style="text-align:right"><span class="payslip-text-normal"></span></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Overtime allowed of the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:center">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:center;width:120px" type="text" id="oaom" name="oaom" value="${slip.oaom?string["0.##"]}"/> hrs
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.oaom?string["0.##"]} hrs</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdlast'><span class="payslip-text-normal"></span></td>
	    </tr>
	    <tr>
        	<td colspan='4'><span class="payslip-text-bold">Computation of the gross salary to be paid for the month of <#if curmon?? && curmon==1>January
        		<#elseif curmon?? && curmon==2>February
        		<#elseif  curmon?? && curmon==3>March
        		<#elseif  curmon?? && curmon==4>April
        		<#elseif  curmon?? && curmon==5>May
        		<#elseif  curmon?? && curmon==6>June
        		<#elseif  curmon?? && curmon==7>July
        		<#elseif  curmon?? && curmon==8>August
        		<#elseif  curmon?? && curmon==9>September
        		<#elseif  curmon?? && curmon==10>October
        		<#elseif  curmon?? && curmon==11>November
        		<#else>December 
        		</#if></span></td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Number of days worked in the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="nodwm" name="nodwm" value="${slip.nodwm?string["0"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.nodwm?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Basic Salary to be paid</span><#assign total=total+slip.bstbp></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="bstbp" name="bstbp" value="${slip.bstbp!''}" onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.bstbp!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Number of hours worked in the month</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="nohwm" name="nohwm" value="${slip.nohwm?string["0.##"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.nohwm?string["0.##"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Salary of overtime working</span><#assign total=total+slip.soow></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="soow" name="soow" value="${slip.soow!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.soow!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Hours of overtime</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="hoo" name="hoo" value="${slip.hoo?string["0.##"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.hoo?string["0.##"]}</span>
	    		</#if>		
	    	</td>
	    	<td><span class="payslip-text-normal">Salary of holiday overtime</span><#assign total=total+slip.soho></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="soho" name="soho" value="${slip.soho!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.soho!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Overtime hrs in holiday</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="ohih" name="ohih" value="${slip.ohih?string["0.##"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.ohih?string["0.##"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Pay for total night hours</span><#assign total=total+slip.pftnh></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="pftnh" name="pftnh" value="${slip.pftnh!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.pftnh!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Hours total night shift</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="htns" name="htns" value="${slip.htns?string["0.##"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.htns?string["0.##"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Salary for all paid leaves</span><#assign total=total+slip.sfapl></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="sfapl" name="sfapl" value="${slip.sfapl!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.sfapl!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">Total paid leaves</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="tpl" name="tpl" value="${slip.tpl?string["0"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.tpl?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Business trip adjustment</span><#assign total=total+slip.bta></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="bta" name="bta" value="${slip.bta!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.bta!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal">No of training and seminars</span></td>
	    	<td class='payslip-tdsect' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="nots" name="nots" value="${slip.nots?string["0"]}"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.nots?string["0"]}</span>
	    		</#if>
	    	</td>
	    	<td><span class="payslip-text-normal">Housing allowance</span><#assign total=total+slip.ha></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="ha" name="ha" value="${slip.ha!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.ha!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdsect'><span class="payslip-text-normal"></span></td>
	    	<td><span class="payslip-text-normal">Medical allowance</span><#assign total=total+slip.ma></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="ma" name="ma" value="${slip.ma!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.ma!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdsect'><span class="payslip-text-normal"></span></td>
	    	<td><span class="payslip-text-normal">Transportation</span><#assign total=total+slip.ta></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="ta" name="ta" value="${slip.ta!''}"  onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.ta!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdsect'><span class="payslip-text-normal"></span></td>
	    	<td><span class="payslip-text-bold">Total Gross Salary</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right;background-color:#82e0aa" type="text" id="total" name="total" value="${total!''}" readonly/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${total!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
	    	<td class='payslip-tdfirst'><span class="payslip-text-normal"></span></td>
	    	<td class='payslip-tdsect'><span class="payslip-text-normal"></span></td>
	    	<td><span class="payslip-text-normal">Bonus</span></td>
	    	<td class='payslip-tdlast' style="text-align:right">
	    		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="bonus" name="bonus" value="${slip.bonus!''}" onblur="CalculateGross();"/>
		    	<#else>
	    			<span class="payslip-text-normal-val">${slip.bonus!''}</span>
	    		</#if>
	    	</td>
	    </tr>
	    <tr>
        	<td colspan='4'><span class="payslip-text-bold">Break Up of deductions for the month <#if curmon?? && curmon==1>January
        		<#elseif curmon?? && curmon==2>February
        		<#elseif  curmon?? && curmon==3>March
        		<#elseif  curmon?? && curmon==4>April
        		<#elseif  curmon?? && curmon==5>May
        		<#elseif  curmon?? && curmon==6>June
        		<#elseif  curmon?? && curmon==7>July
        		<#elseif  curmon?? && curmon==8>August
        		<#elseif  curmon?? && curmon==9>September
        		<#elseif  curmon?? && curmon==10>October
        		<#elseif  curmon?? && curmon==11>November
        		<#else>December 
        		</#if></span></td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Contribution for social security/(Half month Advanced)</span><#assign deduct=deduct+slip.hma></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="hma" name="hma" value="${slip.hma!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.hma!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Contribution for health insurance</span><#assign deduct=deduct+slip.cfhi></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="cfhi" name="cfhi" value="${slip.cfhi!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.cfhi!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Contribution for housing insurance</span><#assign deduct=deduct+slip.cfhoi></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="cfhoi" name="cfhoi" value="${slip.cfhoi!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.cfhoi!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Leave Without Payment (LWP)</span><#assign deduct=deduct+slip.lwp></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="lwp" name="lwp" value="${slip.lwp!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.lwp!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Lunch</span><#assign deduct=deduct+slip.lunch></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="lunch" name="lunch" value="${slip.lunch!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.lunch!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Amount of withholding tax (if any)</span><#assign deduct=deduct+slip.tax></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="tax" name="tax" value="${slip.tax!''}" onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.tax!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-bold">Total Deductions</span></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right;background-color:#82e0aa" type="text" id="deduct" name="deduct" value="${deduct!''}" readonly/>
		    	<#else>
        			<span class="payslip-text-normal-val">${deduct!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-normal">Arrears (if any)</span></td>
        	<td rowspan='2' class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right" type="text" id="arrears" name="arrears" value="${slip.arrears!''}"  onblur="CalculateDeduction();"/>
		    	<#else>
        			<span class="payslip-text-normal-val">${slip.arrears!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-bold"></span></td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-bold">Net salary</span><#assign netsal=(total-deduct)+slip.arrears></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right;background-color:#82e0aa" type="text" id="netsal" name="netsal" value="${netsal!''}" readonly/>
		    	<#else>
        			<span class="payslip-text-normal-val">${netsal!''}</span>
        		</#if>
        	</td>
	    </tr>
	    <tr>
        	<td colspan='3' class='payslip-text-right'><span class="payslip-text-bold">Amount payable to bank account</span><#assign bank=netsal+slip.bonus></td>
        	<td class='payslip-tdlast payslip-text-right'>
        		<#if user.usertype=="Talent Manager">
		    		<input class="payslip-text-normal-val" onkeypress="validate(event)"  style="text-align:right;background-color:#82e0aa" type="text" id="bank" name="bank" value="${bank!''}" readonly/>
		    	<#else>
        			<span class="payslip-text-normal-val">${bank!''}</span>
        		</#if>
        	</td>
	    </tr>
    </table>
</form>