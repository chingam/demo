package com.example.demo.view;

import java.util.List;

import lombok.Data;

@Data
public class PatientSearchReponse {
	String[] headings = {"First name", "Last name", "Address", "Zip code", "City", "Country"};
	List<MultiColumnSearch> values;
}
