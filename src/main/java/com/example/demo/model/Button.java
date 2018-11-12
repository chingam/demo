package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Button {

	private Buttons buttonName;
	private Boolean custEvent;
	private Boolean hasAccess;
	
	public enum Buttons{
		NEW ("btnNew", "Button New"), SAVE("btnSave", "Button Save"), DELETE("btnDelete", "Button Delete"), ENTER_QUERY("btnEnterQuery", "Button Enter"), CLEAR("btnClear", "Button Clear"), NEXT_QUERY("btnNextblock", "Button Next");
		
		String id;
		String cssClass;
		
		private Buttons(String id, String cssClass) {
			this.id = id;
			this.cssClass = cssClass;
		}
		public String getId() {
			return id;
		}
		public String getCssClass() {
			return cssClass;
		}
	}
}
