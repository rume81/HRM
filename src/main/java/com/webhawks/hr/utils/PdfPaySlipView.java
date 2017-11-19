package com.webhawks.hr.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.webhawks.Hawks_model.HMAttendance;
import com.webhawks.Hawks_model.HPaySlip;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PdfPaySlipView extends AbstractPdfView {

    private Font TIME_ROMAN = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    private Font normalsmall = new Font(Font.TIMES_ROMAN, 11, Font.NORMAL);
    private Font boldFont = new Font(Font.TIMES_ROMAN, 13, Font.BOLD);
    private Font normalFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL);

    @Override
    protected void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	
	HPaySlip slip = (HPaySlip) model.get("slip");

	String empname = slip.getEmp_name();
	int mon = slip.getMonth();
	int year = slip.getYear();
		
	String fname = "PaySlip_"+mon+"_"+year+"_"+empname+".pdf";
	
	response.setHeader("Content-Disposition", "attachment; filename=\""+fname+"\"");
	
	addMetaData(document);
	 
	addTitlePage(document);
	
	createTable(document,slip);
    }

    private void addMetaData(Document document) {
	document.addTitle("Generate PDF report");
	document.addSubject("Generate PDF report");
	// document.addAuthor("Java Honk");
	// document.addCreator("Java Honk");
    }

    private void addTitlePage(Document document) throws DocumentException, MalformedURLException, IOException {
	ClassLoader classLoader = getClass().getClassLoader();
	Image png = Image.getInstance(classLoader.getResource("front_logo_bottom.png").getFile());
        png.setAlignment(Image.ALIGN_CENTER);
        document.add(png);
        
	Paragraph preface = new Paragraph();
	preface.add(new Paragraph("House No: 1170, Road No: 50", normalFont));
	preface.add(new Paragraph("Flat No: 1st,5th,6th Floor,Avenue-12, Mirpur DOHS, Dhaka-1216,", normalFont));
	preface.add(new Paragraph("Bangladesh. Phone: +880258070348", normalFont));
	preface.add(new Paragraph("Email: info@webhawksit.com", normalFont));
	preface.setAlignment(Paragraph.ALIGN_CENTER);
	
	document.add(preface);

    }

    private void creteEmptyLine(Paragraph paragraph, int number) {
	for (int i = 0; i < number; i++) {
	    paragraph.add(new Paragraph(" "));
	}
    }
    
    private void createTable(Document document,HPaySlip slip) throws DocumentException {
	Paragraph paragraph = new Paragraph();
	creteEmptyLine(paragraph, 1);
	document.add(paragraph);
	
	PdfPTable table = new PdfPTable(3);
	table.setWidthPercentage(100);
		
	table.addCell(addCell("Name of the employee",normalFont,Element.ALIGN_LEFT));
	table.addCell(addCell(slip.getEmp_name(),normalFont,Element.ALIGN_CENTER));
	table.addCell(addCell("",normalFont,Element.ALIGN_CENTER));
	
	table.addCell(addCell("Employee ID",normalFont,Element.ALIGN_LEFT));
	table.addCell(addCell(slip.getEmp_finance_id(),normalFont,Element.ALIGN_CENTER));
	table.addCell(addCell("",normalFont,Element.ALIGN_CENTER));
	
	table.addCell(addCell("Pay Slip",boldFont,Element.ALIGN_LEFT));
	table.addCell(addCell("For the Month of "+getTextMonth(slip.getMonth())+" "+slip.getYear(),normalFont,Element.ALIGN_CENTER));
	table.addCell(addCell("",normalFont,Element.ALIGN_CENTER));
	
	document.add(table);
	
	PdfPTable table1 = new PdfPTable(1);
	table1.setWidthPercentage(100);
	
	table1.addCell(addCell("Scale of Payment",boldFont,Element.ALIGN_LEFT));
	
	document.add(table1);
	
	PdfPTable table2 = new PdfPTable(4);
	table2.setWidthPercentage(100);
	table2.setWidths(new int[]{2, 1, 2, 1});
	
	table2.addCell(addCell("Description",boldFont,Element.ALIGN_LEFT));
	table2.addCell(addCell("Days/Hr",boldFont,Element.ALIGN_LEFT));
	table2.addCell(addCell("Description",boldFont,Element.ALIGN_LEFT));
	table2.addCell(addCell("Amount",boldFont,Element.ALIGN_LEFT));
	
	table2.addCell(addCell("Standard working days in the month",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getSwdm()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("Gross Salary",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getGross()),normalsmall,Element.ALIGN_RIGHT));
	
	table2.addCell(addCell("Actual working days in the month",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getAwdm()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("Basic pay for a month",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getBasic()),normalsmall,Element.ALIGN_RIGHT));
	
	table2.addCell(addCell("Approved Leave",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getAppleave()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("Daily Basic Pay Rate",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getDbpr()),normalsmall,Element.ALIGN_RIGHT));
	
	table2.addCell(addCell("Absent",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getAbsent()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("Hourly Basic Pay Rate",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getHbpr()),normalsmall,Element.ALIGN_RIGHT));
	
	table2.addCell(addCell("Actual working hour of the month",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getAwhm()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	
	table2.addCell(addCell("Overtime allowed of the month",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell(String.valueOf(slip.getOaom()),normalsmall,Element.ALIGN_CENTER));
	table2.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table2.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	
	document.add(table2);
	
	PdfPTable table3 = new PdfPTable(1);
	table3.setWidthPercentage(100);
	
	table3.addCell(addCell("Computation of the gross salary to be paid for the month of "+getTextMonth(slip.getMonth()),boldFont,Element.ALIGN_LEFT));
	
	document.add(table3);
	
	PdfPTable table4 = new PdfPTable(4);
	table4.setWidthPercentage(100);
	table4.setWidths(new int[]{2, 1, 2, 1});
	double total = 0;
	table4.addCell(addCell("Number of days worked in the month",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getNodwm()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Basic Salary to be paid",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getBstbp()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getBstbp();
	
	table4.addCell(addCell("Number of hours worked in the month",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getNohwm()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Salary of overtime working",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getSoow()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getSoow();
	
	table4.addCell(addCell("Hours of overtime",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getHoo()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Salary of holiday overtime",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getSoho()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getSoho();
	
	table4.addCell(addCell("Overtime hrs in holiday",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getOhih()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Pay for total night hours",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getPftnh()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getPftnh();
	
	table4.addCell(addCell("Hours total night shift",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getHtns()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Salary for all paid leaves",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getSfapl()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getSfapl();
	
	table4.addCell(addCell("Total paid leaves",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getTpl()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Business trip adjustment",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getBta()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getBta();
	
	table4.addCell(addCell("No of training and seminars",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getNots()),normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Housing allowance",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getHa()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getHa();
	
	table4.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Medical allowance",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getMa()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getMa();
	
	table4.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Transportation",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getTa()),normalsmall,Element.ALIGN_RIGHT));
	total = total + slip.getTa();
	
	table4.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Total Gross Salary",boldFont,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(total),normalsmall,Element.ALIGN_RIGHT));
	
	table4.addCell(addCell("",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell("",normalsmall,Element.ALIGN_RIGHT));
	table4.addCell(addCell("Bonus",normalsmall,Element.ALIGN_LEFT));
	table4.addCell(addCell(String.valueOf(slip.getBonus()),normalsmall,Element.ALIGN_RIGHT));
	
	document.add(table4);
	
	PdfPTable table5 = new PdfPTable(1);
	table5.setWidthPercentage(100);
	
	table5.addCell(addCell("Break Up of deductions for the month "+getTextMonth(slip.getMonth()),boldFont,Element.ALIGN_LEFT));
	
	document.add(table5);
	
	PdfPTable table6 = new PdfPTable(2);
	table6.setWidthPercentage(100);
	table6.setWidths(new int[]{5, 1});
	double deduct = 0;
	
	table6.addCell(addCell("Contribution for social security/(Half month Advanced)",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getHma()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getHma();
	
	table6.addCell(addCell("Contribution for health insurance",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getCfhi()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getCfhi();
	
	table6.addCell(addCell("Contribution for housing insurance",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getCfhoi()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getCfhoi();
	
	table6.addCell(addCell("Leave Without Payment (LWP)",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getLwp()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getLwp();
	
	table6.addCell(addCell("Lunch",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getLunch()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getLunch();
	
	table6.addCell(addCell("Amount of withholding tax (if any)",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getTax()),normalsmall,Element.ALIGN_RIGHT));
	deduct = deduct + slip.getTax();
	
	table6.addCell(addCell("Total Deductions",boldFont,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(deduct),normalsmall,Element.ALIGN_RIGHT));
	
	table6.addCell(addCell("Arrears (if any)",normalsmall,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(slip.getArrears()),normalsmall,Element.ALIGN_RIGHT));
	
	double netSal = (total-deduct)+slip.getArrears();
	table6.addCell(addCell("Net salary",boldFont,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(netSal),normalsmall,Element.ALIGN_RIGHT));
	
	double bank = netSal+slip.getBonus();
	table6.addCell(addCell("Amount payable to bank account",boldFont,Element.ALIGN_RIGHT));
	table6.addCell(addCell(String.valueOf(bank),normalsmall,Element.ALIGN_RIGHT));
	
	document.add(table6);
    }
    
    public PdfPCell addCell(String text,Font font,int align){
	PdfPCell c = new PdfPCell(new Phrase(text,font));
	c.setHorizontalAlignment(align);
	
	return c;
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
