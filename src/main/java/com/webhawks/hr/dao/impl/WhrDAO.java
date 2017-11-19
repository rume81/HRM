package com.webhawks.hr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.webhawks.Hawks_mapper.WorkInfoMapper;
import com.webhawks.Hawks_model.HWorkInfo;
import com.webhawks.hr.dao.interfaces.IWhrDAO;
import com.webhawks.Hawks_mapper.HolidayMapper;
import com.webhawks.Hawks_mapper.LeaveApproverMapper;
import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_mapper.LeaveMapper;
import com.webhawks.Hawks_mapper.LeaveQuotaMapper;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HLeaveApprover;
import com.webhawks.Hawks_model.HLeaveQuota;
import com.webhawks.Hawks_model.HMail;
import com.webhawks.Hawks_model.HMonthlyStatus;
import com.webhawks.Hawks_mapper.AttendanceMapper;
import com.webhawks.Hawks_mapper.EmpMapper;
import com.webhawks.Hawks_mapper.EmployeeResignationMapper;
import com.webhawks.Hawks_mapper.HMonthlyStatusMapper;
import com.webhawks.Hawks_mapper.NoticeMapper;
import com.webhawks.Hawks_mapper.PaySlipMapper;
import com.webhawks.Hawks_mapper.PolicyMapper;
import com.webhawks.Hawks_mapper.SalaryDetailsMapper;
import com.webhawks.Hawks_mapper.SalarySheetMapper;
import com.webhawks.Hawks_mapper.SupportingDataMapper;
import com.webhawks.Hawks_mapper.SysPropertyMapper;
import com.webhawks.Hawks_mapper.TeamMapper;
import com.webhawks.Hawks_mapper.TypesOfLeaveMapper;
import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HEmployeeResignation;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HPolicy;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HSalarySheet;
import com.webhawks.Hawks_model.HSupportingData;
import com.webhawks.Hawks_model.HSysProperty;
import com.webhawks.Hawks_model.HTeam;
import com.webhawks.Hawks_model.HTypesOfLeave;

public class WhrDAO extends BaseDAO implements IWhrDAO {
    private final Logger logger = LoggerFactory.getLogger(WhrDAO.class);

