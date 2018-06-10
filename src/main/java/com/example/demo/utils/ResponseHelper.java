package com.example.demo.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


/**
 * Helper class for storing processing status and status message and returning json format in a common structure.<br>
 * This class can be autowired in a controller or taken as method parameter. 
 * 
 * @author Minhaj
 *
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResponseHelper {
	public static final String DEFAULT_INFO_MESSAGE = "";
	public static final String DEFAULT_ERROR_MESSAGE = "Failed to process";
	public static final String DEFAULT_SUCCESS_MESSAGE = "Processing was successful";
	public static final String STATUS_KEY = "status";
	public static final String MESSAGE_KEY = "message";
	public static final String APPEND_SEPERATOR = ", ";
	public enum STATUS {INFO, ERROR, SUCCESS, WARN}
	private STATUS status;
	private String infoMessage;
	private String errorMessage;
	private String successMessage;
	private String warnMessage;
	private Map<String, Object> others;
	
	public void setInfoStatus() {
		setStatus(STATUS.INFO);
	}
	
	public void setErrorStatus() {
		setStatus(STATUS.ERROR);
	}
	
	public void setSuccessStatus() {
		setStatus(STATUS.SUCCESS);
	}
	public void setWarnStatus() {
		setStatus(STATUS.WARN);
	}

	public void setStatus(int result) {
		setStatus(result == 1);
	}

	public void setStatus(boolean result) {
		if (result == Boolean.TRUE) setSuccessStatus();
		else setErrorStatus();
	}
	
	public void setMessages(String infoMessage, String errorMessage, String successMessage) {
		this.infoMessage = infoMessage;
		this.errorMessage = errorMessage;
		this.successMessage = successMessage;
	}
	
	public void setMessages(String errorMessage, String successMessage) {
		this.errorMessage = errorMessage;
		this.successMessage = successMessage;
	}
	
	public void setInfoMessage(String message) {
		this.infoMessage = message;
	}
	
	public void setErrorMessage(String message) {
		this.errorMessage = message;
	}
	
	public void setSuccessMessage(String message) {
		this.successMessage = message;
	}
	
	public void setInfoStatusAndMessage(String message) {
		this.infoMessage = message;
		setInfoStatus();
	}
	
	public void setErrorStatusAndMessage(String message) {
		this.errorMessage = message;
		setErrorStatus();
	}
	
	public void appendErrorMessage(String message) {
		if(StringUtils.isEmpty(message)) return;
		this.errorMessage = this.errorMessage == null ? message : (this.errorMessage + APPEND_SEPERATOR + message);
	}
	
	public void setErrorStatusAndMessage(Exception e) {
		this.errorMessage = getExceptionMessage(e);
		setErrorStatus();
	}
	
	public void setErrorStatusAndMessage(String message, Exception e) {
		this.errorMessage = getExceptionMessage(e);
		if(StringUtils.isNotEmpty(message)) this.errorMessage = message.concat(" Error : ").concat(this.errorMessage);
		setErrorStatus();
	}
	
	private String getExceptionMessage(Exception e) {
		return e.getMessage().contains("::") ? e.getMessage().substring(e.getMessage().indexOf("::") + 3) : e.getMessage();
	}
	
	public void setSuccessStatusAndMessage(String message) {
		this.successMessage = message;
		setSuccessStatus();
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setOther(String key, Object value) {
		getOthers().put(key, value);
	}
	
	public void setOthers(Map<String, Object> others) {
		this.others = others;
	}
	
	public String getWarnMessage() {
		return warnMessage;
	}

	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}

	public void appendOther(String key, String value) {
		if (getOthers().get(key) == null) {
			setOther(key, value);
		} else {
			setOther(key, getOthers().get(key) + APPEND_SEPERATOR + value);
		}
	}
	
	public Map<String, Object> getOthers() {
		if(others == null) {
			others = new HashMap<>();
		}
		return others;
	}
	
	public String getMessage() {
		if(getStatus() == null || getStatus() == STATUS.ERROR) {
			return getErrorMessage() == null ? DEFAULT_ERROR_MESSAGE : getErrorMessage();
		} else if(getStatus() == STATUS.INFO) {
			return getInfoMessage() == null ? DEFAULT_INFO_MESSAGE : getInfoMessage();
		} else {
			return getSuccessMessage() == null ? DEFAULT_SUCCESS_MESSAGE : getSuccessMessage();
		}
	}

	public Map<String, Object> prepareAndGetJSONResponse() {
		Map<String, Object> map = getOthers();
		map.put(STATUS_KEY, (getStatus() == null ? STATUS.ERROR : getStatus()).name().toLowerCase());
		map.put(MESSAGE_KEY, getMessage());
		return Collections.synchronizedMap(map);
	}

	@SuppressWarnings("unchecked")
	public void addReload(String id, String url) {
		Map<String, String> realods = (Map<String, String>) getOthers().get("reloads");
		if (realods == null) realods = new LinkedHashMap<>();
		realods.put(id, url);
		getOthers().put("reloads", realods);
	}
}