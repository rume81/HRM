package com.webhawks.hr.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HMAttendance;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HSalarySheet;
import com.webhawks.Hawks_model.HTeam;
import com.webhawks.hr.model.UserSession;
import com.webhawks.hr.services.interfaces.IWhrService;

@Controller
public class PaySlipController extends BaseController implements ApplicationContextAware {

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
    
    
    @RequestMapping(value = "/payslip", method = RequestMethod.GET)
    public ModelAndView noticeboard(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /payslip");
	boolean validSession = getSessionService().isSessionValid();
	if (!validSession) {
	    return new ModelAndView("redirect:/");
	}
	
	HEmployee currentuser = whrService.getEmployeeById(String.valueOf(getUser().getEmp_id()), true);
	HEmployee selecteduser = whrService.getEmployeeById(String.valueOf(getSelectedUser().getEmp_id()), true);
		
	HAttendance firstAtt = whrService.getFirstAttendance();
	Date dtnow = new Date();
	String firstdate = firstAtt.getAtt_date();
	List<String> monyear = new ArrayList<String>();
	int startmon = getMonth(firstdate);
	int startyear = getYear(firstdate);

	int curmon = Integer.parseInt(CustomDateFormate("MM").format(dtnow));
	int curyear = Integer.parseInt(CustomDateFormate("yyyy").format(dtnow));
	
	if((curmon-1) < 1){
	    curmon = 12;
	    curyear = curyear - 1;
	} else {
	    curmon = curmon - 1;
	}
	
	String selectedValue = getTextMonth(curmon) + ", " + curyear;
	
	int tempcurmon = curmon + 1;
	int tempcuryear = curyear;
	if (tempcurmon > 12) {
	    tempcurmon = 1;
	    tempcuryear = tempcuryear + 1;
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

	    if ((startmon == tempcurmon) && (startyear == tempcuryear)) {
		loop = false;
	    }

	} while (loop);
	
	HSalaryDetails activeSal = whrService.getActiveSalaryDetails(getSelectedUser().getEmp_id());
	
	HPaySlip slip = whrService.getPayslip(curmon, curyear, getSelectedUser().getEmp_id());
	/*if(slip.getSlipstate()==0){
	    slip = new HPaySlip();
	}*/
		
	ModelMap mm = new ModelMap();
	mm.addAttribute("validSession", validSession);
	mm.addAttribute("monyear", monyear);
	mm.addAttribute("selectedVal", selectedValue);
	mm.addAttribute("emp", selecteduser);
	mm.addAttribute("user", currentuser);
	mm.addAttribute("activeSal", activeSal);
	mm.addAttribute("slip", slip);
	mm.addAttribute("curmon", curmon);
	mm.addAttribute("curyear", curyear);

	
	return new ModelAndView("payslip", mm);
    }
    
    @RequestMapping(value = "/downlaodpayslip", method = RequestMethod.GET)
    public ModelAndView downlaodpayslip(HttpServletRequest request, HttpServletResponse response) {
	logger.info("****************************************** url= /downlaodpayslip");
	boolean validSession = getSessionService().isSessionValid();
	ModelMap mm = new ModelMap();

	if (!validSession) {
	    return new ModelAndView("redirect:/");
	} else {
	    HEmployee emp = getUser();
	    HEmployee selecteduser = getSelectedUser();
	    
	    Map<String, String[]> parameterMap = request.getParameterMap();
	    HPaySlip ps = new HPaySlip();
	
	    int emp_id = Integer.parseInt(parameterMap.get("dow_emp_id")[0]);	
	    int month = Integer.parseInt(parameterMap.get("dow_month")[0]);	
	    int year = Integer.parseInt(parameterMap.get("dow_year")[0]);
	    
	    if ((!emp.getUsertype().equalsIgnoreCase("Talent Manager")) && (selecteduser.getEmp_id()!= emp_id)) {
		return new ModelAndView("redirect:/");
	    }	    
	    
	    HPaySlip slip = whrService.getPayslip(month, year, emp_id);
	    // return excel view
	    return new ModelAndView("PdfPaySlipView", "slip", slip);
	}

    }
    
