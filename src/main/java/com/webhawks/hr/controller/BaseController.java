package com.webhawks.hr.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HUpload;
import com.webhawks.hr.services.interfaces.ISessionService;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private ISessionService sessionService;

    public ISessionService getSessionService() {
	return sessionService;
    }

    public void setSessionService(ISessionService sessionService) {
	this.sessionService = sessionService;
    }

    public HEmployee getUser() {
	HEmployee user = getSessionService().getUserSession().getUser();
	return user;
    }

    public HEmployee getSelectedUser() {
	HEmployee user = getSessionService().getUserSession().getSelectedProfile();
	return user;
    }

    public String getStringFromHttpRequest(HttpServletRequest request) throws Exception {

	InputStream is = request.getInputStream();
	String str = "";

	if (is != null) {
	    Writer writer = new StringWriter();

	    char[] buffer = new char[1024];
	    try {
		Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		int n;
		while ((n = reader.read(buffer)) != -1) {
		    writer.write(buffer, 0, n);
		}
	    } finally {
		is.close();
	    }
	    str = writer.toString();
	} else {
	    str = "";
	}
	return str;
    }

    public String convertDateFormate(String dt) {// 25-01-2016 25 01 2016 output
						 // 2016-01-25
	String formatedDate = "";
	if (dt.equals(""))
	    return "1970-01-01";
	String[] splitdt = dt.split("-");
	formatedDate = splitdt[2] + "-" + splitdt[1] + "-" + splitdt[0];

	return formatedDate;

    }

    public Integer getDay(String dt) {// 25-01-2016 01 output 01
	String formatedDate = "";

	String[] splitdt = dt.split("-");
	formatedDate = splitdt[0];

	int day = Integer.parseInt(formatedDate);

	return day;

    }

    public Integer getMonth(String dt) {// 25-01-2016 01 output 01
	String formatedDate = "";

	String[] splitdt = dt.split("-");
	formatedDate = splitdt[1];

	int mon = Integer.parseInt(formatedDate);

	return mon;

    }

    public Integer getYear(String dt) {// 25-01-2016 2016 output 2016
	String formatedDate = "";

	String[] splitdt = dt.split("-");
	formatedDate = splitdt[2];

	int year = Integer.parseInt(formatedDate);

	return year;

    }

    public String changetDateFormate(String dt) {// 4/15/2016 4 15 2016 output
						 // 2016-04-15
	String formatedDate = "";

	String[] splitdt = dt.split("/");
	formatedDate = splitdt[2] + "-" + splitdt[0] + "-" + splitdt[1];

	return formatedDate;

    }

    public String changetTimeFormate(String dt) {// 9:41:32 PM 9 41 32 output
						 // 9:41:32
	String formatedTime = "";

	String[] splitt1 = dt.trim().split(" ");
	String[] splitt2 = splitt1[0].split(":");

	String houre = splitt2[0];
	String minite = splitt2[1];
	String secend = splitt2[2];

	if (houre.equalsIgnoreCase("PM")) {
	    int inhoure = Integer.parseInt(houre);
	    inhoure = inhoure + 12;
	    houre = String.valueOf(inhoure);
	}

	formatedTime = houre + ":" + minite + ":" + secend;

	return formatedTime;

    }

    protected SimpleDateFormat CustomDateFormate(String formate) {
	SimpleDateFormat df = new SimpleDateFormat(formate);
	return df;
    }

    protected SimpleDateFormat DateFormateforView() {
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	return df;
    }

    protected SimpleDateFormat DateFormateforDB() {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	return df;
    }

    protected String getFormatedDate(Date dd) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String dt = df.format(dd);
	return dt;
    }

    protected SimpleDateFormat DateTimeFormateforView() {
	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	return df;
    }

    protected SimpleDateFormat DateTimeFormateforDB() {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return df;
    }

    protected String getFormatedTime(Date dd, String inout, String tt) {
	String dt = "";
	if (inout.equals("0")) {
	    final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
	    Calendar date = Calendar.getInstance();
	    date.setTime(dd);
	    long t = date.getTimeInMillis();
	    int timeAdjust = Integer.parseInt(tt);
	    Date afterMinusMins = new Date(t - (timeAdjust * ONE_MINUTE_IN_MILLIS));

	    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	    dt = df.format(afterMinusMins);
	} else {
	    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	    dt = df.format(dd);
	}
	return dt;
    }

    protected String getFormatedTime(Date dd) {
	String dt = "";

	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	dt = df.format(dd);

	return dt;
    }

    protected String getFormatedNumber(String n) {
	String num = "00";
	if (n.equals("1"))
	    num = "01";
	else if (n.equals("2"))
	    num = "02";
	else if (n.equals("3"))
	    num = "03";
	else if (n.equals("4"))
	    num = "04";
	else if (n.equals("5"))
	    num = "05";
	else if (n.equals("6"))
	    num = "06";
	else if (n.equals("7"))
	    num = "07";
	else if (n.equals("8"))
	    num = "08";
	else if (n.equals("9"))
	    num = "09";
	else if (n.equals("0"))
	    num = "00";
	else
	    num = n;
	return num;
    }

    protected String getTextMonth(int mon) {
	String num = "00";

	if (mon == 1)
	    num = "January";
	else if (mon == 2)
	    num = "February";
	else if (mon == 3)
	    num = "March";
	else if (mon == 4)
	    num = "April";
	else if (mon == 5)
	    num = "May";
	else if (mon == 6)
	    num = "June";
	else if (mon == 7)
	    num = "July";
	else if (mon == 8)
	    num = "August";
	else if (mon == 9)
	    num = "September";
	else if (mon == 10)
	    num = "October";
	else if (mon == 11)
	    num = "November";
	else
	    num = "December";

	return num;
    }
    
    protected int getNumericMonth(String mon) {
	int num = 0;

	if (mon.equals("January"))
	    num = 1;
	else if (mon.equals("February"))
	    num = 2;
	else if (mon.equals("March"))
	    num = 3;
	else if (mon.equals("April"))
	    num = 4;
	else if (mon.equals("May"))
	    num = 5;
	else if (mon.equals("June"))
	    num = 6;
	else if (mon.equals("July"))
	    num = 7;
	else if (mon.equals("August"))
	    num = 8;
	else if (mon.equals("September"))
	    num = 9;
	else if (mon.equals("October"))
	    num = 10;
	else if (mon.equals("November"))
	    num = 11;
	else
	    num = 12;

	return num;
    }
    
    protected boolean findFutureDate(String date) {
	boolean fo = false;

	String[] splitdt = date.split("-");
	int yy = 0, mm = 0, dd = 0;
	if (!splitdt[0].equals(""))
	    dd = Integer.parseInt(splitdt[0]);
	if (!splitdt[1].equals(""))
	    mm = Integer.parseInt(splitdt[1]);
	mm = mm - 1;
	if (!splitdt[2].equals(""))
	    yy = Integer.parseInt(splitdt[2]);
	// create calendar objects.
	Calendar cal = Calendar.getInstance();
	Calendar future = Calendar.getInstance();

	// print the current date
	// System.out.println("Current date: " + cal.getTime());

	// change date in future calendar
	future.set(yy, mm, dd);
	// System.out.println("Year is " + future.get(Calendar.YEAR));

	// check if calendar date is after current date
	Date time = future.getTime();
	if (future.after(cal)) {
	    // System.out.println("Date " + time + " is after current date.");
	    fo = true;
	}

	return fo;
    }

    public String ConvertDateToJP(String date) {
	String jpdate = "";
	String ar[] = date.split("-");
	if (ar[2] != null) {
	    long cyear = Long.parseLong(ar[2]);
	    long i = cyear - 1988;
	    int mon = Integer.parseInt(ar[1]);
	    int day = Integer.parseInt(ar[0]);

	    jpdate = "平成" + i + "年" + mon + "月" + day + "日";
	}
	return jpdate;
    }

    public String ConvertCDateToJP() {
	String datePattern = "dd-MM-yyyy";
	SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
	String date = dateFormatter.format(new Date());
	String jpdate = "";
	String ar[] = date.split("-");
	if (ar[2] != null) {
	    long cyear = Long.parseLong(ar[2]);
	    long i = cyear - 1988;
	    int mon = Integer.parseInt(ar[1]);
	    int day = Integer.parseInt(ar[0]);

	    jpdate = i + "." + mon + "." + day;
	}
	return jpdate;
    }

    public boolean deleteFile(HUpload file) {
	boolean Fo = false;
	String sname = file.getPath();
	try {
	    String rootPath = System.getProperty("catalina.home");
	    File dfile = new File(rootPath + File.separator + "webapps" + sname);
	    if (dfile.delete()) {
		Fo = true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return Fo;
    }

    public String WriteFile(HUpload file, String type) {
	String sname = file.getSaveName();
	String extension = file.getType().split("/")[1];
	String name = sname + "." + extension;
	try {
	    byte[] bytes = file.getBytes();

	    // Creating the directory to store file
	    String rootPath = System.getProperty("catalina.home");
	    File dir;
	    if (type.equals("NDA"))
		dir = new File(rootPath + File.separator + "webapps" + File.separator + "HawksHrFiles" + File.separator
			+ "Nda");
	    else if (type.equals("APPOINTMENT"))
		dir = new File(rootPath + File.separator + "webapps" + File.separator + "HawksHrFiles" + File.separator
			+ "Appointment");
	    else if (type.equals("POLICY"))
		dir = new File(rootPath + File.separator + "webapps" + File.separator + "HawksHrFiles" + File.separator
			+ "Policy");
	    else
		dir = new File(rootPath + File.separator + "webapps" + File.separator + "HawksHrFiles" + File.separator
			+ "Profile");

	    if (!dir.exists())
		dir.mkdirs();

	    // Create the file on server
	    File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
	    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	    stream.write(bytes);
	    stream.close();

	    String fileUrl = File.separator + File.separator;
	    if (type.equals("NDA"))
		fileUrl = fileUrl + "HawksHrFiles" + File.separator + File.separator + "Nda" + File.separator
			+ File.separator + name;
	    else if (type.equals("APPOINTMENT"))
		fileUrl = fileUrl + File.separator + "HawksHrFiles" + File.separator + File.separator + File.separator
			+ "Appointment" + File.separator + File.separator + File.separator + name;
	    else if (type.equals("POLICY"))
		fileUrl = fileUrl + File.separator + "HawksHrFiles" + File.separator + File.separator + File.separator
		+ "Policy" + File.separator + File.separator + File.separator + name;
	    else
		fileUrl = fileUrl + "HawksHrFiles" + File.separator + File.separator + "Profile" + File.separator
			+ File.separator + name;
	    logger.info("Server File Location=" + fileUrl);
	    return fileUrl;

	} catch (Exception e) {
	    return null;
	}
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
    
    public boolean DateCompare(String Fd,String Td,String Sy){
	boolean fo = false;
	SimpleDateFormat df = DateFormateforView();
	String splitY[] = Sy.split("/");
	if(splitY.length==2){
	    String minSt = "01-07-"+splitY[0];
	    String maxSt = "30-06-"+splitY[0].substring(0,2)+splitY[1];
	    Date mindt=new Date();
	    Date maxdt=new Date();
	    Date fromdt=new Date();
	    Date todt=new Date();
	    try {
		mindt = df.parse(minSt);
		maxdt = df.parse(maxSt);
		fromdt= df.parse(Fd);
		todt = df.parse(Td);
		
		if((mindt.getTime() <= fromdt.getTime() && fromdt.getTime() <= maxdt.getTime()) && 
			(mindt.getTime() <= todt.getTime() && todt.getTime() <= maxdt.getTime()) && (fromdt.getTime()<=todt.getTime())){
		    fo = true;
		}
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	  
	    
	    
	}
	return fo;
    }
    
    
    public String getEmialTemplateFromResource(String templateName,Map<String,String> replaceList)
    {
	String emailContent ="";
	
	InputStream is = null;
	is = getClass().getResourceAsStream("/" + templateName+".txt");
	int i = 0;
	try {
	    while ((i = is.read()) != -1) {
	    	emailContent = emailContent + (char) i;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	for (Map.Entry<String, String> entry : replaceList.entrySet())
	{
	    emailContent = emailContent.replace(entry.getKey(), entry.getValue());
	}
		
	return emailContent;
    }

}
