package com.webhawks.hr.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_model.HMAttendance;
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class HolidayController extends BaseController implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(PaySlipController.class);

	private ApplicationContext applicationContext;

	private IWhrService whrService;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setWhrService(IWhrService whrService) {
		this.whrService = whrService;
	}

	@RequestMapping(value = "/holidays", method = RequestMethod.GET)
	public ModelAndView noticeboard(HttpServletRequest request) throws Exception {
		logger.info("****************************************** url= /holidays");
		boolean validSession = getSessionService().isSessionValid();
		if (!validSession) {
			return new ModelAndView("redirect:/");
		}

		HEmployee currentuser = whrService.getEmployeeById(String.valueOf(getUser().getEmp_id()), true);

		HEmployee selecteduser = whrService.getEmployeeById(String.valueOf(getSelectedUser().getEmp_id()), true);

		List<String> monyear = new ArrayList<String>();

		HAttendance firstAtt = whrService.getFirstAttendance();
		Date dtnow = new Date();
		String firstdate = firstAtt.getAtt_date();

		int startmon = getMonth(firstdate);
		int startyear = getYear(firstdate);

		int curmon = Integer.parseInt(CustomDateFormate("MM").format(dtnow));
		int curyear = Integer.parseInt(CustomDateFormate("yyyy").format(dtnow));

		List<HHoliday> weekdays = whrService.getWeekEndsBetweenDates("1-" + startmon + "-" + startyear,
				"31-12-" + curyear);
		List<HHoliday> holiday = whrService.getAllHoliday(false, startyear + "-" + startmon + "-1");
		// String selectedValue = getTextMonth(curmon) + ", " + curyear;
		curmon = curmon + 1;
		if (curmon > 12) {
			curmon = 1;
			curyear = curyear + 1;
		}
		boolean loop = true;

		do {
			String val = getTextMonth(startmon) + ", " + startyear;

			monyear.add(val);

			startmon = startmon + 1;
			if (startmon > 12) {
				startmon = 1;
				startyear = startyear + 1;
			}

			if ((startmon == curmon) && (startyear == curyear)) {
				loop = false;
			}

		} while (loop);

		curmon = Integer.parseInt(CustomDateFormate("MM").format(dtnow));
		curyear = Integer.parseInt(CustomDateFormate("yyyy").format(dtnow));

		int noofpreviousmon = monyear.size();

		ModelMap mm = new ModelMap();
		mm.addAttribute("validSession", validSession);
		mm.addAttribute("prev", noofpreviousmon);
		mm.addAttribute("next", 12 - curmon);
		mm.addAttribute("emp", selecteduser);
		mm.addAttribute("user", currentuser);
		mm.addAttribute("curmon", curmon);
		mm.addAttribute("curyear", curyear);
		mm.addAttribute("startmon", startmon);
		mm.addAttribute("startyear", startyear);
		mm.addAttribute("holiday", holiday);
		mm.addAttribute("weekdays", weekdays);

		return new ModelAndView("holidays", mm);
	}

}
