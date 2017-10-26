    <div class="container-fluid front_page">
        <div class="col-sm-8 col-sm-offset-2 col-xs-12">
            <img src="images/logo.png" alt="" class="dashboard-logo logo" />
        </div>
        <div class="col-xs-12 dashboard no_padding">
            <ul class="dashboard_top no_padding">
            	<a href="${rc.contextPath}/empprofile">
                <li class="profile">
                    
                        <p class="dashboard_heading">Profile</p>
                        <img src="images/profile.png" alt="" />
                        <p>Good Morning!</p>
                        <p>Today 27C <br/> <br/>
                        Mostly cloud <br/> you might need an umbrella</p>
                        
                    
                </li>
                </a>
                <a href="${rc.contextPath}/noticeboard">
                <li class="noticeboard">
                    
                        <p class="dashboard_heading">Notice Board</p>
                        <img src="images/noticeboard.png" alt="" />
                        <p>Keep in touch with information</p>
                   
                </li>
                </a>
                <a href="${rc.contextPath}/attendance">
                <li class="attendance">
                        <p class="dashboard_heading">Attendance</p>
                        <img src="images/attendance.png" alt="" />
                        <ul>
                        	<li>In <br>8.45am</li>
                        	<li class="out-time">Out <br> 6.30pm</li>
                        </ul>
                        
                    
                </li>
                </a>
                <a href="${rc.contextPath}/holidays">
                <li class="holidays">
                        <p class="dashboard_heading">Holidays</p>
                        <img src="images/holidays.png" alt="" />
                        <p>02 Holidays on this month</p>
                        <p>Do you have any weekend plan?</p>
                </li>
                </a>
                <a href="">
                <li class="onboading">
                        <p class="dashboard_heading">On Boarding</p>
                        <img src="images/onboard.png" alt="" />
                        <p>Tells us your need</p>
                </li>
                </a>
            </ul>
            <ul class="dashboard_bottom no_padding">
            	<a href="#">
                <li class="message">
                        <p class="dashboard_heading">Message</p>
                        <p><img src="images/message.png" alt="" />Come to my desk</p>
                        <p class="message_notication">16</p>
                </li>
                </a>
                <a href="#">
                <li class="notifier">
                        <p class="dashboard_heading">Notifier</p>
                        <img src="images/notifier.png" alt="" />
                        <p>10:30am <br/> Meeting with US client </p>
                        <p class="notifier_notification">05</p>
                </li>
                </a>
                <a href="#">
                <li class="performance">
                        <p class="dashboard_heading">Performance</p>
                        <img src="images/performance.png" alt="" />
                        <p>Prove yourself as a good human being</p>
                    
                </li>
                </a>
                <a href="${rc.contextPath}/payslip">
                <li class="payslip">
                        <p class="dashboard_heading">Pay Slip</p>
                        <img src="images/payslip.png" alt="" />
                        <p>Keep every documents safely</p>
                        <p class="payslip_notification">01</p>
                </li>
                </a>
                <a href="${rc.contextPath}/policy">
                <li class="hrpolicy">
                        <p class="dashboard_heading">HR Policy</p>
                        <img src="images/hr.png" alt="" />
                        <p>Welcome to</p>
                        <!--<img src="images/" alt="" />-->
                </li>
                </a>
                <li class="logout">
                	<#if currentuser.usertype=="Admin" || currentuser.usertype=="Talent Manager">
                	<a href="${rc.contextPath}/employeeselection">
                        <img class="logout-img-double" src="images/selectimp.png"/>
                        <p>Employee Selection</p>
                    </a>
                    <a href="${rc.contextPath}/user/logout">
                        <img class="logout-img-double" src="images/logout.png"/>
                        <p>Logout</p>
                    </a>
                    <#else>
                    <a href="${rc.contextPath}/user/logout">
                        <img class="logout-img-single" src="images/logout.png"/>
                        <p>Logout</p>
                    </a>
                    </#if>
                </li>                
            </ul>
        </div>
    </div>