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
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class NoticeboardController extends BaseController implements ApplicationContextAware {
    
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
    
    @RequestMapping(value = "/noticeboard", method = RequestMethod.GET)
    public ModelAndView noticeboard(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /noticeboard");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	
	HEmployee currentuser = whrService.getEmployeeById(String.valueOf(getUser().getEmp_id()), true);
	List<HEmployee> allemp = whrService.getAllEmployee(false);
	List<HNotice> allnotice = whrService.getNoticeByUser(currentuser.getEmp_id(), true);
	
		
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());
	mm.addAttribute("emps", allemp);
	mm.addAttribute("notice", allnotice);
	mm.addAttribute("user", currentuser);

	
	return new ModelAndView("noticeboard", mm);
    }
    
    // ==================================================POST============================================================
    /** Notice Add post */
    @RequestMapping(value = "/add_notice", method = RequestMethod.POST)
    public ModelAndView postEmployeeEdit(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /add_notice");
	Map<String, String[]> parameterMap = request.getParameterMap();
	HNotice notice = new HNotice();
	
	String title = parameterMap.get("title")[0];
	String details = parameterMap.get("details")[0];
	String expdate = parameterMap.get("expdate")[0];
	String access[] = parameterMap.get("chk");
	
	String acclist ="";
	for(int i=0;i<access.length;i++){
	    if((i+1) == access.length){
		acclist = acclist + access[i];
	    } else{
		acclist = acclist + access[i] + ",";
	    }
	}
	
	ModelMap mm = new ModelMap();
	
	notice.setTitle(title);
	notice.setDetails(details);
	notice.setExpdate(convertDateFormate(expdate));
	notice.setAccesslist(acclist);
	notice.setDeleted(false);
	notice.setModId(getUser().getAvator());
	
	boolean res = whrService.addNotice(notice);

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
}
