package com.yjh.pss.service;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.yjh.pss.domain.Department;
import com.yjh.pss.domain.Employee;
import com.yjh.pss.service.impl.EmployeeServiceImpl;

/**
 * 生成一个xlsx文件
 * @author Administrator
 *
 */
public class XlsxTest {
	//生产一个excel表
	@Test
	public void write() throws Exception {
		// 创建一个对象内存
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表
		Sheet sh = wb.createSheet();
		for (int rownum = 1; rownum < 10; rownum++) {
			// 创建表里面的行
			Row row = sh.createRow(rownum);
			for (int cellnum = 1; cellnum < 10; cellnum++) {
				// 处理单元格
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(rownum * cellnum);
			}
		}
		FileOutputStream out = new FileOutputStream("xxxx.xlsx");
		wb.write(out);
		out.close();
		wb.dispose();

	}
	
	//生产一个excel表
	@Test
	public void write2() throws Exception {
		
		Employee employee = new Employee(1L);
		employee.setUsername("员工1");
		employee.setEmail("abby@qq.com");
		employee.setAge(25);
		Department dept1 = new Department(1L);
		dept1.setName("IT部门");
		employee.setDepartment(dept1);
		
		
		Employee employee2 = new Employee(2L);
		employee2.setUsername("员工2");
		employee2.setEmail("abby11111@qq.com");
		employee2.setAge(25);
		employee2.setDepartment(dept1);
		
		
		Employee employee3 = new Employee(3L);
		employee3.setUsername("员工3");
		employee3.setEmail("abby33333333@qq.com");
		employee3.setAge(26);
		Department dept2 = new Department(2L);
		dept2.setName("财务部门");
		employee3.setDepartment(dept2);
		
		List<Employee> employees = new ArrayList<>();
		employees.add(employee);
		employees.add(employee2);
		employees.add(employee3);
		
		// 创建一个对象内存
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表
		Sheet sh = wb.createSheet();
		for (int rownum = 0; rownum <employees.size(); rownum++) {
			// 创建表里面的行
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < 5; cellnum++) {
				// 处理单元格
				Cell cell = row.createCell(cellnum);
				switch (cellnum) {
				case 0:
					cell.setCellValue(employees.get(rownum).getId());
					break;
				case 1:
					cell.setCellValue(employees.get(rownum).getUsername());
					System.out.println(employee.getUsername()+rownum);
					break;
				case 2:
					cell.setCellValue(employees.get(rownum).getEmail());
					System.out.println(employees.get(rownum).getEmail()+rownum);
					break;
				case 3:
					cell.setCellValue(employees.get(rownum).getAge());
					System.out.println(employees.get(rownum).getAge()+rownum);
					break;
				case 4:
					cell.setCellValue(employees.get(rownum).getDepartment().getName());
					System.out.println(employees.get(rownum).getDepartment().getName() +rownum);
					break;
				default:
					break;
				}
			}
		}
		FileOutputStream out = new FileOutputStream("xxxx.xlsx");
		wb.write(out);
		out.close();
		wb.dispose();
	}
	
	//读取一个xlsx文件
	@Test
	public void read() throws Exception {
		// 准备一个文件
		FileInputStream is = new FileInputStream("xxxx.xlsx");
		// 创建读取对象
		Workbook workbook = new XSSFWorkbook(is);
		// 获取表
		Sheet sheet = workbook.getSheetAt(0);
		System.out.println("总的行数:" + sheet.getLastRowNum());
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			// 获取表里面的行对象
			Row row = sheet.getRow(i);
			System.out.println("每行的列数:" + row.getLastCellNum());
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// 获取单元格对象
				Cell cell = row.getCell(j);
				if (cell != null) {
					// 根据类型不同输出不同类型
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						System.out.print(cell.getStringCellValue() + "\t");
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						System.out.print(cell.getNumericCellValue() + "\t");
					}
				}
			}
			// 一行的数据取完之后换行
			System.out.println();
		}
		// 关闭流
		is.close();
	}

	@Test
	public void switchcase() throws Exception {
		List<Integer> integers = new ArrayList<>();
		integers.add(1);
		integers.add(2);
		integers.add(3);
		integers.add(4);
		for (int i = 1; i <= integers.size(); i++) {
//			if(i==1)System.out.println("aaaaaaa");
//			if(i==2)System.out.println("bbbbbbb");
//			if(i==3)System.out.println("ccccccc");
//			if(i==4)System.out.println("ddddddd");
			
			
			switch (i) {
			case 1:
				System.out.println("aaaaaaa"+i);
				break;
			case 2:
				System.out.println("bbbbbbb"+i);
				break;
			case 3:
				System.out.println("ccccccc"+i);
				break;
			case 4:
				System.out.println("dddddddddd"+i);
				break;

			default:
				System.out.println("default"+i);
				break;
			}
		}
//		System.out.println(integers);
		
		
	}
	
}
