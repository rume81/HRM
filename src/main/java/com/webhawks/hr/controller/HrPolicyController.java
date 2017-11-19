package com.webhawks.hr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.webhawks.Hawks_mapper.SysPropertyMapper;
import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HPolicy;
import com.webhawks.Hawks_model.HSysProperty;
import com.webhawks.Hawks_model.HUpload;
import com.webhawks.hr.model.UserSession;
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

	List<HPolicy> policys = whrService.getAllPolicy(false);
	HPolicy selpolicy = new HPolicy();
	
	if (policys.size() > 0)
	    selpolicy = policys.get(0);

	HSysProperty sys = whrService.getSysPropertyByName("DOMAIN_NAME");
	
	
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("emp", selecteduser);
	mm.addAttribute("user", currentuser);
	mm.addAttribute("policys", policys);
	mm.addAttribute("selpolicy", selpolicy);
	mm.addAttribute("sys", sys.getProp_value());

	return new ModelAndView("policy", mm);
    }

    // ==================================================POST============================================================
    @RequestMapping(value = "/uploadpolicy", method = RequestMethod.POST)
    public ModelAndView edit_employee_nda(MultipartHttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	logger.info("****************************************** url= /uploadpolicy");
	Map<String, String[]> parameterMap = request.getParameterMap();
	Map<String, MultipartFile> fileMap = request.getFileMap();

	String pname = String.valueOf(parameterMap.get("pname")[0]);
	HPolicy policy = new HPolicy();
	policy.setPolicy_name(pname);

	MultipartFile mpf = fileMap.get("pdfdoc");
	if (null != mpf) {
	    HUpload ufile = new HUpload();
	    try {
		ufile.setLength(mpf.getBytes().length);
		ufile.setBytes(mpf.getBytes());
		ufile.setType(mpf.getContentType());
		ufile.setName(mpf.getOriginalFilename());
		ufile.setSaveName(String.valueOf(System.currentTimeMillis()));

		String path = WriteFile(ufile, "POLICY");
		ufile.setPath(path);
		policy.setPolicy_file(ufile);

	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} else {
	    // String photo_path = parameterMap.get("photo_path")[0];
	    HUpload ufile = new HUpload();
	    ufile.setPath("");
	    policy.setPolicy_file(ufile);
	}

	policy.setDeleted(false);
	policy.setModId(getUser().getAvator());

	ModelMap mm = new ModelMap();
	boolean res = whrService.addPolicy(policy);

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    @RequestMapping(value = "/deletepolicy", method = RequestMethod.POST)
    public ModelAndView deletepolicy(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /deletepolicy");
	UserSession session = getSessionService().getUserSession();

	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

		
	int id = Integer.parseInt(requestUri);
	
	HPolicy ps = new HPolicy();
		
	ps = whrService.getPolicy(id);
	
	boolean res=false;
	ps.setDeleted(true);
	ps.setModId(getUser().getAvator());
	    
	res = whrService.deletePolicy(ps);
	
	
	ModelMap mm = new ModelMap();

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
}
