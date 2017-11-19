package com.webhawks.hr.services.interfaces;

import java.util.Date;
import java.util.List;

import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HEmployeeResignation;
import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HLeaveApprover;
import com.webhawks.Hawks_model.HLeaveQuota;
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

public interface IWhrService {
    
    public HEmployee getUserValidation(HEmployee user);
    
    public HEmployee getEmployeeById(String empId,boolean isValue);
    
    public List<HEmployee> getUserByTeam(String team);
    
    public List<HSupportingData> getDesignation();
    
    public List<HSupportingData> getCompany();
    
    public List<HSupportingData> getEducation();
    
    public List<HSupportingData> getMaritialStatus();
    
    public List<HSupportingData> getSign();
    
    public List<HSupportingData> getDepartment();
    
    public List<HSupportingData> getBlood();
    
    public boolean registerEmployee(HEmployee obj);
    
    public List<HTeam> getAllTeam(boolean isDeleted);
    
    public List<HSupportingData> getUserType();
    
    public boolean editEmployee(HEmployee obj,String criteria);
    
    public List<HEmployee> getAllEmployee(boolean isDeleted);
    
    public boolean addNotice(HNotice obj);
    
    public List<HNotice> getNoticeByUser(int empid, boolean datecheck);
    
    public HAttendance getFirstAttendance();
    
    public List<HAttendance> getAttendance(String sdate,String edate, int emp_id);
    
    public List<HMAttendance> getAttendanceForMonth(int mon,int year,int emp_id,boolean isCurdatechk);
    
    public List<HTypesOfLeave> getLeaveType(boolean isDeleted);
    
    public List<HLeaveApprover> getLeaveApprovers(boolean isDeleted, int emp_id);
    
    public List<HLeaveQuota> getLeaveQuotas(int empId, String year);
    
    public List<HLeaveQuota> getQuotaYear();
    
    public HTypesOfLeave getLeaveTypeById(Integer id);
    
    public List<String> calculateLeaveDate(String fdate,String tdate,int emp_id, int reqFrom);
    
    public HLeaveQuota getLeaveQuota(int empId, String year, int type_id);
    
    public Integer insertHLeaveInfo(HLeave leave);
    
    public Integer sendMail(HMail mail);
    
    public List<HLeave> getAllLeaveByEmpId(boolean isDeleted, int emp_id);
    
    public List<HLeave> getLeaveInfoByMonth(int mon, int year, int emp_id, int status);
    
    public boolean addSalaryDetails(HSalaryDetails salary);
    
    public HSalaryDetails getActiveSalaryDetails(Integer emp_id);
    
    public List<HPaySlip> getPaySlipState(Integer mon, Integer year);
    
    public HMonthlyStatus getMonthlyStatus(int mon, int year, int emp_id);
    
    public HEmployeeResignation getEmployeeResignationStatus(int mon, int year, int emp_id);
    
    public boolean isEmployeeJoinCurrentPeriod(String fromdt, String todt, int emp_id); 
    
    public HPaySlip getPayslip(int mon, int year, int emp_id);
    
    public boolean checkPayslip(int mon, int year, int emp_id);
    
    public boolean insertPaySlip(List<HPaySlip> slip);
    
    public boolean updatePaySlip(HPaySlip ps);
    
    public boolean deletePaySlip(HPaySlip ps);
    
    public List<HHoliday> getAllHoliday(boolean isDeleted,String dt);
    
    public List<HHoliday> getWeekEndsBetweenDates(String startdate, String enddate);
    
    public boolean addPolicy(HPolicy obj);
    
    public List<HPolicy> getAllPolicy(boolean isDeleted);
    
    public HPolicy getPolicy(int id);
    
    public boolean deletePolicy(HPolicy p);
    
    public HSysProperty getSysPropertyByName(String name);
    
    public List<HSalarySheet> getSalarySheet(Integer mon, Integer year);
    
    public int getMaxHolidayYear();
    
    
}
