package com.parkbobo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 操作EXCEL
 * @author LH
 *
 */

public class ExcelUtils {
	private static ExcelUtils excelUtils;
	/**
	 * 总行数
	 */
	private int totalRows = 0;
	/**
	 * 总列数
	 */
	private int totalCells = 0;
	/**
	 * 错误信息
	 */
	private String errorInfo;
	
	public synchronized static ExcelUtils getInstance()
	{
		if(excelUtils == null)
		{
			excelUtils = new ExcelUtils();
		}
		return excelUtils;
	}
	/**
	 * 根据文件读取Excel
	 * @param excelFile
	 * @return
	 */
	public List<List<String>> read(File excelFile)
	{
		List<List<String>> dataList = new ArrayList<List<String>>();
		InputStream is = null;
		try {
			//验证文件是否是excel文件
			if(!validateExcel(excelFile.getName()))
			{
				System.out.println(errorInfo);
				return null;
			}
			//判断excel版本
			boolean isExcel2003 = true;
			if(isExcel2007(excelFile.getName()))
			{
				isExcel2003 = false;
			}
			is = new FileInputStream(excelFile);
			dataList = read(is, isExcel2003);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(is != null)
			{
				try {
					is.close();
				} catch (Exception e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}
	/**
	 * 根据流读取Excel
	 * 
	 */
	public List<List<String>> read(InputStream inputStream, boolean isExcel2003)
	{
		List<List<String>> dataList = null;
		try {
			Workbook workbook = null;
			if(isExcel2003)
			{
				workbook = new HSSFWorkbook(inputStream);
			}
			else
			{
				workbook = new XSSFWorkbook(inputStream);
			}
			dataList = read(workbook);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	/**
	 * 读取数据
	 */
	public List<List<String>> read(Workbook workbook)
	{
		List<List<String>> dataList = new ArrayList<List<String>>();
		//得到第一个shell
		Sheet sheet = workbook.getSheetAt(0);
		//总行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		//总列数
		if(this.totalRows > 0 && sheet.getRow(0) != null)
		{
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		for(int r = 0; r < this.totalRows; r++)
		{
			Row row = sheet.getRow(r);
			if(row == null)
			{
				continue;
			}
			List<String> rowList = new ArrayList<String>();
			for(int c = 0; c < this.getTotalCells(); c++)
			{
				Cell cell = row.getCell(c);
				String cellValue = "";
				if(null != cell)
				{
					//判断数据类型
					switch (cell.getCellType()) 
					{
					case HSSFCell.CELL_TYPE_NUMERIC://数字
						cellValue = cell.getNumericCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_STRING://字符串
						cellValue = cell.getStringCellValue().trim();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN://boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA://公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK://空值
						cellValue = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR://故障
						cellValue = "非法字符";
					default:
						cellValue = "未知类型";
						break;
					}
				}
				rowList.add(removeTabs(cellValue));
			}
			//保存第r行的第c列
			dataList.add(rowList);
		}
		return dataList;
	}
	/**
	 * 检验Excel文件
	 */
	private boolean validateExcel(String filename)
	{
		if(filename == null || !(isExcel2003(filename) || isExcel2007(filename)))
		{
			this.errorInfo = "文件格式错误";
			return false;
		}
		return true;
	}
	/**
	 * 是否是2003的excel
	 * @return
	 */
	private boolean isExcel2003(String filename)
	{
		return filename.matches("^.+\\.(?i)(xls)$");
	}
	
	
	/**
	 * 是否是2007的excel
	 * @return
	 */
	private boolean isExcel2007(String filename)
	{
		System.out.println(filename);
		return filename.matches("^.+\\.(?i)(xlsx)$");
	}
	/**
	 * 去除制表符
	 * @return
	 */
	private String removeTabs(String value)
	{
		return value.replaceAll("\r", "")
			.replaceAll("\t", "")
			.replaceAll("\\s*", "")
			.replaceAll("\n", "");
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalCells() {
		return totalCells;
	}
	public void setTotalCells(int totalCells) {
		this.totalCells = totalCells;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	
		
		public static void main(String[] args) {
			System.out.println("qwe.xls".matches("^.+\\.(?i)(xls)$"));;
		}
	
}
