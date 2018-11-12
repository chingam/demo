package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import com.example.demo.model.Button;
import com.example.demo.model.Button.Buttons;

public interface AbstractController {
	public default List<Button> getControls() {
		return Arrays.asList(new Button(Buttons.NEW, false, true), 
				new Button(Buttons.SAVE, false, true), 
				new Button(Buttons.CLEAR, false, true), 
				new Button(Buttons.ENTER_QUERY, false, true),
				new Button(Buttons.DELETE, false, true));
	}
}
