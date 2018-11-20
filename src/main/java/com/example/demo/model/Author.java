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
@Table(name = "t91003")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author implements Serializable {

	private static final long serialVersionUID = -2576395587407762051L;
	@Id
	@Column(name = "t_id")
	private String id;
	@Column(name = "t_organizationid")
	private String organizationId;
	@Column(name = "t_authorspecialtyid")
	private String authorSpecialtyId;
	@Column(name = "t_name")
	private String name;

}
