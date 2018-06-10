package com.example.demo.view;

import java.util.List;

public class ProductSearchReponse {
	String[] headings = {"Product name", "Description"};

	List<MultiColumnSearch> values;

	public ProductSearchReponse() {
		super();
	}

	public ProductSearchReponse(List<MultiColumnSearch> values) {
		this.values = values;
	}

	public String[] getHeadings() {
		return headings;
	}

	public void setHeadings(String[] headings) {
		this.headings = headings;
	}

	public List<MultiColumnSearch> getValues() {
		return values;
	}

	public void setValues(List<MultiColumnSearch> values) {
		this.values = values;
	}

	public ProductSearchReponse(String[] headings, List<MultiColumnSearch> values) {
		super();
		this.headings = headings;
		this.values = values;
	}	
}
