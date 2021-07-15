package com.shubham.increff.controller;

import com.shubham.increff.Dto.BrandCategoryDto;
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

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class BrandCategoryController {
	@Autowired
	private BrandCategoryDto dto;

	@ApiOperation(value = "Upload brand Tsv")
	@RequestMapping(value = "/api/brandCategory/upload", method = RequestMethod.POST)
	public void getFile(@RequestPart("file") MultipartFile file) {
		// get your file as InputStream
		BufferedReader br;
		try {

			String line;
			InputStream is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				System.err.println(line);
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}


//		response.setContentType("text/csv");
//		response.addHeader("Content-disposition:", "attachment; filename=" + fileName);
//		String fileClasspath = "/com/increff/employee/" + fileName;
//		System.out.println(fileClasspath);
//		InputStream is = BrandCategoryController.class.getResourceAsStream(fileClasspath);
//		// copy it to response's OutputStream
//		try {
//			IOUtils.copy(is, response.getOutputStream());
//			response.flushBuffer();
//		} catch (IOException e) {
//			throw e;
//		} finally {
//			IOUtil.closeQuietly(is);
//		}

	}

	@ApiOperation(value = "Adds Brand and Category")
	@RequestMapping(path = "/api/brandCategory", method = RequestMethod.POST)
	public void add(@RequestBody BrandCategoryForm form) {
		dto.add(form);
	}

	@ApiOperation(value = "Gets list of all brand category")
	@RequestMapping(path = "/api/brandCategory", method = RequestMethod.GET)
	public List<BrandCategoryData> getAll() {
		return dto.getAll();
	}

	@ApiOperation(value = "Gets brand category by ID")
	@RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.GET)
	public BrandCategoryData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "Updates  Brand and Category")
	@RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandCategoryForm form) throws ApiException {
		dto.update(id,form);
	}

}
