package com.example.demo.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.demo.ApplicationConfig;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class FileUploadController {

	@Autowired private ApplicationConfig appConfig;

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadFile(MultipartHttpServletRequest request) {
		Map<String, String> res = new HashMap<>();
		
		try {
			log.info("Called file upload");
			MultipartFile[] uploadfiles = request.getFileMap().entrySet().stream()
					.map(e -> e.getValue())
					.collect(Collectors.toList()).stream().toArray(MultipartFile[]::new);
			if (uploadfiles == null || uploadfiles.length == 0) {
				res.put("status", "error");
				return res;
			}
			String fileName = saveUploadedFile(uploadfiles);
			res.put("status", StringUtils.isNotEmpty(fileName) ? "success" : "error");
			res.put("message", fileName);
		} catch (Exception e) {
			res.put("status", "error");
			return res;
		}
		return res;
	}

	private String saveUploadedFile(MultipartFile[] uploadfiles) {
		String originalFilename = null;
		for (MultipartFile uploadfile : uploadfiles) {
			originalFilename = uploadfile.getOriginalFilename();
			try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(appConfig.getPath() + File.separator + originalFilename)))) {
				stream.write(uploadfile.getBytes());
			} catch (IOException e) {
				log.info("File is not stored");
			}
		}
		return originalFilename;
	}

}