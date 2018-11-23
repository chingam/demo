package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t91011")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ServerLog implements Serializable{

	private static final long serialVersionUID = 1792313400519398969L;
	
	@Id
	private String messageId;
	private String transactionName;
	private String type;
	private Date date;
}
