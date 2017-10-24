package com.webhawks.hr.controller;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
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
import org.springframework.web.servlet.ModelAndView;

import com.webhawks.Hawks_model.HMAttendance;
import com.webhawks.Hawks_model.HMail;
import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HLeaveApprover;
import com.webhawks.Hawks_model.HLeaveQuota;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HTypesOfLeave;
import com.webhawks.hr.model.UserSession;
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class AttendanceController extends BaseController implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    private ApplicationContext applicationContext;

    private IWhrService whrService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	this.applicationContext = applicationContext;
    }

    public void setWhrService(IWhrService whrService) {
	this.whrService = whrService;
    }

    @RequestMapping(value = "/attendance", method = RequestMethod.GET)
    public ModelAndView attendance(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /attendance");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	
	ModelMap mm = new ModelMap();
	
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

	List<HMAttendance> thisMonAtt = new ArrayList<HMAttendance>();
	thisMonAtt = whrService.getAttendanceForMonth(curmon, curyear, selecteduser.getEmp_id(), false);

	String selectedValue = getTextMonth(curmon) + ", " + curyear;
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
	
	List<HTypesOfLeave> allleavetype = whrService.getLeaveType(false);
	List<HLeaveApprover> approver = whrService.getLeaveApprovers(false, selecteduser.getEmp_id());

	// Leave Quotas
	Date today = new Date();
	SimpleDateFormat dfy = new SimpleDateFormat("yyyy");
	SimpleDateFormat dfm = new SimpleDateFormat("MM");
	int curyy = Integer.parseInt(dfy.format(today));
	int curmm = Integer.parseInt(dfm.format(today));

	List<HLeaveQuota> quotaYear = whrService.getQuotaYear();
	mm.addAttribute("quotaYear", quotaYear);

	String selectYear = "";
	if (curmm <= 6) {
	    selectYear = String.valueOf(curyy - 1) + "/" + String.valueOf(curyy).substring(2);
	} else {
	    selectYear = String.valueOf(curyy) + "/" + String.valueOf(curyy + 1).substring(2);
	}
	mm.addAttribute("selectYear", selectYear);

	List<HLeaveQuota> quotas = whrService.getLeaveQuotas(selecteduser.getEmp_id(), selectYear);

	mm.addAttribute("quotas", quotas);

	List<HTypesOfLeave> leavetype = new ArrayList<HTypesOfLeave>();
	for (HTypesOfLeave type : allleavetype) {
	    int typeid = type.getId();
	    int deductfromid = type.getDeductfrom();
	    if ((type.getIsquota()) || (deductfromid != 0)) {
		for (HLeaveQuota quota : quotas) {
		    if ((quota.getType_id() == typeid) || (quota.getType_id() == deductfromid)) {
			if ((quota.getQuota() - quota.getUsed()) >= type.getLeavededuct()) {
			    leavetype.add(type);
			}
		    }
		}
	    } else {
		leavetype.add(type);
	    }
	}
	
	List<HEmployee> allEmp = whrService.getAllEmployee(false);
	for (HEmployee employee : allEmp) {
	    if (employee.getEmp_id() == selecteduser.getEmp_id()) {
		allEmp.remove(employee);
		break;
	    }
	}
	
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("user", currentuser);
	mm.addAttribute("emp", selecteduser);
	mm.addAttribute("monyear", monyear);
	mm.addAttribute("selectedVal", selectedValue);
	mm.addAttribute("att", thisMonAtt);
	
	mm.addAttribute("leavetype", leavetype);
	mm.addAttribute("allleavetype", allleavetype);
	mm.addAttribute("approver", approver);
	mm.addAttribute("allEmp", allEmp);

	return new ModelAndView("attendance", mm);
    }

    @RequestMapping(value = "/leave", method = RequestMethod.GET)
    public ModelAndView leave(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /leave");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	ModelMap mm = new ModelMap();

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
	
	//List<HLeave> allLeave = whrService.getAllLeaveByEmpId(false, selecteduser.getEmp_id());
	List<HLeave> allLeave = whrService.getLeaveInfoByMonth(curmon, curyear, selecteduser.getEmp_id(),0);
	List<HLeave> paidLeave = new ArrayList<HLeave>();
	List<HLeave> withoutpaidLeave = new ArrayList<HLeave>();
	for(HLeave lev:allLeave){
	    if((lev.getLeavetype().getId() == 1) || (lev.getLeavetype().getId() == 2)){
		paidLeave.add(lev);
	    } else{
		withoutpaidLeave.add(lev);
	    }
	}
	
	String selectedValue = getTextMonth(curmon) + ", " + curyear;
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

	List<HTypesOfLeave> allleavetype = whrService.getLeaveType(false);
	List<HLeaveApprover> approver = whrService.getLeaveApprovers(false, selecteduser.getEmp_id());

	// Leave Quotas
	Date today = new Date();
	SimpleDateFormat dfy = new SimpleDateFormat("yyyy");
	SimpleDateFormat dfm = new SimpleDateFormat("MM");
	int curyy = Integer.parseInt(dfy.format(today));
	int curmm = Integer.parseInt(dfm.format(today));

	List<HLeaveQuota> quotaYear = whrService.getQuotaYear();
	mm.addAttribute("quotaYear", quotaYear);

	String selectYear = "";
	if (curmm <= 6) {
	    selectYear = String.valueOf(curyy - 1) + "/" + String.valueOf(curyy).substring(2);
	} else {
	    selectYear = String.valueOf(curyy) + "/" + String.valueOf(curyy + 1).substring(2);
	}
	mm.addAttribute("selectYear", selectYear);

	List<HLeaveQuota> quotas = whrService.getLeaveQuotas(selecteduser.getEmp_id(), selectYear);

	mm.addAttribute("quotas", quotas);

	List<HTypesOfLeave> leavetype = new ArrayList<HTypesOfLeave>();
	for (HTypesOfLeave type : allleavetype) {
	    int typeid = type.getId();
	    int deductfromid = type.getDeductfrom();
	    if ((type.getIsquota()) || (deductfromid != 0)) {
		for (HLeaveQuota quota : quotas) {
		    if ((quota.getType_id() == typeid) || (quota.getType_id() == deductfromid)) {
			if ((quota.getQuota() - quota.getUsed()) >= type.getLeavededuct()) {
			    leavetype.add(type);
			}
		    }
		}
	    } else {
		leavetype.add(type);
	    }
	}
	
	List<HEmployee> allEmp = whrService.getAllEmployee(false);
	for (HEmployee employee : allEmp) {
	    if (employee.getEmp_id() == selecteduser.getEmp_id()) {
		allEmp.remove(employee);
		break;
	    }
	}
	
	

	mm.addAttribute("pl", paidLeave);
	mm.addAttribute("wpl", withoutpaidLeave);
	
	mm.addAttribute("leavetype", leavetype);

	mm.addAttribute("validSession", validSession);
	mm.addAttribute("user", currentuser);
	mm.addAttribute("emp", selecteduser);
	mm.addAttribute("monyear", monyear);
	mm.addAttribute("selectedVal", selectedValue);

	mm.addAttribute("allleavetype", allleavetype);
	mm.addAttribute("approver", approver);
	mm.addAttribute("allEmp", allEmp);

	return new ModelAndView("leave", mm);
    }
    
    // ==================================================POST============================================================
    @RequestMapping(value = "/getQuota", method = RequestMethod.POST)
    public ModelAndView getQuota(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /getQuota");
	UserSession session = getSessionService().getUserSession();

	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 2) {
	    logger.warn("Expecting atleast 2 arguments but received " + requestUriSplit.length);
	    return null;
	}
	int emp_id = Integer.parseInt(requestUriSplit[0]);
	String year = requestUriSplit[1];

	List<HTypesOfLeave> allleavetype = whrService.getLeaveType(false);

	List<HLeaveQuota> quotas = whrService.getLeaveQuotas(emp_id, year);

	ModelMap mm = new ModelMap();

	mm.addAttribute("allleavetype", allleavetype);
	mm.addAttribute("quotas", quotas);

	return new ModelAndView("quota", mm);
    }
    
    @RequestMapping(value = "/calculateLeave", method = RequestMethod.POST)
    public ModelAndView calculateLeave(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /calculateLeave");
	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 7) {
	    logger.warn("Expecting atleast 7 arguments but received " + requestUriSplit.length);
	    return null;
	}
	ModelMap mm = new ModelMap();
	String fdate = requestUriSplit[0];
	String tdate = requestUriSplit[1];
	int empId = Integer.parseInt(requestUriSplit[2]);
	int excludeLeave = Integer.parseInt(requestUriSplit[3]);
	int leavetypeid = Integer.parseInt(requestUriSplit[4]);
	String selectYear = requestUriSplit[5];
	float seldays = Float.parseFloat(requestUriSplit[6]);

	HTypesOfLeave type = whrService.getLeaveTypeById(leavetypeid);
	// 7 days check
	boolean oldmorethen7 = false;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date
	toDate = new Date(); String formateddate = convertDateFormate(fdate);
	Date fromDate = sdf.parse(formateddate);
	 
	Calendar c = Calendar.getInstance(); c.setTime(fromDate);
	c.add(Calendar.DATE, 10); if (c.getTime().compareTo(toDate) < 0) {
	oldmorethen7 = true; }
	 
	// 7 days check

	List<String> nod = whrService.calculateLeaveDate(fdate, tdate, empId, excludeLeave);

	// Leave Quotas
	boolean validQuota = true;
	if (type.getIsquota())
	    validQuota = DateCompare(fdate, tdate, selectYear);

	List<HLeaveQuota> quotas = whrService.getLeaveQuotas(empId, selectYear);

	float nodselected = nod.size();
	if ((type.getLeavededuct() < 1) && (type.getLeavededuct() > 0)) {
	    nodselected = type.getLeavededuct();
	}
	boolean leaveAvail = false;
	float noofdayavail = 0;
	if (!type.getIsquota()) {
	    leaveAvail = true;
	} else {
	    for (HLeaveQuota qutoa : quotas) {
		if ((qutoa.getType_id() == type.getId()) || (qutoa.getType_id() == type.getDeductfrom())) {
		    if (excludeLeave == 0) {
			if ((qutoa.getQuota() - qutoa.getUsed()) >= nodselected) {
			    leaveAvail = true;
			    noofdayavail = qutoa.getQuota() - qutoa.getUsed();
			    break;
			} else {
			    leaveAvail = false;
			    noofdayavail = qutoa.getQuota() - qutoa.getUsed();
			    break;
			}
		    } else {
			if (((qutoa.getQuota() - qutoa.getUsed()) + seldays) >= nodselected) {
			    leaveAvail = true;
			    noofdayavail = (qutoa.getQuota() - qutoa.getUsed()) + seldays;
			    break;
			} else {
			    leaveAvail = false;
			    noofdayavail = (qutoa.getQuota() - qutoa.getUsed()) + seldays;
			    break;
			}
		    }
		}
	    }
	}
	// Leave Quotas
	if ((nod.size() > 0) && (leaveAvail) && (validQuota) && (!oldmorethen7)) {
	    mm.addAttribute("nod", nod);
	    mm.addAttribute("nsize", nodselected);

	    return new ModelAndView("leavelist", mm);
	} else if (!leaveAvail) {
	    mm.addAttribute("msg", "Not Available Quota for " + type.getLeavetype());
	    return new ModelAndView("result", mm);
	} else if (!validQuota) {
	    mm.addAttribute("msg", "Selected Quota Not Match With Selected Date");
	    return new ModelAndView("result", mm);
	} else if (oldmorethen7) {
	    mm.addAttribute("msg", "You Did Not raised 7 days old leave request.");
	    return new ModelAndView("result", mm);
	} else {
	    mm.addAttribute("msg", "-1");

	    return new ModelAndView("result", mm);
	}
    }
    
    
    @RequestMapping(value = "/addleaverequest", method = RequestMethod.POST)
    public ModelAndView addleaverequest(HttpServletRequest request, HttpServletResponse response)
	    throws MessagingException {
	UserSession session = getSessionService().getUserSession();

	Map<String, String[]> parameterMap = request.getParameterMap();

	HLeave leaveInfo = new HLeave();

	leaveInfo.setEmp_id(Integer.parseInt(parameterMap.get("emp_id")[0]));
	HTypesOfLeave obj = new HTypesOfLeave();
	Integer leaveId = Integer.parseInt(parameterMap.get("leavetype")[0]);
	obj = whrService.getLeaveTypeById(leaveId);
	leaveInfo.setLeavetype(obj);
	leaveInfo.setFdate(convertDateFormate(parameterMap.get("fdate")[0]));
	leaveInfo.setTdate(convertDateFormate(parameterMap.get("tdate")[0]));
	leaveInfo.setDays(Float.parseFloat(parameterMap.get("days")[0]));
	leaveInfo.setReason(parameterMap.get("reason")[0]);
	HLeaveQuota objquota = new HLeaveQuota();
	String quotayear = parameterMap.get("year")[0];
	objquota = whrService.getLeaveQuota(leaveInfo.getEmp_id(), quotayear, leaveInfo.getLeavetype().getId());
	leaveInfo.setLeavequota(objquota);

	int noa = Integer.parseInt(parameterMap.get("appsize")[0]);

	String app1 = "", app2 = "", app3 = "", app4 = "", app5 = "";

	if (noa > 0) {
	    app1 = parameterMap.get("approvar1")[0];
	    if (!app1.equals(""))
		leaveInfo.setApprovar1(Integer.parseInt(app1));
	}
	if (noa > 1) {
	    app2 = parameterMap.get("approvar2")[0];
	    if (!app2.equals(""))
		leaveInfo.setApprovar2(Integer.parseInt(app2));
	}
	if (noa > 2) {
	    app3 = parameterMap.get("approvar3")[0];
	    if (!app3.equals(""))
		leaveInfo.setApprovar3(Integer.parseInt(app3));
	}
	if (noa > 3) {
	    app4 = parameterMap.get("approvar4")[0];
	    if (!app4.equals(""))
		leaveInfo.setApprovar4(Integer.parseInt(app4));
	}
	if (noa > 4) {
	    app5 = parameterMap.get("approvar5")[0];
	    if (!app5.equals(""))
		leaveInfo.setApprovar5(Integer.parseInt(app5));
	}

	leaveInfo.setStatus(0);
	leaveInfo.setApp1state(false);
	leaveInfo.setApp2state(false);
	leaveInfo.setApp3state(false);
	leaveInfo.setApp4state(false);
	leaveInfo.setApp5state(false);
	leaveInfo.setDeleted(false);
	leaveInfo.setModId(session.getUser().getAvator());
	Date now = new Date();
	leaveInfo.setApllieddate(DateTimeFormateforDB().format(now));
	leaveInfo.setModifydate(DateTimeFormateforDB().format(now));

	Integer emid = whrService.insertHLeaveInfo(leaveInfo);
	// Update Leave Quota
	// HLeaveQuota quota = wamsService.getLeaveQuotas(empId, year);
	// Update Leave Quota
	HEmployee employee = whrService.getEmployeeById(String.valueOf(emid),true);
	// Send Mail to Approver for Approve Leave request
	if ((emid == leaveInfo.getEmp_id()) && (!app1.equals("")) && (app1 != null)) {
	    String EMailto[] = new String[1];
	    HEmployee approvarProfile = whrService.getEmployeeById(app1,true);
	    EMailto[0] = approvarProfile.getEmail();
	    String subject = "Leave Approval Request";
	    Map<String, String> email = new HashMap<String, String>();

	    email.put("<#LEAVE_TYPE#>", leaveInfo.getLeavetype().getLeavetype());
	    email.put("<#REASON#>", leaveInfo.getReason());
	    email.put("<#NO_OF_DAYS#>",
		    (leaveInfo.getDays() <= 1 ? leaveInfo.getDays() + " day" : leaveInfo.getDays() + " day's"));
	    email.put("<#DATES#>", (leaveInfo.getDays() <= 1 ? leaveInfo.getFdate()
		    : leaveInfo.getFdate() + " to " + leaveInfo.getTdate()));
	    email.put("<#EMP_NAME#>", employee.getEmployee_name());
	    email.put("<#DESIGNATION#>", employee.getDesignation());
	    email.put("<#EMP_EMAIL#>", employee.getEmail());

	    /*
	     * String link = request.getHeader("referer"); int index =
	     * link.indexOf("WAMS"); String requestUri = link.substring(0,
	     * index);
	     */
	    String requestUri = "http://123.200.15.18:8080/";
	    email.put("<#APPROVE_LINK#>", requestUri + "WAMS/leaveapprovalList");

	    String body = getEmialTemplateFromResource("leaverequest", email);

	    // prepare for save
	    Date date = new Date();
	    HMail mail = new HMail();
	    mail.setTo(EMailto[0]);
	    mail.setFrom("info.webhawksit@gmail.com");
	    mail.setSubject(subject);
	    mail.setCc(employee.getEmail());
	    mail.setBcc("");
	    mail.setDate(DateTimeFormateforDB().format(date));
	    mail.setMsg(escapeHtml4(body));
	    mail.setStatus(0);
	    mail.setSendTime(DateTimeFormateforDB().format(date));
	    mail.setDeleted(false);
	    mail.setModId(session.getUser().getAvator());
	    if (mail.getTo().equals("") || mail.getTo().equals("null")) {
		mail.setTo("ala@webhawksit.com");
		mail.setSubject("null value find for Mail to address");
	    }

	    int stat = whrService.sendMail(mail);
	    // prepare for save
	    // sendEmail(EMailto, employee.getEmail(), subject, body,
	    // mailSender);

	}
	// Send Mail to Approver for Approve Leave request

	ModelMap mm = new ModelMap();
	mm.addAttribute("msg", emid);

	return new ModelAndView("result", mm);
    }
    
    @RequestMapping(value = "/searchattendance", method = RequestMethod.POST)
    public ModelAndView searchattendance(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /searchattendance");
	UserSession session = getSessionService().getUserSession();

	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 2) {
	    logger.warn("Expecting atleast 2 arguments but received " + requestUriSplit.length);
	    return null;
	}
	
	Date dtnow = new Date();
	int curmon = Integer.parseInt(CustomDateFormate("MM").format(dtnow));
	int curyear = Integer.parseInt(CustomDateFormate("yyyy").format(dtnow));
	
	int emp_id = Integer.parseInt(requestUriSplit[0]);
	String monyear = requestUriSplit[1];
	
	String[] criteria = monyear.split(", ");
	
	if(!criteria[0].equals("")){
	    curmon = getNumericMonth(criteria[0]);
	} 
	
	if(!criteria[1].equals("")){
	    curyear = Integer.parseInt(criteria[1]);
	} 
	
	
	List<HMAttendance> thisMonAtt = new ArrayList<HMAttendance>();
	thisMonAtt = whrService.getAttendanceForMonth(curmon, curyear, emp_id, false);

	ModelMap mm = new ModelMap();

	mm.addAttribute("att", thisMonAtt);

	return new ModelAndView("attendancesearch", mm);
    }
    
    @RequestMapping(value = "/searchleave", method = RequestMethod.POST)
    public ModelAndView searchleave(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /searchleave");
	UserSession session = getSessionService().getUserSession();

	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 2) {
	    logger.warn("Expecting atleast 2 arguments but received " + requestUriSplit.length);
	    return null;
	}
	
	Date dtnow = new Date();
	int curmon = Integer.parseInt(CustomDateFormate("MM").format(dtnow));
	int curyear = Integer.parseInt(CustomDateFormate("yyyy").format(dtnow));
	
	int emp_id = Integer.parseInt(requestUriSplit[0]);
	String monyear = requestUriSplit[1];
	
	String[] criteria = monyear.split(", ");
	
	if(!criteria[0].equals("")){
	    curmon = getNumericMonth(criteria[0]);
	} 
	
	if(!criteria[1].equals("")){
	    curyear = Integer.parseInt(criteria[1]);
	} 
	
	List<HLeave> allLeave = whrService.getLeaveInfoByMonth(curmon, curyear, emp_id,0);
	List<HLeave> paidLeave = new ArrayList<HLeave>();
	List<HLeave> withoutpaidLeave = new ArrayList<HLeave>();
	for(HLeave lev:allLeave){
	    if((lev.getLeavetype().getId() == 1) || (lev.getLeavetype().getId() == 2)){
		paidLeave.add(lev);
	    } else{
		withoutpaidLeave.add(lev);
	    }
	}
	
	ModelMap mm = new ModelMap();

	mm.addAttribute("pl", paidLeave);
	mm.addAttribute("wpl", withoutpaidLeave);

	return new ModelAndView("leavesearch", mm);
    }

}
