package com.yjh.pss.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yjh.pss.dao.BaseDao;
import com.yjh.pss.query.BaseQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IBaseService;

public class BaseServiceImpl<T> implements IBaseService<T> {

	protected BaseDao<T> baseDao;
	private Class<T> entityClass;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	// 通过反射获取子类的class
	public BaseServiceImpl() {
		// getClass() 返回此 Object 的运行时类。
		Class clz = getClass();
		// System.out.println("BaseServiceImpl clz:" + clz);

		// getGenericSuperclass() 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接父类的
		Type type = clz.getGenericSuperclass();
		// System.out.println("BaseServiceImpl type:" + type);

		// ParameterizedType 表示范型，如 Collection<String>,BaseServiceImpl<Employee>
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			// getActualTypeArguments() 返回表示此类型实际类型参数的 Type
			// 对象的数组。<Employee>=BaseServiceImpl<Employee>
			entityClass = (Class) pType.getActualTypeArguments()[0];
		}
		// System.out.println("BaseServiceImpl entityClass:" + entityClass);
	}

	@Override
	public void save(T t) {
		baseDao.save(t);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public void delete(Serializable id) {
		baseDao.delete(entityClass, id);
	}

	@Override
	public T get(Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll(entityClass);
	}

	@Override
	public PageList findByQuery(BaseQuery baseQuery) {
		return baseDao.findByQuery(baseQuery);
	}

	@Override
	public InputStream download(String[] heads, List<String[]> list) throws Exception {
		// 创建一个对象内存
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表
		Sheet sh = wb.createSheet();
		// 表中的第一行
		Row row0 = sh.createRow(0);
		for (int cellnum = 0; cellnum < heads.length; cellnum++) {
			// 创建表里面的第一个行，并对第一行中的列进行循环赋值
			// 处理单元格:列的索引是固定
			Cell cell = row0.createCell(cellnum);
			cell.setCellValue(heads[cellnum]);
		}
		// 查询的数据
		for (int rownum = 0; rownum < list.size(); rownum++) {
			// 创建表里面的行,从第二行开始
			Row row = sh.createRow(rownum + 1);
			// 处理每一行的列==String[]
			String[] strings = list.get(rownum);
			for (int cellnum = 0; cellnum < strings.length; cellnum++) {
				// 处理单元格
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(strings[cellnum]);
			}
		}
		// 内存缓冲流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		out.close();
		wb.dispose();
		// 把ByteArrayOutputStream转换为InputStream
		return new ByteArrayInputStream(out.toByteArray());

	}

	@Override
	public List<String[]> importFile(File upload) throws Exception {
		List<String[]> list = new ArrayList<String[]>();
		
		// 准备一个文件
		FileInputStream is = new FileInputStream(upload);
		// 创建读取对象
		Workbook workbook = new XSSFWorkbook(is);
		// 获取表
		Sheet sheet = workbook.getSheetAt(0);
		// System.out.println("总的行数:" + sheet.getLastRowNum());
		// 去掉表头的标题，直接从数据行开始读取
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			// 获取表里面的行对象
			Row row = sheet.getRow(i);
			// 获取当前行有多少列
			short cellNum = row.getLastCellNum();
			// 初始化获取的String数组
			String[] strings = new String[cellNum];
			// System.out.println("每行的列数:" + row.getLastCellNum());
			for (int j = 1; j < cellNum; j++) {
				// 获取单元格对象
				Cell cell = row.getCell(j);
				if (cell != null) {
					 // 根据类型不同输出不同类型
//					 if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//					 System.out.print(cell.getStringCellValue() + "\t");
//					 } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
//					 {
//					 System.out.print(cell.getNumericCellValue() + "\t");
//					 }

					// 只考虑所有的单元格里面都是字符串
					strings[j] = cell.getStringCellValue();
				}
			}
			// 一行的数据取完之后换行
			// System.out.println();
			// 保存完一条记录，即是完成对一个object对象的设置，即是数据库中的一条记录,将其存入list中
			list.add(strings);
		}
		// 关闭流
		is.close();
		return list;
	}

}
