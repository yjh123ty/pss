package com.yjh.pss.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.yjh.pss.domain.Product;
import com.yjh.pss.domain.ProductType;
import com.yjh.pss.query.ProductQuery;
import com.yjh.pss.query.PageList;
import com.yjh.pss.service.IProductService;
import com.yjh.pss.service.IProductTypeService;
import com.yjh.pss.service.ISystemDictionaryDetailService;

import net.coobird.thumbnailator.Thumbnails;


public class ProductAction extends CRUDAction<Product>{
	private static final long serialVersionUID = 1L;
	
	private File upload;
	
	//依赖注入
	private IProductService productService;
	private IProductTypeService productTypeService; 
	private ISystemDictionaryDetailService systemDictionaryDetailService;
	
	//前台对象模型 ,ModelDriver切换成栈顶对象，因此不需要getter,setter
	private Product product;
	
	// 分页对象,getter方法
	private PageList pageList;
	
	// 必须实例化,getter,setter方法
	private ProductQuery baseQuery = new ProductQuery();
	
	//列表
	@Override
	protected void list() throws Exception {
		this.pageList = productService.findByQuery(baseQuery);
	}
	
	
	//中转
	@Override
	public String input() throws Exception {
		// 需要选择产品一级类型列表（直接在数据库拿出来）
		putContext("allParents", productTypeService.getParents());
				
		// 二级类型列表
		List<ProductType> allChildrens = new ArrayList<ProductType>();
		ProductType type = product.getType();
		//若获取到了值，则说明此处有选择了某种二级菜单中的选项（前台只会给二级菜单的选项，因此后台收到的也只会是二级菜单的数据）
		if(type != null){
			//获取父级菜单
			ProductType parentType = type.getParent();
			// 一级类型有值,!=-1
			if (parentType != null && parentType.getId() != -1L) {
				List<ProductType> childrenList = productTypeService.findChildren(parentType.getId());
				allChildrens.addAll(childrenList);
			}
		}else {
			//这种情况说明没有选择二级菜单
			ProductType productType = new ProductType();
			productType.setId(-1L);
			productType.setName("--请选择--");
			allChildrens.add(productType);
		}
		// 需要选择产品二级类型列表
		putContext("allChildrens", allChildrens);
		
				
		//设置品牌
		putContext("allBrands", systemDictionaryDetailService.getBrands());
		//设置单位
		putContext("allUnits", systemDictionaryDetailService.getUnits());
		return INPUT;
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssS");
	
	//保存
	@InputConfig(methodName = "input")
	@Override
	public String save() throws Exception {
		
		if (upload != null) {
			String webapp = ServletActionContext.getServletContext().getRealPath("/");
			// 修改怎样处理原图:直接删除
			if (id != null && StringUtils.isNotBlank(product.getPic())) {
				File picFile = new File(webapp, product.getPic());
				if (picFile.exists()) {
					picFile.delete();
				}
				File smallPicFile = new File(webapp, product.getSmallPic());
				if (smallPicFile.exists()) {
					smallPicFile.delete();
				}
			}

			// 上传文件命名,拷贝到webapp的位置,设置pic到product
			Date date = new Date();
			// 定义上传图片的存在的相对位置
			String fileName = "upload/" + sdf.format(date) + ".png";
			String smallFileName = "upload/" + sdf.format(date) + "_small.png";
			// 定义上传图片服务器的路径
			File destFile = new File(webapp, fileName);
			File smallDestFile = new File(webapp, smallFileName);
			// 如果没有upload父目录，就创建
			File parentFile = destFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();// 自动生成upload目录
			}
			// 把tomcat上传的临时文件拷贝到webapp/upload里面
			System.out.println("临时文件:" + upload.getAbsolutePath());
			System.out.println("大图路径：" + destFile.getAbsolutePath());
			FileUtils.copyFile(upload, destFile);
			// 缩略图:10%
			Thumbnails.of(upload).scale(0.1F).toFile(smallDestFile);
			// 把相对路径设置到产品模型
			product.setPic(fileName);
			product.setSmallPic(smallFileName);
		}
		try {
			if (id == null) {
				baseQuery.setCurrentPage(Integer.MAX_VALUE);
				System.out.println("保存产品");
				productService.save(product);
				// 保存的时候,跳转到最后一页
				addActionMessage("保存成功");
			}else{
				productService.update(product);
				addActionMessage("修改成功");
			}
		} catch (Exception e) {
			addActionError("异常:" + e.getMessage());
			System.out.println("添加产品失败！！！");
			return input();
		}
		return RELOAD;
	}
	
	
	//使用ajax删除
	@Override
	public String delete() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (id != null) {
				String webapp = ServletActionContext.getServletContext().getRealPath("/");
				// 必须先获取产品对象
				product = productService.get(id);
				// 最后才删除数据库记录
				productService.delete(id);
				File picFile = new File(webapp, product.getPic());
				if (picFile.exists()) {
					picFile.delete();
				}
				File smallPicFile = new File(webapp, product.getSmallPic());
				if (smallPicFile.exists()) {
					smallPicFile.delete();
				}
				out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			}else{
				out.print("{\"success\":false,\"msg\":\"删除必须传递id\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();// 便于排错
			out.print("{\"success\":false,\"msg\":\"删除出现异常:"+e.getMessage()+"\"}");
		}
		return null;// 不返回页面,只返回文本

	}
	
	@Override
//	执行action的方法之前做额外的事情
	public void prepare() throws Exception {
	}
	
	//只在调用input方法前调用
	@Override
	public void prepareInput() throws Exception {
		if(id!=null){	//修改
			product = productService.get(id);
		}else{
			//???
			product = new Product();
		}
	}
	
	//只在调用save方法前调用
	@Override
	public void prepareSave() throws Exception {
		if (id != null ) {
			product = productService.get(id);
			product.setType(null);
			product.setUnit(null);
			product.setBrand(null);
		} else {
			//变成栈顶对象，保证没有修改的对象属性没有被丢失
			product = new Product();
		}
		System.out.println("beforeSave......");
	}
	
	// 采购定义点击选择产品的访问此方法
	public String bill() throws Exception {
		this.pageList = productService.findByQuery(baseQuery);
		return "bill";
	}
	

	 //依赖注入
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public void setProductTypeService(IProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	public void setSystemDictionaryDetailService(ISystemDictionaryDetailService systemDictionaryDetailService) {
		this.systemDictionaryDetailService = systemDictionaryDetailService;
	}
	
	// 查询对象的封装
	public ProductQuery getBaseQuery() {
		return baseQuery;
	}
	public void setBaseQuery(ProductQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	 // 查询后结果的封装
	public PageList getPageList() {
		return pageList;
	}

    // 模型驱动
	@Override
	public Product getModel() {
		return product;
	}

	//上传文件
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	// 自动获取上传文件的类型和上传文件的名称，getter，setter必须符合FileUploadInterceptor规范
	private String contentType;
	private String fileName;
	
	public String getUploadContentType() {
		return contentType;
	}

	public void setUploadContentType(String contentType) {
		this.contentType = contentType;
		System.out.println("contentType:" + contentType);
	}

	public String getUploadFileName() {
		return fileName;
	}

	public void setUploadFileName(String fileName) {
		this.fileName = fileName;
		System.out.println("fileName:" + fileName);
	}
	
	// ajax:处理二级联动的数据
	public String findChildren() {
		List<ProductType> childrenList = productTypeService.findChildren(id);
		putContext("map", childrenList);
		return "jsonResult";
	}
	

}
