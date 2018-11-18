package com.example.demo.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.ApplicationConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/document")
public class DownloadController {

	@Autowired
	private ApplicationConfig appConfig;

	@GetMapping(path = "/{id}/{fileType}")
	public ResponseEntity<byte[]> downloadDocuemnt(@PathVariable("id") String docId,
			@PathVariable("fileType") String fileType, HttpServletRequest req, HttpServletResponse res,
			final ModelMap model, Locale locale) {
		String fileName = docId + "." + fileType;
		String url = appConfig.getPath() + File.separator + fileName;
		File file = new File(url);
		if (file == null || !file.exists()) {
			log.warn("File: " + url + " not found");
			String errorMesg = "File not found";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("text", "plain"));
			return new ResponseEntity<>(errorMesg.getBytes(), headers, HttpStatus.NOT_FOUND);
		}

		final HttpHeaders headers = new HttpHeaders();
		if ("pdf".equals(fileType)) {
			headers.setContentType(new MediaType("application", "pdf"));
			headers.add("X-Content-Type-Options", "nosniff");
		}

		byte[] bytes = null;
		try (InputStream input = new FileInputStream(file)) {
			bytes = IOUtils.toByteArray(input);
		} catch (Exception e) {
			log.error("File not found");
		}
		log.info("File should be downloaded: " + fileName);
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		headers.add("Content-Disposition", headerValue);
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}

}