    @RequestMapping(value = "/downloadsalarysheet", method = RequestMethod.GET)
    public ModelAndView downloadsalarysheet(HttpServletRequest request, HttpServletResponse response) {
	logger.info("****************************************** url= /downloadsalarysheet");
	boolean validSession = getSessionService().isSessionValid();
	ModelMap mm = new ModelMap();

	if (!validSession) {
	    return new ModelAndView("redirect:/");
	} else {
	    HEmployee emp = getUser();
	    if (!emp.getUsertype().equalsIgnoreCase("Talent Manager")) {
		return new ModelAndView("redirect:/");
	    }
	    Map<String, String[]> parameterMap = request.getParameterMap();		
		
	    String action = parameterMap.get("dtype")[0];	
	    int month = Integer.parseInt(parameterMap.get("salshet_month")[0]);	
	    int year = Integer.parseInt(parameterMap.get("salshet_year")[0]);
	    
	    List<HSalarySheet> slips = whrService.getSalarySheet(month, year);
	    // return excel view
	    if (action.equalsIgnoreCase("pdf")) {
		return new ModelAndView("PdfPaySlipView", "slip", slips);
	    } else {
		return new ModelAndView("SalarySheetExcel", "slip", slips);
	    }
	}

    }
    
    // ==================================================POST============================================================
    /** Notice Add post */
    @RequestMapping(value = "/salarybrackdown", method = RequestMethod.POST)
    public ModelAndView salarybrackdown(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /salarybrackdown");
	Map<String, String[]> parameterMap = request.getParameterMap();
	HSalaryDetails salary = new HSalaryDetails();
	
	String salid = parameterMap.get("salid")[0];
	String emp_id = parameterMap.get("salemp_id")[0];
	String finid = parameterMap.get("salfinid")[0];
	String gross = parameterMap.get("salgross")[0];
	String basic = parameterMap.get("salbasic")[0];
	String housing = parameterMap.get("salhousing")[0];
	String medical = parameterMap.get("salmedical")[0];
	String transport = parameterMap.get("saltransport")[0];
	String lunch = parameterMap.get("sallunch")[0];
	String tax = parameterMap.get("saltax")[0];
	String appdate = parameterMap.get("salappdate")[0];
	
	ModelMap mm = new ModelMap();
	
	salary.setSalid(Integer.parseInt(salid));
	salary.setEmp_id(Integer.parseInt(emp_id));
	salary.setEmp_finance_id(finid);
	salary.setGross(Double.parseDouble(gross));
	salary.setBasic(Double.parseDouble(basic));
	salary.setHousing(Double.parseDouble(housing));
	salary.setMedical(Double.parseDouble(medical));
	salary.setTransport(Double.parseDouble(transport));
	salary.setLunch(Double.parseDouble(lunch));
	salary.setTax(Double.parseDouble(tax));
	salary.setApplieddate(convertDateFormate(appdate));
	salary.setStatus(true);
	salary.setDeleted(false);
	salary.setModId(getUser().getAvator());
	
	boolean res = whrService.addSalaryDetails(salary);

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    @RequestMapping(value = "/editpayslip", method = RequestMethod.POST)
    public ModelAndView editpayslip(HttpServletRequest request)
	    throws Exception {
	logger.info("****************************************** url= /editpayslip");
	Map<String, String[]> parameterMap = request.getParameterMap();
	HPaySlip ps = new HPaySlip();
	
	
	int emp_id = Integer.parseInt(parameterMap.get("emp_id")[0]);	
	int month = Integer.parseInt(parameterMap.get("month")[0]);	
	int year = Integer.parseInt(parameterMap.get("year")[0]);
	
	ps = whrService.getPayslip(month, year, emp_id);
	
	String swdm = parameterMap.get("swdm")[0];
	ps.setSwdm(Integer.parseInt(swdm));
	String awdm = parameterMap.get("awdm")[0];
	ps.setAwdm(Integer.parseInt(awdm));
	String appleave = parameterMap.get("appleave")[0];
	ps.setAppleave(Integer.parseInt(appleave));
	String absent = parameterMap.get("absent")[0];
	ps.setAbsent(Integer.parseInt(absent));
	String awhm = parameterMap.get("awhm")[0];
	ps.setAwhm(Float.parseFloat(awhm));
	String oaom = parameterMap.get("oaom")[0];
	ps.setOaom(Float.parseFloat(oaom));
	String gross = parameterMap.get("gross")[0];
	ps.setGross(Double.parseDouble(gross));
	String basic = parameterMap.get("basic")[0];
	ps.setBasic(Double.parseDouble(basic));
	String dbpr = parameterMap.get("dbpr")[0];
	ps.setDbpr(Double.parseDouble(dbpr));
	String hbpr = parameterMap.get("hbpr")[0];
	ps.setHbpr(Double.parseDouble(hbpr));
	String nodwm = parameterMap.get("nodwm")[0];
	ps.setNodwm(Float.parseFloat(nodwm));
	String nohwm = parameterMap.get("nohwm")[0];
	ps.setNohwm(Float.parseFloat(nohwm));
	String hoo = parameterMap.get("hoo")[0];
	ps.setHoo(Float.parseFloat(hoo));
	String ohih = parameterMap.get("ohih")[0];
	ps.setOhih(Float.parseFloat(ohih));
	String htns = parameterMap.get("htns")[0];
	ps.setHtns(Float.parseFloat(htns));
	String tpl = parameterMap.get("tpl")[0];
	ps.setTpl(Integer.parseInt(tpl));
	String nots = parameterMap.get("nots")[0];
	ps.setNots(Integer.parseInt(nots));
	String bstbp = parameterMap.get("bstbp")[0];
	ps.setBstbp(Double.parseDouble(bstbp));
	String soow = parameterMap.get("soow")[0];
	ps.setSoow(Double.parseDouble(soow));
	String soho = parameterMap.get("soho")[0];
	ps.setSoow(Double.parseDouble(soho));
	String pftnh = parameterMap.get("pftnh")[0];
	ps.setPftnh(Double.parseDouble(pftnh));
	String sfapl = parameterMap.get("sfapl")[0];
	ps.setSfapl(Double.parseDouble(sfapl));
	String bta = parameterMap.get("bta")[0];
	ps.setBta(Double.parseDouble(bta));
	String ha = parameterMap.get("ha")[0];
	ps.setHa(Double.parseDouble(ha));
	String ma = parameterMap.get("ma")[0];
	ps.setMa(Double.parseDouble(ma));
	String ta = parameterMap.get("ta")[0];
	ps.setTa(Double.parseDouble(ta));
	String bonus = parameterMap.get("bonus")[0];
	ps.setBonus(Double.parseDouble(bonus));
	String hma = parameterMap.get("hma")[0];
	ps.setHma(Double.parseDouble(hma));
	String cfhi = parameterMap.get("cfhi")[0];
	ps.setCfhi(Double.parseDouble(cfhi));
	String cfhoi = parameterMap.get("cfhoi")[0];
	ps.setCfhoi(Double.parseDouble(cfhoi));
	String lwp = parameterMap.get("lwp")[0];
	ps.setLwp(Double.parseDouble(lwp));
	String lunch = parameterMap.get("lunch")[0];
	ps.setLunch(Double.parseDouble(lunch));
	String tax = parameterMap.get("tax")[0];
	ps.setTax(Double.parseDouble(tax));
	String arrears = parameterMap.get("arrears")[0];
	ps.setArrears(Double.parseDouble(arrears));
	ps.setSlipstate(1);
	ps.setDeleted(false);
	ps.setModId(getUser().getAvator());
	
	ModelMap mm = new ModelMap();
		
	boolean res = whrService.updatePaySlip(ps);

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    @RequestMapping(value = "/deletepayslip", method = RequestMethod.POST)
    public ModelAndView deletepayslip(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /deletepayslip");
	UserSession session = getSessionService().getUserSession();

	String requestUri = getStringFromHttpRequest(request);

	logger.info("requestUri=" + requestUri);

	String[] requestUriSplit = requestUri.split("~");

	if (requestUriSplit.length < 3) {
	    logger.warn("Expecting atleast 3 arguments but received " + requestUriSplit.length);
	    return null;
	}
	
	int emp_id = Integer.parseInt(requestUriSplit[0]);
	int month = Integer.parseInt(requestUriSplit[1]);
	int year = Integer.parseInt(requestUriSplit[2]);
	
	HPaySlip ps = new HPaySlip();
		
	ps = whrService.getPayslip(month, year, emp_id);
	
	boolean res=false;
	if(ps.getEmp_id() == emp_id && ps.getMonth()== month && ps.getYear()==year){
	    ps.setDeleted(true);
	    ps.setModId(getUser().getAvator());
	    res = whrService.deletePaySlip(ps);
	}
	
	ModelMap mm = new ModelMap();

	if (res) {
	    mm.addAttribute("msg", "OK");

	} else
	    mm.addAttribute("msg", "ERROR");

	return new ModelAndView("result", mm);
    }
    
    
    @RequestMapping(value = "/searchpayslip", method = RequestMethod.POST)
    public ModelAndView searchpayslip(HttpServletRequest request) throws Exception {
	logger.info("****************************************** url= /searchpayslip");
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
	
	HEmployee currentuser = whrService.getEmployeeById(String.valueOf(getUser().getEmp_id()), true);
	HEmployee selecteduser = whrService.getEmployeeById(String.valueOf(emp_id), true);
	HPaySlip slip = whrService.getPayslip(curmon, curyear, emp_id);
		
	ModelMap mm = new ModelMap();
	
	mm.addAttribute("user", currentuser);
	mm.addAttribute("slip", slip);
	mm.addAttribute("emp", selecteduser);
	mm.addAttribute("curmon", curmon);
	mm.addAttribute("curyear", curyear);

	return new ModelAndView("payslipsearch", mm);
    }
}
