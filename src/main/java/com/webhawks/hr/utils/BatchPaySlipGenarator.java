package com.webhawks.hr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HCardAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HEmployeeResignation;
import com.webhawks.Hawks_model.HEmployeeparam;
import com.webhawks.Hawks_model.HLunch;
import com.webhawks.Hawks_model.HMonthlyStatus;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HWorkInfo;
import com.webhawks.hr.controller.PaySlipController;
import com.webhawks.hr.services.interfaces.IWhrService;

public class BatchPaySlipGenarator extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(BatchPaySlipGenarator.class);
    private static final String APPLICATION_CONTEXT_KEY = "applicationContext";
    private IWhrService whrService;

    /**
     * Setter called after the ExampleJob is instantiated with the value from
     * the JobDetailBean (5)
     */
    private ApplicationContext getApplicationContext(JobExecutionContext context) throws Exception {
	ApplicationContext appCtx = null;
	appCtx = (ApplicationContext) context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
	if (appCtx == null) {
	    throw new JobExecutionException("No application context available in scheduler context for key \""
		    + APPLICATION_CONTEXT_KEY + "\"");
	}
	return appCtx;
    }

    protected SimpleDateFormat DateFormateforDB() {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	return df;
    }

    protected SimpleDateFormat DateFormateforView() {
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	return df;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	logger.info("****************************************** BatchPaySlip Run ******************************************");
	Date today = new Date();
	SimpleDateFormat ye = new SimpleDateFormat("yyyy");
	SimpleDateFormat mo = new SimpleDateFormat("MM");

	int curmon = Integer.parseInt(mo.format(today));
	int curyear = Integer.parseInt(ye.format(today));

	ApplicationContext appCtx = null;
	try {
	    appCtx = getApplicationContext(context);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	whrService = (IWhrService) appCtx.getBean("whrService");

	if ((curmon - 1) < 1) {
	    curmon = 12;
	    curyear = curyear - 1;
	} else {
	    curmon = curmon - 1;
	}

	List<HPaySlip> newslip = new ArrayList<HPaySlip>();
	List<HEmployee> allemp = whrService.getAllEmployee(false);

	for (HEmployee emp : allemp) {
	    boolean pslipFound = whrService.checkPayslip(curmon, curyear,emp.getEmp_id());
	    if(pslipFound){
		continue;
	    }
	    HSalaryDetails empsal = whrService.getActiveSalaryDetails(emp.getEmp_id());
	    if (null == empsal) {
		continue;
	    }
	    HMonthlyStatus empstate = whrService.getMonthlyStatus(curmon, curyear, emp.getEmp_id());
	    if(null == empstate){
		continue;
	    }
	    logger.info("****************************************** BatchPaySlip Execute Emp_id="+emp.getEmp_id()+"******************************************");
	    boolean joinMonth = false;
	    boolean exitMonth = false;
	    String resigDate = "";
	    
	    HEmployeeResignation resig = whrService.getEmployeeResignationStatus(curmon, curyear, emp.getEmp_id());

	    if (resig != null) {
		if (resig.getLastofficedate() != null) {
		    resigDate = DateFormateforView().format(resig.getLastofficedate());
		}
	    }

	    HPaySlip ps = new HPaySlip();
	    ps.setEmp_id(emp.getEmp_id());
	    ps.setEmp_name(emp.getEmployee_name());
	    ps.setEmp_finance_id(empsal.getEmp_finance_id());
	    ps.setMonth(curmon);
	    ps.setYear(curyear);
	    ps.setSwdm(empstate.getTotal_working_day());
	    ps.setAwdm(empstate.getPresent());
	    ps.setAppleave(empstate.getLeave());
	    ps.setAbsent(empstate.getAbsent());

	    float awhm = 0;
	    if (!empstate.getAbsulateworkinghours().equals("-")) {
		String strawhm = empstate.getAbsulateworkinghours();
		String val[] = strawhm.split(":");
		if (!val[0].equals(""))
		    awhm = (Float.parseFloat(val[0]) * 60) + Float.parseFloat(val[1]);
		awhm = awhm / 60;
	    }
	    ps.setAwhm(awhm);

	    float overtime = 0;
	    if (!empstate.getOvertimehours().equals("-")) {
		String strovertime = empstate.getOvertimehours();
		String val[] = strovertime.split(":");
		if (!val[0].equals(""))
		    overtime = (Float.parseFloat(val[0]) * 60) + Float.parseFloat(val[1]);

		overtime = overtime / 60;
	    }

	    float holidayovertime = 0;
	    if (!empstate.getHolidayovertime().equals("-")) {
		String strholidayovertime = empstate.getHolidayovertime();
		String val[] = strholidayovertime.split(":");
		if (!val[0].equals(""))
		    holidayovertime = (Float.parseFloat(val[0]) * 60) + Float.parseFloat(val[1]);

		holidayovertime = holidayovertime / 60;
	    }

	    ps.setOaom(overtime);

	    ps.setGross(empsal.getGross());
	    double basic = empsal.getBasic();

	    ps.setBasic(basic);
	    if (basic > 0) {
		ps.setDbpr(basic / 30);
		ps.setHbpr((basic / 30) / 8);
	    } else {
		ps.setDbpr(0.0d);
		ps.setHbpr(0.0d);
	    }

	    float nod = empstate.getPresent() + empstate.getLeave();
	    ps.setNodwm(nod);
	    ps.setNohwm((empstate.getPresent() * 8) + overtime);
	    ps.setHoo(overtime);
	    ps.setOhih(holidayovertime);

	    // Check if Joining Month
	    String joindate = emp.getJoin_date();
	    int joindt = 0;
	    if (!joindate.equals("")) {
		String splval[] = joindate.split("-");
		if (splval[1].equals(String.valueOf(curmon)) && splval[2].equals(String.valueOf(curyear))) {
		    joinMonth = true;
		    joindt = Integer.parseInt(splval[0]);
		}
	    }

	    if (joinMonth) {
		int salarydays = (30 - joindt) + 1;
		if (empstate.getAbsent() > 0) {
		    int absent = empstate.getAbsent();
		    double absentdeduct = absent * (basic / 30);
		    double bstbp = (salarydays * (basic / 30));
		    ps.setBstbp(bstbp - absentdeduct);
		} else {
		    double bstbp = (salarydays * (basic / 30));
		    ps.setBstbp(bstbp);
		}

	    } else if (exitMonth) {
		// Check if Exit Month
		String espldt[] = resigDate.split("-");
		int salarydays = 0;
		if ((espldt[0] != null) && (!espldt[0].equals(""))) {
		    salarydays = Integer.parseInt(espldt[0]);
		}
		if (empstate.getAbsent() > 0) {
		    int absent = empstate.getAbsent();
		    double absentdeduct = absent * (basic / 30);
		    double bstbp = (salarydays * (basic / 30));
		    ps.setBstbp(bstbp - absentdeduct);
		} else {
		    double bstbp = (salarydays * (basic / 30));
		    ps.setBstbp(bstbp);
		}
	    } else {
		if (empstate.getAbsent() > 0) {
		    int absent = empstate.getAbsent();
		    double absentdeduct = absent * (basic / 30);
		    ps.setBstbp(basic - absentdeduct);
		} else {
		    ps.setBstbp(basic);
		}
	    }

	    ps.setHa(empsal.getHousing());
	    ps.setMa(empsal.getMedical());
	    ps.setTa(empsal.getTransport());
	    ps.setTax(empsal.getTax());
	    ps.setLunch(empsal.getLunch());
	    
	    if (empstate.getLwp() > 0) {
		int lwp = empstate.getLwp();
		double lwpdeduct = lwp * (basic/30);
		ps.setLwp(lwpdeduct);
	    }

	    // Bonus Calculation Remaining
	    double gross = empsal.getGross();
	    if (curmon == 6) {
		boolean fo = whrService.isEmployeeJoinCurrentPeriod(curyear + "-01-01", curyear + "-06-30",
			emp.getEmp_id());
		if (fo) {
		    String start_date = "01-01-" + curyear;
		    String join_date = emp.getJoin_date();
		    long dif = getDateDifferenc(start_date, join_date);
		    if (dif > 90 && dif <= 180) {
			double bonuseSal = (gross / 2);
			double bonus = (bonuseSal / 180) * dif;
			// arrer bonus
			HPaySlip payslip = whrService.getPayslip(curmon, (curyear - 1), emp.getEmp_id());
			if (payslip.getArrear_bonus() > 0) {
			    bonus = bonus + payslip.getArrear_bonus();
			}
			// arrer bonus
			ps.setBonus(bonus);
		    } else if (dif > 180) {
			ps.setBonus(gross / 2);
		    } else {
			double bonuseSal = (gross / 2);
			double bonus = (bonuseSal / 180) * dif;
			ps.setArrear_bonus(bonus);
			ps.setBonus(0d);
		    }
		} else {
		    ps.setBonus(gross / 2);
		}
	    } else if (curmon == 12) {
		boolean fo = whrService.isEmployeeJoinCurrentPeriod(curyear + "-07-01", curyear + "-12-31",
			emp.getEmp_id());
		if (fo) {
		    String start_date = "01-07-" + curyear;
		    String join_date = emp.getJoin_date();
		    long dif = getDateDifferenc(start_date, join_date);
		    if (dif > 90 && dif <= 180) {
			double bonuseSal = (gross / 2);
			double bonus = (bonuseSal / 180) * dif;
			// arrer bonus
			HPaySlip payslip = whrService.getPayslip(curmon, (curyear - 1), emp.getEmp_id());
			if (payslip.getArrear_bonus() > 0) {
			    bonus = bonus + payslip.getArrear_bonus();
			}
			// arrer bonus
			ps.setBonus(bonus);
		    } else if (dif > 180) {
			ps.setBonus(gross / 2);
		    } else {
			double bonuseSal = (gross / 2);
			double bonus = (bonuseSal / 180) * dif;
			ps.setArrear_bonus(bonus);
			ps.setBonus(0d);
		    }
		} else {
		    ps.setBonus(gross / 2);
		}
	    }
	    ps.setSlipstate(0);
	    ps.setDeleted(false);
	    ps.setModId("system");

	    newslip.add(ps);
	}

	// Save payslip
	whrService.insertPaySlip(newslip);
	
	logger.info("****************************************** BatchPaySlip End ******************************************");
    }

    public long getDateDifferenc(String dateStart, String dateStop) {

	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	Date d1 = null;
	Date d2 = null;
	try {
	    d1 = format.parse(dateStart);
	    d2 = format.parse(dateStop);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	// Get msec from each, and subtract.
	long diff = d2.getTime() - d1.getTime();
	long diffdays = diff / (24 * 60 * 60 * 1000);

	return diffdays;
    }
}
