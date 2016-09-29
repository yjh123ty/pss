package com.yjh.pss;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class CreateCodeTest {
	// 14.如果已经存在的文件，默认是不生成的，防止覆盖,千万不要用Employee来测试
	// flag = false:只生成没有成功创建的文件，不覆盖生成的文件；若为true，则要重新生成该domain的所有模板文件
	private static final  boolean flag = true;

	// 3.定义一下常量，规定文件夹的位置
	// 格式：全部都是以相对位置开始，前面没有/
	// 以/结尾
	private static final String SRC = "src/main/java/";
	private static final String TEST = "src/test/java/";
	private static final String RESOURCES = "src/main/resources/";
	private static final String PACKAGE = "com/yjh/pss/";
	private static final String WEBAPP = "src/main/webapp/";

	// 1.那些模型需要生成代码:先用一个临时模型Dept，进行测试
//	private String[] domains = { "Permission" };
//	private String[] domains = { "SystemDictionaryType" };
//	private String[] domains = { "SystemDictionaryDetail" };
//	private String[] domains = { "ProductType" };
//	private String[] domains = { "Product" };
//	private String[] domains = { "Supplier" };
//	private String[] domains = { "PurchaseBill" };
//	private String[] domains = { "PurchaseBillItem" };
//	private String[] domains = { "Depot" };
//	private String[] domains = { "ProductStock" };
//	private String[] domains = { "StockIncomeBill" };
	private String[] domains = { "StockIncomeBillItem" };
	
	// 2.有那些模板要生成:添加类或者jsp,xml,js
	private String[] templates = { "Service.java", "ServiceImpl.java", "Query.java", "Action.java", "ServiceTest.java",
			"hbm.xml", "Context.xml", "domain.js", "list.jsp", "input.jsp" };
	// 3.上面的面板生成到那个文件夹
	private String[] files = { SRC + PACKAGE + "service/", SRC + PACKAGE + "service/impl/", SRC + PACKAGE + "query/",
			SRC + PACKAGE + "web/action/", TEST + PACKAGE + "service/", RESOURCES + PACKAGE + "domain/", RESOURCES + "manager/",
			WEBAPP + "js/model/", WEBAPP + "WEB-INF/views/", WEBAPP + "WEB-INF/views/" };

	@Test
	public void create() throws Exception {
		// 判断:
		if (templates.length != files.length) {
			throw new RuntimeException("模板数量和输出文件数量不对应");
		}
		// 7.Velocity上下文
		VelocityContext context = new VelocityContext();
		// 4.外循环：模型数组
		for (int i = 0; i < domains.length; i++) {
			// 首字母小写
			String lowerDomain = domains[i].substring(0, 1).toLowerCase() + domains[i].substring(1);
			// 8.添加数据到Velocity上下文：大小写
			context.put("domain", domains[i]);
			context.put("lowerDomain", lowerDomain);
			// 6.内循环：模板或者文件夹
			for (int j = 0; j < templates.length; j++) {
				// 10.实例化File对象，打印输出绝对路径:步骤11之后
				File file = new File(files[j] + domains[i] + templates[j]);
				// 11.处理特殊的文件名称或者位置
				// 额外处理特殊路径,文件
				if ("Service.java".equals(templates[j])) {
					file = new File(files[j] + "I" + domains[i] + templates[j]);
				} else if ("hbm.xml".equals(templates[j])) {
					file = new File(files[j] + domains[i] + "." + templates[j]);
				} else if ("domain.js".equals(templates[j])) {
					file = new File(files[j] + lowerDomain + ".js");
				} else if ("list.jsp".equals(templates[j])) {
					file = new File(files[j] + lowerDomain + "/" + lowerDomain + ".jsp");
				} else if ("input.jsp".equals(templates[j])) {
					file = new File(files[j] + lowerDomain + "/" + lowerDomain + "_input.jsp");
				}
				// 13.创建父目录
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				// flag = false:不覆盖并且文件已经存在
				if (!flag && file.exists()) {
					// return;
					// break;
					continue;// 结束本次循环,进入下一次循环
				}
				System.out.println(file.getAbsolutePath());
				// 9.获取模板
				Template template = Velocity.getTemplate("template/" + templates[j], "UTF-8");
				// 12.实例化FileWriter
				FileWriter writer = new FileWriter(file);
				// 12.合并模板，关闭流
				template.merge(context, writer);
				// 关闭
				writer.close();
			}
		}
		System.out.println("刷新工程，没有错误");
		System.out.println("修改映射文件,运行测试,没有错误");
		System.out.println("启动tomcat,成功访问");

	}
}

