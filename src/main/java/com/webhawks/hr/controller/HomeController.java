package com.webhawks.hr.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HSupportingData;
import com.webhawks.Hawks_model.HTeam;
import com.webhawks.Hawks_model.HUpload;
import com.webhawks.hr.model.UserSession;
import com.webhawks.hr.services.interfaces.IWhrService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends BaseController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private ApplicationContext applicationContext;

    private IWhrService whrService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

    public void setWhrService(IWhrService whrService) {
	this.whrService = whrService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView init(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /");
	boolean validSession = getSessionService().isSessionValid();
	
	HEmployee selemp = getSessionService().getUserSession().getSelectedProfile();
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());

	if (validSession) {
	    if(selemp!=null){
		return new ModelAndView("redirect:/dashboard");
	    } else{
		return new ModelAndView("redirect:/employeeselection");
	    }
	}

	return new ModelAndView("redirect:/splash");
    }

    @RequestMapping(value = "/splash", method = RequestMethod.GET)
    public ModelAndView splash(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /splash");
	boolean validSession = getSessionService().isSessionValid();

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());

	if (validSession) {
	    return new ModelAndView("redirect:/");
	}

	return new ModelAndView("splash", mm);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /login");
	boolean validSession = getSessionService().isSessionValid();

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());

	if (validSession) {
	    return new ModelAndView("redirect:/dashboard");
	}

	return new ModelAndView("login", mm);
    }

    @RequestMapping(value = "/employeeselection", method = RequestMethod.GET)
    public ModelAndView employeeselection(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /employeeselection");
	boolean validSession = getSessionService().isSessionValid();
	
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	HEmployee curUser = getUser();
	if((!curUser.getUsertype().equals("Admin")) && (!curUser.getUsertype().equals("Talent Manager"))){
	    return new ModelAndView("redirect:/dashboard");
	}
	List<HTeam> teams = whrService.getAllTeam(false);

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", curUser);
	mm.addAttribute("teams", teams);

	
	
	return new ModelAndView("employeeselection", mm);
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /dashboard");
	boolean validSession = getSessionService().isSessionValid();

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());

	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	return new ModelAndView("dashboard", mm);
    }

    @RequestMapping(value = "/empprofile", method = RequestMethod.GET)
    public ModelAndView empprofile(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /empprofile");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	HEmployee selectedEmp = getSessionService().getUserSession().getSelectedProfile();

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());
	mm.addAttribute("selectedemp", selectedEmp);

	
	return new ModelAndView("empprofile", mm);
    }

    @RequestMapping(value = "/createempprofile", method = RequestMethod.GET)
    public ModelAndView createempprofile(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /createempprofile");
	boolean validSession = getSessionService().isSessionValid();

	HEmployee currentUser = getUser();
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", currentUser);

	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}

	if (!currentUser.getUsertype().equals("Admin")) {
	    return new ModelAndView("redirect:/dashboard");
	}
	return new ModelAndView("createempprofile", mm);
    }
    
    @RequestMapping(value = "/officialprofile", method = RequestMethod.GET)
    public ModelAndView officialprofile(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /officialprofile");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	
	HEmployee selectedEmp = getSessionService().getUserSession().getSelectedProfile();

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());
	mm.addAttribute("selectedemp", selectedEmp);

	
	return new ModelAndView("officialprofile", mm);
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public ModelAndView userLogout(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /user/logout");
	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	getSessionService().invalidateSession(getSessionService().getUserSession().getSessionId());

	return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editprofile", method = RequestMethod.GET)
    public ModelAndView personal_profile(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /editprofile");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	String  emp = String.valueOf(getSessionService().getUserSession().getSelectedProfile().getEmp_id());
	HEmployee logedUser = getSessionService().getUserSession().getUser();
	HEmployee selectedEmp = whrService.getEmployeeById(emp,false);
	
	List<HSupportingData> designation = whrService.getDesignation();
	List<HSupportingData> company = whrService.getCompany();
	List<HSupportingData> education = whrService.getEducation();
	List<HSupportingData> maritialStatus = whrService.getMaritialStatus();
	List<HSupportingData> sign = whrService.getSign();
	List<HSupportingData> department = whrService.getDepartment();
	List<HSupportingData> blood = whrService.getBlood();
	List<HSupportingData> utype = whrService.getUserType();

	List<HTeam> allTeam = whrService.getAllTeam(false);

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());
	mm.addAttribute("selectedemp", selectedEmp);
	mm.addAttribute("designation", designation);
	mm.addAttribute("company", company);
	mm.addAttribute("education", education);
	mm.addAttribute("maritialStatus", maritialStatus);
	mm.addAttribute("sign", sign);
	mm.addAttribute("blood", blood);
	mm.addAttribute("utype", utype);
	mm.addAttribute("department", department);
	mm.addAttribute("allTeam", allTeam);
	mm.addAttribute("usertype", logedUser.getUsertype());

	
	return new ModelAndView("editprofile", mm);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /registration");
	boolean validSession = getSessionService().isSessionValid();

	HEmployee selectedEmp = getSessionService().getUserSession().getSelectedProfile();
	List<HSupportingData> designation = whrService.getDesignation();
	List<HSupportingData> company = whrService.getCompany();
	List<HSupportingData> education = whrService.getEducation();
	List<HSupportingData> maritialStatus = whrService.getMaritialStatus();
	List<HSupportingData> sign = whrService.getSign();
	List<HSupportingData> department = whrService.getDepartment();
	List<HSupportingData> blood = whrService.getBlood();
	List<HSupportingData> utype = whrService.getUserType();

	List<HTeam> allTeam = whrService.getAllTeam(false);

	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("currentuser", getUser());
	mm.addAttribute("selectedemp", selectedEmp);
	mm.addAttribute("designation", designation);
	mm.addAttribute("company", company);
	mm.addAttribute("education", education);
	mm.addAttribute("maritialStatus", maritialStatus);
	mm.addAttribute("sign", sign);
	mm.addAttribute("blood", blood);
	mm.addAttribute("utype", utype);
	mm.addAttribute("department", department);
	mm.addAttribute("allTeam", allTeam);

	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	return new ModelAndView("registration", mm);
    }

    // ==================================================POST============================================================
    @RequestMapping(value = "/users/valideduser", method = RequestMethod.POST)
    public ModelAndView userVerify(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /users/valideduser");
	String requestUri = getStringFromHttpRequest(request);

	//logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 2) {
	    logger.warn("Expecting atleast 2 arguments but received " + requestUriSplit.length);
	    return null;
	}

	String uname = requestUriSplit[0];
	String pass = requestUriSplit[1];

	HEmployee user = new HEmployee();
	user.setAvator(uname);
	user.setPassword(pass);

	HEmployee sysuser = whrService.getUserValidation(user);
	String user_type = "-1";
	if (sysuser != null) {
	    UserSession session = getSessionService().insertSession(request, sysuser);
	    user_type = sysuser.getUsertype();
	    getSessionService().getUserSession().setUser(sysuser);
	    // if(!user_type.equals("Admin"))
	    // {
	    getSessionService().getUserSession().setSelectedProfile(sysuser);
	    // }
	}

	ModelMap mm = new ModelMap();
	mm.addAttribute("msg", user_type);

	return new ModelAndView("result", mm);
    }

    @RequestMapping(value = "/getteammember", method = RequestMethod.POST)
    public ModelAndView getteammember(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /getteammember");
	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 1) {
	    logger.warn("Expecting atleast 1 arguments but received " + requestUriSplit.length);
	    return null;
	}

	String team = requestUriSplit[0];

	List<HEmployee> teamemployee = whrService.getUserByTeam(team);

	ModelMap mm = new ModelMap();

	mm.addAttribute("emp", teamemployee);

	return new ModelAndView("employeeidlist", mm);
    }

    @RequestMapping(value = "/viewDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /viewDetails");
	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 2) {
	    logger.warn("Expecting atleast 2 arguments but received " + requestUriSplit.length);
	    return null;
	}

	String team = requestUriSplit[0];
	String emp = requestUriSplit[1];

	if (emp.equals("")) {
	    emp = String.valueOf(getUser().getEmp_id());
	}

	HEmployee selectedEmployee = whrService.getEmployeeById(emp,true);

	ModelMap mm = new ModelMap();

	if (selectedEmployee.getEmp_id() != null) {
	    mm.addAttribute("msg", selectedEmployee.getEmp_id());
	    getSessionService().getUserSession().setSelectedProfile(selectedEmployee);
	} else {
	    mm.addAttribute("msg", "-1");
	}

	return new ModelAndView("result", mm);
    }

    /** Employee Registration post */
    @RequestMapping(value = "/register_employee", method = RequestMethod.POST)
    public ModelAndView postEmployeeRegistration(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /register_employee");
	Map<String, String[]> parameterMap = request.getParameterMap();
	/*Map<String, MultipartFile> fileMap = request.getFileMap();*/

	HEmployee emp = new HEmployee();

	String fname = parameterMap.get("fname")[0];
	String lname = parameterMap.get("lname")[0];
	String alias = parameterMap.get("alias")[0];
	String designation = parameterMap.get("designation1")[0];
	String designation2 = parameterMap.get("designation2")[0];
	String company = parameterMap.get("company")[0];
	String department = parameterMap.get("department")[0];
	String official_mail = parameterMap.get("official_mail")[0];
	String personal_mail = parameterMap.get("personal_mail")[0];
	String phone = parameterMap.get("phone")[0];
	String referral = parameterMap.get("referral")[0];
	String joining_date = parameterMap.get("joining_date")[0];
	String skype_id = parameterMap.get("skype_id")[0];
	String previous_work = parameterMap.get("previous_work")[0];
	String reporting_manager = parameterMap.get("reporting_manager")[0];
	String birthdate_real = parameterMap.get("birthdate_real")[0];
	String birthdate_certificate = parameterMap.get("birthdate_certificate")[0];
	String education = parameterMap.get("education")[0];
	String nid = parameterMap.get("nid")[0];
	String passport = parameterMap.get("passport")[0];
	String maritial_status = parameterMap.get("maritial_status")[0];
	String strSiblings = parameterMap.get("siblings")[0];
	if(strSiblings.equals(""))
	    strSiblings = "0";
	Integer siblings = Integer.parseInt(strSiblings);
	String hobbies = parameterMap.get("hobbies")[0];
	String activities = parameterMap.get("activities")[0];
	String emegency_phone = parameterMap.get("emegency_phone")[0];
	String relation = parameterMap.get("relation")[0];
	String zodiac = parameterMap.get("zodiac")[0];
	String blood = parameterMap.get("blood")[0];
	String emp_num = parameterMap.get("emp_num")[0];
	String bank_name = parameterMap.get("bank_name")[0];
	String bank_account = parameterMap.get("bank_account")[0];
	String officie_phone = parameterMap.get("officie_phone")[0];
	String extension = parameterMap.get("extension")[0];
	String chat_nick = parameterMap.get("chat_nick")[0];
	String present_address = parameterMap.get("present_address")[0];
	String permanent_address = parameterMap.get("permanent_address")[0];
	String userType = parameterMap.get("userType")[0];
	String team = parameterMap.get("team")[0];
	

	ModelMap mm = new ModelMap();

	emp.setFirst_name(fname);
	emp.setLast_name(lname);
	emp.setAvator(alias);
	emp.setDesignation(designation);
	emp.setDesignation2(designation2);
	emp.setCompany(company);
	emp.setDepartment(department);
	emp.setOfficial_mail(official_mail);
	emp.setPersonal_email(personal_mail);
	emp.setMobile(phone);
	emp.setRefferal_no(referral);
	emp.setJoin_date(convertDateFormate(joining_date));
	emp.setSkype_id(skype_id);
	emp.setPrv_work_place(previous_work);
	emp.setRpt_mgr(reporting_manager);
	emp.setBirthdate_certificate(convertDateFormate(birthdate_certificate));
	emp.setBirthdate_real(convertDateFormate(birthdate_real));
	emp.setEducation(education);
	emp.setNid(nid);
	emp.setPassport(passport);
	emp.setMaritial_status(maritial_status);
	emp.setNo_siblings(siblings);
	emp.setHobbies(hobbies);
	emp.setExtra_activitie(activities);
	emp.setEmergency_phone(emegency_phone);
	emp.setRelation(relation);
	emp.setZodiac(zodiac);
	emp.setBloodgroup(blood);
	emp.setEmp_number(emp_num);
	emp.setBank_name(bank_name);
	emp.setBank_acc_no(bank_account);
	emp.setOffical_phone(officie_phone);
	emp.setExtension(extension);
	emp.setChat_nick(chat_nick);
	emp.setPresent_address(present_address);
	emp.setPermanent_address(permanent_address);
	emp.setUsertype(userType);
	emp.setTeamId(Integer.parseInt(team));
	emp.setDeleted(false);
	emp.setModId(getUser().getAvator());
	HUpload prof = new HUpload();
	prof.setPath("images/roundedprofile.png");
	emp.setProfPic(prof);
	HUpload app = new HUpload();
	app.setPath("");
	emp.setAppointment(app);
	emp.setNda(app);
	emp.setAbout_me("");

	boolean res = whrService.registerEmployee(emp);

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    /** Employee Edit photo post */
    @RequestMapping(value = "/edit_employee_photo", method = RequestMethod.POST)
    public ModelAndView edit_employee_photo(MultipartHttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	logger.info("****************************************** url= /edit_employee_photo");
	Map<String, String[]> parameterMap = request.getParameterMap();
	Map<String, MultipartFile> fileMap = request.getFileMap();
	
	String emp_id = String.valueOf(parameterMap.get("emp_id_ph")[0]);
	HEmployee emp = whrService.getEmployeeById(emp_id,false);
	
	boolean Fo = deleteFile(emp.getProfPic());
	
    	MultipartFile mpf = fileMap.get("profile_img");
    	if (null != mpf) {
    	    HUpload ufile = new HUpload();
    	    try {
    		ufile.setLength(mpf.getBytes().length);
    		ufile.setBytes(mpf.getBytes());
    		ufile.setType(mpf.getContentType());
    		ufile.setName(mpf.getOriginalFilename());
    		ufile.setSaveName(String.valueOf(System.currentTimeMillis()));
    
    		String path = WriteFile(ufile,"PROFILE");
    		ufile.setPath(path);
    		emp.setProfPic(ufile);
    	    } catch (IOException e) {
    		e.printStackTrace();
    	    }
    	} else {
    	    String photo_path = parameterMap.get("photo_path")[0];
    	    HUpload ufile = new HUpload();
    	    ufile.setPath(photo_path);
    	    emp.setProfPic(ufile);
    	}
	
	ModelMap mm = new ModelMap();
	boolean res = whrService.editEmployee(emp,"profile_pic");

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    
    /** Employee Edit appointment post */
    @RequestMapping(value = "/edit_employee_appoinment", method = RequestMethod.POST)
    public ModelAndView edit_employee_appoinment(MultipartHttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	logger.info("****************************************** url= /edit_employee_appoinment");
	Map<String, String[]> parameterMap = request.getParameterMap();
	Map<String, MultipartFile> fileMap = request.getFileMap();
	
	String emp_id = String.valueOf(parameterMap.get("emp_id_ap")[0]);
	HEmployee emp = whrService.getEmployeeById(emp_id,false);
	
	boolean Fo = deleteFile(emp.getAppointment());
	
    	MultipartFile mpf = fileMap.get("appoinment");
    	if (null != mpf) {
    	    HUpload ufile = new HUpload();
    	    try {
    		ufile.setLength(mpf.getBytes().length);
    		ufile.setBytes(mpf.getBytes());
    		ufile.setType(mpf.getContentType());
    		ufile.setName(mpf.getOriginalFilename());
    		ufile.setSaveName(String.valueOf(System.currentTimeMillis()));
    
    		String path = WriteFile(ufile,"APPOINTMENT");
    		ufile.setPath(path);
    		emp.setAppointment(ufile);
    	    } catch (IOException e) {
    		e.printStackTrace();
    	    }
    	} else {
    	    //String photo_path = parameterMap.get("photo_path")[0];
    	    HUpload ufile = new HUpload();
    	    ufile.setPath("");
    	    emp.setAppointment(ufile);
    	}
	
	ModelMap mm = new ModelMap();
	boolean res = whrService.editEmployee(emp,"appointment");

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    /** Employee Edit nda post */
    @RequestMapping(value = "/edit_employee_nda", method = RequestMethod.POST)
    public ModelAndView edit_employee_nda(MultipartHttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	logger.info("****************************************** url= /edit_employee_nda");
	Map<String, String[]> parameterMap = request.getParameterMap();
	Map<String, MultipartFile> fileMap = request.getFileMap();
	
	String emp_id = String.valueOf(parameterMap.get("emp_id_nda")[0]);
	HEmployee emp = whrService.getEmployeeById(emp_id,false);
	
	boolean Fo = deleteFile(emp.getNda());
	
    	MultipartFile mpf = fileMap.get("nda");
    	if (null != mpf) {
    	    HUpload ufile = new HUpload();
    	    try {
    		ufile.setLength(mpf.getBytes().length);
    		ufile.setBytes(mpf.getBytes());
    		ufile.setType(mpf.getContentType());
    		ufile.setName(mpf.getOriginalFilename());
    		ufile.setSaveName(String.valueOf(System.currentTimeMillis()));
    
    		String path = WriteFile(ufile,"NDA");
    		ufile.setPath(path);
    		emp.setNda(ufile);
    	    } catch (IOException e) {
    		e.printStackTrace();
    	    }
    	} else {
    	    //String photo_path = parameterMap.get("photo_path")[0];
    	    HUpload ufile = new HUpload();
    	    ufile.setPath("");
    	    emp.setNda(ufile);
    	}
	
	ModelMap mm = new ModelMap();
	boolean res = whrService.editEmployee(emp,"nda");

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    /** Employee Edit post */
    @RequestMapping(value = "/edit_employee", method = RequestMethod.POST)
    public ModelAndView postEmployeeEdit(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /edit_employee");
	Map<String, String[]> parameterMap = request.getParameterMap();
	
	String emp_id = String.valueOf(parameterMap.get("emp_id")[0]);
	HEmployee emp = whrService.getEmployeeById(emp_id,false);

	String fname = parameterMap.get("fname")[0];
	String lname = parameterMap.get("lname")[0];
	String alias = parameterMap.get("avator")[0];
	String designation = parameterMap.get("designation")[0];
	String designation2 = parameterMap.get("designation2")[0];
	String company = parameterMap.get("company")[0];
	String department = parameterMap.get("department")[0];
	String official_mail = parameterMap.get("official_mail")[0];
	String personal_mail = parameterMap.get("personal_mail")[0];
	String phone = parameterMap.get("phone")[0];
	String referral = parameterMap.get("referral")[0];
	String joining_date = parameterMap.get("joining_date")[0];
	String skype_id = parameterMap.get("skype_id")[0];
	String previous_work = parameterMap.get("previous_work")[0];
	String reporting_manager = parameterMap.get("reporting_manager")[0];
	String birthdate_real = parameterMap.get("birthdate_real")[0];
	String birthdate_certificate = parameterMap.get("birthdate_certificate")[0];
	String education = parameterMap.get("education")[0];
	String nid = parameterMap.get("nid")[0];
	String passport = parameterMap.get("passport")[0];
	String maritial_status = parameterMap.get("maritial_status")[0];
	Integer siblings = Integer.parseInt(parameterMap.get("siblings")[0]);
	String hobbies = parameterMap.get("hobbies")[0];
	String activities = parameterMap.get("activities")[0];
	String emegency_phone = parameterMap.get("emegency_phone")[0];
	String relation = parameterMap.get("relation")[0];
	String zodiac = parameterMap.get("zodiac")[0];
	String blood = parameterMap.get("blood")[0];
	String emp_num = parameterMap.get("emp_num")[0];
	String bank_name = parameterMap.get("bank_name")[0];
	String bank_account = parameterMap.get("bank_account")[0];
	String officie_phone = parameterMap.get("officie_phone")[0];
	String extension = parameterMap.get("extension")[0];
	String chat_nick = parameterMap.get("chat_nick")[0];
	String present_address = parameterMap.get("present_address")[0];
	String permanent_address = parameterMap.get("permanent_address")[0];
	String userType = parameterMap.get("userType")[0];
	String team = parameterMap.get("team")[0];
	
	String ablout_me = parameterMap.get("ablout_me")[0];

	ModelMap mm = new ModelMap();

	emp.setFirst_name(fname);
	emp.setLast_name(lname);
	emp.setAvator(alias);
	emp.setDesignation(designation);
	emp.setDesignation2(designation2);
	emp.setCompany(company);
	emp.setDepartment(department);
	emp.setOfficial_mail(official_mail);
	emp.setPersonal_email(personal_mail);
	emp.setMobile(phone);
	emp.setRefferal_no(referral);
	emp.setJoin_date(convertDateFormate(joining_date));
	emp.setSkype_id(skype_id);
	emp.setPrv_work_place(previous_work);
	emp.setRpt_mgr(reporting_manager);
	emp.setBirthdate_certificate(convertDateFormate(birthdate_certificate));
	emp.setBirthdate_real(convertDateFormate(birthdate_real));
	emp.setEducation(education);
	emp.setNid(nid);
	emp.setPassport(passport);
	emp.setMaritial_status(maritial_status);
	emp.setNo_siblings(siblings);
	emp.setHobbies(hobbies);
	emp.setExtra_activitie(activities);
	emp.setEmergency_phone(emegency_phone);
	emp.setRelation(relation);
	emp.setZodiac(zodiac);
	emp.setBloodgroup(blood);
	emp.setEmp_number(emp_num);
	emp.setBank_name(bank_name);
	emp.setBank_acc_no(bank_account);
	emp.setOffical_phone(officie_phone);
	emp.setExtension(extension);
	emp.setChat_nick(chat_nick);
	emp.setPresent_address(present_address);
	emp.setPermanent_address(permanent_address);
	emp.setUsertype(userType);
	emp.setTeamId(Integer.parseInt(team));
	emp.setAbout_me(ablout_me);
	emp.setDeleted(false);
	emp.setModId(getUser().getAvator());

	boolean res = whrService.editEmployee(emp,"details");

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
}
