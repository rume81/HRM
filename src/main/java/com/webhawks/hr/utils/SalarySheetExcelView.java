package com.webhawks.hr.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.webhawks.Hawks_model.HSalarySheet;


public class SalarySheetExcelView extends AbstractExcelView {
    private int record = 6;
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	List<HSalarySheet> salList = (List<HSalarySheet>) model.get("slip");
	int mon = salList.get(0).getMonth();
	int year = salList.get(0).getYear();
	
	response.setHeader("Content-Disposition", "attachment; filename=\"Salary_sheet_"+mon+"_"+year+".xls\"");
			
	HSSFSheet excelSheet = workbook.createSheet("sheet_"+mon+"_"+year);
	
	HSSFCellStyle style = excelSheet.getWorkbook().createCellStyle();
	//HSSFCellStyle style1 = excelSheet.getWorkbook().createCellStyle();
	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	
	/*style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	style1.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
	style1.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
	style1.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
	style1.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);*/
	
	setExcelHeader(excelSheet,style,mon,year);
	setExcelRows(excelSheet,salList,style);
		
	excelSheet.addMergedRegion(new CellRangeAddress(record-2,record-2,0,1));
	excelSheet.addMergedRegion(new CellRangeAddress(record-1,record-1,0,22));
    }

    public void setExcelHeader(HSSFSheet excelSheet,HSSFCellStyle style,int mon,int year) {
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,0,0));
	
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,1,1));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,2,2));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,3,3));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,4,4));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,5,5));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,6,6));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,7,7));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,8,8));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,9,9));
	excelSheet.addMergedRegion(new CellRangeAddress(0,5,10,10));
	
	excelSheet.addMergedRegion(new CellRangeAddress(0,0,11,23));
	
	excelSheet.addMergedRegion(new CellRangeAddress(1,5,11,11));
	excelSheet.addMergedRegion(new CellRangeAddress(1,1,12,15));
	excelSheet.addMergedRegion(new CellRangeAddress(1,1,16,20));
	excelSheet.addMergedRegion(new CellRangeAddress(1,1,21,21));
	excelSheet.addMergedRegion(new CellRangeAddress(1,1,22,22));
	excelSheet.addMergedRegion(new CellRangeAddress(1,5,23,23));
	
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,12,12));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,13,13));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,14,14));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,15,15));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,16,16));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,17,17));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,18,18));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,19,19));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,20,20));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,21,21));
	excelSheet.addMergedRegion(new CellRangeAddress(2,5,22,22));
	
	HSSFRow excelHeader = excelSheet.createRow(0);
	excelHeader.createCell(0).setCellValue("SL no.");
	excelHeader.createCell(1).setCellValue("Name of employees");
	excelHeader.createCell(2).setCellValue("Employees ID");
	excelHeader.createCell(3).setCellValue("Joining Date");
	excelHeader.createCell(4).setCellValue("Designation");
	excelHeader.createCell(5).setCellValue("Working days");
	excelHeader.createCell(6).setCellValue("Standerd hrs");
	excelHeader.createCell(7).setCellValue("Working hrs");
	excelHeader.createCell(8).setCellValue("Leave");
	excelHeader.createCell(9).setCellValue("Absent");
	excelHeader.createCell(10).setCellValue("Present Days");
	excelHeader.createCell(11).setCellValue("Salary Sheet for "+getTextMonth(mon)+" "+year);
	
	HSSFRow excelHeader1 = excelSheet.createRow(1);
	excelHeader1.createCell(11).setCellValue("Grose Salary");
	excelHeader1.createCell(12).setCellValue("Salary breakdown");
	excelHeader1.createCell(16).setCellValue("Total Deduction");
	excelHeader1.createCell(21).setCellValue("Arrears");
	excelHeader1.createCell(22).setCellValue("Bonus");
	excelHeader1.createCell(23).setCellValue("Net Payable");
	
	HSSFRow excelHeader2 = excelSheet.createRow(2);
	excelHeader2.createCell(12).setCellValue("Basic");
	excelHeader2.createCell(13).setCellValue("House rent");
	excelHeader2.createCell(14).setCellValue("Medical");
	excelHeader2.createCell(15).setCellValue("Conveyance");
	excelHeader2.createCell(16).setCellValue("Tax Deduction");
	excelHeader2.createCell(17).setCellValue("Contribution for lunch");
	excelHeader2.createCell(18).setCellValue("Leave Without Payment(LWP)");
	excelHeader2.createCell(19).setCellValue("Other/Advanced");
	excelHeader2.createCell(20).setCellValue("Total deduction");
	excelHeader2.createCell(21).setCellValue("Arrear (in any)");
	excelHeader2.createCell(22).setCellValue("Festival Bonus");
	
	
	excelHeader.getCell(0).setCellStyle(style);
	excelHeader.getCell(1).setCellStyle(style);
	excelHeader.getCell(2).setCellStyle(style);
	excelHeader.getCell(3).setCellStyle(style);
	excelHeader.getCell(4).setCellStyle(style);
	excelHeader.getCell(5).setCellStyle(style);
	excelHeader.getCell(6).setCellStyle(style);
	excelHeader.getCell(7).setCellStyle(style);
	excelHeader.getCell(8).setCellStyle(style);
	excelHeader.getCell(9).setCellStyle(style);
	excelHeader.getCell(10).setCellStyle(style);
	excelHeader.getCell(11).setCellStyle(style);
	
	
	excelHeader1.getCell(11).setCellStyle(style);
	excelHeader1.getCell(12).setCellStyle(style);
	excelHeader1.getCell(16).setCellStyle(style);
	excelHeader1.getCell(21).setCellStyle(style);
	excelHeader1.getCell(22).setCellStyle(style);
	excelHeader1.getCell(23).setCellStyle(style);
	
	excelHeader2.getCell(12).setCellStyle(style);
	excelHeader2.getCell(13).setCellStyle(style);
	excelHeader2.getCell(14).setCellStyle(style);
	excelHeader2.getCell(15).setCellStyle(style);
	excelHeader2.getCell(16).setCellStyle(style);
	excelHeader2.getCell(17).setCellStyle(style);
	excelHeader2.getCell(18).setCellStyle(style);
	excelHeader2.getCell(19).setCellStyle(style);
	excelHeader2.getCell(20).setCellStyle(style);
	excelHeader2.getCell(21).setCellStyle(style);
	excelHeader2.getCell(22).setCellStyle(style);
    }

    public void setExcelRows(HSSFSheet excelSheet, List<HSalarySheet> payslip,HSSFCellStyle style) {
	double gross = 0;
	double basic = 0;
	double ha = 0;
	double ma =0;
	double ta = 0;
	double totdeduct = 0;
	double totnetpay = 0;
	
	for (HSalarySheet ps : payslip) {
	    HSSFRow excelRow = excelSheet.createRow(record++);
	    
	    excelRow.createCell(0).setCellValue(String.valueOf(record-6));
	    if(ps.getEmp_name()!=null)
		excelRow.createCell(1).setCellValue(ps.getEmp_name());
	    else
		excelRow.createCell(1).setCellValue("");
	    
	    if(ps.getEmp_finance_id()!=null)
		excelRow.createCell(2).setCellValue(ps.getEmp_finance_id());
	    else
		excelRow.createCell(2).setCellValue("");
	    if(ps.getJoin_date()!=null)
		excelRow.createCell(3).setCellValue(ps.getJoin_date());
	    else
		excelRow.createCell(3).setCellValue("");
	    if(ps.getDesignation()!=null)
		excelRow.createCell(4).setCellValue(ps.getDesignation());
	    else
		excelRow.createCell(4).setCellValue("");
	    if(ps.getSwdm()!=null)
		excelRow.createCell(5).setCellValue(ps.getSwdm());
	    else
		excelRow.createCell(5).setCellValue("");
	    
	    if(ps.getAwhm()!=null)
		excelRow.createCell(6).setCellValue(ps.getAwhm());
	    else
		excelRow.createCell(6).setCellValue("");
	    
	    if(ps.getNohwm()!=null)
		excelRow.createCell(7).setCellValue(ps.getNohwm());
	    else
		excelRow.createCell(7).setCellValue("");
	    
	    if(ps.getAppleave()!=null)
		excelRow.createCell(8).setCellValue(ps.getAppleave());
	    else
		excelRow.createCell(8).setCellValue("");
	    
	    if(ps.getAbsent()!=null)
		excelRow.createCell(9).setCellValue(ps.getAbsent());
	    else
		excelRow.createCell(9).setCellValue("");
	    
	    if(ps.getAwdm()!=null)
		excelRow.createCell(10).setCellValue(ps.getAwdm());
	    else
		excelRow.createCell(10).setCellValue("");
	    
	    //Total calculation
	    double total = ps.getBstbp()+ps.getHa()+ps.getMa()+ps.getTa();
	    if(ps.getBstbp()!=null){
		excelRow.createCell(11).setCellValue(ps.getGross());
		gross = gross + ps.getGross();
	    } else
		excelRow.createCell(11).setCellValue("");
	    
	    if(ps.getBstbp()!=null){
		excelRow.createCell(12).setCellValue(ps.getBstbp());
		basic = basic +ps.getBstbp();
	    } else
		excelRow.createCell(12).setCellValue("");
	    
	    
	    if(ps.getHa()!=null){
		excelRow.createCell(13).setCellValue(ps.getHa());
		ha = ha +ps.getHa();
	    } else
		excelRow.createCell(13).setCellValue("");
	    
	    
	    if(ps.getMa()!=null){
		excelRow.createCell(14).setCellValue(ps.getMa());
		ma = ma + ps.getMa();
	    } else
		excelRow.createCell(14).setCellValue("");
	    
	    if(ps.getTa()!=null){
		excelRow.createCell(15).setCellValue(ps.getTa());
		ta = ta + ps.getTa();
	    } else
		excelRow.createCell(15).setCellValue("");
	    
	    
	    if(ps.getTax()!=null)
		excelRow.createCell(16).setCellValue(ps.getTax());
	    else
		excelRow.createCell(16).setCellValue("");
	    
	    if(ps.getLunch()!=null)
		excelRow.createCell(17).setCellValue(ps.getLunch());
	    else
		excelRow.createCell(17).setCellValue("");
	    
	    if(ps.getLwp()!=null)
		excelRow.createCell(18).setCellValue(ps.getLwp());
	    else
		excelRow.createCell(18).setCellValue("");
	    
	    if(ps.getHma()!=null)
		excelRow.createCell(19).setCellValue(ps.getHma());
	    else
		excelRow.createCell(19).setCellValue("");
	    
	    //Total Deduction
	    double deduct = ps.getHma()+ps.getCfhi()+ps.getCfhoi()+ps.getLwp()+ps.getLunch()+ps.getTax();
	    
	    excelRow.createCell(20).setCellValue(deduct);
	    totdeduct = totdeduct + deduct;
	    
	    if(ps.getArrears()!=null)
		excelRow.createCell(21).setCellValue(ps.getArrears());
	    else
		excelRow.createCell(21).setCellValue("");
	    
	    if(ps.getBonus()!=null)
		excelRow.createCell(22).setCellValue(ps.getBonus());
	    else
		excelRow.createCell(22).setCellValue("");
	    
	    double netsal = (total-deduct)+ps.getArrears();
	    double bankpaid = netsal+ps.getBonus();
	    
	    excelRow.createCell(23).setCellValue(bankpaid);
	    totnetpay = totnetpay + bankpaid;
	    
	    for(int i=0;i<24;i++){
		excelRow.getCell(i).setCellStyle(style);
	    }
	}
	
	HSSFRow excelRow = excelSheet.createRow(record++);
	    
	excelRow.createCell(0).setCellValue("Total");
	excelRow.getCell(0).setCellStyle(style);
	
	excelRow.createCell(11).setCellValue(gross);
	excelRow.getCell(11).setCellStyle(style);
	
	excelRow.createCell(12).setCellValue(basic);
	excelRow.getCell(12).setCellStyle(style);
	
	excelRow.createCell(13).setCellValue(ha);
	excelRow.getCell(13).setCellStyle(style);
	
	excelRow.createCell(14).setCellValue(ma);
	excelRow.getCell(14).setCellStyle(style);
	
	excelRow.createCell(15).setCellValue(ta);
	excelRow.getCell(15).setCellStyle(style);
	
	excelRow.createCell(20).setCellValue(totdeduct);
	excelRow.getCell(20).setCellStyle(style);
	
	excelRow.createCell(23).setCellValue(totnetpay);
	excelRow.getCell(23).setCellStyle(style);
	
	HSSFRow excelRow1 = excelSheet.createRow(record++);
	excelRow1.createCell(0).setCellValue("Total Payble");
	excelRow1.getCell(0).setCellStyle(style);
	excelRow1.createCell(23).setCellValue(totnetpay);
	excelRow1.getCell(23).setCellStyle(style);
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
}
