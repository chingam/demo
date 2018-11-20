package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

	@Value( "${store.file.dir}" )
	private String path;
	
	@Value( "${system.reposotory.id}" )
	private String repositoryId;
	
	@Value( "${system.home.id}" )
	private String homeCommunityId;
	
	@Value( "${system.assigningauthority}" )
	private String assigningauthority;

	
	public String getRepositoryId() {
		return repositoryId;
	}
	
	public String getHomeCommunityId() {
		return homeCommunityId;
	}
	
	public String getPath() {
		return path;
	}

	public String getAssigningauthority() {
		return assigningauthority;
	}
	
}
