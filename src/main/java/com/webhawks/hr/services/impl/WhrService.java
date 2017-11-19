package com.webhawks.hr.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HLeaveApprover;
import com.webhawks.Hawks_model.HLeaveQuota;
import com.webhawks.Hawks_model.HWorkInfo;
import com.webhawks.hr.dao.interfaces.IWhrDAO;
import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HEmployeeResignation;
import com.webhawks.Hawks_model.HMAttendance;
import com.webhawks.Hawks_model.HMail;
import com.webhawks.Hawks_model.HMonthlyStatus;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HPolicy;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HSalarySheet;
import com.webhawks.Hawks_model.HSupportingData;
import com.webhawks.Hawks_model.HSysProperty;
import com.webhawks.Hawks_model.HTeam;
import com.webhawks.Hawks_model.HTypesOfLeave;
import com.webhawks.hr.services.interfaces.IWhrService;

public class WhrService implements IWhrService {
    private IWhrDAO whrDao;

    public void setWhrDao(IWhrDAO whrDao) {
	this.whrDao = whrDao;
    }

    public int endDateofMon(int mon, int year) {
	int day = 0;
	if ((mon == 1) || (mon == 3) || (mon == 5) || (mon == 7) || (mon == 8) || (mon == 10) || (mon == 12))
	    day = 31;
	else if ((mon == 4) || (mon == 6) || (mon == 9) || (mon == 11))
	    day = 30;
	else if ((mon == 2) && (LeapYear(year)))
	    day = 29;
	else
	    day = 28;

	return day;
    }

