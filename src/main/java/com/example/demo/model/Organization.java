package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t91002")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Organization implements Serializable {
	private static final long serialVersionUID = 4810219849710544965L;
	@Id
	@Column(name = "t_idnumber")
	private String idNumber;
	@Column(name = "t_organizationname")
	private String organizationName;
}
