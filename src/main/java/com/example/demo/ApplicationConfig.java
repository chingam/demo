package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

	@Value( "${store.file.dir}" )
	private String path;

	public String getPath() {
		return path;
	}
	
}
