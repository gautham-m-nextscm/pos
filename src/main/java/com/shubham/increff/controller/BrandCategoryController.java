package com.shubham.increff.controller;

import com.shubham.increff.model.BrandCategoryData;
import com.shubham.increff.model.BrandCategoryForm;
import com.shubham.increff.pojo.BrandCategoryPojo;
import com.shubham.increff.service.ApiException;
import com.shubham.increff.service.BrandCategoryService;
import com.shubham.increff.util.IOUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class BrandCategoryController {
	@Autowired
	private BrandCategoryService service;

	@ApiOperation(value = "Upload brand Tsv")
	@RequestMapping(value = "/api/brandCategory/upload/{fileName:.+}", method = RequestMethod.GET)
	public void getFile(@PathVariable("fileName") String fileName, @RequestPart MultipartFile file, HttpServletResponse response) throws IOException {
		// get your file as InputStream
		response.setContentType("text/csv");
		response.addHeader("Content-disposition:", "attachment; filename=" + fileName);
		String fileClasspath = "/com/increff/employee/" + fileName;
		System.out.println(fileClasspath);
		InputStream is = BrandCategoryController.class.getResourceAsStream(fileClasspath);
		// copy it to response's OutputStream
		try {
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtil.closeQuietly(is);
		}

	}


	@ApiOperation(value = "Adds Brand and Category")
	@RequestMapping(path = "/api/brandCategory", method = RequestMethod.POST)
	public void add(@RequestBody BrandCategoryForm form) {
		BrandCategoryPojo p = convert(form);
		service.add(p);
	}

	@ApiOperation(value = "Gets list of all brand category")
	@RequestMapping(path = "/api/brandCategory", method = RequestMethod.GET)
	public List<BrandCategoryData> getAll() {
		List<BrandCategoryPojo> list = service.getAll();
		List<BrandCategoryData> list2 = new ArrayList<BrandCategoryData>();
		for (BrandCategoryPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Gets brand category by ID")
	@RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.GET)
	public BrandCategoryData get(@PathVariable int id) throws ApiException {
		BrandCategoryPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Updates  Brand and Category")
	@RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandCategoryForm f) throws ApiException {
		BrandCategoryPojo p = convert(f);
		service.update(id, p);
	}

//pathvariable vs requestparam

	private static BrandCategoryData convert(BrandCategoryPojo p) {
		BrandCategoryData d = new BrandCategoryData();
		d.setBrand(p.getBrand());
		d.setCategory(p.getCategory());
		d.setId(p.getId());
		return d;
	}

	private static BrandCategoryPojo convert(BrandCategoryForm f) {
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}
}
