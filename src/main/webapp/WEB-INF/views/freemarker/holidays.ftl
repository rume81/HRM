<script type="application/javascript">
  var eventData = [
  	<#list weekdays as w>
    	{"date":"${w.date_from}","badge":false,"title":"${w.holiday_desc}"},
    </#list>
    <#list holiday as h>
    	{"date":"${h.date_from}","badge":true,"title":"${h.holiday_desc}"},
    </#list>
  ];
  $(document).ready(function () {
    $("#my-calendar").zabuto_calendar({
      year: ${curyear!'2016'},
      month: ${curmon!'5'},
      show_previous: ${prev!'12'},
      show_next: ${next},
      legend: [
        {type: "text", label: "Holidays", badge: "00"},
        {type: "block", label: "Weekends", classname: "weekend"}
      ],
      data: eventData
    });
  });
</script>
<div class="container middle_page">
	<div class="header-top">
    	<div class="col-sm-3">
        	<img src="images/logo.png" alt="" class="logo attendance_logo" />
        </div>
        <div class="col-sm-5 attendance-date">
        	<!--<select class="form-control">
            	
		    		<option value=""></option>
		    	
            </select>-->
        </div>
        <div class="col-sm-3 attendance-header">
        	<h3>Holiday</h3>
        </div>
        <div class="col-sm-1 back-button">
            <a href="${rc.contextPath}/dashboard"><img src="images/back_button.png" alt="" /></a>
        </div>
    </div>
    <div class="col-sm-12">
        <img src="images/holidays.jpg" alt="" />
    </div>
        
    <!-- Holidays carousel start -->
        
    <div class="container">
        <div class="row"> 
            <div class="col-sm-8 col-sm-offset-2 col-xs-12"> 
                <div id="my-calendar"></div>

            </div><!--(./col-lg-12 col-md-12 col-sm-12 col-xs-12"BELOW ROW:)-->
        </div><!--(./row)-->
    </div><!--(./COntainer")-->

 
    <div class="col-xs-4 col-xs-offset-5 footer">
        <img src="images/front_logo_bottom.png" />
        <p class="front_copyright">&copy; All Rights Reserved</p>
    </div>
</div>
