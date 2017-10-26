package com.webhawks.hr.controller;

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
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class HrPolicyController extends BaseController implements ApplicationContextAware {

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

	@RequestMapping(value = "/policy", method = RequestMethod.GET)
	public ModelAndView noticeboard(HttpServletRequest request) throws Exception {
		logger.info("****************************************** url= /policy");
		
		boolean validSession = getSessionService().isSessionValid();
		if (!validSession) {
			return new ModelAndView("redirect:/");
		}

		HEmployee currentuser = whrService.getEmployeeById(String.valueOf(getUser().getEmp_id()), true);

		HEmployee selecteduser = whrService.getEmployeeById(String.valueOf(getSelectedUser().getEmp_id()), true);

		

		ModelMap mm = new ModelMap();
		mm.addAttribute("validSession", validSession);
		mm.addAttribute("emp", selecteduser);
		mm.addAttribute("user", currentuser);

		return new ModelAndView("policy", mm);
	}

}
