package com.webhawks.hr.controller;

import java.util.List;
import java.util.Map;

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

import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HSupportingData;
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class OnBoardingController extends BaseController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(NoticeboardController.class);

    private ApplicationContext applicationContext;

    private IWhrService whrService;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

    public void setWhrService(IWhrService whrService) {
	this.whrService = whrService;
    }
    
    @RequestMapping(value = "/onboarding", method = RequestMethod.GET)
    public ModelAndView onboarding(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /noticeboard");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	String  emp = String.valueOf(getSessionService().getUserSession().getSelectedProfile().getEmp_id());
	HEmployee selectedEmp = whrService.getEmployeeById(emp,false);
	
	List<HSupportingData> designation = whrService.getDesignation();
	List<HSupportingData> department = whrService.getDepartment();
	List<HEmployee> allEmp = whrService.getAllEmployee(false);
	
	HSalaryDetails activeSal = whrService.getActiveSalaryDetails(getSelectedUser().getEmp_id());
		
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("user", getUser());
	mm.addAttribute("selecteduser", selectedEmp);
	mm.addAttribute("designation", designation);
	mm.addAttribute("department", department);
	mm.addAttribute("activeSal", activeSal);
	mm.addAttribute("allEmp", allEmp);
	
	return new ModelAndView("onboarding", mm);
    }
    
    // ==================================================POST============================================================
    
    @RequestMapping(value = "/onboarding_info", method = RequestMethod.POST)
    public ModelAndView postOnboarding_info(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /onboarding_info");
	Map<String, String[]> parameterMap = request.getParameterMap();
	
	String emp_id = String.valueOf(parameterMap.get("emp_id")[0]);
	HEmployee emp = whrService.getEmployeeById(emp_id,false);

	String jobtitle = parameterMap.get("jobtitle")[0];
	String noa = parameterMap.get("noa")[0];
	String designation = parameterMap.get("designation")[0];
	String loc = parameterMap.get("loc")[0];
	String responsiblefor = parameterMap.get("responsible")[0];
	int rptmanager = Integer.parseInt(parameterMap.get("rptmanager")[0]);
	String department = parameterMap.get("department")[0];
	String appdate = parameterMap.get("appdate")[0];
	String probationary = parameterMap.get("probationary")[0];
	String job_desc = parameterMap.get("jobdesc")[0];
		

	ModelMap mm = new ModelMap();

	emp.setJobtitle(jobtitle);
	emp.setJobnature(noa);
	emp.setDesignation(designation);
	emp.setWorkstation(loc);
	emp.setResponsiblefor(responsiblefor);
	emp.setRpt_mgr(rptmanager);
	emp.setDepartment(department);
	emp.setJoin_date(convertDateFormate(appdate));
	emp.setProbation_period(probationary);
	emp.setJob_desc(job_desc);
	emp.setDeleted(false);
	emp.setModId(getUser().getAvator());

	boolean res = whrService.editEmployee(emp,"onboarding");

	if (res) {
	    mm.addAttribute("msg", "OK");
	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    @RequestMapping(value = "/onboardingletter", method = RequestMethod.POST)
    public ModelAndView onboardingletter(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /onboardingletter");
	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 1) {
	    logger.warn("Expecting atleast 1 arguments but received " + requestUriSplit.length);
	    return null;
	}
	
	String letter = requestUriSplit[0];
	
	ModelMap mm = new ModelMap();
	
	if(letter.equals("OFFER")){
	    String  emp = String.valueOf(getSelectedUser().getEmp_id());
	    HEmployee selectedEmp = whrService.getEmployeeById(emp,false);
	    
	    HEmployee rptMgr = whrService.getEmployeeById(String.valueOf(selectedEmp.getRpt_mgr()), true);
	    
	    mm.addAttribute("emp", selectedEmp);
	    mm.addAttribute("rptMgr", rptMgr);
	    
	    return new ModelAndView("offerletter", mm); 
	}

	//mm.addAttribute("emp", teamemployee);

	return new ModelAndView("blank", mm);
    }
}