    @Override
    public HEmployee getUserValidation(HEmployee user) {
	HEmployee profile = null;
	try {
	    /*
	     * profile = getJdbcService().getJdbcTemplate().queryForObject(
	     * "SELECT * FROM employee LEFT JOIN employeeprofile ON employee.emp_id = employeeprofile.id WHERE avator ='"
	     * + user.getAvator() + "' AND password=MD5('" + user.getPassword()
	     * + "')", new EmpMapper());
	     */

	    profile = getJdbcService().getJdbcTemplate().queryForObject(
		    "CALL validateUser('" + user.getAvator() + "',MD5('" + user.getPassword() + "'))", new EmpMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return profile;
    }

    @Override
    public HEmployee getEmployeeById(String empId, boolean isValue) {
	HEmployee profile = null;
	try {
	    if (!isValue)
		profile = getJdbcService().getJdbcTemplate().queryForObject(
			"SELECT * FROM employee LEFT JOIN employeeprofile ON employee.emp_id = employeeprofile.id WHERE emp_id ="
				+ empId + " AND deleted=false",
			new EmpMapper());
	    else
		profile = getJdbcService().getJdbcTemplate().queryForObject("CALL getEmployee(" + empId + ")",
			new EmpMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return profile;
    }

    @Override
    public List<HEmployee> getUserByTeam(String team) {
	List<HEmployee> allTeamMember = new ArrayList<HEmployee>();

	try {
	    allTeamMember = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM employee WHERE teamId =" + team + " AND deleted=false", new Object[] {},
		    new EmpMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return allTeamMember;
    }

    @Override
    public List<HSupportingData> getDesignation() {
	List<HSupportingData> allDesignation = new ArrayList<HSupportingData>();

	try {
	    allDesignation = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'POSITION'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allDesignation;
    }

    @Override
    public List<HSupportingData> getCompany() {
	List<HSupportingData> allCompany = new ArrayList<HSupportingData>();

	try {
	    allCompany = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'COMPANY'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allCompany;
    }

    @Override
    public List<HSupportingData> getEducation() {
	List<HSupportingData> allEducation = new ArrayList<HSupportingData>();

	try {
	    allEducation = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'EDUCATION'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allEducation;
    }

    @Override
    public List<HSupportingData> getMaritialStatus() {
	List<HSupportingData> allMaritialStatus = new ArrayList<HSupportingData>();

	try {
	    allMaritialStatus = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'MARITAL_STATUS'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allMaritialStatus;
    }

    @Override
    public List<HSupportingData> getSign() {
	List<HSupportingData> allSign = new ArrayList<HSupportingData>();

	try {
	    allSign = getJdbcService().getJdbcTemplate().query("SELECT * from supportingdata WHERE valuetype = 'SIGN'",
		    new Object[] {}, new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allSign;
    }

    @Override
    public List<HSupportingData> getDepartment() {
	List<HSupportingData> allSign = new ArrayList<HSupportingData>();

	try {
	    allSign = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'DEPARTMENT'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allSign;
    }

    @Override
    public List<HSupportingData> getBlood() {
	List<HSupportingData> allBlood = new ArrayList<HSupportingData>();

	try {
	    allBlood = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'BLOOD'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allBlood;
    }

    @Override
    public List<HSupportingData> getUserType() {
	List<HSupportingData> allType = new ArrayList<HSupportingData>();

	try {
	    allType = getJdbcService().getJdbcTemplate().query(
		    "SELECT * from supportingdata WHERE valuetype = 'USERTYPE'", new Object[] {},
		    new SupportingDataMapper());
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allType;
    }

    @Override
    public boolean registerEmployee(HEmployee obj) {
	boolean fo = false;
	try {
	    // StringBuffer strSql = new StringBuffer("INSERT INTO
	    // employee(avator) VALUES('"+obj.getAvator()+"')");
	    StringBuffer strSql = new StringBuffer("CALL createEmployee('" + obj.getFirst_name() + "','"
		    + obj.getLast_name() + "','" + obj.getAvator() + "','" + obj.getDesignation() + "','"
		    + obj.getDesignation2() + "','" + obj.getCompany() + "','" + obj.getDepartment() + "','"
		    + obj.getOfficial_mail() + "','" + obj.getPersonal_email() + "','" + obj.getMobile() + "','"
		    + obj.getRefferal_no() + "','" + obj.getJoin_date() + "','" + obj.getSkype_id() + "','"
		    + obj.getPrv_work_place() + "'," + obj.getRpt_mgr() + ",'" + obj.getBirthdate_real() + "','"
		    + obj.getBirthdate_certificate() + "','" + obj.getEducation() + "','" + obj.getNid() + "','"
		    + obj.getPassport() + "','" + obj.getMaritial_status() + "'," + obj.getNo_siblings() + ",'"
		    + obj.getHobbies() + "','" + obj.getExtra_activitie() + "','" + obj.getEmergency_phone() + "','"
		    + obj.getRelation() + "','" + obj.getZodiac() + "','" + obj.getEmp_number() + "','"
		    + obj.getBank_name() + "','" + obj.getBank_acc_no() + "','" + obj.getOffical_phone() + "','"
		    + obj.getExtension() + "','" + obj.getChat_nick() + "','" + obj.getPresent_address() + "','"
		    + obj.getPermanent_address() + "','" + obj.getAbout_me() + "','" + obj.getBloodgroup() + "','"
		    + obj.getProfPic().getPath() + "','" + obj.getAppointment().getPath() + "','"
		    + obj.getNda().getPath() + "','" + obj.getUsertype() + "'," + obj.getTeamId() + ",'"
		    + obj.getPassport() + "','" + obj.getModId() + "')");

	    logger.info("register Employee Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return fo;
    }

    @Override
    public boolean editEmployee(HEmployee obj, String criteria) {
	boolean fo = false;
	try {
	    StringBuffer strSql = new StringBuffer("");
	    if (criteria.equals("appointment")) {
		strSql = new StringBuffer("UPDATE employeeprofile SET appointment_letter = '"
			+ obj.getAppointment().getPath() + "' WHERE id=" + obj.getEmp_id());
	    } else if (criteria.equals("profile_pic")) {
		strSql = new StringBuffer("UPDATE employeeprofile SET profile_picture = '" + obj.getProfPic().getPath()
			+ "' WHERE id=" + obj.getEmp_id());
	    } else if (criteria.equals("nda")) {
		strSql = new StringBuffer("UPDATE employeeprofile SET nda = '" + obj.getNda().getPath() + "' WHERE id="
			+ obj.getEmp_id());
	    } else if (criteria.equals("onboarding")) { 
		strSql = new StringBuffer("CALL updateonboardinginfo(" + obj.getEmp_id() 
		+ ",'" +obj.getJobtitle() + "','" + obj.getJobnature() 
		+ "','" + obj.getWorkstation() + "','" + obj.getDesignation() 
		+ "','" + obj.getResponsiblefor() + "','" + obj.getProbation_period() 
		+ "','" + obj.getDepartment() + "','" + obj.getJoin_date() + "'," 
		+ obj.getRpt_mgr() + ",'" + obj.getJob_desc() + "','" + obj.getModId()+ "')");
		
	    } else {
		strSql = new StringBuffer("CALL updateEmployee(" + obj.getEmp_id() + ",'" + obj.getFirst_name() + "','"
			+ obj.getLast_name() + "','" + obj.getAvator() + "','" + obj.getDesignation() + "','"
			+ obj.getDesignation2() + "','" + obj.getCompany() + "','" + obj.getDepartment() + "','"
			+ obj.getOfficial_mail() + "','" + obj.getPersonal_email() + "','" + obj.getMobile() + "','"
			+ obj.getRefferal_no() + "','" + obj.getJoin_date() + "','" + obj.getSkype_id() + "','"
			+ obj.getPrv_work_place() + "'," + obj.getRpt_mgr() + ",'" + obj.getBirthdate_real() + "','"
			+ obj.getBirthdate_certificate() + "','" + obj.getEducation() + "','" + obj.getNid() + "','"
			+ obj.getPassport() + "','" + obj.getMaritial_status() + "'," + obj.getNo_siblings() + ",'"
			+ obj.getHobbies() + "','" + obj.getExtra_activitie() + "','" + obj.getEmergency_phone() + "','"
			+ obj.getRelation() + "','" + obj.getZodiac() + "','" + obj.getEmp_number() + "','"
			+ obj.getBank_name() + "','" + obj.getBank_acc_no() + "','" + obj.getOffical_phone() + "','"
			+ obj.getExtension() + "','" + obj.getChat_nick() + "','" + obj.getPresent_address() + "','"
			+ obj.getPermanent_address() + "','" + obj.getAbout_me() + "','" + obj.getBloodgroup() + "','"
			+ obj.getProfPic().getPath() + "','" + obj.getAppointment().getPath() + "','"
			+ obj.getNda().getPath() + "','" + obj.getUsertype() + "'," + obj.getTeamId() + ",'"
			+ obj.getPassport() + "','" + obj.getModId() + "')");
	    }

	    logger.info("Update Employee Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return fo;
    }

    @Override
    public List<HTeam> getAllTeam(boolean isDeleted) {
	List<HTeam> allTeam = new ArrayList<HTeam>();
	try {
	    allTeam = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM team WHERE DELETED =" + isDeleted + " ORDER BY teamName ASC", new Object[] {},
		    new TeamMapper());

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return allTeam;
    }

    @Override
    public List<HEmployee> getAllEmployee(boolean isDeleted) {
	List<HEmployee> emps = new ArrayList<HEmployee>();
	try {
	    emps = getJdbcService().getJdbcTemplate().query("CALL getAllEmployee(" + isDeleted + ")", new Object[] {},
		    new EmpMapper());

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return emps;
    }

    @Override
    public boolean addNotice(HNotice obj) {
	boolean fo = false;
	try {

	    StringBuffer strSql = new StringBuffer(
		    "CALL insertnotice('" + obj.getTitle() + "','" + obj.getDetails() + "','" + obj.getExpdate() + "','"
			    + obj.getAccesslist() + "'," + obj.getDeleted() + ",'" + obj.getModId() + "')");

	    logger.info("insert notice Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return fo;

    }

    @Override
    public List<HNotice> getNoticeByUser(int empid, boolean datecheck) {
	List<HNotice> allNotice = new ArrayList<HNotice>();

	try {
	    if (datecheck) {
		allNotice = getJdbcService()
			.getJdbcTemplate().query(
				"SELECT notice.notice_id, notice.title, notice.details, notice.expiredate, notice.deleted, notice.modifierid, noticeaccess.emp_id AS accesslist FROM notice LEFT JOIN noticeaccess ON notice.notice_id = noticeaccess.notice_id WHERE noticeaccess.emp_id="
					+ empid + " AND notice.expiredate > NOW()",
				new Object[] {}, new NoticeMapper());
	    } else {
		allNotice = getJdbcService().getJdbcTemplate()
			.query("SELECT notice.notice_id, notice.title, notice.details, notice.expiredate, notice.deleted, notice.modifierid, noticeaccess.emp_id AS accesslist FROM notice LEFT JOIN noticeaccess ON notice.notice_id = noticeaccess.notice_id WHERE noticeaccess.emp_id="
				+ empid, new Object[] {}, new NoticeMapper());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return allNotice;
    }

    @Override
    public HAttendance getFirstAttendance() {
	HAttendance att = new HAttendance();

	try {
	    att = getJdbcService().getJdbcTemplate().queryForObject(
		    "SELECT * FROM attendance WHERE att_id= (SELECT MIN(att_id) FROM attendance)",
		    new AttendanceMapper());

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return att;
    }

    @Override
    public List<HAttendance> getAttendance(String sdate, String edate, int emp_id) {
	List<HAttendance> att = new ArrayList<HAttendance>();

	try {
	    att = getJdbcService().getJdbcTemplate().query("SELECT * FROM attendance WHERE att_date BETWEEN '" + sdate
		    + "' AND '" + edate + "' AND emp_id=" + emp_id, new Object[] {}, new AttendanceMapper());

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return att;
    }

    @Override
    public HAttendance getDailyAtt(String curDate, int inout, int emp_id) {
	HAttendance att = new HAttendance();
	try {

	    if (inout == 0) {
		att = getJdbcService().getJdbcTemplate()
			.queryForObject("SELECT * FROM attendance WHERE att_inout = " + inout + " AND att_date='"
				+ curDate + "' AND emp_id=" + emp_id + " AND "
				+ "att_id = (SELECT att_id FROM ams.attendance WHERE  att_inout = " + inout
				+ " AND att_date='" + curDate + "' AND emp_id=" + emp_id
				+ " ORDER BY att_time LIMIT 1)", new Object[] {}, new AttendanceMapper());
	    } else {
		att = getJdbcService().getJdbcTemplate()
			.queryForObject("SELECT * FROM attendance WHERE att_inout = " + inout + " AND att_date='"
				+ curDate + "' AND emp_id=" + emp_id + " AND "
				+ "att_id = (SELECT att_id FROM ams.attendance WHERE  att_inout = " + inout
				+ " AND att_date='" + curDate + "' AND emp_id=" + emp_id
				+ " ORDER BY att_time DESC LIMIT 1)", new Object[] {}, new AttendanceMapper());
	    }
	} catch (Exception ex) {
	    // ex.printStackTrace();
	}
	return att;
    }

    @Override
    public HWorkInfo getWorkInfoById(Integer emp_id, String date) {
	HWorkInfo workInfo = new HWorkInfo();
	try {
	    StringBuffer sql = new StringBuffer("SELECT * FROM workinfo where emp_id = " + emp_id
		    + " AND id=(SELECT MAX(id) FROM workinfo WHERE emp_id = " + emp_id + " AND from_date <= '" + date
		    + "')");
	    logger.info("HWorkInfo Query - > " + sql.toString());

	    workInfo = getJdbcService().getJdbcTemplate().queryForObject(sql.toString(), new Object[] {},
		    new WorkInfoMapper());
	} catch (Exception ex) {
	    // ex.printStackTrace();
	}
	return workInfo;
    }

    @Override
    public List<HHoliday> getHolidayListByDate(String date) {
	List<HHoliday> holiday = new ArrayList<HHoliday>();
	try {
	    StringBuffer sql = new StringBuffer("SELECT * FROM holiday WHERE date_from <='" + date + "' AND date_to >='"
		    + date + "'  AND deleted = false");

	    logger.info("HHoliday Query - > " + sql.toString());
	    holiday = getJdbcService().getJdbcTemplate().query(sql.toString(), new Object[] {}, new HolidayMapper());
	} catch (Exception ex) {
	    // ex.printStackTrace();
	}
	return holiday;
    }

    @Override
    public Boolean getLeaveInfoByDate(String date, int emp_id, int status) {
	Boolean Fo = false;
	List<HLeave> allLeave = new ArrayList<HLeave>();
	try {
	    if (status == -1) {
		allLeave = getJdbcService().getJdbcTemplate()
			.query("SELECT * FROM leavesrequest where fdate <='" + date + "' AND tdate >='" + date
				+ "' AND emp_id=" + emp_id + " AND status<>1 AND deleted = false", new Object[] {},
				new LeaveMapper());
	    } else {
		allLeave = getJdbcService().getJdbcTemplate()
			.query("SELECT * FROM leavesrequest where fdate <='" + date + "' AND tdate >='" + date
				+ "' AND emp_id=" + emp_id + " AND status>=" + status + " AND deleted = false",
				new Object[] {}, new LeaveMapper());
	    }
	    if (allLeave.size() > 0)
		Fo = true;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return Fo;
    }

    @Override
    public List<HLeave> getLeaveDataByDate(String date, int emp_id) {
	List<HLeave> leaves = new ArrayList<HLeave>();
	try {

	    leaves = getJdbcService()
		    .getJdbcTemplate().query(
			    "SELECT * FROM leavesrequest where fdate <='" + date + "' AND tdate >='" + date
				    + "' AND emp_id=" + emp_id + "  AND deleted = false",
			    new Object[] {}, new LeaveMapper());

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return leaves;
    }

    @Override
    public List<HTypesOfLeave> getLeaveType(boolean isDeleted) {
	List<HTypesOfLeave> allLeaveType = new ArrayList<HTypesOfLeave>();
	try {

	    allLeaveType = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM typesofleave WHERE DELETED =" + isDeleted, new Object[] {},
		    new TypesOfLeaveMapper());

	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return allLeaveType;
    }

    @Override
    public List<HLeaveApprover> getLeaveApprovers(boolean isDeleted, int emp_id) {
	List<HLeaveApprover> allApp = new ArrayList<HLeaveApprover>();
	try {
	    allApp = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM leaveapprover WHERE DELETED = " + isDeleted + " AND emp_id = " + emp_id,
		    new Object[] {}, new LeaveApproverMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return allApp;
    }

    @Override
    public List<HLeaveQuota> getLeaveQuotas(int empId, String year) {
	List<HLeaveQuota> leaveQuotas = new ArrayList<HLeaveQuota>();

	try {
	    leaveQuotas = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM leavequota WHERE DELETED = false AND emp_id=" + empId + " AND year='" + year + "'",
		    new Object[] {}, new LeaveQuotaMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return leaveQuotas;
    }

    @Override
    public List<HLeaveQuota> getQuotaYear() {
	List<HLeaveQuota> year = new ArrayList<HLeaveQuota>();

	try {
	    year = getJdbcService().getJdbcTemplate().query("SELECT distinct year FROM leavequota", new Object[] {},
		    new LeaveQuotaMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return year;
    }

    @Override
    public HTypesOfLeave getLeaveTypeById(Integer id) {
	HTypesOfLeave leave = new HTypesOfLeave();
	try {
	    leave = getJdbcService().getJdbcTemplate().queryForObject(
		    "SELECT * FROM typesofleave WHERE id =" + id + " AND DELETED = 0", new TypesOfLeaveMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return leave;
    }

    @Override
    public HLeaveQuota getLeaveQuota(int empId, String year, int type_id) {
	HLeaveQuota leaveQuotas = new HLeaveQuota();
	HTypesOfLeave leaveType = new HTypesOfLeave();
	try {
	    leaveType = getJdbcService().getJdbcTemplate().queryForObject(
		    "SELECT * FROM typesofleave WHERE id=" + type_id, new Object[] {}, new TypesOfLeaveMapper());
	    int deductFrom = type_id;
	    if (leaveType != null && leaveType.getDeductfrom() > 0) {
		deductFrom = leaveType.getDeductfrom();
	    }
	    leaveQuotas = getJdbcService().getJdbcTemplate()
		    .queryForObject("SELECT * FROM leavequota WHERE DELETED = false AND emp_id=" + empId + " AND year='"
			    + year + "' AND type_id=" + deductFrom, new Object[] {}, new LeaveQuotaMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return leaveQuotas;
    }

    @Override
    public Integer insertHLeaveInfo(HLeave leave) {
	Integer empId = 0;
	leave.escapeEcmaScript();

	TransactionStatus status = getJdbcService().getTransactionStatus();

	getJdbcService().beginTran();
	try {
	    StringBuffer sql = new StringBuffer("INSERT INTO leavesrequest ");

	    sql.append(
		    "(emp_id, fdate, tdate, days, leavetype, quotayear, reason, status, approvar1, approvar2, approvar3, approvar4, approvar5, app1state, app2state, app3state, app4state, app5state, deleted, modifierid,apllieddate,modifydate) VALUES(");

	    sql.append(leave.getEmp_id() + ",'" + leave.getFdate() + "','" + leave.getTdate() + "'," + leave.getDays()
		    + "," + leave.getLeavetype().getId() + ",'" + leave.getLeavequota().getYear() + "','"
		    + leave.getReason() + "'," + leave.getStatus() + "," + leave.getApprovar1() + ","
		    + leave.getApprovar2() + "," + leave.getApprovar3() + "," + leave.getApprovar4() + ","
		    + leave.getApprovar5() + "," + leave.getApp1state() + "," + leave.getApp2state() + ","
		    + leave.getApp3state() + "," + leave.getApp4state() + "," + leave.getApp5state() + ","
		    + leave.getDeleted() + ",'" + leave.getModId() + "','" + leave.getApllieddate() + "','"
		    + leave.getModifydate() + "')");

	    logger.info("HLeave Insert Query - > " + sql.toString());

	    getJdbcService().getJdbcTemplate().execute(sql.toString());

	    StringBuffer sql1 = new StringBuffer("CALL insertleavequota(" + leave.getDays() + "," + leave.getEmp_id()
		    + "," + leave.getLeavetype().getId() + ",'" + leave.getLeavequota().getYear() + "')");

	    getJdbcService().getJdbcTemplate().execute(sql1.toString());
	    empId = leave.getEmp_id();

	    getJdbcService().commitTran(status);
	} catch (Exception ex) {
	    ex.printStackTrace();
	    getJdbcService().rollbackTran(status);
	}
	return empId;
    }

    @Override
    public Integer sendMail(HMail mail) {
	Integer mailId = 0;
	mail.escapeEcmaScript();
	try {
	    StringBuffer sql = new StringBuffer("INSERT INTO mails ");

	    sql.append(
		    "(mail_from, mail_to, cc, bcc, subject, date, msg, status, sendTime, deleted, modifierid) VALUES( '");

	    sql.append(mail.getFrom() + "','" + mail.getTo() + "','" + mail.getCc() + "','" + mail.getBcc() + "','"
		    + mail.getSubject() + "','" + mail.getDate() + "','" + mail.getMsg() + "','" + mail.getStatus()
		    + "','" + mail.getSendTime() + "'," + mail.getDeleted() + ",'" + mail.getModId() + "')");

	    logger.info("HMail Insert Query - > " + sql.toString());

	    getJdbcService().getJdbcTemplate().execute(sql.toString());
	    mailId = 1;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return mailId;
    }

    @Override
    public List<HLeave> getAllLeaveByEmpId(boolean isDeleted, int emp_id) {
	List<HLeave> allLeave = new ArrayList<HLeave>();
	try {
	    if (!isDeleted)
		allLeave = getJdbcService()
			.getJdbcTemplate().query(
				"SELECT leavesrequest.*,typesofleave.leavetype AS typename FROM leavesrequest LEFT JOIN typesofleave ON leavesrequest.leavetype = typesofleave.id WHERE leavesrequest.DELETED = 0 AND leavesrequest.emp_id="
					+ emp_id + " order by leavesrequest.fdate asc",
				new Object[] {}, new LeaveMapper());
	    else
		allLeave = getJdbcService()
			.getJdbcTemplate().query(
				"SELECT leavesrequest.*,typesofleave.leavetype AS typename FROM leavesrequest LEFT JOIN typesofleave ON leavesrequest.leavetype = typesofleave.id WHERE leavesrequest.emp_id="
					+ emp_id + " order by leavesrequest.fdate asc",
				new Object[] {}, new LeaveMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return allLeave;
    }

    @Override
    public boolean addSalaryDetails(HSalaryDetails obj) {
	Boolean Fo = false;
	try {

	    StringBuffer strSql = new StringBuffer("CALL insertsalarydetails(" + obj.getSalid() + "," + obj.getEmp_id()
		    + ",'" + obj.getEmp_finance_id() + "'," + obj.getGross() + "," + obj.getBasic() + ","
		    + obj.getHousing() + "," + obj.getMedical() + "," + obj.getTransport() + "," + obj.getLunch() + ","
		    + obj.getTax() + ",'" + obj.getApplieddate() + "'," + obj.getStatus() + "," + obj.getDeleted()
		    + ",'" + obj.getModId() + "')");

	    logger.info("insert Salary Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    Fo = true;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Fo;
    }

    @Override
    public HSalaryDetails getActiveSalaryDetails(Integer emp_id) {
	HSalaryDetails sal = null;
	try {
	    sal = getJdbcService().getJdbcTemplate().queryForObject(
		    "SELECT * FROM salarydetails WHERE emp_id=" + emp_id + " AND status=true AND deleted=false",
		    new SalaryDetailsMapper());
	} catch (Exception ex) {
	}

	return sal;
    }

    @Override
    public List<HPaySlip> getPaySlipState(Integer mon, Integer year) {
	List<HPaySlip> ps = new ArrayList<HPaySlip>();
	try {
	    ps = getJdbcService().getJdbcTemplate().query(
		    "SELECT * FROM payslip WHERE month=" + mon + " AND year=" + year + " AND deleted=false",
		    new PaySlipMapper());
	} catch (Exception ex) {
	}

	return ps;
    }
    
    public List<HSalarySheet> getSalarySheet(Integer mon, Integer year) {
	List<HSalarySheet> ps = new ArrayList<HSalarySheet>();
	try {
	    ps = getJdbcService().getJdbcTemplate().query("CALL getSalarySheet("+mon+","+year+",false)",
		    new SalarySheetMapper());
	} catch (Exception ex) {
	}

	return ps;
    }
    
    @Override
    public HMonthlyStatus getMonthlyStatus(int mon, int year, int emp_id) {
	HMonthlyStatus status = new HMonthlyStatus();

	try {
	    status = getJdbcService().getJdbcTemplate().queryForObject("SELECT * FROM monthlystatus WHERE month='" + mon
		    + "' AND year='" + year + "' AND emp_id=" + emp_id, new Object[] {}, new HMonthlyStatusMapper());
	} catch (Exception ex) {
	    status = null;
	}
	return status;
    }

    @Override
    public HEmployeeResignation getEmployeeResignationStatus(String fromdt, String todt, int emp_id) {
	HEmployeeResignation resig = null;

	try {
	    resig = getJdbcService().getJdbcTemplate().queryForObject(
		    "SELECT * FROM employeeresignation WHERE lastofficedate BETWEEN '" + fromdt + "' AND '" + todt
			    + "' AND status = 2 AND emp_id=" + emp_id,
		    new Object[] {}, new EmployeeResignationMapper());

	} catch (Exception ex) {
	    resig = null;
	}

	return resig;
    }

    @Override
    public boolean isEmployeeJoinCurrentPeriod(String fromdt, String todt, int emp_id) {
	boolean Fo = false;

	try {
	    HEmployee emp = getJdbcService().getJdbcTemplate()
		    .queryForObject("SELECT * FROM employee WHERE join_date BETWEEN '" + fromdt + "' AND '" + todt
			    + "' AND emp_id=" + emp_id, new Object[] {}, new EmpMapper());

	    if (null != emp)
		Fo = true;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return Fo;
    }

    @Override
    public HPaySlip getPayslip(int mon, int year, int emp_id) {
	HPaySlip ps = new HPaySlip();
	try {
	    ps = getJdbcService().getJdbcTemplate().queryForObject("SELECT * FROM payslip WHERE month=" + mon
		    + " AND year=" + year + " AND emp_id =" + emp_id + " AND deleted=false", new PaySlipMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return ps;
    }

    @Override
    public boolean checkPayslip(int mon, int year, int emp_id) {
	boolean Fo = false;
	HPaySlip ps = null;
	try {
	    ps = getJdbcService().getJdbcTemplate().queryForObject("SELECT * FROM payslip WHERE month=" + mon
		    + " AND year=" + year + " AND emp_id =" + emp_id + " AND deleted=false", new PaySlipMapper());
	    if ((null != ps) && (ps.getEmp_id() == emp_id)) {
		Fo = true;
	    }
	} catch (Exception ex) {
	    Fo = false;
	}

	return Fo;
    }

    @Override
    public boolean insertPaySlip(List<HPaySlip> slip) {
	Boolean Fo = false;
	try {
	    StringBuffer strSql = new StringBuffer(
		    "INSERT INTO payslip (emp_id, month, year, emp_name, emp_finance_id, swdm, awdm, appleave, absent, awhm, oaom, gross, basic, dbpr, hbpr, nodwm, nohwm, hoo, ohih, htns, tpl, nots, bstbp, soow, soho, pftnh, sfapl, bta, ha, ma, ta, bonus, arrear_bonus, hma, cfhi, lwp, lunch, tax, arrears, slipstate, deleted, modifierid) VALUES(");
	    for (int i = 0; i < slip.size(); i++) {
		HPaySlip p = slip.get(i);
		if ((i + 1) == slip.size()) {
		    strSql.append(p.getEmp_id() + "," + p.getMonth() + "," + p.getYear() + ",'" + p.getEmp_name()
			    + "','" + p.getEmp_finance_id() + "'," + p.getSwdm() + "," + p.getAwdm() + ","
			    + p.getAppleave() + "," + p.getAbsent() + "," + p.getAwhm() + "," + p.getOaom() + ","
			    + p.getGross() + "," + p.getBasic() + "," + p.getDbpr() + "," + p.getHbpr() + ","
			    + p.getNodwm() + "," + p.getNohwm() + "," + p.getHoo() + "," + p.getOhih() + ","
			    + p.getHtns() + "," + p.getTpl() + "," + p.getNots() + "," + p.getBstbp() + ","
			    + p.getSoow() + "," + p.getSoho() + "," + p.getPftnh() + "," + p.getSfapl() + ","
			    + p.getBta() + "," + p.getHa() + "," + p.getMa() + "," + p.getTa() + "," + p.getBonus()
			    + "," + p.getArrear_bonus() + "," + p.getHma() + "," + p.getCfhi() + "," + p.getLwp() + ","
			    + p.getLunch() + "," + p.getTax() + "," + p.getArrears() + "," + p.getSlipstate() + ","
			    + p.getDeleted() + ",'" + p.getModId() + "')");
		} else {
		    strSql.append(p.getEmp_id() + "," + p.getMonth() + "," + p.getYear() + ",'" + p.getEmp_name()
			    + "','" + p.getEmp_finance_id() + "'," + p.getSwdm() + "," + p.getAwdm() + ","
			    + p.getAppleave() + "," + p.getAbsent() + "," + p.getAwhm() + "," + p.getOaom() + ","
			    + p.getGross() + "," + p.getBasic() + "," + p.getDbpr() + "," + p.getHbpr() + ","
			    + p.getNodwm() + "," + p.getNohwm() + "," + p.getHoo() + "," + p.getOhih() + ","
			    + p.getHtns() + "," + p.getTpl() + "," + p.getNots() + "," + p.getBstbp() + ","
			    + p.getSoow() + "," + p.getSoho() + "," + p.getPftnh() + "," + p.getSfapl() + ","
			    + p.getBta() + "," + p.getHa() + "," + p.getMa() + "," + p.getTa() + "," + p.getBonus()
			    + "," + p.getArrear_bonus() + "," + p.getHma() + "," + p.getCfhi() + "," + p.getLwp() + ","
			    + p.getLunch() + "," + p.getTax() + "," + p.getArrears() + "," + p.getSlipstate() + ","
			    + p.getDeleted() + ",'" + p.getModId() + "'),(");
		}
	    }

	    logger.info("insert HPaySlip Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    Fo = true;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Fo;
    }

    @Override
    public boolean updatePaySlip(HPaySlip ps) {
	Boolean Fo = false;
	try {
	    StringBuffer strSql = new StringBuffer("UPDATE payslip SET " + "swdm=" + ps.getSwdm() + ",awdm="
		    + ps.getAwdm() + ", appleave=" + ps.getAppleave() + ", absent=" + ps.getAbsent() + ", awhm="
		    + ps.getAwhm() + ", oaom=" + ps.getOaom() + ", gross=" + ps.getGross() + ", basic=" + ps.getBasic()
		    + ", dbpr=" + ps.getDbpr() + ", hbpr=" + ps.getHbpr() + ", nodwm=" + ps.getNodwm() + ", nohwm="
		    + ps.getNohwm() + ", hoo=" + ps.getHoo() + ", ohih=" + ps.getOhih() + ", htns=" + ps.getHtns()
		    + ", tpl=" + ps.getTpl() + ", nots=" + ps.getNots() + ", bstbp=" + ps.getBstbp() + ", soow="
		    + ps.getSoow() + ", soho=" + ps.getSoho() + ", pftnh=" + ps.getPftnh() + ", sfapl=" + ps.getSfapl()
		    + ", bta=" + ps.getBta() + ", ha=" + ps.getHa() + ", ma=" + ps.getMa() + ", ta=" + ps.getTa()
		    + ", bonus=" + ps.getBonus() + ", hma=" + ps.getHma() + ", cfhi=" + ps.getCfhi() + ", lwp="
		    + ps.getLwp() + ", lunch=" + ps.getLunch() + ", tax=" + ps.getTax() + ", arrears=" + ps.getArrears()
		    + ", slipstate=" + ps.getSlipstate() + ", deleted=" + ps.getDeleted() + ", modifierid='"
		    + ps.getModId() + "' WHERE emp_id=" + ps.getEmp_id() + " AND month=" + ps.getMonth() + " AND year="
		    + ps.getYear());

	    logger.info("update HPaySlip Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    Fo = true;

	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Fo;
    }

    @Override
    public boolean deletePaySlip(HPaySlip ps) {
	Boolean Fo = false;
	try {
	    StringBuffer strSql = new StringBuffer("UPDATE payslip SET " + " deleted=" + ps.getDeleted()
		    + ", modifierid='" + ps.getModId() + "' WHERE emp_id=" + ps.getEmp_id() + " AND month="
		    + ps.getMonth() + " AND year=" + ps.getYear());

	    logger.info("update HPaySlip Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    Fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Fo;
    }

    @Override
    public List<HHoliday> getAllHoliday(boolean isDeleted,String dt) {
	List<HHoliday> allHoliday = new ArrayList<HHoliday>();
	try {
	    if (!isDeleted)
		allHoliday = getJdbcService().getJdbcTemplate().query(
			"SELECT * FROM holiday WHERE DELETED = 0 AND date_from>='"+dt+"' ORDER BY date_from",
			new Object[] {}, new HolidayMapper());
	    else
		allHoliday = getJdbcService().getJdbcTemplate().query(
			"SELECT * FROM holiday WHERE date_from>='"+dt+"' ORDER BY date_from", new Object[] {},
			new HolidayMapper());
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return allHoliday;
    }
    
    @Override
    public int getMaxHolidayYear(){
    	int year=0;
    	
    	try{
    		year = getJdbcService().getJdbcTemplate().queryForInt("SELECT Max(holiday_year) FROM holiday WHERE deleted = false");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}
    	return year;
    }
    
    @Override
    public boolean addPolicy(HPolicy obj){
	boolean fo = false;
	try {

	    StringBuffer strSql = new StringBuffer(
		    "CALL insertpolicy('" + obj.getPolicy_name() + "','" + obj.getPolicy_file().getPath() + "'," + obj.getDeleted() + ",'" + obj.getModId() + "')");

	    logger.info("insert policy Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return fo;
    }
    
    @Override
    public List<HPolicy> getAllPolicy(boolean isDeleted){
	List<HPolicy> policys = new ArrayList<HPolicy>();
	try {
	    policys = getJdbcService().getJdbcTemplate().query(
			"SELECT * FROM policy WHERE DELETED = "+isDeleted+" ORDER BY policy_name",
			new Object[] {}, new PolicyMapper());
	    
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return policys;
    }
    
    @Override
    public HPolicy getPolicy(int id){
	HPolicy policy = new HPolicy();
	try {
	    policy = getJdbcService().getJdbcTemplate().queryForObject(
			"SELECT * FROM policy WHERE id="+id+" AND DELETED = false",
			new Object[] {}, new PolicyMapper());
	    
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return policy;
    }
    
    @Override
    public boolean deletePolicy(HPolicy ps) {
	Boolean Fo = false;
	try {
	    StringBuffer strSql = new StringBuffer("UPDATE policy SET " + " deleted=" + ps.getDeleted()
		    + ", modifierid='" + ps.getModId() + "' WHERE id=" + ps.getId());

	    logger.info("update HPolicy Query - > " + strSql.toString());

	    getJdbcService().getJdbcTemplate().execute(strSql.toString());

	    Fo = true;
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Fo;
    }
    
    @Override
    public HSysProperty getSysPropertyByName(String name){
	HSysProperty sp = new HSysProperty();
	try {
	    sp = getJdbcService().getJdbcTemplate().queryForObject(
			"SELECT * FROM sysproperty WHERE prop_name='"+name+"'",
			new Object[] {}, new SysPropertyMapper());
	    
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return sp;
    }
}
