package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.utils.ResponseHelper;

public abstract class AbstractController {
	@Autowired ResponseHelper response;
	final static String SUCCESS_MESSAGE = " Saved successfully";
	final static String UPDATE_MESSAGE = " Updated successfully";
	final static String DELETE_MESSAGE = " Deleted successfully";
	
}
