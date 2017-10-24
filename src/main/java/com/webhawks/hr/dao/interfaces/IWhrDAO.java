package com.webhawks.hr.dao.interfaces;

import java.util.List;

import com.webhawks.Hawks_model.HWorkInfo;
import com.webhawks.Hawks_model.HAttendance;
import com.webhawks.Hawks_model.HEmployee;
import com.webhawks.Hawks_model.HEmployeeResignation;
import com.webhawks.Hawks_model.HHoliday;
import com.webhawks.Hawks_model.HLeave;
import com.webhawks.Hawks_model.HLeaveApprover;
import com.webhawks.Hawks_model.HLeaveQuota;
import com.webhawks.Hawks_model.HMail;
import com.webhawks.Hawks_model.HMonthlyStatus;
import com.webhawks.Hawks_model.HNotice;
import com.webhawks.Hawks_model.HPaySlip;
import com.webhawks.Hawks_model.HSalaryDetails;
import com.webhawks.Hawks_model.HSupportingData;
import com.webhawks.Hawks_model.HTeam;
import com.webhawks.Hawks_model.HTypesOfLeave;

public interface IWhrDAO {
    
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
    
    public HAttendance getDailyAtt(String curDate, int inout, int emp_id);
    
    public HWorkInfo getWorkInfoById(Integer emp_id, String date);
    
    public List<HHoliday> getHolidayListByDate(String date);
    
    public Boolean getLeaveInfoByDate(String date, int emp_id, int status);
    
    public List<HLeave> getLeaveDataByDate(String date, int emp_id);
    
    public List<HTypesOfLeave> getLeaveType(boolean isDeleted);
    
    public List<HLeaveApprover> getLeaveApprovers(boolean isDeleted, int emp_id);
    
    public List<HLeaveQuota> getLeaveQuotas(int empId, String year);
    
    public List<HLeaveQuota> getQuotaYear();
    
    public HTypesOfLeave getLeaveTypeById(Integer id);
    
    public HLeaveQuota getLeaveQuota(int empId, String year, int type_id);
    
    public Integer insertHLeaveInfo(HLeave leave);
    
    public Integer sendMail(HMail mail);
    
    public List<HLeave> getAllLeaveByEmpId(boolean isDeleted, int emp_id);
    
    public boolean addSalaryDetails(HSalaryDetails salary);
    
    public HSalaryDetails getActiveSalaryDetails(Integer emp_id);
    
    public List<HPaySlip> getPaySlipState(Integer mon, Integer year);
    
    public HMonthlyStatus getMonthlyStatus(int mon, int year, int emp_id);
    
    public HEmployeeResignation getEmployeeResignationStatus(String fromdt, String todt, int emp_id);
    
    public boolean isEmployeeJoinCurrentPeriod(String fromdt, String todt, int emp_id); 
    
    public HPaySlip getPayslip(int mon, int year, int emp_id);
    
    public boolean checkPayslip(int mon, int year, int emp_id);
    
    public boolean insertPaySlip(List<HPaySlip> slip);
    
    public boolean updatePaySlip(HPaySlip ps);
    
    public boolean deletePaySlip(HPaySlip ps);
    
    public List<HHoliday> getAllHoliday(boolean isDeleted,String dt);
    
}