    private boolean LeapYear(int year) {
	boolean leapyear = false;
	if ((year % 4 == 0) && year % 100 != 0) {
	    leapyear = true;
	} else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0)) {
	    leapyear = true;
	} else {
	    leapyear = false;
	}

	return leapyear;
    }

    private String changeTimeFormate(String input) {
	String output = "";
	try {
	    DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
	    DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
	    output = outputFormat.format(inputFormat.parse(input));
	} catch (ParseException e1) {
	    e1.printStackTrace();
	}
	return output;
    }

    private String getOfficeStart(float st) {
	String sttime = "";
	if (st == 8)
	    sttime = "08:00:00";
	else if (st == 8.5)
	    sttime = "08:30:00";
	else if (st == 9)
	    sttime = "09:00:00";
	else if (st == 9.5)
	    sttime = "09:30:00";
	else if (st == 10)
	    sttime = "10:00:00";
	else if (st == 10.5)
	    sttime = "10:30:00";
	else if (st == 11)
	    sttime = "11:00:00";
	else if (st == 11.5)
	    sttime = "11:30:00";
	else if (st == 12)
	    sttime = "12:00:00";
	else if (st == 12.5)
	    sttime = "12:30:00";
	else if (st == 13)
	    sttime = "13:00:00";
	else if (st == 13.5)
	    sttime = "13:30:00";
	else if (st == 14)
	    sttime = "14:00:00";
	else if (st == 14.5)
	    sttime = "14:30:00";
	else if (st == 15)
	    sttime = "15:00:00";
	else if (st == 15.5)
	    sttime = "15:30:00";
	else if (st == 16)
	    sttime = "16:00:00";
	else if (st == 16.5)
	    sttime = "16:30:00";
	else if (st == 17)
	    sttime = "17:00:00";
	else if (st == 17.5)
	    sttime = "17:30:00";
	else if (st == 18)
	    sttime = "18:00:00";
	else if (st == 18.5)
	    sttime = "18:30:00";
	else if (st == 19)
	    sttime = "19:00:00";
	else if (st == 19.5)
	    sttime = "19:30:00";
	else if (st == 20)
	    sttime = "20:00:00";
	else if (st == 20.5)
	    sttime = "20:30:00";
	else if (st == 21)
	    sttime = "21:00:00";
	else if (st == 21.5)
	    sttime = "21:30:00";
	else if (st == 22)
	    sttime = "22:00:00";
	else if (st == 22.5)
	    sttime = "22:30:00";

	return sttime;

    }
    
    public List<Date> getDaysBetweenDates(Date startdate, Date enddate)
    {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        Calendar calendar1 = new GregorianCalendar();
        calendar.setTime(startdate);
        calendar1.setTime(enddate);
        calendar1.add(Calendar.DATE, 1);
        Date enddt = calendar1.getTime();
        while (calendar.getTime().before(enddt))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    
    public List<HHoliday> getWeekEndsBetweenDates(String dt1, String dt2)
    {
        List<HHoliday> dates = new ArrayList<HHoliday>();
        Calendar calendar = new GregorianCalendar();
        Calendar calendar1 = new GregorianCalendar();
        
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        Date startdate = null;
	Date enddate = null;
	try {
	    startdate = ft.parse(dt1);
	    enddate = ft.parse(dt2);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
        
        calendar.setTime(startdate);
        calendar1.setTime(enddate);
        calendar1.add(Calendar.DATE, 1);
        Date enddt = calendar1.getTime();
        while (calendar.getTime().before(enddt))
        {
            Date result = calendar.getTime();
            SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
            SimpleDateFormat viewft = new SimpleDateFormat("yyyy-MM-dd");
            String weekDay = "";
            if (result != null){
        	weekDay = format2.format(result);
        	if(weekDay.equalsIgnoreCase("Saturday") || weekDay.equalsIgnoreCase("Sunday")){
        	    String cdt = viewft.format(result);
        	    HHoliday hol = new HHoliday();
        	    hol.setHoliday_desc(weekDay);
        	    hol.setHoliday_year(result.getYear());
        	    hol.setDate_from(cdt);
        	    hol.setDate_to(cdt);
        	    hol.setDeleted(false);
        	    hol.setModId("");
        	    hol.setId(0);
        	            	    
        	    dates.add(hol);
        	}
            }
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }
    
    public String convertDateFormate(String dt) {// 25-01-2016 25 01 2016 output// 2016-01-25
        String formatedDate = "";
        if (dt.equals(""))
        return "1970-01-01";
        String[] splitdt = dt.split("-");
        formatedDate = splitdt[2] + "-" + splitdt[1] + "-" + splitdt[0];
        
        return formatedDate;
        
    }

    @Override
    public HEmployee getUserValidation(HEmployee user) {
	return whrDao.getUserValidation(user);
    }

    @Override
    public HEmployee getEmployeeById(String empId, boolean isValue) {
	return whrDao.getEmployeeById(empId, isValue);
    }

    @Override
    public List<HEmployee> getUserByTeam(String team) {
	return whrDao.getUserByTeam(team);
    }

    @Override
    public List<HSupportingData> getDesignation() {
	return whrDao.getDesignation();
    }

    @Override
    public List<HSupportingData> getCompany() {
	return whrDao.getCompany();
    }

    @Override
    public List<HSupportingData> getEducation() {
	return whrDao.getEducation();
    }

    @Override
    public List<HSupportingData> getMaritialStatus() {
	return whrDao.getMaritialStatus();
    }

    @Override
    public List<HSupportingData> getSign() {
	return whrDao.getSign();
    }

    @Override
    public List<HSupportingData> getDepartment() {
	return whrDao.getDepartment();
    }

    @Override
    public List<HSupportingData> getBlood() {
	return whrDao.getBlood();
    }

    @Override
    public List<HSupportingData> getUserType() {
	return whrDao.getUserType();
    }

    @Override
    public boolean registerEmployee(HEmployee obj) {
	return whrDao.registerEmployee(obj);
    }

    @Override
    public List<HTeam> getAllTeam(boolean isDeleted) {
	return whrDao.getAllTeam(isDeleted);
    }

    @Override
    public boolean editEmployee(HEmployee obj, String criteria) {
	return whrDao.editEmployee(obj, criteria);
    }

    @Override
    public List<HEmployee> getAllEmployee(boolean isDeleted) {
	return whrDao.getAllEmployee(isDeleted);
    }

    @Override
    public boolean addNotice(HNotice obj) {
	return whrDao.addNotice(obj);
    }

    @Override
    public List<HNotice> getNoticeByUser(int empid, boolean datecheck) {
	return whrDao.getNoticeByUser(empid, datecheck);
    }

    @Override
    public HAttendance getFirstAttendance() {
	return whrDao.getFirstAttendance();
    }

    @Override
    public List<HAttendance> getAttendance(String sdate, String edate, int emp_id) {
	return whrDao.getAttendance(sdate, edate, emp_id);
    }

    @Override
    public List<HMAttendance> getAttendanceForMonth(int mon, int year, int emp_id, boolean isCurdatechk) {
	List<HMAttendance> monAtt = new ArrayList<HMAttendance>();
	List<HMAttendance> blankmonAtt = new ArrayList<HMAttendance>();

	HEmployee curEmp = whrDao.getEmployeeById(String.valueOf(emp_id), true);
	String join_date = curEmp.getJoin_date();
	int startDate = 1;
	if ((join_date.equals("")) || (join_date == null)) {
	    join_date = "2016-05-18";
	}
	if ((!join_date.equals("")) && (join_date != null)) {
	    String[] splitjoindt = join_date.split("-");
	    int join_mon = 0, join_year = 0;
	    if (!splitjoindt[1].equals(""))
		join_mon = Integer.parseInt(splitjoindt[1]);
	    if (!splitjoindt[2].equals(""))
		join_year = Integer.parseInt(splitjoindt[2]);

	    if ((mon == join_mon) && (year == join_year)) {
		startDate = Integer.parseInt(splitjoindt[0]);
	    }
	}

	int endDate = endDateofMon(mon, year);
	Date today = new Date();
	SimpleDateFormat dfd = new SimpleDateFormat("dd");
	int curdd = Integer.parseInt(dfd.format(today));
	SimpleDateFormat dfm = new SimpleDateFormat("MM");
	int curmm = Integer.parseInt(dfm.format(today));
	SimpleDateFormat dfy = new SimpleDateFormat("yyyy");
	int curyy = Integer.parseInt(dfy.format(today));

	curdd = curdd - 1;
	if (curdd == 0)
	    curdd = 1;
	if ((!isCurdatechk) && (mon == curmm) && (year == curyy) && (endDate > curdd)) {
	    endDate = curdd;
	}
	boolean dataFound = false;
	for (int dd = startDate; dd <= endDate; dd++) {

	    String curDate = year + "-" + mon + "-" + dd;
	    // System.out.println(curDate);
	    String currentDate = dd + "-" + mon + "-" + year;
	    SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	    Date dt1 = null;
	    try {
		dt1 = format1.parse(currentDate);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
	    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	    String weekDay = "";
	    if (dt1 != null)
		weekDay = format2.format(dt1);

	    // System.out.println(weekDay);

	    HAttendance startAtt = whrDao.getDailyAtt(curDate, 0, emp_id);
	    HAttendance endAtt = whrDao.getDailyAtt(curDate, 1, emp_id);

	    String dayforshow = dd + "-" + mon + "-" + year;
	    HMAttendance dailyAtt = new HMAttendance();
	    dailyAtt.setAtt_date(dayforshow);
	    dailyAtt.setAtt_weekday(weekDay);
	    if (null != startAtt.getAtt_time())
		dailyAtt.setAtt_in(changeTimeFormate(startAtt.getAtt_time()));
	    else
		dailyAtt.setAtt_in("");

	    if (null != endAtt.getAtt_time())
		dailyAtt.setAtt_out(changeTimeFormate(endAtt.getAtt_time()));
	    else
		dailyAtt.setAtt_out("");

	    dailyAtt.setEmp_id(emp_id);
	    dailyAtt.setEmployee_name(curEmp.getEmployee_name());

	    HWorkInfo info = whrDao.getWorkInfoById(emp_id, curDate);
	    // satus need to write code for Late
	    if (info.getOffice_start() != null && startAtt.getAtt_time() != null) {
		float officeStart = info.getOffice_start();
		// String startTime = ((int) officeStart) + ":"+"00:00";
		String startTime = getOfficeStart(officeStart);
		String attTime = startAtt.getAtt_time();

		Date stdate = null;
		Date atdate = null;
		try {
		    atdate = formatter.parse(attTime);
		    stdate = formatter.parse(startTime);
		} catch (ParseException e) {
		    e.printStackTrace();
		}

		long diffInMillis = stdate.getTime() - atdate.getTime();
		if (diffInMillis < 0) {
		    long sec = (Math.abs(diffInMillis) / 1000);
		    long min = 0;
		    long hour = 0;
		    if (sec > 60) {
			min = sec / 60;
			sec = sec % 60;
		    }
		    if (min > 60) {
			hour = min / 60;
			min = min % 60;
		    }
		    String stMsg = "";
		    boolean fo = false;
		    if (hour > 0) {
			stMsg = stMsg + hour + " Hour ";
			fo = true;
		    }
		    if (min > 0) {
			if (min == 1)
			    stMsg = stMsg + min + " Minute ";
			else
			    stMsg = stMsg + min + " Minutes ";
			fo = true;
		    }
		    if (sec > 0) {
			// stMsg=stMsg+sec+" Second ";
			// fo = true;
		    }
		    if (fo) {
			stMsg = stMsg + "Late";
			dailyAtt.setLate(true);
		    }
		    dailyAtt.setStatus(stMsg);
		}
		dataFound = true;
	    }
	    // satus need to write code for Late
	    // weekday check
	    if (info.getWeekend() != null) {
		String weekends[] = info.getWeekend().split(",");
		for (String w : weekends) {
		    if (w.equals(weekDay)) {
			dailyAtt.setWeekend(true);

			dailyAtt.setStatus("WeekDay");
		    }
		}
	    }
	    // weekday check
	    // holiday check
	    List<HHoliday> holidayinfoList = whrDao.getHolidayListByDate(curDate);
	    for (HHoliday holidayinfo : holidayinfoList) {
		if (holidayinfo != null) {
		    if (null != holidayinfo.getId()) {
			dailyAtt.setHoliday(true);
			if (dailyAtt.getStatus() != null)
			    dailyAtt.setStatus(dailyAtt.getStatus() + " HoliDay");
			else
			    dailyAtt.setStatus("HoliDay");
			break;
		    }
		}
	    }
	    // holiday check
	    // Leave check
	    boolean lFo = false;
	    lFo = whrDao.getLeaveInfoByDate(curDate, emp_id, 0);
	    if (lFo) {

		List<HLeave> leaves = whrDao.getLeaveDataByDate(curDate, emp_id);
		for (HLeave leave : leaves) {
		    if ((!dailyAtt.isHoliday()) && (!dailyAtt.isWeekend())) {
			if (leave.getStatus() == 1) {
			    dailyAtt.setLeave(false);
			    dailyAtt.setLwp(false);
			    continue;
			}
			if (leave.getStatus() == 0) {
			    dailyAtt.setStatus("Applied For Leave");
			    dailyAtt.setLeave(true);
			    if (leave.getLeavetype().getId() == 3) {// lwp
				dailyAtt.setLwp(true);
			    } else {
				dailyAtt.setLwp(false);
			    }
			} else if (leave.getStatus() == 2) {
			    dailyAtt.setStatus("Leave");
			    dailyAtt.setLeave(true);
			    if (leave.getLeavetype().getId() == 3) {// lwp
				dailyAtt.setLwp(true);
			    } else {
				dailyAtt.setLwp(false);
			    }
			}
		    }
		}
	    }
	    // Leave check
	    // Absent check
	    if ((startAtt.getAtt_time() == null) && (dd <= endDate)) {
		if ((!dailyAtt.isHoliday()) && (!dailyAtt.isWeekend()) && (!dailyAtt.isLeave())) {
		    dailyAtt.setAbsent(true);
		    if (dailyAtt.getStatus() != null)
			dailyAtt.setStatus(dailyAtt.getStatus() + " Absent");
		    else
			dailyAtt.setStatus("Absent");
		}
	    }
	    // Absent check
	    monAtt.add(dailyAtt);
	}
	if (isCurdatechk)
	    return monAtt;
	else {
	    if (dataFound)
		return monAtt;
	    else
		return blankmonAtt;
	}
    }

    @Override
    public List<HTypesOfLeave> getLeaveType(boolean isDeleted) {
	return whrDao.getLeaveType(isDeleted);
    }

    @Override
    public List<HLeaveApprover> getLeaveApprovers(boolean isDeleted, int emp_id) {
	return whrDao.getLeaveApprovers(isDeleted, emp_id);
    }

    @Override
    public List<HLeaveQuota> getLeaveQuotas(int empId, String year) {
	return whrDao.getLeaveQuotas(empId, year);
    }

    @Override
    public List<HLeaveQuota> getQuotaYear() {
	return whrDao.getQuotaYear();
    }

    @Override
    public HTypesOfLeave getLeaveTypeById(Integer id) {
	return whrDao.getLeaveTypeById(id);
    }

    @Override
    public List<String> calculateLeaveDate(String fdate, String tdate, int emp_id, int reqFrom) {

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dbformatter = new SimpleDateFormat("yyyy-MM-dd");
	Date startDate = new Date();
	Date endDate = new Date();
	try {
	    startDate = formatter.parse(fdate);
	    endDate = formatter.parse(tdate);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	Calendar start = Calendar.getInstance();
	start.setTime(startDate);
	Calendar end = Calendar.getInstance();
	end.setTime(endDate);
	end.add(Calendar.DAY_OF_MONTH, 1);

	List<String> liveDay = new ArrayList<String>();
	for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

	    SimpleDateFormat format2 = new SimpleDateFormat("EEEE");

	    String weekDay = "";
	    if (date != null)
		weekDay = format2.format(date);

	    String curDate = formatter.format(date);
	    String curDateDb = dbformatter.format(date);
	    // weekday check
	    boolean wFo = false;
	    boolean hFo = false;
	    HWorkInfo info = whrDao.getWorkInfoById(emp_id, curDateDb);
	    if (info.getWeekend() != null) {
		String weekends[] = info.getWeekend().split(",");

		for (String w : weekends) {
		    if (w.equals(weekDay)) {
			wFo = true;
			break;
		    }
		}
	    }
	    // weekday check
	    // holiday check
	    /*
	     * HHoliday holidayinfo = wamsDao.getHolidayByDate(curDateDb);
	     * if(holidayinfo!=null) { if(null!=holidayinfo.getId()) { hFo =
	     * true; } }
	     */
	    List<HHoliday> holidayinfoList = whrDao.getHolidayListByDate(curDateDb);
	    for (HHoliday holidayinfo : holidayinfoList) {
		if (holidayinfo != null) {
		    if (null != holidayinfo.getId()) {
			hFo = true;
			break;
		    }
		}
	    }
	    // holiday check

	    // Leave check
	    if (reqFrom == 0) {
		boolean lFo = whrDao.getLeaveInfoByDate(curDateDb, emp_id, -1);
		if (lFo) {
		    liveDay = new ArrayList<String>();
		    return liveDay;
		}
	    }
	    // Leave check

	    if ((!wFo) && (!hFo)) {
		liveDay.add(curDate + "   " + weekDay);
		// System.out.println(date+" "+weekDay+" "+curDate);
	    }
	}

	return liveDay;
    }

    @Override
    public HLeaveQuota getLeaveQuota(int empId, String year, int type_id) {
	return whrDao.getLeaveQuota(empId, year, type_id);
    }

    @Override
    public Integer insertHLeaveInfo(HLeave leave) {
	return whrDao.insertHLeaveInfo(leave);
    }

    @Override
    public Integer sendMail(HMail mail) {
	return whrDao.sendMail(mail);
    }

    @Override
    public List<HLeave> getAllLeaveByEmpId(boolean isDeleted, int emp_id) {
	return whrDao.getAllLeaveByEmpId(isDeleted, emp_id);
    }
    
    @Override
    public List<HLeave> getLeaveInfoByMonth(int mon, int year, int emp_id, int status){
	List<HLeave> leavelist = new ArrayList<HLeave>();
	
	HEmployee curEmp = whrDao.getEmployeeById(String.valueOf(emp_id), true);
	String join_date = curEmp.getJoin_date();
	int startDate = 1;
	if ((join_date.equals("")) || (join_date == null)) {
	    join_date = "2016-05-18";
	}
	if ((!join_date.equals("")) && (join_date != null)) {
	    String[] splitjoindt = join_date.split("-");
	    int join_mon = 0, join_year = 0;
	    if (!splitjoindt[1].equals(""))
		join_mon = Integer.parseInt(splitjoindt[1]);
	    if (!splitjoindt[2].equals(""))
		join_year = Integer.parseInt(splitjoindt[2]);

	    if ((mon == join_mon) && (year == join_year)) {
		startDate = Integer.parseInt(splitjoindt[0]);
	    }
	}
	
	int endDate = endDateofMon(mon, year);
	SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
	for (int dd = startDate; dd <= endDate; dd++) {

	    String curDate = year + "-" + mon + "-" + dd;
	    String curVDate = dd + "-" + mon + "-" + year;
	    Date dt1 = null;
	    try {
		dt1 = format1.parse(curVDate);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    SimpleDateFormat format2 = new SimpleDateFormat("EEEE");
	    String weekDay = "";
	    if (dt1 != null)
		weekDay = format2.format(dt1);
	    
	    boolean lFo = whrDao.getLeaveInfoByDate(curDate, emp_id, 0);
	    if(lFo){
		List<HLeave> todaysleave =whrDao.getLeaveDataByDate(curDate, emp_id);
		todaysleave.get(0).setTdate(weekDay);
		todaysleave.get(0).setFdate(curVDate);
		leavelist.addAll(todaysleave);
	    }
	    
	}
	
	return leavelist;
    }

    @Override
    public boolean addSalaryDetails(HSalaryDetails salary) {
	return whrDao.addSalaryDetails(salary);
    }

    @Override
    public HSalaryDetails getActiveSalaryDetails(Integer emp_id) {
	return whrDao.getActiveSalaryDetails(emp_id);
    }
    
    @Override
    public List<HPaySlip> getPaySlipState(Integer mon, Integer year){
	return whrDao.getPaySlipState(mon, year);
    }
    
    @Override
    public List<HSalarySheet> getSalarySheet(Integer mon, Integer year){
	return whrDao.getSalarySheet(mon, year);
    }
    
    @Override
    public HMonthlyStatus getMonthlyStatus(int mon, int year, int emp_id){
	return whrDao.getMonthlyStatus(mon, year, emp_id);
    }

    @Override
    public HEmployeeResignation getEmployeeResignationStatus(int mon, int year, int emp_id) {
	String fromdt = year+"-"+mon+"-01";
	String todt = year+"-"+mon+"-"+endDateofMon(mon, year);
	return whrDao.getEmployeeResignationStatus(fromdt, todt, emp_id);
    }

    @Override
    public boolean isEmployeeJoinCurrentPeriod(String fromdt, String todt, int emp_id) {
	return whrDao.isEmployeeJoinCurrentPeriod(fromdt, todt, emp_id);
    }
    
    @Override
    public HPaySlip getPayslip(int mon, int year, int emp_id){
	return whrDao.getPayslip(mon, year, emp_id);
    }
    
    public boolean checkPayslip(int mon, int year, int emp_id){
	return whrDao.checkPayslip(mon, year, emp_id);
    }
    
    @Override
    public boolean insertPaySlip(List<HPaySlip> slip){
	return whrDao.insertPaySlip(slip);
    }

    @Override
    public boolean updatePaySlip(HPaySlip ps) {
	return whrDao.updatePaySlip(ps);
    }
    
    @Override
    public boolean deletePaySlip(HPaySlip ps) {
	return whrDao.updatePaySlip(ps);
    }
    
    @Override
    public List<HHoliday> getAllHoliday(boolean isDeleted,String dt){
	List<HHoliday> holiday = new ArrayList<HHoliday>();
	List<HHoliday> ho = whrDao.getAllHoliday(isDeleted,dt);
	SimpleDateFormat viewft = new SimpleDateFormat("yyyy-MM-dd");
	for(HHoliday h:ho){
	    if(h.getDate_from().equals(h.getDate_to())){
		String fromdt = convertDateFormate(h.getDate_from());
		h.setDate_from(fromdt);
		h.setDate_to(fromdt);
		holiday.add(h);
	    } else{
		String fd = h.getDate_from();
		String td = h.getDate_to();
		
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		Date dt1 = null;
		Date dt2 = null;
		try {
		    dt1 = ft.parse(fd);
		    dt2 = ft.parse(td);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		List<Date> lod = getDaysBetweenDates(dt1, dt2);
		
		for(Date d:lod){
		    String cdt = viewft.format(d);
		    HHoliday hol = new HHoliday();
		    hol.setHoliday_desc(h.getHoliday_desc());
		    hol.setHoliday_year(h.getHoliday_year());
		    hol.setDate_from(cdt);
		    hol.setDate_to(cdt);
		    hol.setDeleted(h.getDeleted());
		    hol.setModId(h.getModId());
		    hol.setId(h.getId());
		    holiday.add(hol);
		}
	    }
	}
	
	return holiday;
    }
    
    @Override
    public boolean addPolicy(HPolicy obj){
	return whrDao.addPolicy(obj);
    }
    
    @Override
    public List<HPolicy> getAllPolicy(boolean isDeleted){
	return whrDao.getAllPolicy(isDeleted);
    }
    
    @Override
    public HPolicy getPolicy(int id){
	return whrDao.getPolicy(id);
    }
    
    @Override
    public boolean deletePolicy(HPolicy p){
	return whrDao.deletePolicy(p);
    }
    
    @Override
    public HSysProperty getSysPropertyByName(String name){
	return whrDao.getSysPropertyByName(name);
    }
    
    @Override
    public int getMaxHolidayYear(){
    	return whrDao.getMaxHolidayYear();
    }
}
